package com.adtec.framework.impl.uiengine.engineservice;

import com.adtec.framework.interfaces.uiengine.JsonService;
import com.adtec.framework.interfaces.uiengine.JsonServiceFactory;

/**
 * 功能说明: JsonServiceFactory的实现类<br>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-22<br>
 * 功能描述: 从工厂中获取一个JsonService实例<br>
 */

public class JsonServiceFactoryImpl implements JsonServiceFactory {
   
	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.uiengine.engineservice.JsonServiceFactory#getJsonService()
	 */
	public JsonService getJsonService() {
		JsonService obj = new JsonServiceImpl();
		return obj;
	}
}
