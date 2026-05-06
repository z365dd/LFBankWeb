/**
 * 系统名称: SmartWeb平台
 * 模块名称: 过滤静态文件js、css文件，在url上添加时间戳防止客户端浏览器缓存
 * 类  名  称: StaticFilesFilter.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年3月6日 上午9:58:51<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.filter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.framework.common.config.SmartwebHttpServletResponseWrapper;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.web.Servlets;

/**
 * @author chenyl
 *
 */
public class StaticFilesFilter implements Filter {
	/*用于保存请求的URL中对应静态文件的最后修改时间*/
	private static ConcurrentHashMap<String, Long> URL_MAP = new ConcurrentHashMap<String, Long>();
	private final static Logger logger = LoggerFactory.getLogger(StaticFilesFilter.class);
	/** 静态资源 为防止缓存，加上时间戳标志 */
    public static final String STATIC_TAIL = "__smartweb_t=";

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (null == req) {
			throw new BaseException(SysErr.E_NULL_POINTER,",请求对象为空");
		}
		String uri = req.getServletPath();
		String contextPath = req.getContextPath();
		String requestURL = req.getRequestURL().toString();
		String queryStr = req.getQueryString();

		// add timestamp to static resource, to avoid cache
		if (Servlets.isStaticFile(uri)) { // static
			// resource
			String resoucePath = ParamUtil.getWebPath()
					+ requestURL.substring(requestURL.indexOf(contextPath) + contextPath.length());
			if (chkFileRefresh(resoucePath)) {
				String newURL = null;
				if (!StringUtil.isEmpty(queryStr) && queryStr.trim().indexOf(STATIC_TAIL) == -1) {
					queryStr = queryStr.replaceAll("\r","")
							.replaceAll("\n","")
							.replaceAll("\\\\","");
					newURL = requestURL + "?" + queryStr + "&" + STATIC_TAIL + new Date().getTime();
					resp.sendRedirect(newURL);
					return;
				}
				if (StringUtil.isEmpty(queryStr)) {
					newURL = requestURL + "?" + STATIC_TAIL + new Date().getTime();
					resp.sendRedirect(newURL);
					return;
				}
			}
		}
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.error("静态资源过滤器处理异常", e);
		}
		return;
	}
	
	private boolean chkFileRefresh(String file){
		boolean result = false;
		File f = new File(file);
		if(f.exists()){
			long lm = f.lastModified();
			long clm = 0;
			if(URL_MAP.containsKey(file) && null!=URL_MAP.get(file)){
				clm = URL_MAP.get(file);
			}
			if(lm>clm){
				// 缓存中存在此文件，且最后修改时间小于服务器上的则需要执行更新
				URL_MAP.put(file, lm);
				result = true;
				logger.info("缓存文件["+file+"] , 最后修改时间["+lm+"]");
			}
		}
		return result;
	}
}
