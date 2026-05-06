package com.adtec.comp.ctrl.oper.entity;
import com.adtec.sys.common.persistence.BaseDO;
public class FCtrlParaLoadDO extends BaseDO{
	private static final long serialVersionUID = 1L;
	private String engName;
	private String tabName;
	private String chName;
	private String shortRmrk;
	private String midRmrk;
	private String longRmrk;
	private String dac;

	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getShortRmrk() {
		return shortRmrk;
	}
	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	public String getMidRmrk() {
		return midRmrk;
	}
	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	public String getLongRmrk() {
		return longRmrk;
	}
	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	public String getDac() {
		return dac;
	}
	public void setDac(String dac) {
		this.dac = dac;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
