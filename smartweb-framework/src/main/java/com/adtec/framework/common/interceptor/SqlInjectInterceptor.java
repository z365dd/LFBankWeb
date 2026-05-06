/**
 * 系统名称: SmartWeb平台
 * 模块名称: 防止SQL注入的拦截器
 * 类  名  称: SqlInjectInterceptor.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年5月14日 下午7:39:46<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.interceptor;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/**
 * @author chenyl
 *
 */
public class SqlInjectInterceptor implements HandlerInterceptor {
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(SqlInjectInterceptor.class);
	/**
	 * 获取数据字典中配置的不进行SQL注入检查的URL（格式：/a/ms/oper/flow/flowCtrl/list)，SYS_SQLINJ_IGNORE_URL
	 */
	private static final String SYS_SQLINJ_IGNORE_URL = "SYS_SQLINJ_IGNORE_URL";
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		if(ParamUtil.CONF_Y.equalsIgnoreCase(ParamUtil.getSqlInjectOpen())){
			String requestURL = request.getRequestURL().toString();
			if(log.isInfoEnabled()){
				log.info("SQL注入检查,请求URL["+requestURL+"]");
			}
			// 检查是否需要过滤不执行SQL注入语句注入检查的
			boolean ignore = false;
			// 获取数据字典中配置的SYS_SQLINJ_IGNORE_URL
	        List<Dict> list = DictUtils.getDictList(SYS_SQLINJ_IGNORE_URL);
	        if(null!=list && !list.isEmpty()){
	        	for(Dict dict:list){
	        		String url = dict.getValue();
	        		if(requestURL.indexOf(url)>-1){
	        			ignore = true;
	        			if(log.isInfoEnabled()){
	        				log.info("跳过SQL注入检查,数据字典中SYS_SQLINJ_IGNORE_URL存在URL["+url+"]");
	        			}
	                    break;
	                }
	        	}
	        }
	        if(!ignore){
	        	Enumeration<String> names = request.getParameterNames();
	            while (names.hasMoreElements()) {
	                String name = names.nextElement();
	                String[] values = request.getParameterValues(name);
	                if (null == values || values.length == 0){
						throw new BaseException(SysErr.E_IN_NULL,",请求参数[" + name + "]为空");
					}
	                for (String value : values) {
	                    value = clearXss(value);
	                    // 检查是否存在SQL注入的攻击字符
	                    if(null!=value && judgeXSS(value.toLowerCase())){
	                    	log.warn("请求URL["+requestURL+"],参数["+name+"],值["+value+"]含有非法字符,已禁止继续访问");
	                    	throw new BaseException(SysErr.E_MESSAGE, "请求URL["+requestURL+"],参数["+name+"],值["+value+"]含有非法字符,已禁止继续访问");
	                    }
	                }
	            }
	        }
		}
        return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
     * 处理字符转义
     *
     * @param value
     * @return
     */
    private String clearXss(String value) {
        if (value == null || "".equals(value)) {
            return value;
        }
        value = value.replaceAll("<", "<").replaceAll(">", ">");
        value = value.replaceAll("\\(", "(").replace("\\)", ")");
        value = value.replaceAll("'", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
                "\"\"");
        value = value.replace("script", "");
        return value;
    }
    
    /**
	 * 判断参数是否含有攻击串
	 * @param value
	 * @return
	 */
	public boolean judgeXSS(String value){
		if(value == null || "".equals(value)){
			return false;
		}
		String xssStr = "and|or|select|update|delete|drop|truncate|%20|=|-|--|;|'|%|#|+|,|//|/| |\\|!=|(|)";
		String[] xssArr = xssStr.split("\\|");
		for(int i=0;i<xssArr.length;i++){
			if(value.indexOf(xssArr[i])>-1){
				return true;
			}
		}
		return false;
		
	}
}
