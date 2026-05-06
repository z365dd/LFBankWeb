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
public class CtrlCompQryResDTO {
	/*记录个数*/
	private int NUM;
	/*记录循环*/
	private List<CtrlCompQryListResDTO> COMP_LIST;
	
	public int getNUM() {
		return NUM;
	}
	public void setNUM(int nUM) {
		NUM = nUM;
	}
	public List<CtrlCompQryListResDTO> getCOMP_LIST() {
		return COMP_LIST;
	}
	public void setCOMP_LIST(List<CtrlCompQryListResDTO> cOMP_LIST) {
		COMP_LIST = cOMP_LIST;
	}
	
}
