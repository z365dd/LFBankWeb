package com.adtec.para.rules.web;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.para.rules.service.ParaRulesStgService;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/para/rules/stg")
public class ParaRulesStgController extends BaseController  {
	
	@Autowired
	private ParaRulesStgService stgService;

	/**
	 * 存储规则管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"managePage"})
	public String managePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/rules/stgManage";
	}
	
	/**
	 * 存储规则列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "listPage" })
	public String listPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/rules/stgList";
	}
	
	/**
	 * 存储规则新增
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "addPage" })
	public String addPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/rules/stgAddForm";
	}
	
	/**
	 * 存储规则详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "detailPage" })
	public String detailPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/rules/stgDetailForm";
	}
	
	/**
	 * 存储规则详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "grantPage" })
	public String grantPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/rules/stgGrantForm";
	}
	
	/**
	 * 存储规则修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "updatePage" })
	public String updatePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/rules/stgUpdateForm";
	}
	

	/**
	 * 存储规则列表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "list" })
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = stgService.listByPage(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
	}
	
	/**
	 * 存储规则新增
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "insert" })
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		int rs = stgService.getStg(reqDs);
		if (rs > 0){
			IDataset dataset = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, dataset, SysErr.E_MESSAGE, "本缓存中心已存在存储规则["+reqDs.getString("engName")+"]！");
		}else {
			stgService.insert(reqDs);
			IDataset dataset = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
		}
	}
	
	/**
	 * 存储规则详情
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "get" })
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		String id = reqDs.getString("id");
		IDatasets dataset = stgService.get(id);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
	}
	
	/**
	 * 存储规则更新
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "update" })
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
//		int rs = stgService.getStg(reqDs);
//		if (rs > 0){
//			IDataset dataset = DatasetService.getInstace().getDataset();
//			setResponseDataset(request, response, dataset, IErrMsg.ERR_DEFAULT, "本缓存中心已存在存储规则["+reqDs.getString("enname")+"]！");
//		}else {
//			
//		}
		stgService.update(reqDs);
		IDataset dataset = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
	}
	
	/**
	 * 存储规则授权
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "grant" })
	public void grant(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		stgService.grant(reqDs);
		IDataset dataset = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
	}
	
	/**
	 * 存储规则删除
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "delStg" })
	public void delStg(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		stgService.delStg(reqDs);
		IDataset dataset = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
	}
	
	/**
	 * 缓存规则停用
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "stop" })
	public void stop(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		stgService.stop(reqDs);
		IDataset dataset = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
	}
	
//	@RequiresPermissions("user")
//	@RequestMapping(value = { "getTypes" })
//	public void getTypes(HttpServletRequest request, HttpServletResponse response) {
//		IDataset reqDs = DatasetService.getInstace().getDataset(request);
//		IDataset dataset = stgService.getTypes();
//		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "交易成功！");
//	}
	
}
