/**
 * 系统名称: SmartWeb平台
 * 模块名称: 自定义shiro-token重写类,用于多类型用户校验
 * 类  名  称: CustomLoginToken.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年5月23日 下午2:28:12<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.common.security.shiro;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.web.Servlets;

/**
 * @author chenyl
 *
 */
public class CustomLoginToken extends UsernamePasswordToken {
	private final static Logger log = LoggerFactory.getLogger(CustomLoginToken.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 7593296580663651414L;
	/**
	 * 登录类型，对应spring-shiro.xml配置文件中定义的name的值
	 */
	protected String loginType;
	/**
	 * 验证码
	 */
	protected String validateCode;
	/**
	 * 错误信息
	 */
	protected String message;
	/**
	 * 屏幕最大宽度
	 */
	protected String maxWidth;

	/**
	 * 
	 */
    public CustomLoginToken() {}

    /**
     * 
     * @param username
     * @param password
     * @param loginType
     */
    public CustomLoginToken(final String username, final String password, 
            final String loginType) {
        super(username, password);
        /*20200309 add by chenyl for 使用自定义的方法获取请求的IP地址*/
        try{
        	if(DataUtil.isNullStr(this.getHost())){
        		HttpServletRequest request = Servlets.getRequest();
            	String reqHost = StringUtil.getRemoteAddr(request);
            	this.setHost(reqHost);
        	}
        }catch(Exception e){
        	log.warn("获取请求的IP地址失败", e);
        }
        this.loginType = loginType;
    }
    
    /**
     * 
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     * @param loginType
     */
    public CustomLoginToken(final String username, final char[] password,
            final boolean rememberMe, final String host, 
            final String loginType) {
    	super(username, password, rememberMe, host); 
    	/*20200309 add by chenyl for 使用自定义的方法获取请求的IP地址*/
        try{
        	if(DataUtil.isNullStr(this.getHost())){
        		HttpServletRequest request = Servlets.getRequest();
            	String reqHost = StringUtil.getRemoteAddr(request);
            	this.setHost(reqHost);
        	}
        }catch(Exception e){
        	log.warn("获取请求的IP地址失败", e);
        }
    	this.loginType = loginType;
    }
    
    /**
     * 
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     * @param loginType
     * @param validateCode
     */
    public CustomLoginToken(final String username, final char[] password,
            final boolean rememberMe, final String host, 
            final String loginType, final String validateCode) {
    	super(username, password, rememberMe, host); 
    	/*20200309 add by chenyl for 使用自定义的方法获取请求的IP地址*/
        try{
        	if(DataUtil.isNullStr(this.getHost())){
        		HttpServletRequest request = Servlets.getRequest();
            	String reqHost = StringUtil.getRemoteAddr(request);
            	this.setHost(reqHost);
        	}
        }catch(Exception e){
        	log.warn("获取请求的IP地址失败", e);
        }
    	this.loginType = loginType;
    	this.validateCode = validateCode;
    }

    /**
     * 
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     * @param loginType
     * @param validateCode
     * @param message
     */
    public CustomLoginToken(final String username, final char[] password,
            final boolean rememberMe, final String host, 
            final String loginType, final String validateCode, final String message) {
    	super(username, password, rememberMe, host); 
    	/*20200309 add by chenyl for 使用自定义的方法获取请求的IP地址*/
        try{
        	if(DataUtil.isNullStr(this.getHost())){
        		HttpServletRequest request = Servlets.getRequest();
            	String reqHost = StringUtil.getRemoteAddr(request);
            	this.setHost(reqHost);
        	}
        }catch(Exception e){
        	log.warn("获取请求的IP地址失败", e);
        }
    	this.loginType = loginType;
    	this.validateCode = validateCode;
    	this.message = message;
    }
    
    /**
     * 
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     * @param loginType
     * @param validateCode
     * @param message
     * @param maxWidth
     */
    public CustomLoginToken(final String username, final char[] password,
            final boolean rememberMe, final String host, 
            final String loginType, final String validateCode, final String message, final String maxWidth) {
    	super(username, password, rememberMe, host); 
    	/*20200309 add by chenyl for 使用自定义的方法获取请求的IP地址*/
        try{
        	if(DataUtil.isNullStr(this.getHost())){
        		HttpServletRequest request = Servlets.getRequest();
            	String reqHost = StringUtil.getRemoteAddr(request);
            	this.setHost(reqHost);
        	}
        }catch(Exception e){
        	log.warn("获取请求的IP地址失败", e);
        }
    	this.loginType = loginType;
    	this.validateCode = validateCode;
    	this.message = message;
    	this.maxWidth = maxWidth;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

	/**
	 * @return the validateCode
	 */
	public String getValidateCode() {
		return validateCode;
	}

	/**
	 * @param validateCode the validateCode to set
	 */
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the maxWidth
	 */
	public String getMaxWidth() {
		return maxWidth;
	}

	/**
	 * @param maxWidth the maxWidth to set
	 */
	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

    
}
