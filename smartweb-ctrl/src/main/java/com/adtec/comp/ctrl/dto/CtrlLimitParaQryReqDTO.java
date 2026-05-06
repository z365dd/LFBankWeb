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
public class CtrlLimitParaQryReqDTO {
	private String MODL_NO;
	private String SVC_CODE;
	private String SUB_SVC;
	private String SER_NO;
	public String getMODL_NO() {
		return MODL_NO;
	}
	public void setMODL_NO(String mODL_NO) {
		MODL_NO = mODL_NO;
	}
	public String getSVC_CODE() {
		return SVC_CODE;
	}
	public void setSVC_CODE(String sVC_CODE) {
		SVC_CODE = sVC_CODE;
	}
	public String getSUB_SVC() {
		return SUB_SVC;
	}
	public void setSUB_SVC(String sUB_SVC) {
		SUB_SVC = sUB_SVC;
	}
	public String getSER_NO() {
		return SER_NO;
	}
	public void setSER_NO(String sER_NO) {
		SER_NO = sER_NO;
	}
	
}
