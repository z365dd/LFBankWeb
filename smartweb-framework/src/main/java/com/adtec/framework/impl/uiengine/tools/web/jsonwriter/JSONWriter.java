
package com.adtec.framework.impl.uiengine.tools.web.jsonwriter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;

/**
 * 功能说明: JSON 转换器，可以将任意对象转换成标准JSON字符串<BR>
 * 系统版本: v1.0 <BR>
 * 开发人员: chenyl<BR>
 * 开发时间: May 20, 2016<BR>
 * 审核人员:<BR>
 * 相关文档:<BR>
 * 修改记录: <BR>
 * 修改日期 修改人员 修改说明<BR>
 * ======== ====== ============================================<BR>
 * <BR>
 */
@SuppressWarnings("unchecked")
public class JSONWriter {
	private static final Logger log = LoggerFactory.getLogger(JSONWriter.class);

	final static String RFC3339_FORMAT = "yyyy-MM-dd' 'HH:mm:ss"; // RFC3339日期格式
	/**
	 * 缺省枚举类型会转换成name-value
	 */
	public static final boolean ENUM_AS_BEAN_DEFAULT = false;

	static char[] hex = "0123456789ABCDEF".toCharArray();
	private StringBuilder buf = new StringBuilder(); // 存放json数据的数据体
	private Stack stack = new Stack(); // 存入需要转换为json数据的对象
	private boolean ignoreHierarchy = true; // 设置是否启用忽略属性功能，忽略一个class中多余的属性
	private Object root; // 需要转换为json数据的对象
	private boolean buildExpr = false; // 是否有指定排除，或者包含的属性
	private String exprStack = "";
	private Collection<Pattern> excludeProperties; // json输出中需要排除的属性
	private Collection<Pattern> includeProperties; // json输出中需要包括的属性
	private DateFormat formatter; // 日期格式化
	private boolean enumAsBean = ENUM_AS_BEAN_DEFAULT;
	private boolean excludeNullProperties; // 是否不输出值为null的属性，如果某个属性的值为null，则不输出
	private Map mapping = null; // 重命名，把输出的属性名字，替换为另外一种名字输出
	private int maxClobLength = 0; // Clob的长度限制
	private int maxBlobLength = 0; // Blob的长度限制
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
	 * @param 需要序列化的对象
	 * @return JSON 序列化的JSON字符串
	 * @throws BaseException
	 */
	public String write(Object object) throws BaseException {
		return this.write(object, null, null, false, null);
	}

	/**
	 * 转换对象并且带过滤/包括属性 在过滤属性中的key对应的属性将不输出 如果设置了包括，则仅在包括列表中的属性输出
	 * 
	 * @param object
	 *            需要序列化的对象
	 * @return JSON 序列化的JSON
	 * @throws BaseException
	 */
	public String write(Object object, Collection<Pattern> excludeProperties, Collection<Pattern> includeProperties,
			boolean excludeNullProperties, Map mapping) throws BaseException {
		this.excludeNullProperties = excludeNullProperties;
		this.buf.setLength(0);
		this.root = object;
		this.exprStack = "";
		this.buildExpr = ((excludeProperties != null) && !excludeProperties.isEmpty())
				|| ((includeProperties != null) && !includeProperties.isEmpty());
		this.excludeProperties = excludeProperties;
		this.includeProperties = includeProperties;
		this.mapping = mapping;
		this.value(object, null);

		return this.buf.toString();
	}

	/**
	 * 检查循环引用
	 * 
	 */
	private void value(Object object, Method method) throws BaseException {
		if (object == null) {
			this.add("null");

			return;
		}

		if (this.stack.contains(object)) {
			Class clazz = object.getClass();

			// cyclic reference
			if (clazz.isPrimitive() || clazz.equals(String.class)) {
				this.process(object, method);
			} else {

				this.add("null");
			}

			return;
		}

		this.process(object, method);
	}

