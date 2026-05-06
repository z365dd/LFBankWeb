/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: IConvertor.java
 * 软件版权: 
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */

package com.adtec.framework.interfaces.share.writer;

/**
 * 字段值转换器，传入某个值，经过计算后返回另外一个值
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-1 <br>
 */
public interface IConvertor
{
	/**
	 * 根据传入的一个值，返回另一个值
	 * @param value
	 *            原来的值
	 * @return 转换后的值
	 */
	Object convert(Object value);
}
