/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: MapWriter.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 */

package com.adtec.framework.impl.share.dataset;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.event.field.FieldCreator;
import com.adtec.framework.impl.share.event.field.FieldValue;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasetAttribute;
import com.adtec.framework.interfaces.share.writer.IMapWriter;

/**
 * 一个java的Map形式的数据集构造器，可以构造一个拥有一条记录集的数据集
 *<p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class MapWriter implements IMapWriter
{
	/** 数据集 */
	private IDataset		ds;
	/** 与本数据集相关的域值构造器 */
	private FieldCreator	fc;

	/**
	 * 构造一个默认的数据集构造器，{@link IDataset} 和 {@link IDatasetAttribute} 都采用系统默认的
	 */
	public MapWriter()
	{
		this(DatasetService.getDefaultInstance().getDataset(), DatasetService
				.getDefaultDatasetAttribute());
	}

	/**
	 * 生成指定数据集的构造器
	 * @param ds
	 *            数据集
	 */
	public MapWriter(IDataset ds)
	{
		this(ds, DatasetService.getDefaultDatasetAttribute());
	}

	/**
	 * 生成指定数据集和数据集属性的构造器
	 * @param ds
	 *            数据集
	 * @param dssa
	 *            数据集属性
	 */
	public MapWriter(IDataset ds, IDatasetAttribute dssa)
	{
		if (ds == null) {
			throw new BaseException(SysErr.E_NULL_POINTER, "dataset is null");
		}
		this.ds = ds;
		fc = FieldCreator.getNewInstance(dssa);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, int)
	 */
	public void put(String name, int value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateInt(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, long)
	 */
	public void put(String name, long value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateLong(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, double)
	 */
	public void put(String name, double value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateDouble(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, java.lang.String)
	 */
	public void put(String name, String value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateString(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, byte[])
	 */
	public void put(String name, byte[] value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateByteArray(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, java.lang.String[])
	 */
	public void put(String name, String[] value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateStringArray(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * put(java.lang.String, java.lang.Object)
	 */
	public void put(String name, Object value)
	{
		if (ds.getRowCount() == 0) {
			ds.appendRow();
		}
		ds.locateLine(1);

		FieldValue fv = fc.getFieldValue(value);
		if (ds.findColumn(name) == 0) {
			ds.addColumn(name, fv.getType());
		}
		ds.updateValue(name, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IMapWriter#
	 * getDataset()
	 */
	public IDataset getDataset()
	{
		return ds;
	}

}
