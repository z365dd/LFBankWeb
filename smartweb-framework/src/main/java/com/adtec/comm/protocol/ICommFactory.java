package com.adtec.comm.protocol;

import java.util.HashMap;

public interface ICommFactory {
	
	/**
	 * 调用URL地址的请求服务
	 * @param url 			请求url地址
	 * @param reqObj 		请求DTO传输数据对象
	 * @param resBodyClass 		响应报文class
	 * @return 返回响应报文DTO传输数据对象
	 */
	public Object callServiceByUrl(String url, Object reqObj, Class<?> resBodyClass);
	
	/**
	 * 通过微服务寻址，并调用请求服务
	 * @param serviceCode 	请求服务名
	 * @param reqObj 		请求DTO传输数据对象
	 * @param resBodyClass 		响应报文class
	 * @return 返回响应报文DTO传输数据对象
	 */
	public Object callService(String serviceCode, Object reqObj, Class<?> resBodyClass);
	
	/**
	 * 通过微服务寻址，并调用请求服务(带动态参数)
	 * @param tenant		服务所属租户
	 * @param partId		服务所属参与者ID
	 * @param serviceCode	服务码
	 * @param reqObj		请求DTO传输数据对象
	 * @param resBodyClass	响应报文体的类
	 * @param dyncParam		动态参数，可以实现灰度、流控、熔断、黑白名单
	 * @return 返回响应报文DTO传输数据对象
	 */
	public Object callService(String tenant, String partId, String serviceCode, Object reqObj,Class<?> resBodyClass, HashMap<String, Object> dyncParam);
	
}
