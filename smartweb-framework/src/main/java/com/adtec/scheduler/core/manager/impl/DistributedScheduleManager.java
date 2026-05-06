package com.adtec.scheduler.core.manager.impl;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.scheduler.config.SchedulerProperties;
import com.adtec.scheduler.core.event.ScheduleExecOnceEvent;
import com.adtec.scheduler.core.event.ScheduleLaunchRegisterEvent;
import com.adtec.scheduler.core.event.ScheduleStopRegisterEvent;
import com.adtec.scheduler.core.event.handler.SimpTaskRegister;
import com.adtec.scheduler.core.event.handler.impl.DefaultSimpTaskRegister;
import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;
import com.adtec.scheduler.core.manager.ScheduleManager;
import com.adtec.scheduler.core.model.ScheduleJobZkDTO;
import com.adtec.scheduler.core.model.ScheduleServerZkDTO;
import com.adtec.scheduler.core.monitor.ScheduleMonitor;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.entity.ScheduleJobDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分布式调度管理器.
 *
 * @author lijunbin
 */
public class DistributedScheduleManager implements ScheduleManager, DisposableBean {

    private static final transient Logger log = LoggerFactory.getLogger(DistributedScheduleManager.class);

    private final SchedulerProperties schedulerProperties;
    private final ScheduleMonitor scheduleMonitor;

    private final SimpTaskRegister simpTaskRegister;

    private final ThreadPoolTaskGenerator taskGenerator;

    public DistributedScheduleManager(SchedulerProperties schedulerProperties, ScheduleMonitor scheduleMonitor, ThreadPoolTaskGenerator taskGenerator) {
        this.schedulerProperties = schedulerProperties;
        this.scheduleMonitor = scheduleMonitor;
        this.taskGenerator = taskGenerator;
        this.simpTaskRegister = SpringContextHolder.getBean(DefaultSimpTaskRegister.class);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (ScheduleUtil.checkBootFlag()) {
            // 一分钟后才启动，等待其它资源初始化
            taskGenerator.schedule(new Runnable() {
                @Override
                public void run() {
                    List<ScheduleJobDO> list = ScheduleUtil.queryTasks().or(new ArrayList<ScheduleJobDO>());
                    for (ScheduleJobDO scheduleJobDO : list) {
                        if ("1".equals(scheduleJobDO.getRunSwitchFlg())) {
                            // 启动时执行一次
                            SpringContextHolder.publishEvent(new ScheduleExecOnceEvent(scheduleJobDO));
                        }
                        ScheduleJobZkDTO scheduleJobZkDTO = new ScheduleJobZkDTO();
                        scheduleJobZkDTO.setBeanName(scheduleJobDO.getBeanName());
                        scheduleMonitor.getScheduleJobManager().save(scheduleJobZkDTO);
                    }
                }
            }, new Date(System.currentTimeMillis() + 6000));
            this.checkLocalTask();
        }
    }

    /**
     * 定时检查/执行 本地任务
     * 1. 清理过时的本地任务(zk上已经删除的)
     * 2. 添加执行分配给自己的任务
     */
    public void checkLocalTask() {
        taskGenerator.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                checkLocalTask(ScheduleServerZkDTO.getInstance().getUuid());
            }
        }, schedulerProperties.getRefreshTaskInterval() * 1000L);
    }

    /**
     * 检查本地的定时任务，添加调度器；这是动态添加的任务的真正开始执行的地方
     * 如果有的话启动该定时任务；这是一种自定义的定时任务类型，任务的启动方式也是自定义的，主要方法在类 TaskManager 中；
     *
     * @param currentUuid 当前服务器唯一标识
     */
    public void checkLocalTask(String currentUuid) {
        try {
            String zkPath = scheduleMonitor.getTaskNode();
            List<String> taskNames = scheduleMonitor.getClient().getChildren().forPath(zkPath);
            if (CollectionUtils.isEmpty(taskNames)) {
                log.info("当前server:[" + currentUuid + "]: 检查本地任务结束, 任务列表为空");
                clearLocalTask(taskNames);
                return;
            }
            List<String> localTasks = new ArrayList<>();
            for (String beanName : taskNames) {
                if (scheduleMonitor.getScheduleServerManager().isOwner(beanName, currentUuid)) {
                    String taskPath = zkPath + "/" + beanName;
                    byte[] data = scheduleMonitor.getClient().getData().forPath(taskPath);
                    if (null != data) {
                        localTasks.add(beanName);
                        ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
                        if (null != scheduleJobDO) {
                            // 启动任务
                            SpringContextHolder.publishEvent(new ScheduleLaunchRegisterEvent(scheduleJobDO));
                        }
                    }
                }
            }
            clearLocalTask(localTasks);
        } catch (Exception e) {
            log.error("checkLocalTask failed", e);
        }
    }

    /**
     * 清理本地任务
     *
     * @param existBeanName 与本地缓存的任务列表 两者进行比对
     *                      如果远程没有了,本地还有,就要清除本地数据
     *                      并且停止本地任务cancel(true)
     */
    public void clearLocalTask(List<String> existBeanName) {
        for (String beanName : simpTaskRegister.getBeanNames()) {
            if (!existBeanName.contains(beanName)) {
                ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
                SpringContextHolder.publishEvent(new ScheduleStopRegisterEvent(scheduleJobDO));
                log.info("清理本地任务: " + beanName);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        taskGenerator.destroy();
    }
}
