package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

public class FProdSaleProdPageViewResListKeyListDTO {
	private String KEY_NO;
	private String KEY_NAME;
	private String LINE_SER;
	private String COL_SER;
	private String FLG;
	private String VAL_TP;
	private String VAL_LEN;
	private String KEY_TP;
	private String INPUT_FLG;
	private String DEFA_KV;

	private List<FProdSaleProdPageViewResListKeyListCtrlListDTO> CTRL_LIST = new ArrayList<FProdSaleProdPageViewResListKeyListCtrlListDTO>();
	private String KEY_FLG;

	public String getKEY_NO() {
		return KEY_NO;
	}

	public void setKEY_NO(String KEY_NO) {
		this.KEY_NO = KEY_NO;
	}

	public String getKEY_NAME() {
		return KEY_NAME;
	}

	public void setKEY_NAME(String KEY_NAME) {
		this.KEY_NAME = KEY_NAME;
	}

	public String getLINE_SER() {
		return LINE_SER;
	}

	public void setLINE_SER(String LINE_SER) {
		this.LINE_SER = LINE_SER;
	}

	public String getCOL_SER() {
		return COL_SER;
	}

	public void setCOL_SER(String COL_SER) {
		this.COL_SER = COL_SER;
	}

	public String getFLG() {
		return FLG;
	}

	public void setFLG(String FLG) {
		this.FLG = FLG;
	}

	public String getVAL_TP() {
		return VAL_TP;
	}

	public void setVAL_TP(String VAL_TP) {
		this.VAL_TP = VAL_TP;
	}

	public String getVAL_LEN() {
		return VAL_LEN;
	}

	public void setVAL_LEN(String VAL_LEN) {
		this.VAL_LEN = VAL_LEN;
	}

	public String getKEY_TP() {
		return KEY_TP;
	}

	public void setKEY_TP(String KEY_TP) {
		this.KEY_TP = KEY_TP;
	}

	public String getINPUT_FLG() {
		return INPUT_FLG;
	}

	public void setINPUT_FLG(String INPUT_FLG) {
		this.INPUT_FLG = INPUT_FLG;
	}

	public String getDEFA_KV() {
		return DEFA_KV;
	}

	public void setDEFA_KV(String DEFA_KV) {
		this.DEFA_KV = DEFA_KV;
	}

	public List<FProdSaleProdPageViewResListKeyListCtrlListDTO> getCTRL_LIST() {
		return CTRL_LIST;
	}

	public void setCTRL_LIST(List<FProdSaleProdPageViewResListKeyListCtrlListDTO> CTRL_LIST) {
		this.CTRL_LIST = CTRL_LIST;
	}

	public String getKEY_FLG() {
		return KEY_FLG;
	}

	public void setKEY_FLG(String KEY_FLG) {
		this.KEY_FLG = KEY_FLG;
	}
}
