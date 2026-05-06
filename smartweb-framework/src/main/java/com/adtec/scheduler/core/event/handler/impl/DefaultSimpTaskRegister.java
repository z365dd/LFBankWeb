package com.adtec.scheduler.core.event.handler.impl;

import com.adtec.framework.common.functions.FuncP;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.scheduler.core.event.*;
import com.adtec.scheduler.core.event.handler.SimpTaskRegister;
import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;
import com.adtec.scheduler.core.handle.IJobHandle;
import com.adtec.scheduler.core.support.ScheduleParallelRunnable;
import com.adtec.scheduler.core.support.ScheduleRunnable;
import com.adtec.scheduler.core.util.CronUtil;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.sys.modules.sys.utils.NotificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledFuture;

import static com.adtec.framework.common.constant.Constants.Scheduler.*;

/**
 * @author lijunbin
 */
public class DefaultSimpTaskRegister implements SimpTaskRegister, SmartApplicationListener {

    private static final transient Logger logger = LoggerFactory.getLogger(DefaultSimpTaskRegister.class);
    /**
     * 缓存 scheduleKey 与 ScheduledFuture, 删除任务时可以优雅的关闭任务
     */
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();

    private final Map<String, ScheduledFuture<?>> failureTimeFutureMap = new ConcurrentHashMap<>();

    protected final Map<String, Integer> parallelNumCountMap = new ConcurrentHashMap<>();

    private final Set<String> notFoundClasses = new CopyOnWriteArraySet<>();

    private final ThreadPoolTaskGenerator taskGenerator;

    private final Object lock = new Object();

