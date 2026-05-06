package com.adtec.framework.interfaces.db.dialect;

import java.sql.SQLException;
import java.util.Map;

import com.adtec.framework.interfaces.db.storedProcedure.ProcedureParameter;

/**
 * 数据库方言接口
 * @author chenyl
 * @version 1.0
 */
public interface IDialect {

	/**
	 * 是否支持限定条数取回内容
	 * 
	 * @return boolean
	 */
	public boolean supportsLimit();

	/**
	 * 返回取回指定条数数据的SQL语句
	 * 
	 * @param sql
	 *            String
	 * @param offset
	 *            int
	 * @param limit
	 *            int
	 * @return String
	 */
	public String getLimitString(String sql, int offset, int limit);

	/**
	 * 返回最后增加记录的自增长标语句
	 * 
	 * @return
	 */
	public String getAutoIncreaseKeySql();

	/**
	 * 返回最后增加记录的自增长标语句
	 * 
	 * @param seqName
	 *            指定表明
	 * @return
	 */

	public String getAutoIncreaseKeySql(String seqName);

	/**
	 * 返回方言名称
	 * 
	 * @return
	 */
	public String getDialectName();

	/**
	 * 查询当前数据库时间
	 * 
	 * @return
	 */
	public String getCurrentDate();

	/**
	 * 获得totalCount sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getTotalCountSql(String sql);
	
	/**
	 * 针对不同数据库的存储过程,设置返回参数
	 * @param outParametersMap
	 * @param start
	 * @return
	 */
	public Integer addCursorOutParameter(
			Map<Integer, ProcedureParameter> outParametersMap, Integer start);
	
	/**
	 * 解析sql方言函数
	 * @param sql
	 * @return
	 */
	public String buildSqlFuction(String sql)throws SQLException;
	
	/**
	 * 如果当前列名为保留字段，则返回对应的写法，否则原样返回
	 * @param columnName
	 * @return
	 */
	public String getReservedWord(String columnName);
}
