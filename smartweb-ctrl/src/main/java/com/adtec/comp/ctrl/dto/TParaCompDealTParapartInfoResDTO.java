package com.adtec.comp.ctrl.dto;

import java.util.ArrayList;
import java.util.List;

public class TParaCompDealTParapartInfoResDTO {
	private long NUM;
	private List<TParaCompDealTParapartInfoListResDTO> LIST = new ArrayList<TParaCompDealTParapartInfoListResDTO>();

	
	public long getNUM() {
		return NUM;
	}

	public void setNUM(long nUM) {
		NUM = nUM;
	}

	public List<TParaCompDealTParapartInfoListResDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<TParaCompDealTParapartInfoListResDTO> lIST) {
		LIST = lIST;
	}

	
}
