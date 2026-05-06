package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignCustBusiListResDTO {
	private String BUSI_NO;
	private String ENTR_NO;
	private long SUB_BUSI_NUM;
	private List<SignCustSubListResDTO> SUB_BUSI_LIST = new ArrayList<SignCustSubListResDTO>();
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
	public long getSUB_BUSI_NUM() {
		return SUB_BUSI_NUM;
	}
	public void setSUB_BUSI_NUM(long sUB_BUSI_NUM) {
		SUB_BUSI_NUM = sUB_BUSI_NUM;
	}
	public List<SignCustSubListResDTO> getSUB_BUSI_LIST() {
		return SUB_BUSI_LIST;
	}
	public void setSUB_BUSI_LIST(List<SignCustSubListResDTO> sUB_BUSI_LIST) {
		SUB_BUSI_LIST = sUB_BUSI_LIST;
	}
}
