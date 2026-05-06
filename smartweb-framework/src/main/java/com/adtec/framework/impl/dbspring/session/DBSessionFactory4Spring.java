/*
 *    Copyright (c) 2016 ADTEC
 *    All rights reserved
 *
 *    本程序为自由软件；您可依据自由软件基金会所发表的GNU通用公共授权条款规定，就本程序再为发布与／或修改；无论您依据的是本授权的第二版或（您自行选择的）任一日后发行的版本。
 *    本程序是基于使用目的而加以发布，然而不负任何担保责任；亦无对适售性或特定目的适用性所为的默示性担保。详情请参照GNU通用公共授权。
 */
package com.adtec.framework.impl.dbspring.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.UUID;
import com.adtec.framework.impl.dbspring.datasource.DataSourceFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.db.session.IDBSessionFactory;
import com.adtec.framework.common.util.DataUtil;

/**
 * <P>DBSessionFactory4Spring</P>
 * <P>类的详细说明</P>
 * <P>Copyright: Copyright (c) 2015</P>
 * <P>Company: 北京先进数通信息技术股份公司</P>
 *
 * @author chenyl
 * @version 1.0 2016年9月20日
 * <P>          修改者姓名 修改内容说明</P>
 */
public class DBSessionFactory4Spring implements IDBSessionFactory {

	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(DBSessionFactory4Spring.class);

	static public Logger getLog() {
		return log;
	}

	/**
	 * 用于保存线程内的 Session, key为 dataSourceName, value为对应的 Session
	 */
	private final ThreadLocal<Map<String, IDBSession>> sessions = new ThreadLocal<Map<String, IDBSession>>();

	/**
	 * 获取Session,同一个线程为一个。
	 * 从默认数据源中获取一个当前线程内的连接
	 */
	public IDBSession getSession() {
		/*20200227 add by chenyl 获取线程池变量GV_DATASORUCE_NAME设置当前线程的默认数据源*/
		String GVDefaultName = (String) GVarContainer.getVar(DataSourceFactory.GV_DATASOURCE_NAME);
		// 2020-04-27 mod by lijunbin 改为通过线程变量获取 Session, 而不是通过本地变量 defaultName, 因为当前对象是单例的, 使用 defaultName会影响全局
		return DataUtil.isNullStr(GVDefaultName)
				? getSession(DataSourceFactory.DEFALUT_DATASOURCE_NAME)
				: getSession(GVDefaultName);
	}

	/**
	 * 获取一个新的Session,关闭后释放
	 * 从默认数据源中获取一个新创建的连接
	 */
	public IDBSession getNewSession() {
		/*20200227 add by chenyl 获取线程池变量GV_DATASORUCE_NAME设置当前线程的默认数据源*/
		String GVDefaultName = (String) GVarContainer.getVar(DataSourceFactory.GV_DATASOURCE_NAME);
		// 2020-04-27 mod by lijunbin 改为通过线程变量获取 Session, 而不是通过本地变量 defaultName, 因为当前对象是单例的, 使用 defaultName会影响全局
		return DataUtil.isNullStr(GVDefaultName)
				? getNewSession(DataSourceFactory.DEFALUT_DATASOURCE_NAME)
				: getNewSession(GVDefaultName);
	}

	/**
	 * 获取Session,同一个线程为一个。
	 * 从指定数据源中获取一个当前线程内的连接
	 *
	 * @param dataSourceName 数据源名称
	 * @return DBSession
	 */
	public IDBSession getSession(String dataSourceName) {
		IDBSession session = null;
		if (dataSourceName != null) {
			session = getCurrentSessionMap().get(dataSourceName);
			if (session == null) {
				session = creatSession(dataSourceName, false);
				if (session != null) {
					// 新创建并存储到线程变量中
					if (!getCurrentSessionMap().containsKey(dataSourceName)) {
						getCurrentSessionMap().put(dataSourceName, session);
					}
				}
			}
		}
		if (log.isInfoEnabled() && null != session) {
			log.info("从数据源[" + dataSourceName + "]中获取当前线程的DBSession[" + session.getUuid() + "]");
		}
		return session;
	}

