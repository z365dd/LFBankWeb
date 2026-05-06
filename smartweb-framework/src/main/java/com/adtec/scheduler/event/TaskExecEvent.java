package com.adtec.scheduler.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author chenyl
 */
public class TaskExecEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TaskExecEvent(Object source) {
        super(source);
    }
}
