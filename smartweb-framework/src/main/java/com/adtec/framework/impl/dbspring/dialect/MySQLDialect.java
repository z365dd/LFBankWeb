/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: MySQLDialect.java
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

import com.adtec.framework.impl.dbspring.session.DBSession;
import com.adtec.framework.impl.dbspring.storedProcedure.StoredProcedureUtil;
import com.adtec.framework.interfaces.db.dialect.IDialect;
import com.adtec.framework.interfaces.db.storedProcedure.ProcedureParameter;

// TODO: Auto-generated Javadoc
/**
 * The Class MySQLDialect.
 */
public class MySQLDialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(MySQLDialect.class);
	/**
	 * MySQL的关键字和保留字
	 */
	public final static Set<String> RESERVED_WORDS = Sets.newHashSet();
	static{
		RESERVED_WORDS.add("DESCRIBE");
		RESERVED_WORDS.add("ACCESSIBLE");
		RESERVED_WORDS.add("ACCOUNT");
		RESERVED_WORDS.add("ACTION");
		RESERVED_WORDS.add("ADD");
		RESERVED_WORDS.add("AFTER");
		RESERVED_WORDS.add("AGAINST");
		RESERVED_WORDS.add("AGGREGATE");
		RESERVED_WORDS.add("ALGORITHM");
		RESERVED_WORDS.add("ALL");
		RESERVED_WORDS.add("EXEC");
		RESERVED_WORDS.add("INTERVAL");
	}
	/**
	 * Instantiates a new my sql dialect.
	 */
	public MySQLDialect() {
	}

	/**
	 * getLimitString.
	 * 
	 * @param sql
	 *            String
	 * @param offset
	 *            int
	 * @param limit
	 *            int
	 * @return String
	 * @todo Implement this snowrain.database.data.Dialect method
	 */
	public String getLimitString(String sql, int offset, int limit) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(sql);
		/*20181017 add by chenyl for 对于送过来的开始下标小于1的，设置为默认值1*/
		if(offset<1){
			offset = 1;
		}
		pagingSelect.append(" limit " + (offset - 1) + ", " + limit);
		return pagingSelect.toString();
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

		return "SELECT @@session.identity";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql
	 * (java.lang.String)
	 */
	public String getAutoIncreaseKeySql(String tableName) {
		return "SELECT @@session.identity";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "select now()";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_MYSQL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getTotalCountSql(java
	 * .lang.String)
	 */
	public String getTotalCountSql(String sql) {
		String countStr = sql.trim();
		if (sql.indexOf("union") != -1) {
			sql = "select count(1) as TotalCount from (" + sql
					+ ") temp_select ";
			return sql;
		}
		if (countStr.startsWith("select")) {
			if (countStr.indexOf("from") != -1)
				countStr = countStr.substring(0, 6)
						+ " count(1) as TotalCount "
						+ countStr.substring(countStr.indexOf("from"));
		}
		return countStr;
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
			return DialectUtil.buildSqlFuction(sql, DialectUtil.DB_TYPE_MYSQL);
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
			reservedWord = "`"+ columnName +"`";
			log.info("关键字转换：["+columnName+"]->["+reservedWord+"]");
		}
		return reservedWord;
	}
}
