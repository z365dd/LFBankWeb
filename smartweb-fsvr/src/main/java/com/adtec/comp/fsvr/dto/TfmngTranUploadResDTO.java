package com.adtec.comp.fsvr.dto;

public class TfmngTranUploadResDTO {
	//文件流水号
	private String FILE_SET_SEQ;
	//请求流水号
	private String REQ_SEQ;
	/*交易状态:S-交易成功、F-交易失败、T-交易超时*/
	private String TRAN_STAT;
	//错误信息
	private String TRAN_MSG;
	
	public String getTRAN_MSG() {
		return TRAN_MSG;
	}

	public void setTRAN_MSG(String tRAN_MSG) {
		TRAN_MSG = tRAN_MSG;
	}

	public String getTRAN_STAT() {
		return TRAN_STAT;
	}

	public void setTRAN_STAT(String tRAN_STAT) {
		TRAN_STAT = tRAN_STAT;
	}

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
