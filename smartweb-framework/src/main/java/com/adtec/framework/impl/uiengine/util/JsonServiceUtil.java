
package com.adtec.framework.impl.uiengine.util;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * 功能说明: Json服务的辅助工具类<BR>
 * 系统版本: v1.0 <BR>
 * 开发人员: chenyl<BR>
 * 开发时间: 2016-9-22<BR>
 * 审核人员：<BR>
 * 相关文档：<BR>
 * 修改记录：<BR>
 * 修改日期 修改人员 修改说明 <BR>
 * 
 */

public class JsonServiceUtil {

	/**
	 * 将属性映射的字符串拆分
	 * 
	 * @param pattern
	 *            属性映射字符串
	 * @return
	 */
	public static Map setFieldMapping(String pattern) {
		Map fieldNameMapping = new java.util.HashMap();
		if (pattern != null && pattern.length() > 0) {
			StringTokenizer token = new StringTokenizer(pattern, ",");
			while (token.hasMoreTokens()) {
				String regex = token.nextToken();
				String key = null, value = null;

				StringTokenizer token2 = new StringTokenizer(regex, ":");

				if (token2.hasMoreTokens()) {
					key = token2.nextToken();
					if (key.startsWith("\"") && key.endsWith("\"")) {
						key = key.substring(1, key.length() - 1);
					}
				}
				if (token2.hasMoreTokens()) {
					value = token2.nextToken();
					if (value.startsWith("\"") && value.endsWith("\"")) {
						value = value.substring(1, value.length() - 1);
					}
				}
				// key = regex.substring(1, 8);
				// value = regex.substring(11, regex.length()-1);

				if (key != null && value != null) {
					fieldNameMapping.put(key, value);
				}
			}

		}
		return fieldNameMapping;
	}

	public static Map setFieldMapping(String pattern, boolean cases) {
		Map fieldNameMapping = new java.util.HashMap();
		if (pattern != null && pattern.length() > 0) {
			pattern = pattern.substring(1, pattern.length() - 1);
			StringTokenizer token = new StringTokenizer(pattern, ",");
			while (token.hasMoreTokens()) {
				String regex = token.nextToken();
				StringTokenizer token2 = new StringTokenizer(regex, ":");
				String key = null, value = null;
				if (token2.hasMoreTokens()) {
					key = token2.nextToken().trim();
					if (key.startsWith("\"") && key.endsWith("\"")) {
						key = key.substring(1, key.length() - 1);
					}
					if (key.startsWith("'") && key.endsWith("'")) {
						key = key.substring(1, key.length() - 1);
					}
				}
				if (token2.hasMoreTokens()) {
					value = token2.nextToken().trim();
					if (value.startsWith("\"") && value.endsWith("\"")) {
						value = value.substring(1, value.length() - 1);
					}
					if (value.startsWith("'") && value.endsWith("'")) {
						value = value.substring(1, value.length() - 1);
					} else if (value.startsWith("[") && value.endsWith("]")) {
						value = value.substring(1, value.length() - 1);
						String[] objValues = value.split(",");
						for (int i = 0; i < objValues.length; i++) {
							Map objMap = setFieldMapping(objValues[i]);
							fieldNameMapping.put(key, objMap);
						}
					}
				}

				if (key != null && value != null
						&& !fieldNameMapping.containsKey(key)) {
					fieldNameMapping.put(key, value);
				}
			}

		}
		return fieldNameMapping;
	}

	
}
