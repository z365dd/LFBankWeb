package com.adtec.scheduler.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author lijb
 */
public class BaseScheduleHandleEvent extends ApplicationEvent {

    private final String path;

    public BaseScheduleHandleEvent(String path) {
        this(path, path);
    }

    public BaseScheduleHandleEvent(Object source, String path) {
        super(source);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
