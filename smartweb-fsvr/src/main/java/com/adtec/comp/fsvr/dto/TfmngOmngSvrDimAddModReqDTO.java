package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngOmngSvrDimAddModReqDTO {
	private String TRAN_TP;
	private String BUSI_NO;
	private String ENTR_NO;
	private String CHNL_NO;
	private String LEGA_NO;
	private String TRAN_CODE;
	private String DEF_VAL;
	private String DIM_DESC;
	private String FILE_SVR_ID;
	private String STAT;
	private List<TfmngOmngSvrDimAddModListReqDTO> LIST = new ArrayList<TfmngOmngSvrDimAddModListReqDTO>();
	public String getTRAN_TP() {
		return TRAN_TP;
	}
	public void setTRAN_TP(String tRAN_TP) {
		TRAN_TP = tRAN_TP;
	}
	public String getBUSI_NO() {
		return BUSI_NO;
	}
	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}
	public String getENTR_NO() {
		return ENTR_NO;
	}
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	public String getCHNL_NO() {
		return CHNL_NO;
	}
	public void setCHNL_NO(String cHNL_NO) {
		CHNL_NO = cHNL_NO;
	}
	public String getLEGA_NO() {
		return LEGA_NO;
	}
	public void setLEGA_NO(String lEGA_NO) {
		LEGA_NO = lEGA_NO;
	}
	public String getTRAN_CODE() {
		return TRAN_CODE;
	}
	public void setTRAN_CODE(String tRAN_CODE) {
		TRAN_CODE = tRAN_CODE;
	}
	public String getDEF_VAL() {
		return DEF_VAL;
	}
	public void setDEF_VAL(String dEF_VAL) {
		DEF_VAL = dEF_VAL;
	}
	
	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}
	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public List<TfmngOmngSvrDimAddModListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngOmngSvrDimAddModListReqDTO> lIST) {
		LIST = lIST;
	}
	public String getDIM_DESC() {
		return DIM_DESC;
	}
	public void setDIM_DESC(String dIM_DESC) {
		DIM_DESC = dIM_DESC;
	}
	
	
	
}
