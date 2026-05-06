package com.adtec.comp.fsvr.dto;

public class TFmngTranPushReqDTO {
	private String FILE_TRANS_TP;
	private String FILE_SET_SEQ;
	private String PUB_FILE_PATH;
	private String FILE_SVR_ID; 

	public String getFILE_TRANS_TP() {
		return FILE_TRANS_TP;
	}

	public void setFILE_TRANS_TP(String fILE_TRANS_TP) {
		FILE_TRANS_TP = fILE_TRANS_TP;
	}


	public String getFILE_SET_SEQ() {
		return FILE_SET_SEQ;
	}

	public void setFILE_SET_SEQ(String fILE_SET_SEQ) {
		FILE_SET_SEQ = fILE_SET_SEQ;
	}

	public String getPUB_FILE_PATH() {
		return PUB_FILE_PATH;
	}

	public void setPUB_FILE_PATH(String pUB_FILE_PATH) {
		PUB_FILE_PATH = pUB_FILE_PATH;
	}


	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}

	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}

	
}
