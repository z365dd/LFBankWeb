package com.adtec.comp.fsvr.dto;

import java.io.Serializable;

public class TfmngTranUploadAndDownloadListResDTO implements Serializable {
	private String FULL_FILE_PATH;
	private long FILE_SIZE;
	private String FILE_MD5;
	/**
	 * @return the fULL_FILE_PATH
	 */
	public String getFULL_FILE_PATH() {
		return FULL_FILE_PATH;
	}
	/**
	 * @param fULL_FILE_PATH the fULL_FILE_PATH to set
	 */
	public void setFULL_FILE_PATH(String fULL_FILE_PATH) {
		FULL_FILE_PATH = fULL_FILE_PATH;
	}
	/**
	 * @return the fILE_SIZE
	 */
	public long getFILE_SIZE() {
		return FILE_SIZE;
	}
	/**
	 * @param fILE_SIZE the fILE_SIZE to set
	 */
	public void setFILE_SIZE(long fILE_SIZE) {
		FILE_SIZE = fILE_SIZE;
	}
	/**
	 * @return the fILE_MD5
	 */
	public String getFILE_MD5() {
		return FILE_MD5;
	}
	/**
	 * @param fILE_MD5 the fILE_MD5 to set
	 */
	public void setFILE_MD5(String fILE_MD5) {
		FILE_MD5 = fILE_MD5;
	}
	
	
}
