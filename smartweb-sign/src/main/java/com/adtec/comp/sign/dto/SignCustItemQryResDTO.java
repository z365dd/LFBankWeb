package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignCustItemQryResDTO {
	private String BUSI_NO;
	private String SUB_BUSI_NO;
	private String ENTR_NO;
	private String ACCT;
	private String OTH_CUST_NO;
	private long NUM;
	private List<SignCustItemQryListDTO> LIST = new ArrayList<SignCustItemQryListDTO>();
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
	public String getENTR_NO() {
		return ENTR_NO;
	}
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}
	public String getOTH_CUST_NO() {
		return OTH_CUST_NO;
	}
	public void setOTH_CUST_NO(String oTH_CUST_NO) {
		OTH_CUST_NO = oTH_CUST_NO;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<SignCustItemQryListDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<SignCustItemQryListDTO> lIST) {
		LIST = lIST;
	}
}
