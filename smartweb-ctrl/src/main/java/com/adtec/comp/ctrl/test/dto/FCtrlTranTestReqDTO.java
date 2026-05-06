package com.adtec.comp.ctrl.test.dto;

import java.util.ArrayList;
import java.util.List;

import com.adtec.comm.dto.head.AppHeadAUListReqDTO;

public class FCtrlTranTestReqDTO {
	private String COMP_NO;
	private String SVC_CODE;
	private String CHNL_NO;
	private String BUSI_NO;
	private String ENTR_NO;
	private String LEGA_NO;
	private String BRCH_NO;
	private String TLR_LVL;
	private String TNT_NO;
	private List<AppHeadAUListReqDTO> tlrList = new ArrayList<AppHeadAUListReqDTO>();
	
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getCHNL_NO() {
		return CHNL_NO;
	}
	public void setCHNL_NO(String cHNL_NO) {
		CHNL_NO = cHNL_NO;
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
	public String getLEGA_NO() {
		return LEGA_NO;
	}
	public void setLEGA_NO(String lEGA_NO) {
		LEGA_NO = lEGA_NO;
	}
	public String getBRCH_NO() {
		return BRCH_NO;
	}
	public void setBRCH_NO(String bRCH_NO) {
		BRCH_NO = bRCH_NO;
	}
	public String getTLR_LVL() {
		return TLR_LVL;
	}
	public void setTLR_LVL(String tLR_LVL) {
		TLR_LVL = tLR_LVL;
	}
	public String getTNT_NO() {
		return TNT_NO;
	}
	public void setTNT_NO(String tNT_NO) {
		TNT_NO = tNT_NO;
	}
	public List<AppHeadAUListReqDTO> getTlrList() {
		return tlrList;
	}
	public void setTlrList(List<AppHeadAUListReqDTO> tlrList) {
		this.tlrList = tlrList;
	}
}
