package com.adtec.comp.fsvr.dto;

public class TFmngTranPullResDTO {
	//文件流水号
	private String FILE_SET_SEQ;
	//请求流水号
	private String REQ_SEQ;

	public String getREQ_SEQ() {
		return REQ_SEQ;
	}

	public void setREQ_SEQ(String rEQ_SEQ) {
		REQ_SEQ = rEQ_SEQ;
	}

	public String getFILE_SET_SEQ() {
		return FILE_SET_SEQ;
	}

	public void setFILE_SET_SEQ(String fILE_SET_SEQ) {
		FILE_SET_SEQ = fILE_SET_SEQ;
	}
	
}
