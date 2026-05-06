
package com.adtec.framework.impl.share.dataset.convertor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * 功能说明:
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class DateConvertor implements Convertor<Date>
{
	public Date convert(IDataset dataset, String name)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dataset.getString(name);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			System.out.println("操作失败");
		}
		return new Date(0);
	}

}
