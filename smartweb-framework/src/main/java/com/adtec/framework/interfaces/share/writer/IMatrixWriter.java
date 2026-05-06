/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: IMatrixWriter.java
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

/**
 * 以矩阵的方式填数据 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-23 <br>
 * <br>
 */
public interface IMatrixWriter
{
	/**
	 * 设置一个矩阵的长和宽
	 * 
	 * @param rowCount
	 *            行数
	 * @param colCount
	 *            列数
	 */
	void setMatrix(int rowCount, int colCount);

	/**
	 * 设置某一列的列名和类型
	 * 
	 * @param colIndex
	 *            列索引，从1开始
	 * @param colName
	 *            指定的列名
	 * @param type
	 *            列类型
	 */
	void setColumn(int colIndex, String colName, int type);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colIndex
	 *            列索引，从1开始
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, int colIndex, int value);

	/**
	 * 指定行列修改数据，类型为 <code>long</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colIndex
	 *            列索引，从1开始
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, int colIndex, long value);

	/**
	 * 指定行列修改数据，类型为 <code>String</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colIndex
	 *            列索引，从1开始
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, int colIndex, String value);

	/**
	 * 指定行列修改数据，类型为 <code>double</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colIndex
	 *            列索引，从1开始
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, int colIndex, double value);

	/**
	 * 指定行列修改数据，类型为 <code>byte[]</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colIndex
	 *            列索引，从1开始
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, int colIndex, byte[] value);

	/**
	 * 指定行列修改数据，类型为 <code>String[]</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colIndex
	 *            列索引，从1开始
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, int colIndex, String[] value);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colName
	 *            列名
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, String colName, int value);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colName
	 *            列名
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, String colName, long value);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colName
	 *            列名
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, String colName, String value);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colName
	 *            列名
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, String colName, double value);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colName
	 *            列名
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, String colName, byte[] value);

	/**
	 * 指定行列修改数据，类型为 <code>int</code>
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @param colName
	 *            列名
	 * @param value
	 *            修改的值
	 */
	void setValue(int rowIndex, String colName, String[] value);
}
