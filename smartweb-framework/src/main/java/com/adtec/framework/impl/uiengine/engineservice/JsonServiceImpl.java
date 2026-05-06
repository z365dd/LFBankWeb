
package com.adtec.framework.impl.uiengine.engineservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.uiengine.tools.web.jsonwriter.JSONResult;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.uiengine.JsonService;

/**
 * 功能说明: json转化接口服务的实现类<br>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-22<br>
 * 功能描述: 提供把数据转化为json格式字符串<br>
 */

public class JsonServiceImpl implements JsonService {
	
	/**
	 * jsonResult
	 */
	private  JSONResult jsonResult=new JSONResult();
	
	/**
	 * log
	 */
	
	private static final Logger log = LoggerFactory.getLogger(JsonServiceImpl.class); 
	
	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.uiengine.engineservice.JsonService#parse(java.lang.Object)
	 */
	public String parse(Object object) {
		String json = "";		
		try {		
			json = jsonResult.write(null, object);
		} catch (Exception e) {			
			log.error(SysErr.E_IO_ERROR, e);
			json = "";
		}
		return json;
	}

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.uiengine.engineservice.JsonService#parseTree(java.lang.Object, java.lang.String)
	 */
	public String parseTree(Object object, String mapping) {
		String json = "";
		try {
//			log.debug("JsonServiceImpl parseTree mapping = " +mapping);
			json = jsonResult.parseTreeJson(object, mapping);
		} catch (Exception e) {			
			log.error(SysErr.E_CAN_NOT_SET_TEMPLATELOADER, e);
			json = "";
		}
		return json;
	}
    
	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.uiengine.engineservice.JsonService#parseDataset(com.adtec.framework.interfaces.share.dataset.IDataset, boolean)
	 */
	public String parseDataset(IDataset dataset,boolean isMap){
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{\"data\" : ");
		
		try {
			String json = jsonResult.parseIDatasetJson(dataset, isMap);
			jsonBuilder.append(json);
		} catch (Exception e) {
			log.error(SysErr.E_PWINDOW_PROPERTY_NAME,e);
			return "";
		}
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}





	
}
