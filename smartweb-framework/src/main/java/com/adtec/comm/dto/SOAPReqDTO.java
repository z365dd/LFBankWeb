/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP请求报文类
 * 类  名  称: SOAPReqDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年6月28日 上午8:55:23<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto;

import com.adtec.comm.dto.head.SOAPReqHeaderDTO;

/**
 * @author chenyl
 *
 */
public class SOAPReqDTO<T> {
	/**
	 * 请求报文头
	 */
	private SOAPReqHeaderDTO RequestHeader;
	/**
	 * 请求报文体
	 */
	private T RequestBody;
	
	public SOAPReqDTO(){}
	
	/**
	 * @param requestHeader
	 * @param requestBody
	 */
	public SOAPReqDTO(T RequestBody) {
		super();
		this.RequestHeader = new SOAPReqHeaderDTO();
		this.RequestBody = RequestBody;
	}

	/**
	 * @return the requestHeader
	 */
	public SOAPReqHeaderDTO getRequestHeader() {
		return RequestHeader;
	}

	/**
	 * @param requestHeader the requestHeader to set
	 */
	public void setRequestHeader(SOAPReqHeaderDTO requestHeader) {
		RequestHeader = requestHeader;
	}

	/**
	 * @return the requestBody
	 */
	public T getRequestBody() {
		return RequestBody;
	}

	/**
	 * @param requestBody the requestBody to set
	 */
	public void setRequestBody(T requestBody) {
		RequestBody = requestBody;
	}

}
