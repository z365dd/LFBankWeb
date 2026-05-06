package com.adtec.prod.dto;

public class FPRodEntrRegRedisParaReqDTO {
	
	private String LEGA_NO;
	private String OPER_TP;
	

	public String getOPER_TP() {
		return OPER_TP;
	}

	public void setOPER_TP(String oPER_TP) {
		OPER_TP = oPER_TP;
	}

	/**
	 * @return the lEGA_NO
	 */
	public String getLEGA_NO() {
		return LEGA_NO;
	}

	/**
	 * @param lEGA_NO the lEGA_NO to set
	 */
	public void setLEGA_NO(String lEGA_NO) {
		LEGA_NO = lEGA_NO;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParaEntrNoCrtReqDTO [LEGA_NO=" + LEGA_NO + "]";
	}
	

}
