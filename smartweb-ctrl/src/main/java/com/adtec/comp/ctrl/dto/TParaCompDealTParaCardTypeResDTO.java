package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTParaCardTypeResDTO {
	private long NUM;
	private List<TParaCompDealTParaCardTypeListResDTO> LIST = new ArrayList<TParaCompDealTParaCardTypeListResDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaCompDealTParaCardTypeListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TParaCompDealTParaCardTypeListResDTO> lIST) {
		LIST = lIST;
	}

	
	
}
