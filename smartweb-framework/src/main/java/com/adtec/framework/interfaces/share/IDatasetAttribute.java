/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: IDatasetAttribute.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */

package com.adtec.framework.interfaces.share;

/**
 * 数据集的属性，在这里定义了与数据集的类型绑定的默认值 <br>
 * <p>
 * 所有的操作都不是线程安全的。
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-29 <br>
 * @see IDataset
 */
public interface IDatasetAttribute
{
	/**
	 * 复制源数据集，值拷贝
	 * 
	 * @param dsa
	 *            源数据集属性
	 */
	public void copyFrom(IDatasetAttribute dsa);

	/**
	 * 获取默认的int值，系统默认为 0
	 * 
	 * @return int 类型的默认值
	 */
	public int getDefInt();

	/**
	 * 设置默认的int值，系统默认为 0
	 * 
	 * @param defInt
	 *            int 类型的默认值
	 */
	public void setDefInt(int defInt);

	/**
	 * 获取默认的long值，系统默认为 0L
	 * 
	 * @return long 类型的默认值
	 */
	public long getDefLong();

	/**
	 * 设置默认的long值，系统默认为 0L
	 * 
	 * @param defLong
	 *            long 类型的默认值
	 */
	public void setDefLong(long defLong);

	/**
	 * 获取默认的double值，系统默认为 0.0
	 * 
	 * @return double 类型的默认值
	 */
	public double getDefDouble();

	/**
	 * 设置默认的double值，系统默认为 0.0
	 * 
	 * @param defDouble
	 *            double 类型的默认值
	 */
	public void setDefDouble(double defDouble);

	/**
	 * 获取默认的String值，系统默认为 ""
	 * 
	 * @return String 类型的默认值
	 */
	public String getDefString();

	/**
	 * 获取默认的String值，系统默认为 ""，如果传入 null，则也采用系统默认值
	 * 
	 * @param defString
	 *            String 类型的默认值
	 */
	public void setDefString(String defString);

	/**
	 * 获取默认的byte[]值，系统默认为 new byte[0]
	 * 
	 * @return byte[] 类型的默认值
	 */
	public byte[] getDefBytes();

	/**
	 * 设置默认的byte[]值，系统默认为 new byte[0]，如果传入 null，则也采用系统默认值
	 * 
	 * @param defBytes
	 *            byte[] 类型的默认值
	 */
	public void setDefBytes(byte[] defBytes);

	/**
	 * 获取默认的String[]值，系统默认为 new String[0]
	 * 
	 * @return String[] 类型的默认值
	 */
	public String[] getDefStrings();

	/**
	 * 设置默认的String[]值，系统默认为 new String[0]，如果传入 null，则也采用系统默认值
	 * 
	 * @param defStrings
	 *            String[] 类型的默认值
	 */
	public void setDefStrings(String[] defStrings);

	/**
	 * 获取最大的Clob长度，系统默认为 0，表示不限制大小
	 * 
	 * @return 数据库中Clob的最大长度
	 */
	public int getMaxClobLength();

	/**
	 * 设置最大的Clob长度，如果为0，表示不限制大小
	 * 
	 * @param maxClobLength
	 *            数据库中Clob的最大长度
	 */
	public void setMaxClobLength(int maxClobLength);

	/**
	 * 获取最大的Blob长度，系统默认为 0，表示不限制大小
	 * 
	 * @return 数据库中Blob的最大长度
	 */
	public int getMaxBlobLength();

	/**
	 * 设置最大的Blob长度，如果为0，表示不限制大小
	 * 
	 * @param maxBlobLength
	 *            数据库中Blob的最大长度
	 */
	public void setMaxBlobLength(int maxBlobLength);

	/**
	 * 获取格式化Date等为String的格式，系统默认为 "yyyy-MM-dd' 'HH:mm:ss"
	 * 
	 * @return 日期转换格式
	 */
	public String getDateFormat();

	/**
	 * 设置格式化Date等为String的格式，系统默认为 "yyyy-MM-dd' 'HH:mm:ss"
	 * 
	 * @param dateFormat
	 *            日期转换格式
	 */
	public void setDateFormat(String dateFormat);

	/**
	 * 是否将boolean类型转化为字符串，如果是true转化为"true"，false转为为"false"；否则true转化为1，false转化为0
	 * 
	 * @return true 表示boolean类型会被转换为字符串，否则转换为整数
	 */
	public boolean isBoolAsString();

	/**
	 * 是否将boolean类型转化为字符串，如果是true转化为"true"，false转为为"false"；否则true转化为1，false转化为0
	 * 
	 * @param boolAsString
	 *            true 表示boolean类型会被转换为字符串，否则转换为整数
	 */
	public void setBoolAsString(boolean boolAsString);

	/**
	 * 获取Dataset的工作模式，Dataset在获取数据的时候，根据工作模式判断是抛出异常还是采用默认值。
	 * <p>
	 * 此种获取Dataset工作模式的方法已经废弃，可以直接从IDataset中获取 {@link IDataset#getMode()}。
	 * 
	 * @return IDataset.MODE_EXCEPTION 在获取列值失败的情况下，抛出异常 <br>
	 *         IDataset.MODE_DEFAULT 在获取列值失败的情况下，采用默认值
	 */
	@Deprecated
	public int getWorkMode();
}
