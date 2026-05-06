/**
 * 
 */
package com.adtec.framework.common.util;

import com.adtec.framework.common.filter.DateJsonValueProcessor;
import com.adtec.framework.common.filter.JsonPropertyFilter;
import com.adtec.framework.ide.po.ChartBar;
import com.adtec.framework.ide.po.ChartPie;
import com.adtec.framework.ide.po.Datasets;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @类名 JsonUtil
 * @描述:
     Json工具类
 * @版本 v1.0
 */
public class JsonUtil {

	private final static Logger log = LoggerFactory.getLogger(JsonUtil.class.getName());

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private static ObjectMapper objectMapperIgnoreNull = new ObjectMapper();

	private static ObjectMapper upperCaseObjectMapper = new ObjectMapper();

	static {
		upperCaseObjectMapper.setPropertyNamingStrategy(new UpperCaseWithUnderscoresStrategy());
		upperCaseObjectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		// 序列化的时候序列对象的所有属性
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

		// 反序列化的时候如果多了其他属性,不抛出异常
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 如果是空对象的时候,不抛异常
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

		// 取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		
		/*20191002 add by chenyl 新增json转换管理器忽略掉null属性*/
		// 序列化的时候序列对象的所有属性
		objectMapperIgnoreNull.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

		// 反序列化的时候如果多了其他属性,不抛出异常
		objectMapperIgnoreNull.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 如果是空对象的时候,不抛异常
		objectMapperIgnoreNull.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

		// 取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
		objectMapperIgnoreNull.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		objectMapperIgnoreNull.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
	/** 
     * JSON 时间解析器具 
     * @param datePattern 
     * @return 
     */  
    public static JsonConfig configJson(String datePattern){     
        JsonConfig jsonConfig = new JsonConfig();     
        jsonConfig.setExcludes(new String[]{""});     
        jsonConfig.setIgnoreDefaultExcludes(false);     
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);     
        jsonConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor(datePattern));    
        return jsonConfig;     
    } 
    
    /**  
     * 从一个Object转换为JSON格式的数据 
     * @param message,Obj
     * @return  JSONObject
     */  
	@SuppressWarnings("unchecked")
	public static JSONObject generate(Object obj,String... message) {  
       Map <String, Object> map = new HashMap <String, Object>(); 
 		map.put("obj",obj);   
 		map.put("msg",message);  
 		map.put("flag", true); 
 		return JSONObject.fromObject(map); 
 	} 
	
	/**  
     * 从一个Object转换为JSON格式的数据 ,不进行封装
     * @param message,Obj
     * @return  JSONObject
     */
	@SuppressWarnings("unchecked")
	public static JSONObject generateObj(Object obj) {   
 		return JSONObject.fromObject(obj); 
 	} 
	
	/**  
     * 从一个flag转换为JSON格式的数据 
     * @param flag
     * @return  JSONObject
     */  
	public static JSONObject generate(Boolean flag) { 
	    Map <String, Object> map = new HashMap <String, Object>();  
	 	map.put("flag", flag); 
	 	return JSONObject.fromObject(map); 
	} 
	
	/**  
     * 从一个flag转换为JSON格式的数据 
     * @param flag
     * @return  JSONObject
     */  
	public static JSONArray generateTree(List list) {  
	 	return JSONArray.fromObject(list); 
	} 
	
	/**  
     * easyUI格式封装
     * @param message,Obj
     * @return  JSONObject
     */  
	@SuppressWarnings("unchecked")
	public static JSONObject generateEasyUI(List list,Integer num) {  
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(""));
		jsonConfig.setJsonPropertyFilter(new JsonPropertyFilter());
        Map <String, Object> map = new HashMap <String, Object>(); 
 		map.put("rows",list);   
 		map.put("total",num);
 		return JSONObject.fromObject(map,jsonConfig); 
 	}
	
	/**
	 * map转换成JSON格式
	 * @param map
	 * @return JSONObject
	 */
	public static JSONObject generateMap(Map<String, Object> map){
		return JSONObject.fromObject(map);
	}
	/**
	 *  处理饼图、环形图、及地极图json的方法
	 * @param obj
	 * @return 
	 * @return 
	 * @throws Exception
	 * @throws IllegalAccessException
	 */
	public static JSONArray generateObjToChartPie(Object obj) throws Exception, IllegalAccessException{
		List<ChartPie> objList = new ArrayList<ChartPie>();
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		Field[] fields = ClassUtil.getAccessibleFields(obj);
		for (Field o : fields) {
		o.setAccessible(true);
		ChartPie pie = new ChartPie();
		Object value = o.get(obj);
		pie.setLabel(o.getName());
		if (value.getClass().isArray()) {
		Object[] arr = (Object[]) value; 
//		System.out.println("，变量值等于：" + Arrays.toString(arr));
		for (Object a : arr) {
		//System.out.println(a.getClass().getName());
		}
		} else {
			pie.setValue(value.toString());
			
		}
		pie.setColor(getRandColorCode());
		pie.setHighlight(getRandColorCode());
		o.setAccessible(false);
		objList.add(pie);
		}
		return JSONArray.fromObject(objList);
		
	};
	/**
	 * 将数据封装成图表中的柱状图、雷达图、曲线图所需的json格式
	 * @param obj
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	public static JSONObject generateObjListToChartBar(List objList) throws IllegalArgumentException, Exception{
		
		List<String> labels = new ArrayList<String>(); // 用来存取所有的label
		List<Map<String, String>> mList = new ArrayList<Map<String, String>>(); // 用来存取所有的map
		String lab = ParamUtil.getChartLabels(); // 获取label对应字段
		List<String> label = new ArrayList<String>();//用来存取所有对象
		List<Datasets> sets = new ArrayList<Datasets>();
		
		for (int i=0;i<objList.size();i++) {
			if(i==0){
				//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
				Field[] fields = ClassUtil.getAccessibleFields(objList.get(i));
				for (Field o : fields) {
					Map<String, String> map = new HashMap<String, String>();
					o.setAccessible(true);
					Object value = o.get(objList.get(i));
					if(o.getName().contains(lab)){
						if (value.getClass().isArray()) {
							Object[] arr = (Object[]) value; 
	//						System.out.println("，变量值等于：" + Arrays.toString(arr));
							for (Object a : arr) {
							//System.out.println(a.getClass().getName());
							}
							} else {
								labels.add(value.toString());
							}
					}else{
						label.add(o.getName());
						map.put(o.getName(), value.toString());
						mList.add(map);
						
					
					}
				o.setAccessible(false);
				}
			}else{
				//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
				Field[] fields = ClassUtil.getAccessibleFields(objList.get(i));
				for (Field o : fields) {
					Map<String, String> map = new HashMap<String, String>();
					o.setAccessible(true);
					Object value = o.get(objList.get(i));
					if(o.getName().contains(lab)){
						if (value.getClass().isArray()) {
							Object[] arr = (Object[]) value; 
	//						System.out.println("，变量值等于：" + Arrays.toString(arr));
							for (Object a : arr) {
							//System.out.println(a.getClass().getName());
							}
							} else {
								labels.add(value.toString());
							}
					}else{
						map.put(o.getName(), value.toString());
						mList.add(map);
						
					
					}
				o.setAccessible(false);
			    }
			}
		}
		for (String str : label) {
			List<Integer> d = new ArrayList<Integer>();
			for (Map ma : mList) {
				if(ma.containsKey(str)){
					System.out.println(ma.values().toString());
					d.add(Integer.parseInt(ma.get(str).toString()));
				}
			}
			Datasets datasets = new Datasets();
			datasets.setData(d);
			datasets.setFillColor(getRandColorCode());
			datasets.setPointColor(getRandColorCode());
			datasets.setPointHighlightFill(getRandColorCode());
			datasets.setPointHighlightStroke(getRandColorCode());
			datasets.setPointStrokeColor(getRandColorCode());
			datasets.setStrokeColor(getRandColorCode());
			sets.add(datasets);
		}
		ChartBar bar = new ChartBar();
		bar.setDatasets(sets);
		bar.setLabels(labels);
		return JSONObject.fromObject(bar);
		
	};
		/**
		 * 获取随机颜色
		 * @return
		 */
	private static String getRandColorCode(){
		String r,g,b;
		SecureRandom random = new SecureRandom();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();
		r = r.length()==1 ? "0" + r : r ;
		g = g.length()==1 ? "0" + g : g ;
		b = b.length()==1 ? "0" + b : b ;
		return "#"+r+g+b;
	}

	/**
	 * 	对象转Json字符串
	 *  @author chenyl
	 *  @date 2019年2月26日 下午9:08:49
	 *  @param obj 要序列化的对象
	 *  @return Json字符串
	 */
	public static <T> String obj2String(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.warn("'{}' 转换 Json 异常,  errorMSg :{}", obj.getClass().getName(), e);
			return null;
		}
	}

	/**
	 * 	对象转Json字符串，忽略掉null
	 *  @author chenyl
	 *  @date 2019年2月26日 下午9:08:49
	 *  @param obj 要序列化的对象
	 *  @return Json字符串
	 */
	public static <T> String obj2StringIgnoreNull(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapperIgnoreNull.writeValueAsString(obj);
		} catch (Exception e) {
			log.warn("'{}' 转换 Json 异常,  errorMSg :{}", obj.getClass().getName(), e);
			return null;
		}
	}
	
	/**
	 * 	对象转Json字符串 格式化
	 *  @author chenyl
	 *  @date 2019年2月26日 下午9:09:22
	 *  @param obj 要序列化的对象
	 *  @return Json字符串
	 */
	public static <T> String obj2StringPretty(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			log.warn("对象" + obj.getClass().getName() + "转Json异常, error:{}", e);
			return null;
		}
	}

	/**
	 * 	Json字符串转对象
	 *  @author chenyl
	 *  @date 2019年2月26日 下午9:09:43
	 *  @param str Json字符串
	 *  @param clazz 要转的对象类型
	 *  @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T string2Obj(String str, Class<T> clazz) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(str) || clazz == null) {
			return null;
		}
		try {
			return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			log.warn("Json转对象异常, error:{}", e);
			return null;
		}
	}

	/**
	 * 	Json字符串转对象
	 *  @author chenyl
	 *  @date 2019年2月26日 下午9:12:22
	 *  @param str Json字符串
	 *  @param typeReference 泛型填要转的对象类型
	 *  @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
		if (StringUtils.isEmpty(str) || typeReference == null) {
			return null;
		}
		try {
			return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
		} catch (Exception e) {
			log.warn("Json转对象异常, String:{}, TypeReference<T>:{}, error:{}", str, typeReference.getType(), e);
			return null;
		}
	}

	/**
	 * 	Json字符串转对象
	 *  @author chenyl
	 *  @date 2019年2月26日 下午9:10:50
	 *  @param str Json字符串
	 *  @param collectionClass 集合类型 List,Set等等
	 *  @param elementClasses 集合的泛型类型
	 *  @return
	 */
	public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
		JavaType javaTp = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		try {
			return objectMapper.readValue(str, javaTp);
		} catch (Exception e) {
			log.warn("Json转对象异常, error:{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 转换对象
	 *
	 * @param obj
	 * @param typeReference
	 * @param <T>
	 * @return
	 */
	public static <T> T convertValue(Object obj, TypeReference<T> typeReference) {
		return objectMapper.convertValue(obj, typeReference);
	}

	/**
	 * json转对象
	 *
	 * @param str
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T uppercaseString2Obj(String str, Class<T> clazz) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(str) || clazz == null) {
			return null;
		}
		try {
			return clazz.equals(String.class) ? (T) str : upperCaseObjectMapper.readValue(str, clazz);
		} catch (Exception e) {
			log.warn("Json转对象异常, error:{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 对象转字符串，key为大写加下划线
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> String uppercaseObj2String(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : upperCaseObjectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.warn("'{}' 转换 Json 异常,  errorMSg :{}", obj.getClass().getName(), e);
			return null;
		}
	}

	/**
	 * 对象转Map，key为大写加下划线
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> uppercaseObj2Map(T obj) {
		return string2Obj(uppercaseObj2String(obj), new TypeReference<Map<String, Object>>() {
		});
	}

	/**
	 * 驼峰转换为大写加下划线
	 * @param propertyName
	 * @return
	 */
	public static String getUpperFieldName(String propertyName) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < propertyName.length(); i++) {
			if (propertyName.charAt(i) >= 'A' && propertyName.charAt(i) <= 'Z') {
				ret.append("_").append(
						(char) (propertyName.charAt(i) - 'A' + 'a'));
			} else {
				ret.append(propertyName.charAt(i));
			}
		}
		return ret.toString().toUpperCase();
	}

	/**
	 * jackson配置，key转换为大写加下划线
	 */
	public static class UpperCaseWithUnderscoresStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
		@Override
		public String translate(String input) {
			return getUpperFieldName(input);
		}
	}

	/**
	 * 对象转为Map
	 * @param bean
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				if(beanMap.get(key) != null)
					map.put(key + "", beanMap.get(key));
			}
		}
		return map;
	}
}
