package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ParaRedisNodeData {
	/**
	 * 主键id
	 */
	@JSONField(serialize = false)
	private String id;
	/**
	 * 主节点id
	 */
	@JSONField(serialize = false)
	private String mainNodeId;
	/**
	 * 节点ip地址
	 */
	@JSONField(name = ParaMapKey.IP)
	private String ip;
	/**
	 * 节点端口
	 */
	@JSONField(name = ParaMapKey.PORT)
	private String port;
	/**
	 * 从节点数
	 */
	@JSONField(name = ParaMapKey.SLAVE_COUNT)
	private String slaveCount;
	/**
	 * 从节点数据
	 */
	@JSONField(name = ParaMapKey.SLAVE_DATA)
	List<ParaRedisNodeData> slaveData;;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * @return the slaveCount
	 */
	public String getSlaveCount() {
		return slaveCount;
	}
	/**
	 * @param slaveCount the slaveCount to set
	 */
	public void setSlaveCount(String slaveCount) {
		this.slaveCount = slaveCount;
	}
	/**
	 * @return the slaveData
	 */
	public List<ParaRedisNodeData> getSlaveData() {
		return slaveData;
	}
	/**
	 * @param slaveData the slaveData to set
	 */
	public void setSlaveData(List<ParaRedisNodeData> slaveData) {
		this.slaveData = slaveData;
	}

	public String getMainNodeId() {
		return mainNodeId;
	}

	public void setMainNodeId(String mainNodeId) {
		this.mainNodeId = mainNodeId;
	}
}
