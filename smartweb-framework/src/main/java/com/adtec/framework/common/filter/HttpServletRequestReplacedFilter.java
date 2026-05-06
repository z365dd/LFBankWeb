package com.adtec.framework.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.adtec.framework.common.config.SmartwebHttpServletRequestWrapper;
import com.adtec.framework.common.config.SmartwebHttpServletResponseWrapper;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;

/**
 * 处理所有http请求中的request对象拦截，使用自定义BodyReaderHttpServletRequestWrap类解决多次读取请求报文流问题
 * @author chenyl
 *
 */
public class HttpServletRequestReplacedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	SmartwebHttpServletRequestWrapper requestWrapper = null;
    	SmartwebHttpServletResponseWrapper responseWrapper = null;

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if ("POST".equals(httpServletRequest.getMethod().toUpperCase())
					&& null!=httpServletRequest.getContentType() && httpServletRequest.getContentType().toLowerCase().indexOf("json")>-1) {
				requestWrapper = new SmartwebHttpServletRequestWrapper((HttpServletRequest) request);
			}
		}
		
		 // 响应包装翻入线程
        responseWrapper = new SmartwebHttpServletResponseWrapper((HttpServletResponse) response);
        responseWrapper.setContentLength(-1);
 
		if (requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response); 
		}
    }
 
    @Override
    public void destroy() {
    }
}
