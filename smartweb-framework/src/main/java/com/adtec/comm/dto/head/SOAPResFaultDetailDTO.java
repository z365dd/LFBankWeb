/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP报文的返回错误码信息明细
 * 类  名  称: SOAPResFaultDetailDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年6月28日 上午8:53:35<br>
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
public class SOAPResFaultDetailDTO {
	private String TxnStat;

	/**
	 * @return the TxnStat
	 */
	public String getTxnStat() {
		return TxnStat;
	}

	/**
	 * @param txnStat the txnStat to set
	 */
	public void setTxnStat(String TxnStat) {
		this.TxnStat = TxnStat;
	}
	
}
