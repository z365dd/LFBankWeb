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
public class FCtrlMngOprQryListDTO {
	/*模型号*/
	private String COMP_NO;
	/*预算符名*/
	private String OPR;
	/*预算符中文描述*/
	private String OPR_DESC;
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getOPR() {
		return OPR;
	}
	public void setOPR(String oPR) {
		OPR = oPR;
	}
	public String getOPR_DESC() {
		return OPR_DESC;
	}
	public void setOPR_DESC(String oPR_DESC) {
		OPR_DESC = oPR_DESC;
	}
	
}
