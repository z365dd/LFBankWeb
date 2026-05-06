package com.adtec.comp.ctrl.oper.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaCompDealTParaCompParaListReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCompParaReqDTO;
import com.adtec.comp.ctrl.oper.service.CompParaService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/compPara")
public class CompParaController extends BaseController {
	@Autowired
	private CompParaService compParaService;

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "compParaList" })
	public String compParaList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/compParaList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "compParaForm" })
	public String ctrlBankForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/compParaForm";
	}

	/**
	 * 查询、新增、修改、删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "action" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String OPER_TP = reqDs.getString("OPER_TP");
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String LIST_STR = reqDs.getString("LIST");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		
		TParaCompDealTParaCompParaReqDTO reqBody = new TParaCompDealTParaCompParaReqDTO();
		reqBody.setOPER_TP(OPER_TP);
		reqBody.setCOMP_NAME(COMP_NAME);
		reqBody.setCOMP_NO(COMP_NO);
		if(LIST_STR!=null && !"".equals(LIST_STR)){
			JSONArray jsonArr = JSONArray.fromObject(LIST_STR);
			@SuppressWarnings("unchecked")
			List<TParaCompDealTParaCompParaListReqDTO> LIST =JSONArray.toList(jsonArr,TParaCompDealTParaCompParaListReqDTO.class);
			reqBody.setDYN_LIST(LIST);
		}
		
		
		IDataset resDs = compParaService.action(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

}
