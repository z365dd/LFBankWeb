package com.adtec.scheduler.core.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务生成器 继承 {@link ThreadPoolTaskScheduler}, Spring Task 定时任务的默认实现
 *
 * @author lijunbin
 */
public class ThreadPoolTaskGenerator extends ThreadPoolTaskScheduler {

    private static final transient Logger logger = LoggerFactory.getLogger(ThreadPoolTaskGenerator.class);

    private static final long serialVersionUID = 8048640374020873814L;

    public ThreadPoolTaskGenerator(int poolSize) {
        this.setPoolSize(poolSize);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return super.schedule(task, trigger);
    }

}
