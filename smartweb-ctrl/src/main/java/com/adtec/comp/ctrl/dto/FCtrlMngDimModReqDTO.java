package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlMngDimModReqDTO {
	private long NUM;
	private List<FCtrlMngDimModListDTO> DIM_LIST = new ArrayList<FCtrlMngDimModListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngDimModListDTO> getDIM_LIST() {
		return DIM_LIST;
	}
	public void setDIM_LIST(List<FCtrlMngDimModListDTO> dIM_LIST) {
		DIM_LIST = dIM_LIST;
	}
	
}
