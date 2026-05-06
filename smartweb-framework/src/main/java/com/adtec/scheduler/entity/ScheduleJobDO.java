package com.adtec.scheduler.entity;
/*****
 * 系统名称: SmartWeb平台
 * 模块名称:自动任务表实体类
 * 类  名  称: ScheduleJobDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2019-07-31 21:56:42
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */

import com.adtec.framework.common.constant.Constants;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.scheduler.core.annotations.JobDescriptor;
import com.adtec.scheduler.core.handle.IJobHandle;
import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

import static com.adtec.framework.common.constant.Constants.Scheduler.Status;

public class ScheduleJobDO extends BaseDO {

    private static final long serialVersionUID = -1L;
    /**
     * cron表达式 CRON_EXPR
     */
    private String cronExpr;
    /**
     * 类名 BEAN_NAME
     */
    private String beanName;
    /**
     * 中文名称 NAME
     */
    private String name;
    /**
     * 生效时间 EFFT_TIME
     */
    private String efftTime;
    /**
     * 失效时间 INVL_TIME
     */
    private String invlTime;
    /**
     * 任务状态 STAT
     */
    private String openSwitchFlg;
    /**
     * 串行/并行 PLAN_EXEC_METH
     */
    private String planExecMeth;
    /**
     * 并行数 NUM_KV
     */
    private String numKv;
    /**
     * 任务执行时间单位 UNIT
     */
    private String timeUnitTp;
    /**
     * 时间间隔 INTVL_TIME
     */
    private String intvlTime;
    /**
     * 开始时间 STR_TIME
     */
    private String strTime;
    /**
     * 结束时间 END_TIME
     */
    private String endTime;
    /**
     * 执行日期选择方式 PLAN_EXEC_TP
     */
    private String planExecTp;
    /**
     * 执行日期 PROC_DATE_PARA_VAL
     */
    private String procDateParaVal;
    /**
     * 启动执行标志 RUN_SWITCH_FLG
     */
    private String runSwitchFlg;

    private String action;

    private String listenerType;
    /**
     * 上一次执行时间
     */
    private String lastDate;

    private String statStr;

    private String useCron;

    /**
     * 操作人名称
     */
    private String operUsername;

    public ScheduleJobDO() {
    }

    public ScheduleJobDO(String id) {
        this.id = id;
    }

    public ScheduleJobDO(Class<?> clazz) {
        this.beanName = clazz.getName();
    }

    public ScheduleJobDO(IJobHandle handle) {
        JobDescriptor descriptor = handle.getClass().getAnnotation(JobDescriptor.class);
        this.beanName = handle.getClass().getName();
        this.cronExpr = descriptor.cronExpr();
        this.name = descriptor.name();
        this.numKv = String.valueOf(descriptor.numKv());
        this.openSwitchFlg = descriptor.openSwitchFlg().getCode();
        this.planExecMeth = descriptor.planExecMeth().getCode();
        this.planExecTp = descriptor.planExecTp().name().toLowerCase();
        this.runSwitchFlg = descriptor.runSwitchFlg().getCode();
        this.efftTime = DateUtil.getDateTime();
        this.invlTime = "2099-12-31 23:59:59";
        this.rmrk = descriptor.remark();
        this.timeUnitTp = Constants.Scheduler.CronUnit.SECOND;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEfftTime() {
        return efftTime;
    }

    public void setEfftTime(String efftTime) {
        this.efftTime = efftTime;
    }

    public String getInvlTime() {
        return invlTime;
    }

    public void setInvlTime(String invlTime) {
        this.invlTime = invlTime;
    }

    public String getOpenSwitchFlg() {
        return openSwitchFlg;
    }

    public void setOpenSwitchFlg(String openSwitchFlg) {
        this.openSwitchFlg = openSwitchFlg;
    }

    public String getPlanExecMeth() {
        return planExecMeth;
    }

    public void setPlanExecMeth(String planExecMeth) {
        this.planExecMeth = planExecMeth;
    }

    public String getNumKv() {
        return numKv;
    }

    public void setNumKv(String numKv) {
        this.numKv = numKv;
    }

    public String getTimeUnitTp() {
        return timeUnitTp;
    }

    public void setTimeUnitTp(String timeUnitTp) {
        this.timeUnitTp = timeUnitTp;
    }

    public String getIntvlTime() {
        return intvlTime;
    }

    public void setIntvlTime(String intvlTime) {
        this.intvlTime = intvlTime;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlanExecTp() {
        return planExecTp;
    }

    public void setPlanExecTp(String planExecTp) {
        this.planExecTp = planExecTp;
    }

    public String getProcDateParaVal() {
        return procDateParaVal;
    }

    public void setProcDateParaVal(String procDateParaVal) {
        this.procDateParaVal = procDateParaVal;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getListenerType() {
        return listenerType;
    }

    public void setListenerType(String listenerType) {
        this.listenerType = listenerType;
    }

    public boolean statusRunning() {
        return this.openSwitchFlg.equals(Status.RUN);
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getRunSwitchFlg() {
        return runSwitchFlg;
    }

    public void setRunSwitchFlg(String runSwitchFlg) {
        this.runSwitchFlg = runSwitchFlg;
    }

    public String getStatStr() {
        return statStr;
    }

    public void setStatStr(String statStr) {
        this.statStr = statStr;
    }

    public String getUseCron() {
        return useCron;
    }

    public void setUseCron(String useCron) {
        this.useCron = useCron;
    }

    public String getOperUsername() {
        return operUsername;
    }

    public void setOperUsername(String operUsername) {
        this.operUsername = operUsername;
    }

    @Override
    public List<String> getIgnoreFields() {
        List<String> ignoreFields = super.getIgnoreFields();
        ignoreFields.add("action");
        ignoreFields.add("listenerType");
        ignoreFields.add("lastDate");
        ignoreFields.add("delFlg");
        ignoreFields.add("statStr");
        ignoreFields.add("useCron");
        ignoreFields.add("operUsername");
        return ignoreFields;
    }
}
