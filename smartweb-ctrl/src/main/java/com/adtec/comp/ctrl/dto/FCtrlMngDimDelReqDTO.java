package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlMngDimDelReqDTO {
	private long NUM;
	private List<FCtrlMngDimDelListDTO> DIM_LIST = new ArrayList<FCtrlMngDimDelListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngDimDelListDTO> getDIM_LIST() {
		return DIM_LIST;
	}
	public void setDIM_LIST(List<FCtrlMngDimDelListDTO> dIM_LIST) {
		DIM_LIST = dIM_LIST;
	}
}
