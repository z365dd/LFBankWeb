package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustPreSignconfReqDTO {
	private List<FSignFuncCustPreSignConfListReqDTO> LIST = new ArrayList<FSignFuncCustPreSignConfListReqDTO>();

	public List<FSignFuncCustPreSignConfListReqDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<FSignFuncCustPreSignConfListReqDTO> lIST) {
		LIST = lIST;
	}
	
}
