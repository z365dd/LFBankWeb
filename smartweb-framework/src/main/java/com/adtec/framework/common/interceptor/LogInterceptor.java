
package com.adtec.framework.common.interceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.framework.interfaces.log.ILogCounter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.modules.sys.utils.LogUtils;
import com.adtec.sys.seq.PlatSeq;

/**
 * 日志拦截器
 * 
 * @version 2014-8-19
 */
public class LogInterceptor extends BaseService implements HandlerInterceptor {
	public final static List<ILogCounter> LOG_COUNTER_LIST = new ArrayList();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		// 20171212 add by chenyl for 添加全局流水号(yyyyMMddHHmmss+10位流水序号)到日志输出
		GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, PlatSeq.getGlobalSeq());
		long beginTime = System.currentTimeMillis();//1、开始时间  
		GVarContainer.setVar(PatternParserConstant.START_TIME, beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
		if(logger.isDebugEnabled()){
			logger.debug("preHandle requestCode:{}", request.getCharacterEncoding());
		}
		if (logger.isInfoEnabled()){
	        logger.info("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
	        	.format(beginTime), request.getRequestURI());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if(logger.isInfoEnabled()){
			logger.info("postHandle requestCode:{}", request.getCharacterEncoding());
		}
		if (modelAndView != null){
			logger.info("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		if(logger.isInfoEnabled()){
			logger.info("after requestCode:{}", request.getCharacterEncoding());
		}

		// 保存日志
		LogUtils.saveLog(request, handler, ex, null);		
		long beginTime = (Long) GVarContainer.getVar(PatternParserConstant.START_TIME);//得到线程绑定的局部变量（开始时间）  
		long endTime = System.currentTimeMillis(); 	//2、结束时间  
		// 打印JVM信息。
		if (logger.isInfoEnabled()){
	        logger.info("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
	        		new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtil.formatDateTime(endTime - beginTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024, 
					(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024); 
		}
		// add by chenyl 20170509 for 关闭当前线程的DBSession的数据库连接
		DBSessionFactory.clear();
		
		// add by chenyl 20180329 for 清理异常信息线程池
		BaseException.clearErrInfo();
		
		// 20171212 add by chenyl for 清理线程全局变量
		GVarContainer.clearVar();
	}

}
