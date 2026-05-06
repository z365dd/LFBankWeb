package com.adtec.comp.sign.dto;

import com.alibaba.fastjson.JSONArray;

public class SignCustCanReqDTO {
	private long ACCT_NUM;
	private JSONArray ACCT_LIST;
	public long getACCT_NUM() {
		return ACCT_NUM;
	}
	public void setACCT_NUM(long aCCT_NUM) {
		ACCT_NUM = aCCT_NUM;
	}
	public JSONArray getACCT_LIST() {
		return ACCT_LIST;
	}
	public void setACCT_LIST(JSONArray aCCT_LIST) {
		ACCT_LIST = aCCT_LIST;
	}
}
