package com.adtec.scheduler.core.manager.impl;

import com.adtec.framework.common.util.JsonUtil;
import com.adtec.scheduler.config.SchedulerProperties;
import com.adtec.scheduler.core.manager.ScheduleJobManager;
import com.adtec.scheduler.core.model.ScheduleJobZkDTO;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 任务节点管理的 zookeeper实现
 *
 * @author lijunbin
 */
public class ScheduleJobZkManager implements ScheduleJobManager<CuratorFramework> {

    private static final transient Logger log = LoggerFactory.getLogger(ScheduleJobZkManager.class);

    private static final String TASK_NODE = "task";

    private final String jobNode;
    private CuratorFramework client;

    public ScheduleJobZkManager(SchedulerProperties schedulerProperties) {
        String projectNode = schedulerProperties.getScheduleProjectNode();
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
    public boolean isRunning(String beanName) {
        return false;
    }

    @Override
    public void save(ScheduleJobZkDTO scheduleJobZkDTO) {
        try {
            String zkPath = this.jobNode;
            zkPath = zkPath + "/" + scheduleJobZkDTO.getBeanName();
            String json = JsonUtil.obj2String(scheduleJobZkDTO);
            if (this.client.checkExists().forPath(zkPath) == null) {
                this.client.create().withMode(CreateMode.PERSISTENT).forPath(zkPath, json.getBytes());
            }
        } catch (Exception e) {
            log.error("addTask failed:", e);
        }
    }

    @Override
    public void update(ScheduleJobZkDTO scheduleJobZkDTO) {

    }

    @Override
    public void delete(ScheduleJobZkDTO scheduleJobZkDTO) {
        try {
            if (this.client.checkExists().forPath(this.jobNode) != null) {
                String zkPath = this.jobNode + "/" + scheduleJobZkDTO.getBeanName();
                if (this.client.checkExists().forPath(zkPath) != null) {
                    log.info("删除任务: {}", zkPath);
                    this.client.delete().deletingChildrenIfNeeded().forPath(zkPath);
                }
            }
        } catch (Exception e) {
            log.error("delTask failed:", e);
        }
    }

    @Override
    public List<ScheduleJobZkDTO> queryTasks() {
        return null;
    }

    @Override
    public boolean isExist(ScheduleJobZkDTO scheduleJobZkDTO) {
        String zkPath = this.jobNode + "/" + scheduleJobZkDTO.getBeanName();
        if (this.client == null) {
            return true;
        }
        try {
            return this.client.checkExists().forPath(zkPath) != null;
        } catch (Exception e) {
            log.error("zookeeper error", e);
        }
        return false;
    }

    @Override
    public ScheduleJobZkDTO queryTask(ScheduleJobZkDTO scheduleJobZkDTO) {
        return null;
    }

}
