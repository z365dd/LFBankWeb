package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignEntrItemQryResDTO {
	private String BUSI_NO;
	private String SUB_BUSI_NO;
	private String ENTR_NO;
	private long NUM;
	private List<SignEntrItemQryListDTO> LIST = new ArrayList<SignEntrItemQryListDTO>();
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
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<SignEntrItemQryListDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<SignEntrItemQryListDTO> lIST) {
		LIST = lIST;
	}
}
