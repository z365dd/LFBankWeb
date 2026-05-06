package com.adtec.comp.pub.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngTranGetReqDTO {
	private String FILE_TRANS_TP;
	private String PUB_FILE_PATH;
	private String DIM_FLG;
	private String DEF_VAL;
	private long FILE_NUM;
	
	private List<TfmngTranGetListReqDTO> LIST = new ArrayList<TfmngTranGetListReqDTO>();
	private List<TfmngTranGetTListReqDTO> TNT_LIST = new ArrayList<TfmngTranGetTListReqDTO>();
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
	public String getDIM_FLG() {
		return DIM_FLG;
	}
	public void setDIM_FLG(String dIM_FLG) {
		DIM_FLG = dIM_FLG;
	}
	public String getDEF_VAL() {
		return DEF_VAL;
	}
	public void setDEF_VAL(String dEF_VAL) {
		DEF_VAL = dEF_VAL;
	}
	
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}
	public List<TfmngTranGetListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngTranGetListReqDTO> lIST) {
		LIST = lIST;
	}
	public List<TfmngTranGetTListReqDTO> getTNT_LIST() {
		return TNT_LIST;
	}
	public void setTNT_LIST(List<TfmngTranGetTListReqDTO> tNT_LIST) {
		TNT_LIST = tNT_LIST;
	}
	
	
}
