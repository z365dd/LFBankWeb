package com.adtec.comp.ctrl.dto;

public class CtrlSubSvcDelReqDTO {
	/*记录个数*/
	private int NUM;
	/*模型号*/
	private String COMP_NO;
	/*服务码*/
	private String SVC_CODE;
	/*子服务码*/
	private String SUB_SVC_CODE;
	
	public int getNUM() {
		return NUM;
	}
	public void setNUM(int nUM) {
		NUM = nUM;
	}
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getSUB_SVC_CODE() {
		return SUB_SVC_CODE;
	}
	public void setSUB_SVC_CODE(String sUB_SVC_CODE) {
		SUB_SVC_CODE = sUB_SVC_CODE;
	}
}
