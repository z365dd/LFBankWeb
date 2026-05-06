package com.adtec.sys.modules.sys.utils;

import com.adtec.framework.common.functions.Func;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * @author lijunbin
 */
public class ScheduleCallback implements Serializable {

    private final ScheduledFuture<?> scheduledFuture;

    private final Func cancelCallback;

    public ScheduleCallback(ScheduledFuture<?> scheduledFuture, Func cancelCallback) {
        this.scheduledFuture = scheduledFuture;
        this.cancelCallback = cancelCallback;
    }

    /**
     * 取消任务.
     *
     * @param mayInterruptIfRunning 是否中断正在运行的任务.
     */
    public void cancel(boolean mayInterruptIfRunning) {
        if (Objects.nonNull(scheduledFuture) && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(mayInterruptIfRunning);
            doCancelCallback();
        }
    }

    /**
     * 取消任务时执行的回调方法.
     */
    private void doCancelCallback() {
        try {
            cancelCallback.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public Func getCancelCallback() {
        return cancelCallback;
    }


}
