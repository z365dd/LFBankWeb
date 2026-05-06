/**
* 系统名称: Smartweb平台
* 模块名称: sys-modules的实体类模块
* 功能描述: 日志记录映射管理数据定义
* 类 名 称  : LogMappingDO.java
* 软件版权: XXX公司
* 开发人员: chenyl <br>
* 开发时间: 20210429<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.adtec.sys.common.persistence.BaseDO;

/**
 * 日志记录映射管理
 * @author 陈应龙
 * @version 20210429
 */
public class LogMappingDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String requestUri;		// 请求URI
	private String uriCname;		// 请求URI中文名称
	private String logStat;		// 记录日志状态，Y-记录、N-不记录，默认：N
	private String uriCnameBegin;		// 开始 请求URI中文名称
	private String uriCnameEnd;		// 结束 请求URI中文名称
	
	public LogMappingDO() {
		super();
	}

	public LogMappingDO(String id){
		super(id);
	}

	@Length(min=1, max=255, message="请求URI长度必须介于 1 和 255 之间")
	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	
	@Length(min=1, max=255, message="请求URI中文名称长度必须介于 1 和 255 之间")
	public String getUriCname() {
		return uriCname;
	}

	public void setUriCname(String uriCname) {
		this.uriCname = uriCname;
	}
	
	@Length(min=1, max=1, message="记录日志状态，Y-记录、N-不记录，默认：N长度必须介于 1 和 1 之间")
	public String getLogStat() {
		return logStat;
	}

	public void setLogStat(String logStat) {
		this.logStat = logStat;
	}
	
	public String getUriCnameBegin() {
		return uriCnameBegin;
	}

	public void setUriCnameBegin(String uriCnameBegin) {
		this.uriCnameBegin = uriCnameBegin;
	}
	
	public String getUriCnameEnd() {
		return uriCnameEnd;
	}

	public void setUriCnameEnd(String uriCnameEnd) {
		this.uriCnameEnd = uriCnameEnd;
	}
		
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LogMappingDO [ ");
		sb.append("id="+id+" , ");
		sb.append("requestUri="+requestUri+" , ");
		sb.append("uriCname="+uriCname+" , ");
		sb.append("logStat="+logStat+" , ");
		sb.append("rmrk="+rmrk+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("uriCnameBegin");
		list.add("uriCnameEnd");		
		list.add("delFlg");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("id");
		return list;
	}
}