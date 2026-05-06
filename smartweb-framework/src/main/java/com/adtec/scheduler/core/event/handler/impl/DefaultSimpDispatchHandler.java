package com.adtec.scheduler.core.event.handler.impl;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.scheduler.core.event.*;
import com.adtec.scheduler.core.event.handler.SimpDispatchHandler;
import com.adtec.scheduler.core.support.DefaultScheduleNodeChangeHandler;
import com.adtec.scheduler.core.support.ScheduleNodeChangeHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

/**
 * @author lijunbin
 */
public class DefaultSimpDispatchHandler implements SimpDispatchHandler, SmartApplicationListener {

    private final ScheduleNodeChangeHandler scheduleNodeChangeHandler;

    public DefaultSimpDispatchHandler() {
        this.scheduleNodeChangeHandler = SpringContextHolder.getBean(DefaultScheduleNodeChangeHandler.class);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return BaseScheduleHandleEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        BaseScheduleHandleEvent scheduleEvent = (BaseScheduleHandleEvent) event;
        String path = scheduleEvent.getPath();
        if (event instanceof ScheduleServerNodeRegisterEvent) {
            // 服务节点注册，触发重新分配任务
            if (null != path) {
                scheduleNodeChangeHandler.handleServerNodeRegisterEvent(path);
            }
        } else if (event instanceof ScheduleServerNodeOfflineEvent) {
            // 服务节点离线，触发重新分配任务
            if (null != path) {
                scheduleNodeChangeHandler.handleServerNodeOfflineEvent(path);
            }
        } else if (event instanceof ScheduleTaskNodeRegisterEvent) {
            // 任务节点注册，触发重新分配任务
            if (null != path) {
                scheduleNodeChangeHandler.handleTaskNodeRegisterEvent(path);
            }
        } else if (event instanceof ScheduleTaskNodeRemoveEvent) {
            // 任务节点移除，触发重新分配任务
            if (null != path) {
                scheduleNodeChangeHandler.handleTaskNodeRemoveEvent(path);
            }
        }
    }

}
