/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: IDBSession.java
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
package com.adtec.framework.interfaces.db.session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.adtec.framework.impl.share.caseStrategy.BaseCaseStrategy;
import com.adtec.framework.interfaces.db.sqlstatictics.IStatHolder;
import com.adtec.framework.interfaces.db.storedProcedure.ProcedureParameter;
import com.adtec.framework.interfaces.share.IDataset;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDBSession.
 * 
 * @author chenyl
 */
public interface IDBSession {

	/**
	 * 启动事务，事务容器将从默认数据源中取连接.
	 * 
	 * @throws SQLException
	 *             the sQL exception
	 */
	public void beginTransaction() throws SQLException;

	/**
	 * 事务关闭.
	 * 
	 * @throws SQLException
	 *             the sQL exception
	 */
	public void endTransaction() throws SQLException;

	/**
	 * 回滚事务.
	 * 
	 * @throws SQLException
	 *             the sQL exception
	 */
	public void rollback() throws SQLException;

	/**
	 * 返回最后增加的最增长标识<br>
	 * <font color=read>注意，如果在当前操作中有触发器，且触发器中有插入语句，则此方法会返回错误的主键</font>.
	 * 
	 * @return the auto increase key
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int getAutoIncreaseKey() throws SQLException;

	/**
	 * 返回最后增加的最增长标识 <br>
	 * <font color=read>注意，如果在当前操作中有触发器，且触发器中有插入语句，则此方法会返回错误的主键</font>.
	 * 
	 * @param table_name
	 *            the table_name
	 * @return the auto increase key
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int getAutoIncreaseKey(String table_name) throws SQLException;

	/**
	 * 清理session.
	 * 
	 * @throws SQLException
	 *             the sQL exception
	 */
	public void clear() throws SQLException;

