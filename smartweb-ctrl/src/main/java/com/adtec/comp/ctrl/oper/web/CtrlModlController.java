package com.adtec.comp.ctrl.oper.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaCompAddListDTO;
import com.adtec.comp.ctrl.dto.TParaCompAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDelListDTO;
import com.adtec.comp.ctrl.dto.TParaCompDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompModListDTO;
import com.adtec.comp.ctrl.dto.TParaCompModReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.oper.service.CtrlModlService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper")
public class CtrlModlController extends BaseController{
	
	@Autowired
	private CtrlModlService ctrlModlService;
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/modl/ctrlModl";
	}
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlModlList"})
	public String switchesRuleList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/modl/ctrlModlList";
	}
	
	/**
	 *返回修改页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toUpdate"})
	public String addPage(HttpServletRequest request, HttpServletResponse response,String str,ModelMap map) throws UnsupportedEncodingException{
		str = URLDecoder.decode(str,"UTF-8");
		String modlNo = str.split(",")[0];
		String modlName = str.split(",")[1];
		String modlType = str.split(",")[2];
		map.addAttribute("modlNo", modlNo);
		map.addAttribute("modlName", modlName);
		map.addAttribute("modlType", modlType);
		System.out.println(modlNo+modlName+modlType);
		return "starring/comp/ctrl/modl/ctrlModlUpdate";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toAdd"})
	public String updatePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/modl/ctrlModlAddForm";
	}
	
	/**
	 *查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlCompQry"})
	public void CtrlModlQry (HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO"); 
		logger.info("COMP_NO="+COMP_NO);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TParaCompQryReqDTO reqBody = new TParaCompQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		
		IDataset resDs = ctrlModlService.CtrlModlQry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *模型组件新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlCompAdd"})
	public void CtrlModlAdd(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String FLG = reqDs.getString("FLG");
		TParaCompAddReqDTO reqBody = new TParaCompAddReqDTO();
		TParaCompAddListDTO reqList = new TParaCompAddListDTO();
		List<TParaCompAddListDTO> list = new ArrayList<TParaCompAddListDTO>();
		if(!DataUtil.isNullStr(COMP_NO) && !DataUtil.isNullStr(COMP_NAME) && !DataUtil.isNullStr(FLG)){
			reqList.setCOMP_NO(COMP_NO);
			reqList.setCOMP_NAME(COMP_NAME);
			reqList.setFLG(FLG);
		}
		list.add(reqList);
		reqBody.setCOMP_LIST(list);
		reqBody.setNUM(list.size());
		IDataset resDs = ctrlModlService.CtrlModlAdd(reqBody);
		if(resDs!=null){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增成功");
		}
	}
	
	/**
	 *模型组件修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"CtrlCompMod"})
	public void CtrlModlUpdate(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String FLG = reqDs.getString("FLG");
		TParaCompModListDTO reqList = new TParaCompModListDTO();
		List<TParaCompModListDTO> list = new ArrayList<TParaCompModListDTO>();
		TParaCompModReqDTO reqBody = new TParaCompModReqDTO();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setCOMP_NAME(COMP_NAME);
		reqList.setFLG(FLG);
		list.add(reqList);
		reqBody.setCOMP_LIST(list);
		reqBody.setNUM(list.size());
		IDataset resDs = ctrlModlService.CtrlModlUpdate(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *模型组件删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value={"CtrlCompDel"})
	public void CtrlModlDel(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		TParaCompDelReqDTO reqBody = new TParaCompDelReqDTO();
		TParaCompDelListDTO reqList = new TParaCompDelListDTO();
		List<TParaCompDelListDTO> list = new ArrayList<TParaCompDelListDTO>();
		if(!DataUtil.isNullStr(COMP_NO)){
			reqList.setCOMP_NO(COMP_NO);
		}
		list.add(reqList);
		reqBody.setCOMP_LIST(list);
		reqBody.setNUM(list.size());
		IDataset resDs = ctrlModlService.CtrlModlDel(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除成功");
	}
}
