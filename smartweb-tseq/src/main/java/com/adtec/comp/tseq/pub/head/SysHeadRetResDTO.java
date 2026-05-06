/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求应用头
 * 类 名 称  : SysHeadRetResDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: ruanyh@ <br>
 * 开发时间: 2019年3月25日 下午14:00:00<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.tseq.pub.head;

import com.alibaba.fastjson.annotation.JSONField;

public class SysHeadRetResDTO {
	/*交易返回代码*/
	@JSONField(name="RET_CODE")
	private String RET_CODE;
	/*交易返回信息*/
	@JSONField(name="RET_MSG")
	private String RET_MSG;
	/**
	 * 
	 */
	public SysHeadRetResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param rET_CODE
	 * @param rET_MSG
	 */
	public SysHeadRetResDTO(String rET_CODE, String rET_MSG) {
		super();
		RET_CODE = rET_CODE;
		RET_MSG = rET_MSG;
	}
	/**
	 * @return the rET_CODE
	 */
	public String getRET_CODE() {
		return RET_CODE;
	}
	/**
	 * @param rET_CODE the rET_CODE to set
	 */
	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}
	/**
	 * @return the rET_MSG
	 */
	public String getRET_MSG() {
		return RET_MSG;
	}
	/**
	 * @param rET_MSG the rET_MSG to set
	 */
	public void setRET_MSG(String rET_MSG) {
		RET_MSG = rET_MSG;
	}
	
}
