package com.adtec.comp.ctrl.dto;

public class CtrlModlListResDTO {

	/*模板号*/
	private String MODL_NO;
	/*模板名称*/
	private String MODL_NAME;
	/*模板类型*/
	private String MODL_TYPE;
	/*操作*/
	private String ACTION;
	/*模型组件 格式(MODL_NO-MODL_NAME)*/
	private String MODL_SHOW;
	
	public String getMODL_SHOW() {
		return MODL_SHOW;
	}
	public void setMODL_SHOW(String mODL_SHOW) {
		MODL_SHOW = mODL_SHOW;
	}
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
	public String getMODL_NAME() {
		return MODL_NAME;
	}
	public void setMODL_NAME(String mODL_NAME) {
		MODL_NAME = mODL_NAME;
	}
	public String getMODL_TYPE() {
		return MODL_TYPE;
	}
	public void setMODL_TYPE(String mODL_TYPE) {
		MODL_TYPE = mODL_TYPE;
	}
}
