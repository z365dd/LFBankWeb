package com.adtec.sys.modules.flow.entity;

import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class FlowStepTemplateDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /**
     * 流程开始节点号：0-总流程开始
     */
    public static final int STEP_START = 0;
    /**
     * 流程开始节点号中文名称：总流程开始
     */
    public static final String STEP_START_NAME = "总流程开始";
    /**
     * 流程结束节点号：999-总流程结束
     */
    public static final int STEP_END = 999;
    /**
     * 流程结束节点号中文名称：总流程结束
     */
    public static final String STEP_END_NAME = "总流程结束";
    /**
     * 中英文名称模糊查询：0-否
     */
    public static final String MQRY_N = "0";
    /**
     * 中英文名称模糊查询：1-是
     */
    public static final String MQRY_Y = "1";
    /**
     * 模糊查询,0-否、1-是
     */
    private String mQry;
    
    /*流程模板ID*/
    private String flowTmplId;
    /*步骤号*/
    private int stepSer;
    /*步骤英文名称*/
    private String engName;
    /*步骤中文名称*/
    private String name;
    /*通过标准*/
    private String flowApprFlg;
    /*通过用户个数*/
    private int succNum;
    /*事件处理类*/
    private String flowProcClssTp;
    /*下一步骤号*/
    private int nextStepSer;
    /*上一步骤号*/
    private int prvStepSer;
    /*最长处理时间*/
    private int dayNum;
    /*超时处理*/
    private String flowTimeOutFlg;
    /*超时处理用户类型*/
    private String timeOutFlowUserTp;
    /*超时处理用户编码*/
    private String timeOutProcUserId;
    /*审批用户类型*/
    private String apprFlowUserTp;
    /*审批用户编码*/
    private String apprUserId;
    /*DAC*/
    private String dac;

    public String getFlowTmplId() {
        return flowTmplId;
    }

    public void setFlowTmplId(String flowTmplId) {
        this.flowTmplId = flowTmplId;
    }

    public int getStepSer() {
        return stepSer;
    }

    public void setStepSer(int stepSer) {
        this.stepSer = stepSer;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlowApprFlg() {
        return flowApprFlg;
    }

    public void setFlowApprFlg(String flowApprFlg) {
        this.flowApprFlg = flowApprFlg;
    }

    public int getSuccNum() {
        return succNum;
    }

    public void setSuccNum(int succNum) {
        this.succNum = succNum;
    }

    public String getFlowProcClssTp() {
        return flowProcClssTp;
    }

    public void setFlowProcClssTp(String flowProcClssTp) {
        this.flowProcClssTp = flowProcClssTp;
    }

    public int getNextStepSer() {
        return nextStepSer;
    }

    public void setNextStepSer(int nextStepSer) {
        this.nextStepSer = nextStepSer;
    }

    public int getPrvStepSer() {
        return prvStepSer;
    }

    public void setPrvStepSer(int prvStepSer) {
        this.prvStepSer = prvStepSer;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public String getFlowTimeOutFlg() {
        return flowTimeOutFlg;
    }

    public void setFlowTimeOutFlg(String flowTimeOutFlg) {
        this.flowTimeOutFlg = flowTimeOutFlg;
    }

    public String getTimeOutFlowUserTp() {
        return timeOutFlowUserTp;
    }

    public void setTimeOutFlowUserTp(String timeOutFlowUserTp) {
        this.timeOutFlowUserTp = timeOutFlowUserTp;
    }

    public String getTimeOutProcUserId() {
        return timeOutProcUserId;
    }

    public void setTimeOutProcUserId(String timeOutProcUserId) {
        this.timeOutProcUserId = timeOutProcUserId;
    }

    public String getApprFlowUserTp() {
        return apprFlowUserTp;
    }

    public void setApprFlowUserTp(String apprFlowUserTp) {
        this.apprFlowUserTp = apprFlowUserTp;
    }

    public String getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(String apprUserId) {
        this.apprUserId = apprUserId;
    }

    public String getDac() {
        return dac;
    }
    public void setDac(String dac) {
        this.dac = dac;
    }
    /**
	 * @return the mQry
	 */
	public String getmQry() {
		return mQry;
	}
	/**
	 * @param mQry the mQry to set
	 */
	public void setmQry(String mQry) {
		this.mQry = mQry;
	}
	
	/* (non-Javadoc)
	 * @see com.adtec.sys.common.persistence.BaseDO#getIgnoreFields()
	 */
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> ignoreList = super.getIgnoreFields();
		ignoreList.add("delFlg");
		ignoreList.add("MQRY_N");
		ignoreList.add("MQRY_Y");
		ignoreList.add("mQry");
		ignoreList.add("STEP_START");
		ignoreList.add("STEP_END");
		ignoreList.add("STEP_START_NAME");
		ignoreList.add("STEP_END_NAME");
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
