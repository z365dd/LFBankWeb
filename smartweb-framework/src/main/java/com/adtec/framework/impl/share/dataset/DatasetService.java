/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: DatasetService.java
 * 软件版权:
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */

package com.adtec.framework.impl.share.dataset;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.common.util.objectutil.PropertyUtils;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.caseStrategy.BaseCaseStrategy;
import com.adtec.framework.impl.share.caseStrategy.CamelCaseStrategy;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.impl.share.caseStrategy.NormalCaseStrategy;
import com.adtec.framework.impl.share.dataset.convertor.Convertor;
import com.adtec.framework.impl.share.dataset.convertor.ConvertorRegistry;
import com.adtec.framework.impl.share.event.field.FieldCreator;
import com.adtec.framework.impl.share.event.field.FieldValue;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasetAttribute;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.framework.interfaces.share.writer.IMapWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

/**
 * 关于Dataset的一个工具类，根据需要生成所需要的Dataset <br>
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl<br>
 * 开发时间: <br>
 * <br>
 */
@SuppressWarnings("unchecked")
public class DatasetService {
	/** 全局默认的 <code>DatasetService</code> 实例 */
	protected static DatasetService staticInstance;
	/** 全局默认的 <code>IDatasetAttribute</code> 实例 */
	protected static IDatasetAttribute staticdssa;
	/** 数据集属性 */
	protected IDatasetAttribute dssa;
	/** 域值构造器 */
	protected FieldCreator fc;
	/** 20110609 chenyl 值转换器容器 */
	protected ConvertorRegistry convertorRegistry;

	static {
		staticdssa = new DatasetAttribute();
		staticInstance = new DatasetService(staticdssa);
	}

	/**
	 * 采用指定的数据集属性来构造一个 <code>DatasetService</code>，如果传入的属性为null，则生成默认的数据集属性
	 * 
	 * @param dssa
	 *            数据集属性
	 */
	protected DatasetService(IDatasetAttribute dssa) {
		if (dssa == null) {
			this.dssa = new DatasetAttribute();
		} else {
			this.dssa = dssa;
		}
		fc = FieldCreator.getNewInstance(this.dssa);

		// {  增加值转换器
		convertorRegistry = new ConvertorRegistry();
		// }
	}

	/**
	 * 获取系统默认的 <code>DatasetService</code> 实例
	 * 
	 * @return <code>DatasetService</code> 实例
	 */
	public static DatasetService getDefaultInstance() {
		return staticInstance;
	}

	/**
	 * 创建一个新的实例，内部的状态信息，比如类型的默认值等，由调用者自己维护
	 * 
	 * @return 新的数据集服务实例
	 */
	public static DatasetService getInstace() {
		return new DatasetService(new DatasetAttribute());
	}

	/**
	 * 根据数据集属性创建一个新的 <code>DatasetService</code> 实例
	 * 
	 * @return 新的 <code>DatasetService</code> 实例
	 */
	public static DatasetService getInstace(IDatasetAttribute attr) {
		return new DatasetService(attr);
	}

	/**
	 * 获取默认的数据集属性，整个平台共享
	 * 
	 * @return 默认的数据集属性
	 */
	public static IDatasetAttribute getDefaultDatasetAttribute() {
		return staticdssa;
	}

	/**
	 * 根据Dataset获取与之相关联的 数据集属性
	 * 
	 * @param dataset
	 *            数据集
	 * @return 与该数据集相关的属性
	 */
	public static IDatasetAttribute getDatasetAttribute(IDataset dataset) {
		if (dataset == null) {
			return null;
		} else {
			CommonDataset ds = (CommonDataset) dataset;
			return ds.getDatasetAttribute();
		}
	}

	/**
	 * 设置数据集的数据集属性
	 * 
	 * @param dataset
	 *            数据集
	 * @param dssa
	 *            数据集属性
	 */
	public static void setDatasetAttribute(IDataset dataset,
			IDatasetAttribute dssa) {
		if (dataset != null && dssa != null) {
			CommonDataset ds = (CommonDataset) dataset;
			IDatasetAttribute attribute = ds.getDatasetAttribute();
			attribute.copyFrom(dssa);
		}
	}

	/**
	 * 获取默认的数据集，数据集属性采用默认的
	 * 
	 * @return CommonDataset 对象实例
	 */
	public IDataset getDataset() {
		return getDataset(staticdssa);
	}

	/**
	 * 获取通用的数据集
	 * 
	 * @return CommonDataset 对象实例
	 */
	public IDataset getDataset(IDatasetAttribute attr) {
		return new CommonDataset(attr);
	}

