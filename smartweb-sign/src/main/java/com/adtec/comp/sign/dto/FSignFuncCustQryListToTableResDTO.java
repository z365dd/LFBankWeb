package com.adtec.comp.sign.dto;

public class FSignFuncCustQryListToTableResDTO {
	/*private String SIGN_PROT_TP_NO;
	private String SIGN_PROT_TP_NAME;
	private String OTH_SIGN_PROT_NO;
	private String OTH_CUST_NO;
	private String OTH_CUST_NAME;
	private String OTH_ENTR_NO;
	private String BANK_SIGN_PROT_NO;
	private String PROT_END_DATE;
	private String SIGN_STAT;
	private String SIGN_BRCH;
	private String SIGN_TLR_NO;
	private String SIGN_MOD_BRCH;
	private String SIGN_MOD_TLR_NO;
	private String SIGN_CANCL_BRCH;
	private String SIGN_CANCL_TLR_NO;*/
	
	private String SIGN_PROT_TP_ID;
	private String SIGN_PROT_NO;
	
	private String SIGN_CTRCT_NO;
	private String OTH_CUST_NO;
	private String SIGN_STAT;
	private String ERR_TP;

	private String ACCT;
	private String ACCT_NAME;
	private String PHONE_NO;
	
	private String ACTION;

	
	public String getSIGN_PROT_NO() {
		return SIGN_PROT_NO;
	}

	public void setSIGN_PROT_NO(String sIGN_PROT_NO) {
		SIGN_PROT_NO = sIGN_PROT_NO;
	}

	public String getSIGN_PROT_TP_ID() {
		return SIGN_PROT_TP_ID;
	}

	public void setSIGN_PROT_TP_ID(String sIGN_PROT_TP_ID) {
		SIGN_PROT_TP_ID = sIGN_PROT_TP_ID;
	}

	public String getSIGN_CTRCT_NO() {
		return SIGN_CTRCT_NO;
	}

	public void setSIGN_CTRCT_NO(String sIGN_CTRCT_NO) {
		SIGN_CTRCT_NO = sIGN_CTRCT_NO;
	}

	public String getOTH_CUST_NO() {
		return OTH_CUST_NO;
	}

	public void setOTH_CUST_NO(String oTH_CUST_NO) {
		OTH_CUST_NO = oTH_CUST_NO;
	}

	public String getSIGN_STAT() {
		return SIGN_STAT;
	}

	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}

	public String getERR_TP() {
		return ERR_TP;
	}

	public void setERR_TP(String eRR_TP) {
		ERR_TP = eRR_TP;
	}

	public String getACCT() {
		return ACCT;
	}

	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}

	public String getACCT_NAME() {
		return ACCT_NAME;
	}

	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
	}

	public String getPHONE_NO() {
		return PHONE_NO;
	}

	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}

	public String getACTION() {
		return ACTION;
	}

	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	
	
	
}
