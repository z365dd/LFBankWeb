package com.adtec.comp.sign.dto;

import com.alibaba.fastjson.JSONArray;

public class SignEntrSignReqDTO {
	private long ENTR_NUM;
	private JSONArray ENTR_LIST;
	public long getENTR_NUM() {
		return ENTR_NUM;
	}
	public void setENTR_NUM(long eNTR_NUM) {
		ENTR_NUM = eNTR_NUM;
	}
	public JSONArray getENTR_LIST() {
		return ENTR_LIST;
	}
	public void setENTR_LIST(JSONArray eNTR_LIST) {
		ENTR_LIST = eNTR_LIST;
	}
}
