package com.adtec.scheduler.event;

import org.springframework.context.ApplicationEvent;

/**
 * 任务池变动事件，新增、修改、删除、启动、停止任务时触发事件
 *
 * @author chenyl
 */
public class TaskPoolChangeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 4445267270378660677L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TaskPoolChangeEvent(Object source) {
        super(source);
    }
}
