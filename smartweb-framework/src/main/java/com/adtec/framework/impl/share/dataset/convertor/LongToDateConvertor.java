
package com.adtec.framework.impl.share.dataset.convertor;

import java.util.Date;

import com.adtec.framework.interfaces.share.writer.IConvertor;

/**
 * 功能说明:
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class LongToDateConvertor implements IConvertor
{
	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IConvertor#convert(java.lang.Object)
	 */
	public Object convert(Object value)
	{
		long var = Long.parseLong(value.toString());
		return new Date(var).toString();
	}
}
