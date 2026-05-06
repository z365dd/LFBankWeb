package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaMapKey;
import com.alibaba.fastjson.annotation.JSONField;

public class ParaZkNodeData {
	/**
	 * 序号
	 */
	@JSONField(name = ParaMapKey.MYID)
	private String zkId;
	/**
	 * 节点ip地址
	 */
	@JSONField(name = ParaMapKey.IP)
	private String ip;
	/**
	 * 节点端口地址
	 */
	@JSONField(name = ParaMapKey.PORT)
	private String port;

	public String getZkId() {
		return zkId;
	}

	public void setZkId(String zkId) {
		this.zkId = zkId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
