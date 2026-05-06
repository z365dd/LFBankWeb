package com.adtec.framework.common.util;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import org.apache.axis.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @类名 ParamUtil.java
 * @描述:
 *     配置文件读取工具类
 * @版本 v1.0
 */
public class ParamUtil extends PropertyPlaceholderConfigurer {
	private final static Logger logger = LoggerFactory.getLogger(ParamUtil.class);
	private static Map<String, Object> ctxPropertiesMap = Maps.newHashMap();

	/**
	 * 跑批类包结构
	 */
	public static final String BATCH_PACKAGE = "com.adtec.sys.modules.batch.T";

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 配置文件中的是/否
	 */
	public static final String CONF_Y = "Y";
	public static final String CONF_N = "N";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	// 操作类型
	public static final String MNG = "1";
	public static final String TELLER = "2";
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/* (non-Javadoc)
	 * @see org.springframework.core.io.support.PropertiesLoaderSupport#setLocations(org.springframework.core.io.Resource[])
	 */
	@Override
	public void setLocations(Resource... locations) {
		// TODO Auto-generated method stub
		String proPath = "";
		List<Resource> locationList = new ArrayList<Resource>();
		for(Resource r:locations){
			try {
				locationList.add(r);
				if(DataUtil.isNullStr(proPath)){
					String configPath = r.getURI().toString();
					System.out.println("加载配置文件："+r.getURI().toString());
					if(!DataUtil.isNullStr(configPath)){
						proPath = configPath.substring("file:".length(),configPath.lastIndexOf("/"));
					}
				}
			} catch (IOException e) {
				logger.error("读取配置文件失败");
			}
		}
//		System.out.println("配置文件目录："+proPath);
//		List<File> fileList = FileUtil.listFiles(proPath);
//		for(File f:fileList){
//			if(f.getName().startsWith("config_") && f.getName().endsWith(".properties")){
//				// 加载相关配置文件
//				FileInputStream fileInputStream = null;
//				try {
//					fileInputStream = new FileInputStream(f);
//					Resource proRes = new InputStreamResource(fileInputStream);
//					locationList.add(proRes);
//					System.out.println("动态加载配置文件："+f.getAbsolutePath());
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		int size = locationList.size();
		Resource[] ls = new Resource[size];
		for(int i=0;i<size;i++){
			ls[i] = locationList.get(i);
		}
		super.setLocations(ls);
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		// TODO Auto-generated method stub
		logger.info("propeties init ...");
		super.processProperties(beanFactoryToProcess, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			if(!StringUtils.isEmpty(keyStr)){
				String value = props.getProperty(keyStr);
				ctxPropertiesMap.put(keyStr, value);
			}
		}
	}

	/**
	 * 获取config.properties中配置的属性值
	 * @param name
	 * @return
	 */
	public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

	public static Map<String, Object> getCtxPropertiesMap() {
		return ctxPropertiesMap;
	}


	public static void setCtxPropertiesMap(Map<String, Object> ctxPropertiesMap) {
		ParamUtil.ctxPropertiesMap = ctxPropertiesMap;
	}


	/**
	 * 获取config.properties中配置的属性值
	 * @param name
	 * @return
	 */
	public static String getString(String name) {
		String value = "";
		if(null!=getContextProperty(name)){
			value = (String)getContextProperty(name);
		}
        return value;
    }

	/**
	 * 获取当前工作目录
	 * @return
	 */
	public static String getWorkDir(){
		String workDir = System.getenv("JAVAWORKDIR");
		if (DataUtil.isNullStr(workDir)) {
			workDir = System.getenv("HOME");
		}
		if (DataUtil.isNullStr(workDir)) {
			workDir = System.getProperty("user.dir");
		}
		if (DataUtil.isNullStr(workDir)) {
			workDir = ".";
		}
		return workDir;
	}

	/**
	 * 获取保存流水文件路径
	 * @return
	 */
	public static String getSeqPath() {
		// TODO Auto-generated method stub
		String seqPath = getString("seq.path");
		if(DataUtil.isNullStr(seqPath)){
			seqPath = getWorkDir();
		}
		return seqPath;
	}

