package com.adtec.sys.modules.sys.security;

import com.adtec.sys.common.security.shiro.CustomLoginToken;

/**
 * 用户和密码（包含验证码）令牌类
 * 
 * @version 2013-5-19
 */
public class UsernamePasswordToken extends CustomLoginToken {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否手机登录
	 */
	private boolean mobileLogin;
	/**
	 * 用户所属租户
	 */
	private String rent;
	/**
	 * 用户类型
	 */
	private String userType;
	
	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String validateCode, boolean mobileLogin, String rent, String userType, String maxWidth, String loginType) {
		super(username, password, rememberMe, host, loginType, validateCode);
		this.mobileLogin = mobileLogin;
		this.rent = rent;
		this.userType = userType;
		// 20170830 add by chenyl for 存储当前登录用户所使用的屏幕像素宽
		this.maxWidth = maxWidth;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	/**
	 * @param mobileLogin the mobileLogin to set
	 */
	public void setMobileLogin(boolean mobileLogin) {
		this.mobileLogin = mobileLogin;
	}

	/**
	 * @return the rent
	 */
	public String getRent() {
		return rent;
	}

	/**
	 * @param rent the rent to set
	 */
	public void setRent(String rent) {
		this.rent = rent;
	}

	/**
	 * 
	 * @return	用户类型
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 
	 * @param userType	用户类型
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	
}