package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustModSimpleReqDTO {
	/*private String FLG;*/
	private List<FSignFuncCustModSimpleListReqDTO> LIST = new ArrayList<FSignFuncCustModSimpleListReqDTO>();
	
	public List<FSignFuncCustModSimpleListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<FSignFuncCustModSimpleListReqDTO> lIST) {
		LIST = lIST;
	}
	
	
}
