package com.adtec.comp.ctrl.test.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlTranAuthChkResDTO {
	private long NUM;
	private List<FCtrlTranAuthChkListDTO> LIST = new ArrayList<FCtrlTranAuthChkListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlTranAuthChkListDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<FCtrlTranAuthChkListDTO> lIST) {
		LIST = lIST;
	}
}
