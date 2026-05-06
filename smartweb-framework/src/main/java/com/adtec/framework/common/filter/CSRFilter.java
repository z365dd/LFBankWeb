/**
 * 系统名称: SmartWeb平台
 * 模块名称: 跨站点请求伪造 CSRF攻击
 * 类  名  称: CSRFilter.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年5月14日 下午8:20:34<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.adtec.sys.common.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;

/**
 * @author chenyl
 * 解决：跨站点请求伪造
 * 风险： 可能会窃取或操纵客户会话和 cookie，它们可能用于模仿合法用户，从而使黑客能够以该用户身份查
 *	看或变更用户记录以及执行事务
 * 原因： 应用程序使用的认证方法不充分
 * 固定值： 验证“Referer”头的值，并对每个提交的表单使用 one-time-nonce
 */
public class CSRFilter implements Filter {
	private final static Logger log = LoggerFactory.getLogger(CSRFilter.class);
	/**
	 * 获取数据字典中配置的允许请求URL前缀，SYS_CSR_URL
	 */
	private static final String SYS_CSR_URL = "SYS_CSR_URL";
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String referer = ((HttpServletRequest)request).getHeader("Referer");
//		if(log.isInfoEnabled()){
//			log.info("请求头中的Referer值["+referer+"]");
//		}
        boolean b = false;
        boolean csrOpenFlg = false;
        if(ParamUtil.CONF_Y.equalsIgnoreCase(ParamUtil.getCsrOpen())){
        	// 获取数据字典中配置的SYS_CSR_URL
            List<Dict> list = DictUtils.getDictList(SYS_CSR_URL);
            if(null!=list && list.isEmpty()){
            	// 为空时不进行拦截
            	b = true;
            	csrOpenFlg = true;
            }else{
            	for(Dict dict:list){
            		String vReferer = dict.getValue();
//            		if(referer==null || referer.trim().startsWith(vReferer)){
            		if(referer!=null && referer.trim().startsWith(vReferer)){
                        b = true;
                        csrOpenFlg = true;
                        break;
                    }
            	}
            }
            //初次登陆时不进行拦截
			String requestURL = ((HttpServletRequest) request).getRequestURI();
            if(null == referer){
            	if("/smartweb/".equals(requestURL) || requestURL.endsWith("/login") || requestURL.contains("/menu/tree")
						|| requestURL.contains("/b_base/") || requestURL.contains("b_bide")){
            		//不进行拦截
					b = true;
					csrOpenFlg = true;
				}
			}
        }else{
        	// 没开启CSR攻击过滤
        	b = true;
        }

        //达州添加校验authorization
        String uri = ((HttpServletRequest)request).getRequestURI();
        String authId = (String) ((HttpServletRequest)request).getSession().getAttribute("authId");
        //String authorization = ((HttpServletRequest)request).getHeader("authorization");
		String authorization = CookieUtils.getCookie(((HttpServletRequest)request), "authorization");
        String isAjax = ((HttpServletRequest)request).getHeader("x-requested-with");
        //若上面csr开启且过滤不通过才继续校验，某些请求不拦截
		if (!csrOpenFlg && !DataUtil.isNullStr(isAjax) && "XMLHttpRequest".equals(isAjax) && !referer.endsWith("login")
				&& (!uri.contains("menu/tree") && !uri.contains("/b_base/") && !uri.contains("/b_ide/")
						&& !referer.contains(".js") && !referer.contains(".jsp") && !referer.contains("/druid/"))) {
			//校验authorization
			if(!DataUtil.isNullStr(authId) && !authId.equals(authorization)){
				log.error("请求权限校验异常");
				b = false;
			}
		}
        
        if(!b){
        	log.warn("疑似CSRF攻击，referer:"+referer);
        	response.setContentType("text/html;charset=UTF-8");
        	response.getWriter().print("请关闭浏览器重新访问该页面");
        }else{
        	// 允许请求
        	try {
        		chain.doFilter(request, response);
        	} catch (Exception e) {
        		log.error("CSRF过滤器处理异常", e);
    		}
        }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
