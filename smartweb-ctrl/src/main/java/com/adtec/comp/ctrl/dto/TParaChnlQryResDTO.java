package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaChnlQryResDTO {
	private long NUM;
	private List<TParaChnlQryListDTO> CHNL_LIST = new ArrayList<TParaChnlQryListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaChnlQryListDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<TParaChnlQryListDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
}
