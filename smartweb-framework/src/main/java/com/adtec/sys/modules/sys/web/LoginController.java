package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.*;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.security.RSAUtils;
import com.adtec.sys.common.security.shiro.session.SessionDAO;
import com.adtec.sys.common.servlet.ValidateCodeServlet;
import com.adtec.sys.common.utils.CookieUtils;
import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.security.FormAuthenticationFilter;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登录Controller
 * 
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(LoginController.class);

	private final SessionDAO sessionDAO;

	@Autowired
	public LoginController(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		// 如果已登录，再次访问主页，则退出原账号。
		if (ParamUtil.TRUE.equals(ParamUtil.getString("="))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		//20190919 add by chenyl 设置主题样式
		// 获取配置的主题
		String theme = ParamUtil.getString("project.theme");
		if (null == theme || "".equals(theme)) {
			theme = "tech";
		}
		CookieUtils.setCookie(response, "theme", theme);
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
		// 20170822 add by chenyl for 拦截处理session超时页面跳转处理(ajax请求方式)
		String requestedWith = request.getHeader("X-Requested-With");
		// 如果是通过Ajax跳转到登录页面，则通过返回的head的码为401进行页面跳转
		if (StringUtil.isNotEmpty(requestedWith) && StringUtil.equals(requestedWith, "XMLHttpRequest")) {
			// 如果是ajax返回指定数据
			try {
				response.sendError(401);
			} catch (IOException e) {
                System.out.println("出现异常");
            } // 会话超时
		}

		/**
		 * 添加人：chenyl
		 * 20170807

		//根据登录状态选择登录界面
		String loginPage = null;
		if ("sys".equals(CookieUtils.getCookie(request, "loginType"))) {
			loginPage = "modules/sys/sysLogin";
		}else{
			loginPage = "modules/sys/sysLoginOT";
		}
		return loginPage;
		 */
		if (getShowCodeFlg()) {
			model.addAttribute("isValidateCodeLogin", Boolean.valueOf(true));
		}
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		String tntId = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_RENT_PARAM);
		
		if (StringUtil.isBlank(message) || StringUtil.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_RENT_PARAM, tntId);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		//登录失败 验证码变化一次
//		ValidateCodeServlet.validate(request,ValidateCodeServlet.VALIDATE_CODE);
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		//清除用户信息cookie
		CookieUtils.setCookie(response, "password", "",0);
		CookieUtils.setCookie(response, "username", "",0);

		if (getShowCodeFlg()) {
			model.addAttribute("isValidateCodeLogin", Boolean.valueOf(true));
		}
		return  "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();
		//清理缓存
		UserUtils.removeCache(UserUtils.CACHE_USER_ID_LIST+UserUtils.getUser().getId());
		/*if (StringUtils.isBlank(principal.getLoginIp())) {
			return "redirect:" + adminPath + "/sys/user/firstLoginModifyPwd";
		}*/
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (ParamUtil.TRUE.equals(ParamUtil.getString("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtil.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtil.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
//		if (principal.isMobileLogin()){
//			if (request.getParameter("login") != null){
//				return renderString(response, principal);
//			}
//			if (request.getParameter("index") != null){
//				return "modules/sys/sysIndex";
//			}
//			return "redirect:" + adminPath + "/login";
//		}
		
		//add by chenyl 20170426 for 登录成功后缓存租户信息列表和当前使用的租户信息
		// 变更用户信息及租户用户表
		User user = UserUtils.getUser();
		CookieUtils.setCookie(response, "token", principal.getSessionid());

		// 获取配置的主题
		String theme = ParamUtil.getString("project.theme");
		if (null == theme || "".equals(theme)) {
			theme = "tech";
		}
		CookieUtils.setCookie(response, "theme", theme);
		//达州添加校验
		String authId = CookieUtils.getCookie(request, "authorization");
		if(DataUtil.isNullStr(authId)){
			authId = IdGen.uuid();
		}
		/**
		 * 登录成功后，设置令牌
		 * 一个存在session里面 
		 * 一个存cookie里面，页面ajax提交时，在head中上送此值，后台拦截后与session的值对比
		 */
		request.getSession().setAttribute("authId", authId);
		CookieUtils.setCookie(response, "authorization", authId);
		/**监控大屏请求处理*/
		screenReqDeal(request);

		return "modules/sys/sysIndex";
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtil.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtil.get("loginFailMap");
		log.info("缓存中的loginFailMap："+JSON.toJSONString(loginFailMap));
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
			log.info("失败次数："+loginFailNum);
		}
		/*20211029 add by chenyl for 当验证失败次数超过5次则修改为禁止登陆，并情况验证码*/
		if(loginFailNum>=5){
			SystemService systemService = SpringContextHolder.getBean(SystemService.class);
			User user = systemService.getUserByLoginNameNoCache(useruame);
			if(null!=user){
				user.setLoginSwitchFlg(ParamUtil.NO);
				user.setRmrk("【"+StringUtil.getRemoteAddr(Servlets.getRequest())+"】尝试登陆失败次数超过5次，账户被禁用");
				systemService.saveUser(user);
				loginFailNum = 0;
				clean = true;
			}
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		log.info("更新缓存中的loginFailMap："+JSON.toJSONString(loginFailMap));
		CacheUtil.put("loginFailMap", loginFailMap);
		return loginFailNum <= 5;
	}
	
	/*20181206 add by chenyl for 获取RSA加密使用的公钥*/
	@RequestMapping(value="publicKey", method = RequestMethod.POST)
	public void getKey(HttpServletRequest request, HttpServletResponse response){
		String publicKey = RSAUtils.generateBase64PublicKey();
		Map<String, String> map = Maps.newHashMap();
		map.put("key", publicKey);
		IDataset resDs = DatasetService.getInstace().getDataset(map);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "获取公钥成功");
	}

	/**
	 * 监控大屏请求处理类
	 * @param request
	 */
	public void screenReqDeal(HttpServletRequest request) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String screenReqFlag = reqDs.getString("screenReqFlag");
		request.getSession().setAttribute("screenReqFlag", screenReqFlag);
		if ("true".equals(screenReqFlag)) {
			request.getSession().setAttribute("startTime", reqDs.getString("startTime"));
			request.getSession().setAttribute("endTime", reqDs.getString("endTime"));
			request.getSession().setAttribute("glbl_biz_swfno", reqDs.getString("glbl_biz_swfno"));
			request.getSession().setAttribute("prvpt_cenmd_no", reqDs.getString("prvpt_cenmd_no"));
			request.getSession().setAttribute("prvpt_mcrsv_no", reqDs.getString("prvpt_mcrsv_no"));
			request.getSession().setAttribute("cnsmr_mcrsv_swfno", reqDs.getString("cnsmr_mcrsv_swfno"));
			request.getSession().setAttribute("transTmMin", reqDs.getString("transTmMin"));
			request.getSession().setAttribute("transTmMax", reqDs.getString("transTmMax"));
			request.getSession().setAttribute("mcrsv_fnct_intfc_ecd", reqDs.getString("mcrsv_fnct_intfc_ecd"));
			request.getSession().setAttribute("trd_dlwth_retn_cd", reqDs.getString("trd_dlwth_retn_cd"));
			request.getSession().setAttribute("scene_idcd", reqDs.getString("scene_idcd"));
			request.getSession().setAttribute("biz_lunch_org_ecd", reqDs.getString("biz_lunch_org_ecd"));
			request.getSession().setAttribute("chnl_typ_cd", reqDs.getString("chnl_typ_cd"));
		}
	}
	
	private boolean getShowCodeFlg(){
	    String codeFlg = ParamUtil.getString("verificationCode.always.show");

	    return ((StringUtil.isNotEmpty(codeFlg)) && ("Y".equals(codeFlg)));
	}
}
