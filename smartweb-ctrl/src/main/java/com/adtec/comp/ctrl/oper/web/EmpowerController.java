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

import com.adtec.comp.ctrl.dto.FCtrlMngAuthAddList1DTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthAddListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthDelListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthModList1DTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthModListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthQryReqDTO;
import com.adtec.comp.ctrl.oper.service.EmpowerService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/empower")
public class EmpowerController extends BaseController {
	@Autowired
	private EmpowerService empowerService;
	//发起方组件号
	private static String pub_compNo;
	public static String getPub_compNo() {
		return pub_compNo;
	}

	public static void setPub_compNo(String pub_compNo) {
		EmpowerController.pub_compNo = pub_compNo;
	}

	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"empowerRuleList"})
	public String empowerRuleList(HttpServletRequest request, HttpServletResponse response,String compNo) {
		EmpowerController.setPub_compNo(compNo);
		return "starring/comp/ctrl/empower/empowerRuleList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"empowerRuleForm"})
	public String empowerRuleForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/empower/empowerRuleForm";
	}
	
	
	/**
	 *授权规则新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"save"})
	public void save (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		//获取执行号
		FCtrlMngAuthQryReqDTO qryBody = new  FCtrlMngAuthQryReqDTO();
		qryBody.setCOMP_NO(MODL_NO);
		qryBody.setSVC_CODE(SVC_CODE);
		qryBody.setSUB_SVC_CODE(SUB_SVC);
		long maxSer = empowerService.qrySer(qryBody);
		
		String RULE_EXP = reqDs.getString("RULE_EXP");
		String EXP_DESC = reqDs.getString("EXP_DESC");
		String EXPR_DESC = reqDs.getString("EXPR_DESC");
		
		String AUTH_MODE = reqDs.getString("AUTH_MODE");
		
		String AUTH_AMT_LIST_NUM = reqDs.getString("AUTH_AMT_LIST_NUM");
		String AUTH_AMT_ARR = reqDs.getString("AUTH_AMT_ARR");
		String AUTH_TLR_LVL = reqDs.getString("AUTH_TLR_LVL");
		String AUTH_TLR_NUM = reqDs.getString("AUTH_TLR_NUM");
		
		
		FCtrlMngAuthAddListDTO reqList = new  FCtrlMngAuthAddListDTO();
		reqList.setCOMP_NO(MODL_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSUB_SVC_CODE(SUB_SVC);
		reqList.setEXPR(RULE_EXP);
		reqList.setTRANL_EXPR(EXP_DESC);
		reqList.setEXPR_DESC(EXPR_DESC);
		reqList.setEXEC_SER(maxSer+1);
		
		reqList.setAUTH_METH(AUTH_MODE);
		
		reqList.setTP_NUM(Long.parseLong(AUTH_AMT_LIST_NUM));
		if("01".equals(AUTH_MODE)){
			/*有金额授权*/
			List<FCtrlMngAuthAddList1DTO> AUTH_AMT_LIST = new ArrayList<FCtrlMngAuthAddList1DTO>();
			/*前台json数组字符串转为list map*/
			JSONArray jsonArray = JSON.parseArray(AUTH_AMT_ARR);
			for(int a=0;a<jsonArray.size();a++){
				Map reqMap = jsonArray.getJSONObject(a);
				/*AUTH_LIST 内部list*/
				FCtrlMngAuthAddList1DTO reqList1 = new  FCtrlMngAuthAddList1DTO();
				String minAmt = (String) reqMap.get("MIN_AMT");
				reqList1.setMIN_AMT(Double.valueOf(minAmt));
				String maxAmt = (String) reqMap.get("MAX_AMT");
				reqList1.setMAX_AMT(Double.valueOf(maxAmt));
				reqList1.setAUTH_TLR_LVL((String)reqMap.get("AUTH_TLR_LVL"));
				String tlrNum = (String) reqMap.get("AUTH_TLR_NUM");
				reqList1.setAUTH_TLR_NUM(Integer.valueOf(tlrNum));
				reqList1.setSER(a+1);
				AUTH_AMT_LIST.add(reqList1);
			}
			reqList.setAUTH_AMT_LIST(AUTH_AMT_LIST);
			
		}else if("02".equals(AUTH_MODE)){
			/*无金额授权*/
			List<FCtrlMngAuthAddList1DTO> AUTH_AMT_LIST = new ArrayList<FCtrlMngAuthAddList1DTO>();
			FCtrlMngAuthAddList1DTO reqList1 = new  FCtrlMngAuthAddList1DTO();
			reqList1.setAUTH_TLR_LVL(AUTH_TLR_LVL);
			reqList1.setAUTH_TLR_NUM(Integer.parseInt(AUTH_TLR_NUM));
			AUTH_AMT_LIST.add(reqList1);
			reqList.setAUTH_AMT_LIST(AUTH_AMT_LIST);	
		}
		List<FCtrlMngAuthAddListDTO> AUTH_LIST = new ArrayList<FCtrlMngAuthAddListDTO>();
		AUTH_LIST.add(reqList);
		
		FCtrlMngAuthAddReqDTO reqBody = new  FCtrlMngAuthAddReqDTO();
		reqBody.setNUM(AUTH_LIST.size());
		reqBody.setAUTH_LIST(AUTH_LIST);
		
		IDataset resDs = empowerService.save(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *授权规则查询
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
		FCtrlMngAuthQryReqDTO reqBody = new  FCtrlMngAuthQryReqDTO();
		reqBody.setCOMP_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC);
		
		IDataset resDs = empowerService.qry(reqBody,start,limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *授权规则删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"Delete"})
	public void Delete (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String EXCT_SER_NO = reqDs.getString("EXCT_SER_NO");
		
		FCtrlMngAuthDelListDTO reqBodyList = new  FCtrlMngAuthDelListDTO();
		reqBodyList.setCOMP_NO(MODL_NO);
		reqBodyList.setSVC_CODE(SVC_CODE);
		reqBodyList.setSUB_SVC_CODE(SUB_SVC);
		reqBodyList.setEXEC_SER(Long.parseLong(EXCT_SER_NO));
		
		List<FCtrlMngAuthDelListDTO> AUTH_LIST = new ArrayList<FCtrlMngAuthDelListDTO>();
		AUTH_LIST.add(reqBodyList);
		
		FCtrlMngAuthDelReqDTO reqBody = new  FCtrlMngAuthDelReqDTO();
		reqBody.setNUM(1);
		reqBody.setAUTH_LIST(AUTH_LIST);
		
		IDataset resDs = empowerService.Delete(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *授权规则修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"revice"})
	public void revice (HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FLG = reqDs.getString("FLG");
		
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String RULE_EXP = reqDs.getString("RULE_EXP");
		String EXP_DESC = reqDs.getString("EXP_DESC");
		String EXPR_DESC = reqDs.getString("EXPR_DESC");
		
		String AUTH_MODE = reqDs.getString("AUTH_MODE");
		
		String AUTH_AMT_LIST_NUM = reqDs.getString("AUTH_AMT_LIST_NUM");
		String AUTH_AMT_ARR = reqDs.getString("AUTH_AMT_ARR");
		String AUTH_TLR_LVL = reqDs.getString("AUTH_TLR_LVL");
		String AUTH_TLR_NUM = reqDs.getString("AUTH_TLR_NUM");
		
		long EXCT_SER = reqDs.getLong("EXCT_SER");
			
		FCtrlMngAuthModListDTO reqList = new  FCtrlMngAuthModListDTO();
		reqList.setCOMP_NO(MODL_NO);
		reqList.setSVC_CODE(SVC_CODE);
		reqList.setSUB_SVC_CODE(SUB_SVC);
		reqList.setEXPR(RULE_EXP);
		reqList.setTRANL_EXPR(EXP_DESC);
		reqList.setEXPR_DESC(EXPR_DESC);
		
		reqList.setAUTH_METH(AUTH_MODE);
		
		reqList.setEXEC_SER(EXCT_SER);
		
		reqList.setTP_NUM(Long.parseLong(AUTH_AMT_LIST_NUM));
		if("01".equals(AUTH_MODE)){
			/*有金额授权*/
			List<FCtrlMngAuthModList1DTO> AUTH_AMT_LIST = new ArrayList<FCtrlMngAuthModList1DTO>();
			/*前台json数组字符串转为list map*/
			JSONArray jsonArray = JSON.parseArray(AUTH_AMT_ARR);
			for(int a=0;a<jsonArray.size();a++){
				JSONObject jo=jsonArray.getJSONObject(a);
				/*AUTH_LIST 内部list*/
				FCtrlMngAuthModList1DTO reqList1 = new  FCtrlMngAuthModList1DTO();
				reqList1.setSER(a+1);
				reqList1.setMIN_AMT(jo.getDouble("MIN_AMT"));
				reqList1.setMAX_AMT(jo.getDouble("MAX_AMT"));
				reqList1.setAUTH_TLR_NUM(jo.getLong("AUTH_TLR_NUM"));
				reqList1.setAUTH_TLR_LVL(jo.getString("AUTH_TLR_LVL"));
				
				AUTH_AMT_LIST.add(reqList1);
			}
			reqList.setAUTH_AMT_LIST(AUTH_AMT_LIST);	
		}else if("02".equals(AUTH_MODE)){
			/*无金额授权*/
			List<FCtrlMngAuthModList1DTO> AUTH_AMT_LIST = new ArrayList<FCtrlMngAuthModList1DTO>();
			FCtrlMngAuthModList1DTO reqList1 = new  FCtrlMngAuthModList1DTO();
			reqList1.setAUTH_TLR_LVL(AUTH_TLR_LVL);
			reqList1.setAUTH_TLR_NUM(Integer.parseInt(AUTH_TLR_NUM));
			AUTH_AMT_LIST.add(reqList1);
			reqList.setAUTH_AMT_LIST(AUTH_AMT_LIST);	
		}
		
		List<FCtrlMngAuthModListDTO> AUTH_LIST = new ArrayList<FCtrlMngAuthModListDTO>();
		AUTH_LIST.add(reqList);
		FCtrlMngAuthModReqDTO reqBody = new  FCtrlMngAuthModReqDTO();
		reqBody.setNUM(1);
		reqBody.setAUTH_LIST(AUTH_LIST);
		
		reqBody.setFLG(FLG);
		
		IDataset resDs = empowerService.revice(reqBody,pub_compNo);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *授权规则排序修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"saveSort"})
	public void saveSort (HttpServletRequest request, HttpServletResponse response) {
		//TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FLG = reqDs.getString("FLG");
		
		
		String EMPOWER_ARR = reqDs.getString("EMPOWER_ARR");
		JSONArray jsonArray = JSON.parseArray(EMPOWER_ARR);
		
		List<FCtrlMngAuthModListDTO> AUTH_LIST  = new ArrayList<FCtrlMngAuthModListDTO>();
		
		for(int a=0;a<jsonArray.size();a++){
			FCtrlMngAuthModListDTO reqList = new FCtrlMngAuthModListDTO();
			Map reqMap = jsonArray.getJSONObject(a);
			reqList.setCOMP_NO((String)reqMap.get("COMP_NO"));
			reqList.setSVC_CODE((String)reqMap.get("SVC_CODE"));
			reqList.setSUB_SVC_CODE((String)reqMap.get("SUB_SVC_CODE"));
			reqList.setEXPR((String)reqMap.get("EXPR"));
			reqList.setTRANL_EXPR((String)reqMap.get("TRANL_EXPR"));
			reqList.setEXPR_DESC((String)reqMap.get("EXPR_DESC"));
			if("有金额授权".equals((String)reqMap.get("AUTH_METH"))){
				reqList.setAUTH_METH("01");
			}else if("无金额授权".equals((String)reqMap.get("AUTH_METH"))){
				reqList.setAUTH_METH("02");
			}
			reqList.setEXEC_SER(a+1);
			
			String AUTH_AMT_LIST_STR = (String)reqMap.get("AUTH_AMT_LIST_STR");
			JSONArray authJsonArray = JSON.parseArray(AUTH_AMT_LIST_STR);
			reqList.setTP_NUM(authJsonArray.size());
			
			List<FCtrlMngAuthModList1DTO> AUTH_AMT_LIST = new ArrayList<FCtrlMngAuthModList1DTO>();
			for(int b=0;b<authJsonArray.size();b++){
				JSONObject authJsonMap = authJsonArray.getJSONObject(b);
				/*AUTH_LIST 内部list*/
				FCtrlMngAuthModList1DTO reqList1 = new  FCtrlMngAuthModList1DTO();
				reqList1.setSER(b+1);
				reqList1.setMIN_AMT(authJsonMap.getDouble("MIN_AMT"));
				reqList1.setMAX_AMT(authJsonMap.getDouble("MAX_AMT"));
				reqList1.setAUTH_TLR_NUM(authJsonMap.getLong("AUTH_TLR_NUM"));
				reqList1.setAUTH_TLR_LVL(authJsonMap.getString("AUTH_TLR_LVL"));
				AUTH_AMT_LIST.add(reqList1);
			}
			reqList.setAUTH_AMT_LIST(AUTH_AMT_LIST);
			
			AUTH_LIST.add(reqList);
		}
		
		FCtrlMngAuthModReqDTO reqBody = new  FCtrlMngAuthModReqDTO();
		reqBody.setAUTH_LIST(AUTH_LIST);
		reqBody.setNUM(AUTH_LIST.size());
		
		reqBody.setFLG(FLG);
		
		IDataset resDs = empowerService.revice(reqBody,pub_compNo);
		
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
}
