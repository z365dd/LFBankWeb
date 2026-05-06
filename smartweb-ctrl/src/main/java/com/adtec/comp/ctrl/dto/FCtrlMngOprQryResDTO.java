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
public class FCtrlMngOprQryResDTO {
	/*预算符个数*/
	private long NUM;
	/*预算符查询*/
	private List<FCtrlMngOprQryListDTO> OPR_LIST = new ArrayList<FCtrlMngOprQryListDTO>();
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngOprQryListDTO> getOPR_LIST() {
		return OPR_LIST;
	}
	public void setOPR_LIST(List<FCtrlMngOprQryListDTO> oPR_LIST) {
		OPR_LIST = oPR_LIST;
	}
	
	
}
