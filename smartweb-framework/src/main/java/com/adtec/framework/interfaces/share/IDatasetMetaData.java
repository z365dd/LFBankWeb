/*
 * 系统名称:
 * 模块名称:
 * 类 名 称: IDatasetMetaData.java
 * 软件版权:
 * 相关文档:
 * 修改记录:
 * 修改日期              修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */

package com.adtec.framework.interfaces.share;

/**
 * 数据集元数据，提供了访问列基本信息的接口 <br>
 * <p> 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-5-25 <br>
 * @see IDataset
 */
public interface IDatasetMetaData
{
	/**
	 * 返回此 DataSet 对象中的列数。
	 * 
	 * @return 列数
	 */
	int getColumnCount();

	/**
	 * 检索指定列的类型。 <br>
	 * Dataset支持的类型在 {@link DatasetColumnType} 中定义，包括整型 DS_INT、长整形
	 * DS_LONG、byte数组 byte[]、字符串 String、双精度浮点型 double、字符串数组 DS_STRING_ARRAY
	 * 
	 * @see com.adtec.framework.interfaces.share.DatasetColumnType
	 * 
	 * @param column
	 *            第一列是 1，第二个列是 2，……
	 * @return 列类型，如果该列无效则返回 <code>DatasetColumnType.DS_UNKNOWN</code>
	 */
	char getColumnType(int column);

	/**
	 * 将给定的 DataSet 列名称映射到其 DataSet 列索引。
	 * 
	 * @param columnName
	 *            列的名称
	 * @return 给定列名称的列索引，如果不存在则返回 0
	 */
	int findColumn(String columnName);

	/**
	 * 获取指定列的名称
	 * 
	 * @param column
	 *            第一列是 1，第二个列是 2，……
	 * @return 列名称，如果索引无效则返回 <code>null</code>
	 */
	String getColumnName(int column);

}
