package com.adtec.comp.ctrl.oper.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.FCtrlMngFlowAddListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowDelListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowModListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngTypeQryReqDTO;
import com.adtec.comp.ctrl.oper.service.ControllCheckService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/controllCheck")
public class ControllCheckController extends BaseController {
	@Autowired
	private ControllCheckService controllCheckService;
	//发起方组件号
	private static String pub_compNo;
	public static String getPub_compNo() {
		return pub_compNo;
	}

	public static void setPub_compNo(String pub_compNo) {
		ControllCheckController.pub_compNo = pub_compNo;
	}

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "controllCheckList" })
	public String empowerRuleList(HttpServletRequest request, HttpServletResponse response,String compNo) {
		ControllCheckController.setPub_compNo(compNo);
		return "starring/comp/ctrl/controllCheck/controllCheckList";
	}

	/**
	 * 返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "controllCheckForm" })
	public String empowerRuleForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/controllCheck/controllCheckForm";
	}

	/**
	 * 获取控制类型
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "ctrlTpS" })
	public void ctrlTpS(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CTRL_TP = reqDs.getString("CTRL_TP");
		FCtrlMngTypeQryReqDTO reqBody = new FCtrlMngTypeQryReqDTO();
		reqBody.setCTRL_TP(CTRL_TP);
		IDataset resDs = controllCheckService.ctrlTp(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 流程新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "save" })
	public void save(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String FLOW_ARR = reqDs.getString("FLOW_ARR");
		
		//获取最大执行号
		FCtrlMngFlowQryReqDTO qryBody = new FCtrlMngFlowQryReqDTO();
		qryBody.setCOMP_NO(MODL_NO);
		qryBody.setSVC_CODE(SVC_CODE);
		qryBody.setSUB_SVC_CODE(SUB_SVC);
		long maxSer = controllCheckService.qryMaxSer(qryBody);
		
		FCtrlMngFlowAddReqDTO reqBody = new FCtrlMngFlowAddReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC);

		List<FCtrlMngFlowAddListDTO> FLOW_LIST = new ArrayList<FCtrlMngFlowAddListDTO>();
		/* 前台json数组字符串转为list map */
		JSONArray jsonArray = JSON.parseArray(FLOW_ARR);
		reqBody.setNUM(jsonArray.size());
		for (int a = 0; a < jsonArray.size(); a++) {
			JSONObject jo=jsonArray.getJSONObject(a);
			/* AUTH_LIST 内部list */
			FCtrlMngFlowAddListDTO reqList = new FCtrlMngFlowAddListDTO();
			reqList.setEXEC_SER(maxSer+a+1);
			reqList.setCTRL_TP((String) jo.get("CTRL_TP"));
			FLOW_LIST.add(reqList);
		}
		reqBody.setFLOW_LIST(FLOW_LIST);

		IDataset resDs = controllCheckService.save(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 流程查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		FCtrlMngFlowQryReqDTO reqBody = new FCtrlMngFlowQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC);

		IDataset resDs = controllCheckService.qry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	
	/**
	 * 流程删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "Delete" })
	public void Delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");

		FCtrlMngFlowDelListDTO reqBodyList = new FCtrlMngFlowDelListDTO();
		reqBodyList.setCOMP_NO(MODL_NO);
		reqBodyList.setSVC_CODE(SVC_CODE);
		reqBodyList.setSUB_SVC_CODE(SUB_SVC);

		List<FCtrlMngFlowDelListDTO> AUTH_LIST = new ArrayList<FCtrlMngFlowDelListDTO>();
		AUTH_LIST.add(reqBodyList);

		FCtrlMngFlowDelReqDTO reqBody = new FCtrlMngFlowDelReqDTO();
		reqBody.setNUM(AUTH_LIST.size());
		reqBody.setFLOW_LIST(AUTH_LIST);

		IDataset resDs = controllCheckService.Delete(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 流程修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "revice" })
	public void revice(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");

		String FLOW_ARR = reqDs.getString("FLOW_ARR");

		FCtrlMngFlowModReqDTO reqBody = new FCtrlMngFlowModReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC);

		List<FCtrlMngFlowModListDTO> FLOW_LIST = new ArrayList<FCtrlMngFlowModListDTO>();
		/* 前台json数组字符串转为list map */
		JSONArray jsonArray = JSON.parseArray(FLOW_ARR);
		reqBody.setNUM(jsonArray.size());
		for (int a = 0; a < jsonArray.size(); a++) {
			JSONObject jo=jsonArray.getJSONObject(a);
			/* AUTH_LIST 内部list */
			FCtrlMngFlowModListDTO reqList = new FCtrlMngFlowModListDTO();
			reqList.setEXEC_SER(a+1);
			reqList.setCTRL_TP((String) jo.get("CTRL_TP"));
			FLOW_LIST.add(reqList);
		}
		reqBody.setFLOW_LIST(FLOW_LIST);

		IDataset resDs = controllCheckService.revice(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
