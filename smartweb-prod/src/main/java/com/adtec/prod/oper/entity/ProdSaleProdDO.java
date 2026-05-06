package com.adtec.prod.oper.entity;



import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;


public class ProdSaleProdDO extends BaseDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String saleProdCode;
	private String name;
	private String saveName;
	private String fileType;
	private String savePath;
	private String previewPath;
	
	public String getPreviewPath() {
		return previewPath;
	}
	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}
	public String getSaleProdCode() {
		return saleProdCode;
	}
	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
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
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public List<String> getIgnoreFields() {
		List<String> ignoreList = super.getIgnoreFields();
		ignoreList.add("rmrk");
		ignoreList.add("delFlg");
		return ignoreList;
	}
}
