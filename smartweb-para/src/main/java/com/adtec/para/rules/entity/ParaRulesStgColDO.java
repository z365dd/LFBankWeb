/**
 * 系统名称: 缓存中心
 * 模块名称: 存储规则键项表实体类
 * 类  名  称: RulesStgColDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-11-13 11:47:37
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */
package com.adtec.para.rules.entity;



import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

public class ParaRulesStgColDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /*所属存储规则ID*/
    private String ruleId;
    /*列名*/
    private String colNo;
    /*中文描述*/
    private String colName;
    /*数据类型*/
    private String dataTp;
    /*数据长度*/
    private String colLen;
    /*是否非空*/
    private String nullFlg;
    /*是否唯一索引*/
    private String uniqFlg;
    /*备用字段1*/
    private String colSer;

	public ParaRulesStgColDO() {
		super();
	}

	public String getRuleId() {
		return ruleId;
	}

	public ParaRulesStgColDO(String colNo, String colName, String nullFlg, String uniqFlg, String rmrk) {
		this.colNo = colNo;
		this.colName = colName;
		this.nullFlg = nullFlg;
		this.uniqFlg = uniqFlg;
		super.rmrk = rmrk;
	}

	public ParaRulesStgColDO(String ruleId, String colNo, String colName, String dataTp, String colLen, String nullFlg, String uniqFlg, String colSer) {
		this.ruleId = ruleId;
		this.colNo = colNo;
		this.colName = colName;
		this.dataTp = dataTp;
		this.colLen = colLen;
		this.nullFlg = nullFlg;
		this.uniqFlg = uniqFlg;
		this.colSer = colSer;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

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

	public String getColSer() {
		return colSer;
	}

	public void setColSer(String colSer) {
		this.colSer = colSer;
	}

	public String getDataTp() {
		return dataTp;
	}

	public void setDataTp(String dataTp) {
		this.dataTp = dataTp;
	}

	@Override
	public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("serialVersionUID");
		list.add("action");
		list.add("delFlag");
		list.add("delFlg");
		list.add("remarks");
		return list;
	}
}
