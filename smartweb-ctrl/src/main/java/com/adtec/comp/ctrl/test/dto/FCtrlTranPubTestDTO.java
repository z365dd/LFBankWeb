package com.adtec.comp.ctrl.test.dto;

public class FCtrlTranPubTestDTO {
	private String COMP_NO;
	private String SVC_CODE;
	private String SUB_SVC_CODE;
	/*公用维度 json格式 PUB_DIM_LIST:[{DIM_KEY:xxx,DIM_KV:xxx},{...}]*/
	private String PUB_DIM_LIST;
	private String PRI_DIM_LIST;
	private String TRL_LIST;
	private double TRAN_AMT;
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getSUB_SVC_CODE() {
		return SUB_SVC_CODE;
	}
	public void setSUB_SVC_CODE(String sUB_SVC_CODE) {
		SUB_SVC_CODE = sUB_SVC_CODE;
	}
	public String getPUB_DIM_LIST() {
		return PUB_DIM_LIST;
	}
	public void setPUB_DIM_LIST(String pUB_DIM_LIST) {
		PUB_DIM_LIST = pUB_DIM_LIST;
	}
	public String getPRI_DIM_LIST() {
		return PRI_DIM_LIST;
	}
	public void setPRI_DIM_LIST(String pRI_DIM_LIST) {
		PRI_DIM_LIST = pRI_DIM_LIST;
	}
	public String getTRL_LIST() {
		return TRL_LIST;
	}
	public void setTRL_LIST(String tRL_LIST) {
		TRL_LIST = tRL_LIST;
	}
	public double getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(double tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
}
