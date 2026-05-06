package com.adtec.sys.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.utils.CookieUtils;
import com.adtec.sys.common.web.BaseController;

/**
 * 登陆页面类型控制
 * @author chenyl
 *	
 */
@Controller
public class LoginTypeController extends BaseController{
	
	@RequestMapping(value = "/loginType/{loginType}")
	public String getLoginType(@PathVariable String loginType, HttpServletRequest request,HttpServletResponse response){
		if (StringUtil.isNotBlank(loginType)) {
			CookieUtils.setCookie(response, "loginType", loginType);
		}else {
			loginType = CookieUtils.getCookie(request, "loginType");
		}
		
		return "redirect:" + request.getParameter("url");
	}

}