	/**
	 * 序列化对象到JSON
	 * 
	 */
	private void process(Object object, Method method) throws BaseException {
		this.stack.push(object);
		if (object instanceof IDataset) {
			this.dataSet((IDataset) object, method, false);
		} else if (object instanceof Class) {
			this.string(object);
		} else if (object instanceof Boolean) {
			this.bool(((Boolean) object).booleanValue());
		} else if (object instanceof Number) {
			this.add(object);
		} else if (object instanceof String) {
			this.string(object);
		} else if (object instanceof Character) {
			this.string(object);
		} else if (object instanceof Map) {
			this.map((Map) object, method);
		} else if (object.getClass().isArray()) {
			this.array(object, method);
		} else if (object instanceof Iterable) {
			this.array(((Iterable) object).iterator(), method);
		} else if (object instanceof Date) {
			this.date((Date) object, method);
		} else if (object instanceof Calendar) {
			this.date(((Calendar) object).getTime(), method);
		} else if (object instanceof Locale) {
			this.string(object);
		} else if (object instanceof Enum) {
			this.enumeration((Enum) object);
		} else if (object instanceof java.sql.Clob) {
			String clobStr = null;
			java.sql.Clob clob = (java.sql.Clob) object;
			try {
				if (maxClobLength == 0 || maxClobLength >= clob.length()) {
					clobStr = clob.getSubString(1, (int) clob.length());
				} else {
					clobStr = clob.getSubString(1, maxClobLength) + "...";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				clobStr = "";
			}
			this.string(clobStr);
		} else if (object instanceof java.sql.Blob) {
			String blobStr = null;
			java.sql.Blob blob = (java.sql.Blob) object;
			try {
				if (maxBlobLength == 0 || maxBlobLength >= blob.length()) {
					blobStr = new String(blob.getBytes(1, (int) blob.length()));
				} else {
					blobStr = new String(blob.getBytes(1, maxBlobLength));
					// 在构造函数里加会改变字符
					blobStr += "...";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				blobStr = "";
			}
			this.string(blobStr);
		} else {
			this.bean(object);
		}

		this.stack.pop();
	}

	/**
	 * 获取bean的属性
	 */
	private void bean(Object object) throws BaseException {
		this.add("{");
		BeanInfo info;

		try {
			Class clazz = object.getClass();

			info = ((object == this.root) && this.ignoreHierarchy)
					? Introspector.getBeanInfo(clazz, clazz.getSuperclass()) : Introspector.getBeanInfo(clazz);

			PropertyDescriptor[] props = info.getPropertyDescriptors();

			boolean hasData = false;
			for (int i = 0; i < props.length; ++i) {
				PropertyDescriptor prop = props[i];
				String name = prop.getName();
				Method accessor = prop.getReadMethod();
				Method baseAccessor = null;
				if (clazz.getName().indexOf("$$EnhancerByCGLIB$$") > -1) {
					try {
						baseAccessor = Class.forName(clazz.getName().substring(0, clazz.getName().indexOf("$$")))
								.getMethod(accessor.getName(), accessor.getParameterTypes());
					} catch (Exception ex) {
						log.debug(ex.getMessage(), ex);
					}
				} else
					baseAccessor = accessor;

				if (baseAccessor != null) {

					// ignore "class" and others
					if (this.shouldExcludeProperty(clazz, prop)) {
						continue;
					}
					String expr = null;
					if (this.buildExpr) {
						expr = this.expandExpr(name);
						if (this.shouldExcludeProperty(expr)) {
							continue;
						}
						expr = this.setExprStack(expr);
					}

					Object value = accessor.invoke(object, new Object[0]);
					boolean propertyPrinted = this.add(name, value, accessor, hasData);
					hasData = hasData || propertyPrinted;
					if (this.buildExpr) {
						this.setExprStack(expr);
					}
				}
			}

			// special-case handling for an Enumeration - include the name() as
			// a property */
			if (object instanceof Enum) {
				Object value = ((Enum) object).name();
				this.add("_name", value, object.getClass().getMethod("name"), hasData);
			}
		} catch (Exception e) {
			throw new BaseException("219", e.getMessage(), e);
		}

		this.add("}");
	}

	/**
	 * 获取枚举的值
	 */
	private void enumeration(Enum enumeration) throws BaseException {
		if (enumAsBean) {
			this.bean(enumeration);
		} else {
			this.string(enumeration.name());
		}
	}

	/**
	 * 忽略 class 属性
	 */
	private boolean shouldExcludeProperty(Class clazz, PropertyDescriptor prop)
			throws SecurityException, NoSuchFieldException {
		String name = prop.getName();

		if (name.equals("class") || name.equals("declaringClass")) {
			return true;
		}

		return false;
	}

	private String expandExpr(int i) {
		return this.exprStack + "[" + i + "]";
	}

	private String expandExpr(String property) {
		if (this.exprStack.length() == 0)
			return property;
		return this.exprStack + "." + property;
	}

	private String setExprStack(String expr) {
		String s = this.exprStack;
		this.exprStack = expr;
		return s;
	}

	/**
	 * 某个属性值，是否需要排除,返回false表示不排除该属性，true表示排除该属性
	 * 
	 * @param expr
	 * @return
	 */
	private boolean shouldExcludeProperty(String expr) {
		if (this.excludeProperties != null) {
			for (Pattern pattern : this.excludeProperties) {
				if (pattern.matcher(expr).matches()) {
					if (log.isDebugEnabled())
						log.debug("Ignoring property because of exclude rule: " + expr);
					return true;
				}
			}
		}

		if (this.includeProperties != null) {
			for (Pattern pattern : this.includeProperties) {
				if (pattern.matcher(expr).matches()) {
					return false;
				}
			}

			if (log.isDebugEnabled())
				log.debug("Ignoring property because of include rule:  " + expr);
			return true;
		}

		return false;
	}

	/**
	 * 增加一个name/value 对
	 * 
	 */
	private boolean add(String name, Object value, Method method, boolean hasData) throws BaseException {
		if(initD && !DataUtil.isNullStr(name)){
			name = name.substring(0, 1).toUpperCase()+name.substring(1);
		}
		if (!excludeNullProperties || value != null) {
			if (hasData) {
				this.add(',');
			}
			this.add('"');
			this.add(mappingName(name));
			this.add("\":");
			this.value(value, method);
			return true;
		}

		return false;
	}

	/**
	 * 把json输出的属性重命名，
	 * 
	 * @param name
	 *            需要被重命名的属性
	 * @return 重命名后的属性
	 */
	private String mappingName(String name) {
		if (this.mapping == null)
			return name;
		String mappedName = (String) this.mapping.get(name);
		if (mappedName == null) {
			return name;
		} else {
			return mappedName;
		}
	}

	/**
	 * 增加Map，把map格式的数据，转换成json格式
	 * 
	 * @param map
	 * @param method
	 * @throws BaseException
	 */
	private void map(Map map, Method method) throws BaseException {
		this.add("{");

		Iterator it = map.entrySet().iterator();

		boolean hasData = false;
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			String expr = null;
			if (this.buildExpr) {
				if (key == null) {
					log.error("Cannot build expression for null key in " + this.exprStack);
					continue;
				} else {
					expr = this.expandExpr(key.toString());
					if (this.shouldExcludeProperty(expr)) {
						continue;
					}
					expr = this.setExprStack(expr);
				}
			}
			if (hasData) {
				this.add(',');
			}
			hasData = true;
			if (key instanceof String) {
				this.value(mappingName((String) key), method);
			} else {
				this.value(key, method);
			}
			this.add(":");
			this.value(entry.getValue(), method);
			if (this.buildExpr) {
				this.setExprStack(expr);
			}
		}

		this.add("}");
	}

	/**
	 * 增加dataset,把dataset格式的数据转换为json
	 * 
	 * @param dataSet
	 * @param method
	 * @throws BaseException
	 */
	private void dataSet(IDataset ds, Method method, boolean isMap) throws BaseException {
		if (!isMap && ds.getRowCount() >= 1) {
			this.add("[");
			boolean hasData = false;
			int columnCount = ds.getColumnCount();
			// 遍历dataSet
			ds.beforeFirst();
			while (ds.hasNext()) {
				ds.next();
				if (hasData) {
					this.add(',');
				}
				hasData = true;
				this.add("{");
				for (int j = 1; j <= columnCount; j++) {
					if (j > 1) {
						this.add(",");
					}
					try {
						String columnName = ds.getColumnName(j);
						char columnType = ds.getColumnType(j);
						Object value = null;
						switch (columnType) {
						case DatasetColumnType.DS_INT:
							value = ds.getLong(columnName);
							break;
						case DatasetColumnType.DS_DOUBLE:
							value = ds.getDouble(columnName);
							break;
						case DatasetColumnType.DS_LONG:
							value = ds.getLong(columnName);
							break;
						case DatasetColumnType.DS_STRING:
							value = ds.getString(columnName);
							break;
						case DatasetColumnType.DS_STRING_ARRAY:
							value = ds.getStringArray(columnName);
							break;
						case DatasetColumnType.DS_BYTE_ARRAY:
							value = ds.getByteArray(columnName);
							break;
						default:
							value = ds.getString(columnName);
							break;
						}
						this.value(columnName, method);
						this.add(":");
						this.value(value, method);

					} catch (Exception e) {
						log.error(SysErr.E_JSON_RESULT, e);
						throw new BaseException(SysErr.E_JSON_RESULT, e);
					}
				}

				this.add("}");

			}
			this.add("]");
		} else if (isMap) {
			oneRowDataset2Map(ds, method);
		} else {
			this.add("null");
		}
	}

	/**
	 * 将一行dataset转换为mapJSON
	 * 
	 * @param ds
	 */
	public void oneRowDataset2Map(IDataset ds, Method method) {
		int columnCount = ds.getColumnCount();
		// 遍历dataSet
		ds.beforeFirst();
		// Map map = new HashMap();
		while (ds.hasNext()) {
			ds.next();
			this.add("{");
			// 将一行的数据放置于map中
			for (int j = 1; j <= columnCount; j++) {
				if (j > 1) {
					this.add(",");
				}
				try {
					String columnName = ds.getColumnName(j);
					char columnType = ds.getColumnType(j);
					Object value = null;
					switch (columnType) {
					case DatasetColumnType.DS_INT:
						value = ds.getLong(columnName);
						break;
					case DatasetColumnType.DS_DOUBLE:
						value = ds.getDouble(columnName);
						break;
					case DatasetColumnType.DS_LONG:
						value = ds.getLong(columnName);
						break;
					case DatasetColumnType.DS_STRING:
						value = ds.getString(columnName);
						break;
					case DatasetColumnType.DS_STRING_ARRAY:
						value = ds.getStringArray(columnName);
						break;
					case DatasetColumnType.DS_BYTE_ARRAY:
						value = ds.getByteArray(columnName);
						break;
					default:
						value = ds.getString(columnName);
						break;
					}
					this.value(columnName, method);
					this.add(":");
					this.value(value, method);

					// map.put(columnName, value);
				} catch (Exception e) {
					log.error("222", e, e.getMessage());
				}
			}
			this.add("}");
			break;
		}
		// toMapjson
		// try {
		// if(map.size() > 0){
		// this.map(map, method);
		// }else{
		// this.add("null");
		// }
		//
		// } catch (BaseException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 增加日期。格式化日期为"yyyy-MM-dd' 'HH:mm:ss"格式，并转换成json格式
	 * 
	 * @param date
	 * @param method
	 */
	private void date(Date date, Method method) {
		if (this.formatter == null)
			this.formatter = new SimpleDateFormat(RFC3339_FORMAT);

		this.string(formatter.format(date));
	}

	/**
	 * 增加数组。把数组转换成为json格式的数据
	 * 
	 */
	private void array(Iterator it, Method method) throws BaseException {
		this.add("[");

		boolean hasData = false;
		for (int i = 0; it.hasNext(); i++) {
			String expr = null;
			if (this.buildExpr) {
				expr = this.expandExpr(i);
				if (this.shouldExcludeProperty(expr)) {
					it.next();
					continue;
				}
				expr = this.setExprStack(expr);
			}
			if (hasData) {
				this.add(',');
			}
			hasData = true;
			this.value(it.next(), method);
			if (this.buildExpr) {
				this.setExprStack(expr);
			}
		}

		this.add("]");
	}

	/**
	 * 增加数组
	 * 
	 * @throws IllegalArgumentException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private void array(Object object, Method method)
			throws BaseException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
		this.add("[");

		int length = Array.getLength(object);

		boolean hasData = false;
		for (int i = 0; i < length; ++i) {
			String expr = null;
			if (this.buildExpr) {
				expr = this.expandExpr(i);
				if (this.shouldExcludeProperty(expr)) {
					continue;
				}
				expr = this.setExprStack(expr);
			}
			if (hasData) {
				this.add(',');
			}
			hasData = true;
			this.value(Array.get(object, i), method);
			if (this.buildExpr) {
				this.setExprStack(expr);
			}
		}

		this.add("]");
	}

	/**
	 * 增加布尔变量。把boolean变量的数据转换成json格式
	 */
	private void bool(boolean b) {
		this.add(b ? "true" : "false");
	}

	/**
	 * 把字符串转化为json格式，并替换特殊字符
	 */
	private void string(Object obj) {
		this.add('"');

		// CharacterIterator it = new StringCharacterIterator((obj.toString())
		// .trim()); 2010-11-02 修改
		CharacterIterator it = new StringCharacterIterator(obj.toString());

		for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
			if (c == '"') {
				this.add("\\\"");
			} else if (c == '\\') {
				this.add("\\\\");
			} else if (c == '/') {
				this.add("\\/");
			} else if (c == '\b') {
				this.add("\\b");
			} else if (c == '\f') {
				this.add("\\f");
			} else if (c == '\n') {
				this.add("\\n");
			} else if (c == '\r') {
				this.add("\\r");
			} else if (c == '\t') {
				this.add("\\t");
			} else if (Character.isISOControl(c)) {
				this.unicode(c);
			} else {
				this.add(c);
			}
		}

		this.add('"');
	}

	/**
	 * 增加一个普通对象,比如基本数据类型的包装类对象
	 */
	private void add(Object obj) {
		this.buf.append(obj);
	}

	/**
	 * 增加一个字符
	 */
	private void add(char c) {
		this.buf.append(c);
	}

	/**
	 * UNICODE 一个字符
	 * 
	 * @param c
	 *            被Encode的字符
	 */
	private void unicode(char c) {
		this.add("\\u");

		int n = c;

		for (int i = 0; i < 4; ++i) {
			int digit = (n & 0xf000) >> 12;

			this.add(hex[digit]);
			n <<= 4;
		}
	}

	/**
	 * 设置是否启用忽略属性功能，忽略一个class中多余的属性
	 * 
	 * @param ignoreHierarchy
	 */
	public void setIgnoreHierarchy(boolean ignoreHierarchy) {
		this.ignoreHierarchy = ignoreHierarchy;
	}

	/**
	 * 设置是否把枚举当作Bean序列化
	 * 
	 * @param enumAsBean
	 *            true 序列话枚举为bean方式
	 */
	public void setEnumAsBean(boolean enumAsBean) {
		this.enumAsBean = enumAsBean;
	}

	/**
	 * 转换对象,可以重命名属性
	 * 
	 * @param obj
	 * @param mapping
	 * @return
	 * @throws BaseException
	 */
	public String write(Object object, Map mapping) throws BaseException {
		return this.write(object, null, null, false, mapping);
	}

	public String write(IDataset ds, boolean isMap, Collection<Pattern> excludeProperties,
			Collection<Pattern> includeProperties, boolean excludeNullProperties) throws BaseException {
		this.excludeNullProperties = excludeNullProperties;
		this.buf.setLength(0);
		this.root = ds;
		this.exprStack = "";
		this.buildExpr = ((excludeProperties != null) && !excludeProperties.isEmpty())
				|| ((includeProperties != null) && !includeProperties.isEmpty());
		this.excludeProperties = excludeProperties;
		this.includeProperties = includeProperties;
		this.value(ds, isMap, null);
		return this.buf.toString();
	}

	public void value(IDataset ds, boolean isMap, Method method) throws BaseException {
		if (ds == null) {
			this.add("null");
			return;
		}

		if (this.stack.contains(ds)) {
			Class clazz = ds.getClass();

			// cyclic reference
			if (clazz.isPrimitive() || clazz.equals(String.class)) {
				this.process(ds, isMap, method);
			} else {
				// if (log.isDebugEnabled()) {
				// log.debug("Cyclic reference detected on " + ds);
				// }

				this.add("null");
			}

			return;
		}

		this.process(ds, isMap, method);
	}

	public void process(IDataset ds, boolean isMap, Method method) throws BaseException {
		this.stack.push(ds);
		this.dataSet(ds, method, isMap);
		this.stack.pop();
	}
}
