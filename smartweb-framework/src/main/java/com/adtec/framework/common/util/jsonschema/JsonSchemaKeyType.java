/**
 * 系统名称: SmartWeb平台
 * 模块名称: JSON模板中key的数据类型
 * 类  名  称: JsonSchemaKeyType.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年10月2日 上午9:51:18<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.util.jsonschema;

/**
 * @author chenyl
 *
 */
public class JsonSchemaKeyType {
	/**
	 * 对象类型
	 */
	public final static String TYPE_OBJECT = "object";
	
	/**
	 * 数组类型
	 */
	public final static String TYPE_ARRAY = "array";
	
	/**
	 * 布尔类型
	 */
	public final static String TYPE_BOOLEAN = "boolean";
	
	/**
	 * 整型类型
	 */
	public final static String TYPE_INTEGER = "integer";
	
	/**
	 * 空对象类型
	 */
	public final static String TYPE_NULL = "null";
	
	/**
	 * 数值（浮点）类型
	 */
	public final static String TYPE_NUMBER = "number";
	
	/**
	 * 字符串类型
	 */
	public final static String TYPE_STRING = "string";
	
	/**
	 * json模板中每个节点的定位uri前缀 #
	 */
	public final static String $ID_URI = "#";
	
	/**
	 * json模板中每个对象节点的属性定位uri前缀
	 */
	public final static String PROPERTIES_URI = "/properties/";
	
	/**
	 * json模板中数组节点中每个属性定位uri前缀
	 */
	public final static String ITEMS_URI = "items";
}
