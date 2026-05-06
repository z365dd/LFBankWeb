package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDelReqDTO {
	/*序号*/
	private long NUM;
	private List<TParaCompDelListDTO> COMP_LIST = new ArrayList<TParaCompDelListDTO>();
	
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaCompDelListDTO> getCOMP_LIST() {
		return COMP_LIST;
	}
	public void setCOMP_LIST(List<TParaCompDelListDTO> cOMP_LIST) {
		COMP_LIST = cOMP_LIST;
	}
}
