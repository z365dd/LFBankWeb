package com.adtec.scheduler.core.manager;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 调度管理接口.
 *
 * @author lijunbin
 */
public interface ScheduleManager extends ApplicationListener<ContextRefreshedEvent> {

}

