package com.adtec.comp.ctrl.test.dto;

public class FCtrlTranAuthChkListDTO {
	private int ORDER_NO;
	private String TP_DESC;
	private String EXPR_DESC;
	private String STAT;
	private String RES_CODE;
	private String RES_MSG;
	
	public int getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(int oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public String getTP_DESC() {
		return TP_DESC;
	}
	public void setTP_DESC(String tP_DESC) {
		TP_DESC = tP_DESC;
	}
	public String getEXPR_DESC() {
		return EXPR_DESC;
	}
	public void setEXPR_DESC(String eXPR_DESC) {
		EXPR_DESC = eXPR_DESC;
	}
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public String getRES_MSG() {
		return RES_MSG;
	}
	public void setRES_MSG(String rES_MSG) {
		RES_MSG = rES_MSG;
	}
	public String getRES_CODE() {
		return RES_CODE;
	}
	public void setRES_CODE(String rES_CODE) {
		RES_CODE = rES_CODE;
	}
}
