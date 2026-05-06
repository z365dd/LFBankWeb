package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaChnlModReqDTO {
	private long NUM;
	private List<TParaChnlModListDTO> CHNL_LIST = new ArrayList<TParaChnlModListDTO>();;
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaChnlModListDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<TParaChnlModListDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
}
