package com.adtec.comp.sign.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class FSignTParaChnlDO extends BaseDO {
	private static final long serialVersionUID = 1L;
	private String chnlNo;
	private String chnlName;
	private String chnlDesc;
	private String chnlStat;
	private String brch;
	private String chnlTp;
	private String chnlFlg;
	private String seqCrtId;
	private String tlrNo;
	private String relatSys;
	private String lastUptTime;
	private String shortRmrk;
	private String midRmrk;
	private String longRmrk;
	private String dac;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getChnlNo() {
		return chnlNo;
	}

	public void setChnlNo(String chnlNo) {
		this.chnlNo = chnlNo;
	}

	public String getChnlName() {
		return chnlName;
	}

	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}

	public String getChnlDesc() {
		return chnlDesc;
	}

	public void setChnlDesc(String chnlDesc) {
		this.chnlDesc = chnlDesc;
	}

	public String getChnlStat() {
		return chnlStat;
	}

	public void setChnlStat(String chnlStat) {
		this.chnlStat = chnlStat;
	}

	public String getBrch() {
		return brch;
	}

	public void setBrch(String brch) {
		this.brch = brch;
	}

	public String getChnlTp() {
		return chnlTp;
	}

	public void setChnlTp(String chnlTp) {
		this.chnlTp = chnlTp;
	}

	public String getChnlFlg() {
		return chnlFlg;
	}

	public void setChnlFlg(String chnlFlg) {
		this.chnlFlg = chnlFlg;
	}

	public String getSeqCrtId() {
		return seqCrtId;
	}

	public void setSeqCrtId(String seqCrtId) {
		this.seqCrtId = seqCrtId;
	}

	public String getTlrNo() {
		return tlrNo;
	}

	public void setTlrNo(String tlrNo) {
		this.tlrNo = tlrNo;
	}

	public String getRelatSys() {
		return relatSys;
	}

	public void setRelatSys(String relatSys) {
		this.relatSys = relatSys;
	}

	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
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
