/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: SwitchesController.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年1月2日 下午7:29:38<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.ctrl.oper.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.FCtrlMngDimVauleQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngRuleQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngOprQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchAddListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchDelListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchModLsitDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchQryReqDTO;
import com.adtec.comp.ctrl.oper.service.SwitchesService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


/**
 * @author 11093
 *
 */
@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/switches")
public class SwitchesController extends BaseController{
	@Autowired
	private SwitchesService switchesService;
	//发起方组件号
	private static String pub_compNo;
	
	public static String getPub_compNo() {
		return pub_compNo;
	}

	public static void setPub_compNo(String pub_compNo) {
		SwitchesController.pub_compNo = pub_compNo;
	}

	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"switchesRuleList"})
	public String switchesRuleList(HttpServletRequest request, HttpServletResponse response,String compNo) {
		SwitchesController.setPub_compNo(compNo);
		return "starring/comp/ctrl/switches/switchesRuleList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"switchesRuleForm"})
	public String switchesRuleForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/switches/switchesRuleForm";
	}
	
	/**
	 *返回规则定义公共页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ruleCommon"})
	public String ruleCommon(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/switches/ruleCommon";
	}
	
	/**
	 *获取模型号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"modelNoS"})
	public void modelNoS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		TParaCompQryReqDTO reqBody = new  TParaCompQryReqDTO();
		IDataset resDs = switchesService.modelNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *获取服务码
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"svcCodeS"})
	public void svcCodeS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		TParaSvcQryReqDTO reqBody = new  TParaSvcQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		
		IDataset resDs = switchesService.svcCode(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *获取子服务码
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"subSvcS"})
	public void subSvcS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		TParaSubSvcQryReqDTO reqBody = new  TParaSubSvcQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		
		IDataset resDs = switchesService.subSvc(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	/**
	 *获取规则
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"expNameS"})
	public void expNameS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		FCtrlMngRuleQryReqDTO reqBody = new  FCtrlMngRuleQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		
		IDataset resDs = switchesService.expName(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *获取维度
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"dimNoS"})
	public void dimNoS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		FCtrlMngDimQryReqDTO reqBody = new  FCtrlMngDimQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		
		IDataset resDs = switchesService.dimNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *获取预算符
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"oprNoS"})
	public void oprNoS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		FCtrlMngOprQryReqDTO reqBody = new  FCtrlMngOprQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		
		IDataset resDs = switchesService.oprNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *获取维度的值
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"dimValS"})
	public void dimValS(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String DIM_KEY = reqDs.getString("DIM_KEY");
		FCtrlMngDimVauleQryReqDTO reqBody = new  FCtrlMngDimVauleQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setDIM_KEY(DIM_KEY);
		IDataset resDs = switchesService.dimVal(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *开关规则新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"save"})
	public void save (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String RULE_EXP = reqDs.getString("RULE_EXP");
		String EXP_DESC = reqDs.getString("EXP_DESC");
		String EXPR_DESC = reqDs.getString("EXPR_DESC");
		
		FCtrlMngSwitchQryReqDTO qryBody = new  FCtrlMngSwitchQryReqDTO();
		qryBody.setCOMP_NO(MODL_NO);
		qryBody.setSVC_CODE(SVC_CODE);
		qryBody.setSUB_SVC_CODE(SUB_SVC);
		long maxSer = switchesService.qrySer(qryBody);
		
		FCtrlMngSwitchAddListDTO reqBodyList = new  FCtrlMngSwitchAddListDTO();
		reqBodyList.setCOMP_NO(MODL_NO);
		reqBodyList.setSVC_CODE(SVC_CODE);
		reqBodyList.setSUB_SVC_CODE(SUB_SVC);
		reqBodyList.setEXPR(RULE_EXP);
		reqBodyList.setTRANL_EXPR(EXP_DESC);
		reqBodyList.setEXPR_DESC(EXPR_DESC);
		reqBodyList.setEXEC_SER(maxSer+1);
		
		List<FCtrlMngSwitchAddListDTO> list=new ArrayList<FCtrlMngSwitchAddListDTO>();
		list.add(reqBodyList);
		FCtrlMngSwitchAddReqDTO reqBody = new  FCtrlMngSwitchAddReqDTO();
		reqBody.setNUM(1);
		reqBody.setSWITCH_LIST(list);
		
		IDataset resDs = switchesService.save(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *开关规则查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qry"})
	public void qry (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		FCtrlMngSwitchQryReqDTO reqBody = new  FCtrlMngSwitchQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC);
		
		IDataset resDs = switchesService.qry(reqBody,start,limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *开关规则删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"Delete"})
	public void Delete (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String EXEC_SER = reqDs.getString("EXEC_SER");
		FCtrlMngSwitchDelListDTO reqBodyList = new  FCtrlMngSwitchDelListDTO();
		reqBodyList.setCOMP_NO(MODL_NO);
		reqBodyList.setSVC_CODE(SVC_CODE);
		reqBodyList.setSUB_SVC_CODE(SUB_SVC);
		reqBodyList.setEXEC_SER(Long.parseLong(EXEC_SER));
		
		List<FCtrlMngSwitchDelListDTO> list = new ArrayList<FCtrlMngSwitchDelListDTO>();
		list.add(reqBodyList);
		FCtrlMngSwitchDelReqDTO reqBody= new  FCtrlMngSwitchDelReqDTO();
		reqBody.setNUM(list.size());
		reqBody.setSWITCH_LIST(list);
		
		IDataset resDs = switchesService.Delete(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *开关规则修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"revice"})
	public void revice (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FLG = reqDs.getString("FLG");
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String RULE_EXP = reqDs.getString("RULE_EXP");
		String EXP_DESC = reqDs.getString("EXP_DESC");
		String EXPR_DESC = reqDs.getString("EXPR_DESC");
		String EXEC_SER = reqDs.getString("EXCT_SER");
		FCtrlMngSwitchModLsitDTO reqBodyList = new  FCtrlMngSwitchModLsitDTO();
		reqBodyList.setCOMP_NO(MODL_NO);
		reqBodyList.setSVC_CODE(SVC_CODE);
		reqBodyList.setSUB_SVC_CODE(SUB_SVC);
		reqBodyList.setEXPR(RULE_EXP);
		reqBodyList.setTRANL_EXPR(EXP_DESC);
		reqBodyList.setEXPR_DESC(EXPR_DESC);
		reqBodyList.setEXEC_SER(Integer.parseInt(EXEC_SER));
		
		List<FCtrlMngSwitchModLsitDTO> list=new ArrayList<FCtrlMngSwitchModLsitDTO>();
		list.add(reqBodyList);
		FCtrlMngSwitchModReqDTO reqBody = new  FCtrlMngSwitchModReqDTO();
		reqBody.setNUM(list.size());
		reqBody.setSWITCH_LIST(list);
		
		reqBody.setFLG(FLG);
		
		IDataset resDs = switchesService.revice(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *开关规则排序修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"saveSort"})
	public void saveSort (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FLG = reqDs.getString("FLG");
		String SWITCH = reqDs.getString("SWITCH");
		JSONArray jsonArray = JSON.parseArray(SWITCH);
		/*jsonArray.getJSONObject(0).getString("RULE_EXP");*/
		
		List<FCtrlMngSwitchModLsitDTO> list  = new ArrayList<FCtrlMngSwitchModLsitDTO>();
		
		
		for(int a=0;a<jsonArray.size();a++){
			FCtrlMngSwitchModLsitDTO reqList = new  FCtrlMngSwitchModLsitDTO();
			Map reqMap = jsonArray.getJSONObject(a);
			reqList.setCOMP_NO((String)reqMap.get("COMP_NO"));
			reqList.setSVC_CODE((String)reqMap.get("SVC_CODE"));
			reqList.setSUB_SVC_CODE((String)reqMap.get("SUB_SVC_CODE"));
			reqList.setEXPR((String)reqMap.get("EXPR"));
			reqList.setTRANL_EXPR((String)reqMap.get("TRAN_EXPR"));
			reqList.setEXPR_DESC((String)reqMap.get("EXPR_DESC"));
			reqList.setEXEC_SER(a+1);
			list.add(reqList);
		}
		
		FCtrlMngSwitchModReqDTO reqBody = new  FCtrlMngSwitchModReqDTO();
		reqBody.setSWITCH_LIST(list);
		reqBody.setNUM(list.size());
		
		reqBody.setFLG(FLG);
		
		IDataset resDs = switchesService.revice(reqBody,pub_compNo);
		
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
