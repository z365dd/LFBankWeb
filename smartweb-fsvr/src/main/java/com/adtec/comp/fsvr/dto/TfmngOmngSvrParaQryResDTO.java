package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngOmngSvrParaQryResDTO {
	private List<TfmngOmngSvrParaQryListResDTO> LIST = new ArrayList<TfmngOmngSvrParaQryListResDTO>();

	public List<TfmngOmngSvrParaQryListResDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<TfmngOmngSvrParaQryListResDTO> lIST) {
		LIST = lIST;
	}
	
}
