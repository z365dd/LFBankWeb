package com.adtec.comp.sign.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class SignEntrListDTO implements Serializable {
	private String ENTR_NO;
	private String ENTR_NAME;
	private String PRT_NAME;
	private String CERT_TP;
	private String CERT_NO;
	private String CTCT_PER_NAME;
	private String PER_TEL_NO;
	private String LEGA_NO;
	private String LEGA_NAME;
	private String ENTR_TEL_NO;
	private String COMM_ADDR;
	private String POST_ECD;
	private String EMAIL_ADDR;
	private String OPEN_STAT;
	private String SIGN_STAT;
	private JSONArray DYN_LIST;
	private long NUM;
	private String ACTION;
	private long BUSI_NUM;
	private List<SignEntrBusiListDTO> BUSI_LIST = new ArrayList<SignEntrBusiListDTO>();
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
	public String getPRT_NAME() {
		return PRT_NAME;
	}
	public void setPRT_NAME(String pRT_NAME) {
		PRT_NAME = pRT_NAME;
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
	public String getCTCT_PER_NAME() {
		return CTCT_PER_NAME;
	}
	public void setCTCT_PER_NAME(String cTCT_PER_NAME) {
		CTCT_PER_NAME = cTCT_PER_NAME;
	}
	public String getPER_TEL_NO() {
		return PER_TEL_NO;
	}
	public void setPER_TEL_NO(String pER_TEL_NO) {
		PER_TEL_NO = pER_TEL_NO;
	}
	public String getLEGA_NO() {
		return LEGA_NO;
	}
	public void setLEGA_NO(String lEGA_NO) {
		LEGA_NO = lEGA_NO;
	}
	public String getLEGA_NAME() {
		return LEGA_NAME;
	}
	public void setLEGA_NAME(String lEGA_NAME) {
		LEGA_NAME = lEGA_NAME;
	}
	public String getENTR_TEL_NO() {
		return ENTR_TEL_NO;
	}
	public void setENTR_TEL_NO(String eNTR_TEL_NO) {
		ENTR_TEL_NO = eNTR_TEL_NO;
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
	public String getOPEN_STAT() {
		return OPEN_STAT;
	}
	public void setOPEN_STAT(String oPEN_STAT) {
		OPEN_STAT = oPEN_STAT;
	}
	public String getSIGN_STAT() {
		return SIGN_STAT;
	}
	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}
	public JSONArray getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(JSONArray dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	public long getBUSI_NUM() {
		return BUSI_NUM;
	}
	public void setBUSI_NUM(long bUSI_NUM) {
		BUSI_NUM = bUSI_NUM;
	}
	public List<SignEntrBusiListDTO> getBUSI_LIST() {
		return BUSI_LIST;
	}
	public void setBUSI_LIST(List<SignEntrBusiListDTO> bUSI_LIST) {
		BUSI_LIST = bUSI_LIST;
	}
}
