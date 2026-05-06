/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: IMapWriter.java
 * 软件版权:
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */

package com.adtec.framework.interfaces.share.writer;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * Map的方式设置数据集。可以通过 <code>IMapWriter</code> 来构造一个单行的数据集。
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-25 <br>
 * <br>
 */
public interface IMapWriter
{
	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，值的类型为 <code>int</code>。如果已经存在同名的 字段，则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, int value);

	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，值的类型为 <code>long</code>。如果已经存在同名的 字段，则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, long value);

	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，值的类型为 <code>double</code>。如果已经存在同名的
	 * 字段，则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, double value);

	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，值的类型为 <code>String</code>。如果已经存在同名的
	 * 字段，则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, String value);

	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，值的类型为 <code>byte[]</code>。如果已经存在同名的
	 * 字段，则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, byte[] value);

	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，值的类型为 <code>String[]</code>。如果已经存在同名的
	 * 字段，则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, String[] value);

	/**
	 * 增加一个字段，字段名为 <code>name</code>，值为 <code>value</code>，不指定值的类型。如果已经存在同名的字段则覆盖。
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	void put(String name, Object value);

	/**
	 * 获取与 <code>IMapWriter</code> 相关联的数据集。
	 * @return 数据集
	 */
	IDataset getDataset();
}
