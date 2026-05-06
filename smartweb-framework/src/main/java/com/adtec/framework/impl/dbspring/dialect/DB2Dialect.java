/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: DB2Dialect.java
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
package com.adtec.framework.impl.dbspring.dialect;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.apache.curator.shaded.com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.impl.dbspring.storedProcedure.StoredProcedureUtil;
import com.adtec.framework.interfaces.db.dialect.IDialect;
import com.adtec.framework.interfaces.db.storedProcedure.ProcedureParameter;

// TODO: Auto-generated Javadoc
/**
 * The Class DB2Dialect.
 * 
 * @author chenyl
 */
public class DB2Dialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(DB2Dialect.class);
	/**
	 * DB2的关键字和保留字
	 */
	public final static Set<String> RESERVED_WORDS = Sets.newHashSet();
	static{
		RESERVED_WORDS.add("DETERMINISTIC");
		RESERVED_WORDS.add("DISALLOW");
		RESERVED_WORDS.add("DISCONNECT");
		RESERVED_WORDS.add("DISTINCT");
		RESERVED_WORDS.add("DO");
		RESERVED_WORDS.add("DOUBLE");
		RESERVED_WORDS.add("DROP");
		RESERVED_WORDS.add("DSNHATTR");
		RESERVED_WORDS.add("DSSIZE");
		RESERVED_WORDS.add("DYNAMIC");
		RESERVED_WORDS.add("EACH");
		RESERVED_WORDS.add("EDITPROC");
		RESERVED_WORDS.add("ELSE");
		RESERVED_WORDS.add("ELSEIF");
		RESERVED_WORDS.add("ENCODING");
		RESERVED_WORDS.add("END");
		RESERVED_WORDS.add("END-EXEC");
		RESERVED_WORDS.add("END-EXEC1");
		RESERVED_WORDS.add("ERASE");
		RESERVED_WORDS.add("ESCAPE");
		RESERVED_WORDS.add("EXCEPT");
		RESERVED_WORDS.add("EXCEPTION");
		RESERVED_WORDS.add("EXCLUDING");
		RESERVED_WORDS.add("EXECUTE");
		RESERVED_WORDS.add("EXISTS");
		RESERVED_WORDS.add("EXIT");
		RESERVED_WORDS.add("EXTERNAL");
		RESERVED_WORDS.add("FENCED");
		RESERVED_WORDS.add("FETCH");
		RESERVED_WORDS.add("FIELDPROC");
		RESERVED_WORDS.add("FILE");
		RESERVED_WORDS.add("FINAL");
		RESERVED_WORDS.add("FOR");
		RESERVED_WORDS.add("FOREIGN");
		RESERVED_WORDS.add("FREE");
		RESERVED_WORDS.add("FROM");
		RESERVED_WORDS.add("FULL");
		RESERVED_WORDS.add("FUNCTION");
		RESERVED_WORDS.add("GENERAL");
		RESERVED_WORDS.add("GENERATED");
		RESERVED_WORDS.add("GET");
		RESERVED_WORDS.add("GLOBAL");
		RESERVED_WORDS.add("GO");
		RESERVED_WORDS.add("GOTO");
		RESERVED_WORDS.add("GRANT");
		RESERVED_WORDS.add("GRAPHIC");
		RESERVED_WORDS.add("GROUP");
		RESERVED_WORDS.add("HANDLER");
		RESERVED_WORDS.add("HAVING");
	}
	/*
	 * (non-Javadoc) 约定与表名绑定的sequence名称为"seq_default"
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql()
	 */
	public String getAutoIncreaseKeySql() {
		// TODO Auto-generated method stub
		return "select nextval for seq_default from sysibm.sysdummy1";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql
	 * (java.lang.String)
	 */
	public String getAutoIncreaseKeySql(String tableName) {
		// TODO Auto-generated method stub
		return "select nextval for " + DialectUtil.getSeqName(tableName)
				+ " from sysibm.sysdummy1";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getLimitString(java.lang
	 * .String, int, int)
	 */
	public String getLimitString(String sql, int offset, int limit) {
		String tempSql = getCloneSql(sql).toLowerCase();

		// 如果含有union语句进行特殊处理 sql语句转换成子查询
		if (sql.indexOf("union") != -1) {
			sql = "select temp_select.* from (" + sql + ") temp_select "; // sql
			// 语句进行转换
		}
		int startOfSelect = tempSql.indexOf("select");
		boolean hasOffset = false;
		if (offset >= 1) {
			hasOffset = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
				.append(sql.substring(0, startOfSelect)) // add the comment
				.append("select * from ( select ") // nest the main query in an
				// outer select
				.append(getRowNumber(sql)); // add the rownnumber bit into the
		// outer query select list
		if (hasDistinct(tempSql)) {
			pagingSelect.append(" row_.* from ( ") // add another (inner)
					// nested
					// select
					.append(sql.substring(startOfSelect)) // add the main
					// query
					.append(" ) as row_"); // close off the inner nested select
		} else {
			pagingSelect
					.append(("select t_.* from(" + addOrderByToField(sql) + ")as t_")
							.substring(startOfSelect + 6)); // add the
			// main
			// query
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		// add the restriction to the outer select
		if (hasOffset) {
			// pagingSelect.append("between ?+1 and ?");
			pagingSelect.append("between " + (offset) + " and "
					+ (offset + limit - 1));
		} else {
			pagingSelect.append("<= " + (offset + limit - 1));
		}

		return pagingSelect.toString();
	}

	/**
	 * 如果分页查询的sql有排序条件，需要将排序字段也作为查询的输出字段，以便于在sql的OVER函数中调用
	 * 
	 * @param sql
	 * @return
	 */
	private String addOrderByToField(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		if (tempSql.indexOf("union") != -1)
			return sql;

		int orberBy = tempSql.lastIndexOf("order by");
		if (orberBy > 0) {
			int select = tempSql.indexOf("select");
			int from = tempSql.indexOf("from");

			String fields = sql.substring(select + 6, from - 1);

			if (fields.trim().equals(""))
				return sql;
			String fieldArray[] = fields.split(",");
			for (String field : fieldArray) {
				if (field.trim().equals("*"))
					return sql;
			}

			StringBuilder fileStr = new StringBuilder(fields);

			String param = sql.substring(orberBy + 9);
			int desc = param.toLowerCase().indexOf("desc");
			int asc = param.toLowerCase().indexOf("asc");
			if (desc > 0)
				param = param.substring(0, desc - 1);
			if (asc > 0)
				param = param.substring(0, asc - 1);

			if (param != null && param.trim().length() > 0) {
				String[] paraArray = param.split(",");
				for (String para : paraArray) {

					int schemaIdx = para.indexOf(".");
					if (schemaIdx > 0) {
						String schema = para.substring(0, schemaIdx);
						if (fields.indexOf(schema + ".*") != -1)
							continue;
					}

					if (fields.indexOf(para.trim()) == -1)
						fileStr.append(", ").append(para);
				}
			}

			if (fileStr.length() > fields.length()) {
				StringBuilder sqlBuilder = new StringBuilder();
				sqlBuilder.append(sql.substring(0, select)).append("select ")
						.append(fileStr.toString()).append(" ").append(
								sql.substring(from));
				return sqlBuilder.toString();
			}
		}

		return sql;
	}

	/**
	 * Gets the row number.
	 * 
	 * @param sql
	 *            the sql
	 * @return the row number
	 */
	private String getRowNumber(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		StringBuffer rownumber = new StringBuffer(50)
				.append("rownumber() over(");
		if (tempSql.indexOf("union") == -1) { // 没有union情况下

			int orderByIndex = tempSql.lastIndexOf("order by");

			if (orderByIndex > 0 && !hasDistinct(tempSql)) {
				rownumber.append("order by ").append(
						replaceSchema(sql.substring(orderByIndex + 9), "t_"));
			}

		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	/**
	 * 替换sql中字段的schema为指定的sche
	 * 
	 * @param sql
	 * @param sche
	 * @return
	 */
	private String replaceSchema(String sql, String sche) {
		StringBuilder _param = new StringBuilder();
		if (sql != null && sql.trim().length() > 0) {

			String[] paramArray = sql.split(",");
			for (String param : paramArray) {
				int i = param.indexOf(".");
				if (sche != null && sche.trim().length() > 0) {
					_param.append(sche).append(".");
				}
				if (i > 0) {
					_param.append(param.substring(i + 1));
				} else {
					_param.append(param.trim());
				}

				_param.append(",");
				i = -1;
			}

			if (_param.length() > 1)
				return _param.substring(0, _param.length() - 1);
		}

		return _param.toString();
	}

	/**
	 * 生成克隆sql.
	 * 
	 * @param sql
	 *            the sql
	 * @return the clone sql
	 * @return
	 */
	private static String getCloneSql(String sql) {
		byte[] tempBytes = new byte[sql.getBytes().length];
		System
				.arraycopy(sql.getBytes(), 0, tempBytes, 0,
						sql.getBytes().length);
		String tempSql = new String(tempBytes);
		return tempSql;
	}

	/**
	 * Checks for distinct.
	 * 
	 * @param sql
	 *            the sql
	 * @return true, if successful
	 */
	private static boolean hasDistinct(String sql) {
		// String tempSql = getCloneSql(sql);
		return sql.indexOf("select distinct") >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#supportsLimit()
	 */
	public boolean supportsLimit() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "select current timestamp from sysibm.sysdummy1";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_DB2;
	}

	public String getTotalCountSql(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		if (tempSql.indexOf("union") != -1) {
			sql = "select count(1) as num from (" + sql + ") temp_select ";
			return sql;
		}

		String countStr = tempSql.trim();
		StringBuilder sb = new StringBuilder();
		if (countStr.startsWith("select")) {
			if (countStr.indexOf("from") != -1) {
				sb.append(countStr.substring(0, 6)).append(" count(1) as num ");
				if (countStr.indexOf("order by") != -1)
					sb.append(sql.substring(countStr.indexOf("from"), countStr
							.indexOf("order by")));
				else
					sb.append(sql.substring(countStr.indexOf("from")));
			}
		}
		return sb.toString();
	}

	public Integer addCursorOutParameter(
			Map<Integer, ProcedureParameter> outParameters, Integer start) {
		outParameters.put(start + 1, new ProcedureParameter(
				StoredProcedureUtil.DEFAULT_RESULTSET_NAME,
				StoredProcedureUtil.DEFAULT_CURSOR_TYPE, null, "out"));
		return start;
	}

	public String buildSqlFuction(String sql) throws SQLException {
		try {
			return DialectUtil.buildSqlFuction(sql, DialectUtil.DB_TYPE_DB2);
		} catch (Exception e) {
			throw new SQLException("解析方言函数出错：" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getReservedWord(java.lang.String)
	 */
	@Override
	public String getReservedWord(String columnName) {
		// TODO Auto-generated method stub
		String reservedWord = columnName;
		if(RESERVED_WORDS.contains(columnName.toUpperCase())){
			reservedWord = "\""+ columnName +"\"";
			log.info("关键字转换：["+columnName+"]->["+reservedWord+"]");
		}
		return reservedWord;
	}

	
}
