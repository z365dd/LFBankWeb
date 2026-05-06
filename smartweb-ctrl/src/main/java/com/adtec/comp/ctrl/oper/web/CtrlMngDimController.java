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

import com.adtec.comp.ctrl.dto.FCtrlMngDimAddListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimDelListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimModListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryReqDTO;
import com.adtec.comp.ctrl.oper.service.CtrlMngDimService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/mngdim")
public class CtrlMngDimController extends BaseController{
	
	@Autowired
	private CtrlMngDimService ctrlMngDimService;
	//发起方组件号
	private static String pub_compNo;
	public static String getPub_compNo() {
		return pub_compNo;
	}

	public static void setPub_compNo(String pub_compNo) {
		CtrlMngDimController.pub_compNo = pub_compNo;
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response,String compNo) {
		CtrlMngDimController.setPub_compNo(compNo);
		CtrlMngDimController.pub_compNo = compNo;
		return "starring/comp/ctrl/mngdim/fCtrlMngDim";
	}
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toList"})
	public String toDimlist(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/mngdim/fCtrlMngDimList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toAdd"})
	public String toDimAdd(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/mngdim/fCtrlMngDimAdd";
	}
	
	/**
	 *返回修改页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toUpdate"})
	public String toDimMod(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/mngdim/fCtrlMngDimMod";
	}
	
	/**
	 * 查询维度
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"FCtrlMngDimQry"})
	public void qry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String DIM_KEY = reqDs.getString("DIM_KEY");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		FCtrlMngDimQryReqDTO req = new FCtrlMngDimQryReqDTO();
		req.setCOMP_NO(COMP_NO);
		req.setDIM_KEY(DIM_KEY);
		IDataset resDs = ctrlMngDimService.dimQry(req, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 新增维度
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"FCtrlMngDimAdd"})
	public void insert(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String DIM_KEY = reqDs.getString("DIM_KEY");
		String DIM_DESC = reqDs.getString("DIM_DESC");
		String TAB_NAME = reqDs.getString("TAB_NAME");
		/**
		 * 为维度添加前缀 如：  $STARRING_REQ[0].APP_HEAD.BRCH
		  *在应用头的(BRCH_NO、TLR_NO、)
		  *在系统头的(COMP_NO、SVC_CODE)
		  *在本地扩展头的(CHNL_NO、TNT_NO、ENTR_NO、BUSI_NO、LEGA_NO)
		  *其它的则统一加上前缀：$FCTRLTRANEXM_REQ[0].DYN_DATA
		 */
		if("BRCH_NO".equals(DIM_KEY) || "TLR_NO".equals(DIM_KEY)){
			DIM_KEY = "$STARRING_REQ[0].APP_HEAD."+DIM_KEY;
		}else if("CHNL_NO".equals(DIM_KEY) || "TNT_NO".equals(DIM_KEY) || "LEGA_NO".equals(DIM_KEY) 
				|| "ENTR_NO".equals(DIM_KEY) || "BUSI_NO".equals(DIM_KEY)){
			DIM_KEY = "$STARRING_REQ[0].LOCAL_HEAD."+DIM_KEY;
		}else if("COMP_NO".equals(DIM_KEY) || "SVC_CODE".equals(DIM_KEY)){
			DIM_KEY = "$STARRING_REQ[0].SYS_HEAD.REQ_"+DIM_KEY;
		}else if("SUB_SVC_CODE".equals(DIM_KEY)){
			DIM_KEY = "$FCTRLTRANEXM_REQ[0]."+DIM_KEY;
		}else{
			DIM_KEY = "$FCTRLTRANEXM_REQ[0].DYN_DATA."+DIM_KEY;
		}
		
		FCtrlMngDimAddListDTO reqList = new FCtrlMngDimAddListDTO();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setDIM_KEY(DIM_KEY);
		reqList.setDIM_DESC(DIM_DESC);
		reqList.setTAB_NAME(TAB_NAME);
		List<FCtrlMngDimAddListDTO> list = new ArrayList<FCtrlMngDimAddListDTO>();
		list.add(reqList);
		FCtrlMngDimAddReqDTO req = new FCtrlMngDimAddReqDTO();
		req.setDIM_LIST(list);
		req.setNUM(list.size());
		IDataset resDs = ctrlMngDimService.dimAdd(req,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "添加成功");
	}

	/**
	 * 修改维度
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"FCtrlMngDimMod"})
	public void update(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String DIM_KEY = reqDs.getString("DIM_KEY");
		String DIM_DESC = reqDs.getString("DIM_DESC");
		String TAB_NAME = reqDs.getString("TAB_NAME");
		FCtrlMngDimModListDTO reqList = new FCtrlMngDimModListDTO();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setDIM_KEY(DIM_KEY);
		reqList.setDIM_DESC(DIM_DESC);
		reqList.setTAB_NAME(TAB_NAME);
		List<FCtrlMngDimModListDTO> list = new ArrayList<FCtrlMngDimModListDTO>();
		list.add(reqList);
		FCtrlMngDimModReqDTO req = new FCtrlMngDimModReqDTO();
		req.setDIM_LIST(list);
		req.setNUM(list.size());
		IDataset resDs = ctrlMngDimService.dimMod(req,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "修改成功");
	}
	
	/**
	 * 删除维度
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"FCtrlMngDimDel"})
	public void del(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String DIM_KEY = reqDs.getString("DIM_KEY");
		String DIM_DESC = reqDs.getString("DIM_DESC");
		String TAB_NAME = reqDs.getString("TAB_NAME");
		FCtrlMngDimDelListDTO reqList = new FCtrlMngDimDelListDTO();
		reqList.setCOMP_NO(COMP_NO);
		reqList.setDIM_KEY(DIM_KEY);
		reqList.setDIM_DESC(DIM_DESC);
		reqList.setTAB_NAME(TAB_NAME);
		List<FCtrlMngDimDelListDTO> list = new ArrayList<FCtrlMngDimDelListDTO>();
		list.add(reqList);
		FCtrlMngDimDelReqDTO req = new FCtrlMngDimDelReqDTO();
		req.setDIM_LIST(list);
		req.setNUM(list.size());
		IDataset resDs = ctrlMngDimService.dimDel(req,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除成功");
	}
	
	
	/**
	 * 获取维度
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getDim"})
	public void getDim(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		FCtrlMngDimQryReqDTO reqBody = new  FCtrlMngDimQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		IDataset resDs = ctrlMngDimService.getDim(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "获取维度成功");
	}
}
