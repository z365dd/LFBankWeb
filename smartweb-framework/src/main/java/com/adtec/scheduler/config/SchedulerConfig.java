package com.adtec.scheduler.config;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.scheduler.core.event.handler.SimpTaskRegister;
import com.adtec.scheduler.core.event.handler.impl.DefaultSimpDispatchHandler;
import com.adtec.scheduler.core.event.handler.impl.DefaultSimpTaskRegister;
import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;
import com.adtec.scheduler.core.manager.ScheduleManager;
import com.adtec.scheduler.core.manager.impl.DistributedScheduleManager;
import com.adtec.scheduler.core.manager.impl.StandaloneScheduleManager;
import com.adtec.scheduler.core.monitor.ScheduleMonitor;
import com.adtec.scheduler.core.rules.balance.ServerBalanceDispatch;
import com.adtec.scheduler.core.rules.balance.impl.PollingServerBalanceDispatch;
import com.adtec.scheduler.core.support.DefaultScheduleNodeChangeHandler;
import com.adtec.scheduler.core.support.ScheduleNodeChangeHandler;
import com.adtec.scheduler.core.util.ScheduleUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lijunbin
 */
@Configuration
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class SchedulerConfig {

    @Bean
    public SchedulerProperties schedulerProperties() {
        return new SchedulerProperties();
    }

    /**
     * 定义定时任务生成器
     */
    @Bean("smTaskScheduler")
    public ThreadPoolTaskGenerator smTaskScheduler() {
        SchedulerProperties schedulerProperties = schedulerProperties();
        ThreadPoolTaskGenerator threadPoolTaskGenerator = new ThreadPoolTaskGenerator(
                Integer.parseInt(schedulerProperties.getSchedulePoolSize()));
        threadPoolTaskGenerator.setThreadNamePrefix("scheduler-pool-");
        threadPoolTaskGenerator.setRemoveOnCancelPolicy(true);
        return threadPoolTaskGenerator;
    }

    @Bean
    public ScheduleMonitor scheduleMonitor(SchedulerProperties schedulerProperties) {
        return new ScheduleMonitor(schedulerProperties);
    }

    @Bean("serverDispatch")
    public ServerBalanceDispatch serverDispatch() {
        return new PollingServerBalanceDispatch();
    }

    @Bean
    public ScheduleNodeChangeHandler scheduleHandler() {
        return new DefaultScheduleNodeChangeHandler();
    }

    @Bean
    public SimpTaskRegister taskRegister(ThreadPoolTaskGenerator smTaskScheduler) {
        return new DefaultSimpTaskRegister(smTaskScheduler);
    }

    @Bean
    public DefaultSimpDispatchHandler dispatchHandler() {
        return new DefaultSimpDispatchHandler();
    }

    @Bean
    public ScheduleManager scheduleManager(SchedulerProperties schedulerProperties, ThreadPoolTaskGenerator smTaskScheduler) {
        if (ScheduleUtil.checkClusterFlag()) {
            ScheduleMonitor scheduleMonitor = SpringContextHolder.getBean(ScheduleMonitor.class);
            return new DistributedScheduleManager(schedulerProperties, scheduleMonitor, smTaskScheduler);
        }
        return new StandaloneScheduleManager(smTaskScheduler);
    }
}
