package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.curator.shaded.com.google.common.collect.Lists;

import java.util.List;

public class ParaRulesStgData {
	/**
	 * 主键id
	 */
	@JSONField(serialize = false)
	private String id;
	/**
	 * 状态
	 */
	@JSONField(name = ParaMapKey.STATUS)
	private String status;
	/**
	 * 中文名称
	 */
	@JSONField(name = ParaMapKey.NAME)
	private String chName;
	/**
	 * 英文名称
	 */
	@JSONField(name = ParaMapKey.EN_NAME)
	private String engName;
	/**
	 * 所属缓存中心
	 */
	@JSONField(name = ParaMapKey.CACHE_CENTER)
	private String cacheCenter;
	/**
	 * 唯一索引组合
	 */
	@JSONField(name = ParaMapKey.UNIQUE_KEY)
	private String uniqKey;
	/**
	 * 所属租户
	 */
	@JSONField(name = ParaMapKey.TENANT)
	private String tntNo;
	/**
	 * 读取权限等级
	 */
	@JSONField(name = ParaMapKey.READ_AUTH_LEVEL)
	private String readAuthLvl;
	/**
	 * 读取权限列表
	 */
	@JSONField(name = ParaMapKey.READ_AUTH_LIST)
	private String readAuthList;
	/**
	 * 写入权限列表
	 */
	@JSONField(name = ParaMapKey.WRITE_AUTH_LIST)
	private String writeAuthList;
	/**
	 * 键项信息
	 */
	@JSONField(name = ParaMapKey.COLUMN)
	private List<ParaRulesStgColData> column = Lists.newArrayList();
	@JSONField(name = ParaMapKey.STORG_RULE_TP)
	private String storgRuleTp;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the cacheCenter
	 */
	public String getCacheCenter() {
		return cacheCenter;
	}
	/**
	 * @param cacheCenter the cacheCenter to set
	 */
	public void setCacheCenter(String cacheCenter) {
		this.cacheCenter = cacheCenter;
	}
	/**
	 * @return the readAuthList
	 */
	public String getReadAuthList() {
		return readAuthList;
	}
	/**
	 * @param readAuthList the readAuthList to set
	 */
	public void setReadAuthList(String readAuthList) {
		this.readAuthList = readAuthList;
	}
	/**
	 * @return the writeAuthList
	 */
	public String getWriteAuthList() {
		return writeAuthList;
	}
	/**
	 * @param writeAuthList the writeAuthList to set
	 */
	public void setWriteAuthList(String writeAuthList) {
		this.writeAuthList = writeAuthList;
	}
	/**
	 * @return the column
	 */
	public List<ParaRulesStgColData> getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(List<ParaRulesStgColData> column) {
		this.column = column;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getUniqKey() {
		return uniqKey;
	}

	public void setUniqKey(String uniqKey) {
		this.uniqKey = uniqKey;
	}

	public String getTntNo() {
		return tntNo;
	}

	public void setTntNo(String tntNo) {
		this.tntNo = tntNo;
	}

	public String getReadAuthLvl() {
		return readAuthLvl;
	}

	public void setReadAuthLvl(String readAuthLvl) {
		this.readAuthLvl = readAuthLvl;
	}

	public String getStorgRuleTp() {
		return storgRuleTp;
	}

	public void setStorgRuleTp(String storgRuleTp) {
		this.storgRuleTp = storgRuleTp;
	}
}
