package com.adtec.comp.ctrl.test.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlTranExmResDTO {
	private long NUM;
	private List<FCtrlTranExmListDTO> LIST = new ArrayList<FCtrlTranExmListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlTranExmListDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<FCtrlTranExmListDTO> lIST) {
		LIST = lIST;
	}
}
