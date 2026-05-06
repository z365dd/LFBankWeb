package com.adtec.scheduler.core.event;

/**
 * 自动任务停止事件.
 *
 * @author lijunbin
 */
public class ScheduleStopRegisterEvent extends BaseScheduleRegisterEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ScheduleStopRegisterEvent(Object source) {
        super(source);
    }
}
