package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class FCtrlMngPubParaModReqDTO {
	private long NUM;
	private List<FCtrlMngPubParaModListDTO> KEY_LIST = new ArrayList<FCtrlMngPubParaModListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngPubParaModListDTO> getKEY_LIST() {
		return KEY_LIST;
	}
	public void setKEY_LIST(List<FCtrlMngPubParaModListDTO> kEY_LIST) {
		KEY_LIST = kEY_LIST;
	}
}
