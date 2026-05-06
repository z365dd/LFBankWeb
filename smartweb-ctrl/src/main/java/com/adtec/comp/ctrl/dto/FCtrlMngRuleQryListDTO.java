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
public class FCtrlMngRuleQryListDTO {
	/*模型号*/
	private String COMP_NO;
	/*控制检查规则*/
	private String EXPR;
	/*规则翻译*/
	private String TRAN_EXPR;
	/*规则中文描述*/
	private String EXPR_DESC;
//	
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getEXPR() {
		return EXPR;
	}
	public void setEXPR(String eXPR) {
		EXPR = eXPR;
	}
	public String getTRAN_EXPR() {
		return TRAN_EXPR;
	}
	public void setTRAN_EXPR(String tRAN_EXPR) {
		TRAN_EXPR = tRAN_EXPR;
	}
	public String getEXPR_DESC() {
		return EXPR_DESC;
	}
	public void setEXPR_DESC(String eXPR_DESC) {
		EXPR_DESC = eXPR_DESC;
	}
}
