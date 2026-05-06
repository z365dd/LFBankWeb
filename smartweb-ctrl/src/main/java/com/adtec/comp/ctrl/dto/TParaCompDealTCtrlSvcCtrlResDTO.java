package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTCtrlSvcCtrlResDTO {
	private long NUM;
	private List<TParaCompDealTCtrlSvcCtrlListResDTO> LIST = new ArrayList<TParaCompDealTCtrlSvcCtrlListResDTO>();

	
	public long getNUM() {
		return NUM;
	}

	public void setNUM(long nUM) {
		NUM = nUM;
	}

	public List<TParaCompDealTCtrlSvcCtrlListResDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<TParaCompDealTCtrlSvcCtrlListResDTO> lIST) {
		LIST = lIST;
	}
	
}
