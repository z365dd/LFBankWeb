package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

public class SquareRulesDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    //规则关系表
	private String BUSI_NO;	
	private String BUSI_NAME;//业务名称
	private String SVC_CODE;//服务代码
	private String SCENE_NO;//场景
	private String RULE_ID;//清算规则编号

	//清算规则表
	private String RULE_NAME;//
	private String RULE_DESC;//
	private String CLR_DIM_TP;//
	private String CLR_METH;//
	private String CLR_CYC_TP;//
	private String CLR_CYC;//
	private String CLR_SND_GRP_FLG;//
	private String STR_TIME;//
	private String END_TIME;//
	private String MONTH_END_FLG;//
	private String MONTH_END_STR_TIME;//
	private String MONTH_END_END_TIME;//
	private String QUARTER_END_FLG;//
	private String QUARTER_END_STR_TIME;//
	private String QUARTER_END_END_TIME;//
	private String YEAR_END_FLG;//
	private String YEAR_END_STR_TIME;//
	private String YEAR_END_END_TIME;//
	private String BAT_PROC_FLG;//
	private String VRFY_FLG;//
	private String INTRM_ACCT_FLG;//
	private String INTRM_ACCT;//
	private String INTRM_ACCT_NAME;//
	private String ENTR_ACCT;//
	private String ENTR_ACCT_NAME;//
	private String ENTR_ACCT_BANK;//
	private String ENTR_ACCT_BANK_NAME;//
	private String IN_OUT_BANK_FLG;//
	private String POSTING_SUM_CODE;//
	private String POSTING_SUM_DESC;//
	private String FLG;//
	private String UP_RULE_ID;//
	private String BEAN;//
	private String SHORT_RMRK;//
	private String MID_RMRK;//
	private String LONG_RMRK;//
	private String DAC;//

	
	
	
	
	public String getRULE_NAME() {
		return RULE_NAME;
	}
	public void setRULE_NAME(String rULE_NAME) {
		RULE_NAME = rULE_NAME;
	}
	public String getRULE_DESC() {
		return RULE_DESC;
	}
	public void setRULE_DESC(String rULE_DESC) {
		RULE_DESC = rULE_DESC;
	}
	public String getCLR_DIM_TP() {
		return CLR_DIM_TP;
	}
	public void setCLR_DIM_TP(String cLR_DIM_TP) {
		CLR_DIM_TP = cLR_DIM_TP;
	}
	public String getCLR_METH() {
		return CLR_METH;
	}
	public void setCLR_METH(String cLR_METH) {
		CLR_METH = cLR_METH;
	}
	public String getCLR_CYC_TP() {
		return CLR_CYC_TP;
	}
	public void setCLR_CYC_TP(String cLR_CYC_TP) {
		CLR_CYC_TP = cLR_CYC_TP;
	}
	public String getCLR_CYC() {
		return CLR_CYC;
	}
	public void setCLR_CYC(String cLR_CYC) {
		CLR_CYC = cLR_CYC;
	}
	public String getCLR_SND_GRP_FLG() {
		return CLR_SND_GRP_FLG;
	}
	public void setCLR_SND_GRP_FLG(String cLR_SND_GRP_FLG) {
		CLR_SND_GRP_FLG = cLR_SND_GRP_FLG;
	}
	public String getSTR_TIME() {
		return STR_TIME;
	}
	public void setSTR_TIME(String sTR_TIME) {
		STR_TIME = sTR_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getMONTH_END_FLG() {
		return MONTH_END_FLG;
	}
	public void setMONTH_END_FLG(String mONTH_END_FLG) {
		MONTH_END_FLG = mONTH_END_FLG;
	}
	public String getMONTH_END_STR_TIME() {
		return MONTH_END_STR_TIME;
	}
	public void setMONTH_END_STR_TIME(String mONTH_END_STR_TIME) {
		MONTH_END_STR_TIME = mONTH_END_STR_TIME;
	}
	public String getMONTH_END_END_TIME() {
		return MONTH_END_END_TIME;
	}
	public void setMONTH_END_END_TIME(String mONTH_END_END_TIME) {
		MONTH_END_END_TIME = mONTH_END_END_TIME;
	}
	public String getQUARTER_END_FLG() {
		return QUARTER_END_FLG;
	}
	public void setQUARTER_END_FLG(String qUARTER_END_FLG) {
		QUARTER_END_FLG = qUARTER_END_FLG;
	}
	public String getQUARTER_END_STR_TIME() {
		return QUARTER_END_STR_TIME;
	}
	public void setQUARTER_END_STR_TIME(String qUARTER_END_STR_TIME) {
		QUARTER_END_STR_TIME = qUARTER_END_STR_TIME;
	}
	public String getQUARTER_END_END_TIME() {
		return QUARTER_END_END_TIME;
	}
	public void setQUARTER_END_END_TIME(String qUARTER_END_END_TIME) {
		QUARTER_END_END_TIME = qUARTER_END_END_TIME;
	}
	public String getYEAR_END_FLG() {
		return YEAR_END_FLG;
	}
	public void setYEAR_END_FLG(String yEAR_END_FLG) {
		YEAR_END_FLG = yEAR_END_FLG;
	}
	public String getYEAR_END_STR_TIME() {
		return YEAR_END_STR_TIME;
	}
	public void setYEAR_END_STR_TIME(String yEAR_END_STR_TIME) {
		YEAR_END_STR_TIME = yEAR_END_STR_TIME;
	}
	public String getYEAR_END_END_TIME() {
		return YEAR_END_END_TIME;
	}
	public void setYEAR_END_END_TIME(String yEAR_END_END_TIME) {
		YEAR_END_END_TIME = yEAR_END_END_TIME;
	}
	public String getBAT_PROC_FLG() {
		return BAT_PROC_FLG;
	}
	public void setBAT_PROC_FLG(String bAT_PROC_FLG) {
		BAT_PROC_FLG = bAT_PROC_FLG;
	}
	public String getVRFY_FLG() {
		return VRFY_FLG;
	}
	public void setVRFY_FLG(String vRFY_FLG) {
		VRFY_FLG = vRFY_FLG;
	}
	public String getINTRM_ACCT_FLG() {
		return INTRM_ACCT_FLG;
	}
	public void setINTRM_ACCT_FLG(String iNTRM_ACCT_FLG) {
		INTRM_ACCT_FLG = iNTRM_ACCT_FLG;
	}
	public String getINTRM_ACCT() {
		return INTRM_ACCT;
	}
	public void setINTRM_ACCT(String iNTRM_ACCT) {
		INTRM_ACCT = iNTRM_ACCT;
	}
	public String getINTRM_ACCT_NAME() {
		return INTRM_ACCT_NAME;
	}
	public void setINTRM_ACCT_NAME(String iNTRM_ACCT_NAME) {
		INTRM_ACCT_NAME = iNTRM_ACCT_NAME;
	}
	public String getENTR_ACCT() {
		return ENTR_ACCT;
	}
	public void setENTR_ACCT(String eNTR_ACCT) {
		ENTR_ACCT = eNTR_ACCT;
	}
	public String getENTR_ACCT_NAME() {
		return ENTR_ACCT_NAME;
	}
	public void setENTR_ACCT_NAME(String eNTR_ACCT_NAME) {
		ENTR_ACCT_NAME = eNTR_ACCT_NAME;
	}
	public String getENTR_ACCT_BANK() {
		return ENTR_ACCT_BANK;
	}
	public void setENTR_ACCT_BANK(String eNTR_ACCT_BANK) {
		ENTR_ACCT_BANK = eNTR_ACCT_BANK;
	}
	public String getENTR_ACCT_BANK_NAME() {
		return ENTR_ACCT_BANK_NAME;
	}
	public void setENTR_ACCT_BANK_NAME(String eNTR_ACCT_BANK_NAME) {
		ENTR_ACCT_BANK_NAME = eNTR_ACCT_BANK_NAME;
	}
	
	public String getIN_OUT_BANK_FLG() {
		return IN_OUT_BANK_FLG;
	}
	public void setIN_OUT_BANK_FLG(String iN_OUT_BANK_FLG) {
		IN_OUT_BANK_FLG = iN_OUT_BANK_FLG;
	}
	public String getPOSTING_SUM_CODE() {
		return POSTING_SUM_CODE;
	}
	public void setPOSTING_SUM_CODE(String pOSTING_SUM_CODE) {
		POSTING_SUM_CODE = pOSTING_SUM_CODE;
	}
	public String getPOSTING_SUM_DESC() {
		return POSTING_SUM_DESC;
	}
	public void setPOSTING_SUM_DESC(String pOSTING_SUM_DESC) {
		POSTING_SUM_DESC = pOSTING_SUM_DESC;
	}
	public String getFLG() {
		return FLG;
	}
	public void setFLG(String fLG) {
		FLG = fLG;
	}
	public String getBEAN() {
		return BEAN;
	}
	public void setBEAN(String bEAN) {
		BEAN = bEAN;
	}
	public String getSHORT_RMRK() {
		return SHORT_RMRK;
	}
	public void setSHORT_RMRK(String sHORT_RMRK) {
		SHORT_RMRK = sHORT_RMRK;
	}
	public String getMID_RMRK() {
		return MID_RMRK;
	}
	public void setMID_RMRK(String mID_RMRK) {
		MID_RMRK = mID_RMRK;
	}
	public String getLONG_RMRK() {
		return LONG_RMRK;
	}
	public void setLONG_RMRK(String lONG_RMRK) {
		LONG_RMRK = lONG_RMRK;
	}
	public String getDAC() {
		return DAC;
	}
	public void setDAC(String dAC) {
		DAC = dAC;
	}
	public String getBUSI_NO() {
		return BUSI_NO;
	}
	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}
	public String getBUSI_NAME() {
		return BUSI_NAME;
	}
	public void setBUSI_NAME(String bUSI_NAME) {
		BUSI_NAME = bUSI_NAME;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getSCENE_NO() {
		return SCENE_NO;
	}
	public void setSCENE_NO(String sCENE_NO) {
		SCENE_NO = sCENE_NO;
	}
//	public String getRULE_NO() {
//		return RULE_NO;
//	}
//	public void setRULE_NO(String rULE_NO) {
//		RULE_NO = rULE_NO;
//	}
	public String getRULE_ID() {
		return RULE_ID;
	}
	public void setRULE_ID(String rULE_ID) {
		RULE_ID = rULE_ID;
	}
	
	public String getUP_RULE_ID() {
		return UP_RULE_ID;
	}
	public void setUP_RULE_ID(String uP_RULE_ID) {
		UP_RULE_ID = uP_RULE_ID;
	}
	/**
	 * 覆盖 设置忽略字段
	 */
	  public List<String> getIgnoreFields() {
	        List<String> ignoreFields = super.getIgnoreFields();
	        ignoreFields.add("serialVersionUID");
	        ignoreFields.add("DEL_FLAG_NORMAL");
	        ignoreFields.add("DEL_FLAG_DELETE");
	        ignoreFields.add("id");
	        ignoreFields.add("crtr");
	        ignoreFields.add("crtTime");
	        ignoreFields.add("uptr");
	        ignoreFields.add("uptTime");
	        ignoreFields.add("rmrk");
	        ignoreFields.add("delFlg");
	        ignoreFields.add("LIST");
	        ignoreFields.add("ELEM_KEY");
	        ignoreFields.add("ELEM_NAME");
	        ignoreFields.add("ELEM_KV");
	        ignoreFields.add("BUSI_NO");
	        ignoreFields.add("BUSI_NAME");
	        ignoreFields.add("SVC_CODE");
	        ignoreFields.add("SCENE_NO");
	        return ignoreFields;
	    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
