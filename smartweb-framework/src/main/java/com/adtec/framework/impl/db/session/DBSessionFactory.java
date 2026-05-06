
package com.adtec.framework.impl.db.session;

import com.adtec.framework.common.functions.Func;
import com.adtec.framework.common.functions.FuncP;
import com.adtec.framework.common.functions.FuncR;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.impl.dbspring.datasource.DataSourceFactory;
import com.adtec.framework.interfaces.db.handler.*;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.db.session.IDBSessionFactory;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.pool.SmThreadPoolExecutor;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DBSessionFactory {

	protected static final Logger log = LoggerFactory.getLogger(DBSessionFactory.class);
	/**
	 * 根据数据源缓存 Db对象，key为 DatasourceName，value为 Db对象，此 Map中的 Db对象使用 {@link DBSessionFactory#getSession()}方法获取 Session
	 */
	private static final Map<String, DBSessionFactory> DB_MAP = new ConcurrentHashMap<>();
	/**
	 * 根据数据源缓存 Db对象，key为 DatasourceName，value为 Db对象，此 Map中的 Db对象使用 {@link DBSessionFactory#getNewSession()}方法获取 Session
	 */
	private static final Map<String, DBSessionFactory> DB_N_MAP = new ConcurrentHashMap<>();
	/**
	 * 默认使用的数据源名称
	 */
	private static final String DEFAULT_DATASOURCE_NAME = DataSourceFactory.DEFALUT_DATASOURCE_NAME;

	private static final FuncP<Exception> DEFAULT_EXCEPTION_HANDLER = new FuncP<Exception>() {
		@Override
		public void call(Exception e) throws Exception {
			e.printStackTrace();
		}
	};
	/**
	 * 数据库类型
	 */
	private final String dbType;
	/**
	 * 数据源
	 */
	private final DataSource dataSource;
	/**
	 * 数据源名称
	 */
	private final String dataSourceName;
	/**
	 * 是否使用 {@link DBSessionFactory#getNewSession()}方法获取 Session.
	 */
	private final boolean useNewSession;

	static IDBSessionFactory dbSessionFactoryAdapter;

	static {
		try {
			String classNameString = ParamUtil.getConfig("dbSessionFactoryImplClass");
			if (DataUtil.isNullStr(classNameString)) {
				classNameString = "com.adtec.framework.impl.dbspring.session.DBSessionFactory4Spring";
			}
			dbSessionFactoryAdapter = (IDBSessionFactory) Class.forName(
					classNameString).newInstance();
			// 类加载时加入默认数据源
			DB_MAP.put(DEFAULT_DATASOURCE_NAME, new DBSessionFactory(false));
			DB_N_MAP.put(DEFAULT_DATASOURCE_NAME, new DBSessionFactory(true));
		} catch (Exception e) {
			// 插件框架未启动，因此输出到异常栈
			e.printStackTrace();
		}
	}

	/**
	 * 构造方法
	 *
	 * @param useNewSession Db 对象是否使用 {@link DBSessionFactory#getNewSession()}方法获取 Session.
	 */
	private DBSessionFactory(boolean useNewSession) {
		this.useNewSession = useNewSession;
		this.dataSourceName = DEFAULT_DATASOURCE_NAME;
		this.dataSource = DataSourceFactory.getDataSource(DEFAULT_DATASOURCE_NAME);
		this.dbType = ((DruidDataSource) this.dataSource).getDbType();
	}

	/**
	 * 构造方法
	 *
	 * @param dataSourceName 数据源名称
	 * @param useNewSession  Db 对象是否使用 {@link DBSessionFactory#getNewSession()}方法获取 Session.
	 */
	private DBSessionFactory(String dataSourceName, boolean useNewSession) {
		this.useNewSession = useNewSession;
		this.dataSourceName = dataSourceName;
		this.dataSource = DataSourceFactory.getDataSource(dataSourceName);
		this.dbType = ((DruidDataSource) this.dataSource).getDbType();
	}

	/**
	 * 切换当前线程默认数据源
	 *
	 * @param dataSourceName 数据源名称
	 */
	public static void switchDataSource(String dataSourceName) {
		if (dataSourceName != null) {
			GVarContainer.setVar(DataSourceFactory.GV_DATASOURCE_NAME, dataSourceName);
		}
	}

	/**
	 * 使用默认数据源，使用 {@link DBSessionFactory#getSession()}.
	 *
	 * @return Db
	 * <pre>
	 *     DBSessionFactory.use().xxx
	 * </pre>
	 */
	public static DBSessionFactory use() {
		return DB_MAP.get(DataSourceFactory.DEFALUT_DATASOURCE_NAME);
	}

	/**
	 * 使用指定数据源，使用 {@link DBSessionFactory#getSession()}.
	 *
	 * @param dataSourceName 数据源名称
	 * @return Db
	 * <pre>
	 *     DBSessionFactory.use("dataSourceName").xxx
	 * </pre>
	 */
	public static DBSessionFactory use(String dataSourceName) {
		assert dataSourceName != null;
		if (DB_MAP.containsKey(dataSourceName)) {
			return DB_MAP.get(dataSourceName);
		}
		DBSessionFactory db = new DBSessionFactory(dataSourceName, false);
		DB_MAP.put(dataSourceName, db);
		return db;
	}

	/**
	 * 使用默认数据源，使用 {@link DBSessionFactory#getNewSession()}，调用方法后会自动 {@link DBSessionFactory#closeSession(IDBSession)}.
	 *
	 * @return Db
	 * <pre>
	 *     DBSessionFactory.useN().xxx
	 * </pre>
	 */
	public static DBSessionFactory useN() {
		return DB_N_MAP.get(DEFAULT_DATASOURCE_NAME);
	}

	/**
	 * 使用指定数据源，使用 {@link DBSessionFactory#getNewSession()}，调用方法后会自动 {@link DBSessionFactory#closeSession(IDBSession)}.
	 *
	 * @param dataSourceName 数据源名称
	 * @return Db
	 * <pre>
	 *     DBSessionFactory.useN("dataSourceName").xxx
	 * </pre>
	 */
	public static DBSessionFactory useN(String dataSourceName) {
		assert dataSourceName != null;
		if (DB_N_MAP.containsKey(dataSourceName)) {
			return DB_N_MAP.get(dataSourceName);
		}
		DBSessionFactory db = new DBSessionFactory(dataSourceName, true);
		DB_N_MAP.put(dataSourceName, db);
		return db;
	}

	/**
	 * 获取当前连接的数据库类型
	 *
	 * @return 数据库类型
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * 获取当前使用的数据源
	 *
	 * @return dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 获取Session,同一个线程为一个。
	 *
	 * @return DBSession
	 */
	public static IDBSession getSession() {
		return dbSessionFactoryAdapter.getSession();
	}

	/**
	 * 获取一个新的Session,关闭后释放
	 *
	 * @return DBSession
	 */
	public static IDBSession getNewSession() {
		return dbSessionFactoryAdapter.getNewSession();
	}

	/**
	 * 获取事务连接对象
	 *
	 * @param dataSourceName 数据源名称
	 * @return DBSession
	 */
	public static Connection getTransactionConnection(String dataSourceName) {
		return dbSessionFactoryAdapter.getTransactionConnection(dataSourceName);
	}

	/**
	 * 获取Session,同一个线程为一个。
	 *
	 * @param dataSourceName 数据源名称
	 * @return DBSession
	 */
	public static IDBSession getSession(String dataSourceName) {
		String jndiUseFlg = ParamUtil.getConfig("datasource.jndi.use");
		if ("Y".equals(jndiUseFlg)) {
			dbSessionFactoryAdapter.getSession();
		}
		return dbSessionFactoryAdapter.getSession(dataSourceName);
	}

	/**
	 * 获取一个新的Session,关闭后释放
	 *
	 * @param dataSourceName 数据源名称
	 * @return DBSession
	 */
	public static IDBSession getNewSession(String dataSourceName) {
		String jndiUseFlg = ParamUtil.getConfig("datasource.jndi.use");
		if ("Y".equals(jndiUseFlg)) {
			dbSessionFactoryAdapter.getSession();
		}
		return dbSessionFactoryAdapter.getNewSession(dataSourceName);
	}

	/**
	 * 清理线程Session工厂.
	 */
	public static void clear() {
		dbSessionFactoryAdapter.clear();
	}

	/**
	 * 关闭session
	 */
	public static void closeSession(IDBSession session) throws SQLException {
		dbSessionFactoryAdapter.closeSession(session);
	}

	// ------------------------------------------------------------------------
	//  high level api
	// ------------------------------------------------------------------------

	/**
	 * 异步执行数据库操作，执行完自动释放数据库连接.
	 * 此方法为了避免在异步线程中使用 DBSessionFactory.getSession()后，发生 abandon connection 异常.
	 *
	 * @param handler   执行函数.
	 * @param exHandler 错误处理函数.
	 */
	public static void async(final Func handler, final FuncP<Exception> exHandler) {
		SpringContextHolder.getBean(SmThreadPoolExecutor.class).execute(new Runnable() {
			@Override
			public void run() {
				autoRelease(handler, exHandler);
			}
		});
	}

	/**
	 * 异步线程中使用此方法执行数据库操作，执行完会自动释放数据库连接.
	 * 此方法为了避免在异步线程中使用 DBSessionFactory.getSession()后，发生 abandon connection 异常.
	 *
	 * @param handler 执行函数.
	 */
	public static void autoRelease(final Func handler) {
		autoRelease(handler, DEFAULT_EXCEPTION_HANDLER);
	}

	/**
	 * 异步线程中使用此方法执行数据库操作，执行完会自动释放数据库连接.
	 * 此方法为了避免在异步线程中使用 DBSessionFactory.getSession()后，发生 abandon connection 异常.
	 *
	 * @param handler 执行函数.
	 */
	public static <R> R autoRelease(final FuncR<R> handler) {
		return autoRelease(handler, DEFAULT_EXCEPTION_HANDLER);
	}

	/**
	 * 异步线程中使用此方法执行数据库操作，执行完会自动释放数据库连接.
	 * 此方法为了避免在异步线程中使用 DBSessionFactory.getSession()后，发生 abandon connection 异常.
	 *
	 * @param handler   执行函数.
	 * @param exHandler 错误处理函数.
	 */
	public static void autoRelease(final Func handler, final FuncP<Exception> exHandler) {
		autoRelease(new FuncR<Object>() {
			@Override
			public Object call() throws Exception {
				handler.call();
				return null;
			}
		}, new FuncP<Exception>() {
			@Override
			public void call(Exception e) throws Exception {
				exHandler.call(e);
			}
		});
	}

	/**
	 * 异步线程中使用此方法执行数据库操作，执行完会自动释放数据库连接.
	 * 此方法为了避免在异步线程中使用 DBSessionFactory.getSession()后，发生 abandon connection 异常.
	 *
	 * @param handler   执行函数.
	 * @param exHandler 错误处理函数.
	 */
	public static <R> R autoRelease(final FuncR<R> handler, final FuncP<Exception> exHandler) {
		R r = null;
		try {
			r = handler.call();
		} catch (Exception e) {
			try {
				exHandler.call(e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			// 关闭当前线程的DBSession的数据库连接
			clear();
		}
		return r;
	}

	/**
	 * 事务操作.
	 *
	 * @param handler   事务函数，所有操作应在同一函数下执行，确保在同一事务中
	 * @param exHandler 错误处理器
	 * @return DBSessionFactory
	 * <pre>
	 *     DBSessionFactory.use().tx(new TxHandler() {
	 *         @Override
	 *         public void call(IDBSession session) {
	 *             // insert, update, delete
	 *         }
	 *     }, new ExceptionHandler() {
	 *         @Override
	 *         public void apply(IDBSession session, Exception ex) {
	 *             try {
	 *                 session.rollback();
	 *             } catch (SQLException e) {
	 *                 e.printStackTrace();
	 *             }
	 *             log.error(ex.getMessage());
	 *         }
	 *     });
	 * </pre>
	 */
	public DBSessionFactory tx(final TxHandler handler, ExceptionHandler exHandler) {
		execute(new Call<Object>() {
			@Override
			public Object handle(IDBSession session) throws Exception {
				// 开启事务
				session.beginTransaction();
				// 执行数据库操作
				handler.call(session);
				// 关闭事务
				session.endTransaction();
				return null;
			}
		}, exHandler);
		return this;
	}

	/**
	 * 事务操作，有返回值.
	 *
	 * @param handler   事务函数，所有操作应在同一函数下执行，确保在同一事务中
	 * @param exHandler 错误处理器
	 * @param <R>       返回值类型
	 * @return R
	 * <pre>
	 *     Integer result = DBSessionFactory.use().tx(new TxHandler<Integer>() {
	 *         @Override
	 *         public Integer call(IDBSession session) {
	 *             // insert, update, delete
	 *             return 1;
	 *         }
	 *     }, new ExceptionHandler() {
	 *         @Override
	 *         public void apply(IDBSession session, Exception ex) {
	 *             try {
	 *                 session.rollback();
	 *             } catch (SQLException e) {
	 *                 e.printStackTrace();
	 *             }
	 *             log.error(ex.getMessage());
	 *         }
	 *     });
	 * </pre>
	 */
	public <R> R tx(final TxHandlerR<R> handler, ExceptionHandler exHandler) {
		return execute(new Call<R>() {
			@Override
			public R handle(IDBSession session) throws Exception {
				// 开启事务
				session.beginTransaction();
				// 执行数据库操作
				R r = handler.call(session);
				// 关闭事务
				session.endTransaction();
				return r;
			}
		}, exHandler);
	}

	/**
	 * 事务操作，自动回滚.
	 *
	 * @param handler   事务函数，所有操作应在同一函数下执行，确保在同一事务中
	 * @param exHandler 错误处理器
	 * @return DBSessionFactory
	 */
	public DBSessionFactory txAutoRollback(final TxHandler handler, final ExceptionHandler exHandler) {
		tx(handler, new ExceptionHandler() {
			@Override
			public void apply(IDBSession session, Exception ex) {
				try {
					session.rollback();
				} catch (SQLException e) {
					log.error("事务回滚失败: ", e);
				} finally {
					exHandler.apply(session, ex);
				}
			}
		});
		return this;
	}

	/**
	 * 事务操作，有返回值，自动回滚.
	 *
	 * @param handler   事务函数，所有操作应在同一函数下执行，确保在同一事务中
	 * @param exHandler 错误处理器
	 * @param <R>       返回值类型
	 * @return R
	 */
	public <R> R txAutoRollback(final TxHandlerR<R> handler, final ExceptionHandler exHandler) {
		return tx(handler, new ExceptionHandler() {
			@Override
			public void apply(IDBSession session, Exception ex) {
				try {
					session.rollback();
				} catch (SQLException e) {
					log.error("事务回滚失败: ", e);
				} finally {
					exHandler.apply(session, ex);
				}
			}
		});
	}

	/**
	 * 查询
	 *
	 * @param sql       查询语句
	 * @param handler   ResultSet处理器
	 * @param exHandler 错误处理器
	 * @param params    参数
	 * @param <T>       返回类型
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *      String id = "1";
	 *      String result = DBSessionFactory.use().get("select * from tableName where id=?", new ResultSetHandler<String>() {
	 *             @Override
	 *             public String apply(ResultSet rs) {
	 *                 try {
	 *                     return rs.next() ? rs.getString(1) : null;
	 *                 } catch (SQLException e) {
	 *                     e.printStackTrace();
	 *                     return null;
	 *                 }
	 *             }
	 *         }, new ExceptionHandler() {
	 *             @Override
	 *             public void apply(IDBSession session, Exception ex) {
	 *                 log.error("error", ex);
	 *             }
	 *         }, id);
	 * </pre>
	 */
	public <T> T get(final String sql, ResultSetHandler<T> handler, ExceptionHandler exHandler, final Object... params) {
		return getByList(sql, handler, exHandler, Arrays.asList(params));
	}

	/**
	 * 查询
	 *
	 * @param sql       查询语句
	 * @param handler   ResultSet处理器
	 * @param exHandler 错误处理器
	 * @param params    参数
	 * @param <T>       返回类型
	 * @return 结果对象
	 */
	public <T> T getByList(final String sql, ResultSetHandler<T> handler, ExceptionHandler exHandler, final List<Object> params) {
		return handler.apply(execute(new Call<ResultSet>() {
			@Override
			public ResultSet handle(IDBSession session) throws Exception {
				return session.getResultSetByList(sql, params);
			}
		}, exHandler));
	}

	/**
	 * 查询单条单个字段记录,并将其转换为String.
	 *
	 * @param sql       查询语句
	 * @param exHandler 错误处理器
	 * @param params    参数
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     String result = DBSessionFactory.use().getString("select id from tableName where id=?", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id);
	 * </pre>
	 */
	public String getString(String sql, ExceptionHandler exHandler, Object... params) {
		return get(sql, new StringHandler(), exHandler, params);
	}

	/**
	 * 查询单条单个字段记录,并将其转换为String.
	 *
	 * @param sql       查询语句
	 * @param exHandler 错误处理器
	 * @param params    参数
	 * @return 结果对象
	 */
	public String getStringByList(String sql, ExceptionHandler exHandler, List<Object> params) {
		return getByList(sql, new StringHandler(), exHandler, params);
	}

	/**
	 * 查询多条单个字段记录,并将其转换为Sets.
	 *
	 * @param sql       查询语句
	 * @param exHandler 错误处理器
	 * @param params    参数
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     Set<String> result = DBSessionFactory.use().getSets("select id from tableName where id=?", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id);
	 * </pre>
	 */
	public Set<String> getSets(String sql, ExceptionHandler exHandler, Object... params) {
		return get(sql, new SetsHandler(), exHandler, params);
	}

	/**
	 * 查询多条单个字段记录,并将其转换为Sets.
	 *
	 * @param sql       查询语句
	 * @param exHandler 错误处理器
	 * @param params    参数
	 * @return 结果对象
	 */
	public Set<String> getSetsByList(String sql, ExceptionHandler exHandler, List<Object> params) {
		return getByList(sql, new SetsHandler(), exHandler, params);
	}

	/**
	 * 查询返回ResultSet.
	 *
	 * @param sql    查询语句
	 * @param params 参数
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     ResultSet result = DBSessionFactory.use().getResultSet("select id from tableName where id=?", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id);
	 * </pre>
	 */
	public ResultSet getResultSet(String sql, ExceptionHandler handler, Object... params) {
		return get(sql, new ResultSetHandler<ResultSet>() {
			@Override
			public ResultSet apply(ResultSet rs) {
				return rs;
			}
		}, handler, params);
	}

	/**
	 * SQL查询，返回数据集.
	 *
	 * @param sql      查询语句
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     IDataset result = DBSessionFactory.use().getDataSet("select id from tableName where id=?", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id);
	 * </pre>
	 */
	public IDataset getDataSet(final String sql, ExceptionHandler exHandle, Object... params) {
		return getDataSetByList(sql, exHandle, Arrays.asList(params));
	}

	/**
	 * SQL查询，返回数据集.
	 *
	 * @param sql      查询语句
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @return 结果对象
	 */
	public IDataset getDataSetByList(final String sql, ExceptionHandler exHandle, final List<Object> params) {
		return execute(new Call<IDataset>() {
			@Override
			public IDataset handle(IDBSession session) throws Exception {
				return session.getDataSetByList(sql, params);
			}
		}, exHandle);
	}

	/**
	 * 查询单条记录，并将其转换为类型 T.
	 *
	 * @param sql      查询语句
	 * @param clazz    要返回的 class类型
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @param <T>      返回类型
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     XXDO result = DBSessionFactory.use().getObject("select id from tableName where id=?", XXDO.class, new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id);
	 * </pre>
	 */
	public <T> T getObject(final String sql, final Class<T> clazz, ExceptionHandler exHandle, Object... params) {
		return getObjectByList(sql, clazz, exHandle, Arrays.asList(params));
	}

	/**
	 * 查询单条记录，并将其转换为类型 T.
	 *
	 * @param sql      查询语句
	 * @param clazz    要返回的 class类型
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @param <T>      返回类型
	 * @return 结果对象
	 */
	public <T> T getObjectByList(final String sql, final Class<T> clazz, ExceptionHandler exHandle, final List<Object> params) {
		return execute(new Call<T>() {
			@Override
			public T handle(IDBSession session) throws Exception {
				return session.getObjectByList(sql, clazz, params);
			}
		}, exHandle);
	}

	/**
	 * 查询多条记录，并将其转换为类型 T 的集合.
	 *
	 * @param sql      查询语句
	 * @param clazz    要返回的 class类型
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @param <T>      返回集合的类型
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     List<XXDO> result = DBSessionFactory.use().getObject("select id from tableName where id=?", XXDO.class, new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id);
	 * </pre>
	 */
	public <T> List<T> getObjectList(final String sql, final Class<T> clazz, ExceptionHandler exHandle, Object... params) {
		return getObjectListByList(sql, clazz, exHandle, Arrays.asList(params));
	}

	/**
	 * 查询多条记录，并将其转换为类型 T 的集合.
	 *
	 * @param sql      查询语句
	 * @param clazz    要返回的 class类型
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @param <T>      返回集合的类型
	 * @return 结果对象
	 */
	public <T> List<T> getObjectListByList(final String sql, final Class<T> clazz, ExceptionHandler exHandle, final List<Object> params) {
		return execute(new Call<List<T>>() {
			@Override
			public List<T> handle(IDBSession session) throws Exception {
				return session.getObjectListByList(sql, clazz, params);
			}
		}, exHandle);
	}

	/**
	 * 分页查询多条记录，并将其转换为类型 T 的集合.
	 *
	 * @param sql      查询语句
	 * @param clazz    要返回的 class类型
	 * @param exHandle 错误处理器
	 * @param start    the start
	 * @param limit    每页记录数
	 * @param params   参数
	 * @param <T>      返回集合的类型
	 * @return 结果对象
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     List<XXDO> result = DBSessionFactory.use().getObjectListForPage("select id from tableName where id=?", XXDO.class, new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, 0, 10, id);
	 * </pre>
	 */
	public <T> List<T> getObjectListForPage(final String sql, final Class<T> clazz, ExceptionHandler exHandle, final int start, final int limit, Object... params) {
		return getObjectListByListForPage(sql, clazz, exHandle, start, limit, Arrays.asList(params));
	}

	/**
	 * 分页查询多条记录，并将其转换为类型 T 的集合.
	 *
	 * @param sql      查询语句
	 * @param clazz    要返回的 class类型
	 * @param exHandle 错误处理器
	 * @param start    the start
	 * @param limit    每页记录数
	 * @param params   参数
	 * @param <T>      返回集合的类型
	 * @return 结果对象
	 */
	public <T> List<T> getObjectListByListForPage(final String sql, final Class<T> clazz, ExceptionHandler exHandle, final int start, final int limit, final List<Object> params) {
		return execute(new Call<List<T>>() {
			@Override
			public List<T> handle(IDBSession session) throws Exception {
				return session.getObjectListByListForPage(sql, clazz, start, limit, params);
			}
		}, exHandle);
	}

	/**
	 * 执行非查询语句，包括 插入、更新、删除.
	 *
	 * @param sql      sql
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @return 影响行数
	 * Simplest usage is of form:
	 * <pre>
	 *     String id = "1";
	 *     String name = "zhangsan";
	 *     int result = DBSessionFactory.use().execute("insert into tableName (id, name) value (?,?))", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, id ,name);
	 * </pre>
	 */
	public int execute(final String sql, ExceptionHandler exHandle, Object... params) {
		return executeByList(sql, exHandle, Arrays.asList(params));
	}

	/**
	 * 执行非查询语句，包括 插入、更新、删除.
	 *
	 * @param sql      sql
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @return 影响行数
	 * </pre>
	 */
	public int executeByList(final String sql, ExceptionHandler exHandle, final List<Object> params) {
		return execute(new Call<Integer>() {
			@Override
			public Integer handle(IDBSession session) throws Exception {
				return session.executeByList(sql, params);
			}
		}, exHandle);
	}


	/**
	 * 新增一条记录
	 *
	 * @param tableName 表名
	 * @param obj       要保存的对象
	 * @param exHandle  错误处理器
	 * @param <T>       要保存的对象类型，继承于{@link BaseDO}
	 * @return 影响记录数
	 * Simplest usage is of form:
	 * <pre>
	 *     int result = DBSessionFactory.use().insert("tableName", obj, new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     });
	 * </pre>
	 */
	public <T extends BaseDO> int insert(final String tableName, final T obj, ExceptionHandler exHandle) {
		return execute(new Call<Integer>() {
			@Override
			public Integer handle(IDBSession session) throws Exception {
				return session.saveObject(tableName, obj, obj.getIgnoreFields());
			}
		}, exHandle);
	}

	/**
	 * 更新一条记录
	 *
	 * @param tableName 表名
	 * @param obj       要更新的对象
	 * @param exHandle  错误处理器
	 * @param <T>       要保存的对象类型，继承于{@link BaseDO}
	 * @return 影响记录数
	 * Simplest usage is of form:
	 * <pre>
	 *     int result = DBSessionFactory.use().update("tableName", obj, new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     });
	 * </pre>
	 */
	public <T extends BaseDO> int update(final String tableName, final T obj, ExceptionHandler exHandle) {
		return execute(new Call<Integer>() {
			@Override
			public Integer handle(IDBSession session) throws Exception {
				return session.updateObject(tableName, obj, obj.getMatchFields(), obj.getIgnoreFields());
			}
		}, exHandle);
	}


	/**
	 * 统计记录数.
	 *
	 * @param sql      查询语句
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @return 记录数
	 * Simplest usage is of form:
	 * <pre>
	 *     int result = DBSessionFactory.use().account("select * from tableName", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     });
	 * </pre>
	 */
	public int account(final String sql, ExceptionHandler exHandle, Object... params) {
		return accountByList(sql, exHandle, Arrays.asList(params));
	}

	/**
	 * 统计记录数.
	 *
	 * @param sql      查询语句
	 * @param exHandle 错误处理器
	 * @param params   参数
	 * @return 记录数
	 */
	public int accountByList(final String sql, ExceptionHandler exHandle, final List<Object> params) {
		return execute(new Call<Integer>() {
			@Override
			public Integer handle(IDBSession session) throws Exception {
				return session.accountByList(sql, params);
			}
		}, exHandle);
	}

	/**
	 * 批处理执行
	 *
	 * @param sql        sql
	 * @param exHandle   错误处理器
	 * @param paramsList 参数
	 * @return 影响记录数数组
	 * Simplest usage is of form:
	 * <pre>
	 *     List<List<Object>> paramsList = Lists.newArrayList();
	 *     for(int i = 0; i < 6; i++) {
	 *         List<Object> params = Lists.newArrayList();
	 *         params.add(i);
	 *         params.add("name" + i);
	 *         paramsList.add(params);
	 *     }
	 *     int result = DBSessionFactory.use().executeBatch("insert into tableName (id, name) values (?,?)", new ExceptionHandler() {
	 *          @Override
	 *          public void apply(IDBSession session, Exception ex) {
	 *              log.error(ex.getMessage());
	 *          }
	 *     }, paramsList);
	 * </pre>
	 */
	public int[] executeBatch(final String sql, ExceptionHandler exHandle, final List<List<Object>> paramsList) {
		return execute(new Call<int[]>() {
			@Override
			public int[] handle(IDBSession session) throws Exception {
				return session.executeBatchByList(sql, paramsList);
			}
		}, exHandle);
	}

	/**
	 * 批处理执行
	 *
	 * @param sqlList  sqlList
	 * @param exHandle 错误处理器
	 * @return 影响记录数数组
	 */
	public int[] executeBatch(final List<String> sqlList, ExceptionHandler exHandle) {
		return execute(new Call<int[]>() {
			@Override
			public int[] handle(IDBSession session) throws Exception {
				return session.executeBatch(sqlList);
			}
		}, exHandle);
	}

	/**
	 * 执行接口，数据库操作将在此接口的 handle方法中执行.
	 */
	interface Call<T> {
		T handle(IDBSession session) throws Exception;
	}

	/**
	 * 执行方法，执行前获取一个 session，如果使用的是 useN()方法获取的 Db，则该方法执行完成后将自动 closeSession.
	 *
	 * @param call     执行接口
	 * @param exHandle 错误处理器
	 * @param <T>      返回类型
	 * @return 结果对象
	 */
	private <T> T execute(Call<T> call, ExceptionHandler exHandle) {
		T t = null;
		IDBSession session = useNewSession ? getNewSession(dataSourceName) : getSession(dataSourceName);
		try {
			t = call.handle(session);
		} catch (Exception e) {
			exHandle.apply(session, e);
		} finally {
			if (useNewSession) {
				try {
					closeSession(session);
				} catch (SQLException e) {
					log.error("Close db session error", e);
				}
			}
		}
		return t;
	}


}
