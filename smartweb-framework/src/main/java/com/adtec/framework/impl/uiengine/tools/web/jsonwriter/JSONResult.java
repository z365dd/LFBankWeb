
package com.adtec.framework.impl.uiengine.tools.web.jsonwriter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.interfaces.share.IDataset;


/**
 * 
 * 功能说明: 把任何数据转换为json格式<br>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl<br>
 * 开发时间: 2016-9-22<br>
 */
public class JSONResult {
	// 是否输出属性值 中有null值的属性,true不输出，false输出
	private boolean excludeNullProperties = true;
	// json数据编码格式
	private String encoding = "UTF-8";   
	// 需要排除的属性
	private Collection<Pattern> excludeProperties = null;
	// 需要包含的属性
	private Collection<Pattern> includeProperties = null;
	// 需要更改输出属性名的属性集
	private java.util.Map fieldNameMapping = null;
	// 设置是否启用忽略父类的属性,默认是不忽略
	private boolean ignoreHierarchy = false;

	//Clob长度限制,如果不设置，则全部输出
	private int maxClobLength = 0;
	//Blob的长度限制，如果不设置，则全部输出
	private int maxBlobLength = 0;    
	// 20180718 add by chenyl for 是否把属性名设置成大写
	private boolean initD = false;
	
	public int getMaxBlobLength() {
		return maxBlobLength;
	}

	public void setMaxBlobLength(int maxBlobLength) {
		this.maxBlobLength = maxBlobLength;
	}
	public int getMaxClobLength() {
		return maxClobLength;
	}

	public void setMaxClobLength(int maxClobLength) {
		this.maxClobLength = maxClobLength;
	}

	
	/**
	 * @return the initD
	 */
	public boolean isInitD() {
		return initD;
	}

	/**
	 * @param initD the initD to set
	 */
	public void setInitD(boolean initD) {
		this.initD = initD;
	}

	/**
	 * 返回输出json数据是否忽略父类属性
	 * @return true表示忽略父类属性，false表示不忽略父类属性
	 */
	public boolean isIgnoreHierarchy() {
		return ignoreHierarchy;
	}
	
	/**
	 * 设置输出json数据是否启用忽略父类的属性,默认是不忽略
	 * @param ignoreHierarchy ,true表示忽略父类属性，false表示不忽略父类属性
	 */
	public void setIgnoreHierarchy(boolean ignoreHierarchy) {
		this.ignoreHierarchy = ignoreHierarchy;
	}

	/**
	 * 设置是否不输出json数据中属性值为null的属性
	 * 
	 * @param excludeNullProperties
	 * 
	 */
	public void setExcludeNullProperties(boolean excludeNullProperties) {
		this.excludeNullProperties = excludeNullProperties;
	}

	/**
	 * 设置需要排除json数据中的属性,如果有多个patteern用逗号进行分隔
	 * 
	 * @param pattern
	 */
	public void setExcludeProperties(String pattern) {
		if (pattern == null || pattern.length() > 0) {
			StringTokenizer token = new StringTokenizer(pattern, ",");
			java.util.Set excludedPatterns = new java.util.HashSet();
			while (token.hasMoreTokens()) {
				String regex = token.nextToken();
				excludedPatterns.add(Pattern.compile(regex));
			}
			if (excludedPatterns.isEmpty()) {
				this.excludeProperties = null;
			} else {
				this.excludeProperties = excludedPatterns;
			}
		}
	}

	/**
	 * 设置需要包含json数据中的属性,如果有多个patteern用逗号进行分隔
	 * 
	 * @param pattern
	 */
	public void setIncludeProperties(String pattern) {
		if (pattern == null || pattern.length() > 0) {
			StringTokenizer token = new StringTokenizer(pattern, ",");
			java.util.Set includedPatterns = new java.util.HashSet();
			while (token.hasMoreTokens()) {
				String regex = token.nextToken();
				includedPatterns.add(Pattern.compile(regex));
			}
			if (includedPatterns.isEmpty()) {
				this.includeProperties = null;
			} else {
				this.includeProperties = includedPatterns;
			}
		}
	}

	/**
	 * 设置json输出的字符编码
	 * 
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 输出json函数，对于数据中，指定要排除的属性，或者包含的属性，和需要重命名的属性进行正则的检验，
	 * 并组装这些数据。然后在把这些数据传给生成json格式的类进行处理
	 * 
	 * @param key
	 *            object的key值
	 * @param object
	 *            需要转换成json格式的数据
	 * @param success
	 *            是否成功
	 * @param errorMessage
	 *            错误信息
	 * @param excludeFieldsPattern
	 *            需要排除的属性的正则表达式
	 * @param includeFieldsPattern
	 *            需要包含的属性的正则表达式
	 * @param mapping
	 *            需要重命名属性的正则表达式
	 */
	public String write(String key, Object object, boolean success,
			String errorMessage, String excludeFieldsPattern,
			String includeFieldsPattern, String mapping) throws BaseException {
		
		try {
			JSONWriter writer = new JSONWriter();
			writer.setInitD(initD);
			if (excludeFieldsPattern != null
					&& excludeFieldsPattern.trim().length() > 0) {
				setExcludeProperties(excludeFieldsPattern);
			}
			if (includeFieldsPattern != null
					&& includeFieldsPattern.trim().length() > 0) {
				setIncludeProperties(includeFieldsPattern);
			}
			if (mapping != null && mapping.trim().length() > 0) {
				setFieldMapping(mapping);
			}
			Map context = new HashMap();
			if (key != null && key.trim().length() > 0) {
				context.put(key, object);
			} else if (object instanceof java.util.Map) {
				context.putAll((java.util.Map) object);
			} else {
				context.put("data", object);
			}
			if(!context.containsKey("success")) { 
				context.put("success", success);
			}
		
			if (!context.containsKey("errorMessage") && errorMessage != null) {
				context.put("errorMessage", errorMessage);
			}
			
			//设置Clob和Blob的长度
			writer.setMaxClobLength(maxClobLength);
			writer.setMaxBlobLength(maxBlobLength);
			String result = writer.write(context, excludeProperties,
					includeProperties, excludeNullProperties, fieldNameMapping);
			return result;
		} catch (Exception e) {			
			throw new BaseException(SysErr.E_JSON_RESULT, e);
		}
	}

