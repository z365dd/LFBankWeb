package com.adtec.comp.sign.dto;

public class SignStatsListResDTO {
	
	private String COMP_NO;
	private String COMP_NAME;
	private String BUSI_NO;
	private String BUSI_NAME;
	private String SUB_BUSI_NO;
	private String SUB_BUSI_NAME;
	private String ENTR_NO;
	private String ENTR_NAME;
	private long SUCC_TOT_NUM;
	private long FAIL_TOT_NUM;
	/**
	 * @return the cOMP_NO
	 */
	public String getCOMP_NO() {
		return COMP_NO;
	}
	/**
	 * @param cOMP_NO the cOMP_NO to set
	 */
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	/**
	 * @return the cOMP_NAME
	 */
	public String getCOMP_NAME() {
		return COMP_NAME;
	}
	/**
	 * @param cOMP_NAME the cOMP_NAME to set
	 */
	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}
	/**
	 * @return the bUSI_NO
	 */
	public String getBUSI_NO() {
		return BUSI_NO;
	}
	/**
	 * @param bUSI_NO the bUSI_NO to set
	 */
	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}
	/**
	 * @return the bUSI_NAME
	 */
	public String getBUSI_NAME() {
		return BUSI_NAME;
	}
	/**
	 * @param bUSI_NAME the bUSI_NAME to set
	 */
	public void setBUSI_NAME(String bUSI_NAME) {
		BUSI_NAME = bUSI_NAME;
	}
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
	 * @return the eNTR_NO
	 */
	public String getENTR_NO() {
		return ENTR_NO;
	}
	/**
	 * @param eNTR_NO the eNTR_NO to set
	 */
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	/**
	 * @return the eNTR_NAME
	 */
	public String getENTR_NAME() {
		return ENTR_NAME;
	}
	/**
	 * @param eNTR_NAME the eNTR_NAME to set
	 */
	public void setENTR_NAME(String eNTR_NAME) {
		ENTR_NAME = eNTR_NAME;
	}
	/**
	 * @return the sUCC_TOT_NUM
	 */
	public long getSUCC_TOT_NUM() {
		return SUCC_TOT_NUM;
	}
	/**
	 * @param sUCC_TOT_NUM the sUCC_TOT_NUM to set
	 */
	public void setSUCC_TOT_NUM(long sUCC_TOT_NUM) {
		SUCC_TOT_NUM = sUCC_TOT_NUM;
	}
	/**
	 * @return the fAIL_TOT_NUM
	 */
	public long getFAIL_TOT_NUM() {
		return FAIL_TOT_NUM;
	}
	/**
	 * @param fAIL_TOT_NUM the fAIL_TOT_NUM to set
	 */
	public void setFAIL_TOT_NUM(long fAIL_TOT_NUM) {
		FAIL_TOT_NUM = fAIL_TOT_NUM;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SignStatsListResDTO [COMP_NO=" + COMP_NO + ", COMP_NAME=" + COMP_NAME + ", BUSI_NO=" + BUSI_NO
				+ ", BUSI_NAME=" + BUSI_NAME + ", SUB_BUSI_NO=" + SUB_BUSI_NO + ", SUB_BUSI_NAME=" + SUB_BUSI_NAME
				+ ", ENTR_NO=" + ENTR_NO + ", ENTR_NAME=" + ENTR_NAME + ", SUCC_TOT_NUM=" + SUCC_TOT_NUM
				+ ", FAIL_TOT_NUM=" + FAIL_TOT_NUM + "]";
	}
	

}
