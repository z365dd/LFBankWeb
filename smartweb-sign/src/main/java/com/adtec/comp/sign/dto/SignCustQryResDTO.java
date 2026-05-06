package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignCustQryResDTO {
	private long num;
	private List<SignCustAcctListDTO> ACCT_LIST = new ArrayList<SignCustAcctListDTO>();
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public List<SignCustAcctListDTO> getACCT_LIST() {
		return ACCT_LIST;
	}
	public void setACCT_LIST(List<SignCustAcctListDTO> aCCT_LIST) {
		ACCT_LIST = aCCT_LIST;
	}
}
