package com.adtec.prod.oper.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.prod.oper.dao.TPipClrMertAcctDao;
import com.adtec.prod.oper.entity.TPipClrMertAcctDO;
import com.adtec.prod.oper.entity.TPipClrMertRuleDO;
import com.adtec.prod.oper.service.ClrMertRuleService;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/clrMertRule")
public class ClrMertRuleController extends BaseController{
	@Autowired
	private ClrMertRuleService clrMertRuleService;
	@Autowired
	private TPipClrMertAcctDao clrMertAcctDao;
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "clrMertRuleList" })
	public String clrMertRuleList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/clrMertRuleList";
	}
	
	/**
	 * 返回修改页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "clrMertRuleForm" })
	public String clrMertRuleForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/clrMertRuleForm";
	}
	
	
	/**
	 * 配置修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "update" })
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		IDataset resDs = DatasetService.getInstace().getDataset();
//		TPipClrMertRuleDO reqTPipClrMertRuleDO = getData(reqDs);
		TPipClrMertRuleDO reqTPipClrMertRuleDO = DatasetService.getInstace().getObject(reqDs, TPipClrMertRuleDO.class);

		String clrSndGrpFlg = reqTPipClrMertRuleDO.getClrSndGrpFlg();
		String clrSndGrpFlgChg = "";
		if(clrSndGrpFlg !=null && !clrSndGrpFlg.isEmpty()) {//"|"转换为位图
			String[] clrSndGrpFlgArray = clrSndGrpFlg.split(";");
			List<String> list = Arrays.asList(clrSndGrpFlgArray);
			int padLen = 20;
			for(int i=0;i<padLen;i++) {
				if (list.contains(String.valueOf(i+1))) {
					clrSndGrpFlgChg +="1";
				} else {
					clrSndGrpFlgChg +="0";
				}
			}
			reqTPipClrMertRuleDO.setClrSndGrpFlg(clrSndGrpFlgChg);
		}
		//商户账号
		String acctList = reqDs.getString("acctList");
		List<TPipClrMertAcctDO> tpipClrMertAcctDOList = JSON.parseArray(acctList, TPipClrMertAcctDO.class);

		//更新
		int	rs = clrMertRuleService.update(reqTPipClrMertRuleDO,tpipClrMertAcctDOList);
		if(rs==0){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "修改失败");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
		}
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ruleId =  reqDs.getString("RULE_ID");
		TPipClrMertRuleDO reqTPipClrMertRuleDO  =new TPipClrMertRuleDO();
		reqTPipClrMertRuleDO.setRuleId(ruleId);
		
		//获取TPipClrMertRuleDO
		TPipClrMertRuleDO resTPipClrMertRuleDO = clrMertRuleService.getDetail(reqTPipClrMertRuleDO);
		String clrSndGrpFlg = resTPipClrMertRuleDO.getClrSndGrpFlg();
		String clrSndGrpFlgChg = "";
		if(clrSndGrpFlg !=null && !clrSndGrpFlg.isEmpty()) {//位图转换'|'分割
			if(clrSndGrpFlg.charAt(0)=='1') {
				clrSndGrpFlgChg = "1";
			}
			if(clrSndGrpFlg.charAt(1)=='1') {
				clrSndGrpFlgChg += ";2";
			}
			resTPipClrMertRuleDO.setClrSndGrpFlg(clrSndGrpFlgChg);
		}
		IDataset tpipClrMertRuleDOResDs = DatasetService.getInstace().getDataset(resTPipClrMertRuleDO,TPipClrMertRuleDO.class);
		tpipClrMertRuleDOResDs.setDatasetName("tpipClrMertRuleDOResDs");
		
		//获取TPipClrMertAcctDO
		TPipClrMertAcctDO reqTPipClrMertAcctDO = new TPipClrMertAcctDO();
		reqTPipClrMertAcctDO.setRuleId(ruleId);
		List<TPipClrMertAcctDO> resTPipClrMertAcctDOList = clrMertAcctDao.list(reqTPipClrMertAcctDO);

		IDataset tpipClrMertAcctDOResDs = DatasetService.getInstace().getDataset(resTPipClrMertAcctDOList,TPipClrMertAcctDO.class);
		tpipClrMertAcctDOResDs.setDatasetName("tpipClrMertAcctDOResDs");
		tpipClrMertAcctDOResDs.setTotalCount(resTPipClrMertAcctDOList.size());
				
		//赋值到IDatasets
		IDatasets resDss = new CommonDatasets();
		resDss.putDataset(tpipClrMertRuleDOResDs);
		resDss.putDataset(tpipClrMertAcctDOResDs);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "交易成功");
	}
	
	public TPipClrMertRuleDO getData(IDataset ds){
		TPipClrMertRuleDO DO = new TPipClrMertRuleDO();
		
		DO.setRuleId(ds.getString("ruleId"));
		DO.setRuleName(ds.getString("ruleName"));
		DO.setClrMeth(ds.getString("clrMeth"));
		DO.setClrDimTp(ds.getString("clrDimTp"));
		DO.setBatProcFlg(ds.getString("batProcFlg"));
		DO.setClrSndGrpFlg(ds.getString("clrSndGrpFlg"));
		DO.setClrCycTp(ds.getString("clrCycTp"));
		DO.setClrCyc(ds.getInt("clrCyc"));
		DO.setStrTime(ds.getString("strTime"));
		DO.setEndTime(ds.getString("endTime"));
		DO.setBean(ds.getString("bean"));
		DO.setAutoClrChnlNo(ds.getString("autoClrChnlNo"));
		return DO;
	}
	
}
