package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class EntrDemoDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    private String entrNo;
    private String entrName;
    private String entrNature;
    private String entrCertTp;
    private String entrCertNo;
    private String legaName;
    private String legaCertTp;
    private String legaCertNo;
	private String entrAddr;
	private String comnDesc;
    private String entrTelNo;
    private String email;
    private String ctctPerName;
    private String ctctPhoneNo;
    private String sndTranDate;
    private String sndTranCmpleTime;
    private String regBrch;
    private String regTlrNo;
    private String modDate;
    private String modTime;
    private String modBrch;
    //private String modTrNo;
    private String shortRmrk;
    private String midRmrk;
//    private String longRmrk;
    private String dac;
    private String openStat;
	private String url;

	public String getOpenStat() {
		return openStat;
	}
	public void setOpenStat(String openStat) {
		this.openStat = openStat;
	}
	public String getEntrNo() {
		return entrNo;
	}
	public void setEntrNo(String entrNo) {
		this.entrNo = entrNo;
	}
	public String getEntrName() {
		return entrName;
	}
	public void setEntrName(String entrName) {
		this.entrName = entrName;
	}
	public String getEntrNature() {
		return entrNature;
	}
	public void setEntrNature(String entrNature) {
		this.entrNature = entrNature;
	}
	public String getEntrCertTp() {
		return entrCertTp;
	}
	public void setEntrCertTp(String entrCertTp) {
		this.entrCertTp = entrCertTp;
	}
	public String getEntrCertNo() {
		return entrCertNo;
	}
	public void setEntrCertNo(String entrCertNo) {
		this.entrCertNo = entrCertNo;
	}
	public String getLegaName() {
		return legaName;
	}
	public void setLegaName(String legaName) {
		this.legaName = legaName;
	}
	public String getLegaCertTp() {
		return legaCertTp;
	}
	public void setLegaCertTp(String legaCertTp) {
		this.legaCertTp = legaCertTp;
	}
	public String getLegaCertNo() {
		return legaCertNo;
	}
	public void setLegaCertNo(String legaCertNo) {
		this.legaCertNo = legaCertNo;
	}

	public String getEntrAddr() {
		return entrAddr;
	}
	public void setEntrAddr(String entrAddr) {
		this.entrAddr = entrAddr;
	}

	public String getComnDesc() {
		return comnDesc;
	}

	public void setComnDesc(String comnDesc) {
		this.comnDesc = comnDesc;
	}

	public String getEntrTelNo() {
		return entrTelNo;
	}
	public void setEntrTelNo(String entrTelNo) {
		this.entrTelNo = entrTelNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCtctPerName() {
		return ctctPerName;
	}
	public void setCtctPerName(String ctctPerName) {
		this.ctctPerName = ctctPerName;
	}
	public String getCtctPhoneNo() {
		return ctctPhoneNo;
	}
	public void setCtctPhoneNo(String ctctPhoneNo) {
		this.ctctPhoneNo = ctctPhoneNo;
	}
	public String getSndTranDate() {
		return sndTranDate;
	}
	public void setSndTranDate(String sndTranDate) {
		this.sndTranDate = sndTranDate;
	}
	public String getSndTranCmpleTime() {
		return sndTranCmpleTime;
	}
	public void setSndTranCmpleTime(String sndTranCmpleTime) {
		this.sndTranCmpleTime = sndTranCmpleTime;
	}
	public String getRegBrch() {
		return regBrch;
	}
	public void setRegBrch(String regBrch) {
		this.regBrch = regBrch;
	}
	public String getRegTlrNo() {
		return regTlrNo;
	}
	public void setRegTlrNo(String regTlrNo) {
		this.regTlrNo = regTlrNo;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModTime() {
		return modTime;
	}
	public void setModTime(String modTime) {
		this.modTime = modTime;
	}
	public String getModBrch() {
		return modBrch;
	}
	public void setModBrch(String modBrch) {
		this.modBrch = modBrch;
	}
//	public String getModTrNo() {
//		return modTrNo;
//	}
//	public void setModTrNo(String modTrNo) {
//		this.modTrNo = modTrNo;
//	}
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
//	public String getLongRmrk() {
//		return longRmrk;
//	}
//	public void setLongRmrk(String longRmrk) {
//		this.longRmrk = longRmrk;
//	}
	public String getDac() {
		return dac;
	}
	public void setDac(String dac) {
		this.dac = dac;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
