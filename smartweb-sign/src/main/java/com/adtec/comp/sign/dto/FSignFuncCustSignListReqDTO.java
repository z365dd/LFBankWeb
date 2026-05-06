package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class FSignFuncCustSignListReqDTO {
	//写死字段
	/*private String EFFT_FLG;*/
	
	private long SER;
	private String SIGN_PROT_TP_ID;
	private String SIGN_PROT_NO;
	private String OTH_CUST_NO;
	private String OTH_ENTR_NO;
	private String SIGN_CTRCT_NO;
	private String PROT_EFFT_DATE;
	private String PROT_END_DATE;
	
	//private FSignFuncCustSignANReqDTO ACCT_NODE;
	private List<FSignFuncCustSignANReqDTO> ACCT_NODE = new ArrayList<FSignFuncCustSignANReqDTO>();
	private List<FSignFuncCustSignChnlListReqDTO> CHNL_LIST = new ArrayList<FSignFuncCustSignChnlListReqDTO>();
	private List<FSignFuncCustSignLimitListReqDTO> LIMIT_LIST = new ArrayList<FSignFuncCustSignLimitListReqDTO>();
	private List<FSignFuncCustQryDynListReqDTO> DYN_LIST = new ArrayList<FSignFuncCustQryDynListReqDTO>();
	
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
	
	public List<FSignFuncCustSignANReqDTO> getACCT_NODE() {
		return ACCT_NODE;
	}
	public void setACCT_NODE(List<FSignFuncCustSignANReqDTO> aCCT_NODE) {
		ACCT_NODE = aCCT_NODE;
	}
	public List<FSignFuncCustSignChnlListReqDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<FSignFuncCustSignChnlListReqDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
	public List<FSignFuncCustSignLimitListReqDTO> getLIMIT_LIST() {
		return LIMIT_LIST;
	}
	public void setLIMIT_LIST(List<FSignFuncCustSignLimitListReqDTO> lIMIT_LIST) {
		LIMIT_LIST = lIMIT_LIST;
	}
	public List<FSignFuncCustQryDynListReqDTO> getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(List<FSignFuncCustQryDynListReqDTO> dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	
}
