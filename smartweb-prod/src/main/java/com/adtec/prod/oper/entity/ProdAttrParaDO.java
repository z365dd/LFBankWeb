package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class ProdAttrParaDO extends BaseDO{
	 private static final long serialVersionUID = 1L;
	 private String COMP_NO;
	 private String SER;
	 private String COMP_NAME;
//	 private String KEY_TYPE;	(字段更改)
	 private String KEY_NO;
	 private String KEY_NAME;
	 private String KV;		//键值
	 private String FLG;	//标志
	 private String SHORT_RMRK;
	 private String MID_RMRK;
	 private String LONG_RMRK;
	 private String DAC;
	 private String MOD_STAT;
	 private String KEY_DESC;	//键值描述
	 private String KEY_TP;		//属性类型
	 private String ENTER_TP;	//控件类型
	 

	public String getENTER_TP() {
		return ENTER_TP;
	}

	public void setENTER_TP(String eNTER_TP) {
		ENTER_TP = eNTER_TP;
	}

	public String getKEY_TP() {
		return KEY_TP;
	}

	public void setKEY_TP(String kEY_TP) {
		KEY_TP = kEY_TP;
	}

	public String getCOMP_NO() {
		return COMP_NO;
	}

	public String getMOD_STAT() {
		return MOD_STAT;
	}

	public void setMOD_STAT(String mOD_STAT) {
		MOD_STAT = mOD_STAT;
	}

	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}

	public String getSER() {
		return SER;
	}

	public void setSER(String sER) {
		SER = sER;
	}

	public String getCOMP_NAME() {
		return COMP_NAME;
	}

	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}

	public String getKEY_NO() {
		return KEY_NO;
	}

	public void setKEY_NO(String kEY_NO) {
		KEY_NO = kEY_NO;
	}

	public String getKEY_NAME() {
		return KEY_NAME;
	}

	public void setKEY_NAME(String kEY_NAME) {
		KEY_NAME = kEY_NAME;
	}

	public String getKV() {
		return KV;
	}

	public void setKV(String kV) {
		KV = kV;
	}

	public String getFLG() {
		return FLG;
	}

	public void setFLG(String fLG) {
		FLG = fLG;
	}

	public String getSHORT_RMRK() {
		return SHORT_RMRK;
	}

	public void setSHORT_RMRK(String sHORT_RMRK) {
		SHORT_RMRK = sHORT_RMRK;
	}

	public String getMID_RMRK() {
		return MID_RMRK;
	}

	public void setMID_RMRK(String mID_RMRK) {
		MID_RMRK = mID_RMRK;
	}

	public String getLONG_RMRK() {
		return LONG_RMRK;
	}

	public void setLONG_RMRK(String lONG_RMRK) {
		LONG_RMRK = lONG_RMRK;
	}

	public String getDAC() {
		return DAC;
	}

	public void setDAC(String dAC) {
		DAC = dAC;
	}
	
	  public String getKEY_DESC() {
		return KEY_DESC;
	}

	public void setKEY_DESC(String kEY_DESC) {
		KEY_DESC = kEY_DESC;
	}

	public List<String> getIgnoreFields() {
	        List<String> ignoreFields = super.getIgnoreFields();
	        ignoreFields.add("serialVersionUID");
	        ignoreFields.add("DEL_FLAG_NORMAL");
	        ignoreFields.add("DEL_FLAG_DELETE");
	        ignoreFields.add("id");
	        ignoreFields.add("crtr");
	        ignoreFields.add("crtTime");
	        ignoreFields.add("uptr");
	        ignoreFields.add("uptTime");
	        ignoreFields.add("rmrk");
	        ignoreFields.add("delFlg");
	        ignoreFields.add("LIST");
	        ignoreFields.add("ELEM_KEY");
	        ignoreFields.add("ELEM_NAME");
	        ignoreFields.add("ELEM_KV");
	        ignoreFields.add("ENTER_TP");
	        ignoreFields.add("MOD_STAT");
	        return ignoreFields;
	    }
	  public List<String> getMatchFields() {
	        List<String> matchField = new ArrayList<String>();
	        matchField.add("KEY_NO");
	        return matchField;
	    }
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
