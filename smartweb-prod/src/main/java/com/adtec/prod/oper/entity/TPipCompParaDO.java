package com.adtec.prod.oper.entity;

public class TPipCompParaDO {
	private String compNo;		// 组件号
	private String ser;			// 顺序
	private String compName;	// 组件名称
	private String keyTp;		// 属性类型 02-技术KV属性,03-技术关联属性
	private String keyNo;		// 属性编号
	private String keyName;		// 属性名称
	private String kv;			// 对应值
	private String keyDesc;			// 对应值
	private String flg;			// 标志 Y-已配置 N未配置
	private String shortRmrk;	// 备用字段
	private String midRmrk;		// 备用字段
	private String longRmrk;	// 备用字段
	/**
	 * @return the compNo
	 */
	public String getCompNo() {
		return compNo;
	}
	/**
	 * @param compNo the compNo to set
	 */
	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	/**
	 * @return the ser
	 */
	public String getSer() {
		return ser;
	}
	/**
	 * @param ser the ser to set
	 */
	public void setSer(String ser) {
		this.ser = ser;
	}
	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	/**
	 * @return the keyNo
	 */
	public String getKeyNo() {
		return keyNo;
	}
	/**
	 * @param keyNo the keyNo to set
	 */
	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}
	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	 * @return the kv
	 */
	public String getKv() {
		return kv;
	}
	/**
	 * @param kv the kv to set
	 */
	public void setKv(String kv) {
		this.kv = kv;
	}
	/**
	 * @return the flg
	 */
	public String getFlg() {
		return flg;
	}
	/**
	 * @param flg the flg to set
	 */
	public void setFlg(String flg) {
		this.flg = flg;
	}
	/**
	 * @return the shortRmrk
	 */
	public String getShortRmrk() {
		return shortRmrk;
	}
	/**
	 * @param shortRmrk the shortRmrk to set
	 */
	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	/**
	 * @return the midRmrk
	 */
	public String getMidRmrk() {
		return midRmrk;
	}
	/**
	 * @param midRmrk the midRmrk to set
	 */
	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	/**
	 * @return the longRmrk
	 */
	public String getLongRmrk() {
		return longRmrk;
	}
	/**
	 * @param longRmrk the longRmrk to set
	 */
	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	/**
	 * @return the keyTp
	 */
	public String getKeyTp() {
		return keyTp;
	}
	/**
	 * @param keyTp the keyTp to set
	 */
	public void setKeyTp(String keyTp) {
		this.keyTp = keyTp;
	}
	/**
	 * @return the kvDesc
	 */
	public String getKeyDesc() {
		return keyDesc;
	}
	/**
	 * @param kvDesc the kvDesc to set
	 */
	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}
}
