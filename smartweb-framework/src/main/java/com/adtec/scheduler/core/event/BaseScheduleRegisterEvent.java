package com.adtec.scheduler.core.event;

import com.adtec.scheduler.entity.ScheduleJobDO;
import org.springframework.context.ApplicationEvent;

/**
 * @author lijb
 */
public abstract class BaseScheduleRegisterEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BaseScheduleRegisterEvent(Object source) {
        super(source);
    }

    public ScheduleJobDO getScheduleJobDO() {
        return (ScheduleJobDO) source;
    }

}
