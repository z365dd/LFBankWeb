package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;

public class ParaRulesStgColData {
	/**
	 * 字段名
	 */
	@JSONField(name = ParaMapKey.COLID)
	private String colNo;
	/**
	 * 中文描述
	 */
	@JSONField(name = ParaMapKey.COLNAME)
	private String colName;
	/**
	 * 数据类型
	 */
	@JSONField(name = ParaMapKey.TYPE)
	private String colTp;
	/**
	 * 字段长度
	 */
	@JSONField(name = ParaMapKey.LENGTH)
	private String colLen;
	/**
	 * 是否非空
	 */
	@JSONField(name = ParaMapKey.ISNOTNULL)
	private String nullFlg;
	/**
	 * 唯一索引
	 */
	@JSONField(name = ParaMapKey.ISUNIQUE)
	private String uniqFlg;

	public String getColNo() {
		return colNo;
	}

	public void setColNo(String colNo) {
		this.colNo = colNo;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColTp() {
		return colTp;
	}

	public void setColTp(String colTp) {
		this.colTp = colTp;
	}

	public String getColLen() {
		return colLen;
	}

	public void setColLen(String colLen) {
		this.colLen = colLen;
	}

	public String getNullFlg() {
		return nullFlg;
	}

	public void setNullFlg(String nullFlg) {
		this.nullFlg = nullFlg;
	}

	public String getUniqFlg() {
		return uniqFlg;
	}

	public void setUniqFlg(String uniqFlg) {
		this.uniqFlg = uniqFlg;
	}
}
