package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TFmngTranPushResDTO {
	//请求流水号
	private String REQ_SEQ;
	
	//返回文件信息
	private List<TfmngTranUploadAndDownloadListResDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListResDTO>();


	public String getREQ_SEQ() {
		return REQ_SEQ;
	}

	public void setREQ_SEQ(String rEQ_SEQ) {
		REQ_SEQ = rEQ_SEQ;
	}

	public List<TfmngTranUploadAndDownloadListResDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<TfmngTranUploadAndDownloadListResDTO> lIST) {
		LIST = lIST;
	}
	
}
