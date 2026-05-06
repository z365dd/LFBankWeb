/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP报文对JSON
 * 类  名  称: JSONReqDTO.java
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
public class JSONReqDTO {
	/**
	 * 请求报文
	 */
	private SOAPReqDTO Request;

	/**
	 * @return the request
	 */
	public SOAPReqDTO getRequest() {
		return Request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(SOAPReqDTO request) {
		Request = request;
	}
	
}
