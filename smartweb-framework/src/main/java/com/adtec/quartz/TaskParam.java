package com.adtec.quartz;

public class TaskParam {
	/**
	 * smartweb自动任务zk根节点
	 */
	public static final String BASE_TASK_PATH = "/Task/smartweb";
	/**
	 * 上一次自动任务发生的时间
	 */
	public final static String LAST_DATE = "LastDate";
	/**
	 * 流程检查自动任务
	 */
	public static final String FLOWCHKTASK = "FlowChkTask";
	/**
	 * 生效检查自动任务
	 */
	public static final String EFFECTCHKTASK = "EffectChkTask";
	/**
	 * 加载流控监控日志自动任务
	 */
	public static final String LOADFLOWCTRLTASK = "LoadFlowCtrlTask";
	/**
	 * 加载熔断监控日志自动任务
	 */
	public static final String LOADFUSETASK = "LoadFuseTask";
	/**
	 * 加载参与者实例监控日志自动任务
	 */
	public static final String LOADPARTINSTTASK = "LoadPartInstTask";
	/**
	 * 加载质量数据自动任务
	 */
	public static final String LOADQUALITYDATATASK = "LoadQualityDataTask";
	/**
	 * 加载参与者运行数据自动任务
	 */
	public static final String LOADPARTINSTDATATASK = "LoadPartInstDataTask";
	/**
	 * 加载服务接口校验数据自动任务
	 */
	public static final String LOADSERVICEINTERFACEDATATASK = "LoadServiceInterfaceDataTask";
	
}
