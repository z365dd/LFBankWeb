/**
 * 系统名称: SmartWeb平台
 * 模块名称: 全局shiro推出登录处理
 * 类  名  称: CustomLogoutFilter.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年5月23日 下午2:23:20<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.common.security.shiro;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.utils.CommonConstants;
import com.adtec.sys.common.web.Servlets;

/**
 * @author chenyl
 *
 */
public class CustomLogoutFilter extends LogoutFilter {
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(CustomLogoutFilter.class);
	/**
	 * 定义截取的请求路径进行不同的退出页面跳转
	 */
	private Map<String, String> logoutUrlMap = Maps.newHashMap();
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 在这里执行退出系统前需要清空的数据,并根据不同验证的登录类型登出到对应的登录界面
		Subject subject = getSubject(request, response);
		// 获取登出配置的请求url
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestUri = httpRequest.getRequestURI();
		String requestUrl = requestUri.replace(httpRequest.getContextPath(), "");
		
		// 默认的登出url,对应shiroFilter的loginUrl
		String redirectUrl = getRedirectUrl(request, response, subject);
		
		// 获取配置要跳转到退出后的页面路径
		if(logoutUrlMap.containsKey(requestUrl)){
			redirectUrl = logoutUrlMap.get(requestUrl);
		}
		
		logger.info("退出请求URL[{}],退出后跳转URL[{}]", requestUrl, redirectUrl);
		
		try {
			subject.logout();
		} catch (SessionException ise) {
			ise.printStackTrace();
		}

		if (!Servlets.isAjaxRequest((HttpServletRequest)request)){
			// form提交时地址重定
			issueRedirect(request, response, redirectUrl);
		}else{
			// ajax请求时返回Dataset格式返回登出成功的Url
			String type = "application/json";
			String errorNo = SysErr.E_SUCCESS;
			String errorMsg= "登出成功";
			StringBuffer tempBuffer = new StringBuffer();
			tempBuffer.append("{ \"dataSetResult\" : [");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("{ \"data\": [{ \"indexUrl\": \"/").append(redirectUrl.replace(ParamUtil.getAdminPath() + "/", "")).append("\"}],");
			tempBuffer.append(" \"dataSetName\":\"result\",");
			tempBuffer.append(" \"totalCount\": 1");
			tempBuffer.append("} ], ");
			tempBuffer.append("\"errorNo\" : ");
			tempBuffer.append("\"" + errorNo + "\"");
			tempBuffer.append(",");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("\"errorInfo\" : ");
			tempBuffer.append("\"" + errorMsg + "\"");
			tempBuffer.append(",");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("\"returnCode\" : ");
			tempBuffer.append("\"" + errorNo + "\"");
			tempBuffer.append(",");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("\"msg_type\" : \"success\"");
			tempBuffer.append(",");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("\"message\" : ");
			tempBuffer.append("\"" + errorMsg + "\"");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("}");
			try {
				response.reset();
		        response.setContentType(type);
		        response.setCharacterEncoding("utf-8");
				response.getWriter().print(tempBuffer.toString());
			} catch (IOException e) {
                System.out.println("出现异常");
            }
		}
		
		// 返回false表示不执行后续的过滤器，直接返回跳转到登录页面

		return false;
	}

	public Map<String, String> getLogoutUrlMap() {
		return logoutUrlMap;
	}

	public void setLogoutUrlMap(Map<String, String> logoutUrlMap) {
		this.logoutUrlMap = logoutUrlMap;
	}

}
