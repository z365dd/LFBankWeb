package com.adtec.framework.common.util;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.adtec.framework.exception.SysErr;

/**
 * 响应工具类
 * 
 * @author chenyl
 * @date 2019年4月10日 下午12:48:55
 * @param <T> 需要返回的类型
 */
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
//保证序列化json的时候,如果是null的对象,key也会消失
public class ServerResponse<T> implements Serializable {

	private static final long serialVersionUID = 4269744012236999698L;
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";

	private String returnCode;
	private String errorNo;
	private String errorInfo;
	private String msgType;
	private String message;
	private T data;

	public ServerResponse(String errorNo, String message) {
		this.returnCode = errorNo;
		this.errorNo = errorNo;
		this.errorInfo = message;
		this.msgType = "success";
		this.message = message;
	}

	public ServerResponse(String errorNo, String message, T data) {
		this.returnCode = errorNo;
		this.errorNo = errorNo;
		this.errorInfo = message;
		this.msgType = SysErr.E_SUCCESS.equals(errorNo) ? SUCCESS : ERROR;
		this.message = message;
		this.data = data;
	}

	// 使之不在json序列化结果当中
	@JsonIgnore
	public boolean isSuccess() {
		return SysErr.E_SUCCESS.equals(errorNo);
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getErrorNo() {
		return errorNo;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public String getMsg_type() {
		return msgType;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public static <T> ServerResponse<T> createBySuccessMessage(String message) {
		return new ServerResponse<T>(SysErr.E_SUCCESS, message);
	}

	public static <T> ServerResponse<T> createBySuccess(String message, T data) {
		return new ServerResponse<T>(SysErr.E_SUCCESS, message, data);
	}

	public static <T> ServerResponse<T> createByErrorCodeMessage(String errorNo, String errorMessage) {
		return new ServerResponse<T>(errorNo, errorMessage);
	}

}
