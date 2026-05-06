
package com.adtec.framework.impl.dbspring.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.dbspring.dialect.DialectFactory;
import com.adtec.framework.impl.dbspring.dialect.DialectUtil;
import com.adtec.framework.impl.dbspring.session.DBSessionFactory4Spring;
import com.adtec.framework.interfaces.db.dialect.IDialect;
import com.alibaba.druid.pool.DruidDataSource;


public class DataSourceFactory {
	private static ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();
	public final static ConcurrentHashMap<String, IDialect>	dbTypeMap = 	new ConcurrentHashMap<String, IDialect>();
	public static final String DEFALUT_DATASOURCE_NAME = "dataSource";
	public static final String GV_DATASOURCE_NAME = "GV_DATASOURCE_NAME";	// 线程池变量设置的默认数据源的Key
	private static DataSource defalutDataSource;

	/**
	 * 数据库类型置项：datasource.type
	 */
	public final static String DB_TYPE = "datasource.type";
	/**
	 * 数据库驱动配置项：datasource.driverClassName
	 */
	public final static String DRIVER_CLASS = "datasource.driverClassName";
	/**
	 * 数据库连接地址配置项：datasource.url
	 */
	public final static String URL = "datasource.url";
	/**
	 * 数据库用户名配置项：datasource.username
	 */
	public final static String USER_NAME = "datasource.username";
	/**
	 * 数据库密码加密公钥配置项：datasource.publickey
	 */
	public final static String PUBLIC_KEY = "datasource.publickey";
	/**
	 * 数据库密码配置项：datasource.password
	 */
	public final static String PASSWORD = "datasource.password";
	/**
	 * 数据库连接池初始连接数配置项：jdbc.pool.init
	 */
	public final static String POOL_INIT = "jdbc.pool.init";
	/**
	 * 数据库连接池最小空闲连接数配置项：jdbc.pool.minIdle
	 */
	public final static String POOL_MIN = "jdbc.pool.minIdle";
	/**
	 * 数据库连接池最大连接数配置项：jdbc.pool.maxActive
	 */
	public final static String POOL_MAX = "jdbc.pool.maxActive";
	/**
	 * 数据库获取连接最大等待时间配置项：jdbc.pool.maxWait
	 */
	public final static String POOL_MAX_WAIT = "jdbc.pool.maxWait";
	/**
	 * 数据库检查空闲连接时间间隔配置项：jdbc.pool.timeBetweenEvictionRunsMillis
	 */
	public final static String POOL_TEST_TIME = "jdbc.pool.timeBetweenEvictionRunsMillis";
	/**
	 * 数据库连接最小存活时间配置项：jdbc.pool.minEvictableIdleTimeMillis
	 */
	public final static String POOL_ALIVE_TIME = "jdbc.pool.minEvictableIdleTimeMillis";
	/**
	 * 数据库测试SQL配置项：jdbc.pool.minEvictableIdleTimeMillis
	 */
	public final static String POOL_TEST_SQL = "jdbc.testSql";
	/**
	 * 数据库回收连接时是否检查配置项：jdbc.pool.testWhileIdle
	 */
	public final static String POOL_TEST_IDLE = "jdbc.pool.testWhileIdle";
	/**
	 * 数据库获取连接时是否检查配置项：jdbc.pool.testOnBorrow
	 */
	public final static String POOL_TEST_BORROW = "jdbc.pool.testOnBorrow";
	/**
	 * 数据库归还连接时是否检查配置项：jdbc.pool.testOnReturn
	 */
	public final static String POOL_TEST_RETURN = "jdbc.pool.testOnReturn";
	/**
	 * 数据库检测内存泄露的连接配置项：jdbc.pool.removeAbandoned
	 */
	public final static String POOL_REMOVE_ABAND = "jdbc.pool.removeAbandoned";
	/**
	 * 数据库内存泄露的连接丢弃超时时间配置项：jdbc.pool.removeAbandonedTimeout
	 */
	public final static String POOL_REMOVE_ABAND_TIMEOUT = "jdbc.pool.removeAbandonedTimeout";
	/**
	 * 数据库是否记录丢弃连接的日志配置项：jdbc.pool.logAbandoned
	 */
	public final static String POOL_LOG_ABAND = "jdbc.pool.logAbandoned";
	
	
	static {
		init();
	}
	
	/**
	 * 获取默认DataSource;
	 * @return
	 */
	public static DataSource getDataSource() {
		return defalutDataSource;
	}		
	
