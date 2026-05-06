package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignVerifSignVerifyReqDTO {
	/*private String ENTR_NO  ; 
	private String BUSI_NO  ; 
	private String FUNCT_NO ; 
	private String CHNL_NO  ; 
	private String OPER_TP  ; */

	private List<FSignVerifSignVerifyListReqDTO> LIST  =new ArrayList<FSignVerifSignVerifyListReqDTO>();


	public List<FSignVerifSignVerifyListReqDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<FSignVerifSignVerifyListReqDTO> lIST) {
		LIST = lIST;
	}
	
	
}
