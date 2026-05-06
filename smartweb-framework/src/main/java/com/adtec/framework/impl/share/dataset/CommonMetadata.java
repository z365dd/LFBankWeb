/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: CommonMetadata.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.adtec.framework.impl.share.event.field.Field;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDatasetMetaData;

/**
 * 数据集的元数据，按照索引查找字段信息快，按照名字查找字段信息慢。另外，索引从 <code>1</code> 开始 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class CommonMetadata implements IDatasetMetaData, Cloneable
{
	/** 域列表 */
	protected ArrayList<Field>		fields	= new ArrayList<Field>();
	/** 域名与索引，索引与域列表中的索引一致，从0开始 */
	protected Map<String, Integer>	mapping	= new HashMap<String, Integer>();

	/**
	 * 添加一个域
	 * @param field
	 *            域实例
	 */
	public int addField(Field field)
	{
		// {  支持同名列
		fields.add(field);
		int index = findColumn(field.getName());
		if (index == 0) {
			mapping.put(field.getName(), fields.size() - 1);
		}

		return fields.size();
		// }

		// int index = findColumn(field.getName());
		// if (index != 0) {
		// fields.set(index - 1, field);
		// mapping.put(field.getName(), index - 1);
		// return index;
		// } else {
		// fields.add(field);
		// index = fields.size();
		// mapping.put(field.getName(), index - 1);
		// return index;
		// }
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.cep.event.IDatasetMetaData#findColumn(java
	 * .lang.String)
	 */
	public int findColumn(String columnName)
	{
		int index = 0;
		if (columnName == null || columnName.length() == 0) {
			return index;
		}

		Integer it = mapping.get(columnName);
		if (it == null) {
			index = 0;
		} else {
			index = it + 1;
		}

		return index;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.cep.event.IDatasetMetaData#getColumnCount()
	 */
	public int getColumnCount()
	{
		return fields.size();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.cep.event.IDatasetMetaData#getColumnName(int)
	 */
	public String getColumnName(int column)
	{
		if (!isValidColumnIndex(column)) {
			return null;
		} else {
			return fields.get(column - 1).getName();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.adtec.framework.interfaces.cep.event.IDatasetMetaData#getColumnType(int)
	 */
	public char getColumnType(int column)
	{
		if (!isValidColumnIndex(column)) {
			return DatasetColumnType.DS_UNKNOWN;
		} else {
			return fields.get(column - 1).getType();
		}
	}

	/**
	 * 判断该列是否有效
	 * @param column
	 *            列编号
	 * @return <code>column <= 0 || column > fields.size()</code>
	 *         返回false，否则返回true
	 */
	public boolean isValidColumnIndex(int column)
	{
		return column > 0 && column <= fields.size();
	}

	public Field getField(int column)
	{
		if (isValidColumnIndex(column)) {
			return fields.get(column - 1);
		} else {
			return null;
		}
	}

	protected void clear()
	{
		fields.clear();
		mapping.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CommonMetadata clone()
	{
		CommonMetadata other;
		try {
			other = (CommonMetadata) super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
		other.mapping = new HashMap<String, Integer>(mapping.size());
		other.mapping.putAll(mapping);

		int size = fields.size();
		other.fields = new ArrayList<Field>(size);
		ArrayList<Field> newFields = other.fields;
		ArrayList<Field> oldFields = this.fields;
		for (int i = 0; i < size; i++) {
			newFields.add(oldFields.get(i).clone());
		}
		return other;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final ArrayList<Field> oldFields = this.fields;
		int size = oldFields.size();
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		for (int i = 0; i < size; i++) {
			sb.append(oldFields.get(i).toString());
			if (i != size - 1) {
				sb.append(",");
			}
		}

		sb.append("}");
		return sb.toString();
	}

}
