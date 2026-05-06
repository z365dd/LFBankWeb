package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaLegaQryResDTO {
	private long NUM;
	private List<TParaLegaQryListDTO> LEGA_LIST = new ArrayList<TParaLegaQryListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaLegaQryListDTO> getLEGA_LIST() {
		return LEGA_LIST;
	}
	public void setLEGA_LIST(List<TParaLegaQryListDTO> lEGA_LIST) {
		LEGA_LIST = lEGA_LIST;
	}
}
