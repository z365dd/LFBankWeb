package com.adtec.comp.sign.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.SignCustCanReqDTO;
import com.adtec.comp.sign.dto.SignCustModReqDTO;
import com.adtec.comp.sign.dto.SignCustQryReqDTO;
import com.adtec.comp.sign.dto.SignCustSignReq1DTO;
import com.adtec.comp.sign.test.service.SignCustSignService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value="${adminPath}/comp/sign/test/custsign")
public class SignCustSignController extends BaseController{
	
	@Autowired
	private SignCustSignService csService;
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toList"})
	public String toList(HttpServletRequest request, HttpServletResponse response,String compNo) {
		//SwitchesController.setPub_compNo(compNo);
		return "starring/comp/sign/test/custSignList";
	}
	
	/**
	 *返回新增或修改页面页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toForm"})
	public String toForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/custSignForm";
	}
	
	//客户签约查询
	@RequiresPermissions("user")
	@RequestMapping(value ={"signCustQry"})
	public void qry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String SUB_BUSI_NO = reqDs.getString("SUB_BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String ACCT = reqDs.getString("ACCT");
		String CERT_TP = reqDs.getString("CERT_TP");
		String CERT_NO = reqDs.getString("CERT_NO");
		String OTH_CUST_NO = reqDs.getString("OTH_CUST_NO");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String SIGN_STAT = reqDs.getString("SIGN_STAT");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		SignCustQryReqDTO reqBody = new SignCustQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setSUB_BUSI_NO(SUB_BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setCERT_TP(CERT_TP);
		reqBody.setCERT_NO(CERT_NO);
		reqBody.setACCT(ACCT);
		reqBody.setOTH_CUST_NO(OTH_CUST_NO);
		reqBody.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqBody.setSIGN_STAT(SIGN_STAT);
		
		IDataset resDs = csService.qry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	//客户签约添加
	@RequiresPermissions("user")
	@RequestMapping(value ={"signCustSign"})
	public void add(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String addDataStr = reqDs.getString("addDataStr");
		/*String --> JSON */
		JSONObject jo = JSON.parseObject(addDataStr);
		JSONArray joArr = new JSONArray();
		joArr.add(jo);
		SignCustSignReq1DTO reqBody = new SignCustSignReq1DTO();
		reqBody.setACCT_LIST(joArr);
		reqBody.setACCT_NUM(1);
		IDataset resDs = csService.add(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
		
		/*公共主信息*/
		/*String ACCT_TP = jo.getString("ACCT_TP");
		String ACCT = jo.getString("ACCT");
		String ACCT_NAME = jo.getString("ACCT_NAME");
		String ACCT_PASS = jo.getString("ACCT_PASS");
		String CUST_TP = jo.getString("CUST_TP");
		String BANK = jo.getString("BANK");
		String BANK_NAME = jo.getString("BANK_NAME");
		String TEL_NO = jo.getString("TEL_NO");
		String WCHAT_NO = jo.getString("WCHAT_NO");
		String COMM_ADDR = jo.getString("COMM_ADDR");
		String EMAIL_ADDR = jo.getString("EMAIL_ADDR");
		String POST_ECD = jo.getString("POST_ECD");
		String CERT_TP = jo.getString("CERT_TP");
		String CERT_NO = jo.getString("CERT_NO");
		
		List<SignCustBusiListDTO> BUSI_LIST = new ArrayList<SignCustBusiListDTO>();
		List<SignCustSubBusiListDTO> SUB_BUSI_LIST = new ArrayList<SignCustSubBusiListDTO>();
		List<SignCustCtrlListDTO> CTRL_LIST = new ArrayList<SignCustCtrlListDTO>();
		业务信息
		JSONArray BusiArr = jo.getJSONArray("BUSI_LIST");
		for(int i=0;i<BusiArr.size();i++){
			JSONObject BusiJo = BusiArr.getJSONObject(i);
			String BUSI_NO = BusiJo.getString("BUSI_NO");
			String BUSI_NAME = BusiJo.getString("BUSI_NAME");
			SignCustBusiListDTO busiDTO = new SignCustBusiListDTO();
			busiDTO.setBUSI_NO(BUSI_NO);
			busiDTO.setBUSI_NAME(BUSI_NAME);
			
			子业务信息
			JSONArray SubBusiArr = BusiJo.getJSONArray("SUB_BUSI_LIST");
			for(int j=0;j<SubBusiArr.size();j++){
				JSONObject SubBusiJo = SubBusiArr.getJSONObject(j);
				String SUB_BUSI_NO = SubBusiJo.getString("SUB_BUSI_NO");
				String SUB_BUSI_NAME = SubBusiJo.getString("SUB_BUSI_NAME");
				SignCustSubBusiListDTO subDTO = new SignCustSubBusiListDTO();
				subDTO.setSUB_BUSI_NO(SUB_BUSI_NO);
				subDTO.setSUB_BUSI_NAME(SUB_BUSI_NAME);
				
				控制校验信息
				JSONArray CtrlArr = SubBusiJo.getJSONArray("CTRL_LIST");
				for(int k=0;k<CtrlArr.size();k++){
					JSONObject CtrlJo = CtrlArr.getJSONObject(k);
					String KV = CtrlJo.getString("KV");
					String KEY_NAME = CtrlJo.getString("KEY_NAME");
					SignCustCtrlListDTO ctrlDTO = new SignCustCtrlListDTO();
					ctrlDTO.setKV(KV);
					ctrlDTO.setKEY_NAME(KEY_NAME);
					CTRL_LIST.add(ctrlDTO);
				}
				subDTO.setCTRL_LIST(CTRL_LIST);
				SUB_BUSI_LIST.add(subDTO);
			}
			busiDTO.setSUB_BUSI_LIST(SUB_BUSI_LIST);
			BUSI_LIST.add(busiDTO);
		}
		
		SignCustAcctListDTO acctDTO = new SignCustAcctListDTO();
		acctDTO.setACCT(ACCT);
		acctDTO.setACCT_NAME(ACCT_NAME);
		acctDTO.setBUSI_LIST(BUSI_LIST);
		acctDTO.setBUSI_NUM(BUSI_LIST.size());
		List<SignCustAcctListDTO> ACCT_LIST = new ArrayList<SignCustAcctListDTO>();
		ACCT_LIST.add(acctDTO);
		SignCustSignReqDTO reqBody = new SignCustSignReqDTO();
		reqBody.setACCT_NUM(ACCT_LIST.size());
		reqBody.setACCT_LIST(ACCT_LIST);*/
	}
	
	//客户签约修改
	@RequiresPermissions("user")
	@RequestMapping(value ={"signCustMod"})
	public void mod(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String modDataStr = reqDs.getString("modDataStr");
		/*String --> JSON */
		JSONArray jarr = new JSONArray();
		JSONObject jo = JSON.parseObject(modDataStr);
		jarr.add(jo);
		SignCustModReqDTO reqBody = new SignCustModReqDTO();
		reqBody.setACCT_LIST(jarr);
		reqBody.setACCT_NUM(1);
		IDataset resDs = csService.mod(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	//客户解约
	@RequiresPermissions("user")
	@RequestMapping(value ={"signCustDel"})
	public void del(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String delDataStr = reqDs.getString("delDataStr");
		/*String --> JSON */
		JSONArray jarr = new JSONArray();
		JSONObject jo = JSON.parseObject(delDataStr);
		jarr.add(jo);
		SignCustCanReqDTO reqBody = new SignCustCanReqDTO();
		reqBody.setACCT_LIST(jarr);
		reqBody.setACCT_NUM(jarr.size());
		IDataset resDs = csService.del(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

}
