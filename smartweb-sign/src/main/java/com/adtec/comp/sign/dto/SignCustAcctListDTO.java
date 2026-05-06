package com.adtec.comp.sign.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class SignCustAcctListDTO implements Serializable{
	private String ACCT;
	private String ACCT_NAME;
	private String ACCT_TP;
	private String CUST_TP;
	private String ENTR_NO;
	private String ENTR_NAME;
	private String BANK;
	private String BANK_NAME;
	private String CERT_TP;
	private String CERT_NO;
	private String TEL_NO;
	private String COMM_ADDR;
	private String POST_ECD;
	private String EMAIL_ADDR;
	private String WCHAT_NO;
	private String BANK_CUST_NO;
	private String SIGN_STAT;
	private long NUM;
	private JSONArray DYN_LIST;
	private long BUSI_NUM;
	private List<SignCustBusiListDTO> BUSI_LIST = new ArrayList<SignCustBusiListDTO>();
	private String ACTION;
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
	public String getACCT_TP() {
		return ACCT_TP;
	}
	public void setACCT_TP(String aCCT_TP) {
		ACCT_TP = aCCT_TP;
	}
	public String getCUST_TP() {
		return CUST_TP;
	}
	public void setCUST_TP(String cUST_TP) {
		CUST_TP = cUST_TP;
	}
	public String getBANK() {
		return BANK;
	}
	public void setBANK(String bANK) {
		BANK = bANK;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	public String getENTR_NO() {
		return ENTR_NO;
	}
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	public String getENTR_NAME() {
		return ENTR_NAME;
	}
	public void setENTR_NAME(String eNTR_NAME) {
		ENTR_NAME = eNTR_NAME;
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
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}
	public String getCOMM_ADDR() {
		return COMM_ADDR;
	}
	public void setCOMM_ADDR(String cOMM_ADDR) {
		COMM_ADDR = cOMM_ADDR;
	}
	public String getPOST_ECD() {
		return POST_ECD;
	}
	public void setPOST_ECD(String pOST_ECD) {
		POST_ECD = pOST_ECD;
	}
	public String getEMAIL_ADDR() {
		return EMAIL_ADDR;
	}
	public void setEMAIL_ADDR(String eMAIL_ADDR) {
		EMAIL_ADDR = eMAIL_ADDR;
	}
	public String getWCHAT_NO() {
		return WCHAT_NO;
	}
	public void setWCHAT_NO(String wCHAT_NO) {
		WCHAT_NO = wCHAT_NO;
	}
	public String getBANK_CUST_NO() {
		return BANK_CUST_NO;
	}
	public void setBANK_CUST_NO(String bANK_CUST_NO) {
		BANK_CUST_NO = bANK_CUST_NO;
	}
	public String getSIGN_STAT() {
		return SIGN_STAT;
	}
	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public JSONArray getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(JSONArray dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	public List<SignCustBusiListDTO> getBUSI_LIST() {
		return BUSI_LIST;
	}
	public void setBUSI_LIST(List<SignCustBusiListDTO> bUSI_LIST) {
		BUSI_LIST = bUSI_LIST;
	}
	public long getBUSI_NUM() {
		return BUSI_NUM;
	}
	public void setBUSI_NUM(long bUSI_NUM) {
		BUSI_NUM = bUSI_NUM;
	}
}
