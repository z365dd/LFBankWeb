package com.adtec.sys.listener;

import com.adtec.cache.cacheagent.service.CacheAgentMngService;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comm.protocol.tcp.TCPJsonServer;
import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.ErrMsg;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.ms.msagent.service.AgentMngService;
import com.adtec.ms.msagent.util.MapKey;
import com.adtec.ms.msagent.util.Status;
import com.adtec.sys.common.security.Digests;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.sys.service.SysPermissionWeightService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.seq.PlatSeq;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 容器装载监听类
 * 
 * @author 陈应龙
 *
 */
public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	protected final static Logger log = LoggerFactory.getLogger(WebContextListener.class);
	private PlatSeq platSeq = null;
	private final static String QUARTZ_NAME = "SpringJobSchedulerFactoryBean";	// 所有任务管理器定义的bean的id以此为开头

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		// 20171212 add by chenyl for 添加全局流水号(yyyyMMddHHmmss+10位流水序号)到日志输出
		GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, PlatSeq.getGlobalSeq());
		WebApplicationContext wpc = super.initWebApplicationContext(servletContext);
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		Servlets.webContextPath = wpc.getServletContext().getContextPath();
		
		// 清除所有的缓存
		CacheUtil.getCacheManager().clearAll();
		/*20220110 add by chenyl for 初始化字典缓存数据*/
		DictUtils.initDictInCache();
		platSeq = new PlatSeq();
		Thread seqThread = new Thread(platSeq);
		try {
			platSeq.restoreSeq();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 启动自动保存流水线程
		seqThread.start();

		// 初始化错误码
		ErrMsg.init();

		// 初始化http-json.xml
		HttpJsonFactory.initHttpJsonXml();

		cacheAgentStart();

		try{
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 启动Agent
				AgentMngService agentMngService = (AgentMngService) SpringContextHolder.getBean("agentMngService");
				List<HashMap<String, Object>> loginInfos = Lists.newArrayList();
				// 登录1，租户：domain、参与者：Test1、参与者版本：V1.0
				HashMap<String, Object> loginMap = Maps.newHashMap();
				String tenant = ParamUtil.getConfig("ReqTenant");
				String partId = ParamUtil.getConfig("ReqPartId");
				String partVersion = ParamUtil.getConfig("ReqPartVersion");
				String loginUser = ParamUtil.getConfig("Adubbo.username");
				String loginPassword = ParamUtil.getConfig("Adubbo.password");
				String accessKey = ParamUtil.getConfig("Adubbo.accesskey");
				String hostName = ParamUtil.getConfig("ReqHostName");
				String port = ParamUtil.getConfig("ReqPort");
				loginMap.put(MapKey.TENANT, tenant);
				loginMap.put(MapKey.PARTID, partId);
				loginMap.put(MapKey.PART_VERSION, partVersion);
				loginMap.put(MapKey.LOGIN_USER, loginUser);
				loginMap.put(MapKey.LOGIN_PASSWORD, Digests.aesDecrypt(loginPassword));
				loginMap.put(MapKey.LOGIN_ACCESS_KEY, Digests.aesDecrypt(accessKey));
				/*20190319 add by chenyl for 新增参与者登录时入参当前运行服务器所在IP和Port,msagent-2.0.4版本后支持*/
				loginMap.put(MapKey.HOST_NAME, hostName);
				loginMap.put(MapKey.PORT, port);
				loginInfos.add(loginMap);
				HashMap<String, Object> retMap = agentMngService.startAgent(loginInfos);
				String retCode = (String) retMap.get(MapKey.RETCODE);
				System.out.println("agent启动：" + retMap.toString());
				if (null != retCode && !Status.SUCCESS.equals(retCode)) {
					// 启动agent失败则抛异常
					log.error("agent启动失败：" + retMap.get(MapKey.MSG));
					throw new BaseException(SysErr.E_MESSAGE, "agent启动失败：" + retMap.get(MapKey.MSG));
				}

				HashMap<String, Object> resultMap = Maps.newHashMap();
				List resultList = (List)retMap.get(MapKey.RESULTS);
				if(null!=resultList && !resultList.isEmpty()){
					HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(0);
					resultMap.put(MapKey.AGENTSEQ, retMap.get(MapKey.AGENTSEQ));
					resultMap.putAll(map);
				}else{
					log.error("agent启动返回格式有误：" + resultList);
					throw new BaseException(SysErr.E_MESSAGE, "agent启动返回格式有误");
				}
				// 换成Agent信息到换成中
				CacheUtil.put("MSAgent", resultMap);
			}else{
				if(log.isInfoEnabled()){
					log.info("不存在服务治理管理模块，不启动微服务代理");
				}
			}
		}catch(Exception e){
			log.error("启动微服务代理失败", e);
			System.exit(0);
		}
		
		// 20180330 add by chenyl for 启动TCPJsonServer
		/* 20190411 add by chenyl for 根据配置文件中的tcp.json.run=Y时才启动*/
		if (ParamUtil.CONF_Y.equalsIgnoreCase(ParamUtil.getTcpJsonOpen())) {
			TCPJsonServer.startServer();
		}

		// 增量初始化菜单权限
		SysPermissionWeightService sysPermissionWeightService = SpringContextHolder.getBean("sysPermissionWeightService");
		sysPermissionWeightService.initPermission("false");

		/*20200225 add by chenyl for 添加主动释放当前启动线程中使用到的数据库操作*/
		DBSessionFactory.clear();
		
		return wpc;
	}

	private void cacheAgentStart() {
		try{
			if(ParamUtil.CONF_Y.equals(ParamUtil.getCacheAgentOpen())){
				log.info("缓存中心开启");
				// 启动Agent
                CacheAgentMngService agentMngService = (CacheAgentMngService) SpringContextHolder.getBean("cacheAgentMngService");
				List<HashMap<String, Object>> loginInfos = Lists.newArrayList();
				// 登录1，租户：domain、参与者：Test1、参与者版本：V1.0
				HashMap<String, Object> loginMap = Maps.newHashMap();
				String tenant = ParamUtil.getConfig("ReqTenant");
				String partId = ParamUtil.getConfig("ReqPartId");
				loginMap.put(MapKey.TENANT, tenant);
				loginMap.put(MapKey.PARTID, partId);
                loginMap.put(MapKey.LOGIN_TYPE, "0");
				loginInfos.add(loginMap);
				HashMap<String, Object> retMap = agentMngService.startAgent(loginInfos);
				String retCode = (String) retMap.get(MapKey.RETCODE);
				System.out.println("agent启动：" + retMap.toString());
				if (null != retCode && !Status.SUCCESS.equals(retCode)) {
					// 启动agent失败则抛异常
					log.error("agent启动失败：" + retMap.get(MapKey.MSG));
					throw new BaseException(SysErr.E_MESSAGE, "agent启动失败：" + retMap.get(MapKey.MSG));
				}

				HashMap<String, Object> resultMap = Maps.newHashMap();
				List resultList = (List)retMap.get(MapKey.RESULTS);
				if(null!=resultList && !resultList.isEmpty()){
					HashMap<String, Object> map = (HashMap<String, Object>)resultList.get(0);
					resultMap.put(MapKey.AGENTSEQ, retMap.get(MapKey.AGENTSEQ));
					resultMap.putAll(map);
				}else{
					log.error("agent启动返回格式有误：" + resultList);
					throw new BaseException(SysErr.E_MESSAGE, "agent启动返回格式有误");
				}
				// 换成Agent信息到换成中
				CacheUtil.put("CacheAgent", resultMap);
				log.info("缓存中心开启成功");
			}else{
				if(log.isInfoEnabled()){
					log.info("不存在缓存中心管理模块，不启动缓存中心代理");
				}
			}
		}catch(Exception e){
			log.error("启动缓存中心代理失败", e);
			System.exit(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		if (platSeq != null) {
			try {
				platSeq.saveSeq();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			platSeq.stop();// 停止线程
		}


		try{
			if(ParamUtil.CONF_Y.equals(ParamUtil.getCacheAgentOpen())){
				// 停止Agent
				HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("CacheAgent"); // 获取缓存信息agent启动信息
				String token = (String) agentMap.get(MapKey.TOKEN);
				CacheAgentMngService agentMngService = (CacheAgentMngService) SpringContextHolder.getBean("cacheAgentMngService");
				List<String> tokenList = Lists.newArrayList();
				tokenList.add(token);
				HashMap<String, Object> retMap = agentMngService.stopAgent(tokenList);
				System.out.println("agent停止：" + retMap.toString());
			}else{
				if(log.isInfoEnabled()){
					log.info("不存在缓存中心管理模块，不执行缓存中心代理停止");
				}
			}
		}catch(Exception e){
			log.error("停止缓存中心代理失败", e);
		}

		try{
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 停止Agent
				HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent"); // 获取缓存信息agent启动信息
				String token = (String) agentMap.get(MapKey.TOKEN);
				AgentMngService agentMngService = (AgentMngService) SpringContextHolder.getBean("agentMngService");
				List<String> tokenList = Lists.newArrayList();
				tokenList.add(token);
				HashMap<String, Object> retMap = agentMngService.stopAgent(tokenList);
				System.out.println("agent停止：" + retMap.toString());
			}else{
				if(log.isInfoEnabled()){
					log.info("不存在服务治理管理模块，不执行微服务代理停止");
				}
			}
		}catch(Exception e){
			log.error("停止微服务代理失败", e);
		}
		
		// 20181116 add by chenyl for 主动关闭自动任务
		String[] beanNames = SpringContextHolder.getApplicationContext().getBeanDefinitionNames();
		for(String beanName:beanNames){
			if(beanName.indexOf(QUARTZ_NAME)>-1){
				org.quartz.impl.StdScheduler quartz = (org.quartz.impl.StdScheduler)SpringContextHolder.getBean(beanName);
				if(null!=quartz){
					System.out.println("主动停止自动任务："+quartz.getSchedulerName());
					quartz.shutdown();
				}
			}
		}
		
		// 2019-09-26 add by chenyl for关闭kafka-zk连接
		try {
			Class<?> obj = Class.forName("com.adtec.monman.kafka.util.KafkaZKPoolUtils");
			Method clearZkCliPool = obj.getMethod("clearZkCliPool");
			clearZkCliPool.invoke(obj);
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.info("不存在日志监控管理模块，不执行关闭kafka-zk连接");
			}
		}
		
		// 20181116 add by chenyl for 关闭当前线程的DBSession的数据库连接
		DBSessionFactory.clear();

		// 20171212 add by chenyl for 清理线程全局变量
		GVarContainer.clearVar();
		
		// 休眠一秒钟，等待应用所有资源释放
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.contextDestroyed(event);
	}

}