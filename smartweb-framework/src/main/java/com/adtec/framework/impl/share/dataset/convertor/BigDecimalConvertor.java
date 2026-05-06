/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: BigDecimalConvertor.java
 * 软件版权: 
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */

package com.adtec.framework.impl.share.dataset.convertor;

import java.math.BigDecimal;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * 功能说明:
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class BigDecimalConvertor implements Convertor<BigDecimal>
{
	public BigDecimal convert(IDataset dataset, String name)
	{
		String number = dataset.getString(name);
		try {
			return new BigDecimal(number);
		} catch (Exception e) {
			return null;
		}
	}

}
