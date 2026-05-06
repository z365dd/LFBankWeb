package com.adtec.prod.oper.entity;



import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;


public class BusiDemoDO extends BaseDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String busiNo;
	private String name;
	private String saveName;
	private String fileType;
	private String previewPath;
	private String savePath;
	private String fileKind;
	
	public String getFileKind() {
		return fileKind;
	}
	public void setFileKind(String fileKind) {
		this.fileKind = fileKind;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBusiNo() {
		return busiNo;
	}
	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getPreviewPath() {
		return previewPath;
	}
	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	@Override
	public List<String> getIgnoreFields() {
		List<String> ignoreList = super.getIgnoreFields();
		ignoreList.add("rmrk");
		ignoreList.add("delFlg");
		return ignoreList;
	}
}
