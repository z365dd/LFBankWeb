package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTParaCompParaResDTO {
	private long NUM;
	private List<TParaCompDealTParaCompParaListResDTO> LIST = new ArrayList<TParaCompDealTParaCompParaListResDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<TParaCompDealTParaCompParaListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TParaCompDealTParaCompParaListResDTO> lIST) {
		LIST = lIST;
	}
	
}
