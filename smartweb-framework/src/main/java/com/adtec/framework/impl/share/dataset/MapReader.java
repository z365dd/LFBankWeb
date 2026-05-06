/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: MapReader.java
 * 软件版权: 
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */

package com.adtec.framework.impl.share.dataset;

import com.adtec.framework.impl.share.event.field.FieldCreator;
import com.adtec.framework.impl.share.event.field.FieldValue;
import com.adtec.framework.impl.share.util.IndexMap;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasetAttribute;

/**
 * 功能说明:
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-12-23 <br>
 */
public class MapReader
{
	/** 要操纵的数据集 */
	private IDataset				dataset	= null;
	/** 值集合 */
	private IndexMap<FieldValue>	values	= new IndexMap<FieldValue>();
	private FieldCreator			fc;
	private IDatasetAttribute		dsa;

	public MapReader(IDataset ds)
	{
		dataset = ds;

		dsa = DatasetService.getDefaultDatasetAttribute();
		fc = FieldCreator.getNewInstance(DatasetService.getDefaultDatasetAttribute());
		// 将IDataset转换为Map
		trans2Map();
	}

	protected void trans2Map()
	{
		if (dataset != null && dataset.getRowCount() > 0) {
			dataset.locateLine(1);
			int colCount = dataset.getColumnCount();
			for (int i = 1; i <= colCount; i++) {
				String colName = dataset.getColumnName(i);
				Object value = dataset.getValue(i);
				FieldValue fv = fc.getFieldValue(value);
				values.put(colName, fv);
			}
		}
	}

	public int getInt(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getInt();
		} else {
			return dsa.getDefInt();
		}
	}

	public int getInt(String columnName, int def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			try {
				return fv.getInt(def);
			} catch (Exception e) {
				System.out.println("无须处理");
			}
		}
		return def;
	}

	public long getLong(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getLong();
		} else {
			return dsa.getDefLong();
		}
	}

	public long getLong(String columnName, long def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			try {
				return fv.getLong(def);
			} catch (Exception e) {
				System.out.println("操作失败");
			}
		}
		return def;
	}

	public double getDouble(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getDouble();
		} else {
			return dsa.getDefDouble();
		}
	}

	public double getDouble(String columnName, double def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			try {
				return fv.getDouble(def);
			} catch (Exception e) {
				// 不需要抛出异常
				System.out.println("不需要抛出异常");
			}
		}
		return def;
	}

	/**
	 * 以 Java 编程语言中 byte 数组的形式检索此 DataSet 对象的当前行中指定列的值。这些字节表示驱动程序返回的原始值。
	 * @param columnName
	 *            列的名称
	 * @return 列值，如果获取失败，并且Dataset的模式为MODE_DEFAULT的情况下，则会返回默认值
	 *             指针不在有效行上或者columnName列名不存在，只有在模式为 IDataset.MODE_EXCEPTION
	 *             的时候才会抛出异常
	 */
	public byte[] getByteArray(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getByteArray();
		} else {
			return dsa.getDefBytes();
		}
	}

	public byte[] getByteArray(String columnName, byte[] def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			try {
				return fv.getByteArray(def);
			} catch (Exception e) {
				// 不需要处理的异常
				System.out.println("不需要处理的异常");
			}
		}
		return def;
	}

	public String getString(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getString();
		} else {
			return dsa.getDefString();
		}
	}

	public String getString(String columnName, String def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			try {
				return fv.getString(def);
			} catch (Exception e) {
				// 不需要处理的异常
				System.out.println("不需要处理的异常");
			}
		}
		return def;
	}

	public String[] getStringArray(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getStringArray();
		} else {
			return dsa.getDefStrings();
		}
	}

	public String[] getStringArray(String columnName, String[] def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			try {
				return fv.getStringArray(def);
			} catch (Exception e) {
				// 不需要处理的异常
				System.out.println("不需要处理的异常");
			}
		}
		return def;
	}

	public Object getValue(String columnName)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getValue();
		} else {
			return null;
		}
	}

	public Object getValue(String columnName, Object def)
	{
		FieldValue fv = values.get(columnName);
		if (fv != null) {
			return fv.getValue();
		} else {
			return def;
		}
	}

	// public static void main(String[] args)
	// {
	// Map<String, Object> vs = new HashMap<String, Object>();
	// vs.put("1", 11);
	// vs.put("2", "22");
	// vs.put("3", 33.3);
	// vs.put("4", new byte[] { '4', '4', '4', '4' });
	// vs.put("5", new String[] { "5", "5", "5", "5" });
	// vs.put("5", new Character('A'));
	//
	// IDataset ds = DatasetService.getDefaultInstance().getDataset(vs);

	// MapWriter mw = new MapWriter();
	// mw.put("1", 11);
	// mw.put("2", "22");
	// mw.put("3", 33.3);
	// mw.put("4", new byte[] { '4', '4', '4', '4' });
	// mw.put("5", new String[] { "5", "5", "5", "5" });
	// mw.put("6", new Character('A'));
	//
	// IDataset ds = mw.getDataset();
	// MapReader mr = new MapReader(ds);
	//
	// System.out.println(mr.getString("1"));
	// System.out.println(mr.getString("2"));
	// System.out.println(mr.getString("3"));
	// System.out.println(mr.getString("4"));
	// System.out.println(mr.getStringArray("5"));
	// System.out.println(mr.getString("6"));
	//
	// System.out.println(mr.getInt("3"));
	// System.out.println(mr.getDouble("7", 333));
	//
	// Object va = new Character('A');
	// System.out.println(va.toString());
	// }
}
