package com.adtec.scheduler.core.manager.impl;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.scheduler.core.annotations.JobDescriptor;
import com.adtec.scheduler.core.event.ScheduleExecOnceEvent;
import com.adtec.scheduler.core.event.ScheduleLaunchRegisterEvent;
import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;
import com.adtec.scheduler.core.handle.IJobHandle;
import com.adtec.scheduler.core.manager.ScheduleManager;
import com.adtec.scheduler.core.util.ProxyUtils;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.entity.ScheduleJobDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lijunbin
 */
public class StandaloneScheduleManager implements ScheduleManager, DisposableBean {

    protected final static Logger logger = LoggerFactory.getLogger(StandaloneScheduleManager.class);

    private final ThreadPoolTaskGenerator taskGenerator;

    public StandaloneScheduleManager(ThreadPoolTaskGenerator taskGenerator) {
        this.taskGenerator = taskGenerator;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (ScheduleUtil.checkBootFlag()) {
            System.out.println("异步启动调度管理器开始!!!");
            // 异步注册自动任务
            taskGenerator.execute(new Runnable() {
                @Override
                public void run() {
                    List<ScheduleJobDO> list = ScheduleUtil.queryTasks().or(new ArrayList<ScheduleJobDO>());
                    for (ScheduleJobDO scheduleJobDO : list) {
                        if ("1".equals(scheduleJobDO.getRunSwitchFlg())) {
                            // 启动时执行一次
                            SpringContextHolder.publishEvent(new ScheduleExecOnceEvent(scheduleJobDO));
                        }
                        // 注册自动任务
                        SpringContextHolder.publishEvent(new ScheduleLaunchRegisterEvent(scheduleJobDO));
                    }
                    processJobDescriptorTask();
                    System.out.println("异步启动调度管理器结束!!!");
                }
            });
        }
    }

    private void processJobDescriptorTask() {
        // 获取被 @JobDescriptor 注解的 Bean
        Map<String, Object> beansMap = SpringContextHolder.getBeansWithAnnotation(JobDescriptor.class);
        for (Map.Entry<String, Object> entry : beansMap.entrySet()) {
            Object bean = entry.getValue();
            if (bean instanceof IJobHandle) {
                try {
                    Object target = ProxyUtils.getTarget(bean);
                    ScheduleJobDO scheduleJobDO = new ScheduleJobDO((IJobHandle) target);
                    ScheduleJobDO queryDO = ScheduleUtil.get(scheduleJobDO.getBeanName());
                    if (queryDO == null) {
                        if ("1".equals(scheduleJobDO.getRunSwitchFlg())) {
                            // 启动时执行一次
                            SpringContextHolder.publishEvent(new ScheduleExecOnceEvent(scheduleJobDO));
                        }
                        // 注册自动任务
                        ScheduleUtil.insert(scheduleJobDO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        taskGenerator.destroy();
    }

}
