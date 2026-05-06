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
public class FCtrlMngTypeQryListDTO {
	/*控制检查类型*/
	private String CTRL_TP;
	/*控制检查名称*/
	private String TP_DESC;
	/*方法名*/
	private String FUNC_NAME;
	
	public String getCTRL_TP() {
		return CTRL_TP;
	}
	public void setCTRL_TP(String cTRL_TP) {
		CTRL_TP = cTRL_TP;
	}
	public String getTP_DESC() {
		return TP_DESC;
	}
	public void setTP_DESC(String tP_DESC) {
		TP_DESC = tP_DESC;
	}
	public String getFUNC_NAME() {
		return FUNC_NAME;
	}
	public void setFUNC_NAME(String fUNC_NAME) {
		FUNC_NAME = fUNC_NAME;
	}
	
}
