/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求应用头
 * 类 名 称  : ResDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: ruanyh@ <br>
 * 开发时间: 2019年3月25日 下午14:00:00<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.tseq.pub;


import com.adtec.comp.tseq.pub.head.AppHeadResDTO;
import com.adtec.comp.tseq.pub.head.LocalHeadResDTO;
import com.adtec.comp.tseq.pub.head.SysHeadResDTO;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author yyjgs03
 *
 */
public class ResDTO {
	/*中间业务云平台公共系统报文头RES*/
	@JSONField(name="SYS_HEAD")
	private SysHeadResDTO SYS_HEAD;
	/*中间业务云平台公共应用报文头RES*/
	@JSONField(name="APP_HEAD")
	private AppHeadResDTO APP_HEAD;
	/*中间业务云平台公共本地报文头RES*/
	@JSONField(name="LOCAL_HEAD")
	private LocalHeadResDTO LOCAL_HEAD;
	/*报文体*/
	@JSONField(name="BODY")
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
