/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP响应报文类
 * 类  名  称: SOAPResDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年6月28日 上午8:57:26<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto;

import com.adtec.comm.dto.head.SOAPResFaultDTO;
import com.adtec.comm.dto.head.SOAPResHeaderDTO;

/**
 * @author chenyl
 *
 */
public class SOAPResDTO<T> {
	/**
	 * 响应报文头
	 */
	private SOAPResHeaderDTO ResponseHeader;
	/**
	 * 响应报文体
	 */
	private T ResponseBody;
	/**
	 * 响应报文错误信息
	 */
	private SOAPResFaultDTO Fault;
	
	public SOAPResDTO(){}
	
	/**
	 * @param ResponseBody
	 */
	public SOAPResDTO(T ResponseBody) {
		super();
		this.ResponseHeader = new SOAPResHeaderDTO();
		this.Fault = new SOAPResFaultDTO();
		this.ResponseBody = ResponseBody;
	}

	/**
	 * @return the responseHeader
	 */
	public SOAPResHeaderDTO getResponseHeader() {
		return ResponseHeader;
	}

	/**
	 * @param responseHeader the responseHeader to set
	 */
	public void setResponseHeader(SOAPResHeaderDTO responseHeader) {
		ResponseHeader = responseHeader;
	}

	/**
	 * @return the responseBody
	 */
	public T getResponseBody() {
		return ResponseBody;
	}

	/**
	 * @param responseBody the responseBody to set
	 */
	public void setResponseBody(T responseBody) {
		ResponseBody = responseBody;
	}

	/**
	 * @return the fault
	 */
	public SOAPResFaultDTO getFault() {
		return Fault;
	}

	/**
	 * @param fault the fault to set
	 */
	public void setFault(SOAPResFaultDTO fault) {
		Fault = fault;
	}

}
