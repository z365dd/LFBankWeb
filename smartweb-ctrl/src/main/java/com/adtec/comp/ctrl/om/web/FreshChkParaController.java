package com.adtec.comp.ctrl.om.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.FChkMngRegRedisParaReqDTO;
import com.adtec.comp.ctrl.om.service.FreshChkParaService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/om/freshChkPara")
public class FreshChkParaController extends BaseController {
	@Autowired
	private FreshChkParaService freshChkParaService;

	
	/**
	 * 返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "freshChkParaForm" })
	public String freshChkParaForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/om/freshChkParaForm";
	}
	
	/**
	 * 刷新
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"fresh"})
	public void fresh(HttpServletRequest request,HttpServletResponse response){
		//IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		FChkMngRegRedisParaReqDTO reqBody = new FChkMngRegRedisParaReqDTO();
		
		IDataset res = freshChkParaService.fresh(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "提交成功");
	}
	
	
}
