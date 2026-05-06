package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaSvcModReqDTO {
	private long NUM;
	private List<TParaSvcModListDTO> SVC_LIST = new ArrayList<TParaSvcModListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaSvcModListDTO> getSVC_LIST() {
		return SVC_LIST;
	}
	public void setSVC_LIST(List<TParaSvcModListDTO> sVC_LIST) {
		SVC_LIST = sVC_LIST;
	}
}
