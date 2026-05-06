package com.adtec.scheduler.core.handle;

/**
 * 调度管理自动任务处理接口，动态加自动任务必须实现本接口
 *
 * @author lijunbin
 */
public interface IJobHandle {

    /**
     * 自动任务执行方法
     */
    void doTask();

}
