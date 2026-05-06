package com.adtec.sys.modules.sys.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.adtec.framework.common.interceptor.LogInterceptor;
import com.adtec.framework.interfaces.log.ILogCounter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.sys.common.utils.Exceptions;
import com.adtec.sys.modules.sys.dao.LogDao;
import com.adtec.sys.modules.sys.dao.MenuDao;
import com.adtec.sys.modules.sys.entity.Log;
import com.adtec.sys.modules.sys.entity.LogMappingDO;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.LogMappingService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * 
 * @version 2014-11-7
 */
public class LogUtils {
	
	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";
	
	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

	private static boolean LOG_MAPPING_OPEN = ParamUtil.CONF_Y.equals(ParamUtil.getString("log.mapping.open"))?true:false;
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title){
		saveLog(request, null, null, title);
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		User user = UserUtils.getUser();
		Log log = new Log();
		boolean isLog = false;
		if (user != null && user.getId() != null){
			log.setInfoTitle(title);
			isLog = true;
		} else if (LOG_MAPPING_OPEN){
			/*20210429 add by chenyl for 新增配置免登陆授权的请求URI日志记录操作*/
			LogMappingService logMappingService = SpringContextHolder.getBean("logMappingService");
			LogMappingDO qryObj = new LogMappingDO();
			String reqUri = request.getRequestURI();
			int stIdx = reqUri.indexOf(ParamUtil.getAdminPath());
			if(stIdx>=0){
				reqUri = reqUri.substring(stIdx+ParamUtil.getAdminPath().length()); // 去掉上下文和/a
			}
			qryObj.setRequestUri(reqUri);
			List<LogMappingDO> mappingList = logMappingService.list(qryObj);
			if(null!=mappingList){
				for(LogMappingDO mapping:mappingList){
					if(ParamUtil.CONF_Y.equals(mapping.getLogStat())){
						// 只处理匹配第一条请求URL
						log.setInfoTitle(mapping.getUriCname());
						// 设置创建者和修改为超级管理员
						log.setCrtr("1");
						log.setUptr("1");
						isLog = true;
						break;
					}
				}
			}
		}
		if(isLog){
			log.setLogTp(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
			log.setTermIp(StringUtil.getRemoteAddr(request));
			log.setUserAgentMsg(request.getHeader("user-agent"));
			log.setReqUrl(request.getRequestURI());
			log.setParaDescByteData(request.getParameterMap());
			log.setReqMeth(request.getMethod());
			// 20171212 add by chenyl for 获取当前线程的全局流水号
			String globalSeq = (String) GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ);
			log.setSeq(globalSeq);
			// 异步保存日志
			new SaveLogThread(log, handler, ex, globalSeq).start();
		}
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		/**
		 * 日志对象
		 */
		private final static Logger logger = LoggerFactory.getLogger(SaveLogThread.class);
		private final Log log;
		private Object handler;
		private Exception ex;
		// 20171212 add by chenyl for 获取当前线程的全局流水号
		private String globalSeq;
		
		public SaveLogThread(final Log log, Object handler, Exception ex, String globalSeq){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
			// 20171212 add by chenyl for 设置全局流水号
			this.globalSeq = globalSeq;
		}
		
		@Override
		public void run() {
			// 20171212 add by chenyl for 设置全局流水号
			GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, globalSeq);
			try {
				logger.debug("异步执行日志保存开始");
				// 获取日志标题
				if (StringUtil.isBlank(log.getInfoTitle())) {
					String permission = "";
					if (handler instanceof HandlerMethod) {
						Method m = ((HandlerMethod) handler).getMethod();
						RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
						permission = (rp != null ? StringUtil.join(rp.value(), ",") : "");
					}
					log.setInfoTitle(getMenuNamePath(log.getReqUrl(), permission));
				}
				// oracle下修改或通过byte[]存取
				// 如果有异常，设置异常信息
				try {
					log.setJavaExctByteData(Exceptions.getStackTraceAsString(ex).getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 如果无标题并无异常日志，则不保存信息
				try {
					if (StringUtil.isBlank(log.getInfoTitle())
							&& StringUtil.isBlank(new String(log.getJavaExctByteData(), "UTF-8"))) {
						return;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 保存日志信息
				log.preInsert();
				logDao.insert(log);

				// 20211020 add by chenyl for 新增日志统计处理
				for(ILogCounter logCounter: LogInterceptor.LOG_COUNTER_LIST){
					logCounter.dealLog(log);
				}
			} finally {
				logger.debug("异步执行日志保存结束");
				// add by chenyl 20191007 for 关闭当前线程的DBSession的数据库连接
				DBSessionFactory.clear();
				// 20171212 add by chenyl for 清理当前日志线程的全局变量
				GVarContainer.clearVar();
			}
		}
	}

	/**
	 * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
	 */
	public static String getMenuNamePath(String requestUri, String permission){
		String href = StringUtil.substringAfter(requestUri, ParamUtil.getAdminPath());
		@SuppressWarnings("unchecked")
		Map<String, String> menuMap = (Map<String, String>)CacheUtil.get(CACHE_MENU_NAME_PATH_MAP);
		if (menuMap == null){
			menuMap = Maps.newHashMap();
			List<Menu> menuList = menuDao.findAllList();
			for (Menu menu : menuList){
				// 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
				String namePath = "";
				if (menu.getParentIdList() != null){
					List<String> namePathList = Lists.newArrayList();
					for (String id : StringUtil.split(menu.getParentIdList(), ",")){
						if (Menu.getRootId().equals(id)){
							continue; // 过滤跟节点
						}
						for (Menu m : menuList){
							if (m.getId().equals(id)){
								namePathList.add(m.getName());
								break;
							}
						}
					}
					namePathList.add(menu.getName());
					namePath = StringUtil.join(namePathList, "-");
				}
				// 设置菜单名称路径
				if (StringUtil.isNotBlank(menu.getMenuLink())){
					menuMap.put(menu.getMenuLink(), namePath);
				}else if (StringUtil.isNotBlank(menu.getAuth())){
					for (String p : StringUtil.split(menu.getAuth())){
						menuMap.put(p, namePath);
					}
				}
				
			}
			CacheUtil.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
		}
		String menuNamePath = menuMap.get(href);
		if (menuNamePath == null){
			for (String p : StringUtil.split(permission)){
				menuNamePath = menuMap.get(p);
				if (StringUtil.isNotBlank(menuNamePath)){
					break;
				}
			}
			if (menuNamePath == null){
				return "";
			}
		}
		return menuNamePath;
	}

	
}
