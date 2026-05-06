/**
 * 系统名称: SmartWeb平台
 * 模块名称: JSON模板中每个key的定义数据对象
 * 类  名  称: JsonSchemaPropertyDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年10月2日 上午12:44:42<br>
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
public class JsonSchemaPropertyDO {
	/**
	 * 公共属性：
	 * schema中定义json模板层级URI，默认为/#properties/节点key
	 */
	private String $id;
	/**
	 * 公共属性：
	 * 标题
	 */
	private String title;
	/**
	 * 公共属性：
	 * 当前json节点类型
	 * 		array-数组
	 * 		boolean-布尔
	 *		integer-整型
	 *		null-空对象
	 *		number-数值（浮点）
	 *		object-对象
	 *		string-字符串
	 */
	private String type;
	/**
	 * 公共属性：
	 * 描述
	 */
	private String description;
	/**
	 * 公共属性：
	 * key的取值例子列表
	 */
	private ArrayList<String> examples;
	/**
	 * 公共属性：
	 * 正则设置
	 */
	private String pattern;
	/**
	 *  最大值，只允许integer类型，值为数字
	 */
	private Long maximum;
	/**
	 *  最小值，只允许integer类型，值为数字
	 */
	private Long minimum;
	/**
	 *  最大长度，只允许string类型，值为数字
	 */
	private Long maxLength;
	/**
	 *  最小长度，只允许string类型，值为数字
	 */
	private Long minLength;
	/**
	 *  最大元素个数，只允许array类型，值为数字
	 */
	private Long maxItems;
	/**
	 *  最小元素个数，只允许array类型，值为数字
	 */
	private Long minItems;
	/**
	 * 对象类型属性：
	 * 当前json节点下的必输key列表
	 */
	private ArrayList<String> required;
	/**
	 * 对象类型属性：
	 * 存储对象节点当前json节点下的所有key的属性定义列表
	 */
	private HashMap<String, JsonSchemaPropertyDO> properties;
	
	/**
	 * 数组类型属性：
	 * 如果当前才存在该元素
	 */
	private JsonSchemaPropertyDO items;
	
	public JsonSchemaPropertyDO(){
		/*默认创建为对象属性节点*/
		this.properties = Maps.newHashMap();
		this.required = Lists.newArrayList();
		this.type = JsonSchemaKeyType.TYPE_OBJECT;
	}
	
	/**
	 * 当前节点类型
	 * @param type
	 */
	public JsonSchemaPropertyDO(String type){
		if(JsonSchemaKeyType.TYPE_OBJECT.equals(type)){
			/*创建对象属性节点*/
			this.properties = Maps.newHashMap();
			this.required = Lists.newArrayList();
		} else if(JsonSchemaKeyType.TYPE_ARRAY.equals(type)){
			/*创建数组属性节点*/
			this.items = new JsonSchemaPropertyDO();
		}
		this.type = type;
	}

	/**
	 * 当前节点类型
	 * @param $id		当前节点所在的位置
	 * @param type	
	 */
	public JsonSchemaPropertyDO(String $id, String type){
		this.type = type;
		this.$id = $id;
		if(JsonSchemaKeyType.TYPE_OBJECT.equals(type)){
			/*创建对象属性节点*/
			this.properties = Maps.newHashMap();
			this.required = Lists.newArrayList();
		} else if(JsonSchemaKeyType.TYPE_ARRAY.equals(type)){
			/*创建数组属性节点*/
			this.items = new JsonSchemaPropertyDO(this.$id+"/"+JsonSchemaKeyType.ITEMS_URI, JsonSchemaKeyType.TYPE_OBJECT);
		}
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the examples
	 */
	public ArrayList<String> getExamples() {
		return examples;
	}

	/**
	 * @param examples the examples to set
	 */
	public void setExamples(ArrayList<String> examples) {
		this.examples = examples;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	

	public Long getMaximum() {
		return maximum;
	}

	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}

	public Long getMinimum() {
		return minimum;
	}

	public void setMinimum(Long minimum) {
		this.minimum = minimum;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public Long getMinLength() {
		return minLength;
	}

	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

	public Long getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(Long maxItems) {
		this.maxItems = maxItems;
	}

	public Long getMinItems() {
		return minItems;
	}

	public void setMinItems(Long minItems) {
		this.minItems = minItems;
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

	public JsonSchemaPropertyDO getItems() {
		return items;
	}

	public void setItems(JsonSchemaPropertyDO items) {
		this.items = items;
	}

	/**
	 * 输出当前对象的json字符串
	 * @return
	 */
	public String toJsonString(){
		return JsonUtil.obj2StringIgnoreNull(this);
	}
}
