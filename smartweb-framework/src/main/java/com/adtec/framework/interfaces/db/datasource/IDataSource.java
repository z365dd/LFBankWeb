/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: IDataSource.java
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
package com.adtec.framework.interfaces.db.datasource;

import java.sql.Connection;

import javax.sql.DataSource;

import com.adtec.framework.interfaces.db.dialect.IDialect;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDataSource.
 * 
 * @author chenyl
 */
public interface IDataSource extends DataSource{
	
	/**
	 * 从当前线程获得默认数据源连接.
	 * 
	 * @return the current connection
	 * @throws Exception the exception
	 * @return
	 */
	public Connection getCurrentConnection() throws Exception;
	
	/**
	 * 释放当前连接.
	 * 
	 * @throws Exception the exception
	 */
	public void closeCurrentConnection()throws Exception;
	
	/**
	 * 释放指定连接.
	 * 
	 * @param connection the connection
	 * @throws Exception the exception
	 */
	public void closeConnection(Connection connection)throws Exception;
	
	
	/**
	 * 获得方言类型.
	 * 
	 * @return the dialect
	 * @return
	 */
	public IDialect getDialect();
	
	/**
	 * 获取datasource名称.
	 * 
	 * @return the name
	 * @return
	 */
	public String getName();
	
	/**
	 * 判定是否为默认连接池.
	 * 
	 * @return true, if is default
	 * @return
	 */
	public boolean isDefault();

	/**
	 * 获得dbtype.
	 * 
	 * @return the dbtype
	 * @return
	 */
	public String getDbtype();
	
	/**
	 * 获得当前DataSourace活动连接数.
	 * 
	 * @return the num busy connections default user
	 * @return
	 */
	public int getNumBusyConnectionsDefaultUser();

	/**
	 * 是否采用游标分页.
	 * 
	 * @return true, if is cursor
	 * @return
	 */
	public boolean isCursor();
	
	/**
	 * resultset是否可以调用previous等操作.
	 * 
	 * @return true, if is scroll
	 * @return
	 */
	public boolean isScroll();
	
	/**
	 * auto rollback on close.
	 * 
	 * @return true, if is auto rollback
	 * @return
	 */
	public boolean isAutoRollback();
	
	/**
	 * Checks if is start monitor.
	 * 
	 * @return true, if is start monitor
	 */
	public boolean isStartMonitor();

    /**
     * 关闭DataSource连接池,容器关闭时stop管理方法调用
     */
	public void closeDataSource();
	
	/**
	 * 获得FetchSize
	 * @return
	 */
	public int getFetchSize();
	
	/**
	 * 获得initsql
	 * @return
	 */	
	public String getInitSql();

}
