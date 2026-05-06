/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 响应报文
 * 类 名 称  : MBC_RES.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月17日 下午6:26:56<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto;

import com.adtec.comm.dto.head.AppHeadResDTO;
import com.adtec.comm.dto.head.LocalHeadResDTO;
import com.adtec.comm.dto.head.SysHeadResDTO;

/**
 * @author yyjgs03
 *
 */
public class ResDTO {
	/*中间业务云平台公共系统报文头RES*/
	private SysHeadResDTO SYS_HEAD;
	/*中间业务云平台公共应用报文头RES*/
	private AppHeadResDTO APP_HEAD;
	/*中间业务云平台公共本地报文头RES*/
	private LocalHeadResDTO LOCAL_HEAD;
	/*报文体*/
	private Object BODY;
	/**
	 * 
	 */
	public ResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param sYS_HEAD
	 * @param aPP_HEAD
	 * @param lOCAL_HEAD
	 * @param bODY
	 */
	public ResDTO(SysHeadResDTO sYS_HEAD, AppHeadResDTO aPP_HEAD, LocalHeadResDTO lOCAL_HEAD, Object bODY) {
		super();
		SYS_HEAD = sYS_HEAD;
		APP_HEAD = aPP_HEAD;
		LOCAL_HEAD = lOCAL_HEAD;
		BODY = bODY;
	}
	
	public SysHeadResDTO getSYS_HEAD() {
		return SYS_HEAD;
	}
	public void setSYS_HEAD(SysHeadResDTO sYS_HEAD) {
		SYS_HEAD = sYS_HEAD;
	}
	/**
	 * @return the aPP_HEAD
	 */
	public AppHeadResDTO getAPP_HEAD() {
		return APP_HEAD;
	}
	/**
	 * @param aPP_HEAD the aPP_HEAD to set
	 */
	public void setAPP_HEAD(AppHeadResDTO aPP_HEAD) {
		APP_HEAD = aPP_HEAD;
	}
	/**
	 * @return the lOCAL_HEAD
	 */
	public LocalHeadResDTO getLOCAL_HEAD() {
		return LOCAL_HEAD;
	}
	/**
	 * @param lOCAL_HEAD the lOCAL_HEAD to set
	 */
	public void setLOCAL_HEAD(LocalHeadResDTO lOCAL_HEAD) {
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
