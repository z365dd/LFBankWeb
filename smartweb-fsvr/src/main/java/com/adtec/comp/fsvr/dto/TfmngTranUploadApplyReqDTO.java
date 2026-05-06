package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngTranUploadApplyReqDTO {
	private String FILE_TRANS_TP;
	private long FILE_NUM;
	
	private List<TfmngTranUploadApplyListReqDTO> LIST = new ArrayList<TfmngTranUploadApplyListReqDTO>();
	private List<TfmngTranUploadApplyTntListReqDTO> TNT_LIST = new ArrayList<TfmngTranUploadApplyTntListReqDTO>();
	
	
	public List<TfmngTranUploadApplyTntListReqDTO> getTNT_LIST() {
		return TNT_LIST;
	}
	public void setTNT_LIST(List<TfmngTranUploadApplyTntListReqDTO> tNT_LIST) {
		TNT_LIST = tNT_LIST;
	}
	
	public List<TfmngTranUploadApplyListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngTranUploadApplyListReqDTO> lIST) {
		LIST = lIST;
	}
	public String getFILE_TRANS_TP() {
		return FILE_TRANS_TP;
	}
	public void setFILE_TRANS_TP(String fILE_TRANS_TP) {
		FILE_TRANS_TP = fILE_TRANS_TP;
	}
	
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}
	
	
}