	/**
	 * 获取指定DataSource;
	 * @return
	 */
	public static DataSource getDataSource(String dataSourceName) {
		if(DataUtil.isNullStr(dataSourceName)){
			throw new BaseException(SysErr.E_MESSAGE, "获取指定数据源失败，数据源名称不能空");			
		}
		if(!dataSourceMap.containsKey(dataSourceName)){
			throw new BaseException(SysErr.E_MESSAGE, "获取指定数据源失败，不存在数据源["+dataSourceName+"]");		
		}		
		return dataSourceMap.get(dataSourceName);
	}		
	
	
	private static void init() {
		try {
			System.out.println("初始化数据源开始！！！");
			defalutDataSource = (DataSource)SpringContextHolder.getBean(DEFALUT_DATASOURCE_NAME);
			System.out.println("默认数据源["+DEFALUT_DATASOURCE_NAME+"]："+defalutDataSource);
			/*20191007 add by chenyl for 新增保存所有数据源列表，实现多数据源*/
			dataSourceMap.put(DEFALUT_DATASOURCE_NAME, defalutDataSource);
			// 设置数据源对应的数据库类型语言
			dbTypeMap.put(DEFALUT_DATASOURCE_NAME, DialectFactory.getDialect(ParamUtil.getConfig("datasource.type")));
			/*20200225 add by chenyl for 新增多数据源支持*/
			String workdir = ParamUtil.getWorkDir();
			String dataSourcePath = FileUtil.path(workdir+"/DataSource");
			System.out.println("多数据源目录["+dataSourcePath+"]");
			FileUtil.createDirectory(dataSourcePath);	// 创建多数据源目录
			List<File> dsFileList = FileUtil.listFiles(dataSourcePath);
			if(null!=dsFileList && !dsFileList.isEmpty()){
				for(File dsFile:dsFileList){
					if(!dsFile.getName().endsWith(".properties")){
						continue;
					}
					System.out.println("加载数据源配置文件["+dsFile.getAbsolutePath()+"]");
					String fileName = dsFile.getName().substring(0, dsFile.getName().lastIndexOf(".properties"));
					if(DEFALUT_DATASOURCE_NAME.equals(dsFile.getName())){
						System.out.println("已存在数据源["+DEFALUT_DATASOURCE_NAME+"]不重复添加");
					}else{
						Properties props  = new Properties();
						props.load(new FileInputStream(dsFile));
						// 获取配置项
						String dbType = props.getProperty(DB_TYPE);
						System.out.println("配置项["+DB_TYPE+"],值["+dbType+"]");
						if(DataUtil.isNullStr(dbType)){
							throw new BaseException(SysErr.E_MESSAGE, "初始化数据源失败，数据库类型不能空!");
						}
						
						String driverClass = props.getProperty(DRIVER_CLASS);
						System.out.println("配置项["+DRIVER_CLASS+"],值["+driverClass+"]");
						if(DataUtil.isNullStr(driverClass)){
							throw new BaseException(SysErr.E_MESSAGE, "初始化数据源失败，数据库驱动类不能空!");
						}
						
						String url = props.getProperty(URL);
						System.out.println("配置项["+URL+"],值["+url+"]");
						if(DataUtil.isNullStr(url)){
							throw new BaseException(SysErr.E_MESSAGE, "初始化数据源失败，数据库连接地址不能空!");
						}
						
						String username = props.getProperty(USER_NAME);
						System.out.println("配置项["+USER_NAME+"],值["+username+"]");
						if(DataUtil.isNullStr(url)){
							throw new BaseException(SysErr.E_MESSAGE, "初始化数据源失败，数据库用户名不能空!");
						}
						
						String password = props.getProperty(PASSWORD);
						if(DataUtil.isNullStr(url)){
							throw new BaseException(SysErr.E_MESSAGE, "初始化数据源失败，数据库密码不能空!");
						}
						
						String publicKey = props.getProperty(PUBLIC_KEY);
						System.out.println("配置项["+PUBLIC_KEY+"],值["+publicKey+"]");
						
						/*20200515 mod by chenyl for 新增对数据源的连接池的默认初始值配置，以及对应的sql类型的检查语句默认配置*/
						// 数据库测试SQL
						String testSql = props.getProperty(POOL_TEST_SQL);
						if(DataUtil.isNullStr(testSql)){
							if(DialectUtil.DB_TYPE_ORACLE.equals(dbType) || DialectUtil.DB_TYPE_KINGBASE8.equals(dbType)){
								testSql = "SELECT 'x' FROM DUAL";
							}else if(DialectUtil.DB_TYPE_MYSQL.equals(dbType) || DialectUtil.DB_TYPE_INFORMIX.equals(dbType)){
								testSql = "select 1";
							}else if(DialectUtil.DB_TYPE_DB2.equals(dbType)){
								testSql = "select 1 from sysibm.sysdummy1";
							}else{
								throw new BaseException(SysErr.E_NO_MESSAGE, "初始化数据源失败，数据库测试SQL不能空!");
							}
						}
						System.out.println("配置项["+POOL_TEST_SQL+"],值["+testSql+"]");
						
						// 初始化连接数
						String initialSize = props.getProperty(POOL_INIT);
						if(DataUtil.isNullStr(initialSize)){
							initialSize = "1";
						}
						System.out.println("配置项["+POOL_INIT+"],值["+initialSize+"]");
						
						// 最小连接数
						String minIdle = props.getProperty(POOL_MIN);
						if(DataUtil.isNullStr(minIdle)){
							minIdle = "3";
						}
						System.out.println("配置项["+POOL_MIN+"],值["+minIdle+"]");
						
						// 数据库最大连接数
						String maxActive = props.getProperty(POOL_MAX);
						if(DataUtil.isNullStr(maxActive)){
							maxActive = "20";
						}
						System.out.println("配置项["+POOL_MAX+"],值["+maxActive+"]");
						
						// 数据库获取连接最大等待时间(ms)
						String maxWait = props.getProperty(POOL_MAX_WAIT);
						if(DataUtil.isNullStr(maxWait)){
							maxWait = "180000";
						}
						System.out.println("配置项["+POOL_MAX_WAIT+"],值["+maxWait+"]");
						
						// 数据库检查空闲连接时间间隔(ms)
						String testTime = props.getProperty(POOL_TEST_TIME);
						if(DataUtil.isNullStr(testTime)){
							testTime = "60000";
						}
						System.out.println("配置项["+POOL_TEST_TIME+"],值["+testTime+"]");
						
						// 数据库连接最小存活时间(ms)
						String aliveTime = props.getProperty(POOL_ALIVE_TIME);
						if(DataUtil.isNullStr(aliveTime)){
							aliveTime = "300000";
						}
						System.out.println("配置项["+POOL_ALIVE_TIME+"],值["+aliveTime+"]");
						
						// 数据库回收连接时是否检查
						String testIdle = props.getProperty(POOL_TEST_IDLE);
						if(DataUtil.isNullStr(testIdle)){
							testIdle = "true";
						}
						System.out.println("配置项["+POOL_TEST_IDLE+"],值["+testIdle+"]");
						
						// 数据库获取连接时是否检查
						String testBorrow = props.getProperty(POOL_TEST_BORROW);
						if(DataUtil.isNullStr(testBorrow)){
							testBorrow = "true";
						}
						System.out.println("配置项["+POOL_TEST_BORROW+"],值["+testBorrow+"]");
						
						// 数据库归还连接时是否检查
						String testReturn = props.getProperty(POOL_TEST_RETURN);
						if(DataUtil.isNullStr(testReturn)){
							testReturn = "true";
						}
						System.out.println("配置项["+POOL_TEST_RETURN+"],值["+testReturn+"]");
						
						// 数据库检测内存泄露的连接
						String removeAband = props.getProperty(POOL_REMOVE_ABAND);
						if(DataUtil.isNullStr(removeAband)){
							removeAband = "true";
						}
						System.out.println("配置项["+POOL_REMOVE_ABAND+"],值["+removeAband+"]");
						
						// 数据库内存泄露的连接丢弃超时时间(ms)
						String removeAbandTimeout = props.getProperty(POOL_REMOVE_ABAND_TIMEOUT);
						if(DataUtil.isNullStr(removeAbandTimeout)){
							removeAbandTimeout = "300";
						}
						System.out.println("配置项["+POOL_REMOVE_ABAND_TIMEOUT+"],值["+removeAbandTimeout+"]");
						
						// 数据库是否记录丢弃连接的日志
						String logAband = props.getProperty(POOL_LOG_ABAND);
						if(DataUtil.isNullStr(logAband)){
							logAband = "true";
						}
						System.out.println("配置项["+POOL_LOG_ABAND+"],值["+logAband+"]");
						
						
						// 设置配置项值
						DruidDataSource dds = new DruidDataSource();
						dds.setDbType(dbType);
						dds.setDriverClassName(driverClass);
						dds.setUrl(url);
						dds.setUsername(username);
						dds.setPassword(password);
						if(!DataUtil.isNullStr(publicKey)){
							// 公钥不为空时进行解密操作
							dds.setConnectionProperties("config.decrypt=true;config.decrypt.key="+publicKey);
						}
						dds.setInitialSize(Integer.parseInt(initialSize));
						dds.setMinIdle(Integer.parseInt(minIdle));
						dds.setMaxActive(Integer.parseInt(maxActive));
						dds.setMaxWait(Long.parseLong(maxWait));
						dds.setTimeBetweenEvictionRunsMillis(Long.parseLong(testTime));
						dds.setMinEvictableIdleTimeMillis(Long.parseLong(aliveTime));
						dds.setValidationQuery(testSql);
						dds.setTestOnBorrow(Boolean.parseBoolean(testBorrow));
						dds.setTestOnReturn(Boolean.parseBoolean(testReturn));
						dds.setRemoveAbandoned(Boolean.parseBoolean(removeAband));
						dds.setRemoveAbandonedTimeout(Integer.parseInt(removeAbandTimeout));
						/*20210804 mod by chenyl for 新增当oracle数据库使用非oci模式时配置以下两个配置项*/
						if(DialectUtil.DB_TYPE_ORACLE.equals(dbType) && !url.contains(":oci")){
							// 如果是oracle数据库，打开PSCache，并且指定每个连接上PSCache的大小（Oracle使用，如果是oci连接模式需屏蔽以下两个配置项）
							dds.setPoolPreparedStatements(true);
							dds.setMaxPoolPreparedStatementPerConnectionSize(20);
						}
						dds.setFilters("config");
						
						// 设置数据源对应的数据库类型语言
						dbTypeMap.put(fileName, DialectFactory.getDialect(dbType));
						
						// 添加到数据源map中
						dataSourceMap.put(fileName, dds);
						System.out.println("添加数据源["+fileName+"]："+dds);
					}
				}
			}else{
				System.out.println("多数据源目录["+dataSourcePath+"]没有需要初始化的数据源配置");
			}
			System.out.println("初始化数据源结束！！！");
			
		} catch (Exception e) {
			DBSessionFactory4Spring.getLog().error("初始化数据源失败", e);
		}
	}


