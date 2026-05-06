package com.adtec.comp.ctrl.dto;

public class CtrlCompAddReqDTO {
	/*序号*/
	private int NUM;
	/*模板号*/
	private String COMP_NO;
	/*模板名称*/
	private String COMP_NAME;
	/*模板类型*/
	private String FLG;
	
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
	public String getCOMP_NAME() {
		return COMP_NAME;
	}
	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}
	public String getFLG() {
		return FLG;
	}
	public void setFLG(String fLG) {
		FLG = fLG;
	}
}
