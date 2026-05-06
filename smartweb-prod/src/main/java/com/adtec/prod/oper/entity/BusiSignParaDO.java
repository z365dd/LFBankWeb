package com.adtec.prod.oper.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusiSignParaDO implements Serializable{
	private static final long serialVersionUID = 3294058005228224396L;
	private String busiNo;
	private String busiName;
	private String keyType;
	private String keyNo;
	private String keyName;
	private String kv;
	public String getBusiNo() {
		return busiNo;
	}
	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	public String getBusiName() {
		return busiName;
	}
	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getKeyNo() {
		return keyNo;
	}
	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKv() {
		return kv;
	}
	public void setKv(String kv) {
		this.kv = kv;
	}
	
	public List<String> getIgnoreFields(){
		List<String> list = new ArrayList<String>();
		list.add("serialVersionUID");
		return list;
	}
}
