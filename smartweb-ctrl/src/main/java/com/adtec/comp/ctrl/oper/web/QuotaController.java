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

import com.adtec.comp.ctrl.dto.CtrlLimitParaAddReqDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaDelReqDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaEditReqDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaQryReqDTO;
import com.adtec.comp.ctrl.oper.service.QuotaService;
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
@RequestMapping(value="${adminPath}/comp/ctrl/oper/quota")
public class QuotaController extends BaseController{
	@Autowired
	private QuotaService quotaService;
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"quotaRuleList"})
	public String quotaRuleList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/quota/quotaRuleList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"quotaRuleForm"})
	public String quotaRuleForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/quota/quotaRuleForm";
	}
	
	
	
	/**
	 *限额规则新增
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
		
		String AMT_LMT_DAY = reqDs.getString("AMT_LMT_DAY");
		String AMT_LMT_MONTH = reqDs.getString("AMT_LMT_MONTH");
		String AMT_LMT_SEASON = reqDs.getString("AMT_LMT_SEASON");
		String AMT_LMT_YEAR = reqDs.getString("AMT_LMT_YEAR");
		String AMT_LMT_SIGL = reqDs.getString("AMT_LMT_SIGL");
		
		String CNT_LMT_DAY = reqDs.getString("CNT_LMT_DAY");
		String CNT_LMT_MONTH = reqDs.getString("CNT_LMT_MONTH");
		String CNT_LMT_SEASON = reqDs.getString("CNT_LMT_SEASON");
		String CNT_LMT_YEAR = reqDs.getString("CNT_LMT_YEAR");

		CtrlLimitParaAddReqDTO reqBody = new  CtrlLimitParaAddReqDTO();
		reqBody.setMODL_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC(SUB_SVC);
		reqBody.setRULE_EXP(RULE_EXP);
		reqBody.setEXP_DESC(EXP_DESC);
		
		reqBody.setAMT_LMT_DAY(AMT_LMT_DAY);
		reqBody.setAMT_LMT_MONTH(AMT_LMT_MONTH);
		reqBody.setAMT_LMT_SEASON(AMT_LMT_SEASON);
		reqBody.setAMT_LMT_YEAR(AMT_LMT_YEAR);
		reqBody.setAMT_LMT_SIGL(AMT_LMT_SIGL);
		
		reqBody.setCNT_LMT_DAY(CNT_LMT_DAY);
		reqBody.setCNT_LMT_MONTH(CNT_LMT_MONTH);
		reqBody.setCNT_LMT_SEASON(CNT_LMT_SEASON);
		reqBody.setCNT_LMT_YEAR(CNT_LMT_YEAR);
		
		
		IDataset resDs = quotaService.save(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *限额规则查询
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
		CtrlLimitParaQryReqDTO reqBody = new  CtrlLimitParaQryReqDTO();
		reqBody.setMODL_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC(SUB_SVC);
		
		
		
		IDataset resDs = quotaService.qry(reqBody,start,limit);
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
		String SER_NO = reqDs.getString("SER_NO");
		String RULE_EXP = reqDs.getString("RULE_EXP");
		CtrlLimitParaDelReqDTO reqBody = new  CtrlLimitParaDelReqDTO();
		reqBody.setMODL_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC(SUB_SVC);
		reqBody.setSER_NO(SER_NO);
		reqBody.setRULE_EXP(RULE_EXP);
		
		IDataset resDs = quotaService.Delete(reqBody);
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
		String MODL_NO = reqDs.getString("MODL_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC = reqDs.getString("SUB_SVC");
		String RULE_EXP = reqDs.getString("RULE_EXP");
		String EXP_DESC = reqDs.getString("EXP_DESC");
		
		String AMT_LMT_DAY = reqDs.getString("AMT_LMT_DAY");
		String AMT_LMT_MONTH = reqDs.getString("AMT_LMT_MONTH");
		String AMT_LMT_SEASON = reqDs.getString("AMT_LMT_SEASON");
		String AMT_LMT_YEAR = reqDs.getString("AMT_LMT_YEAR");
		String AMT_LMT_SIGL = reqDs.getString("AMT_LMT_SIGL");
		
		String CNT_LMT_DAY = reqDs.getString("CNT_LMT_DAY");
		String CNT_LMT_MONTH = reqDs.getString("CNT_LMT_MONTH");
		String CNT_LMT_SEASON = reqDs.getString("CNT_LMT_SEASON");
		String CNT_LMT_YEAR = reqDs.getString("CNT_LMT_YEAR");
		
		//执行序号全局变量
		String EXCT_SER = reqDs.getString("EXCT_SER");
		//规则表达式序号全局变量
		String SER_NO = reqDs.getString("SER_NO");

		CtrlLimitParaEditReqDTO reqBody = new  CtrlLimitParaEditReqDTO();
		reqBody.setMODL_NO(MODL_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC(SUB_SVC);
		reqBody.setRULE_EXP(RULE_EXP);
		reqBody.setEXP_DESC(EXP_DESC);
		
		reqBody.setAMT_LMT_DAY(AMT_LMT_DAY);
		reqBody.setAMT_LMT_MONTH(AMT_LMT_MONTH);
		reqBody.setAMT_LMT_SEASON(AMT_LMT_SEASON);
		reqBody.setAMT_LMT_YEAR(AMT_LMT_YEAR);
		reqBody.setAMT_LMT_SIGL(AMT_LMT_SIGL);
		
		reqBody.setCNT_LMT_DAY(CNT_LMT_DAY);
		reqBody.setCNT_LMT_MONTH(CNT_LMT_MONTH);
		reqBody.setCNT_LMT_SEASON(CNT_LMT_SEASON);
		reqBody.setCNT_LMT_YEAR(CNT_LMT_YEAR);
		
		reqBody.setEXCT_SER(EXCT_SER);
		reqBody.setSER_NO(SER_NO);
		
		IDataset resDs = quotaService.revice(reqBody);
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
		
		String QUOTA_ARR = reqDs.getString("QUOTA_ARR");
		JSONArray jsonArray = JSON.parseArray(QUOTA_ARR);
		/*jsonArray.getJSONObject(0).getString("RULE_EXP");*/
		
		/*List<CtrlLimitParaEditReqDTO> list  = new ArrayList<CtrlLimitParaEditReqDTO>();
		for(int a=0;a<jsonArray.size();a++){
			CtrlLimitParaEditReqDTO reqList = new  CtrlLimitParaEditReqDTO();
			Map reqMap = jsonArray.getJSONObject(a);
			reqList.setMODL_NO((String)reqMap.get("MODL_NO"));
			reqList.setSVC_CODE((String)reqMap.get("SVC_CODE"));
			reqList.setSUB_SVC((String)reqMap.get("SUB_SVC"));
			reqList.setEXCT_SER((String)reqMap.get("EXCT_SER"));
			reqList.setSER_NO((String)reqMap.get("SER_NO"));
			reqList.setRULE_EXP((String)reqMap.get("RULE_EXP"));
			list.add(reqList);
		}*/
		/*CtrlSwitchParaEditReqDTO reqBody = new  CtrlSwitchParaEditReqDTO();
		reqBody.setSWITCH_LIST(list);*/
		
		CtrlLimitParaEditReqDTO reqBody = new  CtrlLimitParaEditReqDTO();
		Map reqMap = jsonArray.getJSONObject(0);
		reqBody.setMODL_NO((String)reqMap.get("MODL_NO"));
		reqBody.setSVC_CODE((String)reqMap.get("SVC_CODE"));
		reqBody.setSUB_SVC((String)reqMap.get("SUB_SVC"));
		reqBody.setEXCT_SER((String)reqMap.get("EXCT_SER"));
		reqBody.setSER_NO((String)reqMap.get("SER_NO"));
		reqBody.setRULE_EXP((String)reqMap.get("RULE_EXP"));
		
		IDataset resDs = quotaService.revice(reqBody);
		
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
