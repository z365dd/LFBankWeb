package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignCustSignReqDTO {
	private long ACCT_NUM;
	private List<SignCustAcctListDTO> ACCT_LIST = new ArrayList<SignCustAcctListDTO>();
	public long getACCT_NUM() {
		return ACCT_NUM;
	}
	public void setACCT_NUM(long aCCT_NUM) {
		ACCT_NUM = aCCT_NUM;
	}
	public List<SignCustAcctListDTO> getACCT_LIST() {
		return ACCT_LIST;
	}
	public void setACCT_LIST(List<SignCustAcctListDTO> aCCT_LIST) {
		ACCT_LIST = aCCT_LIST;
	}
}