	/**
	 * json的简单输出
	 * 
	 * @param key
	 * @param object
	 */
	public String write(String key, Object object) throws BaseException {
		
		try {
			JSONWriter writer = new JSONWriter();
			writer.setInitD(initD);
			Map context = new HashMap();
			if (key != null && key.trim().length() > 0) {
				context.put(key, object);
				object = context;
			}
			// 设置是否忽略父类属性
			writer.setIgnoreHierarchy(this.isIgnoreHierarchy());
			//设置Clob和Blob的长度
			writer.setMaxClobLength(maxClobLength);
			writer.setMaxBlobLength(maxBlobLength);
			String result = writer.write(object, null, null,
					excludeNullProperties, null);
			return result;
		} catch (Exception e) {			
			throw new BaseException(SysErr.E_JSON_RESULT, e);
		}
	}
	
	/**
	 * 解析对象，转换为json字符串
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public String parseJson(String key, Object object) throws BaseException {
		try {
			JSONWriter writer = new JSONWriter();
			writer.setInitD(initD);
			Map context = new HashMap();
			if (key != null && key.trim().length() > 0) {
				context.put(key, object);
				object = context;
			}
			// 设置是否忽略父类属性
			writer.setIgnoreHierarchy(this.isIgnoreHierarchy());
			//设置Clob和Blob的长度
			writer.setMaxClobLength(maxClobLength);
			writer.setMaxBlobLength(maxBlobLength);
			String result = writer.write(object, null, null,
					excludeNullProperties, null);
            return result;
			//response.getWriter().write(result);
		}catch (Exception e) {			
			throw new BaseException(SysErr.E_JSON_RESULT, e);
		}
	}

	/**
	 * 把需要重新命名的属性进行处理
	 * 
	 * @param mapping
	 */
	public void setFieldMapping(String pattern) {
		if (pattern == null || pattern.length() > 0) {
			StringTokenizer token = new StringTokenizer(pattern, ",");
			while (token.hasMoreTokens()) {
				String regex = token.nextToken();
				StringTokenizer token2 = new StringTokenizer(regex, ":");
				String key = null, value = null;
				if (token2.hasMoreTokens()) {
					key = token2.nextToken();
				}
				if (token2.hasMoreTokens()) {
					value = token2.nextToken();
				}

				if (key != null && value != null) {
					if (this.fieldNameMapping == null) {
						this.fieldNameMapping = new java.util.HashMap();
					}
					this.fieldNameMapping.put(key, value);
				}
			}

		}
	}
	
	
	/**打印树
	 * method comments here
	 * @param object
	 * @param mapping
	 */
	public String treeWrite(Object object, String mapping) throws BaseException {
		
		try {
			
			if (mapping != null && mapping.trim().length() > 0) {
				setFieldMapping(mapping);
			}
			
			JSONWriter writer = new JSONWriter();
			
			String result;
			if(fieldNameMapping!=null && fieldNameMapping.size()>0){
				result = writer.write(object, fieldNameMapping);
			}else{
				result = writer.write(object);
			}
			
			return result;
		}catch (Exception e) {			
			throw new BaseException(SysErr.E_JSON_RESULT, e);
		}
	}
	
	/**
	 * method comments here
	 * @param object
	 * @param mapping
	 * @return
	 * @throws BaseException
	 */
	public String parseTreeJson(Object object, String mapping) throws BaseException {		
		try {
			
			if (mapping != null && mapping.trim().length() > 0) {
				setFieldMapping(mapping);
			}
			
			JSONWriter writer = new JSONWriter();
			
			String result;
			if(fieldNameMapping!=null && fieldNameMapping.size()>0){
				result = writer.write(object, fieldNameMapping);
			}else{
				result = writer.write(object);
			}
			return result;
			
		} catch (Exception e) {
			throw new BaseException(SysErr.E_JSON_RESULT, e);
		}
	}
	
	/**
	 * method comments here
	 * @param dataset
	 * @param isMap
	 * @return
	 * @throws BaseException
	 */
	public String parseIDatasetJson(IDataset dataset,boolean isMap)throws BaseException{
		JSONWriter writer = new JSONWriter();
		String result;
		result = writer.write(dataset, isMap, null, null, false);
		return result;
	}

}
