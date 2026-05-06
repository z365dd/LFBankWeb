/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: IColumnModifier.java
 * 软件版权: 
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */

package com.adtec.framework.interfaces.share.writer;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.interfaces.share.IDataset;

/**
 * <code>IColumnModifier</code> 是 {@link IDataset} 的列编辑器，可以对指定列（支持列索引和列名）的所有行进行批量修改。
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-1 <br>
 */
public interface IColumnModifier
{
	/**
	 * 根据列名向列修改器注册一个转换器，如果有该列已经被注册，则会覆盖原有的转换器，包括按照列索引注册的转换器
	 * @param columnName
	 *            列名
	 * @param columnType
	 *            列类型
	 * @param convertor
	 *            转换器
	 * @throws BaseException
	 *             列名不存在或者传入的列名不存在
	 */
	void registerConvertor(String columnName, int columnType, IConvertor convertor)
			throws BaseException;

	/**
	 * 根据列索引向列修改器注册一个转换器，如果有该列已经被注册，则会覆盖原有的转换器，包括按照列名注册的转换器
	 * @param columnIndex
	 *            列索引
	 * @param columnType
	 *            列类型
	 * @param convertor
	 *            转换器
	 * @throws BaseException
	 *             列索引超出返回或者传入的列名不存在
	 */
	void registerConvertor(int columnIndex, int columnType, IConvertor convertor)
			throws BaseException;

	/**
	 * 执行转换，并返回转换滞后的数据集，根据实现的不同，可能返回的实例与传入的数据集实例不同
	 * @param dataset
	 *            待转换的数据集
	 * @return 转换后的数据集
	 */
	IDataset doConvert(IDataset dataset);
}
