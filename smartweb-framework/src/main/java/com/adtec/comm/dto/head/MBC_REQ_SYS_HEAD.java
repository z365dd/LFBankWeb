/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求系统头
 * 类 名 称  : MBC_REQ_APP_HEAD.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月17日 下午6:24:15<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto.head;

public class MBC_REQ_SYS_HEAD {
	/*请求组件/模型编号*/
	private String REQ_MODL_NO;
	/*请求组件/模型节点号*/
	private String REQ_NODE_NO;
	/*请求方IP*/
	private String REQ_IP;
	/*服务请求方日期*/
	private String REQ_DATE;
	/*服务请求方服务码*/
	private String REQ_SVC_CODE;
	/*服务请求发送方流水号*/
	private String REQ_SEQ_NO;
	/*发起方组件/模型编号*/
	private String SND_MODL_NO;
	/*发起方节点号*/
	private String SND_NODE_NO;
	/*发送方交易日期*/
	private String SND_DATE;
	/*内部全局流水号*/
	private String SEQ_NO;
	/*外部日期*/
	private String OUT_DATE;
	/*外部时间*/
	private String OUT_TIME;
	/*外部系统编号*/
	private String OUT_SYS_NO;
	/*外部系统流水号*/
	private String OUT_SEQ_NO;
	/*请求方平台流水号*/
	private String REQ_PLAT_SEQ;
	/*服务版本号*/
	private String VER_NO;
	
	public MBC_REQ_SYS_HEAD() {}

	/**
	 * @param rEQ_MODL_NO
	 * @param rEQ_NODE_NO
	 * @param rEQ_IP
	 * @param rEQ_DATE
	 * @param rEQ_SVC_CODE
	 * @param rEQ_SEQ_NO
	 * @param sND_MODL_NO
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
	public MBC_REQ_SYS_HEAD(String rEQ_MODL_NO, String rEQ_NODE_NO, String rEQ_IP, String rEQ_DATE, String rEQ_SVC_CODE,
			String rEQ_SEQ_NO, String sND_MODL_NO, String sND_NODE_NO, String sND_DATE, String sEQ_NO, String oUT_DATE,
			String oUT_TIME, String oUT_SYS_NO, String oUT_SEQ_NO, String rEQ_PLAT_SEQ, String vER_NO) {
		super();
		REQ_MODL_NO = rEQ_MODL_NO;
		REQ_NODE_NO = rEQ_NODE_NO;
		REQ_IP = rEQ_IP;
		REQ_DATE = rEQ_DATE;
		REQ_SVC_CODE = rEQ_SVC_CODE;
		REQ_SEQ_NO = rEQ_SEQ_NO;
		SND_MODL_NO = sND_MODL_NO;
		SND_NODE_NO = sND_NODE_NO;
		SND_DATE = sND_DATE;
		SEQ_NO = sEQ_NO;
		OUT_DATE = oUT_DATE;
		OUT_TIME = oUT_TIME;
		OUT_SYS_NO = oUT_SYS_NO;
		OUT_SEQ_NO = oUT_SEQ_NO;
		REQ_PLAT_SEQ = rEQ_PLAT_SEQ;
		VER_NO = vER_NO;
	}

	/**
	 * @return the rEQ_MODL_NO
	 */
	public String getREQ_MODL_NO() {
		return REQ_MODL_NO;
	}

	/**
	 * @param rEQ_MODL_NO the rEQ_MODL_NO to set
	 */
	public void setREQ_MODL_NO(String rEQ_MODL_NO) {
		REQ_MODL_NO = rEQ_MODL_NO;
	}

	/**
	 * @return the rEQ_NODE_NO
	 */
	public String getREQ_NODE_NO() {
		return REQ_NODE_NO;
	}

	/**
	 * @param rEQ_NODE_NO the rEQ_NODE_NO to set
	 */
	public void setREQ_NODE_NO(String rEQ_NODE_NO) {
		REQ_NODE_NO = rEQ_NODE_NO;
	}

	/**
	 * @return the rEQ_IP
	 */
	public String getREQ_IP() {
		return REQ_IP;
	}

	/**
	 * @param rEQ_IP the rEQ_IP to set
	 */
	public void setREQ_IP(String rEQ_IP) {
		REQ_IP = rEQ_IP;
	}

	/**
	 * @return the rEQ_DATE
	 */
	public String getREQ_DATE() {
		return REQ_DATE;
	}

