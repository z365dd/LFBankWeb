package com.adtec.para.chk.web;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.para.chk.service.ParaChkAgentService;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/para/chk")
public class ParaChkAgentController extends BaseController{
	
	@Autowired
	private ParaChkAgentService chkAgentService;
	
	/**
	 * 缓存代理Agent管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/managePage"})
	public String managePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/agentManage";
	}
	/**
	 * 缓存代理Agent列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/listPage"})
	public String agentListPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/agentList";
	}
	/**
	 * 缓存代理Agent本地缓存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/agentData"})
	public String agentData(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/agentData";
	}
	
	/**
	 * agent存储列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/agentRulesPage"})
	public String agentRulesPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/agentRulesPage";
	}
	/**
	 * agent存储列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/agentDataPage"})
	public String agentDataPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/agentDataPage";
	}
	
	/**
	 * 缓存数据查询管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/managePage"})
	public String dataManagePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/dataManage";
	}
	
	/**
	 * 存储规则列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/rulesPage"})
	public String rulesPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/rulesPage";
	}
	/**
	 * 缓存数据列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/dataPage"})
	public String dataPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/dataPage";
	}
	/**
	 * 缓存数据详情列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/dataDetail"})
	public String dataDetail(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/chk/dataDetail";
	}
	
	/**
	 * 缓存代理Agent列表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/list"})
	public void agentList(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.agentList(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	/**
	 * 缓存代理Agent数据查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/agentDataDetail"})
	public void agentDataDetail(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.agentDataDetail(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * Agent所属租户_参与者有权限读取的存储规则
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/agentRulesList"})
	public void agentRulesList(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.agentRulesList(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * Agent监控页面 - Agent所属租户_参与者有权限读取的存储规则
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"agent/agentDataList"})
	public void agentDataList(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.agentDataList(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}

	
	/**
	 * 存储规则列表
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/ruleList"})
	public void ruleList (HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.ruleListByPage(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * 数据列表
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/dataList"})
	public void dataList (HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.dataList(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * 根据Key获取reids中的数据
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"data/getData"})
	public void getData(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = chkAgentService.getData(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
}
