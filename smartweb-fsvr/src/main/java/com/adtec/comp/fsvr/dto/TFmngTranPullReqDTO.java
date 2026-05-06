package com.adtec.comp.fsvr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TFmngTranPullReqDTO {
	private String FILE_TRANS_TP;
	private String PUB_FILE_PATH;
	private long FILE_NUM;
	private String FILE_SVR_ID; 
	
	private List<TfmngTranUploadAndDownloadListReqDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListReqDTO>();

	public String getFILE_TRANS_TP() {
		return FILE_TRANS_TP;
	}

	public void setFILE_TRANS_TP(String fILE_TRANS_TP) {
		FILE_TRANS_TP = fILE_TRANS_TP;
	}


	public String getPUB_FILE_PATH() {
		return PUB_FILE_PATH;
	}

	public void setPUB_FILE_PATH(String pUB_FILE_PATH) {
		PUB_FILE_PATH = pUB_FILE_PATH;
	}

	public long getFILE_NUM() {
		return FILE_NUM;
	}

	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}

	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}

	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}

	public List<TfmngTranUploadAndDownloadListReqDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<TfmngTranUploadAndDownloadListReqDTO> lIST) {
		LIST = lIST;
	}
	
}
