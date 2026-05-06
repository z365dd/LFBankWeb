/**
 * 系统名称: SmartWeb平台
 * 模块名称: 异常模块
 * 功能描述: 基础异常类
 * 类 名 称  : BaseException.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月18日 上午11:16:12<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.exception;

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author chenyl
 *
 */
public class BaseException extends RuntimeException {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7472218256673267088L;
	private String errorCode = null;
	private String errorDesc = null;

	private static Hashtable eRegList = new Hashtable();

	private static Hashtable<Long, String> errThreadList = new Hashtable();

	public BaseException(Throwable cause) {
		super(cause);
		ErrInfo err = new ErrInfo(new LocInfo(new Throwable(), 1), this.errorCode);
		this.errorDesc = cause.getMessage();
		err.setErrMsg(this.errorDesc);

		addErrInfo(err);

		addErrInfo();
	}

	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.errorCode + ":");
		sb.append(this.errorDesc);
		return sb.toString();
	}

	public BaseException(String errorCode) {
		this.errorCode = errorCode;

		ErrInfo err = new ErrInfo(new LocInfo(new Throwable(), 1), errorCode);
		this.errorDesc = err.getErrMsgByCode(errorCode, new Object[0]);

		addErrInfo(err);
		addErrInfo();
	}

	public BaseException(String errorCode, Throwable cause, Object... varg) {
		super(cause);

		this.errorCode = errorCode;

		ErrInfo err = new ErrInfo(new LocInfo(new Throwable(), 1), errorCode);
		this.errorDesc = err.getErrMsgByCode(errorCode, varg);
		err.setErrMsg(this.errorDesc + "|" + cause.getMessage());

		addErrInfo(err);
		addErrInfo();
	}

	public BaseException(String errorCode, Object... varg) {
		this.errorCode = errorCode;

		ErrInfo err = new ErrInfo(new LocInfo(new Throwable(), 1), errorCode);
		
		this.errorDesc = err.getErrMsgByCode(errorCode, varg);
		addErrInfo(err);
		addErrInfo();
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorDesc() {
		return this.errorDesc;
	}

	public static ErrInfo[] getErrRegInfo() {
		Thread th = Thread.currentThread();
		Vector vErr = (Vector) eRegList.get(Long.valueOf(th.getId()));

		if ((vErr == null) || (vErr.isEmpty())) {
			return null;
		}
		return ((ErrInfo[]) vErr.toArray(new ErrInfo[0]));
	}

	public static void clearErrRegInfo() {
		Thread th = Thread.currentThread();
		Vector vErr = (Vector) eRegList.get(Long.valueOf(th.getId()));

		if ((vErr != null) && (!(vErr.isEmpty()))) {
			vErr.clear();
		}
		eRegList.remove(Long.valueOf(th.getId()));
	}

	private void addErrInfo(ErrInfo err) {
		Thread th = Thread.currentThread();
		Vector vErr = (Vector) eRegList.get(Long.valueOf(th.getId()));

		if (vErr == null) {
			vErr = new Vector();
		}

		vErr.add(err);
		eRegList.put(Long.valueOf(th.getId()), vErr);
	}

	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
	}

	public void printStackTrace() {
		super.printStackTrace(System.err);
	}

	private static long getNowThreadID() {
		Thread th = Thread.currentThread();
		return th.getId();
	}

	private static void addErrInfo() {
		errThreadList.put(Long.valueOf(getNowThreadID()), "");
	}

	public static boolean checkErrInfo() {
		return errThreadList.containsKey(Long.valueOf(getNowThreadID()));
	}

	public static void clearErrInfo() {
		errThreadList.remove(Long.valueOf(getNowThreadID()));
	}


}
