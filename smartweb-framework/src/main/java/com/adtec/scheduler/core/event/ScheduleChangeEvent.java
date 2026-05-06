package com.adtec.scheduler.core.event;

public class ScheduleChangeEvent extends BaseScheduleRegisterEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ScheduleChangeEvent(Object source) {
        super(source);
    }
}
