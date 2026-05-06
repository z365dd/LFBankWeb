package com.adtec.comp.sign.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class SignEntrBusiListDTO implements Serializable {
	private String BUSI_NO;
	private String BUSI_NAME;
	private String SIGN_STAT;
	private JSONArray DYN_LIST;
	private long NUM;
	private long SUB_BUSI_NUM;
	private List<SignEntrSubBusiListDTO> SUB_BUSI_LIST = new ArrayList<SignEntrSubBusiListDTO>();
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
	public long getSUB_BUSI_NUM() {
		return SUB_BUSI_NUM;
	}
	public void setSUB_BUSI_NUM(long sUB_BUSI_NUM) {
		SUB_BUSI_NUM = sUB_BUSI_NUM;
	}
	public List<SignEntrSubBusiListDTO> getSUB_BUSI_LIST() {
		return SUB_BUSI_LIST;
	}
	public void setSUB_BUSI_LIST(List<SignEntrSubBusiListDTO> sUB_BUSI_LIST) {
		SUB_BUSI_LIST = sUB_BUSI_LIST;
	}
}
