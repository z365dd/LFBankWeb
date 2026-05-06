package com.adtec.comp.fsvr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TfmngTranDownloadApplyResDTO implements Serializable {
	private String IP;
	private long FILE_NUM;
	private String PORT; 
	
	
	
	private List<TfmngTranDownloadApplyListResDTO> LIST = new ArrayList<TfmngTranDownloadApplyListResDTO>();
	
	
	
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
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
	public List<TfmngTranDownloadApplyListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngTranDownloadApplyListResDTO> lIST) {
		LIST = lIST;
	}
	
	
}
