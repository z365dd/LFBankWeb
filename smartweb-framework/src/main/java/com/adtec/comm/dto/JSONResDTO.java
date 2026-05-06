/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP报文对JSON
 * 类  名  称: JSONResDTO.java
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
public class JSONResDTO {
	/**
	 * 响应报文
	 */
	private SOAPResDTO Response;

	/**
	 * @return the response
	 */
	public SOAPResDTO getResponse() {
		return Response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(SOAPResDTO response) {
		Response = response;
	}

	
}
