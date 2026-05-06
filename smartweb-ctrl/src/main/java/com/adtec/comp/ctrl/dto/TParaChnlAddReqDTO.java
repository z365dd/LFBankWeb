package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaChnlAddReqDTO {
	private long NUM;
	private List<TParaChnlAddListDTO> CHNL_LIST = new ArrayList<TParaChnlAddListDTO>();
	
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaChnlAddListDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<TParaChnlAddListDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
	
}
