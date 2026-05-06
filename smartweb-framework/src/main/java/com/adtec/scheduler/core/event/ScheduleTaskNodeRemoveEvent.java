package com.adtec.scheduler.core.event;

public class ScheduleTaskNodeRemoveEvent extends BaseScheduleHandleEvent {

    public ScheduleTaskNodeRemoveEvent(String path) {
        super(path);
    }

    public ScheduleTaskNodeRemoveEvent(Object source, String path) {
        super(source, path);
    }
}