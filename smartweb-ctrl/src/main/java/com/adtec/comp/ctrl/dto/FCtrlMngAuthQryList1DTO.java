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

import java.util.List;

/**
 * @author 11093
 *
 */
public class FCtrlMngAuthQryList1DTO {
	private long SER;
	/*起点金额*/
	private double MIN_AMT;
	/*结束金额*/
	private double MAX_AMT;
	/*授权柜员级别*/
	private String AUTH_TLR_LVL;
	/*授权柜员个数*/
	private long AUTH_TLR_NUM;
	
	public long getSER() {
		return SER;
	}
	public void setSER(long sER) {
		SER = sER;
	}
	public double getMIN_AMT() {
		return MIN_AMT;
	}
	public void setMIN_AMT(double mIN_AMT) {
		MIN_AMT = mIN_AMT;
	}
	
	public double getMAX_AMT() {
		return MAX_AMT;
	}
	public void setMAX_AMT(double mAX_AMT) {
		MAX_AMT = mAX_AMT;
	}
	public String getAUTH_TLR_LVL() {
		return AUTH_TLR_LVL;
	}
	public void setAUTH_TLR_LVL(String aUTH_TLR_LVL) {
		AUTH_TLR_LVL = aUTH_TLR_LVL;
	}
	public long getAUTH_TLR_NUM() {
		return AUTH_TLR_NUM;
	}
	public void setAUTH_TLR_NUM(long aUTH_TLR_NUM) {
		AUTH_TLR_NUM = aUTH_TLR_NUM;
	}
	
}