	/**
	 * 判断当前事务是否已经结束.
	 * 
	 * @return true, if successful
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public boolean transactionIsClosed() throws SQLException;

	/**
	 * 从当前session中获得连接.
	 * 
	 * @return the connection
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * 查询，返回一个原生结果集.
	 * 
	 * @param sql
	 *            the sql
	 * @return 原生结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSet(String sql) throws SQLException;

	/**
	 * SQL查询，返回数据集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @return 数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSet(String sql) throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetForPage(String sql, int start, int limit)
			throws SQLException;

	/**
	 * SQL执行器.
	 * 
	 * @param sql
	 *            输入的sql 如：insert into user(name,id) valuse("name","id");
	 * @return 影响条数
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int execute(String sql) throws SQLException;

	/**
	 * 查询，返回一个原生结果集.
	 * 
	 * @param sql
	 *            the sql
	 * @param parameters
	 *            the parameters
	 * @return 原生结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSet(String sql, Object... parameters)
			throws SQLException;

	/**
	 * 查询，返回一个原生结果集.
	 * 
	 * @param sql
	 *            the sql
	 * @param parameters
	 *            the parameters
	 * @return 原生结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSetByList(String sql, List<Object> parameters)
			throws SQLException;

	/**
	 * 查询，返回一个原生结果集.
	 * 
	 * @param sql
	 *            形如 select * form A where id=@id and b=@b;
	 * @param parameters
	 *            the parameters
	 * @return 原生结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSetByMap(String sql,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回数据集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param parameters
	 *            输入参数
	 * @return 数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSet(String sql, Object... parameters)
			throws SQLException;

	/**
	 * SQL查询，返回数据集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param parameters
	 *            输入参数
	 * @return 数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByList(String sql, List<Object> parameters)
			throws SQLException;

	/**
	 * SQL查询，返回数据集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param parameters
	 *            输入参数
	 * @return 数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByMap(String sql, Map<String, Object> parameters)
			throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            输入参数
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSetForPage(String sql, int start, int limit,
			Object... parameters) throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            输入参数
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSetByListForPage(String sql, int start,
			int limit, List<Object> parameters) throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            输入参数
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet getResultSetByMapForPage(String sql, int start, int limit,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            输入参数
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetForPage(String sql, int start, int limit,
			Object... parameters) throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            输入参数
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByListForPage(String sql, int start, int limit,
			List<Object> parameters) throws SQLException;

	/**
	 * 分页查询，返回结果集.
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            输入参数
	 * @return 结果数据集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByMapForPage(String sql, int start, int limit,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * SQL执行器.
	 * 
	 * @param sql
	 *            输入的sql 如：insert into user(name,id) valuse(T,T);
	 * @param parameters
	 *            占位参数值，如"name","id"
	 * @return 影响条数
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int execute(String sql, Object... parameters) throws SQLException;

	/**
	 * SQL执行器.
	 * 
	 * @param sql
	 *            输入的sql 如：insert into user(name,id) valuse(T,T);
	 * @param parameters
	 *            占位参数值，如"name","id"
	 * @return 影响条数
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int executeByList(String sql, List<Object> parameters)
			throws SQLException;

	/**
	 * SQL执行器.
	 * 
	 * @param sql
	 *            输入的sql 如：insert into user(name,id) valuse(T,T);
	 * @param parameters
	 *            占位参数值，如"name","id"
	 * @return 影响条数
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int executeByMap(String sql, Map<String, Object> parameters)
			throws SQLException;

	/**
	 * 统计条数.
	 * 
	 * @param sql
	 *            形如select count(*) from aa where id="1000"
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int account(String sql) throws SQLException;

	/**
	 * 统计条数.
	 * 
	 * @param sql
	 *            select count(*) from aa where id=T;
	 * @param parameters
	 *            占位符参数
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int account(String sql, Object... parameters) throws SQLException;

	/**
	 * 统计条数.
	 * 
	 * @param sql
	 *            select count(*) from aa where id=T;
	 * @param parameters
	 *            占位符参数
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int accountByList(String sql, List<Object> parameters)
			throws SQLException;

	/**
	 * 统计条数.
	 * 
	 * @param sql
	 *            select count(*) from aa where id=@id;
	 * @param parameters
	 *            占位符参数
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int accountByMap(String sql, Map<String, Object> parameters)
			throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectList(String sql, Class<T> objectType)
			throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param baseCaseStrategy
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectList(String sql, Class<T> objectType, BaseCaseStrategy baseCaseStrategy)
			throws SQLException;

	/**
	 * 查询单条记录.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @return 结果对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getObject(String sql, Class<T> objectType) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectList(String sql, Class<T> objectType,
			Object... parameters) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByList(String sql, Class<T> objectType,
			List<Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            输入参数
	 * @param baseCaseStrategy
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByList(String sql, Class<T> objectType,
										   List<Object> parameters, BaseCaseStrategy baseCaseStrategy) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByListForPage(String sql,
			Class<T> objectType, int start, int limit, List<Object> parameters)
			throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            输入参数
	 * @param baseCaseStrategy
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByListForPage(String sql,
												  Class<T> objectType, int start, int limit, List<Object> parameters, BaseCaseStrategy baseCaseStrategy)
			throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByMap(String sql, Class<T> objectType,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByMapForPage(String sql,
			Class<T> objectType, int start, int limit,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListForPage(String sql, Class<T> objectType,
			int start, int limit, Object... parameters) throws SQLException;

	/**
	 * SQL查询，返回列表对象.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param baseCaseStrategy
	 * @param parameters
	 *            输入参数
	 * @return 列表对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListForPage(String sql, Class<T> objectType,
											int start, int limit, BaseCaseStrategy baseCaseStrategy, Object... parameters) throws SQLException;

	/**
	 * 查询单条记录.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            the parameters
	 * @return 结果对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getObject(String sql, Class<T> objectType,
			Object... parameters) throws SQLException;

	/**
	 * 查询单条记录.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param baseCaseStrategy
	 * @param parameters
	 *            the parameters
	 * @return 结果对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getObject(String sql, Class<T> objectType, BaseCaseStrategy baseCaseStrategy,
						   Object... parameters) throws SQLException;

	/**
	 * 查询单条记录.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            the parameters
	 * @return 结果对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getObjectByList(String sql, Class<T> objectType,
			List<Object> parameters) throws SQLException;

	/**
	 * 查询单条记录.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            查询的SQL
	 * @param objectType
	 *            返回的对象类型
	 * @param parameters
	 *            the parameters
	 * @return 结果对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getObjectByMap(String sql, Class<T> objectType,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * 关闭结果集.
	 * 
	 * @param resultSet
	 *            the result set
	 * @throws SQLException
	 *             the sQL exception
	 */
	// public void closeResultSet(ResultSet resultSet) throws SQLException;
	/**
	 * 关闭ResultSet与Statement
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public void closeResultSetAndStatement(ResultSet resultSet)
			throws SQLException;

	/**
	 * 保存一个对象 在参数中送baseCaseStrategy
	 *
	 * @param tableName
	 *            表名
	 * @param obj
	 *            对象
	 * @param ignoreFields
	 *            the ignore fields
	 * @param baseCaseStrategy
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int saveObject(String tableName, Object obj,
						  List<String> ignoreFields, BaseCaseStrategy baseCaseStrategy) throws SQLException;

	/**
	 * 保存一个对象 userName->user_name.
	 * 
	 * @param tableName
	 *            表名
	 * @param obj
	 *            对象
	 * @param ignoreFields
	 *            the ignore fields
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int saveObject(String tableName, Object obj,
			List<String> ignoreFields) throws SQLException;

	/**
	 * 保存一个对象.
	 * 
	 * @param tableName
	 *            表名
	 * @param obj
	 *            对象
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int saveObject(String tableName, Object obj) throws SQLException;

	/**
	 * 更新一个对象.
	 * 
	 * @param tableName
	 *            表名
	 * @param obj
	 *            对象
	 * @param matchFields
	 *            the match fields
	 * @param ignoreFields
	 *            the ignore fields
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int updateObject(String tableName, Object obj,
			List<String> matchFields, List<String> ignoreFields)
			throws SQLException;

	/**
	 * 更新一个对象.
	 *
	 * @param tableName
	 *            表名
	 * @param obj
	 *            对象
	 * @param matchFields
	 *            the match fields
	 * @param ignoreFields
	 *            the ignore fields
	 * @param baseCaseStrategy
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int updateObject(String tableName, Object obj,
							List<String> matchFields, List<String> ignoreFields, BaseCaseStrategy baseCaseStrategy)
			throws SQLException;

	/**
	 * 更新一个对象.
	 * 
	 * @param tableName
	 *            表名
	 * @param obj
	 *            对象
	 * @param matchFields
	 *            the match fields
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public int updateObject(String tableName, Object obj,
			List<String> matchFields) throws SQLException;

	/**
	 * 获得session方言对象.
	 * 
	 * @return 方言对象
	 */
	public String getDialectName();

