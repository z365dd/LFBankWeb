package com.adtec.scheduler.core.support;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.scheduler.core.generator.ThreadPoolTaskGenerator;
import com.adtec.scheduler.dao.ScheduleJobLogDao;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.scheduler.entity.ScheduleJobLogDO;
import com.adtec.sys.common.utils.Exceptions;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @author lijunbin
 */
public class ScheduleParallelRunnable implements Runnable {

    private final ScheduleRunnable scheduleRunnable;
    private final ScheduleJobDO scheduleJobDO;
    private final ThreadPoolTaskGenerator taskGenerator;
    private final Map<String, Integer> parallelNumCountMap;

    public ScheduleParallelRunnable(ScheduleRunnable scheduleRunnable, Map<String, Integer> parallelNumCountMap) {
        this.scheduleRunnable = scheduleRunnable;
        this.taskGenerator = SpringContextHolder.getBean(ThreadPoolTaskGenerator.class);
        this.scheduleJobDO = scheduleRunnable.getScheduleJobDO();
        this.parallelNumCountMap = parallelNumCountMap;
    }

    @Override
    public void run() {
        int count = parallelNumCountMap.get(scheduleJobDO.getBeanName());
        int parallelNum = Integer.parseInt(null != scheduleJobDO.getNumKv() ? scheduleJobDO.getNumKv() : "1");
        if (count <= parallelNum) {
            executeTask();
        }
    }

    private void executeTask() {
        taskGenerator.execute((new Runnable() {
            @Override
            public void run() {
                try {
                    ScheduleJobLogDao scheduleJobLogDao = SpringContextHolder.getBean(ScheduleJobLogDao.class);
                    long beginTime = System.currentTimeMillis();
                    String status = "0";
                    ScheduleJobLogDO scheduleJobLogDO = new ScheduleJobLogDO();
                    scheduleJobLogDO.setJobId(scheduleJobDO.getId());
                    scheduleJobLogDO.setBeanName(scheduleJobDO.getBeanName());
                    scheduleJobLogDO.setStrTime(DateUtil.Date2String(new Date(beginTime), DateUtil.DEF_FMT));
                    scheduleJobLogDO.setIp(ParamUtil.getLocalAddr());
                    scheduleJobLogDO.setPort(ParamUtil.getLocalPort());
                    try {
                        scheduleRunnable.run();
                        int count = parallelNumCountMap.get(scheduleJobDO.getBeanName());
                        parallelNumCountMap.put(scheduleJobDO.getBeanName(), count + 1);
                    } catch (Exception e) {
                        status = "1";
                        scheduleJobLogDO.setErrMsgByteData(Exceptions.getStackTraceAsString(e).getBytes(StandardCharsets.UTF_8));
                    } finally {
                        String globalSeq = String.valueOf(GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ));
                        if (null != GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ)) {
                            GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, globalSeq);
                        }
                        scheduleJobLogDO.setPlatSeq(String.valueOf(GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ)));
                        scheduleJobLogDO.setSuccSwitchFlg(status);
                        long endTime = System.currentTimeMillis();
                        scheduleJobLogDO.setEndTime(DateUtil.Date2String(new Date(endTime), DateUtil.DEF_FMT));
                        scheduleJobLogDO.setTranTime(Long.toString(endTime - beginTime));
                        scheduleJobLogDO.preInsert();
                        scheduleJobLogDao.insertLog(scheduleJobLogDO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 关闭当前线程的DBSession的数据库连接
                    DBSessionFactory.clear();
                    // 清理异常信息线程池
                    BaseException.clearErrInfo();
                    // 清理线程全局变量
                    GVarContainer.clearVar();
                }
            }
        }));
    }
}
