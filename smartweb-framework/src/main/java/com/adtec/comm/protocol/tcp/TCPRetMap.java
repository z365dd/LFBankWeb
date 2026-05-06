/**
 * 系统名称: SmartWeb平台
 * 模块名称: TCPJsonServer的返回报文类
 * 类  名  称: TCPRetMap.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年3月30日 下午2:22:00<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.protocol.tcp;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.google.common.collect.Maps;

/**
 * @author chenyl
 *
 */
public class TCPRetMap{
	private final static Logger log = LoggerFactory.getLogger(TCPRetMap.class);
	private boolean  isSuccess;
	private String msg;
	private String tran;
	private String subTran;
	private String requestSeqNo;
	private String responseSeqNo;
	private HashMap<String, Object> data;
	
	
	/**
	 * @return the tran
	 */
	public String getTran() {
		return tran;
	}
	/**
	 * @param tran the tran to set
	 */
	public void setTran(String tran) {
		this.tran = tran;
	}
	/**
	 * @return the subTran
	 */
	public String getSubTran() {
		return subTran;
	}
	/**
	 * @param subTran the subTran to set
	 */
	public void setSubTran(String subTran) {
		this.subTran = subTran;
	}
	/**
	 * @return the requestSeqNo
	 */
	public String getRequestSeqNo() {
		return requestSeqNo;
	}
	/**
	 * @param requestSeqNo the requestSeqNo to set
	 */
	public void setRequestSeqNo(String requestSeqNo) {
		this.requestSeqNo = requestSeqNo;
	}
	/**
	 * @return the responseSeqNo
	 */
	public String getResponseSeqNo() {
		return responseSeqNo;
	}
	/**
	 * @param responseSeqNo the responseSeqNo to set
	 */
	public void setResponseSeqNo(String responseSeqNo) {
		this.responseSeqNo = responseSeqNo;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
		if(isSuccess) this.msg = "交易成功";
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		if(!this.isSuccess){
			// 检查是否存在调用栈列表，存在则把日志log指向到上级调用类中,前两层分别为：[java.lang.Thread.getStackTrace、com.adtec.ms.msagent.entity.RetMap.setMsg
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			String orgMsg = "";
			if(null!=stack && stack.length>=3){
				StackTraceElement parentStakc = stack[2];	// 获取上一个调用栈信息
				orgMsg = "调用方["+parentStakc.getClassName()+"."+parentStakc.getMethodName()+":"+parentStakc.getLineNumber()+"] ";
			}
			log.error(orgMsg+msg);
		}
		this.msg = msg;
	}
	public HashMap<String, Object> getData() {
		return data;
	}
	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	public HashMap<String, Object> toMap(){
		HashMap<String, Object> map = Maps.newHashMap();
		if(isSuccess){
			map.put(TCPJsonServer.RETCODE, SysErr.E_SUCCESS);
		}else{
			if(null!=BaseException.getErrRegInfo() && BaseException.getErrRegInfo().length>0){
				map.put(TCPJsonServer.RETCODE, BaseException.getErrRegInfo()[0].getErrCode());
				msg = BaseException.getErrRegInfo()[0].getErrMsg();
			}else{
				map.put(TCPJsonServer.RETCODE, SysErr.E_DEFAULT);
			}
		}
		map.put(TCPJsonServer.MSG, msg);
		map.put(TCPJsonServer.TRAN, tran);
		map.put(TCPJsonServer.SUB_TRAN, subTran);
		map.put(TCPJsonServer.REQUEST_SEQ_NO, requestSeqNo);
		map.put(TCPJsonServer.RESPONSE_SEQ_NO, responseSeqNo);
		if(data !=null ) map.putAll(data);
		return map;
	}
}
