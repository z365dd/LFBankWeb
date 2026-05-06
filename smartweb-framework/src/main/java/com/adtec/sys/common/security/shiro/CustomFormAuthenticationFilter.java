/**
 * 系统名称: SmartWeb平台
 * 模块名称: 全局shiro拦截表单验证类
 * 类  名  称: CustomFormAuthenticationFilter.java
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
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.utils.CommonConstants;
import com.adtec.sys.common.utils.CookieUtils;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * 表单验证（包含验证码）过滤类
 * @author chenyl
 * @version 2018-05-24
 */
abstract public class CustomFormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);
	/**
	 * 登录类型
	 */
	public static final String DEFAULT_LOGINTYPE_PARAM = "loginType";
	/**
	 * 验证码
	 */
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	/**
	 * 错误信息
	 */
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	/**
	 * 屏幕最大宽度
	 */
	public static final String DEFAULT_MAXWIDTH_PARAM = "maxWidth";
	
	/**
	 * 生成交易令牌,必须重写此方法
	 * @param request
	 * @param response
	 * @return
	 */
	abstract public CustomLoginToken genCustomLoginToken(ServletRequest request, ServletResponse response);

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		return genCustomLoginToken(request, response);
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	/**
	 * 当登录成功
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 登录结果设置cookie
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 解决跨域问题
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Authentication");
		CookieUtils.setCookie(httpResponse, "LOGINED", "true");
		if (!Servlets.isAjaxRequest((HttpServletRequest) request)) {
			// form提交时地址重定
			WebUtils.issueRedirect(request, httpResponse, getSuccessUrl(), null, true);
		} else {
			Principal principal = UserUtils.getPrincipal();
			// ajax请求时返回Dataset格式返回登录成功的Url
			String type = "application/json";
			String errorNo = SysErr.E_SUCCESS;
			String errorMsg = "登录成功";
			StringBuffer tempBuffer = new StringBuffer();
			tempBuffer.append("{ \"dataSetResult\" : [");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("{ \"data\": [{");
			tempBuffer.append("\"indexUrl\": \"/").append(getSuccessUrl().replace(ParamUtil.getAdminPath() + "/", "")).append(";JSESSIONID=").append(principal.getSessionid()).append("?__sid=").append(principal.getSessionid()).append("&__cookie=true").append("\",");
			tempBuffer.append("\"sessionid\": \"").append(principal.getSessionid()).append("\",");
			tempBuffer.append("\"id\": \"").append(principal.getId()).append("\",");
			tempBuffer.append("\"username\": \"").append(principal.getLoginName()).append("\",");
			tempBuffer.append("\"name\": \"").append(principal.getName()).append("\"");
			tempBuffer.append("}],");
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
				httpResponse.setContentType(type);
				httpResponse.setCharacterEncoding("utf-8");
				httpResponse.setHeader("Pragma", "No-cache");
				httpResponse.setHeader("Cache-Control", "no-cache");
				httpResponse.setHeader("Access-Control-Allow-Origin", "*");
				httpResponse.setHeader("Access-Control-Allow-Headers", "Authentication");
				httpResponse.setDateHeader("Expires", 0L);
				PrintWriter out = httpResponse.getWriter();
				out.print(tempBuffer.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
                System.out.println("出现异常");
            }
		}
		// 当成功时阻止流程继续往下跑
		return false;

	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// 登录结果设置cookie
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		CookieUtils.setCookie(httpResponse, "LOGINED", "false");
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)) {
			message = "用户或密码错误, 请重试.";
		} else if (e.getMessage() != null && StringUtil.startsWith(e.getMessage(), "msg:")) {
			message = StringUtil.replace(e.getMessage(), "msg:", "");
		} else {
			message = "系统出现问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
			log.error(e.getMessage());
		}

		if (!Servlets.isAjaxRequest((HttpServletRequest) request)) {
			// 不是ajax请求
			request.setAttribute(getFailureKeyAttribute(), className);
			request.setAttribute(DEFAULT_MESSAGE_PARAM, message);
			return true;
		}

		// ajax请求处理
		try {
			String type = "application/json";
			String errorNo = SysErr.E_DEFAULT;
			String errorMsg = message;
			StringBuffer tempBuffer = new StringBuffer();
			tempBuffer.append("{ \"dataSetResult\" : [");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("{ \"data\": [{ \"indexUrl\": \"/")
					.append(getSuccessUrl().replace(ParamUtil.getAdminPath() + "/", "")).append("\"}],");
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
			tempBuffer.append("\"msg_type\" : \"error\"");
			tempBuffer.append(",");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("\"message\" : ");
			tempBuffer.append("\"" + errorMsg + "\"");
			tempBuffer.append(CommonConstants.CRLF);
			tempBuffer.append("}");

			// 返回错误信息
			httpResponse.setContentType(type);
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.setHeader("Pragma", "No-cache");
			httpResponse.setHeader("Cache-Control", "no-cache");
			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpResponse.setHeader("Access-Control-Allow-Headers", "Authentication");
			httpResponse.setDateHeader("Expires", 0L);
			PrintWriter out = httpResponse.getWriter();
			out.print(tempBuffer.toString());
			out.flush();
			out.close();

		} catch (IOException e1) {
                System.out.println("出现异常");
            }
		return false;
	}
	
}