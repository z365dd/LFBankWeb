package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustQryListResDTO {
	private String SIGN_PROT_TP_ID;
	private long SER;
	private String SIGN_STAT;
	private String SIGN_STAT_STR;
	private String SIGN_PROT_NO;
	private String OTH_CUST_NO;
	private String OTH_ENTR_NO;
	private String SIGN_CTRCT_NO;
	private String PROT_EFFT_DATE;
	private String PROT_END_DATE;
	
	private List<FSignFuncCustQryList1ResDTO> ACCT_NODE = new ArrayList<FSignFuncCustQryList1ResDTO>();
	private List<FSignFuncCustQryChnlListReqDTO> CHNL_LIST = new ArrayList<FSignFuncCustQryChnlListReqDTO>();
	private List<FSignFuncCustQryLimitListReqDTO> LIMIT_LIST = new ArrayList<FSignFuncCustQryLimitListReqDTO>();
	private List<FSignFuncCustQryDynListReqDTO> DYN_LIST = new ArrayList<FSignFuncCustQryDynListReqDTO>();
	

	private String SIGN_DATE;
	private String SIGN_TIME;
	private String SIGN_BRCH;
	private String SIGN_TLR_NO;
	private String SIGN_MOD_DATE;
	private String MOD_SIGN_TIME;
	private String SIGN_MOD_BRCH;
	private String SIGN_MOD_TLR_NO;
	private String CANCL_SIGN_DATE;
	private String CANCL_SIGN_TIME;
	private String CANCL_SIGN_BRCH;
	private String CANCL_SIGN_TLR_NO;
	private String ERR_TP;
	
	private String ACTION;
	
	
	public List<FSignFuncCustQryDynListReqDTO> getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(List<FSignFuncCustQryDynListReqDTO> dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	public List<FSignFuncCustQryChnlListReqDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<FSignFuncCustQryChnlListReqDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
	public List<FSignFuncCustQryLimitListReqDTO> getLIMIT_LIST() {
		return LIMIT_LIST;
	}
	public void setLIMIT_LIST(List<FSignFuncCustQryLimitListReqDTO> lIMIT_LIST) {
		LIMIT_LIST = lIMIT_LIST;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	public String getSIGN_PROT_TP_ID() {
		return SIGN_PROT_TP_ID;
	}
	public void setSIGN_PROT_TP_ID(String sIGN_PROT_TP_ID) {
		SIGN_PROT_TP_ID = sIGN_PROT_TP_ID;
	}
	public long getSER() {
		return SER;
	}
	public void setSER(long sER) {
		SER = sER;
	}
	public String getSIGN_STAT() {
		return SIGN_STAT;
	}
	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}
	public String getSIGN_STAT_STR() {
		return SIGN_STAT_STR;
	}
	public void setSIGN_STAT_STR(String sIGN_STAT_STR) {
		SIGN_STAT_STR = sIGN_STAT_STR;
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
	public String getOTH_ENTR_NO() {
		return OTH_ENTR_NO;
	}
	public void setOTH_ENTR_NO(String oTH_ENTR_NO) {
		OTH_ENTR_NO = oTH_ENTR_NO;
	}
	public String getSIGN_CTRCT_NO() {
		return SIGN_CTRCT_NO;
	}
	public void setSIGN_CTRCT_NO(String sIGN_CTRCT_NO) {
		SIGN_CTRCT_NO = sIGN_CTRCT_NO;
	}
	public String getPROT_EFFT_DATE() {
		return PROT_EFFT_DATE;
	}
	public void setPROT_EFFT_DATE(String pROT_EFFT_DATE) {
		PROT_EFFT_DATE = pROT_EFFT_DATE;
	}
	public String getPROT_END_DATE() {
		return PROT_END_DATE;
	}
	public void setPROT_END_DATE(String pROT_END_DATE) {
		PROT_END_DATE = pROT_END_DATE;
	}
	
	public List<FSignFuncCustQryList1ResDTO> getACCT_NODE() {
		return ACCT_NODE;
	}
	public void setACCT_NODE(List<FSignFuncCustQryList1ResDTO> aCCT_NODE) {
		ACCT_NODE = aCCT_NODE;
	}
	public String getSIGN_DATE() {
		return SIGN_DATE;
	}
	public void setSIGN_DATE(String sIGN_DATE) {
		SIGN_DATE = sIGN_DATE;
	}
	public String getSIGN_TIME() {
		return SIGN_TIME;
	}
	public void setSIGN_TIME(String sIGN_TIME) {
		SIGN_TIME = sIGN_TIME;
	}
	public String getSIGN_BRCH() {
		return SIGN_BRCH;
	}
	public void setSIGN_BRCH(String sIGN_BRCH) {
		SIGN_BRCH = sIGN_BRCH;
	}
	public String getSIGN_TLR_NO() {
		return SIGN_TLR_NO;
	}
	public void setSIGN_TLR_NO(String sIGN_TLR_NO) {
		SIGN_TLR_NO = sIGN_TLR_NO;
	}
	public String getSIGN_MOD_DATE() {
		return SIGN_MOD_DATE;
	}
	public void setSIGN_MOD_DATE(String sIGN_MOD_DATE) {
		SIGN_MOD_DATE = sIGN_MOD_DATE;
	}
	public String getMOD_SIGN_TIME() {
		return MOD_SIGN_TIME;
	}
	public void setMOD_SIGN_TIME(String mOD_SIGN_TIME) {
		MOD_SIGN_TIME = mOD_SIGN_TIME;
	}
	public String getSIGN_MOD_BRCH() {
		return SIGN_MOD_BRCH;
	}
	public void setSIGN_MOD_BRCH(String sIGN_MOD_BRCH) {
		SIGN_MOD_BRCH = sIGN_MOD_BRCH;
	}
	public String getSIGN_MOD_TLR_NO() {
		return SIGN_MOD_TLR_NO;
	}
	public void setSIGN_MOD_TLR_NO(String sIGN_MOD_TLR_NO) {
		SIGN_MOD_TLR_NO = sIGN_MOD_TLR_NO;
	}
	public String getCANCL_SIGN_DATE() {
		return CANCL_SIGN_DATE;
	}
	public void setCANCL_SIGN_DATE(String cANCL_SIGN_DATE) {
		CANCL_SIGN_DATE = cANCL_SIGN_DATE;
	}
	public String getCANCL_SIGN_TIME() {
		return CANCL_SIGN_TIME;
	}
	public void setCANCL_SIGN_TIME(String cANCL_SIGN_TIME) {
		CANCL_SIGN_TIME = cANCL_SIGN_TIME;
	}
	public String getCANCL_SIGN_BRCH() {
		return CANCL_SIGN_BRCH;
	}
	public void setCANCL_SIGN_BRCH(String cANCL_SIGN_BRCH) {
		CANCL_SIGN_BRCH = cANCL_SIGN_BRCH;
	}
	public String getCANCL_SIGN_TLR_NO() {
		return CANCL_SIGN_TLR_NO;
	}
	public void setCANCL_SIGN_TLR_NO(String cANCL_SIGN_TLR_NO) {
		CANCL_SIGN_TLR_NO = cANCL_SIGN_TLR_NO;
	}
	

	public String getERR_TP() {
		return ERR_TP;
	}

	public void setERR_TP(String eRR_TP) {
		ERR_TP = eRR_TP;
	}
	
	
	
}