	/**
	 * 上传文件路径
	 * @return
	 */
	public static String getUploadFile(){
		String uploadFile = getString("uploadFile");
		if(DataUtil.isNullStr(uploadFile)){
			uploadFile = getWorkDir()+"/uploadFile";
		}
		uploadFile = FileUtil.path(uploadFile);
		if(!FileUtil.exsitsFile(uploadFile)){
			// 不存在目录创建
			FileUtil.createDirectory(uploadFile);
		}
		return uploadFile;
	}

	/**
	 * web工作目录路径
	 */

	public static String getWebPath(){
		return getString("web.path");
	}

	/**
	 * jsp路径
	 */

	public static String getJspPath(){
		String jspPath = getWebPath() + "/WEB-INF/views/starring";
		return jspPath;
	}

	/**
	 * 获取js路径
	 * @return
	 */
	public static String getJsPath(){
		String jsPath = getWebPath() + "/b_base/views/starring";
		return jsPath;
	}

	/**
	 * jspbeg路径
	 */
	public static String getJSPBEG(){
		String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		String jspbegPath = path + "/templates/JSPBEG";
		return jspbegPath;
	}

	/**
	 * jspend路径
	 */
	public static String getJSPEND(){
		String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		String jspendPath = path + "/templates/JSPEND";
		return jspendPath;
	}

	/**
	 * jsdesc路径
	 */
	public static String getJSDEC(){
		String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		String jsdescPath = path + "/templates/JSDEC";
		return jsdescPath;
	}

	/**
	 * previewJsp路径
	 */

	public static String getPreviewJsp(){
		String previewJsp = "/b_ide/bootstrap/previewContent.jsp";
		return previewJsp;
	}

	/**
	 * 获取数据库字段对应的label或者月份
	 * @return
	 */
	public static String getChartLabels(){
		return getString("chart.labels");
	}

	/**
	 * 获取项目名称
	 * @return
	 */
	public static String getProjectName(){
		return getString("project.name");
	}

	/**
	 * 平台保存流水时间间隔
	 * @return
	 */
	public static String getPlatSeqTimeout(){
		/*20200304 add by chenyl for 设置平台流水号自动保存时间(单位秒)*/
		String timeout = "3";
		if(!DataUtil.isNullStr(getString("platseq.timeout"))){
			timeout = getString("platseq.timeout");
		}
		return timeout;
	}

