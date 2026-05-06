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

import java.util.List;

/**
 * @author 11093
 *
 */
public class FCtrlMngFlowAddReqDTO {
	private String COMP_NO;
	private String SVC_CODE;
	private String SUB_SVC_CODE;
	/*记录个数*/
	private long NUM;
	/*循环*/
	private List<FCtrlMngFlowAddListDTO> FLOW_LIST;
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
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngFlowAddListDTO> getFLOW_LIST() {
		return FLOW_LIST;
	}
	public void setFLOW_LIST(List<FCtrlMngFlowAddListDTO> fLOW_LIST) {
		FLOW_LIST = fLOW_LIST;
	}
	
}
