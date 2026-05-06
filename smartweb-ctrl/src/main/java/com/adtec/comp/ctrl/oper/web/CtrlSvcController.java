package com.adtec.comp.ctrl.oper.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaSvcAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcDelListDTO;
import com.adtec.comp.ctrl.dto.TParaSvcDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcModListDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcAddListDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcModReqDTO;
import com.adtec.comp.ctrl.oper.service.CtrlSvcService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/svccode")
public class CtrlSvcController extends BaseController{
	
	@Autowired
	private CtrlSvcService ctrlsvcService;
	private static String pub_compNo;
	public static String getPub_compNo() {
		return pub_compNo;
	}

	public static void setPub_compNo(String pub_compNo) {
		CtrlSvcController.pub_compNo = pub_compNo;
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response,String compNo,ModelMap map) {
		map.addAttribute("compNo", compNo);
		CtrlSvcController.pub_compNo = compNo;
		CtrlSvcController.setPub_compNo(compNo);
		return "starring/comp/ctrl/svccode/ctrlSvc";
	}
	
	//控制组件
	@RequiresPermissions("user")
	@RequestMapping(value = { "ctrlstart","" })
	public String ctrl_start(HttpServletRequest request, HttpServletResponse response,String compNo,ModelMap map) {
		String ctrl_comp = "999103";
		map.addAttribute("compNo", ctrl_comp);
		CtrlSvcController.pub_compNo = compNo;
		CtrlSvcController.setPub_compNo(compNo);
		return "starring/comp/ctrl/svccode/ctrlSvc";
	}
	
	//日终日切
	@RequiresPermissions("user")
	@RequestMapping(value = { "daystart","" })
	public String day_start(HttpServletRequest request, HttpServletResponse response,String compNo,ModelMap map) {
		String day_comp = "999100";
		map.addAttribute("compNo", day_comp);
		CtrlSvcController.pub_compNo = compNo;
		CtrlSvcController.setPub_compNo(compNo);
		return "starring/comp/ctrl/svccode/ctrlSvc";
	}
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSvcList"})
	public String CtrlSvcList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/svccode/ctrlSvcList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toAdd"})
	public String CtrlSvcAdd(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/svccode/ctrlSvcAdd";
	}
	
	/**
	 *返回修改页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toUpdate"})
	public String CtrlSvcRep(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/svccode/ctrlSvcUpdate";
	}
	
	
	/**
	 *获取模型号（添加服务码和查询服务码需要）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getModlNo"})
	public void getModlNo(HttpServletRequest request,HttpServletResponse response){
		TParaCompQryReqDTO reqBody = new  TParaCompQryReqDTO();
		IDataset resDs = ctrlsvcService.getModlNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "获取模型号成功");
	}
	
	/**
	 * 获取服务码 （查询页面服务码下拉框的数据 来源此处）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getSvcCode"})
	public void getSvcCode(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		TParaSvcQryReqDTO reqBody = new TParaSvcQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		IDataset resDs = ctrlsvcService.getSvcCode(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "获取服务码成功");
	}
	
	/**
	 *查阅服务码action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSvcQry"})
	public void qry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TParaSvcQryReqDTO reqBody = new TParaSvcQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		
		IDataset resDs = ctrlsvcService.SvcCodeList(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "查询成功");
	}
	
	/**
	 *新增服务码action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSvcAdd"})
	public void insert(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SVC_DESC = reqDs.getString("SVC_DESC");
		TParaSvcAddListDTO reqList = new TParaSvcAddListDTO();
		List<TParaSvcAddListDTO> list =new ArrayList<TParaSvcAddListDTO>();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSVC_DESC(SVC_DESC);
		list.add(reqList);
		TParaSvcAddReqDTO reqBody = new TParaSvcAddReqDTO();
		reqBody.setSVC_LIST(list);
		reqBody.setNUM(list.size());
		
		IDataset resDs = ctrlsvcService.SvcAdd(reqBody,COMP_NO);
		if(resDs != null){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "添加成功");
		}
	}
	
	/**
	 *修改服务码action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSvcMod"})
	public void update(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SVC_DESC = reqDs.getString("SVC_DESC");
		TParaSvcModReqDTO reqBody = new TParaSvcModReqDTO();
		TParaSvcModListDTO reqList = new TParaSvcModListDTO();
		List<TParaSvcModListDTO> list = new ArrayList<TParaSvcModListDTO>();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSVC_DESC(SVC_DESC);
		list.add(reqList);
		reqBody.setSVC_LIST(list);
		reqBody.setNUM(list.size());
		IDataset resDs = ctrlsvcService.SvcUpdate(reqBody,COMP_NO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "修改成功");
	}
	
	/**
	 *删除服务码action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSvcDel"})
	public void del(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		TParaSvcDelReqDTO reqBody = new TParaSvcDelReqDTO();
		TParaSvcDelListDTO reqList = new TParaSvcDelListDTO();
		List<TParaSvcDelListDTO> list = new ArrayList<TParaSvcDelListDTO>();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setSVC_CODE(SVC_CODE);
		list.add(reqList);
		reqBody.setSVC_LIST(list);
		reqBody.setNUM(list.size());
		
		IDataset resDs = ctrlsvcService.SvcDel(reqBody,COMP_NO);
		if(resDs != null){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除成功");
		}
	}
}
