package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTParaCompParaListResDTO {
	private String COMP_NO;
	private String COMP_NAME;
	private long KEY_NUM;
	private String ACTION;
	private List<TParaCompDealTParaCompParaList1ResDTO> DYN_LIST = new ArrayList<TParaCompDealTParaCompParaList1ResDTO>();

	
	public long getKEY_NUM() {
		return KEY_NUM;
	}

	public void setKEY_NUM(long kEY_NUM) {
		KEY_NUM = kEY_NUM;
	}

	public String getACTION() {
		return ACTION;
	}

	public void setACTION(String aCTION) {
		ACTION = aCTION;
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

	public List<TParaCompDealTParaCompParaList1ResDTO> getDYN_LIST() {
		return DYN_LIST;
	}

	public void setDYN_LIST(List<TParaCompDealTParaCompParaList1ResDTO> dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
}
