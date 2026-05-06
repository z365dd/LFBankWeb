package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaSvcAddReqDTO {
	private long NUM;
	private List<TParaSvcAddListDTO> SVC_LIST = new ArrayList<TParaSvcAddListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaSvcAddListDTO> getSVC_LIST() {
		return SVC_LIST;
	}
	public void setSVC_LIST(List<TParaSvcAddListDTO> sVC_LIST) {
		SVC_LIST = sVC_LIST;
	}
}
