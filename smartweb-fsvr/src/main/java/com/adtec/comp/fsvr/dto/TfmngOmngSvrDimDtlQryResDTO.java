package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngOmngSvrDimDtlQryResDTO {
	private String BUSI_NO;
	private String ENTR_NO;
	private String CHNL_NO;
	private String LEGA_NO;
	private String TRAN_CODE;
	private String DEF_VAL;
	private String DIM_DESC;
	private String  FILE_SVR_NO;
	private List<TfmngOmngSvrDimDtlQryListResDTO> LIST = new ArrayList<TfmngOmngSvrDimDtlQryListResDTO>();	
	private String COMP_NO;
	private String COMP_NAME;
	
	private String STAT;
	
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public String getFILE_SVR_NO() {
		return FILE_SVR_NO;
	}
	public void setFILE_SVR_NO(String fILE_SVR_NO) {
		FILE_SVR_NO = fILE_SVR_NO;
	}
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getCOMP_NAME() {
		return COMP_NAME;
	}
	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}
	public List<TfmngOmngSvrDimDtlQryListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngOmngSvrDimDtlQryListResDTO> lIST) {
		LIST = lIST;
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
	public String getDIM_DESC() {
		return DIM_DESC;
	}
	public void setDIM_DESC(String dIM_DESC) {
		DIM_DESC = dIM_DESC;
	}
	
	
	
}
