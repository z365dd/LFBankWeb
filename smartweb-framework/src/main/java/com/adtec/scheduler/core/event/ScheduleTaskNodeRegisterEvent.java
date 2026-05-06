package com.adtec.scheduler.core.event;

/**
 * @author lijunbin
 */
public class ScheduleTaskNodeRegisterEvent extends BaseScheduleHandleEvent {

    public ScheduleTaskNodeRegisterEvent(String path) {
        super(path);
    }

    public ScheduleTaskNodeRegisterEvent(Object source, String path) {
        super(source, path);
    }
}
