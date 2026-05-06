package com.adtec.comp.ctrl.dto;

public class CtrlSubSvcAddReqDTO {
	/*记录个数*/
	private int NUM;
	/*模型号*/
	private String COMP_NO;
	/*服务码*/
	private String SVC_CODE;
	/*子服务码*/
	private String SUB_SVC_CODE;
	/*子服务码名称*/
	private String SUB_SVC_DESC;
	
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
	public String getSUB_SVC_CODE() {
		return SUB_SVC_CODE;
	}
	public void setSUB_SVC_CODE(String sUB_SVC_CODE) {
		SUB_SVC_CODE = sUB_SVC_CODE;
	}
	public String getSUB_SVC_DESC() {
		return SUB_SVC_DESC;
	}
	public void setSUB_SVC_DESC(String sUB_SVC_DESC) {
		SUB_SVC_DESC = sUB_SVC_DESC;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
}
