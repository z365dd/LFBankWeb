package com.adtec.comp.ctrl.test.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class TCtrlFileMsgDo extends BaseDO{
	private static final long serialVersionUID = 1L;
	private String caseNo;
	private String caseName; //案例名 -- 对于一个文件名
	private String fileName; //文件名
	private String filePath; //文件路径
	private String flg;      //标识 ctrl 或 auth
	private String userName; //所属用户
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFlg() {
		return flg;
	}
	public void setFlg(String flg) {
		this.flg = flg;
	}
	
	public String toString(){
		return "TCtrlTranDo ["+"id:"+id+",fileName:"+fileName+",flg:"+flg+"]";
	}
}
