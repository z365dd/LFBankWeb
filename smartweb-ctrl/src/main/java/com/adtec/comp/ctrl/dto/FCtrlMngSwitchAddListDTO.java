/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: CtrlModlQryReqDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年1月3日 上午9:34:07<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.ctrl.dto;

/**
 * @author 11093
 *
 */
public class FCtrlMngSwitchAddListDTO {
	/*
	 * 模型号
	 * */
	private String COMP_NO;
	/*服务码*/
	private String SVC_CODE;
	/*子服务码*/
	private String SUB_SVC_CODE;
	/*执行序号*/
	private long EXEC_SER;
	/*规则表达式*/
	private String EXPR;
	/*规则翻译*/
	private String TRANL_EXPR;
	/*规则中文描述*/
	private String EXPR_DESC;
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getSUB_SVC_CODE() {
		return SUB_SVC_CODE;
	}
	public void setSUB_SVC_CODE(String sUB_SVC_CODE) {
		SUB_SVC_CODE = sUB_SVC_CODE;
	}
	public long getEXEC_SER() {
		return EXEC_SER;
	}
	public void setEXEC_SER(long eXEC_SER) {
		EXEC_SER = eXEC_SER;
	}
	public String getEXPR() {
		return EXPR;
	}
	public void setEXPR(String eXPR) {
		EXPR = eXPR;
	}
	
	public String getTRANL_EXPR() {
		return TRANL_EXPR;
	}
	public void setTRANL_EXPR(String tRANL_EXPR) {
		TRANL_EXPR = tRANL_EXPR;
	}
	public String getEXPR_DESC() {
		return EXPR_DESC;
	}
	public void setEXPR_DESC(String eXPR_DESC) {
		EXPR_DESC = eXPR_DESC;
	}
	
	


}
