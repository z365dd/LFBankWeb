/*
 * 系统名称: 
 * 模块名称: 平台通用模块
 * 类  名  称 : CommonDataset.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */

package com.adtec.framework.impl.share.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.event.field.Field;
import com.adtec.framework.impl.share.event.field.FieldCreator;
import com.adtec.framework.impl.share.event.field.FieldValue;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasetAttribute;
import com.adtec.framework.interfaces.share.IDatasetBase;
import com.adtec.framework.interfaces.share.IDatasetMetaData;

/**
 * 数据集的通用实现类，不允许直接被new出来，而要使用 {@link DatasetService} 来获取其实例。
 * <p>
 * 另外，需要特别说明的是此实现类的所有方法都不是线程安全的。
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 * 
 * @see DatasetService
 */
public class CommonDataset implements IDataset, Cloneable {
	/** 数据集的元数据 */
	private CommonMetadata metadata = new CommonMetadata();
	/** 每一行 */
	private List<ArrayList<FieldValue>> lines = new ArrayList<ArrayList<FieldValue>>();
	/** 当前所在行的索引，行从1开始 */
	private int currentLineIndex = 1;
	/** 当前行 */
	private ArrayList<FieldValue> currentLine;
	/** 结果集的名字 */
	private String datasetName;
	/** 数据集的属性，由DatasetService派生而来 */
	private IDatasetAttribute dssa;
	/** 记录总数，与当前记录的行数无关 */
	private int totalCount = -1;
	/** 值构造器 */
	private final FieldCreator fc;
	/** 工作模式 */
	private int workMode = IDatasetBase.MODE_DEFAULT;
	/** 在构造期提供生成一个默认的域值，避免每次构造行值时都去构造一个新的 */
	private FieldValue defaultFieldValue;

