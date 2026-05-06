package com.adtec.sys.modules.flow.entity;

import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class FlowDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /**
     * 流程系统内部发起用户：FlowInnerUser
     */
    public static final String FLOW_INIT_USER = "FlowInnerUser";
    /**
     * 流程系统内部发起用户中文名称：系统自动发起流程用户
     */
    public static final String FLOW_INIT_USER_NAME = "系统自动发起流程用户";
    /**
     * 流程状态：08-超时人工拒绝
     */
    public static final String STAT_TIMEOUT_REFUSE_END = "07";
    /**
     * 流程状态：07-超时人工通过
     */
    public static final String STAT_TIMEOUT_PASS_END = "06";
    /**
     * 流程状态：06-自动审批拒绝
     */
    public static final String STAT_AUTO_REFUSE_END = "05";
    /**
     * 流程状态：05-自动审批通过
     */
    public static final String STAT_AUTO_PASS_END = "04";
    /**
     * 流程状态：04-审批拒绝
     */
    public static final String STAT_REFUSE_END = "03";
    /**
     * 流程状态：03-审批通过
     */
    public static final String STAT_PASS_END = "02";
    /**
     * 流程状态：02-处理中
     */
    public static final String STAT_DEAL = "01";
    /**
     * 流程状态：00-开始
     */
    public static final String STAT_START = "00";
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
    
    /*全局流水号*/
    private String globalSeq;
    /*流程模板ID*/
    private String flowTmplId;
    /*流程标题*/
    private String infoTitle;
    /*流程描述*/
    private String flowDesc;
    /*流程状态*/
    private String flowStat;
    /*当前步骤号*/
    private int stepSer;
    /*发起用户ID*/
    private String sndUserId;
    /*发起用户名*/
    private String sndUserName;
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

    public int getStepSer() {
        return stepSer;
    }

    public void setStepSer(int stepSer) {
        this.stepSer = stepSer;
    }

    public String getSndUserId() {
        return sndUserId;
    }

    public void setSndUserId(String sndUserId) {
        this.sndUserId = sndUserId;
    }

    public String getSndUserName() {
        return sndUserName;
    }

    public void setSndUserName(String sndUserName) {
        this.sndUserName = sndUserName;
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
		ignoreList.add("STAT_START");
		ignoreList.add("STAT_DEAL");
		ignoreList.add("STAT_PASS_END");
		ignoreList.add("STAT_REFUSE_END");
		ignoreList.add("STAT_AUTO_PASS_END");
		ignoreList.add("STAT_AUTO_REFUSE_END");
		ignoreList.add("STAT_TIMEOUT_PASS_END");
		ignoreList.add("STAT_TIMEOUT_REFUSE_END");
		ignoreList.add("MQRY_N");
		ignoreList.add("MQRY_Y");
		ignoreList.add("mQry");
		ignoreList.add("FLOW_INIT_USER");
		ignoreList.add("FLOW_INIT_USER_NAME");
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
