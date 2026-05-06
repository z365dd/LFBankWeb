
package com.adtec.framework.impl.share.dataset.convertor;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * 功能说明:
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class LongConvertor implements Convertor<Long>
{
	public Long convert(IDataset dataset, String name)
	{
		return dataset.getLong(name);
	}

}
