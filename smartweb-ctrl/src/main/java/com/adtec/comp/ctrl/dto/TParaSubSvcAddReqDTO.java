package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaSubSvcAddReqDTO {
	private long NUM;
	private List<TParaSubSvcAddListDTO> SUB_SVC_LIST = new ArrayList<TParaSubSvcAddListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaSubSvcAddListDTO> getSUB_SVC_LIST() {
		return SUB_SVC_LIST;
	}
	public void setSUB_SVC_LIST(List<TParaSubSvcAddListDTO> sUB_SVC_LIST) {
		SUB_SVC_LIST = sUB_SVC_LIST;
	}
}
