/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: AbstractResource.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明<BR>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.adtec.framework.common.util.resource;

import java.net.URL;

/**
 * 
 * 功能说明: IAdapterURLBean,bizkernerl执行框架，加载资源IAdapter的信息描述<br>
 * <p>
 * 执行框架加载IAdapter的资源规范请参看接口，资源定义接口{@link IAdapter}，加载资源规范接口
 * {@link Loader}。
 * <p>
 * IAdapter资源描述包含很多信息，比如资源所在路径，资源最后修改时间，资源所在位置，是否被执行框架成功加载等信息
 * <p>
 * 
 * 系统版本: v1.0<br> 
 * 开发人员: <br>
 * 开发时间: <br>
 * 功能描述：写明作用，调用方式，使用场景，以及特殊情况<br>
 */

public class  IAdapterURLBean extends URLBean{

	public final static  String STATE_SUCCESS = "success";//该资源已经被成功加载
	public final static	 String STATE_ERROR = "error";//该资源加载时存在错误
	private String state = STATE_ERROR;//资源的状态，该资源有可能被成功加载，加载失败等
	
	public IAdapterURLBean() {
		super();	
	}

	public IAdapterURLBean(URL url, long lastModified, String rootPath,
			String pathType) {
		super(url, lastModified, rootPath, pathType);
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
}
