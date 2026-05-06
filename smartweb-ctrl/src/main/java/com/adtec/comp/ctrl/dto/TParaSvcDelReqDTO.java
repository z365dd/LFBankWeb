package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaSvcDelReqDTO {
	private long NUM;
	private List<TParaSvcDelListDTO> SVC_LIST = new ArrayList<TParaSvcDelListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaSvcDelListDTO> getSVC_LIST() {
		return SVC_LIST;
	}
	public void setSVC_LIST(List<TParaSvcDelListDTO> sVC_LIST) {
		SVC_LIST = sVC_LIST;
	}
}
