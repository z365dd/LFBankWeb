package com.adtec.prod.oper.definition.adapter.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

public class TPipSaleProdAdapterDOForMsmall extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String saleProdCode;		// 可售产品代码
	private String ser;		// 序号
	private String keyNo;		// 键编号
	private String keyTp;		// 键类型
	private String keyName;		// 键名称
	private String atomProdCode;		// 原子产品代码
	private String compNo;		// 组件号
	private String compName;
	private String defaKv;		// 默认值
	private String vslFlg;		// 标志
	private String valTp;		// 值类型
	private String valLen;		// 数值长度
	private String enterTp;		// 键类型
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC

	private String elemKey;
	private String elemName;
	private String elemKv;

	private String svcCode;

	public TPipSaleProdAdapterDOForMsmall() {
		super();
	}

	public TPipSaleProdAdapterDOForMsmall(String id){
		super(id);
	}

	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	
	public String getSer() {
		return ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
	}
	
	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	
	public String getEnterTp() {
		return enterTp;
	}

	public void setEnterTp(String enterTp) {
		this.enterTp = enterTp;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	public String getAtomProdCode() {
		return atomProdCode;
	}

	public void setAtomProdCode(String atomProdCode) {
		this.atomProdCode = atomProdCode;
	}
	
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getDefaKv() {
		return defaKv;
	}

	public void setDefaKv(String defaKv) {
		this.defaKv = defaKv;
	}
	
	public String getVslFlg() {
		return vslFlg;
	}

	public void setVslFlg(String vslFlg) {
		this.vslFlg = vslFlg;
	}
	
	public String getValTp() {
		return valTp;
	}

	public void setValTp(String valTp) {
		this.valTp = valTp;
	}
	
	public String getValLen() {
		return valLen;
	}

	public void setValLen(String valLen) {
		this.valLen = valLen;
	}
	
	public String getKeyTp() {
		return keyTp;
	}

	public void setKeyTp(String keyTp) {
		this.keyTp = keyTp;
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

	public String getElemKey() {
		return elemKey;
	}

	public void setElemKey(String elemKey) {
		this.elemKey = elemKey;
	}

	public String getElemName() {
		return elemName;
	}

	public void setElemName(String elemName) {
		this.elemName = elemName;
	}

	public String getElemKv() {
		return elemKv;
	}

	public void setElemKv(String elemKv) {
		this.elemKv = elemKv;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
}