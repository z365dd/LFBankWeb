/**
 * 系统名称: 微服务框架
 * 模块名称: Redis存储的节点
 * 类  名  称: RedisNode.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年10月25日 上午10:45:29<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.para.common.util;

/**
 * @author chenyl
 *
 */
public class ParaRedisNode {
	/**
	 * 服务质量当前流水跟节点
	 */
	public final static String LOG = "/LOG";
	/**
	 * 服务质量历史流水跟节点
	 */
	public final static String HIST = "/HIST";
	/**
	 * 服务质量报文跟节点
	 */
	public final static String BUFFER = "/BUFFER";
	/**
	 * 应用自定义参数存储树
	 */
	public final static String PARAM_DATA = "/PARAM_DATA";
	
	/**
	 * 参与者最大并发数(熔断规则)
	 */
	public final static String MAX_ACCESS = "MaxAccess";
	/**
	 * 当前并发数
	 */
	public final static String CUR_MAX_ACCESS = "CurMaxAccess";
	/**
	 * 请求报文
	 */
	public final static String REQUEST = "Request";
	/**
	 * 响应报文
	 */
	public final static String RESPONSE = "Response";
	/**
	 * 事务状态：0-未提交事务
	 */
	public final static String NOCOMMIT = "0";
	/**
	 * 事务状态：1-已提交事务
	 */
	public final static String COMMITTED = "1";
	/**
	 * 统计方向：C-消费方
	 */
	public final static String DIRECTION_C = "C";
	/**
	 * 统计方向：S-服务方
	 */
	public final static String DIRECTION_S = "S";
	/**
	 * 服务成功笔数：Success
	 */
	public final static String SERVICE_SUCCESS = "Success";
	/**
	 * 服务失败笔数：Failed
	 */
	public final static String SERVICE_FAILED = "Failed";
	/**
	 * 连续异常笔数：ExceptionCount
	 */
	public final static String EXCEPTION_COUNT = "ExceptionCount";
	/**
	 * 迁移情况：0-初始化
	 */
	public final static String MOVING_INIT = "0";
	/**
	 * 迁移情况：1-正在迁移
	 */
	public final static String MOVING_LOG = "1";
	/**
	 * 迁移情况：2-已迁移到历史
	 */
	public final static String MOVING_HIST = "2";
	/**
	 * 采样结果
	 */
	public final static String SAMPLE = "Sample";
}
