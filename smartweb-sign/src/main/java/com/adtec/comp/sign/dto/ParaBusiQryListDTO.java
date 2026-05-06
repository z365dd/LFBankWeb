package com.adtec.comp.sign.dto;

public class ParaBusiQryListDTO {
	/*组件号*/
	private String COMP_NO;
	/*组件名*/
	private String COMP_NAME;
	/*业务编号*/
	private String BUSI_NO;
	/*业务名称*/
	private String BUSI_NAME;
	/*开通状态*/
	private String OPEN_STAT;
	/*法人号*/
	private String LEGA_NO;
	/*签约标志*/
	private String SIGN_FLG;
	/*是否有子业务*/
	private String FLG;
	/*操作*/
	private String ACTION;
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
	public String getOPEN_STAT() {
		return OPEN_STAT;
	}
	public void setOPEN_STAT(String oPEN_STAT) {
		OPEN_STAT = oPEN_STAT;
	}
	public String getLEGA_NO() {
		return LEGA_NO;
	}
	public void setLEGA_NO(String lEGA_NO) {
		LEGA_NO = lEGA_NO;
	}
	public String getSIGN_FLG() {
		return SIGN_FLG;
	}
	public void setSIGN_FLG(String sIGN_FLG) {
		SIGN_FLG = sIGN_FLG;
	}
	public String getFLG() {
		return FLG;
	}
	public void setFLG(String fLG) {
		FLG = fLG;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
}
