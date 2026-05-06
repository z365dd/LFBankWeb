/**
 * 系统名称: SmartWeb平台
 * 模块名称: JSON数据模板中整个Schema数据结构存储对象
 * 类  名  称: JsonSchemaDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年10月2日 上午12:17:23<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.util.jsonschema;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.curator.shaded.com.google.common.collect.Maps;

import com.adtec.framework.common.util.JsonUtil;

/**
 * @author chenyl
 *
 */
public class JsonSchemaDO {
	/**
	 * 规则定义数据对象
	 */
	private HashMap<String, JsonSchemaDefinitionsDO> definitions = Maps.newHashMap();
	/**
	 * schema所使用的校验规则，默认draft-07，不允许修改
	 */
	private static final String $schema = "http://json-schema.org/draft-07/schema#";
	/**
	 * schema中定义json模板层级URI，默认为http://wwww.adtec.com
	 */
	private String $id = "http://wwww.adtec.com";
	/**
	 * 当前json节点类型，默认 object-对象
	 */
	private String type = JsonSchemaKeyType.TYPE_OBJECT;
	/**
	 * 当前节点的标题
	 */
	private String title;
	/**
	 * 当前json节点下的必输key列表
	 */
	private ArrayList<String> required = Lists.newArrayList();
	/**
	 * 存储当前json节点下的所有key的属性定义列表
	 */
	private HashMap<String, JsonSchemaPropertyDO> properties = Maps.newHashMap();
	
	public JsonSchemaDO(){
		
	}
	
	/**
	 * @param definitions
	 * @param $id
	 * @param type
	 * @param title
	 * @param required
	 * @param properties
	 */
	public JsonSchemaDO(HashMap<String, JsonSchemaDefinitionsDO> definitions, String $id, String type, String title,
			ArrayList<String> required, HashMap<String, JsonSchemaPropertyDO> properties) {
		super();
		this.definitions = definitions;
		this.$id = $id;
		this.type = type;
		this.title = title;
		this.required = required;
		this.properties = properties;
	}
	/**
	 * @return the definitions
	 */
	public HashMap<String, JsonSchemaDefinitionsDO> getDefinitions() {
		return definitions;
	}
	/**
	 * @param definitions the definitions to set
	 */
	public void setDefinitions(HashMap<String, JsonSchemaDefinitionsDO> definitions) {
		this.definitions = definitions;
	}
	/**
	 * @return the $id
	 */
	public String get$id() {
		return $id;
	}
	/**
	 * @param $id the $id to set
	 */
	public void set$id(String $id) {
		this.$id = $id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the required
	 */
	public ArrayList<String> getRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(ArrayList<String> required) {
		this.required = required;
	}
	/**
	 * @return the properties
	 */
	public HashMap<String, JsonSchemaPropertyDO> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(HashMap<String, JsonSchemaPropertyDO> properties) {
		this.properties = properties;
	}
	/**
	 * @return the $schema
	 */
	public static String get$schema() {
		return $schema;
	}
	
	/**
	 * 设置必输字段
	 * @param key
	 */
	public void setRequiredKey(String key){
		this.required.add(key);
	}
	
	/**
	 * 移除必输字段
	 * @param key
	 */
	public void delRequiredKey(String key){
		this.required.remove(key);
	}
	
	/**
	 * 添加字段属性配置
	 * @param key			当前节点key名称
	 * @param value			key的配置属性
	 */
	public void setKeyProperties(String key, JsonSchemaPropertyDO value){
		this.properties.put(key, value);
	}
	
	/**
	 * 移除字段属性配置
	 * @param key
	 * @param value
	 */
	public void delKeyProperties(String key){
		if(this.properties.containsKey(key)){
			this.properties.remove(key);
		}
	}
	
	/**
	 * 输出当前对象的json字符串
	 * @return
	 */
	public String toJsonString(){
		return JsonUtil.obj2StringIgnoreNull(this);
	}
}
