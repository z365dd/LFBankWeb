package com.adtec.comp.sign.dto;

import java.util.List;

public class FSignFuncSignReqDTO {
	private long SER;
	private String SIGN_PROT_TP_ID;
	private String SIGN_PROT_NO;
	private String OTH_CUST_NO;
	private String SIGN_STAT;
	private String SIGN_DATE;
	private String BUSI_NO;
	private String BUSI_NAME;
	private String SIGN_STAT_STR;
	
	
	private List<FSignFuncSignReqDYNDTO>DYN_LIST;
	private List<FSignFuncSignReqAcctNodeDTO> ACCT_NODE;
	
	
	private List<FSignFuncSignReqAcctNodeDTO> CHNL_LIST;
	private List<FSignFuncSignReqAcctNodeDTO> LIMIT_LIST;



	public String getSIGN_STAT_STR() {
		return SIGN_STAT_STR;
	}

	public void setSIGN_STAT_STR(String sIGN_STAT_STR) {
		switch(sIGN_STAT_STR){
		case "00":this.SIGN_STAT_STR="已签约";
		break;
		case "10":this.SIGN_STAT_STR="已解约";
		break;
		case "20":this.SIGN_STAT_STR="待生效";
		break;
		}
	}

	public String getSIGN_DATE() {
		return SIGN_DATE;
	}

	public void setSIGN_DATE(String sIGN_DATE) {
		SIGN_DATE = sIGN_DATE;
	}

	public String getBUSI_NO() {
		return BUSI_NO;
	}

	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}

	public String getBUSI_NAME() {
		return BUSI_NAME;
	}

	public void setBUSI_NAME(String bUSI_NAME) {
		BUSI_NAME = bUSI_NAME;
	}

	public String getSIGN_STAT() {
		return SIGN_STAT;
	}

	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}

	public List<FSignFuncSignReqAcctNodeDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}

	public void setCHNL_LIST(List<FSignFuncSignReqAcctNodeDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}

	public List<FSignFuncSignReqAcctNodeDTO> getLIMIT_LIST() {
		return LIMIT_LIST;
	}

	public void setLIMIT_LIST(List<FSignFuncSignReqAcctNodeDTO> lIMIT_LIST) {
		LIMIT_LIST = lIMIT_LIST;
	}

	public List<FSignFuncSignReqAcctNodeDTO> getACCT_NODE() {
		return ACCT_NODE;
	}

	public void setACCT_NODE(List<FSignFuncSignReqAcctNodeDTO> aCCT_NODE) {
		ACCT_NODE = aCCT_NODE;
	}

	public long getSER() {
		return SER;
	}

	public void setSER(long sER) {
		SER = sER;
	}

	public String getSIGN_PROT_TP_ID() {
		return SIGN_PROT_TP_ID;
	}

	public void setSIGN_PROT_TP_ID(String sIGN_PROT_TP_ID) {
		SIGN_PROT_TP_ID = sIGN_PROT_TP_ID;
	}

	public String getSIGN_PROT_NO() {
		return SIGN_PROT_NO;
	}

	public void setSIGN_PROT_NO(String sIGN_PROT_NO) {
		SIGN_PROT_NO = sIGN_PROT_NO;
	}

	public String getOTH_CUST_NO() {
		return OTH_CUST_NO;
	}

	public void setOTH_CUST_NO(String oTH_CUST_NO) {
		OTH_CUST_NO = oTH_CUST_NO;
	}

	public List<FSignFuncSignReqDYNDTO> getDYN_LIST() {
		return DYN_LIST;
	}

	public void setDYN_LIST(List<FSignFuncSignReqDYNDTO> dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	
	
	
	
	

}
