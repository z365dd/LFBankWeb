/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求应用头
 * 类 名 称  : SysHeadReqDTO.java
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

public class SysHeadReqDTO {
	/*请求组件/模型编号*/
	@JSONField(name="REQ_COMP_NO")
	private String REQ_COMP_NO;
	/*请求组件/模型节点号*/
	@JSONField(name="REQ_NODE_NO")
	private String REQ_NODE_NO;
	/*请求方IP*/
	@JSONField(name="REQ_IP")
	private String REQ_IP;
	/*服务请求方日期*/
	@JSONField(name="REQ_DATE")
	private String REQ_DATE;
	/*服务请求方服务码*/
	@JSONField(name="REQ_SVC_CODE")
	private String REQ_SVC_CODE;
	/*服务请求发送方流水号*/
	@JSONField(name="REQ_SEQ")
	private String REQ_SEQ;
	/*发起方组件/模型编号*/
	@JSONField(name="SND_COMP_NO")
	private String SND_COMP_NO;
	/*发起方节点号*/
	@JSONField(name="SND_NODE_NO")
	private String SND_NODE_NO;
	/*发送方交易日期*/
	@JSONField(name="SND_DATE")
	private String SND_DATE;
	/*内部全局流水号*/
	@JSONField(name="SND_SEQ")
	private String SND_SEQ;
	/*外部日期*/
	@JSONField(name="OUT_DATE")
	private String OUT_DATE;
	/*外部时间*/
	@JSONField(name="OUT_TIME")
	private String OUT_TIME;
	/*外部系统编号*/
	@JSONField(name="OUT_SYS")
	private String OUT_SYS;
	/*外部系统流水号*/
	@JSONField(name="OUT_SEQ")
	private String OUT_SEQ;
	/*请求方平台流水号*/
	@JSONField(name="REQ_PLAT_SEQ")
	private String REQ_PLAT_SEQ;
	/*服务版本号*/
	@JSONField(name="VER_NO")
	private String VER_NO;
	@JSONField(name="MACH_DATE")
	private String MACH_DATE;
	
	public String getREQ_COMP_NO() {
		return REQ_COMP_NO;
	}

	public void setREQ_COMP_NO(String rEQ_COMP_NO) {
		REQ_COMP_NO = rEQ_COMP_NO;
	}

	public String getREQ_SEQ() {
		return REQ_SEQ;
	}

	public void setREQ_SEQ(String rEQ_SEQ) {
		REQ_SEQ = rEQ_SEQ;
	}

	public String getSND_COMP_NO() {
		return SND_COMP_NO;
	}

	public void setSND_COMP_NO(String sND_COMP_NO) {
		SND_COMP_NO = sND_COMP_NO;
	}

	public String getSND_SEQ() {
		return SND_SEQ;
	}

	public void setSND_SEQ(String sND_SEQ) {
		SND_SEQ = sND_SEQ;
	}

	public String getOUT_SYS() {
		return OUT_SYS;
	}

	public void setOUT_SYS(String oUT_SYS) {
		OUT_SYS = oUT_SYS;
	}

	public String getOUT_SEQ() {
		return OUT_SEQ;
	}

	public void setOUT_SEQ(String oUT_SEQ) {
		OUT_SEQ = oUT_SEQ;
	}

	public SysHeadReqDTO() {}

	/**
	 * @param rEQ_COMP_NO
	 * @param rEQ_NODE_NO
	 * @param rEQ_IP
	 * @param rEQ_DATE
	 * @param rEQ_SVC_CODE
	 * @param rEQ_SEQ_NO
	 * @param sND_COMP_NO
	 * @param sND_NODE_NO
	 * @param sND_DATE
	 * @param sEQ_NO
	 * @param oUT_DATE
	 * @param oUT_TIME
	 * @param oUT_SYS_NO
	 * @param oUT_SEQ_NO
	 * @param rEQ_PLAT_SEQ
	 * @param vER_NO
	 */
	public SysHeadReqDTO(String rEQ_COMP_NO, String rEQ_NODE_NO, String rEQ_IP, String rEQ_DATE, String rEQ_SVC_CODE,
			String rEQ_SEQ_NO, String sND_COMP_NO, String sND_NODE_NO, String sND_DATE, String sEQ_NO, String oUT_DATE,
			String oUT_TIME, String oUT_SYS_NO, String oUT_SEQ_NO, String rEQ_PLAT_SEQ, String vER_NO) {
		super();
		REQ_COMP_NO = rEQ_COMP_NO;
		REQ_NODE_NO = rEQ_NODE_NO;
		REQ_IP = rEQ_IP;
		REQ_DATE = rEQ_DATE;
		REQ_SVC_CODE = rEQ_SVC_CODE;
		REQ_SEQ = rEQ_SEQ_NO;
		SND_COMP_NO = sND_COMP_NO;
		SND_NODE_NO = sND_NODE_NO;
		SND_DATE = sND_DATE;
		SND_SEQ = sEQ_NO;
		OUT_DATE = oUT_DATE;
		OUT_TIME = oUT_TIME;
		OUT_SYS = oUT_SYS_NO;
		OUT_SEQ = oUT_SEQ_NO;
		REQ_PLAT_SEQ = rEQ_PLAT_SEQ;
		VER_NO = vER_NO;
	}

	public String getREQ_NODE_NO() {
		return REQ_NODE_NO;
	}

	public void setREQ_NODE_NO(String rEQ_NODE_NO) {
		REQ_NODE_NO = rEQ_NODE_NO;
	}

	public String getREQ_IP() {
		return REQ_IP;
	}

	public void setREQ_IP(String rEQ_IP) {
		REQ_IP = rEQ_IP;
	}

	public String getREQ_DATE() {
		return REQ_DATE;
	}

	public void setREQ_DATE(String rEQ_DATE) {
		REQ_DATE = rEQ_DATE;
	}

	public String getREQ_SVC_CODE() {
		return REQ_SVC_CODE;
	}

	public void setREQ_SVC_CODE(String rEQ_SVC_CODE) {
		REQ_SVC_CODE = rEQ_SVC_CODE;
	}

	public String getSND_NODE_NO() {
		return SND_NODE_NO;
	}

	public void setSND_NODE_NO(String sND_NODE_NO) {
		SND_NODE_NO = sND_NODE_NO;
	}

	public String getSND_DATE() {
		return SND_DATE;
	}

	public void setSND_DATE(String sND_DATE) {
		SND_DATE = sND_DATE;
	}

	public String getOUT_DATE() {
		return OUT_DATE;
	}

	public void setOUT_DATE(String oUT_DATE) {
		OUT_DATE = oUT_DATE;
	}

	public String getOUT_TIME() {
		return OUT_TIME;
	}

	public void setOUT_TIME(String oUT_TIME) {
		OUT_TIME = oUT_TIME;
	}

	public String getREQ_PLAT_SEQ() {
		return REQ_PLAT_SEQ;
	}

	public void setREQ_PLAT_SEQ(String rEQ_PLAT_SEQ) {
		REQ_PLAT_SEQ = rEQ_PLAT_SEQ;
	}

	public String getVER_NO() {
		return VER_NO;
	}

	public void setVER_NO(String vER_NO) {
		VER_NO = vER_NO;
	}

	public String getMACH_DATE() {
		return MACH_DATE;
	}

	public void setMACH_DATE(String mACH_DATE) {
		MACH_DATE = mACH_DATE;
	}

	
}
