package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlMngDimAddReqDTO {
	private long NUM;
	private List<FCtrlMngDimAddListDTO> DIM_LIST = new ArrayList<FCtrlMngDimAddListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngDimAddListDTO> getDIM_LIST() {
		return DIM_LIST;
	}
	public void setDIM_LIST(List<FCtrlMngDimAddListDTO> dIM_LIST) {
		DIM_LIST = dIM_LIST;
	}
	
}
