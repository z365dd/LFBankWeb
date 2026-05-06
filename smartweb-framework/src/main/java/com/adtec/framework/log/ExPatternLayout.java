/**
 * 系统名称: SmartWeb平台
 * 模块名称: 扩展log4j的日志输出格式
 * 类  名  称: ExPatternLayout.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年12月12日 上午10:14:02<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.log;

import org.apache.log4j.*;
import org.apache.log4j.helpers.PatternParser;

/**
 * @author chenyl
 *
 */
public class ExPatternLayout extends PatternLayout {
	public ExPatternLayout() {
		this(DEFAULT_CONVERSION_PATTERN);
	}

	public ExPatternLayout(String pattern) {
		super(pattern);
	}

	@Override
	public PatternParser createPatternParser(String pattern) {
		return new ExPatternParser(pattern == null ? DEFAULT_CONVERSION_PATTERN : pattern);
	}
}
