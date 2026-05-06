/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类 名 称: Service.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年5月27日 上午10:00:31<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.protocol.httpjson.xmlbean;

import java.io.Serializable;

/**
 * @author chenyl
 *
 */
public class Service implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9165201933424999832L;
	/*请求外部的服务码*/
	private String id;
	/*服务描述*/
	private String name;
	/*服务请求路径*/
	private String url;
	/*该服务下使用的响应报文体*/
	private ResponseBody responseBody;
	
	public Service(){}
	
	public Service(String id, String name, String url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ResponseBody getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}
	
}
