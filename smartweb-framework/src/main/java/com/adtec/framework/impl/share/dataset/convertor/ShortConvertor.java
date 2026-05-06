
package com.adtec.framework.impl.share.dataset.convertor;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * 功能说明:
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 */
public class ShortConvertor implements Convertor<Short>
{
	public Short convert(IDataset dataset, String name)
	{
		return ((Integer) dataset.getInt(name)).shortValue();
	}
}
