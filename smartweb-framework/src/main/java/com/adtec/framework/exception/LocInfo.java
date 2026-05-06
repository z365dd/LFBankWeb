/**
 * 系统名称: SmartWeb平台
 * 模块名称: 异常模块
 * 功能描述: 本地异常信息
 * 类 名 称  : LocInfo.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月18日 下午12:34:37<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.exception;

/**
 * @author chenyl
 *
 */
public class LocInfo {
	private String sFileName;
	private String sLineNum;
	private String sClassName;
	private String sMethodName;
	private String sLocInfo;

	public LocInfo(Throwable t, int idx) {
		StackTraceElement[] elemTab = t.getStackTrace();
		int i = 0;

		if ((idx >= 0) && (idx < elemTab.length)) {
			i = idx;
		} else if (elemTab.length >= 1) {
			i = elemTab.length - 1;
		}

		this.sFileName = elemTab[i].getFileName();
		this.sLineNum = String.valueOf(elemTab[i].getLineNumber());
		this.sClassName = elemTab[i].getClassName();
		this.sMethodName = elemTab[i].getMethodName();

		this.sLocInfo = this.sClassName + "." + this.sMethodName + "(" + this.sFileName + ":" + this.sLineNum + ")";
	}

	public String getFileName() {
		return this.sFileName;
	}

	public String getLineNum() {
		return this.sLineNum;
	}

	public String getClassName() {
		return this.sClassName;
	}

	public String getMethodName() {
		return this.sMethodName;
	}

	public String getLocInfo() {
		return this.sLocInfo;
	}
}
