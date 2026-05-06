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
import com.adtec.prod.oper.dao.TPipClrMertFeeCaltDao;
import com.adtec.prod.oper.entity.TPipClrMertAcctDO;
import com.adtec.prod.oper.entity.TPipClrMertFeeCaltDO;
import com.adtec.prod.oper.entity.TPipClrMertFeeRuleDO;
import com.adtec.prod.oper.service.ClrMertFeeRuleService;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/clrMertFeeRule")
public class ClrMertFeeRuleController extends BaseController{
	@Autowired
	private ClrMertFeeRuleService clrMertFeeRuleService;
	@Autowired
	private TPipClrMertAcctDao clrMertAcctDao;
	@Autowired
	private TPipClrMertFeeCaltDao clrMertFeeCaltDao;
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "clrMertFeeRuleList" })
	public String clrMertFeeRuleList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/clrMertFeeRuleList";
	}
	
	/**
	 * 返回修改页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "clrMertFeeRuleForm" })
	public String clrMertFeeRuleForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/clrMertFeeRuleForm";
	}
	
	
	/**
	 * 配置修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "update" })
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		IDataset resDs = DatasetService.getInstace().getDataset();
//		TPipClrMertFeeRuleDO reqTPipClrMertFeeRuleDO = getData(reqDs);
		TPipClrMertFeeRuleDO reqTPipClrMertFeeRuleDO = DatasetService.getInstace().getObject(reqDs, TPipClrMertFeeRuleDO.class);

		String clrSndGrpFlg = reqTPipClrMertFeeRuleDO.getClrSndGrpFlg();
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
			reqTPipClrMertFeeRuleDO.setClrSndGrpFlg(clrSndGrpFlgChg);
		}
		//商户账号
		String acctList = reqDs.getString("acctList");
		List<TPipClrMertAcctDO> tpipClrMertAcctDOList = JSON.parseArray(acctList, TPipClrMertAcctDO.class);
		
		//计算表
		TPipClrMertFeeCaltDO reqTPipClrMertFeeCaltDO = DatasetService.getInstace().getObject(reqDs, TPipClrMertFeeCaltDO.class);


		//更新
		int	rs = clrMertFeeRuleService.update(reqTPipClrMertFeeRuleDO,tpipClrMertAcctDOList,reqTPipClrMertFeeCaltDO);
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
		TPipClrMertFeeRuleDO reqTPipClrMertFeeRuleDO  =new TPipClrMertFeeRuleDO();
		reqTPipClrMertFeeRuleDO.setRuleId(ruleId);

		//赋值到IDatasets
		IDatasets resDss = new CommonDatasets();

		//获取TPipClrMertFeeRuleDO
		TPipClrMertFeeRuleDO resTPipClrMertFeeRuleDO = clrMertFeeRuleService.getDetail(reqTPipClrMertFeeRuleDO);
		if (null != resTPipClrMertFeeRuleDO) {
			String clrSndGrpFlg = resTPipClrMertFeeRuleDO.getClrSndGrpFlg();
			String clrSndGrpFlgChg = "";
			if (clrSndGrpFlg != null && !clrSndGrpFlg.isEmpty()) {//位图转换'|'分割
				if (clrSndGrpFlg.charAt(0) == '1') {
					clrSndGrpFlgChg = "1";
				}
				if (clrSndGrpFlg.charAt(1) == '1') {
					clrSndGrpFlgChg += ";2";
				}
				resTPipClrMertFeeRuleDO.setClrSndGrpFlg(clrSndGrpFlgChg);
			}
			IDataset tpipClrMertFeeRuleDOResDs = DatasetService.getInstace().getDataset(resTPipClrMertFeeRuleDO, TPipClrMertFeeRuleDO.class);
			tpipClrMertFeeRuleDOResDs.setDatasetName("tpipClrMertFeeRuleDOResDs");
			resDss.putDataset(tpipClrMertFeeRuleDOResDs);
		}
		
		//获取TPipClrMertAcctDO列表
		TPipClrMertAcctDO reqTPipClrMertAcctDO = new TPipClrMertAcctDO();
		reqTPipClrMertAcctDO.setRuleId(ruleId);
		List<TPipClrMertAcctDO> resTPipClrMertAcctDOList = clrMertAcctDao.list(reqTPipClrMertAcctDO);

		if (null != resTPipClrMertAcctDOList && resTPipClrMertAcctDOList.size() > 0) {
			IDataset tpipClrMertAcctDOResDs = DatasetService.getInstace().getDataset(resTPipClrMertAcctDOList, TPipClrMertAcctDO.class);
			tpipClrMertAcctDOResDs.setDatasetName("tpipClrMertAcctDOResDs");
			tpipClrMertAcctDOResDs.setTotalCount(resTPipClrMertAcctDOList.size());
			resDss.putDataset(tpipClrMertAcctDOResDs);
		}
		
		//获取TPipClrMertFeeCaltDO
		TPipClrMertFeeCaltDO reqTPipClrMertFeeCaltDO = new TPipClrMertFeeCaltDO();
		reqTPipClrMertFeeCaltDO.setRuleId(ruleId);
		TPipClrMertFeeCaltDO resTPipClrMertFeeCaltDO=clrMertFeeCaltDao.get(reqTPipClrMertFeeCaltDO);

		if (null != resTPipClrMertFeeCaltDO) {
			IDataset tpipClrMertFeeCaltDOResDs = DatasetService.getInstace().getDataset(resTPipClrMertFeeCaltDO, TPipClrMertFeeCaltDO.class);
			tpipClrMertFeeCaltDOResDs.setDatasetName("tpipClrMertFeeCaltDOResDs");
			resDss.putDataset(tpipClrMertFeeCaltDOResDs);
		}

		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "交易成功");
	}
	
	public TPipClrMertFeeRuleDO getData(IDataset ds){
		TPipClrMertFeeRuleDO DO = new TPipClrMertFeeRuleDO();
		
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
