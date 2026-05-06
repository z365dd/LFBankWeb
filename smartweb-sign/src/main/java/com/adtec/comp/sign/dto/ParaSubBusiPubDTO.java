package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class ParaSubBusiPubDTO {
	
	private String BUSI_NO;
	private String BUSI_NAME;
	private long SUB_BUSI_NUM;
	private List<ParaSubBusiPubListDTO> SUB_BUSI_LIST = new ArrayList<ParaSubBusiPubListDTO>();
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
	public long getSUB_BUSI_NUM() {
		return SUB_BUSI_NUM;
	}
	public void setSUB_BUSI_NUM(long sUB_BUSI_NUM) {
		SUB_BUSI_NUM = sUB_BUSI_NUM;
	}
	public List<ParaSubBusiPubListDTO> getSUB_BUSI_LIST() {
		return SUB_BUSI_LIST;
	}
	public void setSUB_BUSI_LIST(List<ParaSubBusiPubListDTO> sUB_BUSI_LIST) {
		SUB_BUSI_LIST = sUB_BUSI_LIST;
	}
}
