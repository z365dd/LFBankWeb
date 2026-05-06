package com.adtec.scheduler.core.event;

/**
 * @author lijunbin
 */
public class ScheduleServerNodeRegisterEvent extends BaseScheduleHandleEvent {

    public ScheduleServerNodeRegisterEvent(String path) {
        super(path);
    }

    public ScheduleServerNodeRegisterEvent(Object source, String path) {
        super(source, path);
    }
}
