package com.adtec.comp.ctrl.oper.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.FCtrlMngPubParaModListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaQryReqDTO;
import com.adtec.comp.ctrl.oper.service.PubParaService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/pubpara")
public class PubparaController extends BaseController{
	
	@Autowired
	private PubParaService pubParaService;
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/pubpara/fCtrlMngPubPara";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "toMod","" })
	public String toMod(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/pubpara/fCtrlMngPubParaMod";
	}
	
	/**
	 * 查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"pubParaQry"})
	public void pubParaQry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String KEY = reqDs.getString("KEY");
		FCtrlMngPubParaQryReqDTO req = new FCtrlMngPubParaQryReqDTO();
		req.setKEY(KEY);
		
		IDataset resDs = pubParaService.qry(req);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "查询成功");
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"pubParaMod"})
	public void pubParaMod(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String KEY_LIST = reqDs.getString("KEY_LIST");
		JSONArray jsonArray = JSON.parseArray(KEY_LIST);
		List<FCtrlMngPubParaModListDTO> reqList = new ArrayList<FCtrlMngPubParaModListDTO>();
		for (int a = 0; a < jsonArray.size(); a++) {
			JSONObject jo=jsonArray.getJSONObject(a);
			String KEY = jo.getString("KEY");
			String KEY_NAME = jo.getString("KEY_NAME");
			String KV = jo.getString("KV");
			FCtrlMngPubParaModListDTO reqDto = new FCtrlMngPubParaModListDTO();
			reqDto.setKEY(KEY);
			reqDto.setKEY_NAME(KEY_NAME);
			reqDto.setKV(KV);
			reqList.add(reqDto);
		}
		
		FCtrlMngPubParaModReqDTO reqBody = new FCtrlMngPubParaModReqDTO();
		reqBody.setKEY_LIST(reqList);
		reqBody.setNUM(reqList.size());
		IDataset resDs = pubParaService.Mod(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "修改成功");
	}
}
