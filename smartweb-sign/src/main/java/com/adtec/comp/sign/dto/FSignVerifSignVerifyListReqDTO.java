package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignVerifSignVerifyListReqDTO {
	
	/*private String BANK_SIGN_PROT_NO;
	private String OTH_SIGN_PROT_NO;
	private String BANK;*/
	private String SIGN_PROT_TP_ID;
	private String SIGN_PROT_NO;
	private String OTH_CUST_NO;
	private String ACCT;
	private String OTH_CUST_NAME;
	private String OTH_ENTR_NO;
	private String ACCT_NAME;
	private String CERT_TP;
	private String CERT_NO;
	private String SIGN_CTRCT_NO;
	private List<FSignFuncCustQryDynListReqDTO> DYN_LIST = new ArrayList<FSignFuncCustQryDynListReqDTO>();
	
	public String getSIGN_PROT_TP_ID() {
		return SIGN_PROT_TP_ID;
	}
	public void setSIGN_PROT_TP_ID(String sIGN_PROT_TP_ID) {
		SIGN_PROT_TP_ID = sIGN_PROT_TP_ID;
	}
	public String getSIGN_PROT_NO() {
		return SIGN_PROT_NO;
	}
	public void setSIGN_PROT_NO(String sIGN_PROT_NO) {
		SIGN_PROT_NO = sIGN_PROT_NO;
	}
	public String getSIGN_CTRCT_NO() {
		return SIGN_CTRCT_NO;
	}
	public void setSIGN_CTRCT_NO(String sIGN_CTRCT_NO) {
		SIGN_CTRCT_NO = sIGN_CTRCT_NO;
	}
	public List<FSignFuncCustQryDynListReqDTO> getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(List<FSignFuncCustQryDynListReqDTO> dYN_LIST) {
		DYN_LIST = dYN_LIST;
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
	public String getOTH_CUST_NAME() {
		return OTH_CUST_NAME;
	}
	public void setOTH_CUST_NAME(String oTH_CUST_NAME) {
		OTH_CUST_NAME = oTH_CUST_NAME;
	}
	public String getOTH_ENTR_NO() {
		return OTH_ENTR_NO;
	}
	public void setOTH_ENTR_NO(String oTH_ENTR_NO) {
		OTH_ENTR_NO = oTH_ENTR_NO;
	}
	public String getACCT_NAME() {
		return ACCT_NAME;
	}
	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
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

	
}
