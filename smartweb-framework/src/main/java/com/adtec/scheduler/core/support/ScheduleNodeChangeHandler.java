package com.adtec.scheduler.core.support;

import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;

/**
 * @author lijunbin
 */
public interface ScheduleNodeChangeHandler {

    /**
     * 获取自动任务生成器.
     *
     * @return ThreadPoolTaskGenerator
     */
    ThreadPoolTaskGenerator getTaskGenerator();

    /**
     * 处理服务节点注册事件
     *
     * @param path 节点路径
     */
    void handleServerNodeRegisterEvent(String path);

    /**
     * 处理服务节点离线事件.
     *
     * @param path 节点路径
     */
    void handleServerNodeOfflineEvent(String path);

    /**
     * 处理任务节点离线事件.
     *
     * @param path 节点路径
     */
    void handleTaskNodeRegisterEvent(String path);

    /**
     * 处理任务节点离线事件.
     *
     * @param path 节点路径
     */
    void handleTaskNodeRemoveEvent(String path);
}
