package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignEntrQryResDTO {
	private long ENTR_NUM;
	private List<SignEntrListDTO> ENTR_LIST = new ArrayList<SignEntrListDTO>();
	public long getENTR_NUM() {
		return ENTR_NUM;
	}
	public void setENTR_NUM(long eNTR_NUM) {
		ENTR_NUM = eNTR_NUM;
	}
	public List<SignEntrListDTO> getENTR_LIST() {
		return ENTR_LIST;
	}
	public void setENTR_LIST(List<SignEntrListDTO> eNTR_LIST) {
		ENTR_LIST = eNTR_LIST;
	}
}
