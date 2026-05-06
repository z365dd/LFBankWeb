package com.adtec.comp.ctrl.test.dto;

import net.sf.json.JSONObject;

public class FCtrlTranExmReqDTO {
	private String SUB_SVC_CODE;
	private double TRAN_AMT;
	private JSONObject DYN_DATA;//私有维度
	
	public String getSUB_SVC_CODE() {
		return SUB_SVC_CODE;
	}
	public void setSUB_SVC_CODE(String sUB_SVC_CODE) {
		SUB_SVC_CODE = sUB_SVC_CODE;
	}
	public double getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(double tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
	public JSONObject getDYN_DATA() {
		return DYN_DATA;
	}
	public void setDYN_DATA(JSONObject dYN_DATA) {
		DYN_DATA = dYN_DATA;
	}
}
