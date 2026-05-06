package com.adtec.prod.oper.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusiSignDocDO implements Serializable{
	private static final long serialVersionUID = -3834961766549088035L;
	private String busiNo;
	private String ser;
	private String docName;
	//文档类型1代表业务需求文档，2代表接口文档，3其他文档
	private String docType;
	private String url;
	public String getBusiNo() {
		return busiNo;
	}
	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	public String getSer() {
		return ser;
	}
	public void setSer(String ser) {
		this.ser = ser;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getIgnoreFields(){
		List<String> list = new ArrayList<String>();
		list.add("serialVersionUID");
		return list;
	}
	
}
