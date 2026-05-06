/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: DatasetColumnType.java
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
 * Dataset的类型，包括整型 DS_INT、长整形 DS_LONG、byte数组 byte[]、字符串 String、双精度浮点型 double、 字符串数组
 * DS_STRING_ARRAY。在构造 <code>IDataet</code> 列信息的时候可能会用到。 <br>
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-5-25 <br>
 * <br>
 * @see IDataset#addColumn(String, int)
 * @see IDataset#getColumnType(int)
 */
public interface DatasetColumnType
{
	/** 未知类型 */
	final char	DS_UNKNOWN		= 'N';
	/** 整型 */
	final char	DS_INT			= 'I';
	/** 长整形 */
	final char	DS_LONG			= 'L';
	/** 二进制，字符数组 */
	final char	DS_BYTE_ARRAY	= 'R';
	/** 长浮点型 */
	final char	DS_DOUBLE		= 'D';
	/** 字符串类型 */
	final char	DS_STRING		= 'S';
	/** 字符串数组 */
	final char	DS_STRING_ARRAY	= 'A';
}
