/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: IDatasets.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期              修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */

package com.adtec.framework.interfaces.share;

import com.adtec.framework.exception.BaseException;

/**
 * <code>IDatasets</code> 是 <code>IDataset</code> 的一个容器，提供了按照名字或者索引获取数据集的方式。
 * <p> 可以放入多个数据集，每个数据集的名字都要不一样，如果存在名字相同的数据集，后者将会覆盖前者。
 * <p> <code>IDatasets</code> 最多只能有一个数据集没有指定名字，尝试去存放 {@link #putDataset(IDataset)} 两个无名的数据集，
 * 会抛出运行时异常 {@link BaseException}。
 * <p> 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-1 <br>
 * <br>
 * @see IDataset
 */
public interface IDatasets
{
	/**
	 * 添加一个Dataset，如果数据集的名字重复，后者将会覆盖前者，
	 * <p> <code>IDatasets</code> 最多只能有一个数据集没有指定名字，尝试去存放两个无名的数据集，
	 * 会抛出运行时异常 {@link BaseException}。
	 * @param dataset
	 *            数据集
	 * @throws BaseException 尝试存放两个无名的数据集
	 */
	void putDataset(IDataset dataset) throws BaseException;

	/**
	 * 根据数据集的名字获取数据集
	 * 
	 * @param name
	 *            数据集的名字
	 * @return 数据集，如果没有找到，返回 <code>null</code>
	 */
	IDataset getDataset(String name);

	/**
	 * 根据索引获取数据集的名字
	 * 
	 * @param index
	 *            索引
	 * @return 数据集的名字，如果索引无效，返回 <code>null</code>
	 */
	String getDatasetName(int index);

	/**
	 * 根据索引获取数据集
	 * 
	 * @param index
	 *            数据集的索引
	 * @return 数据集，如果索引无效，返回 <code>null</code>
	 */
	IDataset getDataset(int index);

	/**
	 * 返回填写的数据集个数
	 * 
	 * @return 数据集个数
	 */
	int getDatasetCount();

	/**
	 * 清空数据集容器
	 */
	void clear();

}
