/**
 * 系统名称: SmartWeb平台
 * 模块名称: 异常模块
 * 功能描述: 异常信息工具类
 * 类 名 称  : ErrInfo.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月18日 上午11:27:39<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.exception;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author chenyl
 *
 */
public class ErrInfo {

	private String sFileName;
	private String sLineNum;
	private String sClassName;
	private String sMethodName;
	private String sCode;
	private String sMesg;
	private String sLocInfo;
	private boolean warnFlag = false;

	public boolean isWarnFlag() {
		return this.warnFlag;
	}

	public void setWarnFlag(boolean warnFlag) {
		this.warnFlag = warnFlag;
	}

	public ErrInfo() {
	}

	public ErrInfo(String sFile, String sLine, String sClass, String sMethod, String sEcode, String sEmsg) {
		this.sFileName = sFile;
		this.sLineNum = sLine;
		this.sClassName = sClass;
		this.sMethodName = sMethod;
		this.sCode = sEcode;
		this.sMesg = sEmsg;

		this.sLocInfo = sClass + "." + sMethod + "(" + sFile + ":" + sLine + ")";
	}

	public ErrInfo(LocInfo locinfo, String sEcode) {
		this.sFileName = locinfo.getFileName();
		this.sLineNum = locinfo.getLineNum();
		this.sClassName = locinfo.getClassName();
		this.sMethodName = locinfo.getMethodName();
		this.sCode = sEcode;

		this.sLocInfo = locinfo.getLocInfo();
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

	public String getErrCode() {
		return this.sCode;
	}

	public String getErrMsg() {
		return this.sMesg;
	}

	public void setFileName(String sFile) {
		this.sFileName = sFile;
	}

	public void setLineNum(String sLine) {
		this.sLineNum = sLine;
	}

	public void setClassName(String sClass) {
		this.sClassName = sClass;
	}

	public void setMethodName(String sMethod) {
		this.sMethodName = sMethod;
	}

	public void setErrCode(String sEcode) {
		this.sCode = sEcode;
	}

	public void setErrMsg(String sEmsg) {
		this.sMesg = sEmsg;
	}

	public String getErrMsgByCode(String sEcode, Object... varg) {
		String sEmsg = (String) ErrMsg.ERR_MSG.get(sEcode);

		if (sEmsg == null) {
			return sEcode;
		}
		this.sMesg = String.format(sEmsg, varg);

		String[] ms = splitStr(this.sMesg, "@WARN@");
		if (ms.length > 1) {
			this.warnFlag = true;

			return ms[0];
		}
		return this.sMesg;
	}

	public static String[] splitStr(String srcStr, String seperator) {
		String[] strArray = null;
		ArrayList strArrayList = new ArrayList();

		if ((seperator == null) || (seperator.equals(""))) {
			return new String[] { srcStr };
		}

		if (srcStr == null) {
			return new String[0];
		}
		int sepLen = seperator.length();
		int start = 0;
		int end = srcStr.indexOf(seperator, start);
		String temp = "";
		while (end != -1) {
			temp = srcStr.substring(start, end);
			strArrayList.add(temp);
			start = end + sepLen;
			end = srcStr.indexOf(seperator, start);
		}
		temp = srcStr.substring(start, srcStr.length());
		strArrayList.add(temp);
		return ((String[]) strArrayList.toArray(new String[strArrayList.size()]));
	}

	public static String getCurDateTime() {
		Date now = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd-HHmmss");
		return sdf1.format(now);
	}

}
