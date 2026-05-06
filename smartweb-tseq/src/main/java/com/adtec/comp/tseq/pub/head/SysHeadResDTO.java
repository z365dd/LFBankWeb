/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求应用头
 * 类 名 称  : SysHeadResDTO.java
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

import java.util.ArrayList;

import com.alibaba.fastjson.annotation.JSONField;

public class SysHeadResDTO {
	/*交易状态:S-成功、F-失败*/
	@JSONField(name="TX_STAT")
	private String TX_STAT;
	/*中间业务云平台公共系统报文头子报文RES*/
	@JSONField(name="TX_RET")
	private ArrayList<SysHeadRetResDTO> TX_RET = new ArrayList<SysHeadRetResDTO>();
	
	/*修改新增*/
	/*交易状态:S-交易成功、F-交易失败、T-交易超时*/
	@JSONField(name="TRAN_STAT")
	private String TRAN_STAT;
	/*中间业务云平台公共系统报文头子报文RES*/
	/*交易返回码数组*/
	@JSONField(name="TRAN_RET")
	private ArrayList<SysHeadRetResDTO> TRAN_RET = new ArrayList<SysHeadRetResDTO>();
	
	public String getTRAN_STAT() {
		return TRAN_STAT;
	}
	public void setTRAN_STAT(String tRAN_STAT) {
		TRAN_STAT = tRAN_STAT;
	}
	public ArrayList<SysHeadRetResDTO> getTRAN_RET() {
		return TRAN_RET;
	}
	public void setTRAN_RET(ArrayList<SysHeadRetResDTO> tRAN_RET) {
		TRAN_RET = tRAN_RET;
	}
	
	/**
	 * 
	 */
	public SysHeadResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tX_STAT
	 * @param tX_RET
	 */
	public SysHeadResDTO(String tX_STAT, ArrayList<SysHeadRetResDTO> tX_RET) {
		super();
		TX_STAT = tX_STAT;
		TX_RET = tX_RET;
	}
	/**
	 * @return the tX_STAT
	 */
	public String getTX_STAT() {
		return TX_STAT;
	}
	/**
	 * @param tX_STAT the tX_STAT to set
	 */
	public void setTX_STAT(String tX_STAT) {
		TX_STAT = tX_STAT;
	}
	/**
	 * @return the tX_RET
	 */
	public ArrayList<SysHeadRetResDTO> getTX_RET() {
		return TX_RET;
	}
	/**
	 * @param tX_RET the tX_RET to set
	 */
	public void setTX_RET(ArrayList<SysHeadRetResDTO> tX_RET) {
		TX_RET = tX_RET;
	}	
	
}
