package com.adtec.sys.modules.sys.security;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.ms.msagent.util.DataUtil;
import com.adtec.sys.common.security.Digests;
import com.adtec.sys.common.security.RSAUtils;
import com.adtec.sys.common.servlet.ValidateCodeServlet;
import com.adtec.sys.common.utils.Encodes;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.sys.entity.*;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.LogUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.web.LoginController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

/**
 * 系统安全认证实现类
 *
 * @version 2014-7-5
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private final static Logger logger = LoggerFactory.getLogger(SystemAuthorizingRealm.class);

	private SystemService systemService;

	/**
	 * 认证回调函数, 登录时调用
	 * modify by chenyl
	 * modify time 20170807
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		logger.info("Shiro-system登录认证");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		int activeSessionSize = getSystemService().getSessionDao().getActiveSessions(false).size();
		if (logger.isDebugEnabled()) {
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}
		// 检查用户状态
		User user = getSystemService().getUserByLoginNameNoCache(token.getUsername());
		if (user != null) {
			if (ParamUtil.NO.equals(user.getLoginSwitchFlg())){
				throw new AuthenticationException("msg:该帐号禁止登录.");
			}
			if (ParamUtil.NO.equals(user.getOffice().getValidSwitchFlg())) {
				throw new AuthenticationException("msg:该帐号所属机构不可用，禁止登录.");
			}
			if (ParamUtil.NO.equals(user.getCorporation().getValidSwitchFlg())) {
				throw new AuthenticationException("msg:该帐号所属法人不可用，禁止登录.");
			}
		}
		// 校验登录验证码
		LoginController.isValidateCodeLogin(token.getUsername(), false, false);
		/*20211029 add by chenyl for 新增对验证码的后端验证，防止前端绕过*/
		Session session = UserUtils.getSession();
		String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		/*20211029 mod by chenyl for 对缓存的验证码进行解密*/
		code = Digests.aesDecrypt(code);
		// 获取验证码
		String validateCode = token.getValidateCode();
		if (DataUtil.isNullStr(validateCode)) {
			throw new AuthenticationException("msg:验证码不能空。");
		}
		if (!validateCode.toUpperCase().equals(code)) {
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		AuthenticationInfo authenticationInfo = null;
		
		authenticationInfo = validateUsernameAndPassword(token);

		return authenticationInfo;
	}
	
	/**
	 * 验证用户名密码
	 * @param token
	 * @return
	 * Add by chenyl 20170807
	 */
	private AuthenticationInfo validateUsernameAndPassword(UsernamePasswordToken token) {
		AuthenticationInfo authenticationInfo = null;
		// 校验用户名密码
		User user = getSystemService().getUserByLoginNameNoCache(token.getUsername());
		if (user != null) {
			if (ParamUtil.NO.equals(user.getLoginSwitchFlg())){
				throw new AuthenticationException("msg:该帐号禁止登录.");
			}
			if (ParamUtil.NO.equals(user.getOffice().getValidSwitchFlg())){
				throw new AuthenticationException("msg:该帐号所属机构不可用，禁止登录.");
			}
			if (ParamUtil.NO.equals(user.getCorporation().getValidSwitchFlg())){
				throw new AuthenticationException("msg:该帐号所属法人不可用，禁止登录.");
			}
			List<User> userList = new ArrayList<User>();

			boolean isContain = false;
			if(user.isAdmin()){
				// 如果是超级管理员可以无任何阻碍登录
				isContain = true;
			}else{
				for (User u : userList) {
					if (u.getId().equals(user.getId())) {
						isContain = true;
						break;
					}
				}
			}
			/*20181206 add by chenyl for 对密码进行解密*/
			String password = new String(token.getPassword());
			password = RSAUtils.decryptBase64(password);
			token.setPassword(password.toCharArray());
			byte[] salt = Encodes.decodeHex(user.getPwd().substring(0,16));
			authenticationInfo = new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin(), token.getMaxWidth()), 
					user.getPwd().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			authenticationInfo = null;
		}
		return authenticationInfo;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!ParamUtil.TRUE.equals(ParamUtil.getString("user.multiAccountLogin"))) {
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal,
					UserUtils.getSession());
			if (sessions.size() > 0) {
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()) {
					for (Session session : sessions) {
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else {
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list) {
				if (StringUtils.isNotBlank(menu.getAuth())) {
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getAuth(), ",")) {
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()) {
				info.addRole(role.getEngName());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}

	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}

	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermitted(permissions, info);
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null && user.isDeveloper()) {
			return true;
		}
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}

	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermittedAll(permissions, info);
	}

	/**
	 * 授权验证方法
	 * 
	 * @param permission
	 */
	private void authorizationValidate(Permission permission) {
		// 模块授权预留接口
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 清空用户关联权限认证，待下次使用时重新加载
	 */
	public void clearCachedAuthorizationInfo(Principal principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清空所有关联认证
	 * 
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null) {
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		private Rent rent; // 登录的租户信息
		// 20170830 add by chenyl for 存储当前登录用户所使用的屏幕像素宽
		private String maxWidth; // 屏幕分辨率的宽
		private String loginIp;//上次登录IP

		private Map<String, Object> cacheMap;
		private Map<String, UserAcctDO> acctMap = Maps.newHashMap();

		public Principal(User user, boolean mobileLogin, String maxWidth) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.name = user.getName();
			this.mobileLogin = mobileLogin;
			this.maxWidth = maxWidth;
			this.loginIp = user.getLoginIp();
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

		public String getMaxWidth() {
			return maxWidth;
		}

		public String getLoginIp() {
			return loginIp;
		}

		/**
		 * @return the rent
		 */
		public Rent getRent() {
			return rent;
		}

		@JsonIgnore
		public Map<String, Object> getCacheMap() {
			if (cacheMap == null) {
				cacheMap = new HashMap<String, Object>();
			}
			return cacheMap;
		}
		
		@JsonIgnore
		public Map<String, UserAcctDO> getAcctMap() {
			if (acctMap == null) {
				acctMap = new HashMap<String, UserAcctDO>();
			}
			return acctMap;
		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try {
				return (String) UserUtils.getSession().getId();
			} catch (Exception e) {
				return "";
			}
		}

		@Override
		public String toString() {
			return id;
		}

	}
}
