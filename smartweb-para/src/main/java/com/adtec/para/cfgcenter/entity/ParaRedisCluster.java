package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;

import java.util.List;

public class ParaRedisCluster {
	@JSONField(name = ParaMapKey.NODE_COUNT)
	private String nodeCount;
	@JSONField(name = ParaMapKey.SERVERS)
	private List<ParaRedisNodeData> servers = Lists.newArrayList();
	public ParaRedisCluster(String nodeCount, List<ParaRedisNodeData> servers) {
		super();
		this.nodeCount = nodeCount;
		this.servers = servers;
	}
	public ParaRedisCluster() {
	}
	/**
	 * @return the nodeCount
	 */
	public String getNodeCount() {
		return nodeCount;
	}
	/**
	 * @param nodeCount the nodeCount to set
	 */
	public void setNodeCount(String nodeCount) {
		this.nodeCount = nodeCount;
	}
	/**
	 * @return the servers
	 */
	public List<ParaRedisNodeData> getServers() {
		return servers;
	}
	/**
	 * @param servers the servers to set
	 */
	public void setServers(List<ParaRedisNodeData> servers) {
		this.servers = servers;
	}
}
