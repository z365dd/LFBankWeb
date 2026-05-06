package com.adtec.scheduler.core.event.handler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

/**
 * @author lijunbin
 */
public interface SimpTaskRegister {

    /**
     * 获取自动任务
     *
     * @param beanName /
     * @return /
     */
    ScheduledFuture<?> getTask(String beanName);

    /**
     * 获取所有自动任务
     *
     * @return /
     */
    Set<ScheduledFuture<?>> getTasks();

    /**
     * 获取自动任务数量
     *
     * @return /
     */
    int getTaskCount();

    /**
     * 获取并行自动任务计数
     *
     * @return /
     */
    Map<String, Integer> getParallelNumCountMap();

    /**
     * 获取所有已注册自动任务的处理类名称
     *
     * @return /
     */
    Set<String> getBeanNames();
}
