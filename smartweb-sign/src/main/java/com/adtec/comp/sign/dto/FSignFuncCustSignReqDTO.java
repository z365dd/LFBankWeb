package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustSignReqDTO {
	/*private String FLG;*/
	private List<FSignFuncCustSignListReqDTO> LIST = new ArrayList<FSignFuncCustSignListReqDTO>();


	public List<FSignFuncCustSignListReqDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<FSignFuncCustSignListReqDTO> lIST) {
		LIST = lIST;
	}

	
}
