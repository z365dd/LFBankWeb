package com.adtec.comp.fsvr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TfmngTranUploadAndDownloadReqDTO implements Serializable {
	private String FILE_TRANS_TP;
	private String LOCAL_FULL_FILE_PATH;
	private String RMT_FULL_FILE_PATH;
	private long FILE_NUM;
	private String FILE_SVR_ID; 
	
	/*private String DIM_FLG;
	private String DEF_VAL;
	
	private String BUSI_NO;
	private String ENTR_NO;
	private String CHNL_NO;
	private String LEGA_NO;
	private String TRAN_CODE;*/
	
	
	private List<TfmngTranUploadAndDownloadListReqDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListReqDTO>();
	
	
	
	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}
	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}
	
	public String getFILE_TRANS_TP() {
		return FILE_TRANS_TP;
	}
	public void setFILE_TRANS_TP(String fILE_TRANS_TP) {
		FILE_TRANS_TP = fILE_TRANS_TP;
	}
	
	public String getLOCAL_FULL_FILE_PATH() {
		return LOCAL_FULL_FILE_PATH;
	}
	public void setLOCAL_FULL_FILE_PATH(String lOCAL_FULL_FILE_PATH) {
		LOCAL_FULL_FILE_PATH = lOCAL_FULL_FILE_PATH;
	}
	public String getRMT_FULL_FILE_PATH() {
		return RMT_FULL_FILE_PATH;
	}
	public void setRMT_FULL_FILE_PATH(String rMT_FULL_FILE_PATH) {
		RMT_FULL_FILE_PATH = rMT_FULL_FILE_PATH;
	}
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}
	public List<TfmngTranUploadAndDownloadListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngTranUploadAndDownloadListReqDTO> lIST) {
		LIST = lIST;
	}
	
	
}
