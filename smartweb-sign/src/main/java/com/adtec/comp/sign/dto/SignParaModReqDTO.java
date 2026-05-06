package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class SignParaModReqDTO {
	
	private long NUM;
	private List<SignParaListResDTO> KEY_LIST = new ArrayList<SignParaListResDTO>();
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
	 * @return the kEY_LIST
	 */
	public List<SignParaListResDTO> getKEY_LIST() {
		return KEY_LIST;
	}
	/**
	 * @param kEY_LIST the kEY_LIST to set
	 */
	public void setKEY_LIST(List<SignParaListResDTO> kEY_LIST) {
		KEY_LIST = kEY_LIST;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SignParaQryResDTO [NUM=" + NUM + "]";
	}

}
