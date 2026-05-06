package com.adtec.comp.sign.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class SignCustSubBusiListDTO implements Serializable{
	private String SUB_BUSI_NO;
	private String SUB_BUSI_NAME;
	private String OTH_CUST_NO;
	private String OTH_ENTR_NO;
	private String BANK_CUST_NO;
	private String SIGN_PROT_NO;
	private String STR_DATE;
	private String END_DATE;
	private String SIGN_STAT;
	private String BANK_SIGN_FLG;
	private String BANK_SIGN_PROT_NO;
	private String OTH_SIGN_FLG;
	private String OTH_SIGN_PROT_NO;
	private String OPP_ACCT;
	private String OPP_ACCT_NAME;
	private String OPP_BANK;
	private String OPP_BANK_NAME;
	private double SGL_LIM;
	private long DAY_NUM;
	private double DAY_LIM;
	private long MONTH_NUM;
	private double MONTH_LIM;
	private long NUM;
	private JSONArray DYN_LIST;
	private long CTRL_NUM;
	private List<SignCustCtrlListDTO> CTRL_LIST = new ArrayList<SignCustCtrlListDTO>();
	/**
	 * @return the sUB_BUSI_NO
	 */
	public String getSUB_BUSI_NO() {
		return SUB_BUSI_NO;
	}
	/**
	 * @param sUB_BUSI_NO the sUB_BUSI_NO to set
	 */
	public void setSUB_BUSI_NO(String sUB_BUSI_NO) {
		SUB_BUSI_NO = sUB_BUSI_NO;
	}
	/**
	 * @return the sUB_BUSI_NAME
	 */
	public String getSUB_BUSI_NAME() {
		return SUB_BUSI_NAME;
	}
	/**
	 * @param sUB_BUSI_NAME the sUB_BUSI_NAME to set
	 */
	public void setSUB_BUSI_NAME(String sUB_BUSI_NAME) {
		SUB_BUSI_NAME = sUB_BUSI_NAME;
	}
	/**
	 * @return the oTH_CUST_NO
	 */
	public String getOTH_CUST_NO() {
		return OTH_CUST_NO;
	}
	/**
	 * @param oTH_CUST_NO the oTH_CUST_NO to set
	 */
	public void setOTH_CUST_NO(String oTH_CUST_NO) {
		OTH_CUST_NO = oTH_CUST_NO;
	}
	/**
	 * @return the oTH_ENTR_NO
	 */
	public String getOTH_ENTR_NO() {
		return OTH_ENTR_NO;
	}
	/**
	 * @param oTH_ENTR_NO the oTH_ENTR_NO to set
	 */
	public void setOTH_ENTR_NO(String oTH_ENTR_NO) {
		OTH_ENTR_NO = oTH_ENTR_NO;
	}
	/**
	 * @return the bANK_CUST_NO
	 */
	public String getBANK_CUST_NO() {
		return BANK_CUST_NO;
	}
	/**
	 * @param bANK_CUST_NO the bANK_CUST_NO to set
	 */
	public void setBANK_CUST_NO(String bANK_CUST_NO) {
		BANK_CUST_NO = bANK_CUST_NO;
	}
	/**
	 * @return the sIGN_PROT_NO
	 */
	public String getSIGN_PROT_NO() {
		return SIGN_PROT_NO;
	}
	/**
	 * @param sIGN_PROT_NO the sIGN_PROT_NO to set
	 */
	public void setSIGN_PROT_NO(String sIGN_PROT_NO) {
		SIGN_PROT_NO = sIGN_PROT_NO;
	}
	/**
	 * @return the sTR_DATE
	 */
	public String getSTR_DATE() {
		return STR_DATE;
	}
	/**
	 * @param sTR_DATE the sTR_DATE to set
	 */
	public void setSTR_DATE(String sTR_DATE) {
		STR_DATE = sTR_DATE;
	}
	/**
	 * @return the eND_DATE
	 */
	public String getEND_DATE() {
		return END_DATE;
	}
	/**
	 * @param eND_DATE the eND_DATE to set
	 */
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	/**
	 * @return the sIGN_STAT
	 */
	public String getSIGN_STAT() {
		return SIGN_STAT;
	}
	/**
	 * @param sIGN_STAT the sIGN_STAT to set
	 */
	public void setSIGN_STAT(String sIGN_STAT) {
		SIGN_STAT = sIGN_STAT;
	}
	/**
	 * @return the bANK_SIGN_FLG
	 */
	public String getBANK_SIGN_FLG() {
		return BANK_SIGN_FLG;
	}
	/**
	 * @param bANK_SIGN_FLG the bANK_SIGN_FLG to set
	 */
	public void setBANK_SIGN_FLG(String bANK_SIGN_FLG) {
		BANK_SIGN_FLG = bANK_SIGN_FLG;
	}
	/**
	 * @return the bANK_SIGN_PROT_NO
	 */
	public String getBANK_SIGN_PROT_NO() {
		return BANK_SIGN_PROT_NO;
	}
	/**
	 * @param bANK_SIGN_PROT_NO the bANK_SIGN_PROT_NO to set
	 */
	public void setBANK_SIGN_PROT_NO(String bANK_SIGN_PROT_NO) {
		BANK_SIGN_PROT_NO = bANK_SIGN_PROT_NO;
	}
	/**
	 * @return the oTH_SIGN_FLG
	 */
	public String getOTH_SIGN_FLG() {
		return OTH_SIGN_FLG;
	}
	/**
	 * @param oTH_SIGN_FLG the oTH_SIGN_FLG to set
	 */
	public void setOTH_SIGN_FLG(String oTH_SIGN_FLG) {
		OTH_SIGN_FLG = oTH_SIGN_FLG;
	}
	/**
	 * @return the oTH_SIGN_PROT_NO
	 */
	public String getOTH_SIGN_PROT_NO() {
		return OTH_SIGN_PROT_NO;
	}
	/**
	 * @param oTH_SIGN_PROT_NO the oTH_SIGN_PROT_NO to set
	 */
	public void setOTH_SIGN_PROT_NO(String oTH_SIGN_PROT_NO) {
		OTH_SIGN_PROT_NO = oTH_SIGN_PROT_NO;
	}
	/**
	 * @return the oPP_ACCT
	 */
	public String getOPP_ACCT() {
		return OPP_ACCT;
	}
	/**
	 * @param oPP_ACCT the oPP_ACCT to set
	 */
	public void setOPP_ACCT(String oPP_ACCT) {
		OPP_ACCT = oPP_ACCT;
	}
	/**
	 * @return the oPP_ACCT_NAME
	 */
	public String getOPP_ACCT_NAME() {
		return OPP_ACCT_NAME;
	}
	/**
	 * @param oPP_ACCT_NAME the oPP_ACCT_NAME to set
	 */
	public void setOPP_ACCT_NAME(String oPP_ACCT_NAME) {
		OPP_ACCT_NAME = oPP_ACCT_NAME;
	}
	/**
	 * @return the oPP_BANK
	 */
	public String getOPP_BANK() {
		return OPP_BANK;
	}
	/**
	 * @param oPP_BANK the oPP_BANK to set
	 */
	public void setOPP_BANK(String oPP_BANK) {
		OPP_BANK = oPP_BANK;
	}
	/**
	 * @return the oPP_BANK_NAME
	 */
	public String getOPP_BANK_NAME() {
		return OPP_BANK_NAME;
	}
	/**
	 * @param oPP_BANK_NAME the oPP_BANK_NAME to set
	 */
	public void setOPP_BANK_NAME(String oPP_BANK_NAME) {
		OPP_BANK_NAME = oPP_BANK_NAME;
	}
	/**
	 * @return the sGL_LIM
	 */
	public double getSGL_LIM() {
		return SGL_LIM;
	}
	/**
	 * @param sGL_LIM the sGL_LIM to set
	 */
	public void setSGL_LIM(double sGL_LIM) {
		SGL_LIM = sGL_LIM;
	}
	/**
	 * @return the dAY_NUM
	 */
	public long getDAY_NUM() {
		return DAY_NUM;
	}
	/**
	 * @param dAY_NUM the dAY_NUM to set
	 */
	public void setDAY_NUM(long dAY_NUM) {
		DAY_NUM = dAY_NUM;
	}
	/**
	 * @return the dAY_LIM
	 */
	public double getDAY_LIM() {
		return DAY_LIM;
	}
	/**
	 * @param dAY_LIM the dAY_LIM to set
	 */
	public void setDAY_LIM(double dAY_LIM) {
		DAY_LIM = dAY_LIM;
	}
	/**
	 * @return the mONTH_NUM
	 */
	public long getMONTH_NUM() {
		return MONTH_NUM;
	}
	/**
	 * @param mONTH_NUM the mONTH_NUM to set
	 */
	public void setMONTH_NUM(long mONTH_NUM) {
		MONTH_NUM = mONTH_NUM;
	}
	/**
	 * @return the mONTH_LIM
	 */
	public double getMONTH_LIM() {
		return MONTH_LIM;
	}
	/**
	 * @param mONTH_LIM the mONTH_LIM to set
	 */
	public void setMONTH_LIM(double mONTH_LIM) {
		MONTH_LIM = mONTH_LIM;
	}
	/**
	 * @return the nUM
	 */
	public long getNUM() {
		return NUM;
	}
	/**
	 * @param nUM the nUM to set
	 */
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	/**
	 * @return the dYN_LIST
	 */
	public JSONArray getDYN_LIST() {
		return DYN_LIST;
	}
	/**
	 * @param dYN_LIST the dYN_LIST to set
	 */
	public void setDYN_LIST(JSONArray dYN_LIST) {
		DYN_LIST = dYN_LIST;
	}
	/**
	 * @return the cTRL_NUM
	 */
	public long getCTRL_NUM() {
		return CTRL_NUM;
	}
	/**
	 * @param cTRL_NUM the cTRL_NUM to set
	 */
	public void setCTRL_NUM(long cTRL_NUM) {
		CTRL_NUM = cTRL_NUM;
	}
	/**
	 * @return the cTRL_LIST
	 */
	public List<SignCustCtrlListDTO> getCTRL_LIST() {
		return CTRL_LIST;
	}
	/**
	 * @param cTRL_LIST the cTRL_LIST to set
	 */
	public void setCTRL_LIST(List<SignCustCtrlListDTO> cTRL_LIST) {
		CTRL_LIST = cTRL_LIST;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SignCustSubBusiListDTO [SUB_BUSI_NO=" + SUB_BUSI_NO + ", SUB_BUSI_NAME=" + SUB_BUSI_NAME
				+ ", OTH_CUST_NO=" + OTH_CUST_NO + ", OTH_ENTR_NO=" + OTH_ENTR_NO + ", BANK_CUST_NO=" + BANK_CUST_NO
				+ ", SIGN_PROT_NO=" + SIGN_PROT_NO + ", STR_DATE=" + STR_DATE + ", END_DATE=" + END_DATE
				+ ", SIGN_STAT=" + SIGN_STAT + ", BANK_SIGN_FLG=" + BANK_SIGN_FLG + ", BANK_SIGN_PROT_NO="
				+ BANK_SIGN_PROT_NO + ", OTH_SIGN_FLG=" + OTH_SIGN_FLG + ", OTH_SIGN_PROT_NO=" + OTH_SIGN_PROT_NO
				+ ", OPP_ACCT=" + OPP_ACCT + ", OPP_ACCT_NAME=" + OPP_ACCT_NAME + ", OPP_BANK=" + OPP_BANK
				+ ", OPP_BANK_NAME=" + OPP_BANK_NAME + ", SGL_LIM=" + SGL_LIM + ", DAY_NUM=" + DAY_NUM + ", DAY_LIM="
				+ DAY_LIM + ", MONTH_NUM=" + MONTH_NUM + ", MONTH_LIM=" + MONTH_LIM + ", NUM=" + NUM + ", DYN_LIST="
				+ DYN_LIST + ", CTRL_NUM=" + CTRL_NUM + ", CTRL_LIST=" + CTRL_LIST + "]";
	}
	
}
