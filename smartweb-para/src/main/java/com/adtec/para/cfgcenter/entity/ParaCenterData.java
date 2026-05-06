package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ParaCenterData {
	/**
	 * 状态
	 */
	@JSONField(name = ParaMapKey.STATUS)
	private String runStat;
	/**
	 * 英文名称
	 */
	@JSONField(name = ParaMapKey.EN_NAME)
	private String engName;
	/**
	 * 中文名称
	 */
	@JSONField(name = ParaMapKey.NAME)
	private String chName;
	/**
	 * 缓存方式
	 */
	@JSONField(name = ParaMapKey.CACHE_MODE)
	private String cacheMode;
	/**
	 * zk-认证登陆名
	 */
	@JSONField(name = ParaMapKey.ZK_LOGIN_NAME)
    private String zkLoginName;
	/**
	 * zk-认证密码
	 */
	@JSONField(name = ParaMapKey.ZK_LOGIN_PWD)
    private String zkPwd;
    /**
     * redis-认证登陆名
     */
	@JSONField(name = ParaMapKey.REDIS_LOGIN_NAME)
    private String rsLoginName;
    /**
     * redis-认证密码
     */
	@JSONField(name = ParaMapKey.REDIS_LOGIN_PWD)
    private String rsPwd;
	/**
	 * zk节点信息
	 */
	@JSONField(name = ParaMapKey.ZK_CLUSTER)
	private ParaZkCluster zkCluster;
	/**
	 * redis节点信息
	 */
	@JSONField(name = ParaMapKey.REDIS_CLUSTER)
	private ParaRedisCluster redisCluster;

	public String getRunStat() {
		return runStat;
	}

	public void setRunStat(String runStat) {
		this.runStat = runStat;
	}

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

	public String getCacheMode() {
		return cacheMode;
	}

	public void setCacheMode(String cacheMode) {
		this.cacheMode = cacheMode;
	}

	public String getZkLoginName() {
		return zkLoginName;
	}

	public void setZkLoginName(String zkLoginName) {
		this.zkLoginName = zkLoginName;
	}

	public String getZkPwd() {
		return zkPwd;
	}

	public void setZkPwd(String zkPwd) {
		this.zkPwd = zkPwd;
	}

	public String getRsLoginName() {
		return rsLoginName;
	}

	public void setRsLoginName(String rsLoginName) {
		this.rsLoginName = rsLoginName;
	}

	public String getRsPwd() {
		return rsPwd;
	}

	public void setRsPwd(String rsPwd) {
		this.rsPwd = rsPwd;
	}

	/**
	 * @return the zkCluster
	 */
	public ParaZkCluster getZkCluster() {
		return zkCluster;
	}
	/**
	 * @param zkCluster the zkCluster to set
	 */
	public void setZkCluster(ParaZkCluster zkCluster) {
		this.zkCluster = zkCluster;
	}
	public void setZkCluster(String nodeCount, List<ParaZkNodeData> zkNodeList) {
		this.zkCluster = new ParaZkCluster(nodeCount, zkNodeList);
	}
	/**
	 * @return the redisCluster
	 */
	public ParaRedisCluster getRedisCluster() {
		return redisCluster;
	}
	/**
	 * @param redisCluster the redisCluster to set
	 */
	public void setRedisCluster(ParaRedisCluster redisCluster) {
		this.redisCluster = redisCluster;
	}
	public void setRedisCluster(String nodeCount, List<ParaRedisNodeData> rsNodeList) {
		this.redisCluster = new ParaRedisCluster(nodeCount, rsNodeList);
	}
}
