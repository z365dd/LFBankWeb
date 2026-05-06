package com.adtec.comp.sign.dto;

import java.io.Serializable;

public class SignCustCtrlListDTO implements Serializable {
	private String KEY;
	private String KEY_NAME;
	private String KV;
	private String KV_DESC;
	public String getKEY() {
		return KEY;
	}
	public void setKEY(String kEY) {
		KEY = kEY;
	}
	public String getKEY_NAME() {
		return KEY_NAME;
	}
	public void setKEY_NAME(String kEY_NAME) {
		KEY_NAME = kEY_NAME;
	}
	public String getKV() {
		return KV;
	}
	public void setKV(String kV) {
		KV = kV;
	}
	public String getKV_DESC() {
		return KV_DESC;
	}
	public void setKV_DESC(String kV_DESC) {
		KV_DESC = kV_DESC;
	}
	
}
