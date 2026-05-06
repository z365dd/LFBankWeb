package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTParaCompParaReqDTO {
	private String OPER_TP;
	private String COMP_NO;
	private String COMP_NAME;
	private List<TParaCompDealTParaCompParaListReqDTO> DYN_LIST = new ArrayList<TParaCompDealTParaCompParaListReqDTO>();
	public String getOPER_TP() {
		return OPER_TP;
	}
	public void setOPER_TP(String oPER_TP) {
		OPER_TP = oPER_TP;
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
	public List<TParaCompDealTParaCompParaListReqDTO> getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(List<TParaCompDealTParaCompParaListReqDTO> dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	
}
