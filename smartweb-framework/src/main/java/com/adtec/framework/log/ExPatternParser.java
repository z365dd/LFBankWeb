/**
 * 系统名称: SmartWeb平台
 * 模块名称: 扩展log4j的日志转换，添加以%#开始读取线程内全局变量，例如%#{GlobalSeq},其中GlobalSeq为线程变量全局流水号
 * 类  名  称: ExPatternParser.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年12月12日 上午9:59:48<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.log;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

import com.adtec.framework.common.util.GVarContainer;
import com.adtec.sys.seq.PlatSeq;

/**
 * @author chenyl
 *
 */
public class ExPatternParser extends PatternParser {

	/**
	 * @param pattern
	 */
	public ExPatternParser(String pattern) {
		super(pattern);
		// TODO Auto-generated constructor stub
	}

	public void finalizeConverter(char c) {
		if (c == '#') {
			String exs = super.extractOption();
			addConverter(new ExrPatternConverter(formattingInfo, exs));
			currentLiteral.setLength(0);

		} else {
			super.finalizeConverter(c);
		}
	}

	private class ExrPatternConverter extends PatternConverter {

		private String cfg;

		ExrPatternConverter(FormattingInfo formattingInfo, String cfg) {
			super(formattingInfo);
			this.cfg = cfg;
		}

		public String convert(LoggingEvent event) {
			Object value = GVarContainer.getVar(cfg);
			/*20191007 add by chenyl for 新增对于自动任务日志输出时没有平台流水号的问题*/
			if(PatternParserConstant.GLOBAL_SEQ.equals(cfg)){
				// 线程中不存在日志平台流水号时则新创建并存储
				if(null==value){
					GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, PlatSeq.getGlobalSeq());
					value = GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ);
				}				
			}
			if (value != null) {
				return String.valueOf(value);
			}
			return "";
		}
	}
}
