package com.adtec.scheduler.core.event;


/**
 * 执行一次事件
 *
 * @author lijb
 */
public class ScheduleExecOnceEvent extends BaseScheduleRegisterEvent {


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ScheduleExecOnceEvent(Object source) {
        super(source);
    }
}
