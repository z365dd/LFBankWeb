package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

public class PBusiDO  extends BaseDO{
	private String busiNo;
	private String entrNo;
	private String busiName;
	private String saleProdCode;
	private String stat;
	private String busiDesc;
	private String compNo;
	

	
	
	public String getCompNo() {
		return compNo;
	}



	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}



	public String getBusiNo() {
		return busiNo;
	}



	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}



	public String getEntrNo() {
		return entrNo;
	}



	public void setEntrNo(String entrNo) {
		this.entrNo = entrNo;
	}



	public String getBusiName() {
		return busiName;
	}



	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}



	public String getSaleProdCode() {
		return saleProdCode;
	}



	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}



	public String getStat() {
		return stat;
	}



	public void setStat(String stat) {
		this.stat = stat;
	}



	public String getBusiDesc() {
		return busiDesc;
	}



	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
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

}
