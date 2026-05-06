/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: FieldCreator.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */

package com.adtec.framework.impl.share.event.field;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDatasetAttribute;

/**
 * 数据集域值构造器，用于根据类型生成数据集的域信息 <code>Field</code> 和 值信息<code>FieldValue</code>
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class FieldCreator
{

	/** 外部类型等到内部类型映射关系表 */
	private static Map<Integer, Integer>	typeMap	= new HashMap<Integer, Integer>();

	/** 数据集参数 */
	private IDatasetAttribute				dssa;

	static {

		// 基本类型
		// {  支持C和F类型的参数
		typeMap.put((int) 'C', (int) DatasetColumnType.DS_STRING);
		typeMap.put((int) 'F', (int) DatasetColumnType.DS_DOUBLE);
		// }
		typeMap.put((int) DatasetColumnType.DS_BYTE_ARRAY, (int) DatasetColumnType.DS_BYTE_ARRAY);
		typeMap.put((int) DatasetColumnType.DS_DOUBLE, (int) DatasetColumnType.DS_DOUBLE);
		typeMap.put((int) DatasetColumnType.DS_INT, (int) DatasetColumnType.DS_INT);
		typeMap.put((int) DatasetColumnType.DS_LONG, (int) DatasetColumnType.DS_LONG);
		typeMap.put((int) DatasetColumnType.DS_STRING, (int) DatasetColumnType.DS_STRING);
		typeMap.put((int) DatasetColumnType.DS_STRING_ARRAY,
				(int) DatasetColumnType.DS_STRING_ARRAY);

		// TODO 数据库类型 - - 没有经过评审，其他没有显示指定的类型，统一都是String的方式
		typeMap.put(Types.BLOB, (int) DatasetColumnType.DS_BYTE_ARRAY);
		typeMap.put(Types.CLOB, (int) DatasetColumnType.DS_STRING);
		typeMap.put(Types.CHAR, (int) DatasetColumnType.DS_INT);
		typeMap.put(Types.DATE, (int) DatasetColumnType.DS_STRING_ARRAY);
		typeMap.put(Types.DECIMAL, (int) DatasetColumnType.DS_DOUBLE);
		typeMap.put(Types.DOUBLE, (int) DatasetColumnType.DS_DOUBLE);
		typeMap.put(Types.FLOAT, (int) DatasetColumnType.DS_DOUBLE);
		typeMap.put(Types.INTEGER, (int) DatasetColumnType.DS_LONG);
		typeMap.put(Types.NUMERIC, (int) DatasetColumnType.DS_DOUBLE);
		typeMap.put(Types.REAL, (int) DatasetColumnType.DS_DOUBLE);
		typeMap.put(Types.TINYINT, (int) DatasetColumnType.DS_INT);
		typeMap.put(Types.TIME, (int) DatasetColumnType.DS_STRING);
		typeMap.put(Types.VARCHAR, (int) DatasetColumnType.DS_STRING);

	}

	/**
	 * 根据数据集属性构造一个 <code>FieldCreator</code>
	 * @param dssa
	 *            数据集属性
	 */
	private FieldCreator(IDatasetAttribute dssa)
	{
		this.dssa = dssa;
	}

	/**
	 * 根据数据集属性，构造一个域值构造器
	 * @param dssa
	 *            数据集属性
	 * @return 新的域值构造器
	 */
	public static FieldCreator getNewInstance(IDatasetAttribute dssa)
	{
		return new FieldCreator(dssa);
	}

	/**
	 * 根据类型获取指定域
	 * @param name
	 *            域名
	 * @param type
	 *            域类型
	 * @return 域
	 */
	public Field getField(String name, int type)
	{
		Integer baseType = typeMap.get(type);
		char interType;
		if (baseType == null) {
			interType = DatasetColumnType.DS_STRING;
		} else {
			interType = (char) baseType.intValue();
		}

		return new Field(name, interType);
	}

	/**
	 * 获取相应类型的默认域值，默认值从 {@link IDatasetAttribute} 中获取。
	 * @param type
	 *            类型
	 * @return 域值
	 */
	public FieldValue getDefaultValue(int type)
	{
		Integer baseType = typeMap.get(type);
		char interType;
		if (baseType == null) {
			interType = DatasetColumnType.DS_STRING;
		} else {
			interType = (char) baseType.intValue();
		}
		// 枚举所有类型
		switch (interType) {
		case DatasetColumnType.DS_STRING:
			return new FieldValue(dssa.getDefString(), dssa); 
			// 将最有可能的放在最前面，提高命中
		case DatasetColumnType.DS_DOUBLE:
			return new FieldValue(dssa.getDefDouble(), dssa);
		case DatasetColumnType.DS_INT:
			return new FieldValue(dssa.getDefInt(), dssa);
		case DatasetColumnType.DS_LONG:
			return new FieldValue(dssa.getDefLong(), dssa);
		case DatasetColumnType.DS_BYTE_ARRAY:
			return new FieldValue(dssa.getDefBytes(), dssa);
		case DatasetColumnType.DS_STRING_ARRAY:
			return new FieldValue(dssa.getDefStrings(), dssa);
		default:
			return new FieldValue(dssa.getDefString(), dssa);
		}
	}

	/**
	 * 获取域值，类型为 byte[]
	 * @param value
	 *            值
	 * @return 域值
	 */
	public FieldValue getFieldValue(byte[] value)
	{
		if (value == null) {
			value = dssa.getDefBytes();
		}
		return new FieldValue(value, dssa);
	}

	/**
	 * 获取域值，类型为 int
	 * @param value
	 *            值
	 * @return 域值
	 */

	public FieldValue getFieldValue(int value)
	{
		return new FieldValue(value, dssa);
	}

	/**
	 * 获取域值，类型为 long
	 * @param value
	 *            值
	 * @return 域值
	 */
	public FieldValue getFieldValue(long value)
	{
		return new FieldValue(value, dssa);
	}

	/**
	 * 获取域值，类型为 String
	 * @param value
	 *            值
	 * @return 域值
	 */
	public FieldValue getFieldValue(String value)
	{
		if (value == null) {
			value = dssa.getDefString();
		}
		return new FieldValue(value, dssa);
	}

	/**
	 * 获取域值，类型为 String[]
	 * @param value
	 *            值
	 * @return 域值
	 */
	public FieldValue getFieldValue(String[] value)
	{
		if (value == null) {
			value = dssa.getDefStrings();
		}
		return new FieldValue(value, dssa);
	}

	/**
	 * 获取域值，类型为 double
	 * @param value
	 *            值
	 * @return 域值
	 */
	public FieldValue getFieldValue(double value)
	{
		return new FieldValue(value, dssa);
	}

	@SuppressWarnings("unchecked")
	public FieldValue getFieldValue(Object value)
	{
		if (value == null) {
			return new FieldValue();
		} else if (value instanceof String) { 
			// 将最有可能数据放在最前面，提高命中，降低比较过程
			return new FieldValue(value.toString(), dssa);
		} else if (value instanceof Number) {
			// {  对于BigDecimal类型，如果精度等于0，则表示为long，否则表示为double
			if (value instanceof BigDecimal) {
				BigDecimal tmp = (BigDecimal) value;
				if (tmp.scale() <= 0) {
					return new FieldValue(tmp.longValue(), dssa);
				} else {
					return new FieldValue(tmp.doubleValue(), dssa);
				}
			}
			// }
			if (value instanceof BigDecimal || value instanceof Float || value instanceof Double) {
				return new FieldValue(Double.valueOf(value.toString()), dssa);
			} else if (value instanceof Long) {
				return new FieldValue((Long) value, dssa);
			} else {
				return new FieldValue(Integer.valueOf(value.toString()), dssa);
			}
		} else if (value instanceof byte[]) {
			return new FieldValue((byte[]) value, dssa);
		} else if (value instanceof String[]) {
			return new FieldValue((String[]) value, dssa);
		} else if (value instanceof Character) {
			return new FieldValue(value.toString(), dssa);
		} else if (value instanceof Date) {
			SimpleDateFormat formatter = new SimpleDateFormat(dssa.getDateFormat());
			return new FieldValue(formatter.format(value), dssa);
		} else if (value instanceof Calendar) {
			SimpleDateFormat formatter = new SimpleDateFormat(dssa.getDateFormat());
			return new FieldValue(formatter.format(((Calendar) value).getTime()), dssa);
		} else if (value instanceof Locale) {
			return new FieldValue(value.toString(), dssa);
		} else if (value instanceof Enum) {
			return new FieldValue(((Enum) value).name(), dssa);
		} else if (value instanceof Boolean) {
			return new FieldValue((Boolean) value, dssa);
		} else if (value instanceof java.sql.Clob) {
			String clobStr = "";
			java.sql.Clob clob = (java.sql.Clob) value;
			try {
				if (dssa.getMaxClobLength() == 0 || dssa.getMaxClobLength() >= clob.length()) {
					clobStr = clob.getSubString(1, (int) clob.length());
				} else {
					clobStr = clob.getSubString(1, dssa.getMaxClobLength()) + "...";
				}
			} catch (SQLException e) {
				clobStr = "";
			}
			return new FieldValue(clobStr, dssa);
		} else if (value instanceof java.sql.Blob) {
			// String blobStr = "";
			// java.sql.Blob blob = (java.sql.Blob) value;
			// try {
			// if (dssa.getMaxBlobLength() == 0 || dssa.getMaxBlobLength() >=
			// blob.length()) {
			// blobStr = new String(blob.getBytes(1, (int) blob.length()));
			// } else {
			// blobStr = new String(blob.getBytes(1, dssa.getMaxBlobLength()));
			// // 在构造函数里加会改变字符
			// blobStr += "...";
			// }
			// } catch (SQLException e) {
			// blobStr = "";
			// }
			// return new FieldValue(blobStr, dssa);

			// { 20110722 HYin 将BLOB按照byte[]的方式来处理
			try {
				java.sql.Blob blob = (java.sql.Blob) value;
				return new FieldValue(blob.getBytes(1, (int) blob.length()), dssa);
			} catch (SQLException e) {
				return new FieldValue(new byte[0], dssa);
			}
			// }
		} else if (value instanceof Serializable) {
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				os.write("#jresobj#".getBytes());
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(value);
				return new FieldValue(os.toByteArray(), dssa);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new FieldValue(value.toString(), dssa);
	}

	/**
	 * @return the typeMap
	 */
	public static Map<Integer, Integer> getTypeMap()
	{
		return typeMap;
	}
}
