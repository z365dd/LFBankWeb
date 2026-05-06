package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlMngPubParaQryResDTO {
	private long NUM;
	private List<FCtrlMngPubParaQryListDTO> KEY_LIST = new ArrayList<FCtrlMngPubParaQryListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngPubParaQryListDTO> getKEY_LIST() {
		return KEY_LIST;
	}
	public void setKEY_LIST(List<FCtrlMngPubParaQryListDTO> kEY_LIST) {
		KEY_LIST = kEY_LIST;
	}
}
