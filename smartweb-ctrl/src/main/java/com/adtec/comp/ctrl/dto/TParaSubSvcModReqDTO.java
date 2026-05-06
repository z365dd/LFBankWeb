package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaSubSvcModReqDTO {
	private long NUM;
	private List<TParaSubSvcModListDTO> SUB_SVC_LIST = new ArrayList<TParaSubSvcModListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaSubSvcModListDTO> getSUB_SVC_LIST() {
		return SUB_SVC_LIST;
	}
	public void setSUB_SVC_LIST(List<TParaSubSvcModListDTO> sUB_SVC_LIST) {
		SUB_SVC_LIST = sUB_SVC_LIST;
	}
}
