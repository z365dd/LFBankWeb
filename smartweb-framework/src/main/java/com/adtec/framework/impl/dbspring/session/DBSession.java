
package com.adtec.framework.impl.dbspring.session;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.UUID;
import com.adtec.framework.impl.dbspring.dataset.DataSetUtil;
import com.adtec.framework.impl.dbspring.datasource.DataSourceFactory;
import com.adtec.framework.impl.dbspring.dialect.DialectUtil;
import com.adtec.framework.impl.dbspring.storedProcedure.StoredProcedureUtil;
import com.adtec.framework.impl.share.caseStrategy.BaseCaseStrategy;
import com.adtec.framework.impl.share.caseStrategy.CamelCaseStrategy;
import com.adtec.framework.impl.share.caseStrategy.NormalCaseStrategy;
import com.adtec.framework.interfaces.db.dialect.IDialect;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.db.sqlstatictics.IStatHolder;
import com.adtec.framework.interfaces.db.storedProcedure.ProcedureParameter;
import com.adtec.framework.interfaces.share.IDataset;
import org.apache.axis.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <P>DBSession</P>
 * <P>类的详细说明</P>
 * <P>Copyright: Copyright (c) 2015</P>
 * <P>Company: 北京先进数通信息技术股份公司</P>
 * @author  chenyl
 * @version 1.0 2016年9月20日 
 * <P>          修改者姓名 修改内容说明</P>
 * @see     参考类1
 */
public class DBSession implements IDBSession {	
	private final static Logger log = LoggerFactory.getLogger(DBSession.class);
	@SuppressWarnings("unused")
	static final private int DEFULT_OUT_PARAMETER_NUM = 100;

	private Connection connection;

	private IDialect dialect;

	private String dbtype;

	private boolean isScroll;

	private boolean isCursor;

	private boolean autoRollback;

	private String dataSourceName;

	private int fetchSize = 1000;

	ArrayList<Statement> statementList = new ArrayList<Statement>();;

	ArrayList<ResultSet> resultSetList = new ArrayList<ResultSet>();

	Map<ResultSet, Statement> statements = new HashMap<ResultSet, Statement>();

	private boolean isTransactionClosed = true;

	private UUID uuid = null;

	private boolean cutOrderBy = false;

	private Pattern orderByPattern;

	private PreparedStatement ps;

	private Statement statement;

	private String batchSqlWithPreparedStatement;

	private String originalSql;

	private boolean isUseSqlFuction = true;
	
	/**
	 * 日志打印类型，输出sql执行日志
	 */
	private static final int LOG_TYPE_SQL = 393;

