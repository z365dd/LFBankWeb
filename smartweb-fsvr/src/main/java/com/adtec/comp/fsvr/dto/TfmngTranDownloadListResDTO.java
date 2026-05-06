package com.adtec.comp.fsvr.dto;

import java.io.Serializable;

public class TfmngTranDownloadListResDTO implements Serializable {
	private String SRC_SUB_FILE_PATH;
	private String SRC_FILE_NAME;
	private String ALIAS_FILE_NAME;
	private String FILE_MD5;
	private String FILE_SIZE;
	private String FULL_FILE_PATH;
	
	
	public String getFULL_FILE_PATH() {
		return FULL_FILE_PATH;
	}
	public void setFULL_FILE_PATH(String fULL_FILE_PATH) {
		FULL_FILE_PATH = fULL_FILE_PATH;
	}
	public String getFILE_SIZE() {
		return FILE_SIZE;
	}
	public void setFILE_SIZE(String fILE_SIZE) {
		FILE_SIZE = fILE_SIZE;
	}
	public String getSRC_SUB_FILE_PATH() {
		return SRC_SUB_FILE_PATH;
	}
	public void setSRC_SUB_FILE_PATH(String sRC_SUB_FILE_PATH) {
		SRC_SUB_FILE_PATH = sRC_SUB_FILE_PATH;
	}
	public String getSRC_FILE_NAME() {
		return SRC_FILE_NAME;
	}
	public void setSRC_FILE_NAME(String sRC_FILE_NAME) {
		SRC_FILE_NAME = sRC_FILE_NAME;
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
