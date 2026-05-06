package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaTlrQryResDTO {
	private long NUM;
	private List<TParaTlrQryListDTO> TLR_LIST = new ArrayList<TParaTlrQryListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaTlrQryListDTO> getTLR_LIST() {
		return TLR_LIST;
	}
	public void setTLR_LIST(List<TParaTlrQryListDTO> tLR_LIST) {
		TLR_LIST = tLR_LIST;
	}
}
