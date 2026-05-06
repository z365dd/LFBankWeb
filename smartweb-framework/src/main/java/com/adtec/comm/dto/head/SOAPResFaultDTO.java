/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP报文的返回错误码信息
 * 类  名  称: SOAPResFaultDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年6月28日 上午8:52:09<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto.head;

/**
 * @author chenyl
 *
 */
public class SOAPResFaultDTO {
	private String FaultCode;
	private String FaultString;
	private SOAPResFaultDetailDTO Detail;
	/**
	 * @return the faultCode
	 */
	public String getFaultCode() {
		return FaultCode;
	}
	/**
	 * @param faultCode the faultCode to set
	 */
	public void setFaultCode(String faultCode) {
		FaultCode = faultCode;
	}
	/**
	 * @return the faultString
	 */
	public String getFaultString() {
		return FaultString;
	}
	/**
	 * @param faultString the faultString to set
	 */
	public void setFaultString(String faultString) {
		FaultString = faultString;
	}
	/**
	 * @return the detail
	 */
	public SOAPResFaultDetailDTO getDetail() {
		return Detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(SOAPResFaultDetailDTO detail) {
		Detail = detail;
	}
		
}
