package com.adtec.comp.ctrl.dto;

public class TParaCompDealTCtrlSvcCtrlReqDTO {	
	private String COMP_NO;
	private String COMP_NAME;
	private String SVC_CODE;
	private String SVC_DESC;
	private String STAT;
	private String OPER_TP;
	
	public String getCOMP_NAME() {
		return COMP_NAME;
	}
	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}
	public String getSVC_DESC() {
		return SVC_DESC;
	}
	public void setSVC_DESC(String sVC_DESC) {
		SVC_DESC = sVC_DESC;
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
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public String getOPER_TP() {
		return OPER_TP;
	}
	public void setOPER_TP(String oPER_TP) {
		OPER_TP = oPER_TP;
	}
	
}
