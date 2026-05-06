package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompAddReqDTO {
	/*序号*/
	private long NUM;
	private List<TParaCompAddListDTO> COMP_LIST = new ArrayList<TParaCompAddListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaCompAddListDTO> getCOMP_LIST() {
		return COMP_LIST;
	}
	public void setCOMP_LIST(List<TParaCompAddListDTO> cOMP_LIST) {
		COMP_LIST = cOMP_LIST;
	}
}
