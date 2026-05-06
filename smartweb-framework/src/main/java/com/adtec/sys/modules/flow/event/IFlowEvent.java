/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程事件处理接口
 * 类  名  称: IFlowEvent.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年9月6日 下午3:55:53<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.event;

import java.util.HashMap;

/**
 * @author chenyl
 *
 */
public interface IFlowEvent {
	/*定义dataMap中的key*/
	/**
	 * 业务流水号
	 */
	public static final String KEY_GLOBAL_SEQ_NO = "GLOBAL_SEQ_NO";
	/**
	 * 使用的流程模板ID
	 */
	public static final String KEY_FLOW_TEMPLATE_ID = "FLOW_TEMPLATE_ID";
	/**
	 * 当前步骤号
	 */
	public static final String KEY_STEP_NO = "STEP_NO";
	/**
	 * 流程标题
	 */
	public static final String KEY_FLOW_TITLE = "FLOW_TITLE";
	/**
	 * 当前步骤标题
	 */
	public static final String KEY_STEP_TITLE = "STEP_TITLE";
	/**
	 * 当前步骤审批结果：02-审批通过、03-审批拒绝
	 */
	public static final String KEY_APPLY_STAT = "APPLY_STAT";
	/**
	 * 当前步骤审批备注信息
	 */
	public static final String KEY_APPLY_MSG = "APPLY_MSG";
	/**
	 * 流程发起人ID
	 */
	public static final String KEY_UP_USER_ID = "UP_USER_ID";
	/**
	 * 流程发起人中文名
	 */
	public static final String KEY_UP_USER_NAME = "UP_USER_NAME";
	/**
	 * 最后审批人ID
	 */
	public static final String KEY_DEAL_USER_ID = "DEAL_USER_ID";
	/**
	 * 最后审批人中文名
	 */
	public static final String KEY_DEAL_USER_NAME = "DEAL_USER_NAME";

	
	/**
	 * 审批通过事件
	 * @param dataMap	审批流程入参
	 */
	public void passEvent(HashMap<String, Object> dataMap);
	
	/**
	 * 审批拒绝事件
	 * @param dataMap	审批流程入参
	 */
	public void refuseEvent(HashMap<String, Object> dataMap);
}
