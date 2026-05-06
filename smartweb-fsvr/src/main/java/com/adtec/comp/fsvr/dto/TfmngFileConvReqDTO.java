package com.adtec.comp.fsvr.dto;

import java.util.List;

public class TfmngFileConvReqDTO {
	private String chgNo;//转换号
	private String tempFmtNo;//模板格式号
	private String fmtName;//文件格式转换名称
	private String fileCode;//文件编码
	private String fileFmt;//格式类型
	private String fmtDltSym;//分隔符
	private String isHaveHead;//是否有头 1-有 2-无
	private String isHaveTail;//是否有尾 1-有 2-无
	private Long headNum;//头数量     有头默认送1
	private Long tailNum;//尾数量     有尾默认送1
	private String isHaveEnumConv;//是否有枚举转换 1-有 2-无
	
	private List<TfmngFileDtlReqDTO> list ;
	
	public String getChgNo() {
		return chgNo;
	}

	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}

	public String getTempFmtNo() {
		return tempFmtNo;
	}

	public void setTempFmtNo(String tempFmtNo) {
		this.tempFmtNo = tempFmtNo;
	}

	public String getFmtName() {
		return fmtName;
	}

	public void setFmtName(String fmtName) {
		this.fmtName = fmtName;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileFmt() {
		return fileFmt;
	}

	public void setFileFmt(String fileFmt) {
		this.fileFmt = fileFmt;
	}

	public List<TfmngFileDtlReqDTO> getList() {
		return list;
	}

	public void setList(List<TfmngFileDtlReqDTO> list) {
		this.list = list;
	}

	public String getFmtDltSym() {
		return fmtDltSym;
	}

	public void setFmtDltSym(String fmtDltSym) {
		this.fmtDltSym = fmtDltSym;
	}

	public String getIsHaveHead() {
		return isHaveHead;
	}

	public void setIsHaveHead(String isHaveHead) {
		this.isHaveHead = isHaveHead;
	}

	public String getIsHaveTail() {
		return isHaveTail;
	}

	public void setIsHaveTail(String isHaveTail) {
		this.isHaveTail = isHaveTail;
	}

	public Long getHeadNum() {
		return headNum;
	}

	public void setHeadNum(Long headNum) {
		this.headNum = headNum;
	}

	public Long getTailNum() {
		return tailNum;
	}

	public void setTailNum(Long tailNum) {
		this.tailNum = tailNum;
	}

	public String getIsHaveEnumConv() {
		return isHaveEnumConv;
	}

	public void setIsHaveEnumConv(String isHaveEnumConv) {
		this.isHaveEnumConv = isHaveEnumConv;
	}


}
