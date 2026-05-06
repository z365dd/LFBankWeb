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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11093
 *
 */
public class FCtrlMngSwitchDelReqDTO {
	/*删除个数*/
	private long NUM;
	/*循环*/
	private List<FCtrlMngSwitchDelListDTO> SWITCH_LIST = new ArrayList<FCtrlMngSwitchDelListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngSwitchDelListDTO> getSWITCH_LIST() {
		return SWITCH_LIST;
	}
	public void setSWITCH_LIST(List<FCtrlMngSwitchDelListDTO> sWITCH_LIST) {
		SWITCH_LIST = sWITCH_LIST;
	}
	
}
