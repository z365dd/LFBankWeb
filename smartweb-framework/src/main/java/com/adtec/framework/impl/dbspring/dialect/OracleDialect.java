/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: OracleDialect.java
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
 * The Class OracleDialect.
 */
public class OracleDialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(OracleDialect.class);
	/**
	 * Oracle的关键字和保留字
	 */
	public final static Set<String> RESERVED_WORDS = Sets.newHashSet();
	static{
		RESERVED_WORDS.add("ABORT");
		RESERVED_WORDS.add("ACCESS");
		RESERVED_WORDS.add("ACCESSED");
		RESERVED_WORDS.add("ACCOUNT");
		RESERVED_WORDS.add("ACTIVATE");
		RESERVED_WORDS.add("ADD");
	}

	/**
	 * Instantiates a new oracle dialect.
	 */
	public OracleDialect() {
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
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getLimitString(java.lang
	 * .String, int, int)
	 */
	public String getLimitString(String sql, int offset, int limit) {
		StringBuffer pagingSelect = new StringBuffer();
		/*20181017 add by chenyl for 对于送过来的开始下标小于1的，设置为默认值1*/
		if(offset<1){
			offset = 1;
		}
		pagingSelect
				.append("select * from ( select row_.*, rownum jres_db_rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <=" + (offset + limit - 1)
				+ ") where jres_db_rownum_ >=" + offset);

		return pagingSelect.toString();
	}

	/*
	 * 
	 * 约定与表名绑定的sequence名称为"seq_default"
	 * @see
	 * com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql()
	 */
	public String getAutoIncreaseKeySql() {
		return "select seq_default.NEXTVAL from dual";
	}

	/**
	 * 
	 * 根据表名从序列中取值，序列命名要符合"seq_" + tableName的规范
	 * 
	 * @param tableName
	 *            the table name
	 * @return the auto increase key sql
	 */
	public String getAutoIncreaseKeySql(String tableName) {
		return "select " + DialectUtil.getSeqName(tableName) + ".NEXTVAL from dual";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "select  to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') from dual";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_ORACLE;
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
			sql = "select count(1) as TotalCount from (" + sql + ") temp_select ";
			return sql;
		}		
		
		StringBuffer countSelect = new StringBuffer();
		countSelect.append("select count(1) as TotalCount from ( ");
		countSelect.append(sql);
		countSelect.append(" ) ");
		return countSelect.toString();
	}

	public Integer addCursorOutParameter(
			Map<Integer, ProcedureParameter> outParameters, Integer start) {
		outParameters.put(++start, new ProcedureParameter(
				StoredProcedureUtil.DEFAULT_RESULTSET_NAME,
				oracle.jdbc.OracleTypes.CURSOR, null, "out"));
		return start;
	}

	public String buildSqlFuction(String sql) throws SQLException{
		try{
			return DialectUtil.buildSqlFuction(sql, DialectUtil.DB_TYPE_ORACLE);
		}catch(Exception e){
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
