package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class ParaEntrAddReqDTO {
	
	private long ENTR_NUM;
	private List<ParaEntrPubListDTO> ENTR_LIST = new ArrayList<ParaEntrPubListDTO>();
	/**
	 * @return the eNTR_NUM
	 */
	public long getENTR_NUM() {
		return ENTR_NUM;
	}
	/**
	 * @param eNTR_NUM the eNTR_NUM to set
	 */
	public void setENTR_NUM(long eNTR_NUM) {
		ENTR_NUM = eNTR_NUM;
	}
	/**
	 * @return the eNTR_LIST
	 */
	public List<ParaEntrPubListDTO> getENTR_LIST() {
		return ENTR_LIST;
	}
	/**
	 * @param eNTR_LIST the eNTR_LIST to set
	 */
	public void setENTR_LIST(List<ParaEntrPubListDTO> eNTR_LIST) {
		ENTR_LIST = eNTR_LIST;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParaEntrAddReqDTO [ENTR_NUM=" + ENTR_NUM + ", ENTR_LIST=" + ENTR_LIST + "]";
	}

	
}
