package com.adtec.comp.ctrl.oper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaCompDealTParapartInfoReqDTO;
import com.adtec.comp.ctrl.oper.service.CtrlPartInfoService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/partInfo")
public class CtrlPartInfoController extends BaseController{
	@Autowired
	private CtrlPartInfoService ctrlPartInfoService;
	
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "partInfoList" })
	public String partInfoList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/partInfoList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "partInfoForm" })
	public String partInfoForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/partInfoForm";
	}
	
	
	
	/**
	 * 查询、新增、修改、删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "action" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SVC_DESC = reqDs.getString("SVC_DESC");
		String OPER_TP = reqDs.getString("OPER_TP");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		
		TParaCompDealTParapartInfoReqDTO reqBody = new TParaCompDealTParapartInfoReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setCOMP_NAME(COMP_NAME);
		reqBody.setSVC_DESC(SVC_DESC);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setOPER_TP(OPER_TP);
		
		IDataset resDs = ctrlPartInfoService.action(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	
	
}