	/**
	 * 将<code>HttpServletRequest</code>对象转换为IDataset
	 * <p>
	 * 转换规则：只读取parameters中的所有数据（Map形式），并以key-value的方式存入IDataset中，每一列的列名为key，
	 * 列的值为value； 列类型为String。如果存在value为String[]，则将String[]展开会IDataset中的多行数据，
	 * 整个IDataset的行数即为value.length最大的值，展开的多行中，如果没有数据对应，相应的值会被自动填充为
	 * <code>""</code>。
	 * 
	 * @param request
	 *            <code>HttpServletRequest</code>实例
	 * @return 转换好的IDataset，如果http请求中没有数据，则返回行、列都为空的IDataset实例
	 */
	public IDataset getDataset(HttpServletRequest request) {
		return getDataset(request, staticdssa);
	}

	/**
	 * 根据HttpServletRequest生成一个Dataset
	 * 
	 * @param request
	 *            http请求
	 * @return 数据集，此数据集包括请求参数和session里面的所有内容
	 */
	public IDataset getDataset(HttpServletRequest request,
			IDatasetAttribute attr) {
		IDataset ds = getDataset(attr);

		int maxStringArray = 0;
		Map<String, String> strings = new HashMap<String, String>();
		Map<String, String[]> stringArrays = new HashMap<String, String[]>();
		// 遍历Request
		Map<String, String[]> map = request.getParameterMap();
		// 20180423 add by chenyl for 是为json字符串送过来的,则通过getInputStream方法获取存放到mapJson中
		Map<String, Object> mapJson = Maps.newHashMap(); 
		boolean isJson = false;
		if(null!=request.getContentType() && request.getContentType().toLowerCase().indexOf("json")>-1){
			InputStream stream = null;
			BufferedReader reader = null;
	        try {
	        	String line = "";
		        StringBuilder body = new StringBuilder();
		        int counter = 0;
	        	stream = request.getInputStream();
				//读取POST提交的数据内容
		        reader = new BufferedReader(new InputStreamReader(stream));
				if (null == reader) {
					throw new BaseException(SysErr.E_IO_ERROR, "数据读取失败");
				}
		        while ((line = reader.readLine()) != null) {
		            if(counter > 0){
		                body.append("\r\n");
		            }
		            body.append(line);
		            counter++;
		        }
		        String jsonStr = body.toString();
		        if(DataUtil.isNullStr(jsonStr)){
		        	jsonStr = "{}";
		        }
		        // 转成json对象->转成Map<String, Object>
		        mapJson = JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {
                });
		        isJson = true;
			} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
				if(null!=reader){
					try {
						reader.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}
				if(null!=stream){
					try {
						stream.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}
			}
		}
		
		// 变量parameters的参数
		Iterator<Entry<String, String[]>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				if (value instanceof String[]) {
					String[] vs = (String[]) value;
					if (maxStringArray < vs.length) {
						maxStringArray = vs.length;
					}
					stringArrays.put(key, vs);
					ds.addColumn(key, DatasetColumnType.DS_STRING);
				}
			}
		}
		
		// 变量json的参数
		if (isJson) {
			Iterator<Entry<String, Object>> it2 = mapJson.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry<String, Object> entry = it2.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof List) {
						List vs = (List) value;
						if (maxStringArray < vs.size()) {
							maxStringArray = vs.size();
						}
						String[] vss = new String[vs.size()];
						for (int i = 0; i < vs.size(); i++) {
							vss[i] = "" + vs.get(i);
						}
						stringArrays.put(key, vss);
						ds.addColumn(key, DatasetColumnType.DS_STRING);
					} else {
						String v = "" + value;
						if (maxStringArray < 1) {
							maxStringArray = 1;
						}
						String[] vs = { v };
						stringArrays.put(key, vs);
						ds.addColumn(key, DatasetColumnType.DS_STRING);
					}
				}

			}
		}
				
				
		// 遍历session
		// HttpSession session = request.getSession();
		// for (Enumeration e = session.getAttributeNames();
		// e.hasMoreElements();) {
		// String name = (String) e.nextElement();
		// if (strings.get(name) == null && stringArrays.get(name) == null) {
		// strings.put(name, session.getAttribute(name).toString());
		// }
		// }

		int stringArraySize = stringArrays.size();
		int stringSize = strings.size();
		if (stringArraySize != 0) {
			for (int i = 0; i < maxStringArray; i++) {
				ds.appendRow();
			}
			Iterator<Entry<String, String[]>> iter = stringArrays.entrySet()
					.iterator();

			while (iter.hasNext()) {
				Entry<String, String[]> entry = iter.next();
				String key = entry.getKey();
				String[] value = entry.getValue();
				for (int i = 0; i < value.length; i++) {
					ds.locateLine(i + 1);
					ds.updateString(key, value[i]);
				}
			}
		}

		if (stringSize != 0) {
			if (ds.getRowCount() == 0) {
				ds.appendRow();
			}
			Iterator<Entry<String, String>> iter = strings.entrySet()
					.iterator();

			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				ds.addColumn(key, DatasetColumnType.DS_STRING);
				ds.locateLine(1);
				ds.updateString(key, value);
			}
		}

		if (ds.getRowCount() != 0) {
			ds.locateLine(1);
		}

		return ds;
	}

	/**
	 * 将<code>java.sql.ResultSet</code>对象转换为IDataset
	 * <p>
	 * ResultSet中的字段类型与IDataset中的字段类型映射关系如下： <br>
	 * java.sql.Types.BLOB --> DatasetColumnType.DS_BYTE_ARRAY <br>
	 * java.sql.Types.CLOB --> DatasetColumnType.DS_STRING <br>
	 * java.sql.Types.CHAR --> DatasetColumnType.DS_INT <br>
	 * java.sql.Types.DATE --> DatasetColumnType.DS_STRING_ARRAY <br>
	 * java.sql.Types.DECIMAL --> DatasetColumnType.DS_DOUBLE <br>
	 * java.sql.Types.DOUBLE --> DatasetColumnType.DS_DOUBLE <br>
	 * java.sql.Types.FLOAT --> DatasetColumnType.DS_DOUBLE <br>
	 * java.sql.Types.INTEGER --> DatasetColumnType.DS_LONG <br>
	 * java.sql.Types.NUMERIC --> DatasetColumnType.DS_DOUBLE <br>
	 * java.sql.Types.REAL --> DatasetColumnType.DS_DOUBLE <br>
	 * java.sql.Types.TINYINT --> DatasetColumnType.DS_INT <br>
	 * java.sql.Types.TIME --> DatasetColumnType.DS_STRING <br>
	 * java.sql.Types.VARCHAR --> DatasetColumnType.DS_STRING <br>
	 * others --> DatasetColumnType.DS_STRING
	 * <p>
	 * 其中比较特殊的是BigDecimal类型，如果精度小于等于0，则按照DS_LONG处理，否则按照DS_DOUBLE处理
	 * 
	 * @param rs
	 *            <code>java.sql.ResultSet</code>实例
	 * @return 转换好的IDataset，如果ResultSet中没有数据，则返回行、列都为空的IDataset实例
	 */
	public IDataset getDataset(ResultSet rs) {
		return getDataset(rs, staticdssa);
	}

	/**
	 * 将ResultSet转换为通用数据类型，推荐使用
	 * 
	 * @param rs
	 *            数据库的结果集
	 * @return 通用数据类型
	 */
	public IDataset getDataset(ResultSet rs, IDatasetAttribute attr) {
		IDataset cds = getDataset(attr);
		try {
			ResultSetMetaData rsmeta = rs.getMetaData();
			// 列信息
			int colCount = rsmeta.getColumnCount();
			for (int i = 1; i <= colCount; i++) {
				cds.addColumn(rsmeta.getColumnName(i), rsmeta.getColumnType(i));
			}
			// 行信息
			while (rs.next()) {
				cds.appendRow();
				for (int i = 1; i <= colCount; i++) {
					cds.updateValue(i, rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cds;
	}

	/**
	 * 将Resultset转换为IDataset，并且包括colNames中指定的所有字段
	 * 
	 * @param rs
	 *            要转换的Resultset
	 * @param colNames
	 *            IDataset中需要包含的字段
	 * @return 返回包括colNames所有字段的IDataset
	 */
	public IDataset getDatasetIncludeCols(ResultSet rs, String[] colNames) {
		return getDatasetIncludeCols(rs, colNames, staticdssa);
	}

	private IDataset getDatasetIncludeCols(ResultSet rs, String[] colNames,
			IDatasetAttribute attr) {
		IDataset cds = getDataset(attr);
		if (colNames == null || colNames.length == 0) {
			return cds;
		}
		try {
			int colNamesSize = colNames.length;
			ResultSetMetaData rsmeta = rs.getMetaData();
			Integer[] includeCols = new Integer[colNamesSize];
			for (int i = 0; i < colNamesSize; i++) {
				String colName = colNames[i];
				int index = 0;
				try {
					index = rs.findColumn(colName);
				} catch (SQLException e) {
					index = 0;
				}
				if (index > 0) {
					includeCols[i] = index; // 存在此列
					cds.addColumn(colName, rsmeta.getColumnType(index));
				} else {
					includeCols[i] = 0; // 不存在此列
					cds.addColumn(colName);
				}
			}
			// 行信息
			while (rs.next()) {
				cds.appendRow();
				for (int i = 0; i < colNamesSize; i++) {
					int index = includeCols[i];
					if (index > 0) {
						cds.updateValue(i + 1, rs.getObject(index));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cds;
	}

	public IDataset getErrorDataset(int returnCode, String errorNo,
			String errorInfo) {
		IDataset errorDataset = getDataset(staticdssa);
		errorDataset.setDatasetName("error");
		errorDataset.addColumn("returnCode", DatasetColumnType.DS_INT);
		errorDataset.addColumn("errorNo", DatasetColumnType.DS_LONG);
		errorDataset.addColumn("errorInfo", DatasetColumnType.DS_STRING);
		errorDataset.appendRow(); // 添加一行
		errorDataset.updateInt("returnCode", returnCode);
		errorDataset.updateString("errorNo", errorNo);
		errorDataset.updateString("errorInfo", errorInfo);
		return errorDataset;
	}

	/**
	 * 将<code>java.util.Map</code>转换为IDataset <br>
	 * 
	 * @param oneRow
	 *            <code>java.util.Map</code>实例
	 * @return 
	 *         如果Map为空，则返回一个空的IDataset实例；返回单行IDataset，列名有Map的key决定，列类型有value的类型决定
	 */
	public IDataset getDataset(Map oneRow) {
		return getDataset(oneRow, staticdssa);
	}

	/**
	 * 根据 Map 获取构造一个dataset
	 * 
	 * @param oneRow
	 *            键值对
	 * @return 与 oneRow 对应的dataset，如果转换失败则会返回null
	 */
	public IDataset getDataset(Map oneRow, IDatasetAttribute attr) {
		if (oneRow == null) {
			return getDataset(attr);
		} else {
			List<Map> l = new ArrayList<Map>();
			l.add(oneRow);
			return getDataset(l, attr);
		}
	}

	/**
	 * 将Map的集合转换为IDataset
	 * <p>
	 * 
	 * @param mutiRows
	 *            具有相同key值的Map实例集合
	 * @return 如果Collection为空，则返回空的IDataset实例；否则，返回的IDataset，列名有第一个Map的key决定，
	 *         列类型由第一个Map的value的类型决定
	 */
	public IDataset getDataset(Collection<Map> mutiRows) {
		return getDataset(mutiRows, staticdssa);
	}

	/**
	 * 根据一个 Collection 来生成一个Dataset，Dataset的列值以第一个Map为准
	 * 
	 * @param mutiRows
	 *            多行记录
	 * @return 返回一个多行的数据集；如果 mutiRows
	 *         为null则返回null，如果mutiRows的size为0，则返回一个默认的空Dataset
	 */
	public IDataset getDataset(Collection<Map> mutiRows, IDatasetAttribute attr) {
		if (mutiRows == null || mutiRows.size() == 0) {
			return getDataset();
		}
		Iterator<Map> it = mutiRows.iterator();

		IDataset ds = getDataset(attr);
		// 域
		Map<String, Object> oneRow = it.next();
		if (oneRow.size() == 0) {
			return getDataset(attr);
		}

		// 列信息
		Iterator<Entry<String, Object>> en = oneRow.entrySet().iterator();
		while (en.hasNext()) {
			Map.Entry<String, Object> e = en.next();
			String key = e.getKey();
			Object value = e.getValue();
			addColumnWithValue(ds, key, value);
		}

		// 行信息
		it = mutiRows.iterator();
		while (it.hasNext()) {
			ds.appendRow();
			oneRow = it.next();
			en = oneRow.entrySet().iterator();
			while (en.hasNext()) {
				Map.Entry<String, Object> e = en.next();
				String key = e.getKey();
				Object value = e.getValue();
				try {
					updateRowValue(ds, key, value);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return ds;
	}

	/**
	 * 将一个POJO转换为IDataset
	 * <p>
	 * 
	 * @param oneRow
	 *            一个Pojo实例，并且要求POJO中只能包括基本java类型，否则按照默认转换规则转换为String
	 * @param clz
	 *            Pojo实例的class
	 * @return 返回单行IDataset，列名由POJO的属性名决定，列类型由属性的类型而定
	 */
	public IDataset getDataset(Object oneRow, Class<?> clz) {
		return getDataset(oneRow, clz, staticdssa);
	}

	/**
	 * 根据一个bean来生成一个Dataset，Dataset只有一行
	 * 
	 * @param oneRow
	 *            一个任意对象
	 * @param clz
	 *            对象的class
	 * @return 返回一个只有一条记录的Dataset；如果 oneRow为null，则返回空的Dataset
	 */
	public IDataset getDataset(Object oneRow, Class<?> clz,
			IDatasetAttribute attr) {
		if (oneRow == null) {
			List<Object> l = null;
			return getDataset(l, clz, attr);
		} else {
			List<Object> l = new ArrayList<Object>();
			l.add(oneRow);
			return getDataset(l, clz);
		}
	}

	/**
	 * 将一个POJO集合转换为IDataset
	 * <p>
	 * 
	 * @param mutiRows
	 *            多个Pojo实例，并且要求POJO中只能包括基本java类型，否则按照默认转换规则转换为String
	 * @param clz
	 *            Pojo实例的class
	 * @return 返回IDataset实例，列名由第一个POJO的属性名决定，列类型由第一个POJO的属性的类型而定
	 */
	public IDataset getDataset(Collection<?> mutiRows, Class<?> clz) {
		return getDataset(mutiRows, clz, staticdssa);
	}

	/**
	 * 根据多个bean生成一个Dataset，Dataset可能有多行，由mutiRows的项数决定
	 * 
	 * @param mutiRows
	 *            多行记录
	 * @param clz
	 *            每个对象的class
	 * @return 返回一个多行的数据集；如果 mutiRows
	 *         为null则返回null，如果mutiRows的size为0，则返回一个默认的空Dataset
	 */
	public IDataset getDataset(Collection<?> mutiRows, Class<?> clz,
			IDatasetAttribute attr) {
		if (mutiRows == null || mutiRows.size() == 0) {
			IDataset ds = getDataset();
			if (clz != null) {
				List<String> attrs = PropertyUtils.getReadableProperties(clz);
				for (String prop : attrs) {
					if (StringUtil.equals("class", prop)) {
						continue;
					}
					ds.addColumn(prop, DatasetColumnType.DS_STRING);
				}
			}
			return ds;
		}

		IDataset ds = getDataset(attr);
		Object obj = mutiRows.iterator().next();
		if (obj == null) {
			return getDataset(attr);
		}
		List<String> attrs = PropertyUtils.getReadableProperties(obj);
		for (String prop : attrs) {
			// 20180508 add by chenyl for 新增对matchFields、ignoreFields的过滤
			if (StringUtil.equals("class", prop) || StringUtil.equals("matchFields", prop) || StringUtil.equals("ignoreFields", prop)) {
				continue;
			}
			Object value = PropertyUtils.read(obj, prop);
			addColumnWithValue(ds, prop, value);
		}

		for (Object o : mutiRows) {
			if (!clz.isInstance(o)) { // 不需要class完全一样，可以支持父类
				continue;
			}
			ds.appendRow();
			for (String prop : attrs) {
				// 20180508 add by chenyl for 新增对matchFields、ignoreFields的过滤
				if (StringUtil.equals("class", prop) || StringUtil.equals("matchFields", prop) || StringUtil.equals("ignoreFields", prop)) {
					continue;
				}
				Object value = PropertyUtils.read(o, prop);
				try {
					updateRowValue(ds, prop, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ds;
	}

	/**
	 * 为Dataset生成一个Map方式写入器
	 * 
	 * @param ds
	 *            数据集
	 * @return Map写入器
	 */
	public IMapWriter getMapWriter(IDataset ds) {
		if (ds == null) {
			throw new BaseException(SysErr.E_NULL_POINTER,
					"can not process dataset[null]");
		}
		return new MapWriter(ds, getDatasetAttribute(ds));
	}

	private void addColumnWithValue(IDataset ds, String key, Object object) {
		char type = DatasetColumnType.DS_STRING;
		if (object != null) {
			FieldValue fv = fc.getFieldValue(object);
			type = fv.getType();
		}
		ds.addColumn(key, type);
	}

	private void updateRowValue(IDataset ds, String key, Object object)
			throws BaseException {
		ds.updateValue(key, object);
	}

	public IDatasetAttribute getDatasetAttribute() {
		return dssa;
	}

	public IDatasetAttribute setDatasetAttribute(IDatasetAttribute dssa) {
		IDatasetAttribute pre = this.dssa;
		this.dssa.copyFrom(dssa);
		fc = FieldCreator.getNewInstance(this.dssa);
		return pre;
	}

	public static void printDataset(IDataset ds) {
		try {
			printDataset(ds, System.out);
		} catch (IOException e) {
                System.out.println("出现异常");
            }
	}

	// 添加输出datasets到String的方法
	public static String printDatasetsToString(IDatasets dss) {
		if (dss == null)
			return null;
		String result = "";
		int datasetCount = dss.getDatasetCount();
		for (int i = 0; i < datasetCount; i++) {
			result += "\n dataset(" + i + ")["
					+ printDatasetToString(dss.getDataset(i)) + "]";
		}
		return result;
	}

	//  添加输出dataset到String的方法
	public static String printDatasetToString(IDataset ds) {
		if (ds == null)
			return null;
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			printDataset(ds, outputStream);
		} catch (IOException e) {
                System.out.println("出现异常");
            }
		return outputStream.toString();
	}

	// 增加指定输出器的打印IDataset方法
	public static void printDataset(IDataset ds, OutputStream out)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("name[" + ds.getDatasetName() + "] ");
		sb.append("totalCount[" + ds.getTotalCount() + "]\n");
		int columnCount = ds.getColumnCount();
		for (int j = 1; j <= columnCount; j++) {
			sb.append("# " + j + "\t");
			sb.append(ds.getColumnName(j));
			sb.append("\t");
			sb.append(ds.getColumnType(j) + "\n");
		}

		ds.beforeFirst();
		while (ds.hasNext()) {
			ds.next();
			for (int j = 1; j <= columnCount; j++) {
				sb.append("|" + ds.getString(j) + "\t");
			}
			sb.append("\n");
		}
		// 使用指定输出器
		out.write(sb.toString().getBytes());
	}
	
	/**
	 * 将IDataset转换为指定的Class对象，Dataset中列字段名存在"_"时去掉"_"并返回驼峰型数据对象
	 * 
	 * @param <T>
	 *            泛型
	 * @param dataset
	 *            原始IDataset实例
	 * @param clz
	 *            Class，要求改Class有默认构造函数
	 * @return 如果转换失败则返回null
	 */
	public <T> T getObject(IDataset dataset, Class<T> clz) {
		T ret = null;
		dataset.beforeFirst();
		if (dataset.hasNext()) {
			ret = getObjectFromDataset(dataset, clz, new CamelCaseStrategy());
		}
		return ret;
	}

	/**
	 * 将IDataset转换为指定的Class对象，Dataset中列字段名称和对象属性名称一致(必须全小写)
	 * 
	 * @param <T>
	 *            泛型
	 * @param dataset
	 *            原始IDataset实例
	 * @param clz
	 *            Class，要求改Class有默认构造函数
	 * @return 如果转换失败则返回null
	 */
	public <T> T getHsStdObject(IDataset dataset, Class<T> clz) {
		T ret = null;
		dataset.beforeFirst();
		if (dataset.hasNext()) {
			ret = getObjectFromDataset(dataset, clz, new NormalCaseStrategy());
		}
		return ret;
	}
	
	/**
	 * 返回MBC数据类型，属性全大写
	 * @param dataset
	 * @param clz
	 * @return
	 */
	public <T> T getMBCObject(IDataset dataset, Class<T> clz) {
		T ret = null;
		dataset.beforeFirst();
		if (dataset.hasNext()) {
			ret = getObjectFromDataset(dataset, clz, new MBCCaseStrategy());
		}
		return ret;
	}

	private <T> T getObjectFromDataset(IDataset dataset, Class<T> clz,
			BaseCaseStrategy caseStrategy) {
		T obj = getObject(clz);
		if (obj != null) {
			dataset.next();
			for (int i = 1; i <= dataset.getColumnCount(); i++) {
				try {
					setProperty(obj, caseStrategy.getPropertyName(dataset
							.getColumnName(i)), dataset, i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	private <T> void setProperty(T obj, String fieldName, IDataset dataset,
			int index) throws Exception {
		//add by chenyl 20170423 for 新增dataset中的列名称与对对象的属性名一致时才进行赋值(忽略大小写)
		boolean isEqual = false;
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		for(Field f:ClassUtil.getAccessibleFields(obj)){
			if(f.getName().equalsIgnoreCase(fieldName)){
				// 20180508 mod by chenyl for 修改成对应数据对象中的属性名
				fieldName = f.getName();
				isEqual = true;
				break;
			}
		}
		if(!isEqual){
			return;
		}
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		Field field = ClassUtil.getAccessibleField(obj, fieldName);
		// 20180505 add by chenyl for 防止报空指针
		if(null==field){
			return;
		}
		Class<?> type = field.getType();
		if (type.equals(long.class) || type.equals(Long.class)) {
			ClassUtil.setFieldValue(obj, fieldName, dataset.getLong(index));
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			ClassUtil.setFieldValue(obj, fieldName, dataset.getInt(index));
		} else if (type.equals(String.class)) {
			String value = dataset.getString(index);
			if (value == null) {
				value = "";
			}
			ClassUtil.setFieldValue(obj, fieldName, value);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			ClassUtil.setFieldValue(obj, fieldName, dataset.getDouble(index));
		} else {
			ClassUtil.setFieldValue(obj, fieldName, dataset.getString(index));
		}
	}

	private <T> T getObject(Class<T> clz) {
		T obj = null;
		try {
			obj = clz.newInstance();
		} catch (Exception e) {
			System.out.println("操作失败");
		}
		return obj;
	}

	/**
	 * 将一个IDataset实例转换为指定的Class对象实例
	 * 
	 * @param <T>
	 *            泛型
	 * @param dataset
	 *            IDataset实例
	 * @param clz
	 *            指定的Class
	 * @return 将IDataset转换为JAVA对象，并做尽力的转换，如果转换失败，则返回null
	 */
	public <T> T transform(IDataset dataset, Class<T> clz) {
		if (dataset.getRowCount() <= 0) {
			return null;
		}
		try {
			// 解析对象类的所有可设置字段
			Map<String, Convertor<?>> convertors = new HashMap<String, Convertor<?>>();
			parseClass(clz, convertorRegistry, convertors);
			// 定位到第一行
			dataset.locateLine(1);
			return constructClass(dataset, clz, convertors);
		} catch (Exception e) {
			return null;
		}
	}

	public <T> T transform(IDataset dataset, Class<T> clz,
			ConvertorRegistry convertorRegistry) {
		if (dataset.getRowCount() <= 0) {
			return null;
		}
		try {
			convertorRegistry = new ConvertorRegistry(convertorRegistry);
			// 解析对象类的所有可设置字段
			Map<String, Convertor<?>> convertors = new HashMap<String, Convertor<?>>();
			parseClass(clz, convertorRegistry, convertors);
			// 定位到第一行
			dataset.locateLine(1);
			return constructClass(dataset, clz, convertors);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将IDataset转换为一个List<POJO>
	 * 
	 * @param <T>
	 *            泛型
	 * @param dataset
	 *            IDataset实例
	 * @param clz
	 *            Class对象
	 * @return 将IDataset转换为List<POJO>，并做尽力的转换，如果转换失败，则返回null
	 */
	public <T> List<T> transformToList(IDataset dataset, Class<T> clz) {
		List<T> list = new ArrayList<T>();
		if (dataset.getRowCount() <= 0) {
			return list;
		}

		try {
			// 解析对象类的所有可设置字段
			Map<String, Convertor<?>> convertors = new HashMap<String, Convertor<?>>();
			parseClass(clz, convertorRegistry, convertors);

			int datasetCount = dataset.getRowCount();
			for (int i = 1; i <= datasetCount; i++) {
				dataset.locateLine(i);
				T t = constructClass(dataset, clz, convertors);
				list.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public <T> List<T> transformToList(IDataset dataset, Class<T> clz,
			ConvertorRegistry convertorRegistry) {
		List<T> list = new ArrayList<T>();
		if (dataset.getRowCount() <= 0) {
			return list;
		}
		
		try {
			convertorRegistry = new ConvertorRegistry(convertorRegistry);
			// 解析对象类的所有可设置字段
			Map<String, Convertor<?>> convertors = new HashMap<String, Convertor<?>>();
			Map<String, Method> setMethods = new HashMap<String, Method>();
			parseClass(clz, convertorRegistry, convertors);

			int datasetCount = dataset.getRowCount();
			for (int i = 1; i <= datasetCount; i++) {
				dataset.locateLine(i);
				T t = constructClass(dataset, clz, convertors);
				list.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}


	private <T> T constructClass(IDataset dataset, Class<T> clz,
			Map<String, Convertor<?>> convertors) throws Exception {
		T t = clz.getConstructor(new Class[] {}).newInstance(new Object[] {});
		// 设置所有字段
		Iterator<Entry<String, Convertor<?>>> it = convertors.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, Convertor<?>> entiry = it.next();
			String fieldName = entiry.getKey();
			Convertor<?> convertor = entiry.getValue();
			/*ClassUtil.setFieldValue(t, fieldName, convertor.convert(dataset,
					fieldName));*/
			BeanUtils.setProperty(t, fieldName, convertor.convert(dataset,
					fieldName));
		}
		return t;
	}

	private <T> void parseClass(Class<T> clz,
			ConvertorRegistry convertorRegistry, Map<String, Convertor<?>> convertors) throws Exception{
		T t = clz.getConstructor(new Class[] {}).newInstance(new Object[] {});
		Map map = BeanUtils.describe(t);
		
		for(Iterator ite = map.entrySet().iterator(); ite.hasNext();){  
			  Map.Entry entry = (Map.Entry) ite.next();  
			  String fieldNameString = (String)entry.getKey();
			  Class<?> typeClass = PropertyUtils.getPropertyType(t, fieldNameString);
			  try {
					convertors.put(fieldNameString, convertorRegistry
							.getConvertor(typeClass));
				} catch (Exception e) {
					continue;
				}
			}  
	}

	/**
	 * 将一个Idataset转换Map
	 * 
	 * @param dataset
	 *            IDataset实例
	 * @return Map<String, Object>实例，Map的key值即为IDataset的列值
	 */
	public Map<String, Object> transformToMap(IDataset dataset) {
		if (dataset.getRowCount() <= 0) {
			return new HashMap<String, Object>();
		}
		dataset.locateLine(1);
		return parseDatasetToMap(dataset);
	}

	/**
	 * 将IDataset实例转换为List
	 * 
	 * @param dataset
	 *            IDataset实例
	 * @return List实例
	 */
	public List<Map<String, Object>> transformToListMap(IDataset dataset) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int rowCount = dataset.getRowCount();
		int colCount = dataset.getColumnCount();
		if (rowCount <= 0 || colCount <= 0) {
			return list;
		}
		for (int i = 1; i <= rowCount; i++) {
			dataset.locateLine(i);
			list.add(parseDatasetToMap(dataset));
		}
		return list;
	}

	private Map<String, Object> parseDatasetToMap(IDataset dataset) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int colCount = dataset.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			map.put(dataset.getColumnName(i), dataset.getValue(i));
		}
		return map;
	}

	/**
	 * 深度拷贝一份IDataset实例
	 * 
	 * @param other
	 *            原始IDataset实例
	 * @return 全新的IDataset
	 */
	public IDataset clone(IDataset other) {
		if (other == null) {
			return null;
		}
		CommonDataset dataset = (CommonDataset) other;
		return dataset.clone();
	}

	/**
	 * 在原IDataset中，保留names所列的所有列
	 * 
	 * @param ds
	 *            数据集
	 * @param names
	 *            要保留的字段列表
	 * @return 全新的IDataset
	 */
	public IDataset cloneIncludeCols(IDataset ds, String[] names) {
		if (ds == null) {
			return ds;
		} else if (names == null || names.length == 0) {
			return getDataset();
		}
		return ((CommonDataset) ds).cloneInclude(names);
	}

	/**
	 * 在原IDataset中，剔除names所列的所有列
	 * 
	 * @param ds
	 *            数据集
	 * @param names
	 *            需要剔除的字段列表
	 * @return 全新的IDataset
	 */
	public IDataset cloneExcludeCols(IDataset ds, String[] names) {
		if (ds == null || names == null || names.length == 0) {
			return ds;
		}
		return ((CommonDataset) ds).cloneExclude(names);
	}

	//
	// public static void main(String[] args)
	// {
	// int type = java.sql.Types.ARRAY;
	// System.out.println((char) type);
	// type = java.sql.Types.BIGINT;
	// System.out.println((char) type);
	// type = java.sql.Types.BINARY;
	// System.out.println((char) type);
	// type = java.sql.Types.BIT;
	// System.out.println((char) type);
	// type = java.sql.Types.BLOB;
	// System.out.println((char) type);
	// type = java.sql.Types.BOOLEAN;
	// System.out.println((char) type);
	// type = java.sql.Types.CHAR;
	// System.out.println((char) type);
	// type = java.sql.Types.CLOB;
	// System.out.println((char) type);
	// type = java.sql.Types.DATALINK;
	// System.out.println((char) type);
	// type = java.sql.Types.DATE;
	// System.out.println((char) type);
	// type = java.sql.Types.DECIMAL;
	// System.out.println((char) type);
	// type = java.sql.Types.DISTINCT;
	// System.out.println((char) type);
	// type = java.sql.Types.DOUBLE;
	// System.out.println((char) type);
	// type = java.sql.Types.INTEGER;
	// System.out.println((char) type);
	// type = java.sql.Types.JAVA_OBJECT;
	// System.out.println((char) type);
	// type = java.sql.Types.LONGVARBINARY;
	// System.out.println((char) type);
	// type = java.sql.Types.NULL;
	// System.out.println((char) type);
	// type = java.sql.Types.NUMERIC;
	// System.out.println((char) type);
	// type = java.sql.Types.OTHER;
	// System.out.println((char) type);
	// type = java.sql.Types.REAL;
	// System.out.println((char) type);
	// type = java.sql.Types.REF;
	// System.out.println((char) type);
	// type = java.sql.Types.SMALLINT;
	// System.out.println((char) type);
	// type = java.sql.Types.STRUCT;
	// System.out.println((char) type);
	// type = java.sql.Types.TIME;
	// System.out.println((char) type);
	// type = java.sql.Types.TIMESTAMP;
	// System.out.println((char) type);
	// type = java.sql.Types.TINYINT;
	// System.out.println((char) type);
	// type = java.sql.Types.VARBINARY;
	// System.out.println((char) type);
	// type = java.sql.Types.VARCHAR;
	// System.out.println((char) type);
	// }

}
