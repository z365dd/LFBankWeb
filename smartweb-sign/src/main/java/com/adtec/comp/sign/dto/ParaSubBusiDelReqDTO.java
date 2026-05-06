package com.adtec.comp.sign.dto;

import java.util.ArrayList;
import java.util.List;

public class ParaSubBusiDelReqDTO {

	private long BUSI_NUM;
	private List<ParaSubBusiPubDTO> BUSI_LIST = new ArrayList<ParaSubBusiPubDTO>();
	/**
	 * @return the bUSI_NUM
	 */
	public long getBUSI_NUM() {
		return BUSI_NUM;
	}
	/**
	 * @param bUSI_NUM the bUSI_NUM to set
	 */
	public void setBUSI_NUM(long bUSI_NUM) {
		BUSI_NUM = bUSI_NUM;
	}
	/**
	 * @return the bUSI_LIST
	 */
	public List<ParaSubBusiPubDTO> getBUSI_LIST() {
		return BUSI_LIST;
	}
	/**
	 * @param bUSI_LIST the bUSI_LIST to set
	 */
	public void setBUSI_LIST(List<ParaSubBusiPubDTO> bUSI_LIST) {
		BUSI_LIST = bUSI_LIST;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParaSubBusiAddReqDTO [BUSI_NUM=" + BUSI_NUM + ", BUSI_LIST=" + BUSI_LIST + "]";
	}
	
}
