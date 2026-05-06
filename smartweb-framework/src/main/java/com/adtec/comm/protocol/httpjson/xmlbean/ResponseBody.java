/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类 名 称: ResponseBody.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年5月27日 上午9:54:55<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.protocol.httpjson.xmlbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyl
 *
 */
public class ResponseBody implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -73502527279732528L;
	/*服务下用到的响应报文体全路径class*/
	private String id;
	/*响应报文体的描述*/
	private String name;
	/*响应报文中包含的自定义属性列表*/
	private List<ResClazz> clazzList = new ArrayList<ResClazz>();
	
	public ResponseBody(){}
	
	/**
	 * @param resId
	 * @param resName
	 */
	public ResponseBody(String resId, String resName) {
		// TODO Auto-generated constructor stub
		this.id = resId;
		this.name = resName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<ResClazz> getClazzList() {
		return clazzList;
	}

	public void setClazzList(List<ResClazz> clazzList) {
		this.clazzList = clazzList;
	}

}
