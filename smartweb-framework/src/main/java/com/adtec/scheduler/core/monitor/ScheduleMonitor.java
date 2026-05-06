package com.adtec.scheduler.core.monitor;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.scheduler.config.SchedulerProperties;
import com.adtec.scheduler.core.event.ScheduleServerNodeRegisterEvent;
import com.adtec.scheduler.core.event.ScheduleTaskNodeRegisterEvent;
import com.adtec.scheduler.core.event.ScheduleTaskNodeRemoveEvent;
import com.adtec.scheduler.core.manager.ScheduleJobManager;
import com.adtec.scheduler.core.manager.ScheduleServerManager;
import com.adtec.scheduler.core.manager.impl.ScheduleJobZkManager;
import com.adtec.scheduler.core.manager.impl.ScheduleServerZkManager;
import com.adtec.scheduler.core.model.ScheduleServerZkDTO;
import com.adtec.scheduler.core.util.ScheduleUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lijunbin
 */
public class ScheduleMonitor implements InitializingBean {

    private static final transient Logger LOG = LoggerFactory.getLogger(ScheduleMonitor.class);

    private static final String SERVER_PATH = "server";

    private static final String TASK_PATH = "task";


    private CuratorFramework client;
    private final SchedulerProperties schedulerProperties;
    private final ScheduleJobManager<CuratorFramework> scheduleJobManager;
    private final ScheduleServerManager<CuratorFramework> scheduleServerManager;

    private final String serverNode;
    private final String taskNode;

    public ScheduleMonitor(SchedulerProperties schedulerProperties) {
        this.schedulerProperties = schedulerProperties;
        this.scheduleJobManager = new ScheduleJobZkManager(schedulerProperties);
        this.scheduleServerManager = new ScheduleServerZkManager(schedulerProperties);
        String projectNode = schedulerProperties.getScheduleProjectNode();
        this.serverNode = projectNode + "/" + SERVER_PATH;
        this.taskNode = projectNode + "/" + TASK_PATH;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public ScheduleJobManager<CuratorFramework> getScheduleJobManager() {
        return scheduleJobManager;
    }

    public ScheduleServerManager<CuratorFramework> getScheduleServerManager() {
        return scheduleServerManager;
    }

    public String getServerNode() {
        return serverNode;
    }

    public String getTaskNode() {
        return taskNode;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (ScheduleUtil.checkClusterFlag()) {
            this.initClient();
        }
    }

    private void initClient() {
        int baseSleepTimeMs, maxRetries, sessionTimeoutMs, connectionTimeoutMs;
        String zkServerList = schedulerProperties.getZkServerList();
        try {
            baseSleepTimeMs = Integer.parseInt(schedulerProperties.getZkRetryInterval());
            maxRetries = Integer.parseInt(schedulerProperties.getZkRetryTimes());
            sessionTimeoutMs = Integer.parseInt(schedulerProperties.getZkSessionTimeout());
            connectionTimeoutMs = Integer.parseInt(schedulerProperties.getZkConnectionTimeout());
            if (zkServerList.isEmpty()) {
                throw new BaseException(SysErr.E_MESSAGE, "集群模式下" + SchedulerProperties.ZK_SERVER_LIST + "不能为空！");
            }
        } catch (Exception e) {
            throw new BaseException(SysErr.E_MESSAGE, e, "smartweb的zookeeper重连配置参数格式异常！");
        }
        final RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        client = CuratorFrameworkFactory.builder().connectString(schedulerProperties.getZkServerList())
                .retryPolicy(retryPolicy).sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs)
                .build();
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState state) {
                switch (state) {
                    case LOST:
                        // 挂起后重试超时，客户端认为与zk服务器的连接丢失
                        LOG.warn("lost connection with zookeeper");
                        // 重新建立连接
                        initClient();
                        break;
                    case CONNECTED:
                        // 成功建立连接
                        LOG.info("connected with zookeeper");
                        initNode();
                        initWatchAndRegisterServer();
                        break;
                    case RECONNECTED:
                        // 挂起或者丢失连接后重新连接
                        LOG.info("reconnected with zookeeper");
                        initWatchAndRegisterServer();
                        break;
                    default:
                        break;
                }
            }
        });
        client.start();
    }

    private void initProjectNode() {
        String scheduleProjectNode = schedulerProperties.getScheduleProjectNode();
        // 当zk状态正常后才能调用
        LOG.info("开始初始化工程节点{}", scheduleProjectNode);
        try {
            if (null == client.checkExists().forPath(scheduleProjectNode)) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(scheduleProjectNode);
            }
        } catch (Exception e) {
            LOG.error("初始化工程节点" + scheduleProjectNode + "失败.", e);
        }
    }

    private void initNode(String path) {
        try {
            if (null == this.client.checkExists().forPath(path)) {
                this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            }
        } catch (Exception e) {
            LOG.error("initNode failed", e);
        }
    }

    private void initNode() {
        this.initProjectNode();
        this.initNode(taskNode);
        this.initNode(serverNode);
    }

    /**
     * 监听指定节点
     * <p>
     * 主要做重新分配任务使用
     *
     * @param path
     */
    private void watchPath(String path) {
        try {
            // 监听子节点变化情况
            final PathChildrenCache watcher = new PathChildrenCache(this.client, path, true);
            watcher.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            watcher.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            LOG.info("监听到节点变化: 新增path=: {}", event.getData().getPath());
                            // 新增server节点和task节点都需要触发重新分配任务事件
                            if (event.getData().getPath().startsWith(serverNode)) {
                                // 新增server节点触发 ServerNodeAddEvent
                                SpringContextHolder.publishEvent(new ScheduleServerNodeRegisterEvent(event.getData().getPath()));
                            } else {
                                // 新增task节点需要触发重新分配任务事件
                                SpringContextHolder.publishEvent(new ScheduleTaskNodeRegisterEvent(event.getData().getPath()));
                            }
                            break;
                        case CHILD_REMOVED:
                            LOG.info("监听到节点变化: 删除path=: {}", event.getData().getPath());
                            // 删除task节点不需要发布重新分配任务事件
                            if (event.getData().getPath().startsWith(serverNode)) {
                                SpringContextHolder.publishEvent(new ScheduleServerNodeRegisterEvent(event.getData().getPath()));
                            } else {
                                SpringContextHolder.publishEvent(new ScheduleTaskNodeRemoveEvent(event.getData().getPath()));
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("watchPath failed", e);
        }
    }

    /**
     * 初始化监听和注册当前server节点
     */
    private void initWatchAndRegisterServer() {
        this.scheduleJobManager.setClient(this.client);
        this.scheduleServerManager.setClient(this.client);
        // 监听节点, 负责重新分配任务
        this.watchPath(this.taskNode);
        this.watchPath(this.serverNode);
        // 设置为未注册
        ScheduleServerZkDTO.getInstance().setRegister(false);
        // 注册当前server
        this.scheduleServerManager.registerServer(ScheduleServerZkDTO.getInstance());
    }

}
