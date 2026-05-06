package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TFmngTranDownloadResDTO {
	private String FILE_SET_SEQ;
	private String SRC_PUB_FILE_PATH;
	private long FILE_NUM;
	private List<TfmngTranDownloadListResDTO> LIST = new ArrayList<TfmngTranDownloadListResDTO>();
	public String getFILE_SET_SEQ() {
		return FILE_SET_SEQ;
	}
	public void setFILE_SET_SEQ(String fILE_SET_SEQ) {
		FILE_SET_SEQ = fILE_SET_SEQ;
	}
	public String getSRC_PUB_FILE_PATH() {
		return SRC_PUB_FILE_PATH;
	}
	public void setSRC_PUB_FILE_PATH(String sRC_PUB_FILE_PATH) {
		SRC_PUB_FILE_PATH = sRC_PUB_FILE_PATH;
	}
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}
	public List<TfmngTranDownloadListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngTranDownloadListResDTO> lIST) {
		LIST = lIST;
	}
	
}
