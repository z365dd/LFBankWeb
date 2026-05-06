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

public class SybaseDialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(SybaseDialect.class);
	/**
	 * Sybase的关键字和保留字
	 */
	public final static Set<String> RESERVED_WORDS = Sets.newHashSet();
	static{
		RESERVED_WORDS.add("A");
		RESERVED_WORDS.add("ABORT");
		RESERVED_WORDS.add("ABS");
		RESERVED_WORDS.add("ABSOLUTE");
		RESERVED_WORDS.add("ACCESS");
		RESERVED_WORDS.add("ACOS");
		RESERVED_WORDS.add("ACQUIRE");
		RESERVED_WORDS.add("ACTION");
		RESERVED_WORDS.add("ACTIVATE");
		RESERVED_WORDS.add("ADA");
	}
	
	public SybaseDialect() {

	}

	public Integer addCursorOutParameter(
			Map<Integer, ProcedureParameter> outParametersMap, Integer start) {

		outParametersMap.put(start + 1, new ProcedureParameter(
				StoredProcedureUtil.DEFAULT_RESULTSET_NAME,
				StoredProcedureUtil.DEFAULT_CURSOR_TYPE, null, "out"));
		return start;
	}

	public String buildSqlFuction(String sql) throws SQLException {
		try {
			return DialectUtil.buildSqlFuction(sql, DialectUtil.DB_TYPE_SYBASE);
		} catch (Exception e) {
			throw new SQLException("解析方言函数出错：" + e.getMessage());
		}
	}

	public String getAutoIncreaseKeySql() {
		return "select @@IDENTITY";
	}

	public String getAutoIncreaseKeySql(String tableName) {
		return "select @@IDENTITY";
	}

	public String getCurrentDate() {
		return "Select CONVERT(varchar(100), GETDATE(), 21)";
	}

	public String getDialectName() {
		return DialectUtil.DB_TYPE_SYBASE;
	}

	public String getLimitString(String sql, int offset, int limit) {
		return sql;
	}

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

	public boolean supportsLimit() {
		// TODO Auto-generated method stub
		return false;
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
