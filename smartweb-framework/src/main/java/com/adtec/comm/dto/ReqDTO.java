/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求报文体
 * 类 名 称  : MBC_REQ_APP_HEAD.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月17日 下午6:24:15<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto;

import com.adtec.comm.dto.head.AppHeadReqDTO;
import com.adtec.comm.dto.head.LocalHeadReqDTO;
import com.adtec.comm.dto.head.SysHeadReqDTO;

public class ReqDTO {
	/*中间业务云平台公共系统报文头REQ*/
	private SysHeadReqDTO SYS_HEAD;
	/*中间业务云平台公共应用报文头REQ*/
	private AppHeadReqDTO APP_HEAD;
	/*中间业务云平台公共本地报文头REQ*/
	private LocalHeadReqDTO LOCAL_HEAD;
	/*报文体*/
	private Object BODY;
	
	public ReqDTO(){}	
	
	public ReqDTO(Object body){
		SYS_HEAD = new SysHeadReqDTO();
		APP_HEAD = new AppHeadReqDTO();
		LOCAL_HEAD = new LocalHeadReqDTO();
		BODY = body;
	}
	
	/**
	 * @param sYS_HEAD
	 * @param aPP_HEAD
	 * @param lOCAL_HEAD
	 * @param bODY
	 */
	public ReqDTO(SysHeadReqDTO sYS_HEAD, AppHeadReqDTO aPP_HEAD, LocalHeadReqDTO lOCAL_HEAD, Object bODY) {
		super();
		SYS_HEAD = sYS_HEAD;
		APP_HEAD = aPP_HEAD;
		LOCAL_HEAD = lOCAL_HEAD;
		BODY = bODY;
	}


	/**
	 * @return the sYS_HEAD
	 */
	public SysHeadReqDTO getSYS_HEAD() {
		return SYS_HEAD;
	}
	/**
	 * @param sYS_HEAD the sYS_HEAD to set
	 */
	public void setSYS_HEAD(SysHeadReqDTO sYS_HEAD) {
		SYS_HEAD = sYS_HEAD;
	}
	/**
	 * @return the aPP_HEAD
	 */
	public AppHeadReqDTO getAPP_HEAD() {
		return APP_HEAD;
	}
	/**
	 * @param aPP_HEAD the aPP_HEAD to set
	 */
	public void setAPP_HEAD(AppHeadReqDTO aPP_HEAD) {
		APP_HEAD = aPP_HEAD;
	}
	/**
	 * @return the lOCAL_HEAD
	 */
	public LocalHeadReqDTO getLOCAL_HEAD() {
		return LOCAL_HEAD;
	}
	/**
	 * @param lOCAL_HEAD the lOCAL_HEAD to set
	 */
	public void setLOCAL_HEAD(LocalHeadReqDTO lOCAL_HEAD) {
		LOCAL_HEAD = lOCAL_HEAD;
	}
	/**
	 * @return the bODY
	 */
	public Object getBODY() {
		return BODY;
	}
	/**
	 * @param bODY the bODY to set
	 */
	public void setBODY(Object bODY) {
		BODY = bODY;
	}
	
}
