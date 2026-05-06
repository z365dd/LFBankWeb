package com.adtec.comp.ctrl.dto;

public class CtrlModlAddReqDTO {
	/*模板号*/
	private String MODL_NO;
	/*模板名称*/
	private String MODL_NAME;
	/*模板类型*/
	private String MODL_TYPE;
	
	
	public CtrlModlAddReqDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CtrlModlAddReqDTO(String mODL_NO, String mODL_NAME, String mODL_TYPE) {
		super();
		MODL_NO = mODL_NO;
		MODL_NAME = mODL_NAME;
		MODL_TYPE = mODL_TYPE;
	}
	
	/*
	 * get() / set() 方法
	 */
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
