package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class ProdAttrDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    private String COMP_NO;
    private String COMP_NAME;
    private String PROD_COMP_TP;
    private String OPEN_STAT;
    private String SHORT_RMRK;
    private String MID_RMRK;
    private String LONG_RMRK;
    public String getLONG_RMRK() {
		return LONG_RMRK;
	}


	public void setLONG_RMRK(String lONG_RMRK) {
		LONG_RMRK = lONG_RMRK;
	}


	private String DAC;
   
	
	public String getCOMP_NO() {
		return COMP_NO;
	}


	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}


	public String getCOMP_NAME() {
		return COMP_NAME;
	}


	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}

	public String getPROD_COMP_TP() {
		return PROD_COMP_TP;
	}

	public void setPROD_COMP_TP(String PROD_COMP_TP) {
		this.PROD_COMP_TP = PROD_COMP_TP;
	}

	public String getOPEN_STAT() {
		return OPEN_STAT;
	}

	public void setOPEN_STAT(String OPEN_STAT) {
		this.OPEN_STAT = OPEN_STAT;
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


	public String getDAC() {
		return DAC;
	}


	public void setDAC(String dAC) {
		DAC = dAC;
	}
	
	/**
	 * 覆盖 设置忽略字段
	 */
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
	        return ignoreFields;
	    }
	  
	  /**
	   * 覆盖 设置匹配条件字段
	   */
	  public List<String> getMatchFields(){
			//设置更新匹配条件
			List<String> matchField = new ArrayList<String>();
			matchField.add("COMP_NO");
			return matchField;
		}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
