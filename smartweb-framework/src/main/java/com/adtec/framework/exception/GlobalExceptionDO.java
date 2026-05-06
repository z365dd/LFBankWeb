package com.adtec.framework.exception;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 统一异常返回数据对象
 * @author chenyl
 *
 */
@XStreamAlias("Root")
//@XmlRootElement(name="Root")
public class GlobalExceptionDO {
	/**
	 * 错误码
	 */
	private String errorNo;
	/**
	 * 错误信息
	 */
	private String errorInfo;
	/**
	 * 返回码
	 */
	private String returnCode;
	/**
	 * 返回信息
	 */
	private String message;
	/**
	 * 信息类型：error-错误、info-提示、loading-加载、success-成功、warning-警告
	 */
	private String msg_type;
	public String getErrorNo() {
		return errorNo;
	}
	@XmlElement
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	@XmlElement
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getReturnCode() {
		return returnCode;
	}
	@XmlElement
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getMessage() {
		return message;
	}
	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMsg_type() {
		return msg_type;
	}
	@XmlElement
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{errorNo="+errorNo+", errorInfo="+errorInfo+", returnCode="+returnCode+", message="+message+", msg_type="+msg_type+"}";
	}
	
	
}
