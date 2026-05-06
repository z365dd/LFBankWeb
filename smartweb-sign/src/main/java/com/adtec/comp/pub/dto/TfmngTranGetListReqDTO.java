package com.adtec.comp.pub.dto;

public class TfmngTranGetListReqDTO {
	private String SUB_FILE_PATH;
	private String FILE_NAME;
	private String ALIAS_FILE_NAME;
	private String FILE_MD5;
	public String getSUB_FILE_PATH() {
		return SUB_FILE_PATH;
	}
	public void setSUB_FILE_PATH(String sUB_FILE_PATH) {
		SUB_FILE_PATH = sUB_FILE_PATH;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getALIAS_FILE_NAME() {
		return ALIAS_FILE_NAME;
	}
	public void setALIAS_FILE_NAME(String aLIAS_FILE_NAME) {
		ALIAS_FILE_NAME = aLIAS_FILE_NAME;
	}
	public String getFILE_MD5() {
		return FILE_MD5;
	}
	public void setFILE_MD5(String fILE_MD5) {
		FILE_MD5 = fILE_MD5;
	}
	
}
