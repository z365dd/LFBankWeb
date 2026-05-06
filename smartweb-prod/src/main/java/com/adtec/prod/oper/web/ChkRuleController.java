package com.adtec.prod.oper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.prod.oper.entity.TPipChkRuleDO;
import com.adtec.prod.oper.service.ChkRuleService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/chkRule")
public class ChkRuleController extends BaseController{
	@Autowired
	private ChkRuleService chkRulesService;
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "chkRuleList" })
	public String squareRulesList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/chkRuleList";
	}
	
	/**
	 * 返回修改页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "chkRuleForm" })
	public String squareRulesForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/chkRuleForm";
	}
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ruleId =  reqDs.getString("RULE_ID");
		TPipChkRuleDO DO  =new TPipChkRuleDO();
		DO.setRuleId(ruleId);
		//获取详情
		TPipChkRuleDO resBody = chkRulesService.getDetail(DO);
		String chkSndGrpFlg = resBody.getChkSndGrpFlg();
		String chkSndGrpFlgChg = "";
		if(chkSndGrpFlg !=null && !chkSndGrpFlg.isEmpty()) {//位图转换'|'分割
			if(chkSndGrpFlg.charAt(0)=='1') {
				chkSndGrpFlgChg = "1";
			}
			if(chkSndGrpFlg.charAt(1)=='1') {
				chkSndGrpFlgChg += ";2";
			}
			if(chkSndGrpFlg.charAt(2)=='1') {
				chkSndGrpFlgChg += ";3";
			}
			resBody.setChkSndGrpFlg(chkSndGrpFlgChg);
		}
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,TPipChkRuleDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 对账修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "update" })
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		IDataset resDs = DatasetService.getInstace().getDataset();
		TPipChkRuleDO DO = 	DatasetService.getInstace().getMBCObject(reqDs,TPipChkRuleDO.class);
		
		String chkSndGrpFlg = DO.getChkSndGrpFlg();
		String chkSndGrpFlgChg = "";
		if(chkSndGrpFlg !=null && !chkSndGrpFlg.isEmpty()) {//"|"转换为位图
			String[] chkSndGrpFlgArray = chkSndGrpFlg.split(";");
			List<String> list = Arrays.asList(chkSndGrpFlgArray);
			int padLen = 20;
			for(int i=0;i<padLen;i++) {
				if (list.contains(String.valueOf(i+1))) {
					chkSndGrpFlgChg +="1";
				} else {
					chkSndGrpFlgChg +="0";
				}
			}
			DO.setChkSndGrpFlg(chkSndGrpFlgChg);
		}
		int	rs = chkRulesService.update(DO);
		if(rs==0){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "修改失败");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
		}
	}
}
