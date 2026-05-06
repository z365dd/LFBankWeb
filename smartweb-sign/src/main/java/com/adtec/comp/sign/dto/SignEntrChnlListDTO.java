package com.adtec.comp.sign.dto;

import java.io.Serializable;

public class SignEntrChnlListDTO implements Serializable {
	private String CHNL_NO;
	private String CHNL_NAME;
	private String SIGN_FLG;
	public String getCHNL_NO() {
		return CHNL_NO;
	}
	public void setCHNL_NO(String cHNL_NO) {
		CHNL_NO = cHNL_NO;
	}
	public String getCHNL_NAME() {
		return CHNL_NAME;
	}
	public void setCHNL_NAME(String cHNL_NAME) {
		CHNL_NAME = cHNL_NAME;
	}
	public String getSIGN_FLG() {
		return SIGN_FLG;
	}
	public void setSIGN_FLG(String sIGN_FLG) {
		SIGN_FLG = sIGN_FLG;
	}
}
