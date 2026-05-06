package com.adtec.sys.modules.flow.entity;

import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class FlowTemplateDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /**
     * 流程模板类型：01-用户新增
     */
    public static final String FLOW_TYPE_USER = "01";
    /**
     * 流程模板类型：02-词素模板维护
     */
    public static final String FLOW_TYPE_MORPHEME = "02";
    /**
     * 流程模板类型：03-数字字典维护
     */
    public static final String FLOW_TYPE_DICT = "03";
    /**
     * 流程模板类型：04-参与者维护
     */
    public static final String FLOW_TYPE_PART = "04";
    /**
     * 流程模板类型：06-订单审批
     */
    public static final String FLOW_TYPE_ORDER = "06";
    /**
     * 流程模板类型：07-ESB映射审批
     */
    public static final String FLOW_TYPE_ESB = "07";
    /**
     * 流程模板类型：08-总线组件审批
     */
    public static final String FLOW_TYPE_ESB_COMP = "08";
    
    /**
     * 流程模板状态：1-启用
     */
    public static final String STAT_ON = "1";
    /**
     * 流程模板状态：2-挂起
     */
    public static final String STAT_OFF = "2";
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
    
    /*流程英文名称*/
    private String engName;
    /*流程中文名称*/
    private String name;
    /*流程类型*/
    private String flowTp;
    /*版本*/
    private String verNo;
    /*状态*/
    private String flowTmplStat;
    /*最长处理时间*/
    private int dayNum;
    /*超时处理*/
    private String flowTimeOutFlg;
    /*超时处理用户类型*/
    private String timeOutFlowUserTp;
    /*超时处理用户编码*/
    private String timeOutProcUserId;
    /*发起用户类型*/
    private String sndFlowUserTp;
    /*发起用户编码*/
    private String sndUserId;
    /*处理类*/
    private String flowProcClssTp;
    /*DAC*/
    private String dac;
    /*申请内容详情URL*/
    private String applyUrl;

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

    public String getFlowTp() {
        return flowTp;
    }

    public void setFlowTp(String flowTp) {
        this.flowTp = flowTp;
    }

    public String getVerNo() {
        return verNo;
    }

    public void setVerNo(String verNo) {
        this.verNo = verNo;
    }

    public String getFlowTmplStat() {
        return flowTmplStat;
    }

    public void setFlowTmplStat(String flowTmplStat) {
        this.flowTmplStat = flowTmplStat;
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

    public String getSndFlowUserTp() {
        return sndFlowUserTp;
    }

    public void setSndFlowUserTp(String sndFlowUserTp) {
        this.sndFlowUserTp = sndFlowUserTp;
    }

    public String getSndUserId() {
        return sndUserId;
    }

    public void setSndUserId(String sndUserId) {
        this.sndUserId = sndUserId;
    }

    public String getFlowProcClssTp() {
        return flowProcClssTp;
    }

    public void setFlowProcClssTp(String flowProcClssTp) {
        this.flowProcClssTp = flowProcClssTp;
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
	
	public String getApplyUrl() {
		return applyUrl;
	}

	public void setApplyUrl(String applyUrl) {
		this.applyUrl = applyUrl;
	}

	/* (non-Javadoc)
	 * @see com.adtec.sys.common.persistence.BaseDO#getIgnoreFields()
	 */
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> ignoreList = super.getIgnoreFields();
		ignoreList.add("delFlg");
		ignoreList.add("FLOW_TYPE_USER");
		ignoreList.add("FLOW_TYPE_MORPHEME");
		ignoreList.add("FLOW_TYPE_DICT");
		ignoreList.add("FLOW_TYPE_PART");
        ignoreList.add("FLOW_TYPE_ORDER");
        ignoreList.add("FLOW_TYPE_ESB");
        ignoreList.add("FLOW_TYPE_ESB_COMP");
		ignoreList.add("STAT_ON");
		ignoreList.add("STAT_OFF");
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
