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
public class FCtrlMngAuthModReqDTO {
	private String FLG;
	/*授权记录个数*/
	private long NUM;
	/*循环*/
	private List<FCtrlMngAuthModListDTO> AUTH_LIST = new ArrayList<FCtrlMngAuthModListDTO>();
	
	public String getFLG() {
		return FLG;
	}
	public void setFLG(String fLG) {
		FLG = fLG;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long nUM) {
		NUM = nUM;
	}
	public List<FCtrlMngAuthModListDTO> getAUTH_LIST() {
		return AUTH_LIST;
	}
	public void setAUTH_LIST(List<FCtrlMngAuthModListDTO> aUTH_LIST) {
		AUTH_LIST = aUTH_LIST;
	}
	
}
