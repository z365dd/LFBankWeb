package com.adtec.scheduler.core.manager.impl;

import com.adtec.framework.common.util.JsonUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.scheduler.config.SchedulerProperties;
import com.adtec.scheduler.core.manager.ScheduleServerManager;
import com.adtec.scheduler.core.model.ScheduleServerZkDTO;
import com.adtec.sys.common.utils.IdGen;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author lijunbin
 */
public class ScheduleServerZkManager implements ScheduleServerManager<CuratorFramework> {

    private static final transient Logger log = LoggerFactory.getLogger(ScheduleJobZkManager.class);

    private static final String SERVER_NODE = "server";
    private static final String TASK_NODE = "task";

    private final String serverNode;
    private final String jobNode;
    private CuratorFramework client;

    public ScheduleServerZkManager(SchedulerProperties schedulerProperties) {
        String projectNode = schedulerProperties.getScheduleProjectNode();
        this.serverNode = projectNode + "/" + SERVER_NODE;
        this.jobNode = projectNode + "/" + TASK_NODE;
    }

    @Override
    public CuratorFramework getClient() {
        return this.client;
    }

    @Override
    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public boolean isOwner(String beanName, String serverUuid) {
        boolean isOwner = false;
        //查看集群中是否注册当前任务，如果没有就自动注册
        String zkPath = this.jobNode + "/" + beanName;
        //判断是否分配给当前节点
        try {
            if (client.checkExists().forPath(zkPath + "/" + serverUuid) != null) {
                isOwner = true;
            }
        } catch (Exception e) {
            log.error("isOwner assert error", e);
        }
        return isOwner;
    }

    @Override
    public void registerServer(ScheduleServerZkDTO server) {
        try {
            if (server.isRegister()) {
                log.warn(server.getUuid() + " 被重复注册");
                return;
            }
            String realPath;
            //此处必须增加UUID作为唯一性保障
            final String id = ParamUtil.getProjectName() + "@" + server.getIp() + "$" + IdGen.uuid().toUpperCase();
            final String zkServerPath = serverNode + "/" + id + "$";
            // 临时顺序节点
            realPath = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(zkServerPath);
            server.setUuid(realPath.substring(realPath.lastIndexOf("/") + 1));
            server.setRegister(true);
            String valueString = JsonUtil.obj2String(server);
            client.setData().forPath(realPath, valueString.getBytes());
            log.info("注册server成功: {}", server.getUuid());
        } catch (Exception e) {
            log.error("registerScheduleServer failed:", e);
        }
    }

    @Override
    public boolean isMaster(String serverUuid, List<String> servers) {
        return serverUuid.equals(getLeader(servers));
    }

    @Override
    public List<String> getServers() {
        List<String> servers = new ArrayList<>(1);
        try {
            if (client.checkExists().forPath(serverNode) == null) {
                return Collections.emptyList();
            }
            servers = client.getChildren().forPath(serverNode);
            Collections.sort(servers, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (o1 == null && o2 == null) {
                        return 0;
                    } else if (o1 == null && o2 != null) {
                        return -1;
                    } else if (o1 != null && o2 == null) {
                        return 1;
                    }
                    int o1Num = Integer.parseInt(o1.substring(o1.lastIndexOf("$") + 1));
                    int o2Num = Integer.parseInt(o2.substring(o2.lastIndexOf("$") + 1));
                    return o1Num - o2Num;
                }
            });
        } catch (Exception e) {
            log.error("loadScheduleServerNames failed", e);
        }
        return servers;
    }

    /**
     * 取serverCode最小的服务器为leader。这种方法的好处是，
     * 由于serverCode是递增的，再新增服务器的时候，leader节点不会变化，比较稳定，算法又简单。
     */
    private String getLeader(List<String> servers) {
        if (CollectionUtils.isEmpty(servers)) {
            return "";
        }
        long no = Long.MAX_VALUE;
        long tmpNo = -1;
        String leader = null;
        for (String server : servers) {
            tmpNo = Long.parseLong(server.substring(server.lastIndexOf("$") + 1));
            if (no > tmpNo) {
                no = tmpNo;
                leader = server;
            }
        }
        return leader;
    }
}
