package com.adtec.para.common.util;

public class ParaZkNode {
	
	
	
//	private static String ZK_ROOT = ParaParamUtil.getZkRoot();
	private static String ZK_ROOT = "/Para";
	/**
	 * 缓存中心基本信息根节点
	 */
	public final static String CACHE_CENTERS = ZK_ROOT + "/Centers";
	/**
	 * 存储规则根节点
	 */
	public final static String STORAGE = ZK_ROOT + "/Rules/Storage";
	/**
	 * Agent节点
	 */
	public final static String AGENT = ZK_ROOT + "/Agent";
	/**
	 * 参与者实例节点
	 */
	public final static String PART_INST = ZK_ROOT + "/PartInst";
}
