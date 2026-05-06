package com.adtec.framework.interfaces.db.session;

import java.sql.Connection;
import java.sql.SQLException;


public interface IDBSessionFactory {

	/**
	 * 获取Session,同一个线程为一个。
	 * 
	 * @return
	 */
	public IDBSession getSession();

	/**
	 * 获取一个新的Session,关闭后释放
	 * 
	 * @return
	 */
	public IDBSession getNewSession();

	/**
	 * 获取事务连接对象
	 * 
	 * @param dataSourceName
	 *            数据源名称
	 * @return
	 */
	public Connection getTransactionConnection(String dataSourceName);

	/**
	 * 获取Session,同一个线程为一个。
	 * 
	 * @param dataSourceName
	 * @return
	 */
	// 增加别名映射
	public IDBSession getSession(String dataSourceName);

	/**
	 * 获取一个新的Session,关闭后释放
	 * 
	 * @param dataSourceName
	 * @return
	 */
	public IDBSession getNewSession(String dataSourceName);

	/**
	 * 清理线程Session工厂.
	 */
	public void clear();

	/**
	 * 关闭session
	 */
	public void closeSession(IDBSession session) throws SQLException;


}
