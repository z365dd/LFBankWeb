/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: IResultSetReader.java
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

package com.adtec.framework.interfaces.share.reader;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.interfaces.share.IDatasetBase;

/**
 * 类似于ResultSet结构的读取器 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-23 <br>
 * <br>
 */
public interface IResultSetReader extends IDatasetBase
{
	/**
	 * 以 Java 编程语言中 int 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	int getInt(String columnName);

	/**
	 * 以 Java 编程语言中 int 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	int getInt(int columnIndex);

	/**
	 * 以 Java 编程语言中 int 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	int getInt(String columnName, int def);

	/**
	 * 以 Java 编程语言中 int 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	int getInt(int columnIndex, int def);

	/**
	 * 以 Java 编程语言中 long 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	long getLong(String columnName);

	/**
	 * 以 Java 编程语言中 long 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	long getLong(int columnIndex);

	/**
	 * 以 Java 编程语言中 long 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	long getLong(String columnName, long def);

	/**
	 * 以 Java 编程语言中 long 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	long getLong(int columnIndex, long def);

	/**
	 * 以 Java 编程语言中 double 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	double getDouble(String columnName);

	/**
	 * 以 Java 编程语言中 double 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	double getDouble(int columnIndex);

	/**
	 * 以 Java 编程语言中 double 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	double getDouble(String columnName, double def);

	/**
	 * 以 Java 编程语言中 double 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	double getDouble(int columnIndex, double def);

	/**
	 * 以 Java 编程语言中 byte 数组的形式检索此 DataSet 对象的当前行中指定列的值。这些字节表示驱动程序返回的原始值。
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	byte[] getByteArray(String columnName);

	/**
	 * 以 Java 编程语言中 byte 数组的形式检索此 DataSet 对象的当前行中指定列的值。这些字节表示驱动程序返回的原始值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	byte[] getByteArray(int columnIndex);

	/**
	 * 以 Java 编程语言中 byte 数组的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	byte[] getByteArray(String columnName, byte[] def);

	/**
	 * 以 Java 编程语言中 byte 数组的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	byte[] getByteArray(int columnIndex, byte[] def);

	/**
	 * 以 Java 编程语言中 String 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String getString(String columnName);

	/**
	 * 以 Java 编程语言中 String 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String getString(int columnIndex);

	/**
	 * 以 Java 编程语言中 String 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String getString(String columnName, String def);

	/**
	 * 以 Java 编程语言中 String 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String getString(int columnIndex, String def);

	/**
	 * 以 Java 编程语言中 String[] 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String[] getStringArray(String columnName);

	/**
	 * 以 Java 编程语言中 String[] 的形式检索此 DataSet 对象的当前行中指定列的值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String[] getStringArray(int columnIndex);

	/**
	 * 以 Java 编程语言中 String[] 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String[] getStringArray(String columnName, String[] def);

	/**
	 * 以 Java 编程语言中 String[] 的形式检索此 DataSet 对象的当前行中指定列的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	String[] getStringArray(int columnIndex, String[] def);

	/**
	 * 不指定列类型，根据列名返回列原生的值
	 * 
	 * @param columnName
	 *            列的名称
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	Object getValue(String columnName);

	/**
	 * 不指定列类型，根据列索引返回列原生的值
	 * 
	 * @param columnIndex
	 *            列的索引
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	Object getValue(int columnIndex);

	/**
	 * 不指定列类型，根据列名返回列原生的值，并显式指定默认值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	Object getValue(String columnName, Object def);

	/**
	 * 不指定列类型，根据列索引返回列原生的值，并显式指定默认值。
	 * 
	 * @param columnIndex
	 *            列的索引
	 * @param def
	 *            显示的指定默认值
	 * 
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             如果列索引超出有效返回，会抛出此异常。另外，转换失败只有在模式为 IDataset.MODE_EXCEPTION 的时候才会抛出异常
	 */
	Object getValue(int columnIndex, Object def);

	/**
	 * 定位到指定行，行索引从1开始，如果超出了有效返回会抛出 {@link BaseException}
	 * 
	 * @param lineIndex
	 *            行索引，行索引从 1开始
	 * @throws BaseException
	 *             行索引超出有效范围
	 */
	void locateLine(int lineIndex);

	/**
	 * 将指针移动到此 DataSet 对象的第一行的前一行，在遍历前初始化指针。
	 */
	void beforeFirst();

	/**
	 * 判断是否有下一行
	 * 
	 * @return 如果下一行是有效行，则返回 true；如果下一行是无效行，则返回 false
	 */
	boolean hasNext();

	/**
	 * 将指针从当前位置下移一行。DataSet 指针最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；第二次调用使第二行成为当前行，依此类推。
	 * <p>
	 * 调用此方法不能保证每次都成功，如果指针越界会抛出 {@link NullPointerException}。为避免此种情况的发生，先要调用 {@link #hasNext()}
	 * 方法进行确认。
	 */
	void next();

}
