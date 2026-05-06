package com.adtec.sys.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.security.shiro.CustomFormAuthenticationFilter;
import com.adtec.sys.common.security.shiro.CustomLoginToken;

/**
 * 表单验证（包含验证码）过滤类
 * 
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends CustomFormAuthenticationFilter {
	
	// 20180523 add by chenyl for 新增使用的登录类型
	public static final String LOGIN_TYPE = "system";

	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_RENT_PARAM = "rent";
	public static final String DEFAULT_USERTYPE_PARAM = "userType";
	
	/* (non-Javadoc)
	 * @see com.adtec.sys.common.security.shiro.CustomFormAuthenticationFilter#genCustomLoginToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public CustomLoginToken genCustomLoginToken(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		// 20180509 add by chenyl for 对于ajax请求入参不在parameter的，使用DataSet获取
				HttpServletRequest httpRequest = (HttpServletRequest)request;
				IDataset requestDs = DatasetService.getInstace().getDataset(httpRequest);
				String username = requestDs.getString(DEFAULT_USERNAME_PARAM);
				String password = requestDs.getString(DEFAULT_PASSWORD_PARAM);
				boolean rememberMe = Boolean.parseBoolean(requestDs.getString(DEFAULT_REMEMBER_ME_PARAM));
				String captcha = requestDs.getString(DEFAULT_CAPTCHA_PARAM);
				boolean mobile = Boolean.parseBoolean(requestDs.getString(DEFAULT_MOBILE_PARAM));
				String rent = requestDs.getString(DEFAULT_RENT_PARAM);
				String userType = requestDs.getString(DEFAULT_USERTYPE_PARAM);
				String maxWidth = requestDs.getString(DEFAULT_MAXWIDTH_PARAM);
				String loginType = requestDs.getString(DEFAULT_LOGINTYPE_PARAM);	// 如果存在登录类型，则使用上送的，否则使用默认的
				if(null == loginType || (null != loginType && StringUtil.isEmpty(loginType))){
					loginType = LOGIN_TYPE;
				}

				if (null == password){
					password = "";
				}
				String host = StringUtil.getRemoteAddr(httpRequest);
				
				// 20180510 add by chenyl for 保存请求数据在线程变量中防止验证失败后从当前线程变量中获取信息(UserInfoController类)
				GVarContainer.setVar(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
				GVarContainer.setVar(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
				GVarContainer.setVar(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
				
				return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile, rent,userType, maxWidth, loginType);
	}
}