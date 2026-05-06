package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTParaBankResDTO {
	private long NUM;
	private List<TParaCompDealTParaBankListResDTO> LIST = new ArrayList<TParaCompDealTParaBankListResDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaCompDealTParaBankListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TParaCompDealTParaBankListResDTO> lIST) {
		LIST = lIST;
	}
	
}