    public DefaultSimpTaskRegister(ThreadPoolTaskGenerator taskGenerator) {
        this.taskGenerator = taskGenerator;
    }


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return BaseScheduleRegisterEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        BaseScheduleRegisterEvent scheduleEvent = (BaseScheduleRegisterEvent) event;
        ScheduleJobDO scheduleJobDO = scheduleEvent.getScheduleJobDO();
        String beanName = scheduleJobDO.getBeanName();
        if (event instanceof ScheduleLaunchRegisterEvent) {
            synchronized (lock) {
                if (!scheduledFutureMap.containsKey(beanName) && !notFoundClasses.contains(beanName)) {
                    scheduleCronTask(scheduleJobDO);
                    setFailureTime(scheduleJobDO);
                }
            }
        } else if (event instanceof ScheduleStopRegisterEvent) {
            synchronized (lock) {
                cancelTask(beanName);
                setFailureTime(scheduleJobDO);
            }
        } else if (event instanceof ScheduleChangeEvent) {
            setFailureTime(scheduleJobDO);
            switch (scheduleJobDO.getListenerType()) {
                case INSERT:
                case LAUNCH:
                    if (scheduleJobDO.statusRunning()) {
                        scheduleCronTask(scheduleJobDO);
                    }
                    break;
                case UPDATE:
                    cancelTask(beanName);
                    if (scheduleJobDO.statusRunning()) {
                        scheduleCronTask(scheduleJobDO);
                    }
                    break;
                case DELETE:
                case STOP:
                    if (!scheduleJobDO.statusRunning()) {
                        cancelTask(beanName);
                    }
                    break;
                default:
                    break;
            }
        } else if (event instanceof ScheduleExecOnceEvent) {
            ScheduleRunnable scheduleRunnable = buildScheduleRunnable(scheduleJobDO);
            if (scheduleRunnable != null) {
                final String title = "自动任务执行结果";
                final String operUsername = scheduleJobDO.getOperUsername();
                if (!DataUtil.isNullStr(operUsername)) {
                    final String runningMsg = String.format("自动任务 %s 正在执行，请耐心等待执行结果！", scheduleJobDO.getBeanName());
                    NotificationUtil.notifyUser(operUsername, "执行自动任务", runningMsg);
                    final FuncP<String> callback = new FuncP<String>() {
                        @Override
                        public void call(String message) throws Exception {
                            NotificationUtil.notifyUser(operUsername, title, message);
                        }
                    };
                    scheduleRunnable.setSuccessCallback(callback);
                    scheduleRunnable.setFailureCallback(callback);
                }
                taskGenerator.execute(scheduleRunnable);
            }
        }
    }

    @Override
    public ScheduledFuture<?> getTask(String beanName) {
        return this.scheduledFutureMap.get(beanName);
    }

    @Override
    public Set<ScheduledFuture<?>> getTasks() {
        Set<ScheduledFuture<?>> scheduledFutures = new HashSet<>();
        for (Map.Entry<String, ScheduledFuture<?>> entry : this.scheduledFutureMap.entrySet()) {
            scheduledFutures.add(entry.getValue());
        }
        return scheduledFutures;
    }

    @Override
    public int getTaskCount() {
        return this.scheduledFutureMap.size();
    }

    @Override
    public Map<String, Integer> getParallelNumCountMap() {
        return parallelNumCountMap;
    }

    @Override
    public Set<String> getBeanNames() {
        return scheduledFutureMap.keySet();
    }

    public void scheduleCronTask(ScheduleJobDO scheduleJobDO) {
        logger.info("开始启动自动任务: {}", scheduleJobDO.getBeanName());
        if (!scheduledFutureMap.containsKey(scheduleJobDO.getBeanName())) {
            ScheduleRunnable scheduleRunnable = buildScheduleRunnable(scheduleJobDO);
            if (null != scheduleRunnable) {
                ScheduledFuture<?> scheduledFuture;
                // 生成 cron表达式
                String cronExpr = DataUtil.isNullStr(scheduleJobDO.getCronExpr())
                        ? CronUtil.generateCron(scheduleJobDO)
                        : scheduleJobDO.getCronExpr();
                Trigger trigger = new CronTrigger(cronExpr);
                if (Serial.YES.equals(scheduleJobDO.getPlanExecMeth())) {
                    // 串行
                    scheduledFuture = taskGenerator.schedule(scheduleRunnable, trigger);
                } else {
                    // 并行
                    parallelNumCountMap.put(scheduleJobDO.getBeanName(), 0);
                    ScheduleParallelRunnable runnable = new ScheduleParallelRunnable(scheduleRunnable, parallelNumCountMap);
                    scheduledFuture = taskGenerator.schedule(runnable, trigger);
                }
                if (null != scheduledFuture) {
                    scheduledFutureMap.put(scheduleJobDO.getBeanName(), scheduledFuture);
                }
            }
        }
        logger.info("自动任务 {} 启动成功", scheduleJobDO.getBeanName());
    }

    protected ScheduleRunnable buildScheduleRunnable(ScheduleJobDO scheduleJobDO) {
        String errorMsg;
        try {
            Object bean = Class.forName(scheduleJobDO.getBeanName()).newInstance();
            if (bean instanceof IJobHandle) {
                return new ScheduleRunnable(scheduleJobDO, bean);
            }
            errorMsg = "自动任务处理类必须实现com.adtec.scheduler.core.handle.IJobHandle接口";
        } catch (IllegalAccessException e) {
            errorMsg = "构造方法权限为private，无权限实例化！" + e.getMessage();
        } catch (InstantiationException e) {
            errorMsg = "实例化对象失败！" + e.getMessage();
        } catch (ClassNotFoundException e) {
            errorMsg = "找不到该类！";
            notFoundClasses.add(scheduleJobDO.getBeanName());
        }
        logger.error("启动自动任务失败: {}, 失败原因: {}", scheduleJobDO.getBeanName(), errorMsg);
        return null;
    }

    /**
     * 设置任务失效时间，到达失效时间点，自动停止任务.
     *
     * @param scheduleJobDO 自动任务信息.
     */
    private void setFailureTime(final ScheduleJobDO scheduleJobDO) {
        ScheduledFuture<?> scheduledFuture = failureTimeFutureMap.get(scheduleJobDO.getBeanName());
        if (null != scheduledFuture && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            failureTimeFutureMap.remove(scheduleJobDO.getBeanName());
        }
        if (scheduleJobDO.statusRunning()) {
            scheduledFuture = taskGenerator.schedule(new Runnable() {
                @Override
                public void run() {
                    cancelTask(scheduleJobDO.getBeanName());
                }
            }, DateUtil.string2Date(scheduleJobDO.getInvlTime(), CronUtil.TRANS_DATE_FMT));
            if (null != scheduledFuture) {
                failureTimeFutureMap.put(scheduleJobDO.getBeanName(), scheduledFuture);
            }
        }
    }

    /**
     * 取消自动任务.
     *
     * @param beanName 自动任务处理类类名.
     */
    private void cancelTask(String beanName) {
        ScheduledFuture<?> scheduledFuture = this.scheduledFutureMap.get(beanName);
        if (null != scheduledFuture && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
        this.scheduledFutureMap.remove(beanName);
    }

}
