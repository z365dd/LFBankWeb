package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class BusiDO  extends BaseDO{
	private static final long serialVersionUID = 1L;
	private String BUSI_NO;
	private String ENTR_NO;
	private String BUSI_NAME;
	private String SALE_PROD_CODE;
	private String STAT;
	private String BUSI_DESC;
	private String CLR_TP;
	private String INTRM_ACCT_NAME;
	private String INTRM_ACCT;
	private String ENTR_ACCT_NAME;
	private String ENTR_ACCT;
	private String INNER_BANK_FLAG;
	public String getBUSI_NO() {
		return BUSI_NO;
	}
	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}
	public String getENTR_NO() {
		return ENTR_NO;
	}
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	public String getBUSI_NAME() {
		return BUSI_NAME;
	}
	public void setBUSI_NAME(String bUSI_NAME) {
		BUSI_NAME = bUSI_NAME;
	}
	public String getSALE_PROD_CODE() {
		return SALE_PROD_CODE;
	}
	public void setSALE_PROD_CODE(String sALE_PROD_CODE) {
		SALE_PROD_CODE = sALE_PROD_CODE;
	}
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public String getBUSI_DESC() {
		return BUSI_DESC;
	}
	public void setBUSI_DESC(String bUSI_DESC) {
		BUSI_DESC = bUSI_DESC;
	}
	public String getCLR_TP() {
		return CLR_TP;
	}
	public void setCLR_TP(String cLR_TP) {
		CLR_TP = cLR_TP;
	}
	public String getINTRM_ACCT_NAME() {
		return INTRM_ACCT_NAME;
	}
	public void setINTRM_ACCT_NAME(String iNTRM_ACCT_NAME) {
		INTRM_ACCT_NAME = iNTRM_ACCT_NAME;
	}
	public String getINTRM_ACCT() {
		return INTRM_ACCT;
	}
	public void setINTRM_ACCT(String iNTRM_ACCT) {
		INTRM_ACCT = iNTRM_ACCT;
	}
	public String getENTR_ACCT_NAME() {
		return ENTR_ACCT_NAME;
	}
	public void setENTR_ACCT_NAME(String eNTR_ACCT_NAME) {
		ENTR_ACCT_NAME = eNTR_ACCT_NAME;
	}
	public String getENTR_ACCT() {
		return ENTR_ACCT;
	}
	public void setENTR_ACCT(String eNTR_ACCT) {
		ENTR_ACCT = eNTR_ACCT;
	}
	public String getINNER_BANK_FLAG() {
		return INNER_BANK_FLAG;
	}
	public void setINNER_BANK_FLAG(String iNNER_BANK_FLAG) {
		INNER_BANK_FLAG = iNNER_BANK_FLAG;
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
	        return ignoreFields;
	    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	  

}
