package com.adtec.para.cfgcenter.entity;

import com.adtec.para.common.util.ParaZkNode;
import com.alibaba.fastjson.JSON;

public class ParaCenterNode {
	
	/**
	 * 
	 */
	private String status;
	/**
	 * 缓存中心英文名称-配置中心节点名
	 */
	private String cacheCenter;
	/**
	 * 节点数据
	 */
	private String nodeData;
	
	/**
     * 获取缓存中心节点
     *
     * @return
     */
    public String getCacheCenterId() {
    	return String.format("/%s", cacheCenter);
    }
    
    /**
     * 获取缓存中心节点
     *
     * @return
     */
    public String getCacheCenterNode() {
    	return String.format("%s/%s", ParaZkNode.CACHE_CENTERS, cacheCenter);
    }
    /**
     * 对象转json
     * @param nodeData
     */
	public void setNodeData(Object nodeData) {
		this.nodeData = JSON.toJSONString(nodeData);
	}
	/**
	 * 将nodeData转成对应的数据对象
	 * @return
	 */
	public ParaCenterData getNodeDataForObj () {
		if(null==nodeData){
			nodeData = "";
		}
		return JSON.parseObject(nodeData, ParaCenterData.class);
	}
}
