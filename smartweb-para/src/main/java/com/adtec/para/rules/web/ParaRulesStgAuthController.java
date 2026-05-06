package com.adtec.para.rules.web;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.para.rules.service.ParaRulesStgAuthService;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/para/rules/stg/auth")
public class ParaRulesStgAuthController extends BaseController {
	
	@Autowired
	private ParaRulesStgAuthService rulesStgAuthService;
	
	/**
	 * 获取租户信息
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getTenanices" })
	public void getTenanices(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = rulesStgAuthService.getTenanices(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}

	/**
	 * 获取当前租户及子租户信息
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getChildRents" })
	public void getChildRents(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = rulesStgAuthService.getChildRents(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * 获取参与者信息
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getParts" })
	public void getParts(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = rulesStgAuthService.getParts(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}

	/**
	 * 获取参与者信息
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getPartList" })
	public void getPartList(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = rulesStgAuthService.getPartList();
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
}
