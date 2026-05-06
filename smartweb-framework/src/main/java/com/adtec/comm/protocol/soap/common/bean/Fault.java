package com.adtec.comm.protocol.soap.common.bean;

public class Fault {

	protected String faultCode;
	protected String faultString;

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String value) {
		this.faultCode = value;
	}

	public String getFaultString() {
		return faultString;
	}

	public void setFaultString(String value) {
		this.faultString = value;
	}

	/**
	 * 返回错误码及错误信息
	 * 
	 * @return
	 */
	public String getErrMsg() {
		return "[" + this.getFaultCode()
				+ "]:" + this.getFaultString();
	}

}
