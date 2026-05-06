package com.adtec.comp.fsvr.dto;

public class TfmngOmngSvrParaQryReqDTO {
	private String FILE_SVR_ID;
	private String SVR_DESC;
	private String FILE_SVR_TP;
	private String COMM_PROT_GRP_TP;
	private String IP;
	private String STAT;
	
	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}
	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}
	public String getSVR_DESC() {
		return SVR_DESC;
	}
	public void setSVR_DESC(String sVR_DESC) {
		SVR_DESC = sVR_DESC;
	}
	public String getFILE_SVR_TP() {
		return FILE_SVR_TP;
	}
	public void setFILE_SVR_TP(String fILE_SVR_TP) {
		FILE_SVR_TP = fILE_SVR_TP;
	}
	public String getCOMM_PROT_GRP_TP() {
		return COMM_PROT_GRP_TP;
	}
	public void setCOMM_PROT_GRP_TP(String cOMM_PROT_GRP_TP) {
		COMM_PROT_GRP_TP = cOMM_PROT_GRP_TP;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	
}
