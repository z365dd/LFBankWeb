package com.adtec.prod.oper.entity;

public class BusiTypeDo {
	/**
	 * 业务类型id
	 */
	private String busiTypeId;
	/**
	 * 业务类型名称
	 */
	private String busiTypeName;
	/**
	 * 拓展字段
	 */
	private String remark;
	public String getBusiTypeId() {
		return busiTypeId;
	}
	public void setBusiTypeId(String busiTypeId) {
		this.busiTypeId = busiTypeId;
	}
	public String getBusiTypeName() {
		return busiTypeName;
	}
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
