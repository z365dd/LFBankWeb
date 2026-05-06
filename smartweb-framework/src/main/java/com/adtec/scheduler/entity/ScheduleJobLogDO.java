package com.adtec.scheduler.entity;
/**
 * 系统名称: SmartWeb平台
 * 模块名称:自动任务日志表实体类
 * 类  名  称: UleJobLogDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2019-07-31 21:56:42
 * 系统版本: V1.0.0
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */

import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

public class ScheduleJobLogDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /**
     * 平台流水号 PLAT_SEQ
     */
    private String platSeq;
    /**
     * 任务id JOB_ID
     */
    private String jobId;
    /**
     * 实例号 TASK_INS_NO
     */
    private String taskInsNo;
    /**
     * ip
     */
    private String ip;
    /**
     * 端口号
     */
    private String port;
    /**
     * 类名
     */
    private String beanName;
    /**
     * 开始时间 STR_TIME
     */
    private String strTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 任务状态 STAT
     */
    private String succSwitchFlg;
    /**
     * 错误码 ERR_CODE
     */
    private String errCode;
    /**
     * 错误信息 ERR_MSG
     */
    private byte[] errMsgByteData;
    /**
     * 交易耗时
     */
    private String tranTime;

    private String errMsgStr;

    public String getPlatSeq() {
        return platSeq;
    }

    public void setPlatSeq(String platSeq) {
        this.platSeq = platSeq;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTaskInsNo() {
        return taskInsNo;
    }

    public void setTaskInsNo(String taskInsNo) {
        this.taskInsNo = taskInsNo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
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

    public String getSuccSwitchFlg() {
        return succSwitchFlg;
    }

    public void setSuccSwitchFlg(String succSwitchFlg) {
        this.succSwitchFlg = succSwitchFlg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public byte[] getErrMsgByteData() {
        return errMsgByteData;
    }

    public void setErrMsgByteData(byte[] errMsgByteData) {
        this.errMsgByteData = errMsgByteData;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getErrMsgStr() {
        return errMsgStr;
    }

    public void setErrMsgStr(String errMsgStr) {
        this.errMsgStr = errMsgStr;
    }

    @Override
    public List<String> getIgnoreFields() {
        List<String> ignoreFields = super.getIgnoreFields();
        ignoreFields.add("delFlg");
        ignoreFields.add("rmrk");
        ignoreFields.add("errorMsgStr");
        return ignoreFields;
    }

}
