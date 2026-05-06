/**
 * 系统名称: SmartWeb平台
 * 模块名称: 自定义日志常量
 * 类  名  称: PatternParserConstant.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年12月12日 上午10:44:32<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.log;

/**
 * @author chenyl
 *
 */
public class PatternParserConstant {
	/**
	 * 全局流水号，日志使用方式：%#{GlobalSeq}
	 */
	public final static String GLOBAL_SEQ = "GlobalSeq"; 
	
	/**
	 * 请求服务开始时间，日志使用方式：%#{StartTime}
	 */
	public final static String START_TIME = "StartTime"; 
}
