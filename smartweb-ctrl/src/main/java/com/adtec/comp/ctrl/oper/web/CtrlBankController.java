package com.adtec.comp.ctrl.oper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaCompDealTParaBankReqDTO;
import com.adtec.comp.ctrl.oper.service.CtrlBankService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlBank")
public class CtrlBankController extends BaseController {
	@Autowired
	private CtrlBankService ctrlBankService;

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "ctrlBankList" })
	public String ctrlBankList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/ctrlBankList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "ctrlBankForm" })
	public String ctrlBankForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/ctrlBankForm";
	}

	/**
	 * 查询、新增、修改、删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "action" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String OPER_TP = reqDs.getString("OPER_TP");
		String BANK = reqDs.getString("BANK");
		String BANK_NAME = reqDs.getString("BANK_NAME");
		String APP_NAME = reqDs.getString("APP_NAME");
		String BANK_BUSI_GRP_TP = reqDs.getString("BANK_BUSI_GRP_TP");
		String BANK_BUSI_KD = reqDs.getString("BANK_BUSI_KD");
		String OTH_BANK = reqDs.getString("OTH_BANK");
		String REGION_CODE = reqDs.getString("REGION_CODE");
		String DIRT_BANK = reqDs.getString("DIRT_BANK");
		String CLR_BRCH = reqDs.getString("CLR_BRCH");
		String UP_BRCH = reqDs.getString("UP_BRCH");
		String AGENT_BANK = reqDs.getString("AGENT_BANK");
		String CITY_CODE = reqDs.getString("CITY_CODE");
		String GRP_FLG = reqDs.getString("GRP_FLG");
		String TEL_NO = reqDs.getString("TEL_NO");
		String POST_ECD = reqDs.getString("POST_ECD");
		String EMAIL_ADDR = reqDs.getString("EMAIL_ADDR");
		String DTL_ADDR = reqDs.getString("DTL_ADDR");
		String EFFT_DATE = reqDs.getString("EFFT_DATE");
		String CANCL_DATE = reqDs.getString("CANCL_DATE");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TParaCompDealTParaBankReqDTO reqBody = new TParaCompDealTParaBankReqDTO();
		reqBody.setOPER_TP(OPER_TP);
		reqBody.setBANK(BANK);
		reqBody.setBANK_NAME(BANK_NAME);
		reqBody.setAPP_NAME(APP_NAME);
		reqBody.setBANK_BUSI_GRP_TP(BANK_BUSI_GRP_TP);
		reqBody.setBANK_BUSI_KD(BANK_BUSI_KD);
		reqBody.setOTH_BANK(OTH_BANK);
		reqBody.setREGION_CODE(REGION_CODE);
		reqBody.setDIRT_BANK(DIRT_BANK);
		reqBody.setCLR_BRCH(CLR_BRCH);
		reqBody.setUP_BRCH(UP_BRCH);
		reqBody.setAGENT_BANK(AGENT_BANK);
		reqBody.setCITY_CODE(CITY_CODE);
		reqBody.setGRP_FLG(GRP_FLG);
		reqBody.setTEL_NO(TEL_NO);
		reqBody.setPOST_ECD(POST_ECD);
		reqBody.setEMAIL_ADDR(EMAIL_ADDR);
		reqBody.setDTL_ADDR(DTL_ADDR);
		reqBody.setEFFT_DATE(EFFT_DATE);
		reqBody.setCANCL_DATE(CANCL_DATE);

		IDataset resDs = ctrlBankService.action(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

}