	/**
	 * 获得datasource名字.
	 * 
	 * @return the data source name
	 * @return
	 */
	public String getDataSourceName();

	/**
	 * 设置datasource名字.
	 * 
	 * @param dataSourceName
	 *            the new data source name
	 */
	public void setDataSourceName(String dataSourceName);

	/**
	 * 获得dbtype.
	 * 
	 * @return the dbtype
	 * @return
	 */
	public String getDbtype();

	/**
	 * 设置dbtype.
	 * 
	 * @param dbtype
	 *            the new dbtype
	 * @return
	 */
	public void setDbtype(String dbtype);

	/**
	 * 根据rs装配object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param rs
	 *            源结果集
	 * @param clz
	 *            object类
	 * @return 装配完成的object
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getObjectFromResultSet(ResultSet rs, Class<T> clz)
			throws SQLException;

	/**
	 * 获得数据库时间.
	 * 
	 * @return java.util.Date 数据库时间
	 * @throws SQLException
	 *             the sQL exception
	 */
	public java.util.Date getSysDate() throws SQLException;

	/**
	 * 存储过程调用,主要开放给工具调用.
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @param parameterList
	 *            参数列表,详细见ProcedureParameter类定义
	 * @param isCursorBack
	 *            是否返回游标
	 * @return 输出结果集及输出参数，returncode
	 * @throws SQLException
	 *             the sQL exception
	 */
	public Map callProcedure(String procedureName,
			List<ProcedureParameter> parameterList, boolean isCursorBack)
			throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount(总记录条数)，通过IDataset getTotalCount方法进行引用。.
	 * 
	 * @param sql
	 *            查询语句
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetHasTotalCount(String sql) throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetHasTotalCount(String sql, int start, int limit)
			throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param parameters
	 *            查询参数
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByVarargsHasTotalCount(String sql,
			Object... parameters) throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param parameters
	 *            List查询参数
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByListHasTotalCount(String sql,
			List<Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param parameters
	 *            Map查询参数
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByMapHasTotalCount(String sql,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            参数
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByVarargsHasTotalCount(String sql, int start,
			int limit, Object... parameters) throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            List参数
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByListHasTotalCount(String sql, int start,
			int limit, List<Object> parameters) throws SQLException;

	/**
	 * SQL查询，返回数据集，包含TotalCount。.
	 * 
	 * @param sql
	 *            查询语句
	 * @param start
	 *            开始记录
	 * @param limit
	 *            页面大小
	 * @param parameters
	 *            Map参数
	 * @return 返回结果集
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByMapHasTotalCount(String sql, int start,
			int limit, Map<String, Object> parameters) throws SQLException;

	/**
	 * 获得dataSource username.
	 * 
	 * @return the data source username
	 * @return
	 */
	public String getDataSourceUsername();

	/**
	 * 获得dataSource password.
	 * 
	 * @return the data source password
	 * @return
	 */
	public String getDataSourcePassword();

	/**
	 * Gets the first record object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            需要执行的sql语句
	 * @param classType
	 *            对象类类型
	 * @param parameters
	 *            参数链表
	 * @return 返回第一个对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getFirstRecordObject(String sql, Class<T> classType,
			Object... parameters) throws SQLException;

	/**
	 * 返回第一条记录的IDataset.
	 * 
	 * @param sql
	 *            sql语句
	 * @param parameters
	 *            执行参数
	 * @return the first record dataset
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public IDataset getFirstRecordDataset(String sql, Object... parameters)
			throws SQLException;

	/**
	 * Gets the first record object by list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            需要执行的sql语句
	 * @param classType
	 *            对象类型
	 * @param list
	 *            参数链表
	 * @return 返回查询交易的第一个对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getFirstRecordObjectByList(String sql, Class<T> classType,
			List<Object> list) throws SQLException;

	/**
	 * 获取第一条记录以IDataset返回.
	 * 
	 * @param sql
	 *            sql语句
	 * @param list
	 *            参数链表
	 * @return the first record dataset by list
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public IDataset getFirstRecordDatasetByList(String sql, List<Object> list)
			throws SQLException;

	/**
	 * Gets the first record object by map.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            需要执行的sql语句
	 * @param classType
	 *            对象类型
	 * @param map
	 *            the map
	 * @return 返回查询交易的第一个对象
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getFirstRecordObjectByMap(String sql, Class<T> classType,
			Map<String, Object> map) throws SQLException;

	/**
	 * 获取第一条记录以IDataset返回.
	 * 
	 * @param sql
	 *            sql语句
	 * @param map
	 *            参数表
	 * @return the first record dataset by map
	 * @throws SQLException
	 *             the sQL exception
	 * @return
	 */
	public IDataset getFirstRecordDatasetByMap(String sql,
			Map<String, Object> map) throws SQLException;

	/**
	 * 是否采用游标分页.
	 * 
	 * @return true, if is cursor
	 * @return
	 */
	public boolean isCursor();

	/**
	 * 设置游标分页 true游标分页 false方言分页.
	 * 
	 * @param isCursor
	 *            the new cursor
	 * @return
	 */
	public void setCursor(boolean isCursor);

	/**
	 * resultset是否可以调用previous等操作.
	 * 
	 * @return true, if is scroll
	 * @return
	 */
	public boolean isScroll();

	/**
	 * 设置resultset实现方式.
	 * 
	 * @param isScroll
	 *            the new scroll
	 */
	public void setScroll(boolean isScroll);

	/**
	 * auto rollback on close.
	 * 
	 * @return true, if is auto rollback
	 * @return
	 */
	public boolean isAutoRollback();

	/**
	 * auto rollback on close.
	 * 
	 * @param autoRollback
	 *            the new auto rollback
	 */
	public void setAutoRollback(boolean autoRollback);

	/**
	 * Hs std save object.
	 * 
	 * @param tableName
	 *            the table name
	 * @param object
	 *            the object
	 * @param ignoreField
	 *            the ignore field
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int hsStdSaveObject(String tableName, Object object,
			List<String> ignoreField) throws SQLException;

	/**
	 * Hs std save object.
	 * 
	 * @param tableName
	 *            the table name
	 * @param object
	 *            the object
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int hsStdSaveObject(String tableName, Object object)
			throws SQLException;

	/**
	 * Hs std update object.
	 * 
	 * @param tableName
	 *            the table name
	 * @param object
	 *            the object
	 * @param matchField
	 *            the match field
	 * @param ignoreField
	 *            the ignore field
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int hsStdUpdateObject(String tableName, Object object,
			List<String> matchField, List<String> ignoreField)
			throws SQLException;

	/**
	 * Hs std update object.
	 * 
	 * @param tableName
	 *            the table name
	 * @param object
	 *            the object
	 * @param matchField
	 *            the match field
	 * @return the int
	 * @throws SQLException
	 *             the sQL exception
	 */
	public int hsStdUpdateObject(String tableName, Object object,
			List<String> matchField) throws SQLException;

	/**
	 * Gets the hs std object from result set.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param rs
	 *            the rs
	 * @param clz
	 *            the clz
	 * @return the hs std object from result set
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdObjectFromResultSet(ResultSet rs, Class<T> clz)
			throws SQLException;

	/**
	 * Gets the hs std first record object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param classType
	 *            the class type
	 * @param parameters
	 *            the parameters
	 * @return the hs std first record object
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdFirstRecordObject(String sql, Class<T> classType,
			Object... parameters) throws SQLException;

	/**
	 * Gets the hs std first record object by list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param classType
	 *            the class type
	 * @param list
	 *            the list
	 * @return the hs std first record object by list
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdFirstRecordObjectByList(String sql,
			Class<T> classType, List<Object> list) throws SQLException;

	/**
	 * Gets the hs std first record object by map.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param classType
	 *            the class type
	 * @param map
	 *            the map
	 * @return the hs std first record object by map
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdFirstRecordObjectByMap(String sql, Class<T> classType,
			Map<String, Object> map) throws SQLException;

	/**
	 * Gets the hs std object list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @return the hs std object list
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectList(String sql, Class<T> clz)
			throws SQLException;

	/**
	 * Gets the hs std object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @return the hs std object
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdObject(String sql, Class<T> clz) throws SQLException;

	/**
	 * Gets the hs std object list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param parameters
	 *            the parameters
	 * @return the hs std object list
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectList(String sql, Class<T> clz,
			Object... parameters) throws SQLException;

	/**
	 * Gets the hs std object list by list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param parameters
	 *            the parameters
	 * @return the hs std object list by list
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectListByList(String sql, Class<T> clz,
			List<Object> parameters) throws SQLException;

	/**
	 * Gets the hs std object list by list for page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the hs std object list by list for page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectListByListForPage(String sql,
			Class<T> clz, int start, int limit, List<Object> parameters)
			throws SQLException;

	/**
	 * Gets the hs std object list by map.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param parameters
	 *            the parameters
	 * @return the hs std object list by map
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectListByMap(String sql, Class<T> clz,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * Gets the hs std object list by map for page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the hs std object list by map for page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectListByMapForPage(String sql, Class<T> clz,
			int start, int limit, Map<String, Object> parameters)
			throws SQLException;

	/**
	 * Gets the hs std object list for page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the hs std object list for page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getHsStdObjectListForPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException;

	/**
	 * Gets the hs std object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param parameters
	 *            the parameters
	 * @return the hs std object
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdObject(String sql, Class<T> clz, Object... parameters)
			throws SQLException;

	/**
	 * Gets the hs std object by list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param parameters
	 *            the parameters
	 * @return the hs std object by list
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdObjectByList(String sql, Class<T> clz,
			List<Object> parameters) throws SQLException;

	/**
	 * Gets the hs std object by map.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param parameters
	 *            the parameters
	 * @return the hs std object by map
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> T getHsStdObjectByMap(String sql, Class<T> clz,
			Map<String, Object> parameters) throws SQLException;

	/**
	 * Gets the uuid.
	 * 
	 * @return the uuid
	 */
	public com.adtec.framework.common.util.UUID getUuid();

	/**
	 * Sets the uuid.
	 * 
	 * @param uuid
	 *            the new uuid
	 */
	public void setUuid(com.adtec.framework.common.util.UUID uuid);

	/**
	 * Gets the fetch size.
	 * 
	 * @return the fetch size
	 */
	public int getFetchSize();

	/**
	 * Sets the fetch size.
	 * 
	 * @param fetchSize
	 *            the new fetch size
	 */
	public void setFetchSize(int fetchSize);

	/**
	 * 返回值IDataset分页接口 增加的显示游标分页和显示方言分页接口.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the data set for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetForDialectPage(String sql, int start, int limit)
			throws SQLException;

	/**
	 * Gets the data set for cursor page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the data set for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetForCursorPage(String sql, int start, int limit)
			throws SQLException;

	/**
	 * Gets the data set for dialect page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the data set for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetForDialectPage(String sql, int start, int limit,
			Object... parameters) throws SQLException;

	/**
	 * Gets the data set for cursor page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the data set for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetForCursorPage(String sql, int start, int limit,
			Object... parameters) throws SQLException;

	/**
	 * Gets the data set by list for dialect page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the data set by list for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByListForDialectPage(String sql, int start,
			int limit, List<Object> parameters) throws SQLException;

	/**
	 * Gets the data set by list for cursor page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the data set by list for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByListForCursorPage(String sql, int start,
			int limit, List<Object> parameters) throws SQLException;

	/**
	 * Gets the data set by map for dialect page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the data set by map for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByMapForDialectPage(String sql, int start,
			int limit, Map<String, Object> parameters) throws SQLException;

	/**
	 * Gets the data set by map for cursor page.
	 * 
	 * @param sql
	 *            the sql
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the data set by map for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public IDataset getDataSetByMapForCursorPage(String sql, int start,
			int limit, Map<String, Object> parameters) throws SQLException;

	/**
	 * 返回值List<T>分页接口 增加的显示游标分页和显示方言分页接口.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the object list for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListForDialectPage(String sql, Class<T> clz,
			int start, int limit) throws SQLException;

	/**
	 * Gets the object list for cursor page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the object list for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListForCursorPage(String sql, Class<T> clz,
			int start, int limit) throws SQLException;

	/**
	 * Gets the object list for dialect page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the object list for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListForDialectPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException;

	/**
	 * Gets the object list for cursor page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the object list for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListForCursorPage(String sql, Class<T> clz,
			int start, int limit, Object... parameters) throws SQLException;

	/**
	 * Gets the object list by list for dialect page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the object list by list for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByListForDialectPage(String sql,
			Class<T> clz, int start, int limit, List<Object> parameters)
			throws SQLException;

	/**
	 * Gets the object list by list for cursor page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the object list by list for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByListForCursorPage(String sql,
			Class<T> clz, int start, int limit, List<Object> parameters)
			throws SQLException;

	/**
	 * Gets the object list by map for dialect page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the object list by map for dialect page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByMapForDialectPage(String sql,
			Class<T> clz, int start, int limit, Map<String, Object> parameters)
			throws SQLException;

	/**
	 * Gets the object list by map for cursor page.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            the sql
	 * @param clz
	 *            the clz
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the object list by map for cursor page
	 * @throws SQLException
	 *             the sQL exception
	 */
	public <T> List<T> getObjectListByMapForCursorPage(String sql,
			Class<T> clz, int start, int limit, Map<String, Object> parameters)
			throws SQLException;

	/**
	 * 对指定列进行统计操作，统计列信息保存在IStatHolder接口中，
	 * 例如通过statHolder.addStatColumn("employee_id","sum")方法增加需要统计的列*信息，
	 * 接口会查询IstatHolder中所有的统计列信息，并把统计列信息转化成sql统计函数，
	 * 在原有sql语句基础上拼接成sql统计信息的查询语句。最后执行该语句，
	 * 把查询出来的记录转化成单行多列的IDataset.IDataset的列名称规定为“统计列名称”格式，
	 * 例如对employee_id进行sum统计操作，则IDataset的列名称为：employee_id.
	 * 因此可以通过IDataset.getString(“employee_id”)获得该列的统计值。
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param statInfo
	 *            SQL统计列信息持有接口，保存所有进行统计的列信息
	 * @param parameters
	 *            sql语句的参数信息
	 * @return 返回的列统计信息，是一个单行多列的IDataset
	 */
	public IDataset getDatasetByArray(String sql, IStatHolder statInfo,
			Object... parameters) throws SQLException;

	/**
	 * 对指定列进行统计操作，统计列信息保存在IStatHolder接口中，
	 * 例如通过statHolder.addStatColumn("employee_id","sum")方法增加需要统计的列信息，
	 * 接口会查询IstatHolder中所有的统计列信息，并把统计列信息转化成sql统计函数，
	 * 在原有sql语句基础上拼接成sql统计信息的查询语句。最后执行该语句，
	 * 把查询出来的记录转化成单行多列的IDataset.IDataset的列名称规定为“统计列名称”格式，
	 * 例如对employee_id进行sum统计操作，则IDataset的列名称为：employee_id.
	 * 因此可以通过IDataset.getString(“employee_id”)获得该列的统计值。
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param statInfo
	 *            SQL统计列信息持有接口，保存所有进行统计的列信息
	 * @param parameters
	 *            sql语句的参数信息
	 * @return 返回的列统计信息，是一个单行多列的IDataset
	 */
	public IDataset getDatasetByList(String sql, IStatHolder statInfo,
			List parameters) throws SQLException;

	/**
	 * 对指定列进行统计操作，统计列信息保存在IStatHolder接口中，
	 * 例如通过statHolder.addStatColumn("employee_id","sum")方法增加需要统计的列信息，
	 * 接口会查询IstatHolder中所有的统计列信息，并把统计列信息转化成sql统计函数，
	 * 在原有sql语句基础上拼接成sql统计信息的查询语句。最后执行该语句，
	 * 把查询出来的记录转化成单行多列的IDataset.IDataset的列名称规定为“统计列名称”格式，
	 * 例如对employee_id进行sum统计操作，则IDataset的列名称为：employee_id.
	 * 因此可以通过IDataset.getString(“employee_id”)获得该列的统计值。
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param statInfo
	 *            SQL统计列信息持有接口，保存所有进行统计的列信息
	 * @param parameters
	 *            sql语句的参数信息
	 * @return 返回的列统计信息，是一个单行多列的IDataset
	 */
	public IDataset getDatasetByMap(String sql, IStatHolder statInfo,
			Map parameters) throws SQLException;

	/**
	 * 根据Object删除表记录，采用(驼峰-下划线规则)，如(对象成员名userName)对应(表字段名user_name)
	 * 
	 * @param tableName
	 *            表名
	 * @param object
	 *            对象
	 * @param matchField
	 *            用做匹配的对象成员名称列表，matchField字段在生成的SQL语句中仅做 "=" 匹配，该参数不允许为null或为空
	 * @return 影响记录条数，如果什么都没做返回0
	 * 
	 * @throws SQLException
	 */
	public int deleteObject(String tableName, Object object,
			List<String> matchField) throws SQLException;

	/**
	 * 根据Object删除表记录，在参数中送baseCaseStrategy
	 *
	 * @param tableName
	 *            表名
	 * @param object
	 *            对象
	 * @param matchField
	 *            用做匹配的对象成员名称列表，matchField字段在生成的SQL语句中仅做 "=" 匹配，该参数不允许为null或为空
	 * @param baseCaseStrategy
	 * @return 影响记录条数，如果什么都没做返回0
	 *
	 * @throws SQLException
	 */
	public int deleteObject(String tableName, Object object,
							List<String> matchField, BaseCaseStrategy baseCaseStrategy) throws SQLException;

	/**
	 * 根据Object删除表记录
	 * 
	 * @param tableName
	 *            表名
	 * @param object
	 *            对象
	 * @param matchField
	 *            用做匹配的对象成员名称列表，matchField字段在生成的SQL语句中仅做 "=" 匹配，该参数不允许为null或为空
	 * @return 影响记录条数，如果什么都没做返回0
	 * 
	 * @throws SQLException
	 */
	public int hsStdDeleteObject(String tableName, Object object,
			List<String> matchField) throws SQLException;
	
	/**
	 * 
	 * 使用Statement对象进行批处理操作
	 * @param sqls 要进行批处理的sql语句，不带参数的
	 * @throws SQLException
	 */
	public int[] executeBatch(List<String> sqls)throws SQLException;
	/**
	 * 
	 * 使用PreparedStatement进行批处理操作 
	 * @param sql 预编译的sql语句
	 * @param parameters 以list方式存储的sql参数集
	 * @throws SQLException
	 */
	public int[] executeBatchByList(String sql,List<List<Object>> parameters)throws SQLException;
	
	/**
	 * 
	 * 使用PreparedStatement进行批处理操作 
	 * @param sql 预编译的sql语句
	 * @param parameters 以map方式存储的sql参数集
	 * @throws SQLException
	 */
	public int[] executeBatchByMap(String sql,List<Map<String,Object>>parameters)throws SQLException;

	
	/**
	 * 
	 * 开始批处理功能，完成的是初始化批处理操作的Statement 
	 * @return true：支持批处理 false：不支持批处理
	 */
	public boolean beginBatchWithStatement()throws SQLException;
	
	/**
	 * 
	 * 开始批处理功能，完成的是初始化批处理操作的PreparedStatement 
	 * @param sql 预编译的sql语句
	 * @return true：支持批处理 false：不支持批处理
	 */
	public boolean beginBatchWithPreparedStatement(String sql)throws SQLException;
	
	/**
	 * 
	 * Statement批处理方式增加批处理sql命令列表
	 * @param sql
	 * @throws SQLException
	 */
	public void addBatchSql(String sql)throws SQLException;
	
	/**
	 * 
	 * PreparedStatement批处理方式 对预编译的sql语句设置参数
	 * @param sql 需要增加的批处理语句
	 * @param parameters sql语句对应的参数值
	 */
	public void addBatchByList(List<Object> parameters)throws SQLException;
	
	/**
	 * 
	 * PreparedStatement批处理方式 对预编译的sql语句设置参数
	 * @param sql 需要增加的批处理语句
	 * @param parameters sql语句对应的参数值
	 */
	public void addBatchByMap(Map<String,Object> parameters)throws SQLException;
	
	/**
	 * 
	 * Statement批处理执行sql命令列表
	 */
	public int[] excuteBatchWithStatement()throws SQLException;
	
	/**
	 * 
	 * PreparedStatement批处理执行sql命令列表
	 */
	public int[] excuteBatchWithPreparedStatement()throws SQLException;

	public <T> void setProperty(T obj, String fieldName, ResultSet rs, int index) throws Exception;
}
