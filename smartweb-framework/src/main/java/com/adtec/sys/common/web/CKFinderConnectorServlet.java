package com.adtec.sys.common.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.ckfinder.connector.ConnectorServlet;

/**
 * CKFinderConnectorServlet
 * 
 * @version 2014-06-25
 */
public class CKFinderConnectorServlet extends ConnectorServlet {
	private final static Logger log = LoggerFactory.getLogger(CKFinderConnectorServlet.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, false);
		super.doGet(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, true);
		super.doPost(request, response);
	}
	
	private void prepareGetResponse(final HttpServletRequest request,
			final HttpServletResponse response, final boolean post) throws ServletException {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return;
		}
		if(null == request){
			throw new BaseException(SysErr.E_NULL_POINTER, "请求为空");
		}
		log.info("prepareGetResponse start ...");
		String command = request.getParameter("command");
		String type = request.getParameter("type");
		// 20180620 add by chenyl for 新增所有用户可见
		String startupPath = request.getParameter("startupPath");// 当前文件夹可指定为模块名
		log.info("CKfinder收到请求command["+command+"],type["+type+"],startupPath["+startupPath+"]");
		boolean __IsAllVisiable = false;
		String typePath = "";	// 类型路径
		String customPath = "";	// 自定义路径
		if (startupPath!=null){
			String[] ss = startupPath.split(":");
			if(ss.length==3){
				__IsAllVisiable = Boolean.parseBoolean(ss[2]);
			}
			if(ss.length>=2){
				typePath = ss[0];
				customPath = ss[1];
			}
		}
		// 把是否所有用户可见设置到线程参数中
		GVarContainer.setVar("__IsAllVisiable", __IsAllVisiable);
		GVarContainer.setVar("__customPath", customPath);
		
		// 初始化时，如果startupPath文件夹不存在，则自动创建startupPath文件夹
		if ("Init".equals(command)) {
			String realPath = FileUtil.path(ParamUtil.getUserfilesBaseDir() + ParamUtil.USERFILES_BASE_URL
					+ (__IsAllVisiable?"":(principal + "/")) + typePath + customPath);
			FileUtil.createDirectory(FileUtil.path(realPath));
			
		}
		log.info("------------------------");
		for (Object key : request.getParameterMap().keySet()){
			log.info(key + ": " + request.getParameter(key.toString()));
		}
		
		log.info("prepareGetResponse end ...");
	}
	
}
