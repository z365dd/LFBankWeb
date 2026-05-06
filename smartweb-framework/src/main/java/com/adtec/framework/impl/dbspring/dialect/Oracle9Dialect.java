/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: Oracle9Dialect.java
 * 软件版权: 
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *             
 * ========    =======  ============================================
*/
package com.adtec.framework.impl.dbspring.dialect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
 * The Class Oracle9Dialect.
 */
public class Oracle9Dialect implements IDialect {
	private final static Logger log = LoggerFactory.getLogger(Oracle9Dialect.class);
	/**
	 * Oracle9的关键字和保留字
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
	 * Instantiates a new oracle9 dialect.
	 */
	public Oracle9Dialect() {
	}
	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#supportsLimit()
	 */
	public boolean supportsLimit() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getLimitString(java.lang.String, int, int)
	 */
	public String getLimitString(String sql, int offset, int limit) {

		// todo
		StringBuffer pagingSelect = new StringBuffer();
		// pagingSelect
		// .append("select * from ( select row_.* from ( ");
		// pagingSelect.append(sql);
		// pagingSelect.append(" ) row_ where rownum <=" + limit
		// + ") where rownum >=" + offset);
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

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql()
	 */
	public String getAutoIncreaseKeySql() {
		return null;
	}

	/**
	 * 约定与表名绑定的sequence名称为"seq_"+tableName<br>
	 * 如果不是这样，则需要进行调整.
	 * 
	 * @param tableName the table name
	 * @return the auto increase key sql
	 */
	public String getAutoIncreaseKeySql(String tableName) {
		return "select seq_" + tableName + ".CURRVAL from dual";
	}

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "select  to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') from dual";
	}

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return "oracle9";
	}

	/* (non-Javadoc)
	 * @see com.adtec.framework.interfaces.db.dialect.IDialect#getTotalCountSql(java.lang.String)
	 */
	public String getTotalCountSql(String sql) {
		StringBuffer countSelect = new StringBuffer();
		countSelect.append("select count(*) as TotalCount from ( ");
		countSelect.append(sql);
		countSelect.append(" ) ");
		return countSelect.toString();
	}
	
	public Integer addCursorOutParameter(
			Map<Integer, ProcedureParameter> outParameters, Integer start) {
		outParameters.put(++start, new ProcedureParameter(StoredProcedureUtil.DEFAULT_RESULTSET_NAME,
				oracle.jdbc.OracleTypes.CURSOR, null, "out"));
		return start;
	}
	
	public String buildSqlFuction(String sql) {
		// TODO Auto-generated method stub
		return sql;
	}

	public List<Integer> getDataType(Connection conn, String sql, int count, String dataSourceName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveBigText(PreparedStatement ps, int pos, String data)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void saveImage(PreparedStatement ps, int pos, byte[] bytes)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public Object transResultSet(ResultSet rs, int columnIndex, int type)
			throws SQLException {
		return rs.getObject(columnIndex);
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
