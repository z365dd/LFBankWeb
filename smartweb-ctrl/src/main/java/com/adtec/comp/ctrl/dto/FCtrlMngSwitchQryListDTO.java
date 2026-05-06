/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: CtrlModlQryListDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年1月3日 上午9:47:03<br>
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
public class FCtrlMngSwitchQryListDTO {
	/*序号*/
	private int ORDER_NO;
	/*模型号*/
	private String COMP_NO;
	/*模型名称*/
	private String COMP_NAME;
	/*服务码*/
	private String SVC_CODE;
	/*服务码描述*/
	private String SVC_DESC;
	/*子服务码*/
	private String SUB_SVC_CODE;
	/*子服务码描述*/
	private String SUB_SVC_DESC;
	/*执行序号*/
	private long EXEC_SER;
	/*规则表达式*/
	private String EXPR;
	/*规则翻译*/
	private String TRANL_EXPR;
	/*规则中文描述*/
	private String EXPR_DESC;
	/*操作*/
	private String ACTION;
	
	public int getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(int oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getCOMP_NAME() {
		return COMP_NAME;
	}
	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getSVC_DESC() {
		return SVC_DESC;
	}
	public void setSVC_DESC(String sVC_DESC) {
		SVC_DESC = sVC_DESC;
	}
	public String getSUB_SVC_CODE() {
		return SUB_SVC_CODE;
	}
	public void setSUB_SVC_CODE(String sUB_SVC_CODE) {
		SUB_SVC_CODE = sUB_SVC_CODE;
	}
	public String getSUB_SVC_DESC() {
		return SUB_SVC_DESC;
	}
	public void setSUB_SVC_DESC(String sUB_SVC_DESC) {
		SUB_SVC_DESC = sUB_SVC_DESC;
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
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	
	
}
