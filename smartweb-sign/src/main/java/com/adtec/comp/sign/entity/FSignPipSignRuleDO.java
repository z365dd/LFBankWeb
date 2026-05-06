package com.adtec.comp.sign.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class FSignPipSignRuleDO extends BaseDO{
	private static final long serialVersionUID = 1L;
	private String ruleId;
	private String ruleDesc;
	private String signFlg;
	private String noteTp;
	private String signTp;
	private String acctStatList;
	private String vrfyAcctNameFlg;
	private String vrfyCertFlg;
	private String vrfyPhoneFlg;
	private String vrfyModBrchFlg;
	private String vrfyCanclBrchFlg;
	private String custDefLimFlg;
	private String shortRmrk;
	private String midRmrk;
	private String longRmrk;
	private String dac;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getSignFlg() {
		return signFlg;
	}
	public void setSignFlg(String signFlg) {
		this.signFlg = signFlg;
	}
	public String getNoteTp() {
		return noteTp;
	}
	public void setNoteTp(String noteTp) {
		this.noteTp = noteTp;
	}
	public String getSignTp() {
		return signTp;
	}
	public void setSignTp(String signTp) {
		this.signTp = signTp;
	}
	public String getAcctStatList() {
		return acctStatList;
	}
	public void setAcctStatList(String acctStatList) {
		this.acctStatList = acctStatList;
	}
	public String getVrfyAcctNameFlg() {
		return vrfyAcctNameFlg;
	}
	public void setVrfyAcctNameFlg(String vrfyAcctNameFlg) {
		this.vrfyAcctNameFlg = vrfyAcctNameFlg;
	}
	public String getVrfyCertFlg() {
		return vrfyCertFlg;
	}
	public void setVrfyCertFlg(String vrfyCertFlg) {
		this.vrfyCertFlg = vrfyCertFlg;
	}
	public String getVrfyPhoneFlg() {
		return vrfyPhoneFlg;
	}
	public void setVrfyPhoneFlg(String vrfyPhoneFlg) {
		this.vrfyPhoneFlg = vrfyPhoneFlg;
	}
	public String getVrfyModBrchFlg() {
		return vrfyModBrchFlg;
	}
	public void setVrfyModBrchFlg(String vrfyModBrchFlg) {
		this.vrfyModBrchFlg = vrfyModBrchFlg;
	}
	public String getVrfyCanclBrchFlg() {
		return vrfyCanclBrchFlg;
	}
	public void setVrfyCanclBrchFlg(String vrfyCanclBrchFlg) {
		this.vrfyCanclBrchFlg = vrfyCanclBrchFlg;
	}
	public String getCustDefLimFlg() {
		return custDefLimFlg;
	}
	public void setCustDefLimFlg(String custDefLimFlg) {
		this.custDefLimFlg = custDefLimFlg;
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
	
}
