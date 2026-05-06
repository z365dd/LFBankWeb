/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: IResultSetWriter.java
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

package com.adtec.framework.interfaces.share.writer;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.reader.IResultSetReader;

/**
 * 模拟java.sql.ResultSet的写入方式的IDataset的构造器
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-23 <br>
 * <br>
 */
public interface IResultSetWriter extends IResultSetReader
{
	/**
	 * 根据列名添加一个新的列，该列采用默认类型 <code>DatasetColumnType.DS_STRING</code>。如果已经存在该列名，则覆盖。
	 * @param colName
	 *            列名
	 */
	void addColumn(String colName);

	/**
	 * 添加一个新的列，并指定列名和类型，如果已经存在该列名，则覆盖
	 * 
	 * @param colName
	 *            列名
	 * @param type
	 *            列类型，该值不会作为行值类型校验的凭证
	 */
	void addColumn(String colName, int type);

	/**
	 * 修改指定列名的列的类型，只是修改了标识，而不会重新更新已经存在的值。
	 * @param colName
	 *            列名
	 * @param type
	 *            列类型，在 {@link DatasetColumnType} 中定义
	 * @throws BaseException
	 *             传入的列名不存在或者传入的类型无效
	 */
	void modifyColumnType(String colName, int type) throws BaseException;

	/**
	 * 修改指定列索引的列的类型，只是修改了标识，而不会重新更新已经存在的值
	 * @param colIndex
	 *            列索引
	 * @param type
	 *            列类型，在 {@link DatasetColumnType} 中定义
	 * @throws BaseException
	 *             传入的列名不存在或者传入的类型无效
	 */
	void modifyColumnType(int colIndex, int type) throws BaseException;

	/**
	 * 用 byte[] 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateByteArray(int columnIndex, byte[] v) throws BaseException;

	/**
	 * 用 byte[] 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateByteArray(String columnName, byte[] v) throws BaseException;

	/**
	 * 用 double 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateDouble(int columnIndex, double v) throws BaseException;

	/**
	 * 用 double 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateDouble(String columnName, double v) throws BaseException;

	/**
	 * 用 int 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateInt(int columnIndex, int v) throws BaseException;

	/**
	 * 用 int 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateInt(String columnName, int v) throws BaseException;

	/**
	 * 用 long 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateLong(int columnIndex, long v) throws BaseException;

	/**
	 * 用 long 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateLong(String columnName, long v) throws BaseException;

	/**
	 * 用 String 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateString(int columnIndex, String v) throws BaseException;

	/**
	 * 用 double 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateString(String columnName, String v) throws BaseException;

	/**
	 * 用 String[] 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateStringArray(int columnIndex, String[] v) throws BaseException;

	/**
	 * 用 String[] 值更新指定列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateStringArray(String columnName, String[] v) throws BaseException;

	/**
	 * 不指定具体的类型更新指定索引列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnIndex
	 *            第一个列是 1，第二个列是 2，……
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnIndex超出了列范围或者类型不匹配
	 */
	public void updateValue(int columnIndex, Object v);

	/**
	 * 不指定具体的类型更新指定名字列。更新方法用于更新当前行或插入行中的列值。
	 * 
	 * @param columnName
	 *            列的名称
	 * @param v
	 *            新列值
	 * @throws BaseException
	 *             指针不在有效行上、columnName列不存在或者类型不匹配
	 */
	public void updateValue(String columnName, Object v);

	/**
	 * 在插入模式下，将当前行插入到有效行的末尾，
	 * 
	 * @return 添加行成功返回true，如果不是处于插入模式，返回false
	 */
	public boolean appendRow();

	/**
	 * 清空所有行信息
	 */
	void clear();

	/**
	 * 情况所有行、列信息
	 */
	void clearAll();
}
