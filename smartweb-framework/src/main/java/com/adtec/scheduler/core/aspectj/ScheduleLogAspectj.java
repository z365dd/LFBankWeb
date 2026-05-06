package com.adtec.scheduler.core.aspectj;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.dao.ScheduleJobLogDao;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.scheduler.entity.ScheduleJobLogDO;
import com.adtec.sys.common.utils.Exceptions;
import com.adtec.sys.seq.PlatSeq;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.adtec.framework.common.constant.Constants.Scheduler.Serial;

/**
 * 使用 aop 切面记录自动任务日志信息
 *
 * @author lijunbin
 */
@Aspect
@Component
public class ScheduleLogAspectj {

    private static final Logger logger = LoggerFactory.getLogger("com.adtec.schedule.LOG");

    private static final String BEGIN_TIME = "beginTime";
    private static final String SCHEDULE_BEAN = "scheduleBean";

    private final ScheduleJobLogDao scheduleJobLogDao;

    public ScheduleLogAspectj(ScheduleJobLogDao scheduleJobLogDao) {
        this.scheduleJobLogDao = scheduleJobLogDao;
    }

    /**
     * 切入点
     */
    @Pointcut("execution(* com.adtec.scheduler.core.handle.IJobHandle.doTask(..))")
    public void log() {
    }

    /**
     * 前置操作
     *
     * @param point 切入点
     */
    @Before("log()")
    public void beforeLog(JoinPoint point) {
        GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, PlatSeq.getGlobalSeq());
        String className = point.getTarget().getClass().getName();
        ScheduleJobDO scheduleJobDO = ScheduleUtil.get(className);
        GVarContainer.setVar(SCHEDULE_BEAN, scheduleJobDO);
        GVarContainer.setVar(BEGIN_TIME, System.currentTimeMillis());
        if (logger.isTraceEnabled()) {
            logger.trace("自动任务[{}]准备执行", className);
        }
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        if (logger.isTraceEnabled() && null != result) {
            logger.trace("【返回值】：{}", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 后置操作
     */
    @AfterReturning("log()")
    public void afterReturning() {
        ScheduleJobDO scheduleJobDO = (ScheduleJobDO) GVarContainer.getVar(SCHEDULE_BEAN);
        if (null != scheduleJobDO && checkSerial(scheduleJobDO)) {
            saveLog("", "0");
        }

    }

    @AfterThrowing(throwing = "e", pointcut = "log()")
    public void afterThrowing(Throwable e) {
        ScheduleJobDO scheduleJobDO = (ScheduleJobDO) GVarContainer.getVar(SCHEDULE_BEAN);
        if (null != scheduleJobDO && checkSerial(scheduleJobDO)) {
            String stackTraceAsString = Exceptions.getStackTraceAsString(e);
            saveLog(stackTraceAsString, "1");
            logger.error("自动任务执行异常：\n处理类：[{}], \n堆栈信息: {}", scheduleJobDO.getBeanName(), stackTraceAsString);
        }
    }

    private void saveLog(String errorMsg, String status) {
        if (logger.isTraceEnabled()) {
            logger.trace("AOP执行自动任务日志保存开始");
        }
        try {
            ScheduleJobDO scheduleJobDO = (ScheduleJobDO) GVarContainer.getVar(SCHEDULE_BEAN);
            String globalSeq = String.valueOf(GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ));
            long beginTime = Long.parseLong(String.valueOf(GVarContainer.getVar(BEGIN_TIME)));
            long endTime = System.currentTimeMillis();
            if (null != scheduleJobDO) {
                ScheduleJobLogDO scheduleJobLogDO = new ScheduleJobLogDO();
                scheduleJobLogDO.setJobId(scheduleJobDO.getId());
                scheduleJobLogDO.setBeanName(scheduleJobDO.getBeanName());
                scheduleJobLogDO.setPlatSeq(globalSeq);
                scheduleJobLogDO.setStrTime(DateUtil.Date2String(new Date(beginTime), DateUtil.DEF_FMT));
                scheduleJobLogDO.setEndTime(DateUtil.Date2String(new Date(endTime), DateUtil.DEF_FMT));
                scheduleJobLogDO.setSuccSwitchFlg(status);
                scheduleJobLogDO.setIp(ParamUtil.getLocalAddr());
                scheduleJobLogDO.setPort(ParamUtil.getLocalPort());
                scheduleJobLogDO.setTranTime(Long.toString(endTime - beginTime));
                scheduleJobLogDO.setErrMsgByteData(errorMsg.getBytes(StandardCharsets.UTF_8));
                scheduleJobLogDO.preInsert();
                scheduleJobLogDao.insertLog(scheduleJobLogDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (logger.isTraceEnabled()) {
                logger.trace("AOP执行自动任务日志保存结束");
            }
            // 关闭当前线程的DBSession的数据库连接
            DBSessionFactory.clear();
            // 清理异常信息线程池
            BaseException.clearErrInfo();
            // 清理线程全局变量
            GVarContainer.clearVar();
        }
    }

    private boolean checkSerial(ScheduleJobDO scheduleJobDO) {
        return Serial.YES.equals(scheduleJobDO.getPlanExecMeth());
    }

}
