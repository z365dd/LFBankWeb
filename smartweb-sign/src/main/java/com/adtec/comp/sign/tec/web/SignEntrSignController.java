package com.adtec.comp.sign.tec.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.SignEntrCanReqDTO;
import com.adtec.comp.sign.dto.SignEntrModReqDTO;
import com.adtec.comp.sign.dto.SignEntrQryReqDTO;
import com.adtec.comp.sign.dto.SignEntrSignReqDTO;
import com.adtec.comp.sign.tec.service.SignEntrSignService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value="${adminPath}/comp/sign/tec/entrsign")
public class SignEntrSignController extends BaseController{
	
	@Autowired
	private SignEntrSignService entrService;
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toList"})
	public String toList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/entrSignList";
	}
	
	/**
	 *返回新增或修改页面页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toForm"})
	public String toForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/entrSignForm";
	}
	
	/**
	 *单位签约查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"entrSignQry"})
	public void qry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String SUB_BUSI_NO = reqDs.getString("SUB_BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String ACCT = reqDs.getString("ACCT");
		String CERT_TP = reqDs.getString("CERT_TP");
		String CERT_NO = reqDs.getString("CERT_NO");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String SIGN_STAT = reqDs.getString("SIGN_STAT");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		SignEntrQryReqDTO reqBody = new SignEntrQryReqDTO();
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setSUB_BUSI_NO(SUB_BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setCERT_TP(CERT_TP);
		reqBody.setCERT_NO(CERT_NO);
		reqBody.setACCT(ACCT);
		reqBody.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqBody.setSIGN_STAT(SIGN_STAT);
		
		IDataset resDs = entrService.qry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 单位签约新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"entrSignAdd"})
	public void add(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String addDataStr = reqDs.getString("ENTR_LIST");
		/*String --> JSON */
		JSONObject jo = JSON.parseObject(addDataStr);
		JSONArray joArr = new JSONArray();
		joArr.add(jo);
		SignEntrSignReqDTO reqBody = new SignEntrSignReqDTO();
		reqBody.setENTR_LIST(joArr);
		reqBody.setENTR_NUM(joArr.size());
		
		IDataset resDs = entrService.add(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 单位签约修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"entrSignMod"})
	public void mod(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String modDataStr = reqDs.getString("ENTR_LIST");
		/*String --> JSON */
		JSONObject jo = JSON.parseObject(modDataStr);
		JSONArray joArr = new JSONArray();
		joArr.add(jo);
		SignEntrModReqDTO reqBody = new SignEntrModReqDTO();
		reqBody.setENTR_LIST(joArr);
		reqBody.setENTR_NUM(joArr.size());
		
		IDataset resDs = entrService.mod(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 单位解约
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"entrSignCan"})
	public void del(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String delDataStr = reqDs.getString("delDataStr");
		/*String --> JSON */
		JSONObject jo = JSON.parseObject(delDataStr);
		JSONArray joArr = new JSONArray();
		joArr.add(jo);
		SignEntrCanReqDTO reqBody = new SignEntrCanReqDTO();
		reqBody.setENTR_LIST(joArr);
		reqBody.setENTR_NUM(joArr.size());
		
		IDataset resDs = entrService.del(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
}
