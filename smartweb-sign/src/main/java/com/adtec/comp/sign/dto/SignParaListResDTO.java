package com.adtec.comp.sign.dto;

public class SignParaListResDTO {
	
	private String KEY;
	private String KEY_NAME;
	private String KV;
	/**
	 * @return the kEY
	 */
	public String getKEY() {
		return KEY;
	}
	/**
	 * @param kEY the kEY to set
	 */
	public void setKEY(String kEY) {
		KEY = kEY;
	}
	/**
	 * @return the kEY_NAME
	 */
	public String getKEY_NAME() {
		return KEY_NAME;
	}
	/**
	 * @param kEY_NAME the kEY_NAME to set
	 */
	public void setKEY_NAME(String kEY_NAME) {
		KEY_NAME = kEY_NAME;
	}
	/**
	 * @return the kV
	 */
	public String getKV() {
		return KV;
	}
	/**
	 * @param kV the kV to set
	 */
	public void setKV(String kV) {
		KV = kV;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SignParaQryListResDTO [KEY=" + KEY + ", KEY_NAME=" + KEY_NAME + ", KV=" + KV + "]";
	}
	
}
