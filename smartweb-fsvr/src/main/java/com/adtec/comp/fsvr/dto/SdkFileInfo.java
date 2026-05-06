package com.adtec.comp.fsvr.dto;

public class SdkFileInfo {
	
	private String fileName;
	private String seqNo;
	private String fileSeqSet;
	private long totSize;
	private long cmpleSize;
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * @return the totSize
	 */
	public long getTotSize() {
		return totSize;
	}
	/**
	 * @param totSize the totSize to set
	 */
	public void setTotSize(long totSize) {
		this.totSize = totSize;
	}
	/**
	 * @return the cmpleSize
	 */
	public long getCmpleSize() {
		return cmpleSize;
	}
	/**
	 * @param cmpleSize the cmpleSize to set
	 */
	public void setCmpleSize(long cmpleSize) {
		this.cmpleSize = cmpleSize;
	}
	public String getFileSeqSet() {
		return fileSeqSet;
	}
	public void setFileSeqSet(String fileSeqSet) {
		this.fileSeqSet = fileSeqSet;
	}

}
