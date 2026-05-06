package com.adtec.comp.ctrl.oper.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcAddListDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcDelListDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcModListDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcModReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryReqDTO;
import com.adtec.comp.ctrl.oper.service.CtrlSvcService;
import com.adtec.comp.ctrl.oper.service.SubSvcService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/subsvccode")
public class SubSvcController extends BaseController{
	
	@Autowired
	private SubSvcService subSvcService;
	@Autowired
	private CtrlSvcService ctrlSvcService;
	//发起方组件号
	private static String pub_compNo;
	public static String getPub_compNo() {
		return pub_compNo;
	}

	public static void setPub_compNo(String pub_compNo) {
		SubSvcController.pub_compNo = pub_compNo;
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response,String compNo) {
		SubSvcController.setPub_compNo(compNo);
		SubSvcController.pub_compNo = compNo;
		return "starring/comp/ctrl/subsvccode/ctrlSubSvc";
	}
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toList"})
	public String CtrlSvcList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/subsvccode/ctrlSubSvcList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toAdd"})
	public String CtrlSvcAdd(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/subsvccode/ctrlSubSvcAdd";
	}
	/**
	 *返回修改页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toUpdate"})
	public String CtrlSvcMod(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/subsvccode/ctrlSubSvcMod";
	}
	
	/**
	 * 获取模型号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getModlNo"})
	public void getModlNo(HttpServletRequest request,HttpServletResponse response){
		TParaCompQryReqDTO reqBody = new  TParaCompQryReqDTO();
		IDataset resDs = ctrlSvcService.getModlNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "获取模型号成功");
	}
	/**
	 * 获取服务码
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getSvcCode"})
	public void getSvcCode(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		TParaSvcQryReqDTO req = new TParaSvcQryReqDTO();
		req.setCOMP_NO(COMP_NO);
		IDataset res = ctrlSvcService.getSvcCode(req);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "获取服务码成功");
	}
	/**
	 * 获取子服务码
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getSubSvcCode"})
	public void getSubSvcCode(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		TParaSubSvcQryReqDTO req = new TParaSubSvcQryReqDTO();
		req.setCOMP_NO(COMP_NO);
		req.setSVC_CODE(SVC_CODE);
		IDataset res = subSvcService.getSubSvcCode(req);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "获取子服务码成功");
	}
	
	/**
	 * 查询子服务码
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSubSvcQry"})
	public void subSvcQry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TParaSubSvcQryReqDTO req = new TParaSubSvcQryReqDTO();
		req.setCOMP_NO(COMP_NO);
		req.setSUB_SVC_CODE(SUB_SVC_CODE);
		req.setSVC_CODE(SVC_CODE);
		
		IDataset resDs = subSvcService.subSvcList(req, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "查询成功");
	}
	
	/**
	 * 新增子服务码Action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSubSvcAdd"})
	public void subSvcAdd(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		String SUB_SVC_DESC = reqDs.getString("SUB_SVC_DESC");
		TParaSubSvcAddReqDTO req = new TParaSubSvcAddReqDTO();
		TParaSubSvcAddListDTO reqList = new TParaSubSvcAddListDTO();
		List<TParaSubSvcAddListDTO> list = new ArrayList<TParaSubSvcAddListDTO>();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSUB_SVC_CODE(SUB_SVC_CODE);
		reqList.setSUB_SVC_DESC(SUB_SVC_DESC);
		list.add(reqList);
		req.setSUB_SVC_LIST(list);
		req.setNUM(list.size());
		
		IDataset resDs = subSvcService.subSvcAdd(req,COMP_NO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "添加成功");
	}
	
	/**
	 * 修改子服务码Action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSubSvcMod"})
	public void subSvcMod(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		String SUB_SVC_DESC = reqDs.getString("SUB_SVC_DESC");
		TParaSubSvcModReqDTO  req = new TParaSubSvcModReqDTO();
		TParaSubSvcModListDTO reqList = new TParaSubSvcModListDTO();
		List<TParaSubSvcModListDTO> list = new ArrayList<TParaSubSvcModListDTO>();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSUB_SVC_CODE(SUB_SVC_CODE);
		reqList.setSUB_SVC_DESC(SUB_SVC_DESC);
		list.add(reqList);
		req.setSUB_SVC_LIST(list);
		req.setNUM(list.size());
		
		IDataset resDs = subSvcService.subSvcUpdate(req,COMP_NO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "修改成功");
	}
	
	/**
	 * 删除子服务码Action
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlSubSvcDel"})
	public void subSvcDel(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		TParaSubSvcDelReqDTO req = new TParaSubSvcDelReqDTO();
		TParaSubSvcDelListDTO reqList = new TParaSubSvcDelListDTO();
		List<TParaSubSvcDelListDTO> list = new ArrayList<TParaSubSvcDelListDTO>();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSUB_SVC_CODE(SUB_SVC_CODE);
		list.add(reqList);
		req.setSUB_SVC_LIST(list);
		req.setNUM(list.size());
		
		IDataset resDs = subSvcService.subSvcDel(req,COMP_NO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除成功");
	}

}
