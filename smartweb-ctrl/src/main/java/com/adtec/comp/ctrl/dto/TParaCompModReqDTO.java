package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompModReqDTO {
	/*序号*/
	private long NUM;
	private List<TParaCompModListDTO> COMP_LIST = new ArrayList<TParaCompModListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaCompModListDTO> getCOMP_LIST() {
		return COMP_LIST;
	}
	public void setCOMP_LIST(List<TParaCompModListDTO> cOMP_LIST) {
		COMP_LIST = cOMP_LIST;
	}
}