	// 20110623增加别名映射 ,创建session
	private IDBSession creatSession(String dataSourceName, boolean isNewConnection) {
		IDBSession session = null;
		try {
			if (isNewConnection) {
				// 新建连接的方式不存放到内存变量中
				session = new DBSession(DataSourceFactory.getDataSource(dataSourceName).getConnection(), DataSourceFactory.dbTypeMap.get(dataSourceName));
				session.setUuid(UUID.randomUUID());
				if (log.isInfoEnabled()) {
					log.info("从数据源[" + dataSourceName + "]中创建DBSession[" + session.getUuid() + "]");
				}
			} else {
				// 从当前线程变量中获取
				session = getCurrentSessionMap().get(dataSourceName);
				if (null == session) {
					// 线程变量中没有则新创建一个并添加到线程变量中
					session = new DBSession(DataSourceFactory.getDataSource(dataSourceName).getConnection(), DataSourceFactory.dbTypeMap.get(dataSourceName));
					session.setUuid(UUID.randomUUID());
					// 新创建并存储到线程变量中
					getCurrentSessionMap().put(dataSourceName, session);
					if (log.isInfoEnabled()) {
						log.info("当前线程内不存数据源[" + dataSourceName + "]，创建DBSession[" + session.getUuid() + "]");
					}
				} else {
					if (log.isInfoEnabled()) {
						log.info("当前线程内的数据源[" + dataSourceName + "]中获取DBSession[" + session.getUuid() + "]");
					}
				}
			}
		} catch (Exception e) {
			log.error("创建DBSession失败", e);
		}
		return session;
	}

	/**
	 * 获取一个新的Session,关闭后释放
	 * 从指定数据源中获取一个新创建的连接
	 *
	 * @param dataSourceName 数据源名称
	 * @return DBSession
	 */
	public IDBSession getNewSession(String dataSourceName) {
		IDBSession session = creatSession(dataSourceName, true);
		if (log.isInfoEnabled() && null != session) {
			log.info("从数据源[" + dataSourceName + "]中获取新的DBSession[" + session.getUuid() + "]");
		}
		return session;
	}

	/**
	 * 清理线程Session工厂.
	 */
	public void clear() {
		if (log.isDebugEnabled()) {
			log.debug("清理线程Session工厂开始...");
		}
		for (Map.Entry<String, IDBSession> entry : getCurrentSessionMap().entrySet()) {
			try {
				if (!entry.getValue().transactionIsClosed()) {
					if (entry.getValue().isAutoRollback())
						entry.getValue().rollback();
					else
						entry.getValue().endTransaction();
				}
				if (log.isDebugEnabled()) {
					log.debug("session value[{}]", entry.getValue().getUuid());
				}
				entry.getValue().clear();
			} catch (Exception e) {
				log.error("清理线程Session工厂异常！", e);
			} finally {
				//add by chenyl 20170510 for 关闭当前线程的DBSession的数据库连接
				try {
					if (null != entry.getValue().getConnection() && !entry.getValue().getConnection().isClosed()) {
						if (log.isDebugEnabled()) {
							log.debug("关闭连接：" + entry.getValue().getConnection());
						}
						entry.getValue().getConnection().close();
					}
				} catch (SQLException e) {
					log.error("关闭数据库连接失败！", e);
				}
			}
		}
		getCurrentSessionMap().clear();
		if (log.isDebugEnabled()) {
			log.debug("清理线程Session工厂结束...");
		}
	}

	/**
	 * 关闭session
	 */
	public void closeSession(IDBSession session) throws SQLException {
		try {
			if (session != null) {
				Map<String, IDBSession> sessionMap = getCurrentSessionMap();
				session.clear();
				if (!session.transactionIsClosed()) {
					if (session.isAutoRollback())
						session.rollback();
					else {
						session.endTransaction();
					}
				}
				for (Map.Entry<String, IDBSession> entry : sessionMap.entrySet()) {
					if (entry.getValue() == session) {
						sessionMap.remove(entry.getKey());
					}
				}
				//add by chenyl 20170510 for 关闭当前线程的DBSession的数据库连接
				if (null != session.getConnection() && !session.getConnection().isClosed()) {
					if (log.isInfoEnabled()) {
						log.info("关闭连接：" + session.getUuid());
					}
					session.getConnection().close();
				}
			}
		} catch (Exception e) {
			log.error("关闭数据库连接失败！", e);
		}
	}

	/**
	 * 获取当前的线程会话存储
	 *
	 * @return
	 */
	private Map<String, IDBSession> getCurrentSessionMap() {
		Map<String, IDBSession> map = sessions.get();
		if (map == null) {
			map = new ConcurrentHashMap<String, IDBSession>();
			sessions.set(map);
		}
		return map;
	}

	@Override
	public Connection getTransactionConnection(String dataSourceName) {
		// TODO Auto-generated method stub
		return null;
	}

}
