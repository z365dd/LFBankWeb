package com.adtec.comp.fsvr.dto;

public class FileMsgDTO {
	/*private static final long serialVersionUID = 1L;*/
	private String fileName;//文件名称
	private String saveName;//保存名称
	private String type;//类型
	private String path;//保存路径
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
