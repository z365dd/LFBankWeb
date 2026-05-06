package com.adtec.comp.sign.dto;

import java.util.List;

public class FSignFuncSignQryResDTO {
	private String FILE_NAME;
	private String FILE_SET_SEQ;
	private List<FSignFuncSignReqDTO>LIST;
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
	public List<FSignFuncSignReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<FSignFuncSignReqDTO> lIST) {
		LIST = lIST;
	}
	
}
