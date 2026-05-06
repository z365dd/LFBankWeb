/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: DatasetAttribute.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */

package com.adtec.framework.impl.share.dataset;

import com.adtec.framework.interfaces.share.IDatasetAttribute;
import com.adtec.framework.interfaces.share.IDatasetBase;

/**
 * IDatasetAttribute 的实现类
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class DatasetAttribute implements IDatasetAttribute
{
	/** int类型的默认值 */
	protected int		defInt			= 0;
	/** long类型的默认值 */
	protected long		defLong			= 0L;
	/** 浮点类型的默认值 */
	protected double	defDouble		= 0D;
	/** String类型的默认值 */
	protected String	defString		= "";
	/** byte[]类型的默认值 */
	protected byte[]	defBytes		= new byte[0];
	/** String[]类型的默认值 */
	protected String[]	defStrings		= new String[0];

	/** 是否将boolean类型转换为String */
	protected boolean	boolAsString	= true;

	/** Clob最大的长度 */
	protected int		maxClobLength	= 0;
	/** Blob最大的长度 */
	protected int		maxBlobLength	= 0;

	/** 将日期转换为String的 */
	protected String	dateFormat		= "yyyy-MM-dd' 'HH:mm:ss";		// RFC3339日期格式
	/** Dataset的工作模式 */
	protected int		workmode		= IDatasetBase.MODE_DEFAULT;

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute# copyFrom
	 * (com.adtec.framework.interfaces.share.dataset.IDatasetAttribute)
	 */
	public void copyFrom(IDatasetAttribute dsa)
	{
		defInt = dsa.getDefInt();
		defLong = dsa.getDefLong();
		defDouble = dsa.getDefDouble();
		setDefString(dsa.getDefString());
		setDefBytes(dsa.getDefBytes());
		setDefStrings(dsa.getDefStrings());
		boolAsString = dsa.isBoolAsString();
		maxClobLength = dsa.getMaxClobLength();
		maxBlobLength = dsa.getMaxBlobLength();
		setDateFormat(dsa.getDateFormat());
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDefInt()
	 */
	public int getDefInt()
	{
		return defInt;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDefInt(int)
	 */
	public void setDefInt(int defInt)
	{
		this.defInt = defInt;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDefLong()
	 */
	public long getDefLong()
	{
		return defLong;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDefLong(long)
	 */
	public void setDefLong(long defLong)
	{
		this.defLong = defLong;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDefDouble()
	 */
	public double getDefDouble()
	{
		return defDouble;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDefDouble(double)
	 */
	public void setDefDouble(double defDouble)
	{
		this.defDouble = defDouble;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDefString()
	 */
	public String getDefString()
	{
		return defString;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDefString(java.lang.String)
	 */
	public void setDefString(String defString)
	{
		if (defString == null) {
			defString = "";
		}
		this.defString = defString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDefBytes()
	 */
	public byte[] getDefBytes()
	{
		return defBytes;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDefBytes(byte[])
	 */
	public void setDefBytes(byte[] defBytes)
	{
		if (defBytes == null) {
			defBytes = new byte[0];
		}
		this.defBytes = defBytes;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDefStrings()
	 */
	public String[] getDefStrings()
	{
		return defStrings;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDefStrings(java.lang.String[])
	 */
	public void setDefStrings(String[] defStrings)
	{
		if (defStrings == null) {
			defStrings = new String[0];
		}
		this.defStrings = defStrings;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#isBoolAsString()
	 */
	public boolean isBoolAsString()
	{
		return boolAsString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setBoolAsString(boolean)
	 */
	public void setBoolAsString(boolean boolAsString)
	{
		this.boolAsString = boolAsString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getMaxClobLength()
	 */
	public int getMaxClobLength()
	{
		return maxClobLength;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setMaxClobLength(int)
	 */
	public void setMaxClobLength(int maxClobLength)
	{
		this.maxClobLength = maxClobLength;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getMaxBlobLength()
	 */
	public int getMaxBlobLength()
	{
		return maxBlobLength;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setMaxBlobLength(int)
	 */
	public void setMaxBlobLength(int maxBlobLength)
	{
		this.maxBlobLength = maxBlobLength;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getDateFormat()
	 */
	public String getDateFormat()
	{
		return dateFormat;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#setDateFormat(java.lang.String)
	 */
	public void setDateFormat(String dateFormat)
	{
		if (dateFormat == null) {
			dateFormat = "yyyy-MM-dd' 'HH:mm:ss";
		}
		this.dateFormat = dateFormat;
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetAttribute#getWorkMode()
	 */
	public int getWorkMode()
	{
		return workmode;
	}
}
