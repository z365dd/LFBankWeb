package com.adtec.scheduler.core.support;

import com.adtec.framework.common.constant.Constants;
import com.adtec.framework.common.functions.FuncP;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.scheduler.core.handle.IJobHandle;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.entity.ScheduleJobDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对定时任务类采用反射调用
 *7
 * @author lijunbin
 */
public class ScheduleRunnable implements Runnable {

    private static final transient Logger logger = LoggerFactory.getLogger(ScheduleRunnable.class);

    private final Object target;
    private final ScheduleJobDO scheduleJobDO;

    private FuncP<String> successCallback;
    private FuncP<String> failureCallback;

    public ScheduleRunnable(ScheduleJobDO scheduleJobDO, Object target) {
        this.target = target;
        this.scheduleJobDO = scheduleJobDO;
    }

    public ScheduleJobDO getScheduleJobDO() {
        return scheduleJobDO;
    }

    public FuncP<String> getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(FuncP<String> successCallback) {
        this.successCallback = successCallback;
    }

    public FuncP<String> getFailureCallback() {
        return failureCallback;
    }

    public void setFailureCallback(FuncP<String> failureCallback) {
        this.failureCallback = failureCallback;
    }

    @Override
    public void run() {
        if (target instanceof IJobHandle) {
            try {
                String registerBeanName = ScheduleUtil.processBeanName(target.getClass().getSimpleName());
                if (!SpringContextHolder.containsBean(registerBeanName)) {
                    ScheduleUtil.registerBean(target.getClass());
                }
                IJobHandle jobHandle = (IJobHandle) SpringContextHolder.getBean(target.getClass());
                jobHandle.doTask();
                if (successCallback != null) {
                    successCallback.call(String.format("自动任务 %s 执行成功！", scheduleJobDO.getBeanName()));
                }
            } catch (Exception e) {
                if (failureCallback != null) {
                    try {
                        failureCallback.call(String.format("自动任务 %s 执行失败！点击【更多】->【查看日志】按钮查看详情！", scheduleJobDO.getBeanName()));
                    } catch (Exception exception) {
                       logger.error("通信失败");
                    }
                }
                logger.warn("自动任务{}发生异常:{}", scheduleJobDO.getBeanName(), e.getMessage());
                if (Constants.Scheduler.Serial.NO.equals(scheduleJobDO.getPlanExecMeth())) {
                    throw new BaseException(SysErr.E_MESSAGE, "自动任务{}发生异常:{}", scheduleJobDO.getBeanName(), e.getMessage());
                }
            }
        }
    }

}