	/**
	 * 被隐藏的构造函数，只有被 <code>DatasetService</code> 使用
	 * 
	 * @param dssa
	 *            数据集属性
	 */
	protected CommonDataset(IDatasetAttribute dssa) {
		this.dssa = dssa;
		fc = FieldCreator.getNewInstance(dssa);
		defaultFieldValue = fc.getFieldValue((Object) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CommonDataset clone() {
		CommonDataset newDS;
		try {
			newDS = (CommonDataset) super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
		newDS.metadata = metadata.clone();

		List<ArrayList<FieldValue>> oldLines = this.lines;
		int rowCount = oldLines.size();
		newDS.lines = new ArrayList<ArrayList<FieldValue>>(rowCount);
		List<ArrayList<FieldValue>> newLines = newDS.lines;

		for (int i = 0; i < rowCount; i++) {
			ArrayList<FieldValue> oldLine = oldLines.get(i);
			ArrayList<FieldValue> newLine = (ArrayList<FieldValue>) oldLine
					.clone();
			newLines.add(newLine);
		}

		return newDS;
	}

	public CommonDataset cloneInclude(String[] cols) {
		CommonDataset newDS = new CommonDataset(this.dssa);
		// 初始化各字段
		newDS.datasetName = datasetName;
		newDS.totalCount = totalCount;
		newDS.workMode = workMode;

		int size = cols.length;
		Integer[] indexes = new Integer[size];

		// 构造列
		int marked = 0;
		for (int i = 0; i < size; i++) {
			String colName = cols[i];
			int index = metadata.findColumn(colName);
			indexes[i] = index;
			if (index > 0) {
				marked++;
				newDS.addColumn(colName, metadata.getColumnType(index));
			} else {
				newDS.addColumn(colName);
			}
		}

		// 构造行
		int rowCount = lines.size();
		if (marked == 0 || rowCount == 0) {
			return newDS;
		} else if (rowCount <= 2 && marked <= 10) {
			for (int i = 0; i < rowCount; i++) {
				newDS.appendRow();
				final ArrayList<FieldValue> newLine = newDS.currentLine;
				final ArrayList<FieldValue> currentLine = lines.get(i);
				for (int j = 0; j < size; j++) {
					int index = indexes[j];
					if (index > 0) {
						newLine.set(j, currentLine.get(index - 1));
					}
				}
			}
		} else { // 如果列数过多则采用快速
			newDS._fastAppendRowBegin();

			for (int i = 0; i < rowCount; i++) {
				final ArrayList<FieldValue> newLine = newDS._fastAppendRow();
				final ArrayList<FieldValue> currentLine = lines.get(i);
				for (int j = 0; j < size; j++) {
					int index = indexes[j];
					if (index > 0) {
						newLine.set(j, currentLine.get(index - 1));
					}
				}
			}

			newDS._fastAppendRowEnd();
		}

		return newDS;
	}

	@SuppressWarnings("unchecked")
	public CommonDataset cloneExclude(String[] cols) {
		CommonDataset newDS = new CommonDataset(this.dssa);
		// 初始化各字段
		newDS.datasetName = datasetName;
		newDS.totalCount = totalCount;
		newDS.workMode = workMode;

		int size = cols.length;
		final Map<String, Integer> mapping = metadata.mapping;
		ArrayList<Field> fields = (ArrayList<Field>) metadata.fields.clone();

		// 筛选
		for (int i = 0; i < size; i++) {
			String colName = cols[i];
			Integer index = mapping.get(colName);
			if (index != null) {
				// marked++;
				fields.set(index, null);
			}
		}

		// 构造列
		int marked = 0;
		int pastColCount = fields.size();
		Integer[] indexes = new Integer[pastColCount];
		for (int i = 0; i < pastColCount; i++) {
			Field field = fields.get(i);
			if (field != null) {
				marked++;
				indexes[i] = i + 1;
				newDS.addColumn(field.getName(), metadata.getColumnType(i + 1));
			} else {
				indexes[i] = 0;
			}
		}

		// 构造行
		int rowCount = lines.size();
		if (marked == 0 || rowCount == 0) {
			return newDS;
		} else if (rowCount <= 2 && marked <= 10) {
			for (int i = 0; i < rowCount; i++) {
				newDS.appendRow();
				final ArrayList<FieldValue> newLine = newDS.currentLine;
				final ArrayList<FieldValue> currentLine = lines.get(i);

				int m = 0;
				for (int j = 0; j < pastColCount; j++) {
					int index = indexes[j];
					if (index > 0) {
						newLine.set(m++, currentLine.get(index - 1));
					}
				}
			}
		} else { // 如果列数过多则采用快速
			newDS._fastAppendRowBegin();

			for (int i = 0; i < rowCount; i++) {
				final ArrayList<FieldValue> newLine = newDS._fastAppendRow();
				final ArrayList<FieldValue> currentLine = lines.get(i);

				int m = 0;
				for (int j = 0; j < pastColCount; j++) {
					int index = indexes[j];
					if (index > 0) {
						newLine.set(m++, currentLine.get(index - 1));
					}
				}
			}

			newDS._fastAppendRowEnd();
		}

		return newDS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.event.IDataset#getDatasetName ()
	 */
	public String getDatasetName() {
		return datasetName;
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getByteArray(java.lang. String)
	 */
	public byte[] getByteArray(String columnName) {
		return getByteArray(columnName, dssa.getDefBytes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getDouble
	 *      (java.lang.String)
	 */
	public double getDouble(String columnName) {
		return getDouble(columnName, dssa.getDefDouble());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getInt
	 *      (java.lang.String)
	 */
	public int getInt(String columnName) {
		return getInt(columnName, dssa.getDefInt());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getLong
	 *      (java.lang.String)
	 */
	public long getLong(String columnName) {
		return getLong(columnName, dssa.getDefLong());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getString
	 *      (java.lang.String)
	 */
	public String getString(String columnName) {
		return getString(columnName, dssa.getDefString());
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getStringArray(java.lang .String)
	 */
	public String[] getStringArray(String columnName) {
		return getStringArray(columnName, dssa.getDefStrings());
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getByteArray(int)
	 */
	public byte[] getByteArray(int columnIndex) {
		return getByteArray(columnIndex, dssa.getDefBytes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getDouble
	 *      (int)
	 */
	public double getDouble(int columnIndex) {
		return getDouble(columnIndex, dssa.getDefDouble());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getInt
	 *      (int)
	 */
	public int getInt(int columnIndex) {
		return getInt(columnIndex, dssa.getDefInt());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getLong
	 *      (int)
	 */
	public long getLong(int columnIndex) {
		return getLong(columnIndex, dssa.getDefLong());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getString
	 *      (int)
	 */
	public String getString(int columnIndex) {
		return getString(columnIndex, dssa.getDefString());
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getStringArray(int)
	 */
	public String[] getStringArray(int columnIndex) {
		return getStringArray(columnIndex, dssa.getDefStrings());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetBase#getRowCount()
	 */
	public int getRowCount() {
		return lines.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#beforeFirst ()
	 */
	public void beforeFirst() {
		currentLineIndex = 0;
		currentLine = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#hasNext ()
	 */
	public boolean hasNext() {
		return currentLineIndex < lines.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#next()
	 */
	public void next() {
		currentLineIndex++;
		currentLine = lines.get(currentLineIndex - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetMetaData#findColumn
	 *      (java.lang.String)
	 */
	public int findColumn(String columnName) {
		return metadata.findColumn(columnName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetMetaData#getColumnCount ()
	 */
	public int getColumnCount() {
		return metadata.getColumnCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetMetaData#getColumnName
	 *      (int)
	 */
	public String getColumnName(int column) {
		return metadata.getColumnName(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetMetaData#getColumnType
	 *      (int)
	 */
	public char getColumnType(int column) {
		return metadata.getColumnType(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#appendRow ()
	 */
	public boolean appendRow() {
		boolean result = false;
		int size = metadata.getColumnCount();
		currentLine = new ArrayList<FieldValue>(size);
		lines.add(currentLine);

		// 初始化该行
		for (int i = 1; i <= size; i++) {
			// currentLine.add(fc.getFieldValue((Object) null)); 
			// 避免每次都构造一个新的
			currentLine.add(defaultFieldValue);
		}
		return result;
	}

	private ArrayList<FieldValue> _tmpBak;

	private void _fastAppendRowBegin() {
		int size = metadata.getColumnCount();
		_tmpBak = new ArrayList<FieldValue>(size);
		for (int i = 1; i <= size; i++) {
			// currentLine.add(fc.getFieldValue((Object) null)); // 20110418
			// HYin 避免每次都构造一个新的
			_tmpBak.add(defaultFieldValue);
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<FieldValue> _fastAppendRow() {
		currentLine = (ArrayList<FieldValue>) _tmpBak.clone();
		lines.add(currentLine);
		return currentLine;
	}

	private void _fastAppendRowEnd() {
		_tmpBak = null;
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * modifyColumnType(java.lang .String, int)
	 */
	public void modifyColumnType(String colName, int type) {
		Integer t = FieldCreator.getTypeMap().get(type);
		if (t == null) {
			throw new BaseException(SysErr.E_COLUMN_TYPE_INVALID,
					type);
		}
		int index = checkColumnName(colName, true);
		Field field = metadata.getField(index);
		field.setType((char) t.intValue());
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * modifyColumnType(int, int)
	 */
	public void modifyColumnType(int colIndex, int type) {
		Integer t = FieldCreator.getTypeMap().get(type);
		if (t == null) {
			throw new BaseException(SysErr.E_COLUMN_TYPE_INVALID,
					type);
		}
		checkColumnIndex(colIndex);

		Field field = metadata.getField(colIndex);
		field.setType((char) t.intValue());
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateByteArray(int, byte[])
	 */
	public void updateByteArray(int columnIndex, byte[] v)
			throws BaseException {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateByteArray(columnIndex, v);
	}

	private void _updateByteArray(int columnIndex, byte[] v)
			throws BaseException {
		Field field = metadata.getField(columnIndex);
		if (field.getType() != DS_BYTE_ARRAY)
			throw new BaseException(SysErr.E_NO_MESSAGE,
					"the column type and value type does not match, adjust the type to byte[]!");
		
		FieldValue value = fc.getFieldValue(v);
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateByteArray(java.lang .String, byte[])
	 */
	public void updateByteArray(String columnName, byte[] v)
			throws BaseException {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateByteArray(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateDouble(int, double)
	 */
	public void updateDouble(int columnIndex, double v)
			throws BaseException {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateDouble(columnIndex, v);
	}

	private void _updateDouble(int columnIndex, double v)
			throws BaseException {
		FieldValue value = fc.getFieldValue(v);
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateDouble(java.lang. String, double)
	 */
	public void updateDouble(String columnName, double v)
			throws BaseException {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateDouble(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#updateInt
	 *      (int, int)
	 */
	public void updateInt(int columnIndex, int v)
			throws BaseException {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateInt(columnIndex, v);
	}

	private void _updateInt(int columnIndex, int v)
			throws BaseException {
		FieldValue value = fc.getFieldValue(v);
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#updateInt
	 *      (java.lang.String, int)
	 */
	public void updateInt(String columnName, int v)
			throws BaseException {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateInt(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#updateLong
	 *      (int, long)
	 */
	public void updateLong(int columnIndex, long v)
			throws BaseException {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateLong(columnIndex, v);
	}

	private void _updateLong(int columnIndex, long v)
			throws BaseException {
		FieldValue value = fc.getFieldValue(v);
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#updateLong
	 *      (java.lang.String , long)
	 */
	public void updateLong(String columnName, long v)
			throws BaseException {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateLong(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateString(int, java.lang.String)
	 */
	public void updateString(int columnIndex, String v)
			throws BaseException {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateString(columnIndex, v);
	}

	private void _updateString(int columnIndex, String v)
			throws BaseException {
		FieldValue value = fc.getFieldValue(v);
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateString(java.lang. String, java.lang.String)
	 */
	public void updateString(String columnName, String v)
			throws BaseException {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateString(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateStringArray(int, java.lang.String[])
	 */
	public void updateStringArray(int columnIndex, String[] v)
			throws BaseException {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateStringArray(columnIndex, v);
	}

	private void _updateStringArray(int columnIndex, String[] v)
			throws BaseException {
		FieldValue value = fc.getFieldValue(v);
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#
	 * updateStringArray(java. lang.String, java.lang.String[])
	 */
	public void updateStringArray(String columnName, String[] v)
			throws BaseException {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateStringArray(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#updateValue
	 *      (int, java.lang.Object)
	 */
	public void updateValue(int columnIndex, Object v) {
		// 校验列索引
		checkColumnIndex(columnIndex);

		_updateValue(columnIndex, v);
	}

	private void _updateValue(int columnIndex, Object v) {
		Field field = metadata.getField(columnIndex);
		if (v instanceof byte[])
		if (field.getType() != DS_BYTE_ARRAY)
			throw new BaseException(SysErr.E_NO_MESSAGE,
					"the column type and value type does not match, adjust the type to byte[]!");
		FieldValue value = fc.getFieldValue(v);
		// { 20111208 HYin
		// 在设置double值的时候，如果该列类型为DS_LONG、DS_INT，则将该列自动修改为DS_DOUBLE
		int type = metadata.getColumnType(columnIndex);
		if ((type == DS_LONG || type == DS_INT) && value.getType() == DS_DOUBLE) {
			metadata.getField(columnIndex).setType(DS_DOUBLE);
		}
		// }
		currentLine.set(columnIndex - 1, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#updateValue
	 *      (java.lang.String , java.lang.Object)
	 */
	public void updateValue(String columnName, Object v) {
		// 校验列名，校验成功返回列索引，否则抛出异常
		int columnIndex = checkColumnName(columnName, true);

		_updateValue(columnIndex, v);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getByteArray(java.lang. String, byte[])
	 */
	public byte[] getByteArray(String columnName, byte[] def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			return def;
		} else {
			return _getByteArray(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getByteArray(int, byte[])
	 */
	public byte[] getByteArray(int columnIndex, byte[] def) {
		checkColumnIndex(columnIndex);

		return _getByteArray(columnIndex, def);
	}

	private byte[] _getByteArray(int columnIndex, byte[] def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getByteArray(def);
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getDouble
	 *      (java.lang.String, double)
	 */
	public double getDouble(String columnName, double def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			return def;
		} else {
			return _getDouble(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getDouble
	 *      (int, double)
	 */
	public double getDouble(int columnIndex, double def) {
		checkColumnIndex(columnIndex);

		return _getDouble(columnIndex, def);
	}

	private double _getDouble(int columnIndex, double def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getDouble(def);
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getInt
	 *      (java.lang.String, int)
	 */
	public int getInt(String columnName, int def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			return def;
		} else {
			return _getInt(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getInt
	 *      (int, int)
	 */
	public int getInt(int columnIndex, int def) {
		checkColumnIndex(columnIndex);

		return _getInt(columnIndex, def);
	}

	private int _getInt(int columnIndex, int def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getInt(def);
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getLong
	 *      (java.lang.String, long)
	 */
	public long getLong(String columnName, long def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			return def;
		} else {
			return _getLong(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getLong
	 *      (int, long)
	 */
	public long getLong(int columnIndex, long def) {
		checkColumnIndex(columnIndex);

		return _getLong(columnIndex, def);
	}

	private long _getLong(int columnIndex, long def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getLong(def);
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getString
	 *      (java.lang.String, java.lang.String)
	 */
	public String getString(String columnName, String def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			return def;
		} else {
			return _getString(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getString
	 *      (int, java.lang.String)
	 */
	public String getString(int columnIndex, String def) {
		checkColumnIndex(columnIndex);

		return _getString(columnIndex, def);
	}

	private String _getString(int columnIndex, String def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getString(def);
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getStringArray(java.lang .String, java.lang.String[])
	 */
	public String[] getStringArray(String columnName, String[] def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			return def;
		} else {
			return _getStringArray(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seecom.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#
	 * getStringArray(int, java.lang.String[])
	 */
	public String[] getStringArray(int columnIndex, String[] def) {
		checkColumnIndex(columnIndex);

		return _getStringArray(columnIndex, def);
	}

	private String[] _getStringArray(int columnIndex, String[] def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getStringArray(def);
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getValue
	 *      (java.lang.String)
	 */
	public Object getValue(String columnName) {
		return getValue(columnName, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getValue
	 *      (int)
	 */
	public Object getValue(int columnIndex) {
		return getValue(columnIndex, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getValue
	 *      (java.lang.String, java.lang.String[])
	 */
	public Object getValue(String columnName, Object def) {
		int index = checkColumnName(columnName, isExceptionMode());
		if (index == 0) {
			// { 20120111 HYin 修改返回默认值的方式
			// return dssa.getDefLong();
			return def;
			// }
		} else {
			return _getValue(index, def);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader#getValue
	 *      (int, java.lang.String[])
	 */
	public Object getValue(int columnIndex, Object def) {
		checkColumnIndex(columnIndex);

		return _getValue(columnIndex, def);
	}

	private Object _getValue(int columnIndex, Object def) {
		FieldValue value = currentLine.get(columnIndex - 1);
		try {
			return value.getValue();
		} catch (BaseException e) {
			if (isExceptionMode()) {
				throw e;
			} else {
				return def;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter
	 *      #addColumn(java.lang.String, char)
	 */
	public void addColumn(String colName, int type) {
		Field field = fc.getField(colName, type);
		int realIndex = metadata.addField(field);

		// 为所有行添加一列
		int size = lines.size();
		if (size > 0) {
			List<FieldValue> vs = lines.get(0);
			if (realIndex > vs.size()) { // 如果是添加列而不是更新某列
				for (int i = 0; i < size; i++) {
					vs = lines.get(i);
					FieldValue fv = fc.getDefaultValue(field.getType());
					vs.add(fv);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.writer.IResultSetWriter#addColumn
	 *      (java.lang.String)
	 */
	public void addColumn(String colName) {
		addColumn(colName, DatasetColumnType.DS_STRING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetBase#getMode()
	 */
	public int getMode() {
		return workMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasetBase#setMode(int)
	 */
	public void setMode(int mode) {
		if (IDatasetBase.MODE_EXCEPTION == mode) {
			workMode = mode;
		} else {
			workMode = IDatasetBase.MODE_DEFAULT;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#setDatasetName(java
	 *      .lang.String)
	 */
	public void setDatasetName(String name) {
		datasetName = name;
	}

	/**
	 * 获取数据集属性
	 * 
	 * @return 当前数据集属性
	 */
	public IDatasetAttribute getDatasetAttribute() {
		return dssa;
	}

	/**
	 * 设置数据集属性
	 * 
	 * @param dssa
	 *            数据集属性
	 * @return 修改之前的数据集属性
	 */
	public IDatasetAttribute setDatasetAttribute(IDatasetAttribute dssa) {
		if (dssa == null) {
			return null;
		}
		IDatasetAttribute pre = dssa;
		this.dssa.copyFrom(dssa);
		return pre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#clear()
	 */
	public void clear() {
		lines.clear();
		currentLineIndex = 1;
		currentLine = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#clearAll()
	 */
	public void clearAll() {
		metadata.clear();
		lines.clear();
		currentLineIndex = 1;
		currentLine = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.reader.IResultSetReader
	 *      #locateLine(int)
	 */
	public void locateLine(int lineIndex) {
		if (!locateRow(lineIndex)) {
			throw new BaseException(
					SysErr.E_LINE_INDEX_OUT_OF_BOUND, lineIndex, 1, lines
							.size());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#getTotalCount()
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#setTotalCount(int)
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 该数据集是否是异常模式
	 * 
	 * @return true 是异常模式，在此模式下获取数据失败抛出异常；false，默认模式，在此模式下获取数据失败返回默认值。
	 */
	protected boolean isExceptionMode() {
		return IDatasetBase.MODE_EXCEPTION == getMode();
	}

	/**
	 * 修改行指针，定位到某一行
	 * 
	 * @param rowIndex
	 *            行索引，从1开始
	 * @return 定位成功返回true，否则返回false
	 */
	protected boolean locateRow(int rowIndex) {
		if (rowIndex >= 1 && rowIndex <= getRowCount()) {
			currentLineIndex = rowIndex;
			currentLine = lines.get(currentLineIndex - 1);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验列名是否存在，如果存在则返回列索引，否则根据 bException判断要不要抛出异常，如果不补抛出异常返回 0
	 * 
	 * @param columnName
	 *            列名
	 * @param bException
	 *            如果为true，在列名不存在的情况下，抛出异常，否则不抛出异常
	 * @return 如果列名存在则返回列索引，否则 在bException为false的情况下返回 0
	 */
	protected int checkColumnName(String columnName, boolean bException) {
		int index = metadata.findColumn(columnName);
		if (index == 0) {
			if (bException) {
				throw new BaseException(
						SysErr.E_COLUMN_NAME_NOT_EXSITED, columnName);
			}
		}
		return index;
	}

	/**
	 * 校验列索引是否有效，如果无效抛出BaseException异常
	 * 
	 * @param columnIndex
	 *            列索引
	 */
	protected void checkColumnIndex(int columnIndex) {
		if (!metadata.isValidColumnIndex(columnIndex)) {
			throw new BaseException(
					SysErr.E_COLUMN_INDEX_OUT_OF_BOUND, columnIndex, 1,
					metadata.getColumnCount());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#getMetaData()
	 */
	public IDatasetMetaData getMetaData() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.share.dataset.IDataset#deleteRow(int)
	 */
	public void deleteRow(int rowIndex) {
		checkRowIndex(rowIndex);
		lines.remove(rowIndex - 1);
	}

	private void checkRowIndex(int rowIndex) {
		int rowCount = lines.size();
		if (rowIndex >= 1 && rowIndex <= rowCount) {
			return;
		} else {
			throw new BaseException(
					SysErr.E_LINE_INDEX_OUT_OF_BOUND, rowIndex, 1, rowCount);
		}
	}

}
