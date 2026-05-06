package com.adtec.comp.fsvr.dto;

import java.util.ArrayList;
import java.util.List;

public class TfmngMngJrnlStatsResDTO {
	private List<TfmngMngJrnlStatsListResDTO> LIST = new ArrayList<TfmngMngJrnlStatsListResDTO>();

	public List<TfmngMngJrnlStatsListResDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<TfmngMngJrnlStatsListResDTO> lIST) {
		LIST = lIST;
	}

	
}
