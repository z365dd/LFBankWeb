package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;

import java.util.List;

public class ParaZkCluster {
	@JSONField(name = ParaMapKey.NODE_COUNT)
	private String nodeCount;
	@JSONField(name = ParaMapKey.SERVERS)
	private List<ParaZkNodeData> servers = Lists.newArrayList();
	public ParaZkCluster(String nodeCount, List<ParaZkNodeData> servers) {
		super();
		this.nodeCount = nodeCount;
		this.servers = servers;
	}
	public ParaZkCluster() {
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
	public List<ParaZkNodeData> getServers() {
		return servers;
	}
	/**
	 * @param servers the servers to set
	 */
	public void setServers(List<ParaZkNodeData> servers) {
		this.servers = servers;
	}
	
}
