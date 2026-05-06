package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngTranUploadApplyResDTO  {
	private String FILE_SET_SEQ;
	private String IP;
	private long FILE_NUM;
	private String PORT; 
	
	
	
	private List<TfmngTranUploadApplyListResDTO> LIST = new ArrayList<TfmngTranUploadApplyListResDTO>();
	
	
	
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}
	public String getFILE_SET_SEQ() {
		return FILE_SET_SEQ;
	}
	public void setFILE_SET_SEQ(String fILE_SET_SEQ) {
		FILE_SET_SEQ = fILE_SET_SEQ;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String pORT) {
		PORT = pORT;
	}
	public List<TfmngTranUploadApplyListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngTranUploadApplyListResDTO> lIST) {
		LIST = lIST;
	}
	
	
}
