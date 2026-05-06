package com.adtec.sys.modules.flow.entity;

import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class FlowStepDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    
    /**
     * 流程状态：07-超时人工拒绝
     */
    public static final String STAT_TIMEOUT_REFUSE_END = "07";
    /**
     * 流程状态：06-超时人工通过
     */
    public static final String STAT_TIMEOUT_PASS_END = "06";
    /**
     * 流程状态：05-自动审批拒绝
     */
    public static final String STAT_AUTO_REFUSE_END = "05";
    /**
     * 流程状态：04-自动审批通过
     */
    public static final String STAT_AUTO_PASS_END = "04";
    /**
     * 流程状态：03-审批拒绝
     */
    public static final String STAT_REFUSE_END = "03";
    /**
     * 流程状态：02-审批通过
     */
    public static final String STAT_PASS_END = "02";
    /**
     * 流程状态：01-待审批
     */
    public static final String STAT_PENDING = "01";
    
    /*处理该步骤的全局流水号*/
    private String globalSeq;
    /*流程模板ID*/
    private String flowTmplId;
    /*流程步骤模板ID*/
    private String stepTmplId;
    /*步骤号*/
    private int stepSer;
    /*步骤标题*/
    private String infoTitle;
    /*步骤程描述*/
    private String flowDesc;
    /*流程状态*/
    private String flowStat;
    /*审批信息*/
    private String appMsg;
    /*处理用户ID*/
    private String curProcUserId;
    /*处理用户名称*/
    private String curProcUserName;
    /*开始时间*/
    private String strTime;
    /*结束时间*/
    private String endTime;
    /*超时处理用户ID*/
    private String timeOutProcUserId;
    /*超时处理用户名称*/
    private String timeOutProcUserName;
    /*DAC*/
    private String dac;

    public String getGlobalSeq() {
        return globalSeq;
    }

    public void setGlobalSeq(String globalSeq) {
        this.globalSeq = globalSeq;
    }

    public String getFlowTmplId() {
        return flowTmplId;
    }

    public void setFlowTmplId(String flowTmplId) {
        this.flowTmplId = flowTmplId;
    }

    public String getStepTmplId() {
        return stepTmplId;
    }

    public void setStepTmplId(String stepTmplId) {
        this.stepTmplId = stepTmplId;
    }

    public int getStepSer() {
        return stepSer;
    }

    public void setStepSer(int stepSer) {
        this.stepSer = stepSer;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getFlowDesc() {
        return flowDesc;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }

    public String getFlowStat() {
        return flowStat;
    }

    public void setFlowStat(String flowStat) {
        this.flowStat = flowStat;
    }

    public String getAppMsg() {
        return appMsg;
    }

    public void setAppMsg(String appMsg) {
        this.appMsg = appMsg;
    }

    public String getCurProcUserId() {
        return curProcUserId;
    }

    public void setCurProcUserId(String curProcUserId) {
        this.curProcUserId = curProcUserId;
    }

    public String getCurProcUserName() {
        return curProcUserName;
    }

    public void setCurProcUserName(String curProcUserName) {
        this.curProcUserName = curProcUserName;
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

    public String getTimeOutProcUserId() {
        return timeOutProcUserId;
    }

    public void setTimeOutProcUserId(String timeOutProcUserId) {
        this.timeOutProcUserId = timeOutProcUserId;
    }

    public String getTimeOutProcUserName() {
        return timeOutProcUserName;
    }

    public void setTimeOutProcUserName(String timeOutProcUserName) {
        this.timeOutProcUserName = timeOutProcUserName;
    }

    public String getDac() {
        return dac;
    }
    public void setDac(String dac) {
        this.dac = dac;
    }
    /* (non-Javadoc)
	 * @see com.adtec.sys.common.persistence.BaseDO#getIgnoreFields()
	 */
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> ignoreList = super.getIgnoreFields();
		ignoreList.add("delFlg");
		ignoreList.add("STAT_PENDING");
		ignoreList.add("STAT_PASS_END");
		ignoreList.add("STAT_REFUSE_END");
		ignoreList.add("STAT_AUTO_PASS_END");
		ignoreList.add("STAT_AUTO_REFUSE_END");
		ignoreList.add("STAT_TIMEOUT_PASS_END");
		ignoreList.add("STAT_TIMEOUT_REFUSE_END");
		ignoreList.add("MQRY_N");
		ignoreList.add("MQRY_Y");
		ignoreList.add("mQry");
		return ignoreList;
	}
	/* (non-Javadoc)
	 * @see com.adtec.sys.common.persistence.BaseDO#getMatchFields()
	 */
	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		return super.getMatchFields();
	}

}
