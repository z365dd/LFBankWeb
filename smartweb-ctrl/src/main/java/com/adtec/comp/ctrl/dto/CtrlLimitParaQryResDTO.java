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
public class CtrlLimitParaQryResDTO {
	private String LIMIT_LIST_NUM;
	private List<CtrlLimitParaQryListDTO> LIMIT_LIST;
	public String getLIMIT_LIST_NUM() {
		return LIMIT_LIST_NUM;
	}
	public void setLIMIT_LIST_NUM(String lIMIT_LIST_NUM) {
		LIMIT_LIST_NUM = lIMIT_LIST_NUM;
	}
	public List<CtrlLimitParaQryListDTO> getLIMIT_LIST() {
		return LIMIT_LIST;
	}
	public void setLIMIT_LIST(List<CtrlLimitParaQryListDTO> lIMIT_LIST) {
		LIMIT_LIST = lIMIT_LIST;
	}
	
}
