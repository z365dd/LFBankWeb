package com.adtec.comp.sign.dto;

import java.io.Serializable;

public class SignEntrQryReqDTO implements Serializable {
	private String ENTR_NO;
	private String BUSI_NO;
	private String SUB_BUSI_NO;
	private String CERT_TP;
	private String CERT_NO;
	private String SIGN_PROT_NO;
	private String OTH_CUST_NO;
	private String ACCT;
	private String SIGN_STAT;
	public String getENTR_NO() {
		return ENTR_NO;
	}
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	public String getBUSI_NO() {
		return BUSI_NO;
	}
	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}
	public String getSUB_BUSI_NO() {
		return SUB_BUSI_NO;
	}
	public void setSUB_BUSI_NO(String sUB_BUSI_NO) {
		SUB_BUSI_NO = sUB_BUSI_NO;
	}
	public String getCERT_TP() {
		return CERT_TP;
	}
	public void setCERT_TP(String cERT_TP) {
		CERT_TP = cERT_TP;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getSIGN_PROT_NO() {
		return SIGN_PROT_NO;
	}
	public void setSIGN_PROT_NO(String sIGN_PROT_NO) {
		SIGN_PROT_NO = sIGN_PROT_NO;
	}
	public String getOTH_CUST_NO() {
		return OTH_CUST_NO;
	}
	public void setOTH_CUST_NO(String oTH_CUST_NO) {
		OTH_CUST_NO = oTH_CUST_NO;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}
	public String getSIGN_STAT() {
		return SIGN_STAT;
	}
	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}
}
