/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: IMatrixReader.java
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
 * 一个矩阵结构，行列索引都从1开始 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-22 <br>
 * <br>
 */
public interface IMatrixReader extends IDatasetBase
{
	/**
	 * 根据行列索引定位值，值的类型为 int
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	int matrixGetInt(int rowIndex, int colIndex);

	/**
	 * 根据行列索引定位值，值类型为 int
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	int matrixGetInt(int rowIndex, int colIndex, int def);

	/**
	 * 根据行列索引定位值，值的类型为 int
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	int matrixGetInt(int rowIndex, String colName);

	/**
	 * 根据行列索引定位值，值类型为 int
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	int matrixGetInt(int rowIndex, String colName, int def);

	/**
	 * 根据行列索引定位值，值的类型为 long
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	long matrixGetLong(int rowIndex, int colIndex);

	/**
	 * 根据行列索引定位值，值类型为 long
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	long matrixGetLong(int rowIndex, int colIndex, long def);

	/**
	 * 根据行列索引定位值，值的类型为 long
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	long matrixGetLong(int rowIndex, String colName);

	/**
	 * 根据行列索引定位值，值类型为 long
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	long matrixGetLong(int rowIndex, String colName, long def);

	/**
	 * 根据行列索引定位值，值的类型为 double
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	double matrixGetDouble(int rowIndex, int colIndex);

	/**
	 * 根据行列索引定位值，值类型为 double
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	double matrixGetDouble(int rowIndex, int colIndex, double def);

	/**
	 * 根据行列索引定位值，值的类型为 double
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	double matrixGetDouble(int rowIndex, String colName);

	/**
	 * 根据行列索引定位值，值类型为 double
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	double matrixGetDouble(int rowIndex, String colName, double def);

	/**
	 * 根据行列索引定位值，值的类型为 String
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String matrixGetString(int rowIndex, int colIndex);

	/**
	 * 根据行列索引定位值，值类型为 String
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String matrixGetString(int rowIndex, int colIndex, String def);

	/**
	 * 根据行列索引定位值，值的类型为 String
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String matrixGetString(int rowIndex, String colName);

	/**
	 * 根据行列索引定位值，值类型为 String
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String matrixGetString(int rowIndex, String colName, String def);

	/**
	 * 根据行列索引定位值，值的类型为 String[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String[] matrixGetStringArray(int rowIndex, int colIndex);

	/**
	 * 根据行列索引定位值，值类型为 String[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String[] matrixGetStringArray(int rowIndex, int colIndex, String[] def);

	/**
	 * 根据行列索引定位值，值的类型为 String[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String[] matrixGetStringArray(int rowIndex, String colName);

	/**
	 * 根据行列索引定位值，值类型为 String[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	String[] matrixGetStringArray(int rowIndex, String colName, String[] def);

	/**
	 * 根据行列索引定位值，值的类型为 byte[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	byte[] matrixGetByteArray(int rowIndex, int colIndex);

	/**
	 * 根据行列索引定位值，值类型为 byte[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	byte[] matrixGetByteArray(int rowIndex, int colIndex, byte[] def);

	/**
	 * 根据行列索引定位值，值的类型为 byte[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	byte[] matrixGetByteArray(int rowIndex, String colName);

	/**
	 * 根据行列索引定位值，值类型为 byte[]
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param colName
	 *            列名
	 * @param def
	 *            默认值
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 * @throws BaseException
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	byte[] matrixGetByteArray(int rowIndex, String colName, byte[] def);
}
