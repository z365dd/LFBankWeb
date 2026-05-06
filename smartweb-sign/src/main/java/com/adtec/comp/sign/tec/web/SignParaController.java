package com.adtec.comp.sign.tec.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.SignParaListResDTO;
import com.adtec.comp.sign.dto.SignParaModReqDTO;
import com.adtec.comp.sign.dto.SignParaQryReqDTO;
import com.adtec.comp.sign.tec.service.SignParaService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value="${adminPath}/comp/sign/tec/signPara")
public class SignParaController extends BaseController{
	
	@Autowired
	private SignParaService signParaService;
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/paraMod";
	}
	
	
	/**
	 * 签约参数查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qry"})
	public void qry(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String key = reqDs.getString("KEY");
		SignParaQryReqDTO reqBody = new SignParaQryReqDTO();
		if(key != null && !"".equals(key)){
			reqBody.setKEY(key);
		}
		IDataset res = signParaService.qry(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "签约参数查询成功");
	}
	
	/**
	 * 签约参数修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"mod"})
	public void mod(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String KEY_LIST = reqDs.getString("KEY_LIST");
		JSONArray jsonArray = JSON.parseArray(KEY_LIST);
		List<SignParaListResDTO> reqList = new ArrayList<SignParaListResDTO>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo=jsonArray.getJSONObject(i);
			String KEY = jo.getString("KEY");
			String KEY_NAME = jo.getString("KEY_NAME");
			String KV = jo.getString("KV");
			SignParaListResDTO reqDto = new SignParaListResDTO();
			reqDto.setKEY(KEY);
			reqDto.setKEY_NAME(KEY_NAME);
			reqDto.setKV(KV);
			reqList.add(reqDto);
		}
		SignParaModReqDTO reqBody = new SignParaModReqDTO();
		reqBody.setKEY_LIST(reqList);
		reqBody.setNUM(reqList.size());
		
		IDataset res = signParaService.mod(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "签约参数修改成功");
	}

}
