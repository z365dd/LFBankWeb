/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: InformixDialect.java
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
 * The Class InformixDialect.
 */
public class InformixDialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(InformixDialect.class);
	/**
	 * Informix的关键字和保留字
	 */
	public final static Set<String> RESERVED_WORDS = Sets.newHashSet();
	/**
	 * Instantiates a new informix dialect.
	 */
	public InformixDialect() {
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
		/*20181017 add by chenyl for 对于送过来的开始下标小于1的，设置为默认值1*/
		if(offset<1){
			offset = 1;
		}
		pagingSelect.append("select skip ");
		pagingSelect.append(offset - 1);
		pagingSelect.append(" first " + limit);
		pagingSelect.append(" * from (" + sql);
		pagingSelect.append(" )");
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

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql
	 * (java.lang.String)
	 */
	public String getAutoIncreaseKeySql(String tableName) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "SELECT current FROM sysmaster:sysshmvals";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_INFORMIX;
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
			sql = "select count(*) from (" + sql + ") temp_select ";
			return sql;
		}
		StringBuilder sb = new StringBuilder();
		if (countStr.startsWith("select")) {
			if (countStr.indexOf("from") != -1) {
				sb.append(countStr.substring(0, 6)).append(
						" count(*) as TotalCount ");
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
			return DialectUtil.buildSqlFuction(sql,
					DialectUtil.DB_TYPE_INFORMIX);
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
