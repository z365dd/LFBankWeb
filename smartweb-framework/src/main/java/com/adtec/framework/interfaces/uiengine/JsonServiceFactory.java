
package com.adtec.framework.interfaces.uiengine;

/**
 * 功能说明: JsonService的工厂接口<br>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-22<br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */

public interface JsonServiceFactory{
	
	/**
	 * 获取一个JsonService接口的实例
	 * @return    JsonService的实现类
	 */
	public JsonService  getJsonService();

}
