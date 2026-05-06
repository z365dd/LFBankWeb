package com.adtec.comp.ctrl.om.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaBrchQryReqDTO;
import com.adtec.comp.ctrl.om.service.BrchQryService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/om/brchQry")
public class BrchQryController extends BaseController {
	@Autowired
	private BrchQryService brchQryService;

	
	
	/**
	 * 机构查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getBrch"})
	public void getBrch(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BRCH = reqDs.getString("BRCH");
		TParaBrchQryReqDTO reqBody = new TParaBrchQryReqDTO();
		reqBody.setBRCH(BRCH);
		
		IDataset res = brchQryService.getBrch(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "提交成功");
	}
	
	
}
