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
public class FCtrlMngDimVauleQryListDTO {
	/*模型号*/
	private String COMP_NO;
	/*维度名*/
	private String DIM_KEY;
	/*维度值*/
	private String DIM_KV;
	/*维度值中文描述*/
	private String DIM_DESC;
	public String getCOMP_NO() {
		return COMP_NO;
	}
	public void setCOMP_NO(String cOMP_NO) {
		COMP_NO = cOMP_NO;
	}
	public String getDIM_KEY() {
		return DIM_KEY;
	}
	public void setDIM_KEY(String dIM_KEY) {
		DIM_KEY = dIM_KEY;
	}
	public String getDIM_KV() {
		return DIM_KV;
	}
	public void setDIM_KV(String dIM_KV) {
		DIM_KV = dIM_KV;
	}
	public String getDIM_DESC() {
		return DIM_DESC;
	}
	public void setDIM_DESC(String dIM_DESC) {
		DIM_DESC = dIM_DESC;
	}
	
}