	/**
	 * 获取当前系统版本号
	 * @return
	 */
	public static String getVersion(){
		return getString("version");
	}

	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getString("urlSuffix");
	}

	/**
	 * 获取拦截的静态文件
	 * @return
	 */
	public static String getStaticFile(){
		return getString("web.staticFile");
	}

	/**
	 * 获取分页默认值
	 * @return
	 */
	public static String getPageSize(){
		return getString("page.pageSize");
	}

	/**
	 * 获取管理路径
	 * @return
	 */
	public static String getAdminPath(){
		return getString("adminPath");
	}

	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}

	/**
	 * 页面获取配置属性
	 * @param key
	 * @return
	 */
	public static String getConfig(String key){
		String value;
		try{
			value = getString(key);
		}catch(Exception e){
			System.out.println("不存在配置项："+key);
			value = StringUtil.EMPTY;
		}
		return value;
	}

	/**
	 * 页面获取常量
	 * @see {fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return ParamUtil.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
			logger.info("没有配置");
		}
		return null;
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}

	/*---------------TCP JSON 适配器配置项 BEG ---------------*/
	/**
	 * 获取TCP JSON适配器监听端口，默认8096
	 * @return
	 */
	public static String getTcpJsonPort() {
		// TODO Auto-generated method stub
		String port = "8096";
		if(DataUtil.isNullStr(getString("tcp.json.port"))){
			port = getString("tcp.json.port");
		}
		return port;
	}

	/**
	 * 是否启动TCPJson服务，Y-是、N-否
	 * @return
	 */
	public static String getTcpJsonOpen(){
		String open = CONF_N;
		if(CONF_Y.equalsIgnoreCase(getString("tcp.json.run"))){
			open = CONF_Y;
		}
		return open;
	}
	/*---------------TCP JSON 适配器配置项 END ---------------*/

	/*---------------微服务代理相关配置信息 BEG---------------*/
	/**
	 * 是否启动微服务框架，Y-是、N-否
	 * @return
	 */
	public static String getMsAgentOpen(){
		String open = CONF_N;
		if(CONF_Y.equalsIgnoreCase(getString("ms.agent.open"))){
			open = CONF_Y;
		}
		return open;
	}
	/*---------------微服务代理相关配置信息 END---------------*/

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getUploadFile();
		if (StringUtil.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		return dir;
	}


	/**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = ParamUtil.getConfig("projectPath");
		if (StringUtil.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
                System.out.println("出现异常");
            }
		return projectPath;
    }


	/**
	 * 获取soap的WebService服务地址
	 * @return
	 */
	public static String getWsurl() {
		// TODO Auto-generated method stub
		return getConfig("serverAddress");
	}

	/**
	 * 获取http+json的默认请求路径
	 * @return
	 */
	public static String getHttpJsonAddress() {
		// TODO Auto-generated method stub
		return getConfig("httpJsonAddress");
	}

	/**
	 * smartweb集群标识
	 * @return
	 */
	public static String getClusterFlag(){
		return getConfig("sm.cluster.flag");
	}

	public static String getFlowChkInterval() {
		return getConfig("flow.check.interval");
	}

	/**
	 * smartweb的redis配置
	 * @return
	 */
	public static String getRedisServerList() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.server.list");
	}

	/**
	 * 集群创建连接的超时时间（毫秒）
	 * @return
	 */
	public static String getRedisTimeOut() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.timeout");
	}

	/**
	 * redis取值失败，最大尝试次数
	 * @return
	 */
	public static String getRedisMaxRedirections() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.maxRedirections");
	}

	/**
	 * redis最大连接数
	 * @return
	 */
	public static String getRedisMaxTotal() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.maxTotal");
	}

	/**
	 * redis 连接最大空闲连接数
	 * @return
	 */
	public static String getRedisMaxIdle() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.maxIdle");
	}

	/**
	 * redis 最小空闲连接数
	 * @return
	 */
	public static String getRedisMinIdle() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.minIdle");
	}

	/**
	 * redis 每次释放连接的最大数目
	 * @return
	 */
	public static String getReidsNumTestsPerEvictionRun() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.numTestsPerEvictionRun");
	}

	/**
	 * redis 释放连接的扫描间隔（毫秒）
	 * @return
	 */
	public static String getRedisTimeBetweenEvictionRunsMillis() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.timeBetweenEvictionRunsMillis");
	}

	/**
	 * redis 连接最小空闲时间（毫秒）
	 * @return
	 */
	public static String getRedisMinEvictableIdleTimeMillis() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.minEvictableIdleTimeMillis");
	}

	/**
	 * 连接空闲多久后释放（毫秒）, 当空闲时间>该值 且 空闲连接>最大空闲连接数 时直接释放
	 * @return
	 */
	public static String getRedisSoftMinEvictableIdleTimeMillis() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.softMinEvictableIdleTimeMillis");
	}

	/**
	 * 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间,默认-1
	 * @return
	 */
	public static String getRedisMaxWaitMillis() {
		// TODO Auto-generated method stub
		return getConfig("sm.redis.maxWaitMillis");
	}

	/**
	 * zk 连接重试时间间隔（毫秒）
	 * @return
	 */
	public static String getZKRetryInterval() {
		// TODO Auto-generated method stub
		return getConfig("sm.zookeeper.retry.interval");
	}

	/**
	 * zk 连接重试次数
	 * @return
	 */
	public static String getZKRetryTimes() {
		// TODO Auto-generated method stub
		return getConfig("sm.zookeeper.retry.times");
	}

	/**
	 * Zookeeper,也可配置和微服务框架的参数
	 * @return
	 */
	public static String getZKServerList() {
		// TODO Auto-generated method stub
		return getConfig("sm.zookeeper.server.list");
	}

	/**
	 * zk 会话超时时间（毫秒）
	 * @return
	 */
	public static String getZKSessionTimeOut() {
		// TODO Auto-generated method stub
		return getConfig("sm.zookeeper.session.timeout");
	}

	/**
	 * zk 连接超时时间（毫秒）
	 * @return
	 */
	public static String getZKConnectTimeOut() {
		// TODO Auto-generated method stub
		return getConfig("sm.zookeeper.connection.timeout");
	}

	public static String getMSEffectRun() {
		// TODO Auto-generated method stub
		return getConfig("ms.task.run");
	}

	public static String getMSEffectCheckInterval() {
		// TODO Auto-generated method stub
		return getConfig("effect.check.interval");
	}

	public static String getMSMonitorFuseTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.fuseTask.run");
	}

	public static String getMSMonitorFuseInterval() {
		// TODO Auto-generated method stub
		return getConfig("monitor.fuse.interval");
	}

	public static String getMSMonitorFlowTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.flowcontrolTask.run");
	}

	public static String getMSMonitorFlowInterval() {
		// TODO Auto-generated method stub
		return getConfig("monitor.flowCtrl.interval");
	}

	public static String getMsMallQualityTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("msmall.qualityTask.run");
	}

	public static String getMsMallQualityInterval() {
		// TODO Auto-generated method stub
		return getConfig("load.quality.interval");
	}

	public static String getMsMallFileTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("msmall.fileTask.run");
	}

	public static String getMsMallFileInterval() {
		// TODO Auto-generated method stub
		return getConfig("load.quality.interval");
	}

	public static String getLocalAddr() {
		// TODO Auto-generated method stub
		return getConfig("ReqHostName");
	}

	public static String getLocalPort() {
		// TODO Auto-generated method stub
		return getConfig("ReqPort");
	}

	/**
	 * 获取是否开启防止CSR攻击过滤
	 * @return
	 */
	public static String getCsrOpen(){
		return getConfig("csr.open");
	}

	/**
	 * 获取是否开启SQL注入检查
	 * @return
	 */
	public static String getSqlInjectOpen(){
		return getConfig("sqlInject.open");
	}

	/**
	 * 获取日志输出路径
	 * @param file
	 * @return
	 */
	public static String getLogPath(String file) {
		// TODO Auto-generated method stub
		String logHome = getWorkDir();
		if(null==logHome){
			logHome = "log/" + file;
		}else{
			logHome = logHome + "/log/" + file;
		}
		return FileUtil.path(logHome);
	}

	/**
	 * 是否开启参与者实例监控日志
	 * @return
	 */
	public static String getMonitorPartinstTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.partinstTask.run");
	}
	/**
	 * 装载参与者实例监控日志任务间隔，单位毫秒
	 * @return
	 */
	public static String getMonitorPartinstInterval() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.partinst.interval");
	}
	/**
	 * 是否开启服务接口校验监控日志
	 * @return
	 */
	public static String getMonitorServiceInterfaceTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.serviceinterfaceTask.run");
	}
	/**
	 * 装载服务接口校验监控日志任务间隔，单位毫秒
	 * @return
	 */
	public static String getMonitorServiceInterfaceInterval() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.serviceinterface.interval");
	}
	/**
	 * 是否开启参与者实例监控日志
	 * @return
	 */
	public static String getMonitorAgentTaskRun() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.agentTask.run");
	}
	/**
	 * 装载参与者实例监控日志任务间隔，单位毫秒
	 * @return
	 */
	public static String getMonitorAgentInterval() {
		// TODO Auto-generated method stub
		return getConfig("ms.monitor.agent.interval");
	}

	/**
	 * 获取webSocket url
	 * @return
	 */
	public static String getWebSocketUrl() {
		return "ws://" + getLocalAddr() + ":" +getLocalPort() + getAdminPath() + "/ws";
	}
	
	/**
	 * Y/N 是否开启转换SQL语句为全大写，Y-全大写、N-全小写、默认不处理
	 *
	 * @return
	 */
	public static String getSqlCase() {
		String open = "";
		if (!DataUtil.isNullStr(getString("sql.case"))) {
			open = getString("sql.case");
		}
		return open;
	}

	public static String getRedisClusterOpen() {
		String flg = CONF_N;
		if (!DataUtil.isNullStr(getString("sm.redis.open"))) {
			flg = getString("sm.redis.open");
		}
		return flg;
	}

	public static String getCacheAgentOpen() {
		String open = CONF_N;
		if (CONF_Y.equalsIgnoreCase(getString("cache.agent.open"))) {
			open = CONF_Y;
		}
		return open;
	}

	public static String getBusiRuleQueueList(){
		return getString("busi.code.queue.list");
	}
}
 