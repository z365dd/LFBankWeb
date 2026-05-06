/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: MyDailyRollingFileAppender.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年7月5日 上午10:29:34<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.log;

import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;

import com.adtec.framework.common.util.ParamUtil;

/**
 * @author chenyl
 *
 */
public class MyDailyRollingFileAppender extends DailyRollingFileAppender {

	/**
	 * 
	 */
	public MyDailyRollingFileAppender() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 * @param filename
	 * @param datePattern
	 * @throws IOException
	 */
	public MyDailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
		super(layout, filename, datePattern);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String)
	 */
	@Override
	public void setFile(String file) {
		// TODO Auto-generated method stub
		/*重写文件输出路径*/
		file = ParamUtil.getLogPath(file);
//		System.out.println("设置输出日志："+file);
		super.setFile(file);
	}

}
