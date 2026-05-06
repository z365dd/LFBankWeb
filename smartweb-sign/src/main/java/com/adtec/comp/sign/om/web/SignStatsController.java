package com.adtec.comp.sign.om.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.SignStatsReqDTO;
import com.adtec.comp.sign.om.service.SignStatsService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value="${adminPath}/comp/sign/om/signStats")
public class SignStatsController extends BaseController{
	
	@Autowired
	private SignStatsService signStatsService;
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/om/signStatsQry";
	}
	
	
	/**
	 * 统计信息查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qry"})
	public void qry(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String compNo = reqDs.getString("COMP_NO");
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		String strDate = reqDs.getString("STR_DATE");
		String endDate = reqDs.getString("END_DATE");
		
		SignStatsReqDTO reqBody = new SignStatsReqDTO();
		reqBody.setCOMP_NO(compNo);
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setENTR_NO(entrNo);
		reqBody.setSTR_DATE(strDate);
		reqBody.setEND_DATE(endDate);
		
		IDataset res = signStatsService.qry(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "统计信息成功");
	}
	
}
