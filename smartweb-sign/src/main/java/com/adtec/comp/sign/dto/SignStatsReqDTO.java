package com.adtec.comp.sign.dto;

public class SignStatsReqDTO {
	
	private String COMP_NO;
	private String BUSI_NO;
	private String SUB_BUSI_NO;
	private String ENTR_NO;
	private String STR_DATE;
	private String END_DATE;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SignStatsReqDTO [COMP_NO=" + COMP_NO + ", BUSI_NO=" + BUSI_NO + ", SUB_BUSI_NO=" + SUB_BUSI_NO
				+ ", ENTR_NO=" + ENTR_NO + ", STR_DATE=" + STR_DATE + ", END_DATE=" + END_DATE + "]";
	}


}
