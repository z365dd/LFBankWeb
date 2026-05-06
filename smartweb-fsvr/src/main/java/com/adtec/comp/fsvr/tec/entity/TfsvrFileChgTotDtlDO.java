package com.adtec.comp.fsvr.tec.entity;

public class TfsvrFileChgTotDtlDO {
	private String chgNo;
	private String fmtName;
	private String fileFmt;
	private String compNo;
	private String tempFmtNo;
	private String fileCode;
	private String fmtDltSym;
	private String inHaveEnumConv;
	private String tranTp;
	private int enumNum;
	private int headNum;
	private int tailNum;
	private int bodyNum;
	TfsvrTemPlateDO tempData;
	TfsvrFilePlateDO fileData;
	
	public String getTranTp() {
		return tranTp;
	}
	public void setTranTp(String tranTp) {
		this.tranTp = tranTp;
	}
	public int getHeadNum() {
		return headNum;
	}
	public void setHeadNum(int headNum) {
		this.headNum = headNum;
	}
	public int getTailNum() {
		return tailNum;
	}
	public void setTailNum(int tailNum) {
		this.tailNum = tailNum;
	}
	public int getBodyNum() {
		return bodyNum;
	}
	public void setBodyNum(int bodyNum) {
		this.bodyNum = bodyNum;
	}
	public int getEnumNum() {
		return enumNum;
	}
	public void setEnumNum(int enumNum) {
		this.enumNum = enumNum;
	}
	public String getChgNo() {
		return chgNo;
	}
	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	public String getFmtName() {
		return fmtName;
	}
	public void setFmtName(String fmtName) {
		this.fmtName = fmtName;
	}
	public String getFileFmt() {
		return fileFmt;
	}
	public void setFileFmt(String fileFmt) {
		this.fileFmt = fileFmt;
	}
	public String getCompNo() {
		return compNo;
	}
	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	public String getTempFmtNo() {
		return tempFmtNo;
	}
	public void setTempFmtNo(String tempFmtNo) {
		this.tempFmtNo = tempFmtNo;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFmtDltSym() {
		return fmtDltSym;
	}
	public void setFmtDltSym(String fmtDltSym) {
		this.fmtDltSym = fmtDltSym;
	}
	public String getInHaveEnumConv() {
		return inHaveEnumConv;
	}
	public void setInHaveEnumConv(String inHaveEnumConv) {
		this.inHaveEnumConv = inHaveEnumConv;
	}
	public TfsvrTemPlateDO getTempData() {
		return tempData;
	}
	public void setTempData(TfsvrTemPlateDO tempData) {
		this.tempData = tempData;
	}
	public TfsvrFilePlateDO getFileData() {
		return fileData;
	}
	public void setFileData(TfsvrFilePlateDO fileData) {
		this.fileData = fileData;
	}
	
}
