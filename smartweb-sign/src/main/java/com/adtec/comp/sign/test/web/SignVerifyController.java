package com.adtec.comp.sign.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.FSignVerifSignVerifyListReqDTO;
import com.adtec.comp.sign.dto.FSignVerifSignVerifyReqDTO;
import com.adtec.comp.sign.test.service.SignVerifyService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value = "${adminPath}/comp/sign/test/signVerify")
public class SignVerifyController extends BaseController {

	@Autowired
	private SignVerifyService signVerifyService;

	@RequiresPermissions("user")
	@RequestMapping(value = { "signVerifyForm" })
	public String signVerifyForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/signVerifyForm";
	}

	/**
	 * 签约检查
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String OTH_CUST_NO = reqDs.getString("OTH_CUST_NO");
		String ACCT = reqDs.getString("ACCT");
		String OTH_CUST_NAME = reqDs.getString("OTH_CUST_NAME");
		String OTH_ENTR_NO = reqDs.getString("OTH_ENTR_NO");
		String ACCT_NAME = reqDs.getString("ACCT_NAME");
		String CERT_TP = reqDs.getString("CERT_TP");
		String CERT_NO = reqDs.getString("CERT_NO");
		String SIGN_CTRCT_NO = reqDs.getString("SIGN_CTRCT_NO");
		FSignVerifSignVerifyReqDTO reqBody = new FSignVerifSignVerifyReqDTO();
	/*	reqBody.setENTR_NO(ENTR_NO);
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setFUNCT_NO(FUNCT_NO);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setOPER_TP(OPER_TP);*/

		FSignVerifSignVerifyListReqDTO reqList = new FSignVerifSignVerifyListReqDTO();
		reqList.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
		reqList.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqList.setOTH_CUST_NO(OTH_CUST_NO);
		reqList.setACCT(ACCT);
		reqList.setOTH_CUST_NAME(OTH_CUST_NAME);
		reqList.setOTH_ENTR_NO(OTH_ENTR_NO);
		reqList.setACCT_NAME(ACCT_NAME);
		reqList.setCERT_TP(CERT_TP);
		reqList.setCERT_NO(CERT_NO);
		reqList.setSIGN_CTRCT_NO(SIGN_CTRCT_NO);
		reqBody.getLIST().add(reqList);

		IDataset res = signVerifyService.add(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "签约检查成功");
	}

}
