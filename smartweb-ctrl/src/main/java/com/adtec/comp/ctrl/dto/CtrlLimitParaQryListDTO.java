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
public class CtrlLimitParaQryListDTO {
	private String MODL_NO;
	private String SVC_CODE;
	private String SUB_SVC;
	private String RULE_EXP;
	private String EXP_DESC;
	
	private String SER_NO;
	private String EXCT_SER;
	
	private String AMT_LMT_DAY;
	private String AMT_LMT_MONTH;
	private String AMT_LMT_SEASON;
	private String AMT_LMT_YEAR;
	private String AMT_LMT_SIGL;
	
	private String CNT_LMT_DAY;
	private String CNT_LMT_MONTH;
	private String CNT_LMT_SEASON;
	private String CNT_LMT_YEAR;
	
	private String ACTION;
	
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
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
	public String getRULE_EXP() {
		return RULE_EXP;
	}
	public void setRULE_EXP(String rULE_EXP) {
		RULE_EXP = rULE_EXP;
	}
	public String getEXP_DESC() {
		return EXP_DESC;
	}
	public void setEXP_DESC(String eXP_DESC) {
		EXP_DESC = eXP_DESC;
	}
	public String getSER_NO() {
		return SER_NO;
	}
	public void setSER_NO(String sER_NO) {
		SER_NO = sER_NO;
	}
	public String getEXCT_SER() {
		return EXCT_SER;
	}
	public void setEXCT_SER(String eXCT_SER) {
		EXCT_SER = eXCT_SER;
	}
	public String getAMT_LMT_DAY() {
		return AMT_LMT_DAY;
	}
	public void setAMT_LMT_DAY(String aMT_LMT_DAY) {
		AMT_LMT_DAY = aMT_LMT_DAY;
	}
	public String getAMT_LMT_MONTH() {
		return AMT_LMT_MONTH;
	}
	public void setAMT_LMT_MONTH(String aMT_LMT_MONTH) {
		AMT_LMT_MONTH = aMT_LMT_MONTH;
	}
	public String getAMT_LMT_SEASON() {
		return AMT_LMT_SEASON;
	}
	public void setAMT_LMT_SEASON(String aMT_LMT_SEASON) {
		AMT_LMT_SEASON = aMT_LMT_SEASON;
	}
	public String getAMT_LMT_YEAR() {
		return AMT_LMT_YEAR;
	}
	public void setAMT_LMT_YEAR(String aMT_LMT_YEAR) {
		AMT_LMT_YEAR = aMT_LMT_YEAR;
	}
	public String getAMT_LMT_SIGL() {
		return AMT_LMT_SIGL;
	}
	public void setAMT_LMT_SIGL(String aMT_LMT_SIGL) {
		AMT_LMT_SIGL = aMT_LMT_SIGL;
	}
	public String getCNT_LMT_DAY() {
		return CNT_LMT_DAY;
	}
	public void setCNT_LMT_DAY(String cNT_LMT_DAY) {
		CNT_LMT_DAY = cNT_LMT_DAY;
	}
	public String getCNT_LMT_MONTH() {
		return CNT_LMT_MONTH;
	}
	public void setCNT_LMT_MONTH(String cNT_LMT_MONTH) {
		CNT_LMT_MONTH = cNT_LMT_MONTH;
	}
	public String getCNT_LMT_SEASON() {
		return CNT_LMT_SEASON;
	}
	public void setCNT_LMT_SEASON(String cNT_LMT_SEASON) {
		CNT_LMT_SEASON = cNT_LMT_SEASON;
	}
	public String getCNT_LMT_YEAR() {
		return CNT_LMT_YEAR;
	}
	public void setCNT_LMT_YEAR(String cNT_LMT_YEAR) {
		CNT_LMT_YEAR = cNT_LMT_YEAR;
	}
	
	
}
