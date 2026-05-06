/**
 * 系统名称: SmartWeb平台
 * 模块名称: 全局shiro拦截分发realm
 * 类  名  称: CustomModularRealmAuthenticator.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

/**
 * @author chenyl
 *
 */
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
	/**
	 * 自定义验证realm的列表
	 */
	private Map<String, Object> definedRealms;

	/* (non-Javadoc)
	 * @see org.apache.shiro.authc.pam.ModularRealmAuthenticator#doAuthenticate(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		// 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        CustomLoginToken token = (CustomLoginToken) authenticationToken;
        // 找到当前登录人的登录类型
        String loginType = token.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 找到登录类型对应的指定Realm
        Collection<Realm> typeRealms = new ArrayList<Realm>();
        for (Realm realm : realms) {
            if (realm.getName().toLowerCase().contains(loginType)) {
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.iterator().next(), token);
        } else {
            return doMultiRealmAuthentication(typeRealms, token);
        }

	}
	
	/* (non-Javadoc)
	 * 判断realm是否为空 
	 * @see org.apache.shiro.authc.pam.ModularRealmAuthenticator#assertRealmsConfigured()
	 */
	@Override
	protected void assertRealmsConfigured() throws IllegalStateException {
		// TODO Auto-generated method stub
		this.definedRealms = this.getDefinedRealms();  
        if (CollectionUtils.isEmpty(this.definedRealms)) {  
            throw new ShiroException("值传递错误!");  
        }  
	}


	/**
	 * @return the definedRealms
	 */
	public Map<String, Object> getDefinedRealms() {
		return definedRealms;
	}

	/**
	 * @param definedRealms the definedRealms to set
	 */
	public void setDefinedRealms(Map<String, Object> definedRealms) {
		this.definedRealms = definedRealms;
	}
	
	
}
