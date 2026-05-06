
package com.adtec.framework.impl.share.dataset.convertor;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * 默认转换器
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 */
public class DefaultConvertor<T> implements Convertor<T>
{
	private Class<T>	clz;

	public DefaultConvertor(Class<T> clz)
	{
		this.clz = clz;
	}

	public T convert(IDataset dataset, String name)
	{
		try {
			return clz.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

}
