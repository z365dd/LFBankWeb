package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaSubSvcDelReqDTO {
	/*记录个数*/
	private long NUM;
	private List<TParaSubSvcDelListDTO> SUB_SVC_LIST = new ArrayList<TParaSubSvcDelListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaSubSvcDelListDTO> getSUB_SVC_LIST() {
		return SUB_SVC_LIST;
	}
	public void setSUB_SVC_LIST(List<TParaSubSvcDelListDTO> sUB_SVC_LIST) {
		SUB_SVC_LIST = sUB_SVC_LIST;
	}
}
