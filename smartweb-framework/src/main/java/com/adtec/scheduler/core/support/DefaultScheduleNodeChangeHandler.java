package com.adtec.scheduler.core.support;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.scheduler.core.event.ScheduleStopRegisterEvent;
import com.adtec.scheduler.core.event.ScheduleTaskNodeRegisterEvent;
import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;
import com.adtec.scheduler.core.model.ScheduleJobZkDTO;
import com.adtec.scheduler.core.model.ScheduleServerZkDTO;
import com.adtec.scheduler.core.monitor.ScheduleMonitor;
import com.adtec.scheduler.core.rules.balance.ServerBalanceDispatch;
import com.adtec.scheduler.core.rules.balance.impl.PollingServerBalanceDispatch;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.entity.ScheduleJobDO;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunbin
 */
public class DefaultScheduleNodeChangeHandler implements ScheduleNodeChangeHandler {

    private static final transient Logger log = LoggerFactory.getLogger(DefaultScheduleNodeChangeHandler.class);

    private final ThreadPoolTaskGenerator taskGenerator;
    private final ScheduleMonitor scheduleMonitor;
    private final ServerBalanceDispatch serverDispatch;

    private final Object lock = new Object();

    public DefaultScheduleNodeChangeHandler() {
        this.taskGenerator = SpringContextHolder.getBean(ThreadPoolTaskGenerator.class);
        this.scheduleMonitor = SpringContextHolder.getBean(ScheduleMonitor.class);
        this.serverDispatch = new PollingServerBalanceDispatch();
    }

    @Override
    public ThreadPoolTaskGenerator getTaskGenerator() {
        return this.taskGenerator;
    }

