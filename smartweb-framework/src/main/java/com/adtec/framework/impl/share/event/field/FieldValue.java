/*
 * 系统名称:
 * 模块名称:
 * 类 名 称: FieldValue.java
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

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetAttribute;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDatasetAttribute;

/**
 * 数据集的值信息，
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public final class FieldValue implements DatasetColumnType, Cloneable
{
	/** 默认值表示 */
	private final char			DEFAULT_TYPE	= 'T';
	/** 默认值表示 */
	private final char			BOOLEAN_TYPE	= 'B';
	/** 域值类型 */
	private char				type			= DS_UNKNOWN;
	/** 数据的原始值 */
	private final Object		value;
	/** 与此数据集相关联的属性 */
	private IDatasetAttribute	dssa;

	/**
	 * 使用默认的值
	 */
	public FieldValue()
	{
		value = null;
		type = DEFAULT_TYPE;
	}

	//  增加复制构造函数
	public FieldValue(FieldValue other)
	{
		this.type = other.type;
		this.value = other.value;
		this.dssa = other.dssa;
	}

	/**
	 * 构造一个原始类型为 byte[] 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */
	protected FieldValue(byte[] value, IDatasetAttribute dssa)
	{
		if (value == null) {
			value = new byte[0];
		}
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = DS_BYTE_ARRAY;
		this.value = value;
		this.dssa = dssa;
	}

	/**
	 * 构造一个原始类型为 String 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */

	protected FieldValue(String value, IDatasetAttribute dssa)
	{
		if (value == null) {
			value = "";
		}
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = DS_STRING;
		this.value = value;
		this.dssa = dssa;
	}

	/**
	 * 构造一个原始类型为 String[] 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */

	protected FieldValue(String[] value, IDatasetAttribute dssa)
	{
		if (value == null) {
			value = new String[0];
		}
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = DS_STRING_ARRAY;
		this.value = value;

		this.dssa = dssa;
	}

	/**
	 * 构造一个原始类型为 int 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */
	protected FieldValue(int value, IDatasetAttribute dssa)
	{
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = DS_INT;
		this.value = value;
		this.dssa = dssa;
	}

	/**
	 * 构造一个原始类型为 long 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */
	protected FieldValue(long value, IDatasetAttribute dssa)
	{
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = DS_LONG;
		this.value = value;
		this.dssa = dssa;
	}

	/**
	 * 构造一个原始类型为 double 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */
	protected FieldValue(double value, IDatasetAttribute dssa)
	{
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = DS_DOUBLE;
		this.value = value;
		this.dssa = dssa;
	}

	/**
	 * 构造一个原始类型为 boolean 的域值
	 * @param value
	 *            byte[] 类型的值
	 * @param dssa
	 *            数据集属性
	 */
	protected FieldValue(boolean value, IDatasetAttribute dssa)
	{
		if (dssa == null) {
			dssa = new DatasetAttribute();
		}

		type = BOOLEAN_TYPE;
		this.value = value;
		this.dssa = dssa;
	}

	/**
	 * 获取 byte[] 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @return byte[] 形式的值
	 */
	public byte[] getByteArray()
	{
		return getByteArray(dssa.getDefBytes());
	}

	/**
	 * 获取 byte[] 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @param def
	 *            显示指定默认值
	 * @return byte[] 形式的值
	 */
	public byte[] getByteArray(byte[] def)
	{
		if (type == DS_BYTE_ARRAY) {
			return (byte[]) value;
		} else if (type == DS_STRING || type == DS_DOUBLE || type == DS_INT || type == DS_LONG) {
			return value.toString().getBytes();
		} else if (useDefaultValue()) {
			return def;
		} else {
			throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
					"byte[]");
		}
	}

	/**
	 * 获取 String 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @return String 形式的值
	 */
	public String getString()
	{
		return getString(dssa.getDefString());
	}

	/**
	 * 获取 String 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @param def
	 *            显示指定默认值
	 * @return String 形式的值
	 */
	public String getString(String def)
	{
		if (type == DS_STRING) {
			return value.toString();
		} else if (type == BOOLEAN_TYPE) {
			return value.toString();
		} else if (type == DS_BYTE_ARRAY) {
			return new String((byte[]) value);
		} else if (type == DS_DOUBLE || type == DS_INT || type == DS_LONG) {
			return value.toString();
		} else if (useDefaultValue()) {
			return def;
		} else {
			throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
					"string");
		}
	}

	/**
	 * 获取 String[] 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @return String[] 形式的值
	 */
	public String[] getStringArray()
	{
		return getStringArray(dssa.getDefStrings());
	}

	/**
	 * 获取 String[] 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @param def
	 *            显示指定默认值
	 * @return String[] 形式的值
	 */
	public String[] getStringArray(String[] def)
	{
		if (type == DS_STRING_ARRAY) {
			return (String[]) value;
		} else if (type == DS_BYTE_ARRAY) {
			return new String[] { new String((byte[]) value) };
		} else if (type == DS_DOUBLE || type == DS_INT || type == DS_LONG || type == DS_STRING) {
			return new String[] { value.toString() };
		} else if (useDefaultValue()) {
			return def;
		} else {
			throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
					"string");
		}
	}

	/**
	 * 获取 int 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @return int 形式的值
	 */
	public int getInt()
	{
		return getInt(dssa.getDefInt());
	}

	/**
	 * 获取 int 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @param def
	 *            显示指定默认值
	 * @return int 形式的值
	 */
	public int getInt(int def)
	{
		if (useDefaultValue()) {
			return def;
		}

		if (type == DS_INT) {
			return (Integer) value;
		} else if (type == DS_DOUBLE) {
			try {
				Double d = (Double) value;
				return d.intValue();
			} catch (Exception e) {
				throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
						"int");
			}
		} else if (type == BOOLEAN_TYPE) {
			boolean v = (Boolean) value;
			if (v) {
				return 1;
			} else {
				return 0;
			}
		} else if (type == DS_BYTE_ARRAY) {
			// {  将byte[]转换为String之后再做转换
			String s = new String((byte[]) value);
			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
						"int");
			}
			// }
		} else {
			try {
				return Integer.parseInt(value.toString());
			} catch (Exception e) {
				Double d = attempt2Transform();
				if (d == null) {
					throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
							"int");
				} else {
					return d.intValue();
				}
			}
		}
	}

	/**
	 * 获取 long 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @return long 形式的值
	 */
	public long getLong()
	{
		return getLong(dssa.getDefLong());
	}

	/**
	 * 获取 long 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @param def
	 *            显示指定默认值
	 * @return long 形式的值
	 */
	public long getLong(long def)
	{
		if (useDefaultValue()) {
			return def;
		}

		if (type == DS_LONG) {
			return (Long) value;
		} else if (type == DS_DOUBLE) {
			try {
				Double d = (Double) value;
				return d.longValue();
			} catch (Exception e) {
				throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
						"long");
			}
		} else if (type == BOOLEAN_TYPE) {
			boolean v = (Boolean) value;
			if (v) {
				return 1;
			} else {
				return 0;
			}
		} else if (type == DS_BYTE_ARRAY) {
			// {  将byte[]转换为String之后再做转换
			String s = new String((byte[]) value);
			try {
				return Long.parseLong(s);
			} catch (Exception e) {
				throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
						"long");
			}

			// }
		} else {
			try {
				return Long.parseLong(value.toString());
			} catch (Exception e) {
				Double d = attempt2Transform();
				if (d == null) {
					throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
							"long");
				} else {
					return d.longValue();
				}
			}
		}
	}

	/**
	 * 获取 double 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @return double 形式的值
	 */
	public double getDouble()
	{
		return getDouble(dssa.getDefDouble());
	}

	/**
	 * 获取 double 形式的值，做尽力的转化，如果无法转换或者转换失败则根据当前的工作方式决定 <br>
	 * 是抛出运行时异常还是采用默认值
	 * @param def
	 *            显示指定默认值
	 * @return double 形式的值
	 */
	public double getDouble(double def)
	{
		if (useDefaultValue()) {
			return def;
		}

		if (type == DS_DOUBLE) {
			return (Double) value;
		} else {
			try {
				// {  将byte[]转换为String之后再做转换
				String s;
				if (type == DS_BYTE_ARRAY) {
					s = new String((byte[]) value);
				} else {
					s = value.toString();
				}
				return Double.parseDouble(s);
				// }
			} catch (Exception e) {
				throw new BaseException(SysErr.E_TYPE_CONVERT_FAILURE, shortData(),
						"double");
			}
		}
	}

	/**
	 * 得到值的类型
	 * @return 值的类型
	 */
	public char getType()
	{
		char type = this.type;
		if (type == BOOLEAN_TYPE) {
			type = DS_STRING;
		}
		return type;
	}

	/**
	 * 得到值的原始值
	 * @return 值的原始值
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * 判断该值是否是用作默认值
	 * @return 如果返回true，表示采用默认值，false表示采用正常值
	 */
	public boolean useDefaultValue()
	{
		return DEFAULT_TYPE == type;
	}

	protected Double attempt2Transform()
	{
		try {
			Double d = Double.parseDouble(value.toString());
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串截断为8个字节，主要用于提示信息
	 * @return 如果不大于100个字节，则返回原始字符串，否则截断返回
	 */
	private String shortData()
	{
		StringBuffer sb = new StringBuffer();
		if (type == DS_BYTE_ARRAY) {
			sb.append(new String((byte[]) value));
		} else if (type == DS_STRING_ARRAY) {
			sb.append("String[] -- ");
			String[] tmp = (String[]) value;
			for (String str : tmp) {
				sb.append(str);
				sb.append("_@I@_");
			}
		} else {
			sb.append(value);
		}
		String print = sb.toString();
		if (print.length() > 100) {
			String t = print.substring(0, 100) + " ...";
			return t;
		} else {
			return print;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String data = "can't display!";
		try {
			data = shortData();
		} catch (Exception e) {
			System.out.println("操作失败");
		}
		return "[" + data + "," + type + "]";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public FieldValue clone()
	{
		try {
			return (FieldValue) super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	// public static void main(String[] args)
	// {
	// FieldValue fv = new FieldValue(false, null);
	// System.out.println(fv.getString());
	// System.out.println(fv.getInt());
	//
	// Object vv = false;
	// boolean bv = (Boolean) vv;
	// System.out.println(bv);
	//
	// vv = Boolean.TRUE;
	// bv = (Boolean) vv;
	// System.out.println(bv);
	// }
}
