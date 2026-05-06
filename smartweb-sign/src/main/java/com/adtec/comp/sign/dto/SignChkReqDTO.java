package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignChkReqDTO {
	private long NUM;
	private List<SignChkReqListDTO> LIST  =new ArrayList<SignChkReqListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<SignChkReqListDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<SignChkReqListDTO> lIST) {
		LIST = lIST;
	}
}
