/**
 * 系统名称: SmartWeb平台
 * 模块名称: 异常处理模块
 * 功能描述: 全局异常类处理类
 * 类 名 称  : GlobalExceptionResolver.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月18日 上午10:40:26<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.mapper.JsonMapper;
import com.adtec.sys.common.web.Servlets;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author chenyl
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		System.out.println("统一异常处理开始...");
		/*20210518 add by chenyl for 新增对xml、json报文请求的统一异常处理对象返回*/
		String contentType = request.getHeader("Content-Type");
//		Enumeration headerNames = request.getHeaderNames();
//		while(headerNames.hasMoreElements()){
//			Object headKey = headerNames.nextElement();
//			System.out.println("【"+headKey+"】"+request.getHeader(""+headKey));
//		}
		if(Servlets.isAjaxRequest(request)){
			// 如果是ajax请求或JSON、XML
			try {
				PrintWriter writer = response.getWriter();
				GlobalExceptionDO geDO = new GlobalExceptionDO();
				geDO.setErrorInfo("错误信息");
				geDO.setMsg_type("error");
				// 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
				if (exception instanceof BaseException) {
					BaseException be = (BaseException) exception;
					geDO.setMessage(be.getMessage());
					geDO.setErrorNo(be.getErrorCode());
					geDO.setReturnCode(be.getErrorCode());
				} else if (exception instanceof AuthenticationException || exception instanceof AuthorizationException
						|| exception instanceof UnauthenticatedException) {
					// 20181120 add by chenyl for 对于msmall请求的无权限时，前端返回为401
					System.out.println("ajax requestUrl=" + request.getRequestURL().toString());
					if (request.getRequestURL().toString().indexOf(ParamUtil.getAdminPath() + "/msmall/") > -1) {
						response.setStatus(401);
						geDO.setMessage("请重新登录，谢谢！");
						geDO.setErrorNo("0401");
						geDO.setReturnCode("0401");
					} else {
						// 对于非ajax请求的无权限异常，我们都统一跳转到403.jsp页面
						response.setStatus(401);
						geDO.setMessage("操作权限不足");
						geDO.setErrorNo("0403");
						geDO.setReturnCode("0403");

					}
				} else if (exception instanceof BindException || exception instanceof ConstraintViolationException
						|| exception instanceof ValidationException) {
					// 对于非ajax请求的参数绑定异常，我们都统一跳转到400.jsp页面
					geDO.setMessage("参数绑定异常");
					geDO.setErrorNo("0400");
					geDO.setReturnCode("0400");
				} else {
					// 对于非ajax请求的系统异常，我们都统一跳转到500.jsp页面
					geDO.setMessage("系统异常");
					geDO.setErrorNo("0500");
					geDO.setReturnCode("0500");
				}
				// XML请求
				if(null!=contentType && contentType.indexOf("application/xml") > -1){
					XStreamMarshaller xmlMarshaller = xmlMarshaller();
					String xml = xmlMarshaller.getXStream().toXML(geDO);
					response.setContentType("application/xml;charset=UTF-8");
					writer.write(xml);
					// JAXB模式
//					writer.write(JaxbMapper.toXml(geDO));
				} else {
					// 默认JSON请求
					response.setContentType("application/json;charset=UTF-8");
					writer.write(JsonMapper.toJsonString(geDO));
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
                System.out.println("出现异常");
            }
		}else{
			// 其他报文类型
			//这里需要手动将异常打印出来，由于没有配置log，实际生产环境应该打印到log里面
//			exception.printStackTrace();
			// 如果不是ajax，JSP格式返回
			// 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
			if (exception instanceof BaseException) {
				//对于非ajax请求的业务异常，我们都统一跳转到error.jsp页面
				return new ModelAndView("error/error");
			} else if (exception instanceof AuthenticationException || exception instanceof AuthorizationException|| exception instanceof UnauthenticatedException) {
				// 20181120 add by chenyl for 对于msmall请求的无权限时，前端返回为401
				System.out.println("requestUrl="+request.getRequestURL().toString());
				if(request.getRequestURL().toString().contains(ParamUtil.getAdminPath()+"/msmall/")){
					response.setStatus(401);
					return new ModelAndView("error/401");
				}
				//对于非ajax请求的无权限异常，我们都统一跳转到403.jsp页面
				return new ModelAndView("error/403");
			} else if (exception instanceof BindException
					|| exception instanceof ConstraintViolationException
					|| exception instanceof ValidationException) {
				//对于非ajax请求的参数绑定异常，我们都统一跳转到400.jsp页面
				return new ModelAndView("error/400");
			} else {
				//对于非ajax请求的系统异常，我们都统一跳转到500.jsp页面
				return new ModelAndView("error/500");
			}
		}
		return null;
	}
	
	public XStreamMarshaller xmlMarshaller() {
		XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		Class<?>[] annotatedClasses = { GlobalExceptionDO.class };
		xStreamMarshaller.setAnnotatedClasses(annotatedClasses);
		xStreamMarshaller.setStreamDriver(new StaxDriver());
		xStreamMarshaller.afterPropertiesSet();
		return xStreamMarshaller;
	}

	
}
