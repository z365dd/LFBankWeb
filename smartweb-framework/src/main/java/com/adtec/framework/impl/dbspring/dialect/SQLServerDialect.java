/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: SQLServerDialect.java
 * 软件版权: 
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *             
 * ========    =======  ============================================
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
 * The Class SQLServerDialect.
 */
public class SQLServerDialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(SQLServerDialect.class);
	/**
	 * SqlServer的关键字和保留字
	 */
	public final static Set<String> RESERVED_WORDS = Sets.newHashSet();
	static{
		RESERVED_WORDS.add("ADD");
		RESERVED_WORDS.add("ALL");
		RESERVED_WORDS.add("ALTER");
		RESERVED_WORDS.add("AND");
		RESERVED_WORDS.add("ANY");
		RESERVED_WORDS.add("AS");
	}
	
	/**
	 * Instantiates a new sQL server dialect.
	 */
	public SQLServerDialect() {
	}

	/**
	 * getLimitString.
	 * 
	 * @param querySelect
	 *            the query select
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @return String
	 * @todo Implement this snowrain.database.data.Dialect method
	 */
	public String getLimitString(String sql, int offset, int limit) {
		String tempSql = DialectUtil.getCloneSql(sql).toLowerCase();

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
//		if (DialectUtil.hasDistinct(tempSql)) {
//			pagingSelect.append(" row_.* from ( ") // add another (inner)
//					// nested
//					// select
//					.append(sql.substring(startOfSelect)) // add the main
//					// query
//					.append(" ) as row_"); // close off the inner nested select
//		} else {
//			String fieldstr = DialectUtil.addOrderByToField(sql);
//			int orderIdx = tempSql.lastIndexOf("order by");
//			if (orderIdx != -1)
//				fieldstr = fieldstr.substring(0, orderIdx);
//			pagingSelect.append(" t_.* from(" + fieldstr + ")as t_");
//		}

		String fieldstr = DialectUtil.addOrderByToField(sql);
		int orderIdx = tempSql.lastIndexOf("order by");
		if (orderIdx != -1)
			fieldstr = fieldstr.substring(0, orderIdx);
		pagingSelect.append(" t_.* from(" + fieldstr + ")as t_");

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

	private String getRowNumber(String sql) {
		String tempSql = DialectUtil.getCloneSql(sql).toLowerCase();
		StringBuffer rownumber = new StringBuffer(50)
				.append("row_number() over(");
		if (tempSql.indexOf("union") == -1) { // 没有union情况下

			int orderByIndex = tempSql.lastIndexOf("order by");

			if (orderByIndex > 0 && !DialectUtil.hasDistinct(tempSql)) {
				rownumber.append("order by ").append(
						DialectUtil.replaceSchema(sql
								.substring(orderByIndex + 9), "t_"));
			} else if (orderByIndex > 0) {
				rownumber.append("order by ").append(
						DialectUtil.replaceSchema(sql
								.substring(orderByIndex + 9), "t_"));
			}

		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	/**
	 * supportsLimit.
	 * 
	 * @return boolean
	 * @todo Implement this snowrain.database.data.Dialect method
	 */
	public boolean supportsLimit() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql()
	 */
	public String getAutoIncreaseKeySql() {
		return "select @@IDENTITY";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql
	 * (java.lang.String)
	 */
	public String getAutoIncreaseKeySql(String tableName) {
		return "SELECT IDENT_CURRENT('" + tableName + "')";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "Select CONVERT(varchar(100), GETDATE(), 21)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_SQLSERVER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getTotalCountSql(java
	 * .lang.String)
	 */
	public String getTotalCountSql(String sql) {
		if (sql.indexOf("union") != -1) {
			sql = "select count(1) as TotalCount from (" + sql
					+ ") temp_select ";
			return sql;
		}

		String countStr = sql.trim();
		StringBuilder sb = new StringBuilder();
		if (countStr.startsWith("select")) {
			if (countStr.indexOf("from") != -1) {
				sb.append(countStr.substring(0, 6)).append(
						" count(1) as TotalCount ");
				if (countStr.indexOf("order by") != -1)
					sb.append(countStr.substring(countStr.indexOf("from"),
							countStr.indexOf("order by")));
				else
					sb.append(countStr.substring(countStr.indexOf("from")));
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
			
//			return sql;
			return DialectUtil.buildSqlFuction(sql,
					DialectUtil.DB_TYPE_SQLSERVER);
		} catch (Exception e) {
			throw new SQLException("解析方言函数出错：" + e.getMessage());
		}
	}

	/*
	 * private String transSubstrFunc(String sql){ StringBuilder sb = new
	 * StringBuilder(); String temsql =
	 * DialectUtil.getCloneSql(sql).toLowerCase();
	 * 
	 * if (temsql.indexOf(DialectUtil.FUN_HS_SUBSTR) != -1) { int start =
	 * temsql.indexOf(DialectUtil.FUN_HS_SUBSTR);
	 * 
	 * sb.append(sql.substring(0, start)); String tsql = sql.substring(start +
	 * DialectUtil.FUN_HS_SUBSTR.length()); int end =
	 * DialectUtil.getEndPosition(tsql, '(', ')'); if (end > 0) { String column
	 * = tsql.substring(tsql.indexOf("(")+1, end); if(column.indexOf(",")!=-1){
	 * String[] cArray = column.split(","); if(cArray.length==2){ column =
	 * column + ", " + "len(" + cArray[0] + ")"; } }
	 * sb.append("substring(").append(column).append(")");
	 * sb.append(transSubstrFunc(tsql.substring(end + 1))); } sql =
	 * sb.toString(); } temsql = null; return sql; }
	 */

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
