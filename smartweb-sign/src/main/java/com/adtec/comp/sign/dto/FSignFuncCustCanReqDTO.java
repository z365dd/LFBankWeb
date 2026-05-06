package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustCanReqDTO {
	private String FLG;
	private List<FSignFuncCustCanListReqDTO> LIST = new ArrayList<FSignFuncCustCanListReqDTO>();
	
	public String getFLG() {
		return FLG;
	}
	public void setFLG(String fLG) {
		FLG = fLG;
	}

	public List<FSignFuncCustCanListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<FSignFuncCustCanListReqDTO> lIST) {
		LIST = lIST;
	}
	
	
}
