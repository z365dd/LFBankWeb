package com.adtec.comp.sign.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class SignEntrSubBusiListDTO implements Serializable {
	private String SUB_BUSI_NO;
	private String SUB_BUSI_NAME;
	private String STR_DATE;
	private String END_DATE;
	private String BANK_CUST_NO;
	private String ACCT_TP;
	private String ACCT;
	private String ACCT_NAME;
	private String OPEN_ACCT_BRCH;
	private String BANK_NAME;
	private String SIGN_PROT_NO;
	private String BANK_SIGN_PROT_NO;
	private String FEE_TP;
	private String FEE_CODE;
	private String FEE_TF_OUT_ACCT;
	private String FEE_TF_OUT_ACCT_NAME;
	private String FEE_TF_IN_ACCT;
	private String FEE_TF_IN_ACCT_NAME;
	private String SUM_CODE;
	private String SUM_DESC;
	private String INTRM_ACCT_FLG;
	private String INTRM_ACCT;
	private String INTRM_ACCT_NAME;
	private String SIGN_STAT;
	private JSONArray DYN_LIST;
	private long NUM;
	private long CHNL_NUM;
	private List<SignEntrChnlListDTO> CHNL_LIST = new ArrayList<SignEntrChnlListDTO>();
	private long CTRL_NUM;
	private List<SignCustCtrlListDTO> CTRL_LIST = new ArrayList<SignCustCtrlListDTO>();
	public String getSUB_BUSI_NO() {
		return SUB_BUSI_NO;
	}
	public void setSUB_BUSI_NO(String sUB_BUSI_NO) {
		SUB_BUSI_NO = sUB_BUSI_NO;
	}
	public String getSUB_BUSI_NAME() {
		return SUB_BUSI_NAME;
	}
	public void setSUB_BUSI_NAME(String sUB_BUSI_NAME) {
		SUB_BUSI_NAME = sUB_BUSI_NAME;
	}
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
	public String getBANK_CUST_NO() {
		return BANK_CUST_NO;
	}
	public void setBANK_CUST_NO(String bANK_CUST_NO) {
		BANK_CUST_NO = bANK_CUST_NO;
	}
	public String getACCT_TP() {
		return ACCT_TP;
	}
	public void setACCT_TP(String aCCT_TP) {
		ACCT_TP = aCCT_TP;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}
	public String getACCT_NAME() {
		return ACCT_NAME;
	}
	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
	}
	public String getOPEN_ACCT_BRCH() {
		return OPEN_ACCT_BRCH;
	}
	public void setOPEN_ACCT_BRCH(String oPEN_ACCT_BRCH) {
		OPEN_ACCT_BRCH = oPEN_ACCT_BRCH;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	public String getSIGN_PROT_NO() {
		return SIGN_PROT_NO;
	}
	public void setSIGN_PROT_NO(String sIGN_PROT_NO) {
		SIGN_PROT_NO = sIGN_PROT_NO;
	}
	public String getBANK_SIGN_PROT_NO() {
		return BANK_SIGN_PROT_NO;
	}
	public void setBANK_SIGN_PROT_NO(String bANK_SIGN_PROT_NO) {
		BANK_SIGN_PROT_NO = bANK_SIGN_PROT_NO;
	}
	public String getFEE_TP() {
		return FEE_TP;
	}
	public void setFEE_TP(String fEE_TP) {
		FEE_TP = fEE_TP;
	}
	public String getFEE_CODE() {
		return FEE_CODE;
	}
	public void setFEE_CODE(String fEE_CODE) {
		FEE_CODE = fEE_CODE;
	}
	public String getFEE_TF_OUT_ACCT() {
		return FEE_TF_OUT_ACCT;
	}
	public void setFEE_TF_OUT_ACCT(String fEE_TF_OUT_ACCT) {
		FEE_TF_OUT_ACCT = fEE_TF_OUT_ACCT;
	}
	public String getFEE_TF_OUT_ACCT_NAME() {
		return FEE_TF_OUT_ACCT_NAME;
	}
	public void setFEE_TF_OUT_ACCT_NAME(String fEE_TF_OUT_ACCT_NAME) {
		FEE_TF_OUT_ACCT_NAME = fEE_TF_OUT_ACCT_NAME;
	}
	public String getFEE_TF_IN_ACCT() {
		return FEE_TF_IN_ACCT;
	}
	public void setFEE_TF_IN_ACCT(String fEE_TF_IN_ACCT) {
		FEE_TF_IN_ACCT = fEE_TF_IN_ACCT;
	}
	public String getFEE_TF_IN_ACCT_NAME() {
		return FEE_TF_IN_ACCT_NAME;
	}
	public void setFEE_TF_IN_ACCT_NAME(String fEE_TF_IN_ACCT_NAME) {
		FEE_TF_IN_ACCT_NAME = fEE_TF_IN_ACCT_NAME;
	}
	public String getSUM_CODE() {
		return SUM_CODE;
	}
	public void setSUM_CODE(String sUM_CODE) {
		SUM_CODE = sUM_CODE;
	}
	public String getSUM_DESC() {
		return SUM_DESC;
	}
	public void setSUM_DESC(String sUM_DESC) {
		SUM_DESC = sUM_DESC;
	}
	public String getINTRM_ACCT_FLG() {
		return INTRM_ACCT_FLG;
	}
	public void setINTRM_ACCT_FLG(String iNTRM_ACCT_FLG) {
		INTRM_ACCT_FLG = iNTRM_ACCT_FLG;
	}
	public String getINTRM_ACCT() {
		return INTRM_ACCT;
	}
	public void setINTRM_ACCT(String iNTRM_ACCT) {
		INTRM_ACCT = iNTRM_ACCT;
	}
	public String getINTRM_ACCT_NAME() {
		return INTRM_ACCT_NAME;
	}
	public void setINTRM_ACCT_NAME(String iNTRM_ACCT_NAME) {
		INTRM_ACCT_NAME = iNTRM_ACCT_NAME;
	}
	public String getSIGN_STAT() {
		return SIGN_STAT;
	}
	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}
	public long getCHNL_NUM() {
		return CHNL_NUM;
	}
	public void setCHNL_NUM(long cHNL_NUM) {
		CHNL_NUM = cHNL_NUM;
	}
	public List<SignEntrChnlListDTO> getCHNL_LIST() {
		return CHNL_LIST;
	}
	public void setCHNL_LIST(List<SignEntrChnlListDTO> cHNL_LIST) {
		CHNL_LIST = cHNL_LIST;
	}
	public long getCTRL_NUM() {
		return CTRL_NUM;
	}
	public void setCTRL_NUM(long cTRL_NUM) {
		CTRL_NUM = cTRL_NUM;
	}
	public List<SignCustCtrlListDTO> getCTRL_LIST() {
		return CTRL_LIST;
	}
	public void setCTRL_LIST(List<SignCustCtrlListDTO> cTRL_LIST) {
		CTRL_LIST = cTRL_LIST;
	}
	public JSONArray getDYN_LIST() {
		return DYN_LIST;
	}
	public void setDYN_LIST(JSONArray dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
}
