package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignStatsResDTO {
	
	private String STR_DATE;
	private String END_DATE;
	private long NUM;
	private List<SignStatsListResDTO> LIST = new ArrayList<SignStatsListResDTO>();
	public String getSTR_DATE() {
		return STR_DATE;
	}
	public void setSTR_DATE(String sTR_DATE) {
		STR_DATE = sTR_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<SignStatsListResDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<SignStatsListResDTO> lIST) {
		LIST = lIST;
	}
}