	/**
	 * @param rEQ_DATE the rEQ_DATE to set
	 */
	public void setREQ_DATE(String rEQ_DATE) {
		REQ_DATE = rEQ_DATE;
	}

	/**
	 * @return the rEQ_SVC_CODE
	 */
	public String getREQ_SVC_CODE() {
		return REQ_SVC_CODE;
	}

	/**
	 * @param rEQ_SVC_CODE the rEQ_SVC_CODE to set
	 */
	public void setREQ_SVC_CODE(String rEQ_SVC_CODE) {
		REQ_SVC_CODE = rEQ_SVC_CODE;
	}

	/**
	 * @return the rEQ_SEQ_NO
	 */
	public String getREQ_SEQ_NO() {
		return REQ_SEQ_NO;
	}

	/**
	 * @param rEQ_SEQ_NO the rEQ_SEQ_NO to set
	 */
	public void setREQ_SEQ_NO(String rEQ_SEQ_NO) {
		REQ_SEQ_NO = rEQ_SEQ_NO;
	}

	/**
	 * @return the sND_MODL_NO
	 */
	public String getSND_MODL_NO() {
		return SND_MODL_NO;
	}

	/**
	 * @param sND_MODL_NO the sND_MODL_NO to set
	 */
	public void setSND_MODL_NO(String sND_MODL_NO) {
		SND_MODL_NO = sND_MODL_NO;
	}

	/**
	 * @return the sND_NODE_NO
	 */
	public String getSND_NODE_NO() {
		return SND_NODE_NO;
	}

	/**
	 * @param sND_NODE_NO the sND_NODE_NO to set
	 */
	public void setSND_NODE_NO(String sND_NODE_NO) {
		SND_NODE_NO = sND_NODE_NO;
	}

	/**
	 * @return the sND_DATE
	 */
	public String getSND_DATE() {
		return SND_DATE;
	}

	/**
	 * @param sND_DATE the sND_DATE to set
	 */
	public void setSND_DATE(String sND_DATE) {
		SND_DATE = sND_DATE;
	}

	/**
	 * @return the sEQ_NO
	 */
	public String getSEQ_NO() {
		return SEQ_NO;
	}

	/**
	 * @param sEQ_NO the sEQ_NO to set
	 */
	public void setSEQ_NO(String sEQ_NO) {
		SEQ_NO = sEQ_NO;
	}

	/**
	 * @return the oUT_DATE
	 */
	public String getOUT_DATE() {
		return OUT_DATE;
	}

	/**
	 * @param oUT_DATE the oUT_DATE to set
	 */
	public void setOUT_DATE(String oUT_DATE) {
		OUT_DATE = oUT_DATE;
	}

	/**
	 * @return the oUT_TIME
	 */
	public String getOUT_TIME() {
		return OUT_TIME;
	}

	/**
	 * @param oUT_TIME the oUT_TIME to set
	 */
	public void setOUT_TIME(String oUT_TIME) {
		OUT_TIME = oUT_TIME;
	}

	/**
	 * @return the oUT_SYS_NO
	 */
	public String getOUT_SYS_NO() {
		return OUT_SYS_NO;
	}

	/**
	 * @param oUT_SYS_NO the oUT_SYS_NO to set
	 */
	public void setOUT_SYS_NO(String oUT_SYS_NO) {
		OUT_SYS_NO = oUT_SYS_NO;
	}

	/**
	 * @return the oUT_SEQ_NO
	 */
	public String getOUT_SEQ_NO() {
		return OUT_SEQ_NO;
	}

	/**
	 * @param oUT_SEQ_NO the oUT_SEQ_NO to set
	 */
	public void setOUT_SEQ_NO(String oUT_SEQ_NO) {
		OUT_SEQ_NO = oUT_SEQ_NO;
	}

	/**
	 * @return the rEQ_PLAT_SEQ
	 */
	public String getREQ_PLAT_SEQ() {
		return REQ_PLAT_SEQ;
	}

	/**
	 * @param rEQ_PLAT_SEQ the rEQ_PLAT_SEQ to set
	 */
	public void setREQ_PLAT_SEQ(String rEQ_PLAT_SEQ) {
		REQ_PLAT_SEQ = rEQ_PLAT_SEQ;
	}

	/**
	 * @return the vER_NO
	 */
	public String getVER_NO() {
		return VER_NO;
	}

	/**
	 * @param vER_NO the vER_NO to set
	 */
	public void setVER_NO(String vER_NO) {
		VER_NO = vER_NO;
	}
	
	
}
