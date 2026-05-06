package com.adtec.scheduler.core.event;

/**
 * 自动任务启动事件.
 *
 * @author lijunbin
 */
public class ScheduleLaunchRegisterEvent extends BaseScheduleRegisterEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ScheduleLaunchRegisterEvent(Object source) {
        super(source);
    }
}
