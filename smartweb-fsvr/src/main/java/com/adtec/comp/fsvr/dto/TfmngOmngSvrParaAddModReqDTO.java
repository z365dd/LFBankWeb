package com.adtec.comp.fsvr.dto;

import java.util.List;

public class TfmngOmngSvrParaAddModReqDTO {
	private String TRAN_TP;
	
//	private String FILE_SVR_NO;
	private String SVR_DESC;
	private String FILE_SVR_TP;
	private String COMM_PROT_GRP_TP;
	private String OPEN_SVC_FLG;
	private String IP;
	private String PORT;
	private String USER_NO;
	private String PWD;
	private String DOWNLOAD_FILE_PATH;
	private String UPLOAD_FILE_PATH;
	private String MEMB_ID;
	private String FILE_SVR_ID;
	private String CONT_FLG;
	
	private List<TfmngOmngSvrParaAddModListReqDTO> LIST ;
	
	
	
	public List<TfmngOmngSvrParaAddModListReqDTO> getLIST() {
		return LIST;
	}
	public void setLIST(List<TfmngOmngSvrParaAddModListReqDTO> lIST) {
		LIST = lIST;
	}
	public String getCONT_FLG() {
		return CONT_FLG;
	}
	public void setCONT_FLG(String cONT_FLG) {
		CONT_FLG = cONT_FLG;
	}
	public String getFILE_SVR_ID() {
		return FILE_SVR_ID;
	}
	public void setFILE_SVR_ID(String fILE_SVR_ID) {
		FILE_SVR_ID = fILE_SVR_ID;
	}
	public String getMEMB_ID() {
		return MEMB_ID;
	}
	public void setMEMB_ID(String mEMB_ID) {
		MEMB_ID = mEMB_ID;
	}
	public String getCOMM_PROT_GRP_TP() {
		return COMM_PROT_GRP_TP;
	}
	public void setCOMM_PROT_GRP_TP(String cOMM_PROT_GRP_TP) {
		COMM_PROT_GRP_TP = cOMM_PROT_GRP_TP;
	}
	public String getDOWNLOAD_FILE_PATH() {
		return DOWNLOAD_FILE_PATH;
	}
	public void setDOWNLOAD_FILE_PATH(String dOWNLOAD_FILE_PATH) {
		DOWNLOAD_FILE_PATH = dOWNLOAD_FILE_PATH;
	}
	public String getUPLOAD_FILE_PATH() {
		return UPLOAD_FILE_PATH;
	}
	public void setUPLOAD_FILE_PATH(String uPLOAD_FILE_PATH) {
		UPLOAD_FILE_PATH = uPLOAD_FILE_PATH;
	}
	private String STAT;
	public String getTRAN_TP() {
		return TRAN_TP;
	}
	public void setTRAN_TP(String tRAN_TP) {
		TRAN_TP = tRAN_TP;
	}
//	public String getFILE_SVR_NO() {
//		return FILE_SVR_NO;
//	}
//	public void setFILE_SVR_NO(String fILE_SVR_NO) {
//		FILE_SVR_NO = fILE_SVR_NO;
//	}
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
	public String getOPEN_SVC_FLG() {
		return OPEN_SVC_FLG;
	}
	public void setOPEN_SVC_FLG(String oPEN_SVC_FLG) {
		OPEN_SVC_FLG = oPEN_SVC_FLG;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String pORT) {
		PORT = pORT;
	}
	public String getUSER_NO() {
		return USER_NO;
	}
	public void setUSER_NO(String uSER_NO) {
		USER_NO = uSER_NO;
	}
	public String getPWD() {
		return PWD;
	}
	public void setPWD(String pWD) {
		PWD = pWD;
	}
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	
	
}
