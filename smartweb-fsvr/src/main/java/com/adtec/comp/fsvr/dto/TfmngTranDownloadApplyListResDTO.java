package com.adtec.comp.fsvr.dto;

import java.io.Serializable;

public class TfmngTranDownloadApplyListResDTO implements Serializable {
	private String RMT_FULL_FILE_PATH;
	private String RMT_FILE_NAME;
	private String LOCAL_FULL_FILE_PATH;
	private String LOCAL_FILE_NAME;
	
	public String getLOCAL_FULL_FILE_PATH() {
		return LOCAL_FULL_FILE_PATH;
	}
	public void setLOCAL_FULL_FILE_PATH(String lOCAL_FULL_FILE_PATH) {
		LOCAL_FULL_FILE_PATH = lOCAL_FULL_FILE_PATH;
	}
	public String getLOCAL_FILE_NAME() {
		return LOCAL_FILE_NAME;
	}
	public void setLOCAL_FILE_NAME(String lOCAL_FILE_NAME) {
		LOCAL_FILE_NAME = lOCAL_FILE_NAME;
	}
	public String getRMT_FULL_FILE_PATH() {
		return RMT_FULL_FILE_PATH;
	}
	public void setRMT_FULL_FILE_PATH(String rMT_FULL_FILE_PATH) {
		RMT_FULL_FILE_PATH = rMT_FULL_FILE_PATH;
	}
	public String getRMT_FILE_NAME() {
		return RMT_FILE_NAME;
	}
	public void setRMT_FILE_NAME(String rMT_FILE_NAME) {
		RMT_FILE_NAME = rMT_FILE_NAME;
	}
	
	
}
