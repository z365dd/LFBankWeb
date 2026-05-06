package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class ParaBusiQryResDTO {
	private long BUSI_NUM;
	private List<ParaBusiQryListDTO> BUSI_LIST = new ArrayList<ParaBusiQryListDTO>();
	public long getBUSI_NUM() {
		return BUSI_NUM;
	}
	public void setBUSI_NUM(long bUSI_NUM) {
		BUSI_NUM = bUSI_NUM;
	}
	public List<ParaBusiQryListDTO> getBUSI_LIST() {
		return BUSI_LIST;
	}
	public void setBUSI_LIST(List<ParaBusiQryListDTO> bUSI_LIST) {
		BUSI_LIST = bUSI_LIST;
	}
}