	/**
	 * 新增数据源
	 * by linjk
	 * @date 2021-02-23 11:11
	 * @param dbTp 数据库类型
	 * @param userName 数据库用户名称
	 * @param password  数据库密码
	 * @param urlSuffix 数据库url后缀
	 * @param dbsName 数据库名称
	 */
	public static void initOtherDBIns(String dbTp,String userName,String password ,String urlSuffix,String dbsName){
		if(dataSourceMap.containsKey(dbsName)){
			return;
		}
		String driverClass;
		String url;
		String testSql;
		if(DialectUtil.DB_TYPE_ORACLE.equals(dbTp)){
			driverClass="oracle.jdbc.OracleDriver";
			url="jdbc:oracle:thin:@"+urlSuffix;
			testSql="SELECT 'x' FROM DUAL";
		}else {
			driverClass="com.mysql.jdbc.Driver";
			url="l=jdbc:mysql://"+urlSuffix;
			testSql="SELECT 1";
		}
		DruidDataSource dds = new DruidDataSource();
		dds.setDbType(dbTp);
		dds.setDriverClassName(driverClass);
		dds.setUrl(url);
		dds.setUsername(userName);
		dds.setPassword(password);

		dds.setInitialSize(3);
		dds.setMinIdle(5);
		dds.setMaxActive(20);
		dds.setValidationQuery(testSql);

		if(DialectUtil.DB_TYPE_ORACLE.equals(dbTp) && !url.contains(":oci")){
			// 如果是oracle数据库，打开PSCache，并且指定每个连接上PSCache的大小
			dds.setPoolPreparedStatements(true);
			dds.setMaxPoolPreparedStatementPerConnectionSize(20);
		}

		// 设置数据源对应的数据库类型语言
		dbTypeMap.put(dbsName, DialectFactory.getDialect(dbTp));

		// 添加到数据源map中
		dataSourceMap.put(dbsName, dds);

	}


	/**
	 * 删除数据源
	 * by linjk
	 * @date 2021-02-23 11:11
	 * @param dbsName 数据库名称
	 */
	public static void deleteDBS(String dbsName){
		DruidDataSource dataSource=(DruidDataSource)dataSourceMap.get(dbsName);
		if(null!=dataSource){
			dataSource.close();
		}
		dataSourceMap.remove(dbsName);
	}
}
