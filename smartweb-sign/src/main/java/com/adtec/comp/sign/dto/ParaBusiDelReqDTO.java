package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class ParaBusiDelReqDTO {
	private long BUSI_NUM;
	private List<ParaBusiDelListDTO> BUSI_LIST = new ArrayList<ParaBusiDelListDTO>();
	public long getBUSI_NUM() {
		return BUSI_NUM;
	}
	public void setBUSI_NUM(long bUSI_NUM) {
		BUSI_NUM = bUSI_NUM;
	}
	public List<ParaBusiDelListDTO> getBUSI_LIST() {
		return BUSI_LIST;
	}
	public void setBUSI_LIST(List<ParaBusiDelListDTO> bUSI_LIST) {
		BUSI_LIST = bUSI_LIST;
	}
}
