package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaChnlDelReqDTO {
	private long NUM;
	private List<TParaChnlDelListDTO> CHNL_LIST = new ArrayList<TParaChnlDelListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaChnlDelListDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<TParaChnlDelListDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
}
