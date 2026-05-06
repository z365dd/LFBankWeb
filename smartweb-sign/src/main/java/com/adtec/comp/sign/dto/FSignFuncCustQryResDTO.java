package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustQryResDTO {
	private String FILE_NAME;
	private String FILE_SET_SEQ;
	
	private List<FSignFuncCustQryListResDTO> LIST = new ArrayList<FSignFuncCustQryListResDTO>();
	
	
	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public String getFILE_SET_SEQ() {
		return FILE_SET_SEQ;
	}

	public void setFILE_SET_SEQ(String fILE_SET_SEQ) {
		FILE_SET_SEQ = fILE_SET_SEQ;
	}

	public List<FSignFuncCustQryListResDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<FSignFuncCustQryListResDTO> lIST) {
		LIST = lIST;
	}
	
}