	public void closeResultSet(ResultSet resultSet) throws SQLException {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSetList.remove(resultSet);
			}
		} catch (Exception e) {
			System.out.println("操作失败");
		}
	}

	public void closeResultSetAndStatement(ResultSet resultSet)
			throws SQLException {
		try {
			if (resultSet != null) {
				Statement statement = statements.remove(resultSet);
				if (statement != null) {
					closeStatement(statement);
				}
				resultSet.close();
				resultSetList.remove(resultSet);
			}
		} catch (Exception e) {
			System.out.println("操作失败");
		}
	}

	private void closeStatement(Statement statement) throws SQLException {
		statement.close();
		statementList.remove(statement);
	}

	public DBSession(Connection connection, IDialect dialect) {
		this.connection = connection;
		this.dialect = dialect;
		this.dbtype = dialect.getDialectName();
		String region = "ORDER\\s+BY\\s+(([\\w\u4e00-\u9fa5]+([\\w\u4e00-\u9fa5.$])*|([\\(](.*?)[\\)]))[\\s]*(desc|asc)?(,)?[\\s]*)+";
		orderByPattern = Pattern.compile(region, Pattern.CASE_INSENSITIVE);
	}

	public DBSession(Connection conn) {
		this.connection = conn;
		this.dialect = DataSourceFactory.dbTypeMap.get(DataSourceFactory.DEFALUT_DATASOURCE_NAME);
		this.dbtype = ParamUtil.getConfig("datasource.type");
	}

	private PreparedStatement getPreparedStatement(String sql,
			Object... parameters) throws SQLException {
		/*20200908 add by chenyl for 新增开启sql全大写转换功能*/
		String sqlCase = ParamUtil.getSqlCase();
		if(log.isDebugEnabled()){
			log.debug("SQL语句模式："+sqlCase);
		}
		if(ParamUtil.CONF_Y.equals(sqlCase)){
			sql  = sql.toUpperCase();
		}else if(ParamUtil.CONF_N.equals(sqlCase)){
			sql = sql.toLowerCase();
		}
		
		if (isUseSqlFuction)
		sql = dialect.buildSqlFuction(sql);
		
		
		PreparedStatement ps;
		if (isScroll)
			ps = connection.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		else
			ps = connection.prepareStatement(sql);
		ps.setFetchSize(getFetchSize());
		this.statementList.add(ps);
		for (int i = 0; i < parameters.length; i++) {
			setParameter(ps, i + 1, parameters[i], null);
		}

		return ps;
	}

	private void setParameter(PreparedStatement ps, int index, Object obj,
			Integer dateType) throws SQLException {
		if (obj == null) {
			ps.setObject(index, obj, java.sql.Types.VARCHAR);
		} else {
			if (obj instanceof Character || obj.getClass().equals(char.class)) {
				ps.setString(index, obj.toString());
			} else if (obj instanceof String
					|| obj.getClass().equals(String.class)) {
				ps.setString(index, obj.toString());
			} else if (obj instanceof byte[]
					|| obj.getClass().equals(byte[].class)) {
				ps.setBytes(index, (byte[]) obj);
			} else {
				if (dateType == null) {
					ps.setObject(index, obj);
				} else {
					ps.setObject(index, obj, dateType);
				}
			}
		}
	}

	private PreparedStatement getPreparedStatement(String sql)
			throws SQLException {
		log.info("getPreparedStatement");
		log.info("DBSession.dialect:"+dialect+" , connection:"+connection);
		/*20200908 add by chenyl for 新增开启sql全大写转换功能*/
		String sqlCase = ParamUtil.getSqlCase();
		if(log.isDebugEnabled()){
			log.debug("SQL语句模式："+sqlCase);
		}
		if(ParamUtil.CONF_Y.equals(sqlCase)){
			sql  = sql.toUpperCase();
		}else if(ParamUtil.CONF_N.equals(sqlCase)){
			sql = sql.toLowerCase();
		}
		
		if (isUseSqlFuction)
		sql = dialect.buildSqlFuction(sql);
		dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
				"no parameters");
		/*20190515 add by chenyl for 输入打印实际完整sql语句*/
		printRealSql(sql, new ArrayList());
		PreparedStatement ps;
		if (isScroll)
			ps = connection.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		else
			ps = connection.prepareStatement(sql);
		ps.setFetchSize(getFetchSize());
		this.statementList.add(ps);
		return ps;
	}

	private PreparedStatement getPreparedStatementByMap(String sql,
			Map<String, Object> parameters) throws SQLException {
		StringBuffer buf = new StringBuffer();
		ArrayList<Object> paraList = new ArrayList<Object>();
		String patternStr = "([\"](.*?)[\"])|([\'](.*?)[\'])|([@][a-zA-Z_$]*[\\w$]*)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(sql);
		int curpos = 0;
		while (matcher.find()) {
			String replaceStr = matcher.group();
			String variable = replaceStr.substring(1, replaceStr.length());
			if (!replaceStr.startsWith("\"") && !replaceStr.startsWith("'")
					&& parameters != null && parameters.containsKey(variable)) {
				buf.append(sql.substring(curpos, matcher.start()));
				curpos = matcher.end();
				paraList.add(parameters.get(variable));
				buf.append("?");
			}
			continue;
		}
		buf.append(sql.substring(curpos));
		return getPreparedStatementByList(buf.toString(), paraList);
	}

	private PreparedStatement getPreparedStatementByList(String sql,
			List<Object> parameters) throws SQLException {
		/*20200908 add by chenyl for 新增开启sql全大写转换功能*/
		String sqlCase = ParamUtil.getSqlCase();
		if(log.isDebugEnabled()){
			log.debug("SQL语句模式："+sqlCase);
		}
		if(ParamUtil.CONF_Y.equals(sqlCase)){
			sql  = sql.toUpperCase();
		}else if(ParamUtil.CONF_N.equals(sqlCase)){
			sql = sql.toLowerCase();
		}
		
		if (isUseSqlFuction)
		sql = dialect.buildSqlFuction(sql);
		PreparedStatement ps;
		if (isScroll)
			ps = connection.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		else
			ps = connection.prepareStatement(sql);
		ps.setFetchSize(getFetchSize());
		this.statementList.add(ps);
		List<Integer> dataTypes = new ArrayList<Integer>();
		log.info("dbType："+this.getDbtype());
		if (this.getDbtype().equals(DialectUtil.DB_TYPE_SYBASE)) {
			dataTypes.addAll(DialectUtil.getDataType(connection, sql,
					dataSourceName));
		}
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				if (dataTypes.size() > i && dataTypes.get(i) != null)
					setParameter(ps, i + 1, parameters.get(i), dataTypes.get(i));
				else
					setParameter(ps, i + 1, parameters.get(i), null);
			}
		}
		return ps;

	}

	private ResultSet openResultSet(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.executeQuery();
		this.resultSetList.add(rs);
		statements.put(rs, ps);
		log.info("openResultSet:"+rs+" , resultList: "+this.resultSetList.size());
		return rs;
	}

	public int account(String sql) throws SQLException {
		int ret = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = getPreparedStatement(sql);
			rs = openResultSet(ps);
			if (rs.next()) {
				ret = rs.getInt(1);
			}
		} catch (SQLException e) {
			
					
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(ps);
		}

		return ret;
	}

	private int getAccount(PreparedStatement ps) throws SQLException {
		int ret = 0;
		ResultSet rs = null;
		try {
			rs = openResultSet(ps);
			if (rs.next()) {
				ret = rs.getInt(1);
			}
		} catch (SQLException e) {
			
					
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(ps);
		}
		return ret;
	}

	public int account(String sql, Object... parameters) throws SQLException {
		int count = 0;
		try {
			PreparedStatement ps = getPreparedStatement(sql, parameters);
			count = getAccount(ps);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);
		} catch (SQLException e) {			
			throw e;
		}
		return count;
	}

	public int accountByList(String sql, List<Object> parameters)
			throws SQLException {
		int count = 0;
		try {
			PreparedStatement ps = getPreparedStatementByList(sql, parameters);
			count = getAccount(ps);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);		
		} catch (SQLException e) {
			
					
					
			throw e;
		}
		return count;
	}


	public int accountByMap(String sql, Map<String, Object> parameters)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatementByMap(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);		
			return getAccount(ps);
		} catch (SQLException e) {
					
			throw e;
		}

	}

	public void beginTransaction() throws SQLException {
		if (!isTransactionClosed) {
			throw new SQLException("Transaction is started.");
		}
		isTransactionClosed = false;
		connection.setAutoCommit(false);

	}

	public void clear() throws SQLException {
		try {
			for (int i = this.resultSetList.size() - 1; i >= 0; i--) {
				this.closeResultSetAndStatement(resultSetList.get(i));
			}
			for (int i = this.statementList.size() - 1; i >= 0; i--) {
				this.closeStatement(statementList.get(i));
			}
		} catch (Exception e) {
			log.error("DBSession["+uuid+"]关闭statement异常", e);
		}
	}

	public void endTransaction() throws SQLException {
		if (!isTransactionClosed) {
			this.connection.commit();
			connection.setAutoCommit(true);
			isTransactionClosed = true;
		} else {
			throw new SQLException("No transaction exist.");
		}
	}

	private int execute(PreparedStatement ps) throws SQLException {
		int ret = ps.executeUpdate();
		this.closeStatement(ps);
		return ret;
	}

	public int execute(String sql) throws SQLException {
		try {

			return execute(getPreparedStatement(sql));
		} catch (SQLException e) {
			
			throw e;
		}
	}

	public int execute(String sql, Object... parameters) throws SQLException {
		try {

			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));	
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);	
			return execute(getPreparedStatement(sql, parameters));
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public int executeByList(String sql, List<Object> parameters)
			throws SQLException {
		try {
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));	
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);	
			return execute(getPreparedStatementByList(sql, parameters));
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * sql中@参数值要与parameters中的key一样
	 * 例如：sql=select * from table where id=@id; parameters中存在key为id的
	 */
	public int executeByMap(String sql, Map<String, Object> parameters)
			throws SQLException {
		try {
			
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);	
			return execute(getPreparedStatementByMap(sql, parameters));
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public Map callProcedure(String procedureName,
			List<ProcedureParameter> parameterList, boolean isCursorBack)
			throws SQLException {
		Map result = null;
		String sql = null;
		CallableStatement cstmt = null;
		try {
			Map inParametersMap = new HashMap<Integer, Object>();
			Map outParametersMap = new HashMap<Integer, ProcedureParameter>();
			Integer returnType = StoredProcedureUtil.paresReturnType(-1,
					parameterList);
			Integer start = StoredProcedureUtil
					.paresInParametersAndOutParameters(0, returnType,
							parameterList, inParametersMap, outParametersMap);
			if (isCursorBack)
				start = dialect.addCursorOutParameter(outParametersMap, start);
			sql = StoredProcedureUtil.getCallProcedureSql(procedureName, start,
					returnType);
			cstmt = connection.prepareCall(sql);
			StoredProcedureUtil.setCallProcedureInParameterByPosition(cstmt,
					inParametersMap);
			StoredProcedureUtil.registerOutParameterByPosition(cstmt,
					outParametersMap);
			result = StoredProcedureUtil.executeCallProcedureByPosition(cstmt,
					outParametersMap);
		} catch (SQLException e) {
			throw e;
		} finally {// 20120418 关闭打开的statement
			if (cstmt != null) {
				cstmt.close();
			}
		}
		return result;
	}

	public Connection getConnection() throws SQLException {
		return connection;
	}

	private IDataset getDataSet(ResultSet rs) throws SQLException {
		IDataset dataset = null;
		try {
			dataset = DataSetUtil.getCopyDataset(rs);
		} finally {
			closeResultSetAndStatement(rs);
		}
		return dataset;
	}

	public IDataset getDataSet(String sql) throws SQLException {
		try {
			log.info(sql);
			PreparedStatement ps = getPreparedStatement(sql);
			return getDataSet(openResultSet(ps));
		} catch (SQLException e) {
			throw e;
		}
	}

	public IDataset getDataSetForPage(String sql, int start, int limit)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatement(sql);
			if (supportDialectForPage()) {
				sql = dialect.getLimitString(sql, start, limit);
				ps.close();
				ps = getPreparedStatement(sql);
				return getDataSet(openResultSet(ps));
			} else {
				return getCopyLimitDataset(start, limit, openResultSet(ps));
			}
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	private boolean supportDialectForPage() {
		return !isCursor() && dialect.supportsLimit();
	}

	public IDataset getDataSet(String sql, Object... parameters)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatement(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);		
			return getDataSet(openResultSet(ps));
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public IDataset getDataSetByList(String sql, List<Object> parameters)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatementByList(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);			
			return getDataSet(openResultSet(ps));
		} catch (SQLException e) {
			throw e;
		}
	}

	public IDataset getDataSetByMap(String sql, Map<String, Object> parameters)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatementByMap(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);			
			return getDataSet(openResultSet(ps));
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public IDataset getDataSetForPage(String sql, int start, int limit,
			Object... parameters) throws SQLException {
		try {
			if (dialect.supportsLimit() && !isCursor()) {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatement(sql, parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);		
				return getDataSet(openResultSet(ps));
			} else {
				PreparedStatement ps = getPreparedStatement(sql, parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);			
				return getCopyLimitDataset(start, limit, openResultSet(ps));
			}
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public IDataset getDataSetByListForPage(String sql, int start, int limit,
			List<Object> parameters) throws SQLException {
		try {
			if (dialect.supportsLimit() && !isCursor()) {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatementByList(sql,
						parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);			
				return getDataSet(openResultSet(ps));
			} else {
				PreparedStatement ps = getPreparedStatementByList(sql,
						parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);			
				return getCopyLimitDataset(start, limit, openResultSet(ps));
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public IDataset getDataSetByMapForPage(String sql, int start, int limit,
			Map<String, Object> parameters) throws SQLException {
		try {
			if (dialect.supportsLimit() && !isCursor()) {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatementByMap(sql,
						parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);			
				return getDataSet(openResultSet(ps));
			} else {
				PreparedStatement ps = getPreparedStatementByMap(sql,
						parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);			
				return getCopyLimitDataset(start, limit, openResultSet(ps));
			}
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public ResultSet getResultSet(String sql) throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatement(sql);
			return openResultSet(ps);
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public ResultSet getResultSet(String sql, Object... parameters)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatement(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);			
			return openResultSet(ps);
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public ResultSet getResultSetByList(String sql, List<Object> parameters)
			throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatementByList(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);			
			return openResultSet(ps);
		} catch (SQLException e) {
			throw e;
		}
	}

	public ResultSet getResultSetByMap(String sql,
			Map<String, Object> parameters) throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatementByMap(sql, parameters);
			dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
					paramsToString(parameters));
			/*20190515 add by chenyl for 输入打印实际完整sql语句*/
			printRealSql(sql, parameters);			
			return openResultSet(ps);
		} catch (SQLException e) {
			
					
			throw e;
		}
	}

	public void rollback() throws SQLException {
		if (!isTransactionClosed) {
			connection.rollback();
			connection.setAutoCommit(true);
			isTransactionClosed = true;
		}
	}

	public boolean transactionIsClosed() throws SQLException {

		return isTransactionClosed;
	}

	public int getAutoIncreaseKey() throws SQLException {
		if (dialect.getAutoIncreaseKeySql() == null) {
			throw new SQLException("The db don't support this operation.");
		}
		return this.account(dialect.getAutoIncreaseKeySql());
	}


	public int getAutoIncreaseKey(String tableName) throws SQLException {
		if (dialect.getAutoIncreaseKeySql(tableName) == null) {
			
			throw new SQLException("The db don't support this operation");
		}
		return this.account(dialect.getAutoIncreaseKeySql(tableName));
	}

	private <T> T getObject(String sql, Class<T> clz,
			BaseCaseStrategy caseStrategy) throws SQLException {
		T ret = null;
		ResultSet rs = null;
		try {
			rs = this.getResultSet(sql);
			if (rs.next()) {
				ret = getObjectFromResultSet(rs, clz, caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return ret;
	}

	public <T> T getObject(String sql, Class<T> clz,
			BaseCaseStrategy caseStrategy, Object... parameters)
			throws SQLException {
		ResultSet rs = null;
		T ret = null;
		try {
			rs = this.getResultSet(sql, parameters);
			if (rs.next()) {
				ret = getObjectFromResultSet(rs, clz, caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return ret;
	}

	private <T> T getObjectByList(String sql, Class<T> clz,
			List<Object> parameters, BaseCaseStrategy caseStrategy)
			throws SQLException {
		ResultSet rs = null;
		T ret = null;
		try {
			rs = this.getResultSetByList(sql, parameters);
			if (rs.next()) {
				ret = getObjectFromResultSet(rs, clz, caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return ret;
	}

	private <T> T getObjectByMap(String sql, Class<T> clz,
			Map<String, Object> parameters, BaseCaseStrategy caseStrategy)
			throws SQLException {
		T ret = null;
		ResultSet rs = null;
		try {
			rs = this.getResultSetByMap(sql, parameters);
			if (rs.next()) {
				ret = getObjectFromResultSet(rs, clz, caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return ret;
	}

	public <T> List<T> getObjectList(String sql, Class<T> clz,
			BaseCaseStrategy caseStrategy)

	throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSet(sql);
			while (rs.next()) {
				T obj = getObjectFromResultSet(rs, clz, caseStrategy);
				list.add(obj);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	private <T> List<T> getObjectList(String sql, Class<T> clz,
			BaseCaseStrategy caseStrategy, Object... parameters)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSet(sql, parameters);
			while (rs.next()) {
				T obj = getObjectFromResultSet(rs, clz, caseStrategy);
				list.add(obj);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListForPage(String sql, Class<T> clz,
			int start, int limit, BaseCaseStrategy caseStrategy,
			Object... parameters) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (dialect.supportsLimit() && !isCursor()) {
				rs = this.getResultSetForPage(sql, start, limit, parameters);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz, caseStrategy);
					list.add(obj);
				}
			} else {
				rs = this.getResultSet(sql, parameters);
				list = getCopyLimitObjectList(clz, start, limit, rs,
						caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	private <T> List<T> getObjectListByMapForPage(String sql, Class<T> clz,
			int start, int limit, Map<String, Object> parameters,
			BaseCaseStrategy caseStrategy) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (dialect.supportsLimit() && !isCursor()) {
				rs = this.getResultSetByMapForPage(sql, start, limit,
						parameters);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz, caseStrategy);
					list.add(obj);
				}
			} else {
				rs = this.getResultSetByMap(sql, parameters);
				list = getCopyLimitObjectList(clz, start, limit, rs,
						caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListByListForPage(String sql, Class<T> clz,
			int start, int limit, List<Object> parameters,
			BaseCaseStrategy caseStrategy) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (dialect.supportsLimit() && !isCursor()) {
				rs = this.getResultSetByListForPage(sql, start, limit,
						parameters);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz, caseStrategy);
					list.add(obj);
				}
			} else {
				rs = this.getResultSetByList(sql, parameters);
				list = getCopyLimitObjectList(clz, start, limit, rs,
						caseStrategy);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListByList(String sql, Class<T> clz,
			List<Object> parameters, BaseCaseStrategy caseStrategy)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSetByList(sql, parameters);
			while (rs.next()) {
				T obj = getObjectFromResultSet(rs, clz, caseStrategy);
				list.add(obj);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	private <T> List<T> getObjectListByMap(String sql, Class<T> clz,
			Map<String, Object> parameters, BaseCaseStrategy caseStrategy)
			throws SQLException {

		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSetByMap(sql, parameters);
			while (rs.next()) {
				T obj = getObjectFromResultSet(rs, clz, caseStrategy);
				list.add(obj);
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	private <T> T getObject(Class<T> clz) {
		T obj = null;
		try {
			obj = clz.newInstance();
		} catch (Exception e) {
			System.out.println("操作失败");
		}
		return obj;
	}

	private <T> T getObjectFromResultSet(ResultSet rs, Class<T> clz,
			BaseCaseStrategy caseStrategy) throws SQLException {
		T obj = getObject(clz);
		if (obj != null) {
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				try {
					setProperty(obj, caseStrategy.getPropertyName(meta
							.getColumnLabel(i)), rs, i);
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}
		}
		return obj;
	}


	public <T> void setProperty(T obj, String fieldName, ResultSet rs,
			int index) throws Exception {
		// add by chenyl 20170709 for 新增dataset中的列名称与对对象的属性名一致时才进行赋值(忽略大小写)
		boolean isEqual = false;
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		for (Field f : ClassUtil.getAccessibleFields(obj)) {
			if (f.getName().equalsIgnoreCase(fieldName)) {
				isEqual = true;
				break;
			}
		}
		if (!isEqual) {
			return;
		}
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		Field field = ClassUtil.getAccessibleField(obj,fieldName);
		Class<?> type = field.getType();
		ResultSetMetaData metaData = rs.getMetaData();
		int scale = metaData.getScale(index);
		int colType = metaData.getColumnType(index);
		if (type.equals(long.class) || type.equals(Long.class)) {
			ClassUtil.setFieldValue(obj, fieldName, rs.getLong(index));
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			ClassUtil.setFieldValue(obj, fieldName, rs.getInt(index));
		} else if (type.equals(String.class)) {
			String value = null;
			if (colType == java.sql.Types.CLOB) {
				java.sql.Clob clob = rs.getClob(index);
				value = clob.getSubString(1, (int) clob.length());
			} else {
				value = rs.getString(index);
			}
			if (value == null) {
				value = "";
			}
			ClassUtil.setFieldValue(obj, fieldName, value);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			ClassUtil.setFieldValue(obj, fieldName, round(rs.getDouble(index),
					scale));
		} else if (type.equals(byte[].class)) {
			if (colType == java.sql.Types.BLOB) {
				java.sql.Blob blob = rs.getBlob(index);
				byte[] value = blob.getBytes(1, (int) blob.length());
				ClassUtil.setFieldValue(obj, fieldName, value);
			} else {
				ClassUtil.setFieldValue(obj, fieldName, rs.getBytes(index));
			}
		} else {
			ClassUtil.setFieldValue(obj, fieldName, rs.getObject(index));
		}
	}

	private double round(double v, int scale) {

		if (scale > 0) {

			BigDecimal b = new BigDecimal(Double.toString(v));

			BigDecimal one = new BigDecimal("1");

			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		}
		return v;

	}

	public ResultSet getResultSetForPage(String sql, int start, int limit,
			Object... parameters) throws SQLException {
		if (dialect.supportsLimit() && !isCursor()) {
			try {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatement(sql, parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);		
				return openResultSet(ps);
			} catch (SQLException e) {
				
						
						
						
				throw e;

			}
		}
		throw new SQLException("The db don't support this operation");
	}

	public ResultSet getResultSetByListForPage(String sql, int start,
			int limit, List<Object> parameters) throws SQLException {
		if (dialect.supportsLimit() && !isCursor()) {
			try {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatementByList(sql,
						parameters);
				dbSessionDebugLog(LOG_TYPE_SQL, getUuid().toString(), sql,
						paramsToString(parameters));
				/*20190515 add by chenyl for 输入打印实际完整sql语句*/
				printRealSql(sql, parameters);		
				return openResultSet(ps);
			} catch (SQLException e) {
				
						
						
						
				throw e;
			}
		}
		throw new SQLException("The db don't support this operation");
	}

	public ResultSet getResultSetByMapForPage(String sql, int start, int limit,
			Map<String, Object> parameters) throws SQLException {
		if (dialect.supportsLimit() && !isCursor()) {
			sql = dialect.getLimitString(sql, start, limit);
			PreparedStatement ps = getPreparedStatementByMap(sql, parameters);
			return openResultSet(ps);
		}
		throw new SQLException("The db don't support this operation");
	}

	public int saveObject(String tableName, Object object,
			List<String> ignoreField, BaseCaseStrategy caseStrategy)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		StringBuffer field = new StringBuffer();
		StringBuffer values = new StringBuffer();
		sb.append("insert into " + tableName + "(");
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		Field[] fields = ClassUtil.getAccessibleFields(object);
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < fields.length; i++) {
			if (ignoreField != null) {
				//mody by chenyl at 20170709 for 过滤域时不处理
				if (!ignoreField.contains(fields[i].getName())) {
					// mody by chenyl at 20180813 for 新增对数据库关键字处理
					field.append(","
							+ dialect.getReservedWord(caseStrategy.getFieldName(fields[i].getName())));
					values.append(",?");
					try {
						params.add(getProperty(object, fields[i].getName()));
					} catch (Exception e) {
						
						throw new SQLException(e.getMessage());
					}
				}
			} else {
				// mody by chenyl at 20180813 for 新增对数据库关键字处理
				field.append(","
						+ dialect.getReservedWord(caseStrategy.getFieldName(fields[i].getName())));
				values.append(",?");
				try {
					params.add(getProperty(object, fields[i].getName()));
				} catch (Exception e) {
					
							
					throw new SQLException(e.getMessage());
				}
			}
		}
		sb.append(field.substring(1)).append(")values(").append(
				values.substring(1)).append(")");
		return executeByList(sb.toString(), params);
	}

	public int updateObject(String tableName, Object object,
			List<String> matchField, List<String> ignoreField,
			BaseCaseStrategy caseStrategy) throws SQLException {
		if (matchField == null || matchField.size() == 0) {
			throw new SQLException("配置字段为空,tableName:" + tableName + " Object:"
					+ object.getClass().getName());
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer set = new StringBuffer();
		StringBuffer where = new StringBuffer();
		sb.append("update " + tableName + " set ");
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		Field[] fields = ClassUtil.getAccessibleFields(object);
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < fields.length; i++) {
			if (ignoreField != null) {
				if (!ignoreField.contains(fields[i].getName())) {
					if (!matchField.contains(fields[i].getName())) {
						// mody by chenyl at 20180813 for 新增对数据库关键字处理
						set.append(","
								+ dialect.getReservedWord(caseStrategy
										.getFieldName(fields[i].getName()))
								+ "=?");
						try {
							params
									.add(getProperty(object, fields[i]
											.getName()));
						} catch (Exception e) {
							
							throw new SQLException(e.getMessage());
						}
					}
				}
			} else {
				if (!matchField.contains(fields[i].getName())) {
					// mody by chenyl at 20180813 for 新增对数据库关键字处理
					set.append(","
							+ dialect.getReservedWord(caseStrategy.getFieldName(fields[i].getName()))
							+ "=?");
					try {
						params.add(getProperty(object, fields[i].getName()));
					} catch (Exception e) {
						throw new SQLException(e.getMessage());
					}
				}
			}
		}
		for (int i = 0; i < matchField.size(); i++) {
			if (i > 0) {
				where.append(" and ");
			}
			// mody by chenyl at 20180813 for 新增对数据库关键字处理
			where.append(dialect.getReservedWord(caseStrategy.getFieldName(matchField.get(i))) + "=?");
			try {
				params.add(getProperty(object, matchField.get(i)));
			} catch (Exception e) {
				
						
				throw new SQLException(e.getMessage());
			}
		}
		sb.append(set.substring(1)).append(" where ").append(where);
		return executeByList(sb.toString(), params);
	}

	private Object getProperty(Object object, String fieldName)
			throws Exception {
		/*20200401 mod by chenyl for 使用自定义的ClassUtil.getFieldValu方法获取属性值*/
		Object obj = ClassUtil.getFieldValue(object, fieldName);
		return obj == null ? "" : obj;
	}

	private String paramsToString(Object[] objects) {
		if (objects == null) {
			return "no parameters";
		}
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append("," + object);
		}
		if (builder.length() <= 1)
			return "";
		return builder.toString().substring(1);
	}

	private String paramsToString(List<Object> objects) {
		if (objects == null) {
			return "no parameters";
		}
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append("," + object);
		}

		if (builder.length() <= 1)
			return "";
		return builder.toString().substring(1);
	}

	private String paramsToString(Map<String, Object> objects) {
		if (objects == null) {
			return "no parameters";
		}
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, Object> object : objects.entrySet()) {
			builder.append("," + object.getKey() + ":" + object.getValue());
		}

		if (builder.length() <= 1)
			return "";
		return builder.toString().substring(1);
	}

	public String getDialectName() {
		return dialect.getDialectName();
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDbtype() {
		return dbtype;
	}

	public boolean isCursor() {
		return isCursor;
	}

	public void setCursor(boolean isCursor) {
		this.isCursor = isCursor;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	private void dbSessionDebugLog(int logType, String... values) {
		String msg = "";
		if(logType==LOG_TYPE_SQL){
			if(values.length==3){
				msg = "SessionID["+values[0]+"]  SQL\u6267\u884c,\u5f53\u524dSQL:"+values[1]+" \u5f53\u524d\u53c2\u6570:"+values[2];
			}
		}
		log.info(msg);
	}
	
	/**
	 * 输出真实的sql语句，方便与日志查询
	 * @param sql		SQL 语句，可以带有 ? 的占位符 
	 * @param params	插入到 SQL 中的参数，可单个可多个可不填
	 * @return			实际 sql 语句 
	 */
	private String printRealSql(String sql, Object[] params) {
		/*20200908 add by chenyl for 新增开启sql全大写转换功能*/
		String sqlCase = ParamUtil.getSqlCase();
		if(log.isDebugEnabled()){
			log.debug("SQL语句模式："+sqlCase);
		}
		if(ParamUtil.CONF_Y.equals(sqlCase)){
			sql  = sql.toUpperCase();
		}else if(ParamUtil.CONF_N.equals(sqlCase)){
			sql = sql.toLowerCase();
		}
		/*20200305 add by chenyl for 捕捉打印真实执行SQL的语句异常问题 */
		String statement = "";
		try{
			if (params == null || params.length == 0) {
				if (log.isInfoEnabled()) {
					log.info("实际执行 SQL ------------>\n" + sql);
				}
				return sql;
			}

			if (!match(sql, params)) {
				if (log.isInfoEnabled()) {
					log.info("SQL 语句中的占位符与参数个数不匹配。SQL：" + sql);
				}
				return null;
			}

			int cols = params.length;
			Object[] values = new Object[cols];
			System.arraycopy(params, 0, values, 0, cols);

			for (int i = 0; i < cols; i++) {
				Object value = values[i];
				if (value instanceof Date) {
					values[i] = "'" + value + "'";
				} else if (value instanceof String) {
					values[i] = "'" + value + "'";
				} else if (value instanceof Boolean) {
					values[i] = (Boolean) value ? 1 : 0;
				}
			}

			statement = String.format(sql.replaceAll("\\?", "%s"), values);

			if (log.isInfoEnabled()) {
				log.info("实际执行 SQL ------------>\n" + statement);
			}
		}catch(Exception e){
			log.warn("打印真实执行SQL语句失败", e.getMessage());
		}
		return statement;
	}
	
	private String printRealSql(String sql, List<Object> params) {
		Object[] ps = null; 
		if(null!=params && !params.isEmpty()){
			ps = params.toArray();
		}
		return printRealSql(sql, ps);
	}
	
	private String printRealSql(String sql, Map<String, Object> params) {
		Object[] ps = null; 
		if(null!=params && null!=params.values() && !params.values().isEmpty()){
			ps = params.values().toArray();
		}
		return printRealSql(sql, ps);
	}
	
	/** 
	 * ? 和参数的实际个数是否匹配 
	 * 
	 * @param sql 
	 *   SQL 语句，可以带有 ? 的占位符 
	 * @param params 
	 *   插入到 SQL 中的参数，可单个可多个可不填 
	 * @return true 表示为 ? 和参数的实际个数匹配 
	 */
	private boolean match(String sql, Object[] params) {
		if (params == null || params.length == 0)
			return true; // 没有参数，完整输出
		Matcher m = Pattern.compile("(\\?)").matcher(sql);
		int count = 0;
		while (m.find()) {
			count++;
		}
		return count == params.length;
	}

	public java.util.Date getSysDate() throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatement(dialect
					.getCurrentDate());
			return getDate(ps);
		} catch (SQLException e) {
			
			throw e;
		}
	}

	private java.util.Date getDate(PreparedStatement ps) throws SQLException {
		java.util.Date ret = null;
		ResultSet rs = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			rs = openResultSet(ps);
			if (rs.next()) {
				if(!StringUtils.isEmpty(rs.getString(1))){
					ret = format.parse(rs.getString(1));
				}
			}
		} catch (Exception e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			closeResultSet(rs);
			closeStatement(ps);
		}
		return ret;
	}

	public IDataset getDataSetHasTotalCount(String sql) throws SQLException {
		IDataset result = getDataSet(sql);
		result.setTotalCount(result.getRowCount());// 20120326 不是分页sql
		return result;
	}

	public IDataset getDataSetHasTotalCount(String sql, int start, int limit)
			throws SQLException {
		IDataset result = getDataSetForPage(sql, start, limit);
		String cutSql = sql;
		if (cutOrderBy) {
			cutSql = cutOrderByWithTotalCountSql(sql);
		}
		result.setTotalCount(account(dialect.getTotalCountSql(cutSql)));
		return result;
	}

	public IDataset getDataSetByVarargsHasTotalCount(String sql,
			Object... parameters) throws SQLException {
		IDataset result = getDataSet(sql, parameters);
		result.setTotalCount(result.getRowCount());
		return result;
	}

	public IDataset getDataSetByListHasTotalCount(String sql,
			List<Object> parameters) throws SQLException {
		IDataset result = this.getDataSetByList(sql, parameters);
		result.setTotalCount(result.getRowCount());
		return result;
	}

	public IDataset getDataSetByMapHasTotalCount(String sql,
			Map<String, Object> parameters) throws SQLException {
		IDataset result = this.getDataSetByMap(sql, parameters);
		result.setTotalCount(result.getRowCount());
		return result;
	}

	public IDataset getDataSetByVarargsHasTotalCount(String sql, int start,
			int limit, Object... parameters) throws SQLException {
		IDataset result = this.getDataSetForPage(sql, start, limit, parameters);
		String cutSql = sql;
		if (cutOrderBy) {
			cutSql = cutOrderByWithTotalCountSql(sql);
		}
		result.setTotalCount(account(dialect.getTotalCountSql(cutSql),
				parameters));
		return result;
	}

	public IDataset getDataSetByListHasTotalCount(String sql, int start,
			int limit, List<Object> parameters) throws SQLException {
		IDataset result = this.getDataSetByListForPage(sql, start, limit,
				parameters);
		String cutSql = sql;
		if (cutOrderBy) {
			cutSql = cutOrderByWithTotalCountSql(sql);
		}
		result.setTotalCount(accountByList(dialect.getTotalCountSql(cutSql),
				parameters));
		return result;
	}

	public IDataset getDataSetByMapHasTotalCount(String sql, int start,
			int limit, Map<String, Object> parameters) throws SQLException {
		IDataset result = this.getDataSetByMapForPage(sql, start, limit,
				parameters);
		String cutSql = sql;
		if (cutOrderBy) {
			cutSql = cutOrderByWithTotalCountSql(sql);
		}
		result.setTotalCount(accountByMap(dialect.getTotalCountSql(cutSql),
				parameters));
		return result;
	}

	public String getDataSourceUsername() {
		return null;
	}

	public String getDataSourcePassword() {
		return null;
	}

	private <T> T getFirstRecordObject(String sql, Class<T> classType,
			BaseCaseStrategy caseStrategy, Object... parameters)
			throws SQLException {
		ResultSet set = null;
		try {
			if (parameters.length > 0) {
				set = this.getResultSet(sql, parameters);
			} else {
				set = this.getResultSet(sql);
			}
			if (set.next()) {
				return this
						.getObjectFromResultSet(set, classType, caseStrategy);
			} else {
				return null;
			}
		} finally {
			if (set != null) {
				this.closeResultSetAndStatement(set);
				set = null;
			}
		}

	}

	public IDataset getFirstRecordDataset(String sql, Object... parameters)
			throws SQLException {
		ResultSet set = null;
        IDataset idataset = null;
		try {
			if(parameters.length > 0)
				set = getResultSet(sql, parameters);
			else
				set = getResultSet(sql);
			idataset = DataSetUtil.getCopyFirstDataset(set);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}finally {
			if(set != null){
				closeResultSetAndStatement(set);
				set = null;
			}
		}

        return idataset;
        
	}

	private <T> T getFirstRecordObjectByList(String sql, Class<T> classType,
			List<Object> list, BaseCaseStrategy caseStrategy)
			throws SQLException {
		ResultSet set = null;
		try {
			set = this.getResultSetByList(sql, list);
			if (set.next()) {
				return this
						.getObjectFromResultSet(set, classType, caseStrategy);
			} else {
				return null;
			}
		} finally {
			if (set != null) {
				this.closeResultSetAndStatement(set);
				set = null;
			}
		}
	}

	public IDataset getFirstRecordDatasetByList(String sql, List<Object> list)
			throws SQLException {
		ResultSet set = null;
        IDataset idataset = null;
		try {
			set = getResultSetByList(sql, list);
			idataset = DataSetUtil.getCopyFirstDataset(set);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}finally {
			if(set != null){
				closeResultSetAndStatement(set);
				set = null;
			}
		}

        return idataset;
	}

	private <T> T getFirstRecordObjectByMap(String sql, Class<T> classType,
			Map<String, Object> map, BaseCaseStrategy caseStrategy)
			throws SQLException {
		ResultSet set = null;
		try {
			set = this.getResultSetByMap(sql, map);
			if (set.next()) {
				return this
						.getObjectFromResultSet(set, classType, caseStrategy);
			} else {
				return null;
			}
		} finally {
			if (set != null) {
				this.closeResultSetAndStatement(set);
				set = null;
			}
		}
	}

	public IDataset getFirstRecordDatasetByMap(String sql,
			Map<String, Object> map) throws SQLException {
				return null;
	}

	private <T> List<T> getCopyLimitObjectList(Class<T> clz, int start,
			int limit, ResultSet rs, BaseCaseStrategy caseStrategy)
			throws SQLException {
		if (start == 0)
			start = 1;
		List<T> list = new ArrayList<T>();
		try {
			int recordNum = 0;
			if (rs.getType() == ResultSet.TYPE_FORWARD_ONLY) {
				int count = 0;
				while (rs.next()) {
					count++;
					if (count == start) {
						break;
					}
				}
				if (count == start) {
					do {
						T obj = getObjectFromResultSet(rs, clz, caseStrategy);
						list.add(obj);
					} while ((rs.next()) && (++recordNum < limit));
				}
			} else {
				if (rs.absolute(start))
					do {
						T obj = getObjectFromResultSet(rs, clz, caseStrategy);
						list.add(obj);
					} while ((rs.next()) && (++recordNum < limit));
			}
		} finally {
			if (rs != null) {
				closeResultSetAndStatement(rs);
				rs = null;
			}
		}
		return list;
	}

	private IDataset getCopyLimitDataset(int start, int limit, ResultSet rs)
			throws SQLException {
		IDataset dataset = null;
		return dataset;
	}

	public boolean isScroll() {
		return isScroll;
	}

	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	public boolean isAutoRollback() {
		return autoRollback;
	}

	public void setAutoRollback(boolean autoRollback) {
		this.autoRollback = autoRollback;
	}

	private BaseCaseStrategy getCamelCaseStrategy() {
		return new CamelCaseStrategy();
	}

	private BaseCaseStrategy getNormalCaseStrategy() {
		return new NormalCaseStrategy();
	}

	public int saveObject(String tableName, Object object,
			List<String> ignoreField) throws SQLException {
		return saveObject(tableName, object, ignoreField,
				getCamelCaseStrategy());
	}

	public int hsStdSaveObject(String tableName, Object object,
			List<String> ignoreField) throws SQLException {
		return saveObject(tableName, object, ignoreField,
				getNormalCaseStrategy());
	}

	public int saveObject(String tableName, Object object) throws SQLException {
		return saveObject(tableName, object, null, getCamelCaseStrategy());
	}

	public int hsStdSaveObject(String tableName, Object object)
			throws SQLException {
		return saveObject(tableName, object, null, getNormalCaseStrategy());
	}


	public int updateObject(String tableName, Object object,
			List<String> matchField, List<String> ignoreField)
			throws SQLException {
		return updateObject(tableName, object, matchField, ignoreField,
				getCamelCaseStrategy());
	}

	public int hsStdUpdateObject(String tableName, Object object,
			List<String> matchField, List<String> ignoreField)
			throws SQLException {
		return updateObject(tableName, object, matchField, ignoreField,
				getNormalCaseStrategy());
	}

	public int updateObject(String tableName, Object object,
			List<String> matchField) throws SQLException {
		return updateObject(tableName, object, matchField, null,
				getCamelCaseStrategy());
	}

	public int hsStdUpdateObject(String tableName, Object object,
			List<String> matchField) throws SQLException {
		return updateObject(tableName, object, matchField, null,
				getNormalCaseStrategy());
	}

	public <T> T getObjectFromResultSet(ResultSet rs, Class<T> clz)
			throws SQLException {
		return getObjectFromResultSet(rs, clz, getCamelCaseStrategy());
	}

	public <T> T getHsStdObjectFromResultSet(ResultSet rs, Class<T> clz)
			throws SQLException {
		return getObjectFromResultSet(rs, clz, getNormalCaseStrategy());
	}

	public <T> T getFirstRecordObject(String sql, Class<T> classType,
			Object... parameters) throws SQLException {
		return getFirstRecordObject(sql, classType, getCamelCaseStrategy(),
				parameters);
	}

	public <T> T getHsStdFirstRecordObject(String sql, Class<T> classType,
			Object... parameters) throws SQLException {
		return getFirstRecordObject(sql, classType, getNormalCaseStrategy(),
				parameters);
	}

	public <T> T getFirstRecordObjectByList(String sql, Class<T> classType,
			List<Object> list) throws SQLException {
		return getFirstRecordObjectByList(sql, classType, list,
				getCamelCaseStrategy());
	}

	public <T> T getHsStdFirstRecordObjectByList(String sql,
			Class<T> classType, List<Object> list) throws SQLException {
		return getFirstRecordObjectByList(sql, classType, list,
				getNormalCaseStrategy());
	}

	public <T> T getFirstRecordObjectByMap(String sql, Class<T> classType,
			Map<String, Object> map) throws SQLException {
		return getFirstRecordObjectByMap(sql, classType, map,
				getCamelCaseStrategy());
	}

	public <T> T getHsStdFirstRecordObjectByMap(String sql, Class<T> classType,
			Map<String, Object> map) throws SQLException {
		return getFirstRecordObjectByMap(sql, classType, map,
				getNormalCaseStrategy());
	}

	public <T> List<T> getObjectList(String sql, Class<T> clz)
			throws SQLException {
		return getObjectList(sql, clz, getCamelCaseStrategy());
	}

	public <T> List<T> getHsStdObjectList(String sql, Class<T> clz)
			throws SQLException {
		return getObjectList(sql, clz, getNormalCaseStrategy());
	}

	public <T> T getObject(String sql, Class<T> clz) throws SQLException {
		return getObject(sql, clz, getCamelCaseStrategy());
	}

	public <T> T getHsStdObject(String sql, Class<T> clz) throws SQLException {
		return getObject(sql, clz, getNormalCaseStrategy());
	}

	public <T> List<T> getObjectList(String sql, Class<T> clz,
			Object... parameters) throws SQLException {
		return getObjectList(sql, clz, getCamelCaseStrategy(), parameters);
	}

	public <T> List<T> getHsStdObjectList(String sql, Class<T> clz,
			Object... parameters) throws SQLException {
		return getObjectList(sql, clz, getNormalCaseStrategy(), parameters);
	}

	public <T> List<T> getObjectListByList(String sql, Class<T> clz,
			List<Object> parameters) throws SQLException {
		return getObjectListByList(sql, clz, parameters, getCamelCaseStrategy());
	}

	public <T> List<T> getHsStdObjectListByList(String sql, Class<T> clz,
			List<Object> parameters) throws SQLException {
		return getObjectListByList(sql, clz, parameters,
				getNormalCaseStrategy());
	}

	public <T> List<T> getObjectListByListForPage(String sql, Class<T> clz,
			int start, int limit, List<Object> parameters) throws SQLException {
		return getObjectListByListForPage(sql, clz, start, limit, parameters,
				getCamelCaseStrategy());
	}

	public <T> List<T> getHsStdObjectListByListForPage(String sql,
			Class<T> clz, int start, int limit, List<Object> parameters)
			throws SQLException {
		return getObjectListByListForPage(sql, clz, start, limit, parameters,
				getNormalCaseStrategy());
	}

	public <T> List<T> getObjectListByMap(String sql, Class<T> clz,
			Map<String, Object> parameters) throws SQLException {
		return getObjectListByMap(sql, clz, parameters, getCamelCaseStrategy());
	}

	public <T> List<T> getHsStdObjectListByMap(String sql, Class<T> clz,
			Map<String, Object> parameters) throws SQLException {
		return getObjectListByMap(sql, clz, parameters, getNormalCaseStrategy());
	}

	public <T> List<T> getObjectListByMapForPage(String sql, Class<T> clz,
			int start, int limit, Map<String, Object> parameters)
			throws SQLException {
		return getObjectListByMapForPage(sql, clz, start, limit, parameters,
				getCamelCaseStrategy());
	}

	public <T> List<T> getHsStdObjectListByMapForPage(String sql, Class<T> clz,
			int start, int limit, Map<String, Object> parameters)
			throws SQLException {
		return getObjectListByMapForPage(sql, clz, start, limit, parameters,
				getNormalCaseStrategy());
	}

	public <T> List<T> getObjectListForPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException {
		return getObjectListForPage(sql, clz, start, limit,
				getCamelCaseStrategy(), parameters);
	}

	public <T> List<T> getHsStdObjectListForPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException {
		return getObjectListForPage(sql, clz, start, limit,
				getNormalCaseStrategy(), parameters);
	}

	public <T> T getObject(String sql, Class<T> clz, Object... parameters)
			throws SQLException {
		return getObject(sql, clz, getCamelCaseStrategy(), parameters);
	}

	public <T> T getHsStdObject(String sql, Class<T> clz, Object... parameters)
			throws SQLException {
		return getObject(sql, clz, getNormalCaseStrategy(), parameters);
	}

	public <T> T getObjectByList(String sql, Class<T> clz,
			List<Object> parameters) throws SQLException {
		return getObjectByList(sql, clz, parameters, getCamelCaseStrategy());
	}

	public <T> T getHsStdObjectByList(String sql, Class<T> clz,
			List<Object> parameters) throws SQLException {
		return getObjectByList(sql, clz, parameters, getNormalCaseStrategy());
	}

	public <T> T getObjectByMap(String sql, Class<T> clz,
			Map<String, Object> parameters) throws SQLException {
		return getObjectByMap(sql, clz, parameters, getCamelCaseStrategy());
	}

	public <T> T getHsStdObjectByMap(String sql, Class<T> clz,
			Map<String, Object> parameters) throws SQLException {
		return getObjectByMap(sql, clz, parameters, getNormalCaseStrategy());
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public IDataset getDataSetForDialectPage(String sql, int start, int limit)
			throws SQLException {
		try {
			if (supportDialectForPage()) {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatement(sql);
				return getDataSet(openResultSet(ps));
			} else {
				return null;
			}
		} catch (SQLException e) {
			
					
			throw e;
		}

	}

	public IDataset getDataSetForCursorPage(String sql, int start, int limit)
			throws SQLException {

		try {
			return getCopyLimitDataset(start, limit,
					openResultSet(getPreparedStatement(sql)));

		} catch (SQLException e) {
			
					
			throw e;
		}

	}

	public IDataset getDataSetForDialectPage(String sql, int start, int limit,
			Object... parameters) throws SQLException {
		try {
			if (supportDialectForPage()) {
				sql = dialect.getLimitString(sql, start, limit);
				
						
				PreparedStatement ps = getPreparedStatement(sql, parameters);
				return getDataSet(openResultSet(ps));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public IDataset getDataSetForCursorPage(String sql, int start, int limit,
			Object... parameters) throws SQLException {
		try {
			
					
			return getCopyLimitDataset(start, limit,
					openResultSet(getPreparedStatement(sql, parameters)));
		} catch (SQLException e) {
			
			throw e;
		}
	}

	public IDataset getDataSetByListForDialectPage(String sql, int start,
			int limit, List<Object> parameters) throws SQLException {
		try {
			if (supportDialectForPage()) {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatementByList(sql,
						parameters);
				
						
				return getDataSet(openResultSet(ps));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public IDataset getDataSetByListForCursorPage(String sql, int start,
			int limit, List<Object> parameters) throws SQLException {
		try {
			
					
			return getCopyLimitDataset(start, limit,
					openResultSet(getPreparedStatementByList(sql, parameters)));

		} catch (SQLException e) {
			
			throw e;
		}
	}

	public IDataset getDataSetByMapForDialectPage(String sql, int start,
			int limit, Map<String, Object> parameters) throws SQLException {
		try {
			if (supportDialectForPage()) {
				sql = dialect.getLimitString(sql, start, limit);
				PreparedStatement ps = getPreparedStatementByMap(sql,
						parameters);
				
						
				return getDataSet(openResultSet(ps));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public IDataset getDataSetByMapForCursorPage(String sql, int start,
			int limit, Map<String, Object> parameters) throws SQLException {
		try {
			
					
			return getCopyLimitDataset(start, limit,
					openResultSet(getPreparedStatementByMap(sql, parameters)));
		} catch (SQLException e) {
			throw e;
		}
	}

	public <T> List<T> getObjectListForDialectPage(String sql, Class<T> clz,
			int start, int limit) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (supportDialectForPage()) {
				rs = this.getResultSetForPage(sql, start, limit);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz,
							getCamelCaseStrategy());
					list.add(obj);
				}
			} else {
				return null;
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListForCursorPage(String sql, Class<T> clz,
			int start, int limit) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSet(sql);
			list = getCopyLimitObjectList(clz, start, limit, rs,
					getCamelCaseStrategy());

		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListForDialectPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (supportDialectForPage()) {
				rs = this.getResultSetForPage(sql, start, limit, parameters);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz,
							getCamelCaseStrategy());
					list.add(obj);
				}
			} else {
				return null;
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListForCursorPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSet(sql, parameters);
			list = getCopyLimitObjectList(clz, start, limit, rs,
					getCamelCaseStrategy());

		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListByListForDialectPage(String sql,
			Class<T> clz, int start, int limit, List<Object> parameters)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (supportDialectForPage()) {
				rs = this.getResultSetByListForPage(sql, start, limit,
						parameters);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz,
							getCamelCaseStrategy());
					list.add(obj);
				}
			} else {
				return null;
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListByListForCursorPage(String sql,
			Class<T> clz, int start, int limit, List<Object> parameters)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSetByList(sql, parameters);
			list = getCopyLimitObjectList(clz, start, limit, rs,
					getCamelCaseStrategy());
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListByMapForDialectPage(String sql,
			Class<T> clz, int start, int limit, Map<String, Object> parameters)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			if (supportDialectForPage()) {
				rs = this.getResultSetByMapForPage(sql, start, limit,
						parameters);
				while (rs.next()) {
					T obj = getObjectFromResultSet(rs, clz,
							getCamelCaseStrategy());
					list.add(obj);
				}
			} else {
				return null;
			}
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public <T> List<T> getObjectListByMapForCursorPage(String sql,
			Class<T> clz, int start, int limit, Map<String, Object> parameters)
			throws SQLException {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		try {
			rs = this.getResultSetByMap(sql, parameters);
			list = getCopyLimitObjectList(clz, start, limit, rs,
					getCamelCaseStrategy());
		} finally {
			this.closeResultSetAndStatement(rs);
		}
		return list;
	}

	public IDataset getDatasetByArray(String sql, IStatHolder statInfo,
			Object... parameters) throws SQLException {
		return null;
	}

	public IDataset getDatasetByList(String sql, IStatHolder statInfo,
			List parameters) throws SQLException {
		return null;
	}

	public IDataset getDatasetByMap(String sql, IStatHolder statInfo,
			Map parameters) throws SQLException {
		return null;

	}

	public int deleteObject(String tableName, Object object,
			List<String> matchField) throws SQLException {
		return deleteObject(tableName, object, matchField,
				getCamelCaseStrategy());
	}

	public int hsStdDeleteObject(String tableName, Object object,
			List<String> matchField) throws SQLException {
		return deleteObject(tableName, object, matchField,
				getNormalCaseStrategy());
	}

	public int[] executeBatch(List<String> sqls) throws SQLException {
		Statement statement = null;
		try {
			beginTransaction();
			DatabaseMetaData metaData = connection.getMetaData();
			if (metaData.supportsBatchUpdates()) {
				statement = getStatement();
				if (statement != null) {

					if (sqls != null) {
						for (int i = 0; i < sqls.size(); i++) {
							statement.addBatch(sqls.get(i));
						}
					}
					return statement.executeBatch();
				} else {
					throw new SQLException("statement为空");
				}
			} else {
				throw new SQLException("数据库类型：" + dbtype + "不支持批处理操作");
			}
		} catch (SQLException e) {
			rollback();
			throw e;
		} finally {
			endBatch(statement);
		}
	}

	private Statement getStatement() throws SQLException {
		Statement ps;
		if (isScroll)
			ps = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		else
			ps = connection.createStatement();
		ps.setFetchSize(getFetchSize());
		this.statementList.add(ps);
		return ps;
	}

	public int[] executeBatchByList(String sql, List<List<Object>> parameters)
			throws SQLException {
		PreparedStatement ps = null;
		try {
			beginTransaction();
			DatabaseMetaData metaData = connection.getMetaData();
			if (metaData.supportsBatchUpdates()) {
				ps = getPreparedStatement(sql);
				for (int i = 0; i < parameters.size(); i++) {
					if (ps != null) {
						setUpParamtersByList(sql, ps, parameters.get(i));
					} else {
						throw new SQLException("statement为空");
					}
				}
				return ps.executeBatch();
			} else {
				throw new SQLException("数据库类型：" + dbtype + "不支持批处理操作");
			}
		} catch (SQLException e) {
			rollback();
			throw e;
		} finally {
			endBatch(ps);
		}
	}

	private void setUpParamtersByList(String sql, PreparedStatement ps,
			List<Object> parameters) throws SQLException {
		List<Integer> dataTypes = new ArrayList<Integer>();
		if (this.getDbtype().equals(DialectUtil.DB_TYPE_SYBASE)) {
			dataTypes.addAll(DialectUtil.getDataType(connection, sql,
					dataSourceName));
		}
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				if (dataTypes.size() > i && dataTypes.get(i) != null)
					setParameter(ps, i + 1, parameters.get(i), dataTypes.get(i));
				else
					setParameter(ps, i + 1, parameters.get(i), null);
			}
		}
		ps.addBatch();
	}

	/**
	 * sql中@参数值要与parameters中的key一样
	 * 例如：sql=select * from table where id=@id; parameters中存在key为id的
	 */
	public int[] executeBatchByMap(String sql,
			List<Map<String, Object>> parameters) throws SQLException {
		PreparedStatement ps = null;
		try {
			beginTransaction();
			DatabaseMetaData metaData = connection.getMetaData();
			if (metaData.supportsBatchUpdates()) {
				String batchSql = processSqlWithMap(sql);
				ps = getPreparedStatement(batchSql);
				if (ps != null) {
					for (int i = 0; i < parameters.size(); i++) {
						List<Object> parameter = getSqlParameterValues(sql,
								parameters.get(i));
						setUpParamtersByList(batchSql, ps, parameter);
					}
					return ps.executeBatch();
				} else {
					throw new SQLException("statement为空");
				}
			} else {
				throw new SQLException("数据库类型：" + dbtype + "不支持批处理操作");
			}
		} catch (SQLException e) {
			rollback();
			throw e;
		} finally {
			endBatch(ps);
		}
	}

	private String processSqlWithMap(String sql) throws SQLException {

		StringBuffer buf = new StringBuffer();
		String patternStr = "([\"](.*?)[\"])|([\'](.*?)[\'])|([@][a-zA-Z_$]*[\\w$]*)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(sql);
		int curpos = 0;
		while (matcher.find()) {
			String replaceStr = matcher.group();
			if (!replaceStr.startsWith("\"") && !replaceStr.startsWith("'")) {
				buf.append(sql.substring(curpos, matcher.start()));
				curpos = matcher.end();
				buf.append("?");
			}
			continue;
		}
		buf.append(sql.substring(curpos));
		return buf.toString();
	}

	private List<Object> getSqlParameterValues(String sql,
			Map<String, Object> parameters) throws SQLException {

		List<Object> paraList = new ArrayList<Object>();
		String patternStr = "([\"](.*?)[\"])|([\'](.*?)[\'])|([@][a-zA-Z_$]*[\\w$]*)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String replaceStr = matcher.group();
			String variable = replaceStr.substring(1, replaceStr.length());
			if (!replaceStr.startsWith("\"") && !replaceStr.startsWith("'")) {
				paraList.add(parameters.get(variable));
			}
			continue;
		}

		return paraList;
	}

	public boolean beginBatchWithStatement() throws SQLException {
		if (statement == null) {
			DatabaseMetaData metaData = connection.getMetaData();
			if (metaData.supportsBatchUpdates()) {
				statement = getStatement();
				beginTransaction();
				return true;
			} else {
				return false;
			}

		} else {
			throw new SQLException("批处理初始化工作已经完成");
		}
	}

	public boolean beginBatchWithPreparedStatement(String sql)
			throws SQLException {
		if (ps == null) {
			DatabaseMetaData metaData = connection.getMetaData();
			if (metaData.supportsBatchUpdates()) {
				this.originalSql = sql;
				this.batchSqlWithPreparedStatement = processSqlWithMap(sql);
				ps = getPreparedStatement(batchSqlWithPreparedStatement);
				beginTransaction();
				return true;
			} else {
				return false;
			}
		} else {
			throw new SQLException("批处理初始化工作已经完成");
		}
	}

	public void addBatchSql(String sql) throws SQLException {
		if (statement == null) {
			throw new SQLException("statement为空，请先调用beginBatchWithStatement方法");
		}
		statement.addBatch(sql);
	}

	public void addBatchByList(List<Object> parameters) throws SQLException {
		if (ps == null) {
			throw new SQLException(
					"statement为空，请先调用beginBatchWithPreparedStatement方法");
		}
		setUpParamtersByList(batchSqlWithPreparedStatement, ps, parameters);
	}

	public void addBatchByMap(Map<String, Object> parameters)
			throws SQLException {
		if (ps == null) {
			throw new SQLException(
					"statement为空，请先调用beginBatchWithPreparedStatement方法");
		}
		List<Object> parameter = getSqlParameterValues(originalSql, parameters);
		setUpParamtersByList(batchSqlWithPreparedStatement, ps, parameter);
	}

	public int[] excuteBatchWithStatement() throws SQLException {

		if (statement == null) {
			throw new SQLException("statement为空，请先调用beginBatchWithStatement方法");
		}
		try {
			return statement.executeBatch();
		} catch (SQLException e) {
			rollback();
			throw e;
		} finally {
			endBatchWithStatement();
		}
	}

	public int[] excuteBatchWithPreparedStatement() throws SQLException {
		if (ps == null) {
			throw new SQLException(
					"statement为空，请先调用beginBatchWithPreparedStatement方法");
		}
		try {
			return ps.executeBatch();
		} catch (SQLException e) {
			rollback();
			throw e;
		} finally {
			endBatchWithPreparedStatement();
		}
	}

	private void endBatch(Statement statement) throws SQLException {
		if (!transactionIsClosed()) {
			endTransaction();
		}
		if (statement != null) {
			closeStatement(statement);
			statement = null;
		}
	}

	private void endBatchWithStatement() throws SQLException {
		if (!transactionIsClosed()) {
			endTransaction();
		}
		if (statement != null) {
			closeStatement(statement);
			statement = null;
		}
	}

	private void endBatchWithPreparedStatement() throws SQLException {
		if (!transactionIsClosed()) {
			endTransaction();
		}
		if (ps != null) {
			closeStatement(ps);
			ps = null;
		}
	}

	public int deleteObject(String tableName, Object object,
			List<String> matchField, BaseCaseStrategy caseStrategy)
			throws SQLException {
		if (matchField == null || matchField.size() == 0) {
			throw new SQLException("配置字段为空,tableName:" + tableName + " Object:"
					+ object.getClass().getName());
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer where = new StringBuffer();
		sb.append("delete from " + tableName);
		//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
		Field[] fields = ClassUtil.getAccessibleFields(object);
		List<Object> params = new ArrayList<Object>();

		for (int i = 0; i < matchField.size(); i++) {
			if (i > 0) {
				where.append(" and ");
			}
			where.append(caseStrategy.getFieldName(matchField.get(i)) + "=?");
			try {
				params.add(getProperty(object, matchField.get(i)));
			} catch (Exception e) {
				
						
				throw new SQLException(e.getMessage());
			}
		}
		sb.append(" where ").append(where);
		return executeByList(sb.toString(), params);
	}

	protected void setCutOrderBy(boolean cutOrderBy) {
		this.cutOrderBy = cutOrderBy;
	}

	private String cutOrderByWithTotalCountSql(String sql) {
		Matcher matcher = orderByPattern.matcher(sql);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			if (matcher.hitEnd()) {
				matcher.appendReplacement(buffer, "");
			}
		}
		matcher.appendTail(buffer);
		return sql = buffer.toString();

	}

}