    @Override
    public void handleServerNodeRegisterEvent(String path) {
        final List<String> servers = scheduleMonitor.getScheduleServerManager().getServers();
        if (!scheduleMonitor.getScheduleServerManager().isMaster(ScheduleServerZkDTO.getInstance().getUuid(), servers)) {
            log.info("当前server:[" + ScheduleServerZkDTO.getInstance().getUuid() + "]: 不是负责任务分配的Leader,直接返回");
            return;
        }
        final String serverId = path.substring(path.lastIndexOf("/") + 1);
        List<String> tasks = new ArrayList<>();
        try {
            tasks.addAll(scheduleMonitor.getClient().getChildren().forPath(scheduleMonitor.getTaskNode()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tasks.size() <= servers.size()) {
            log.info("任务数小于 server 节点数, 不进行任务重新分配");
            return;
        }
        final BigDecimal len = new BigDecimal(tasks.size()).divide(new BigDecimal(servers.size()), 0, RoundingMode.DOWN);
        // 先清理对异常未下线的task节点
        try {
			List<String> serverList = scheduleMonitor.getClient().getChildren().forPath(scheduleMonitor.getServerNode());
			for(String taskName:tasks){
				List<String> taskInst = scheduleMonitor.getClient().getChildren().forPath(scheduleMonitor.getTaskNode()+"/"+taskName);
				String taskPath = scheduleMonitor.getTaskNode()+"/"+taskName;
				boolean delFlag = false;
				for(String inst:taskInst){
					ScheduleJobDO scheduleJobDO = ScheduleUtil.get(taskName);
					if(null == scheduleJobDO || !scheduleJobDO.statusRunning()) {
						delFlag = true;
						scheduleMonitor.getClient().delete().forPath(taskPath);
						break;
					}
					if(!serverList.contains(inst)){
						String taskInstPath = taskPath+"/"+inst;
						scheduleMonitor.getClient().delete().forPath(taskInstPath);
						continue;
					}
				}
				if(!delFlag) {
					List<String> taskInstList = scheduleMonitor.getClient().getChildren().forPath(scheduleMonitor.getTaskNode()+"/"+taskName);
					if(CollectionUtils.isEmpty(taskInstList)){
						scheduleMonitor.getClient().delete().forPath(taskPath);
						ScheduleJobZkDTO scheduleJobZkDTO = new ScheduleJobZkDTO();
                        scheduleJobZkDTO.setBeanName(taskName);
						scheduleMonitor.getScheduleJobManager().save(scheduleJobZkDTO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        for (int i = 0; i < len.longValue(); i++) {
            // 分配指定任务给指定server
            assignTask2Server(tasks.get(i), serverId);
        }
    }

    @Override
    public void handleServerNodeOfflineEvent(String path) {
        handleServerNodeRegisterEvent(path);
    }

    @Override
    public void handleTaskNodeRegisterEvent(String path) {
        List<String> serverList = scheduleMonitor.getScheduleServerManager().getServers();
        // 执行任务分配
        assignTask(ScheduleServerZkDTO.getInstance().getUuid(), serverList);
    }

    @Override
    public void handleTaskNodeRemoveEvent(String path) {
        int index = path.lastIndexOf("/");
        if (index > 0) {
            String beanName = path.substring(index + 1);
            ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
            SpringContextHolder.publishEvent(new ScheduleStopRegisterEvent(scheduleJobDO));
        }
    }

    /**
     * 分配任务
     *
     * @param currentUuid    当前服务器uuid
     * @param taskServerList 所有服务器uuid(过滤后的)
     */
    public void assignTask(String currentUuid, List<String> taskServerList) {
        if (CollectionUtils.isEmpty(taskServerList)) {
            log.info("当前Server List 为空, 暂不能分配任务...");
            return;
        }
        log.info("当前server:[" + currentUuid + "]: 开始重新分配任务......");
        if (!scheduleMonitor.getScheduleServerManager().isMaster(currentUuid, taskServerList)) {
            log.info("当前server:[" + currentUuid + "]: 不是负责任务分配的Leader,直接返回");
            return;
        }
        if (CollectionUtils.isEmpty(taskServerList)) {
            //在服务器动态调整的时候，可能出现服务器列表为空的情况
            log.info("服务器列表为空: 停止分配任务, 等待服务器上线...");
            return;
        }
        try {
            String zkPath = scheduleMonitor.getTaskNode();
            List<String> taskNames = scheduleMonitor.getClient().getChildren().forPath(zkPath);
            if (CollectionUtils.isEmpty(taskNames)) {
                log.info("当前server:[" + currentUuid + "]: 分配结束,没有集群任务");
                return;
            }
            for (String taskName : taskNames) {
                String taskPath = zkPath + "/" + taskName;
                List<String> taskServerIds = scheduleMonitor.getClient().getChildren().forPath(taskPath);
                if (CollectionUtils.isEmpty(taskServerIds)) {
                    // 没有找到目标server信息, 执行分配任务给server节点
                    assignServer2Task(taskServerList, taskPath);
                } else {
                    boolean hasAssignSuccess = false;
                    for (String serverId : taskServerIds) {
                        if (taskServerList.contains(serverId)) {
                            //防止重复分配任务，如果已经成功分配，第二个以后都删除
                            if (hasAssignSuccess) {
                                scheduleMonitor.getClient().delete().deletingChildrenIfNeeded()
                                        .forPath(taskPath + "/" + serverId);
                            } else {
                                hasAssignSuccess = true;
                            }
                        }
                    }
                    if (!hasAssignSuccess) {
                        assignServer2Task(taskServerList, taskPath);
                    }
                }
            }
        } catch (Exception e) {
            log.error("assignTask failed:", e);
        }
    }

    /**
     * 分配指定任务给指定server
     *
     * @param taskName 任务名称
     * @param serverId server节点
     */
    private void assignTask2Server(final String taskName, final String serverId) {
        final String taskPath = scheduleMonitor.getTaskNode() + "/" + taskName;
        try {
            final List<String> taskServerIds = scheduleMonitor.getClient().getChildren().forPath(taskPath);           
            if (!CollectionUtils.isEmpty(taskServerIds)) {
                // 任务已分配, 删除分配信息
                for (String taskServerId : taskServerIds) {
                    scheduleMonitor.getClient().delete().deletingChildrenIfNeeded()
                            .forPath(taskPath + "/" + taskServerId);
                }
            }
            final String runningInfo = "0:" + System.currentTimeMillis();
            final String path = taskPath + "/" + serverId;
            final Stat stat = scheduleMonitor.getClient().checkExists().forPath(path);
            if (stat == null) {
                scheduleMonitor.getClient()
                        .create()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(path, runningInfo.getBytes());
            }

            log.info("成功分配任务 [" + taskPath + "]" + " 给 server [" + serverId + "]");
        } catch (Exception e) {
            log.error("assignTask2Server failed: taskName={}, serverId={}", taskName, serverId, e);
        }
    }

    /**
     * 重新分配任务给server 任务的分配是需要加锁，避免数据分配错误。
     *
     * @param taskServerList 待分配server列表
     * @param taskPath       任务path
     */
    private void assignServer2Task(List<String> taskServerList, String taskPath) {
        synchronized (lock) {
            // 轮询分配给server
            String serverId = serverDispatch.doSelect(taskServerList);
            try {
                if (scheduleMonitor.getClient().checkExists().forPath(taskPath) != null) {
                    final String runningInfo = "0:" + System.currentTimeMillis();
                    final String path = taskPath + "/" + serverId;
                    final Stat stat = scheduleMonitor.getClient().checkExists().forPath(path);
                    if (stat == null) {
                        scheduleMonitor.getClient()
                                .create()
                                .withMode(CreateMode.EPHEMERAL)
                                .forPath(path, runningInfo.getBytes());
                    }
                    log.info("成功分配任务 [" + taskPath + "]" + " 给 server [" + serverId + "]");
                }
            } catch (Exception e) {
                log.error("assign task error", e);
            }
        }
    }
}
