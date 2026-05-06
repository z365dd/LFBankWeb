package com.adtec.comp.ctrl.oper.util;

import java.util.ResourceBundle;

public class BusiUtil {

	/**
	 * 获取配置文件中的属性
	 * @param propertiesFileName
	 * @param key
	 * @return
	 */
	public static String getBusiConfigOneInfo(String propertiesFileName,String key) {
		// 获得资源包
		ResourceBundle source = ResourceBundle.getBundle(propertiesFileName.trim());
		return source.getString(key);
	}
}
