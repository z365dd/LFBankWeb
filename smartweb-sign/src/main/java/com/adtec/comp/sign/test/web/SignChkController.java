package com.adtec.comp.sign.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.SignChkReqDTO;
import com.adtec.comp.sign.dto.SignChkReqListDTO;
import com.adtec.comp.sign.test.service.SignChkService;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value="${adminPath}/comp/sign/test/signChk")
public class SignChkController extends BaseController{
	
	@Autowired
	private SignChkService signChkService;
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/signChkForm";
	}
	
	
	/**
	 * 签约检查
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"chk"})
	public void chk(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		String oldAcct = reqDs.getString("OLD_ACCT");
		String newAcct = reqDs.getString("NEW_ACCT");
		String othCustNo = reqDs.getString("OTH_CUST_NO");
		String bankSignPortNo = reqDs.getString("BANK_SIGN_PROT_NO");
		String signProtNo = reqDs.getString("SIGN_PROT_NO");
		String othSignProtNo = reqDs.getString("OTH_SIGN_PROT_NO");
		String certTp = reqDs.getString("CERT_TP");
		String certNo = reqDs.getString("CERT_NO");
		String acctName = reqDs.getString("ACCT_NAME");
		String bank = reqDs.getString("BANK");
		String bankName = reqDs.getString("BANK_NAME");
		String perTelNo = reqDs.getString("PER_TEL_NO");
		String entrAcct = reqDs.getString("ENTR_ACCT");
		String entrAcctName = reqDs.getString("ENTR_ACCT_NAME");
		String entrAcctBank = reqDs.getString("ENTR_ACCT_BANK");
		String entrAcctBankName = reqDs.getString("ENTR_ACCT_BANK_NAME");
		String entrTelNo = reqDs.getString("ENTR_TEL_NO");
		String oppAcct = reqDs.getString("OPP_ACCT");
		String oppAcctName = reqDs.getString("OPP_ACCT_NAME");
		String oppBank = reqDs.getString("OPP_BANK");
		String oppBankName = reqDs.getString("OPP_BANK_NAME");
		String flg = reqDs.getString("FLG");
		String tranAmt = reqDs.getString("TRAN_AMT");
		String dynData = reqDs.getString("DYN_DATA");
		String chnlNo = reqDs.getString("CHNL_NO");
		
		SignChkReqDTO reqBody = new SignChkReqDTO();
		SignChkReqListDTO reqList = new SignChkReqListDTO();
		reqList.setBUSI_NO(busiNo);
		reqList.setSUB_BUSI_NO(subBusiNo);
		reqList.setENTR_NO(entrNo);
		reqList.setOLD_ACCT(oldAcct);
		reqList.setNEW_ACCT(newAcct);
		reqList.setOTH_CUST_NO(othCustNo);
		reqList.setBANK_SIGN_PROT_NO(bankSignPortNo);
		reqList.setSIGN_PROT_NO(signProtNo);
		reqList.setOTH_SIGN_PROT_NO(othSignProtNo);
		reqList.setCERT_TP(certTp);
		reqList.setCERT_NO(certNo);
		reqList.setACCT_NAME(acctName);
		reqList.setBANK(bank);
		reqList.setBANK_NAME(bankName);
		reqList.setPER_TEL_NO(perTelNo);
		reqList.setENTR_ACCT(entrAcct);
		reqList.setENTR_ACCT_NAME(entrAcctName);
		reqList.setENTR_ACCT_BANK(entrAcctBank);
		reqList.setENTR_ACCT_BANK_NAME(entrAcctBankName);
		reqList.setENTR_TEL_NO(entrTelNo);
		reqList.setOPP_ACCT(oppAcct);
		reqList.setOPP_ACCT_NAME(oppAcctName);
		reqList.setOPP_BANK(oppBank);
		reqList.setOPP_BANK_NAME(oppBankName);
		reqList.setFLG(flg);
		reqList.setTRAN_AMT(DataUtil.isNullStr(tranAmt)?0:Double.valueOf(tranAmt));
		reqList.setCHNL_NO(chnlNo);
		reqList.setDYN_DATA(dynData);
		
		reqBody.setNUM(1);
		reqBody.getLIST().add(reqList);
		
		IDataset res = signChkService.chk(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "签约检查成功");
	}
	
}
