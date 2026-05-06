package com.adtec.comp.fsvr.dto;

public class TFmngTranSearchReqDTO {
	private String FLG;
	private String FILE_SVR_ID;
	private String SEARCH_TP;
	private String FILE_PATH;
	private String EXPR;
	
	public String getFLG() {
		return FLG;
	}
	public void setFLG(String fLG) {
		FLG = fLG;
	}
	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}
	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}
	public String getSEARCH_TP() {
		return SEARCH_TP;
	}
	public void setSEARCH_TP(String sEARCH_TP) {
		SEARCH_TP = sEARCH_TP;
	}
	public String getFILE_PATH() {
		return FILE_PATH;
	}
	public void setFILE_PATH(String fILE_PATH) {
		FILE_PATH = fILE_PATH;
	}
	public String getEXPR() {
		return EXPR;
	}
	public void setEXPR(String eXPR) {
		EXPR = eXPR;
	}
	
}
