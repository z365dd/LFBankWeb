package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaBrchQryResDTO {
	private long NUM;
	private List<TParaBrchQryListResDTO> BRCH_LIST = new ArrayList<TParaBrchQryListResDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaBrchQryListResDTO> getBRCH_LIST() {
		return BRCH_LIST;
	}
	public void setBRCH_LIST(List<TParaBrchQryListResDTO> bRCH_LIST) {
		BRCH_LIST = bRCH_LIST;
	}
}
