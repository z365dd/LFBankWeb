package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class ParaBusiModReqDTO {
	private long BUSI_NUM;
	private List<ParaBusiModListDTO> BUSI_LIST = new ArrayList<ParaBusiModListDTO>();
	public long getBUSI_NUM() {
		return BUSI_NUM;
	}
	public void setBUSI_NUM(long bUSI_NUM) {
		BUSI_NUM = bUSI_NUM;
	}
	public List<ParaBusiModListDTO> getBUSI_LIST() {
		return BUSI_LIST;
	}
	public void setBUSI_LIST(List<ParaBusiModListDTO> bUSI_LIST) {
		BUSI_LIST = bUSI_LIST;
	}
}
