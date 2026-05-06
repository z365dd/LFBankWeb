/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: DataSetUtil.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明<BR>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.adtec.framework.impl.dbspring.dataset;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.adtec.framework.impl.dbspring.session.DBSessionFactory4Spring;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.dialect.IDialect;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;

/**
 * @author chenyl
 * 
 */
public class DataSetUtil {
	private static final String IGNORE_COLUMN_NAME = "ignore_db_rownum_";

	private static IDialect dialect = null;

	public static void setDialect(IDialect dialect) {
		DataSetUtil.dialect = dialect;
	}

	public static IDataset getCopyDataset(ResultSet rs) {
		IDataset dataSet = DatasetService.getDefaultInstance().getDataset();
		Map<String, Integer> columnViewnum = new HashMap<String, Integer>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			Map<Integer, List<Object>> unknowTypes = new HashMap<Integer, List<Object>>();
			// 同名列处理
			int ignoreColumnNumber = synColumnProcess(columnViewnum, metaData,
					dataSet, unknowTypes);
			while (rs.next()) {
				dataSet.appendRow();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					if (i != ignoreColumnNumber)
						updateValue(rs, dataSet, metaData, i, unknowTypes);
				}
			}
			// 20120427 修改未知类型
			updateUnKnowType(unknowTypes, dataSet);

		} catch (Exception e) {
			DBSessionFactory4Spring.getLog().error("拷贝Dataset异常", e);
		}
		return dataSet;
	}

	/**
	 * 
	 * 根据值修改未知类型的列类型
	 * 
	 * @param unknowTypes
	 * @param dataSet
	 */
	private static void updateUnKnowType(
			Map<Integer, List<Object>> unknowTypes, IDataset dataSet) {
		for (Entry<Integer, List<Object>> entries : unknowTypes.entrySet()) {
			if (entries != null) {
				int index = entries.getKey();
				List<Object> values = entries.getValue();
				if (values != null) {
					for (int i = 0; i < values.size(); i++) {
						Object value = values.get(i);
						if (value != null) {
							if (value.toString().indexOf(".") > 0) {
								dataSet.modifyColumnType(index,
										DatasetColumnType.DS_DOUBLE);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 同名列处理
	 * 
	 * @param ignoreColumnNumber
	 *            无效列的行数
	 * @param columnViewnum
	 *            列名与同名列的个数映射
	 * @param metaData
	 *            ResultSet 对象中列的类型和属性信息的对象
	 * @param dataSet
	 * @throws SQLException
	 */
	private static int synColumnProcess(Map<String, Integer> columnViewnum,
			ResultSetMetaData metaData, IDataset dataSet,
			Map<Integer, List<Object>> unknowTypes) throws SQLException {
		int ignoreColumnNumber = 0;
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			// 针对同名列添加后缀
			String postfix = "";
			if (!columnViewnum.containsKey(metaData.getColumnLabel(i)))
				columnViewnum.put(metaData.getColumnLabel(i), 1);
			else {
				postfix = columnViewnum.get(metaData.getColumnLabel(i))
						.toString();
				columnViewnum.put(metaData.getColumnLabel(i), Integer
						.parseInt(postfix) + 1);
			}
			if (!postfix.equals(""))
				postfix = "_" + postfix;
			// 名称转成小写

			if (metaData.getColumnLabel(i).toLowerCase().equals(
					IGNORE_COLUMN_NAME))
				ignoreColumnNumber = i;
			else
				dataSet.addColumn(metaData.getColumnLabel(i).toLowerCase()
						+ postfix, translate(i, metaData, unknowTypes));
		}
		return ignoreColumnNumber;
	}

	/**
	 * 返回第一条记录转换成IDataset
	 * 
	 * @param rs
	 * @return
	 */
	public static IDataset getCopyFirstDataset(ResultSet rs) {
		IDataset dataSet = DatasetService.getDefaultInstance().getDataset();
		Map<String, Integer> columnViewnum = new HashMap<String, Integer>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			Map<Integer, List<Object>> unknowTypes = new HashMap<Integer, List<Object>>();
			// 同名列处理
			synColumnProcess(columnViewnum, metaData, dataSet, unknowTypes);

			if (rs.next()) {
				dataSet.appendRow();
				for (int i = 1; i <= metaData.getColumnCount(); ++i)
					updateValue(rs, dataSet, metaData, i, unknowTypes);
			}
			// 20120427 修改未知类型
			updateUnKnowType(unknowTypes, dataSet);
		} catch (Exception e) {
			DBSessionFactory4Spring.getLog()
					.error("返回第一条记录转换成IDataset异常", e);
		}

		return dataSet;
	}

	/**
	 * 指定的开始和结束的条数，转换成IDataset
	 * 
	 * @param start
	 * @param limit
	 * @param rs
	 * @return
	 */
	public static IDataset getCopyLimitDataset(int start, int limit,
			ResultSet rs) {
		if (start == 0)
			start = 1;
		IDataset dataSet = DatasetService.getDefaultInstance().getDataset();
		Map<String, Integer> columnViewnum = new HashMap<String, Integer>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			Map<Integer, List<Object>> unknowTypes = new HashMap<Integer, List<Object>>();
			// 同名列处理
			int ignoreColumnNumber = synColumnProcess(columnViewnum, metaData,
					dataSet, unknowTypes);
			// 获得指定区间的记录
			int recordNum = 0;
			// MODIFIED BY JYH 如果采用游标分页，游标模式为非双向滚动模式，自行进行next计算
			// ADDED BY JYH 2010-10-13 BEGIN
			if (rs.getType() == ResultSet.TYPE_FORWARD_ONLY) {
				int count = 0;
				// 1.游标位置定位
				while (rs.next()) {
					count++;
					if (count == start) {
						break;
					}
				}
				if (count == start) { // 绝对定位成功 ，记录数足够则提取分页需要的记录数
					do {
						dataSet.appendRow();
						for (int i = 1; i <= metaData.getColumnCount(); ++i) {
							if (i != ignoreColumnNumber)
								updateValue(rs, dataSet, metaData, i,
										unknowTypes);
						}
					} while ((rs.next()) && (++recordNum < limit));
				}
			} else
			// ADDED BY JYH 2010-10-13 END
			{
				if (rs.absolute(start))
					do {
						dataSet.appendRow();
						for (int i = 1; i <= metaData.getColumnCount(); ++i) {
							if (i != ignoreColumnNumber)
								updateValue(rs, dataSet, metaData, i,
										unknowTypes);
						}
					} while ((rs.next()) && (++recordNum < limit));
			}
			// 20120427 修改未知类型
			updateUnKnowType(unknowTypes, dataSet);

		} catch (Exception e) {
			DBSessionFactory4Spring.getLog()
					.error("指定的开始和结束的条数，转换成IDataset异常", e);
		}

		return dataSet;
	}

	/**
	 * 类型翻译
	 * 
	 * @param type
	 * @param scale
	 * @param precision
	 * @return
	 * @throws SQLException
	 */
	private static char translate(int index, ResultSetMetaData metaData,
			Map<Integer, List<Object>> unknowTypes) throws SQLException {
		// char translatedType = DatasetColumnType.DS_UNKNOWN;
		int type = metaData.getColumnType(index);
		int scale = metaData.getScale(index);
		int precision = metaData.getPrecision(index);
		char translatedType = DatasetColumnType.DS_STRING;
		switch (type) {
		case Types.ARRAY:
		case Types.VARCHAR:
		case Types.CLOB:
		case Types.CHAR:
			translatedType = DatasetColumnType.DS_STRING;
			break;
		case Types.BIGINT:
			translatedType = DatasetColumnType.DS_LONG;
			break;
		case Types.DECIMAL:
		case Types.NUMERIC:
		case Types.DOUBLE:
		case Types.FLOAT:
			if (scale == 0 || scale == -127) {
				if (precision > 0 && precision < 10) {// TODO
					translatedType = DatasetColumnType.DS_INT;
				} else if (precision >= 10) {
					translatedType = DatasetColumnType.DS_LONG;
				} else if (precision == 0) {
					// 默认视为long类型
					translatedType = DatasetColumnType.DS_LONG;
					// 记住未知类型的编号
					unknowTypes.put(index, new ArrayList<Object>());
				} else {
					translatedType = DatasetColumnType.DS_DOUBLE;
				}
			} else {
				translatedType = DatasetColumnType.DS_DOUBLE;
			}
			break;

		case Types.REAL:
			translatedType = DatasetColumnType.DS_DOUBLE;
			break;
		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
			translatedType = DatasetColumnType.DS_INT;
			break;
		case Types.BLOB:
			translatedType = DatasetColumnType.DS_BYTE_ARRAY;
			break;
		}
		return translatedType;
	}

	/**
	 * 更新dataSet中的值
	 * 
	 * @param rs
	 * @param dataSet
	 * @param type
	 * @param columnIndex
	 * @param unknowTypes
	 */
	private static void updateValue(ResultSet rs, IDataset dataSet,
			ResultSetMetaData metaData, int columnIndex,
			Map<Integer, List<Object>> unknowTypes) {
		try {
			Object data = null;
			int type = metaData.getColumnType(columnIndex);
			if (type == java.sql.Types.BLOB) {
				java.sql.Blob blob = rs.getBlob(columnIndex);
				if (blob != null)
					data = blob.getBytes(1, (int) blob.length());
			} else if (type == java.sql.Types.LONGVARBINARY
					|| type == java.sql.Types.VARBINARY
					|| type == java.sql.Types.BINARY) {
				data = rs.getBytes(columnIndex);
			} else
				data = rs.getObject(columnIndex);
			// 20120515 精度缺失修改
			int scale=metaData.getScale(columnIndex);
			int columnType = dataSet.getColumnType(columnIndex);
			switch (columnType) {// 20120405 由之前根据resultset的类型判断
			// 修改为根据dataset的列类型进行赋值
			case DatasetColumnType.DS_INT:
				dataSet.updateInt(columnIndex, rs.getInt(columnIndex));
				break;
			case DatasetColumnType.DS_LONG:
				if (unknowTypes.containsKey(columnIndex)) {
					Object value = rs.getObject(columnIndex);
					unknowTypes.get(columnIndex).add(value);
					dataSet.updateValue(columnIndex, value);
				} else {
					dataSet.updateLong(columnIndex, rs.getLong(columnIndex));
				}
				break;
			case DatasetColumnType.DS_DOUBLE:
				// 20120515 精度缺失修改
				double postData=round(rs.getDouble(columnIndex),scale);
				dataSet.updateDouble(columnIndex, postData);
				break;
			default:
				dataSet.updateValue(columnIndex, data);
			}

		} catch (Exception e) {
			DBSessionFactory4Spring.getLog().error("更新dataSet中的值异常", e);
		}
	}

	/**
	 * 
	 * 对于有精度的数据采用四舍五入
	 * @param v
	 * @param scale
	 * @return
	 */
	private static double round(double v, int scale) {

		if (scale > 0) {

			BigDecimal b = new BigDecimal(Double.toString(v));

			BigDecimal one = new BigDecimal("1");

			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		}
		return v;

	}
	
}
