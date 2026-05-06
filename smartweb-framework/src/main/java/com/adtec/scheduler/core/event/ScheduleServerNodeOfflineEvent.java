package com.adtec.scheduler.core.event;

/**
 * @author lijunbin
 */
public class ScheduleServerNodeOfflineEvent extends BaseScheduleHandleEvent {
    public ScheduleServerNodeOfflineEvent(String path) {
        super(path);
    }

    public ScheduleServerNodeOfflineEvent(Object source, String path) {
        super(source, path);
    }
}
