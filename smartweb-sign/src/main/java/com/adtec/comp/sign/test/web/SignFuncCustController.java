package com.adtec.comp.sign.test.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.FSignFactQryAcctInfoReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustCanListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustCanReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleANReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleChnlListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleLimitListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustPreSignConfListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustPreSignconfReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustQryReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignANReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignChnlListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignLimitListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignReqDTO;
import com.adtec.comp.sign.entity.FSignPipSignRuleDO;
import com.adtec.comp.sign.entity.FSignTParaChnlDO;
import com.adtec.comp.sign.test.service.SignFuncCustService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "${adminPath}/comp/sign/test/signFuncCust")
public class SignFuncCustController extends BaseController {

	@Autowired
	private SignFuncCustService signFuncCustService;

	@RequiresPermissions("user")
	@RequestMapping(value = "signFuncCustList")
	public String signFuncCustList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/signFuncCustList";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "signFuncCustMsgList")
	public String signFuncCustMsgList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/signFuncCustMsgList";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "signFuncCustForm")
	public String signFuncCustForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/signFuncCustForm";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "signFuncPreForm")
	public String signFuncPreForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/test/signFuncPreForm";
	}
	
	/**
	 * 签约协议类型ID下拉框,带空项
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getSignTpPara" })
	public void getSignTpPara(HttpServletRequest request, HttpServletResponse response){
		List<FSignPipSignRuleDO> list = signFuncCustService.getParaLoadList(new FSignPipSignRuleDO());
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "--请选择--");
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for(FSignPipSignRuleDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getRuleDesc());
			map.put("value", DO.getRuleId());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
	
	/**
	 * 渠道号下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getChnlNo" })
	public void getChnlNo(HttpServletRequest request, HttpServletResponse response){
		List<FSignTParaChnlDO> list = signFuncCustService.getChnlNoList(new FSignTParaChnlDO());
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "全渠道");
		emptyMap.put("value", "000000");
		maps.add(emptyMap);
		for(FSignTParaChnlDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getChnlName());
			map.put("value", DO.getChnlNo());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
	
	/**
	 * 获取签约类型
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getSignTp" })
	public void getSignTp(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ruleId = reqDs.getString("SIGN_PROT_TP_ID");
		String SiGN_TP = signFuncCustService.getSignTp(new FSignPipSignRuleDO(), ruleId);
		
		renderString(response, SiGN_TP);
	}
	
	// 列表查询
	@RequiresPermissions("user")
	@RequestMapping(value = "qry")
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String OTH_CUST_NO = reqDs.getString("OTH_CUST_NO");
		String OTH_ENTR_NO = reqDs.getString("OTH_ENTR_NO");
		String ACCT = reqDs.getString("ACCT");
		String SIGN_CTRCT_NO = reqDs.getString("SIGN_CTRCT_NO");
		String CERT_TP = reqDs.getString("CERT_TP");
		String CERT_NO = reqDs.getString("CERT_NO");
		String SIGN_STAT = reqDs.getString("SIGN_STAT");
		String PER_TEL_NO = reqDs.getString("PER_TEL_NO");
		String RESP_FILE_FLG = reqDs.getString("RESP_FILE_FLG");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		String TP = reqDs.getString("TP");

		FSignFuncCustQryReqDTO reqBody = new FSignFuncCustQryReqDTO();
		reqBody.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
		reqBody.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqBody.setOTH_CUST_NO(OTH_CUST_NO);
		reqBody.setOTH_ENTR_NO(OTH_ENTR_NO);
		reqBody.setACCT(ACCT);
		reqBody.setSIGN_CTRCT_NO(SIGN_CTRCT_NO);
		reqBody.setCERT_TP(CERT_TP);
		reqBody.setCERT_NO(CERT_NO);
		reqBody.setSIGN_STAT(SIGN_STAT);
		reqBody.setPER_TEL_NO(PER_TEL_NO);
		reqBody.setRESP_FILE_FLG(RESP_FILE_FLG);

		IDataset resDs = signFuncCustService.qry(reqBody, start, limit,TP);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 新增获取数据
	 */
	public FSignFuncCustSignReqDTO getReqBody(IDataset reqDs) {
		
		String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String OTH_ENTR_NO = reqDs.getString("OTH_ENTR_NO");
		String OTH_CUST_NO = reqDs.getString("OTH_CUST_NO");
		String SIGN_CTRCT_NO = reqDs.getString("SIGN_CTRCT_NO");
		String PROT_EFFT_DATE = reqDs.getString("PROT_EFFT_DATE");
		String PROT_END_DATE = reqDs.getString("PROT_END_DATE");
	
		String ACCT = reqDs.getString("ACCT");
		String ACCT_NAME = reqDs.getString("ACCT_NAME");
		String ACCT_TP = reqDs.getString("ACCT_TP");
		String CERT_TP = reqDs.getString("CERT_TP");
		String CERT_NO = reqDs.getString("CERT_NO");
		String PHONE_NO = reqDs.getString("PHONE_NO");
		String COMM_ADDR = reqDs.getString("COMM_ADDR");
		String EMAIL_ADDR = reqDs.getString("EMAIL_ADDR");
		
		String CHNL_NO1 = reqDs.getString("CHNL_NO1");
		String LIST_STR = reqDs.getString("LIST");
		/*String DYN_LIST_STR = reqDs.getString("LIST2");*/

		FSignFuncCustSignReqDTO reqBody = new FSignFuncCustSignReqDTO();
		/*reqBody.setFLG(FLG);*/
		FSignFuncCustSignListReqDTO reqList = new FSignFuncCustSignListReqDTO();
		reqList.setSER(1);
		reqList.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
		reqList.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqList.setOTH_ENTR_NO(OTH_ENTR_NO);
		// reqList.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqList.setOTH_CUST_NO(OTH_CUST_NO);
		reqList.setSIGN_CTRCT_NO(SIGN_CTRCT_NO);
		// reqList.setOTH_ENTR_NO(OTH_ENTR_NO);
		reqList.setPROT_EFFT_DATE(PROT_EFFT_DATE);
		reqList.setPROT_END_DATE(PROT_END_DATE);

		FSignFuncCustSignANReqDTO ACCT_NODE = new FSignFuncCustSignANReqDTO();
		ACCT_NODE.setACCT(ACCT);
		ACCT_NODE.setACCT_NAME(ACCT_NAME);
		ACCT_NODE.setACCT_TP(ACCT_TP);
		ACCT_NODE.setCERT_TP(CERT_TP);
		ACCT_NODE.setCERT_NO(CERT_NO);
		//ACCT_NODE.setCUST_TP(CUST_TP);
		ACCT_NODE.setPHONE_NO(PHONE_NO);
		ACCT_NODE.setCOMM_ADDR(COMM_ADDR);
		ACCT_NODE.setEMAIL_ADDR(EMAIL_ADDR);
		//ACCT_NODE.setBANK_CUST_NO(BANK_CUST_NO);
		reqList.getACCT_NODE().add(ACCT_NODE);
		
		if(!StringUtils.isEmpty(CHNL_NO1)){
			String[] chnlArr = CHNL_NO1.split(";",-1);
			for(int a =0;a<chnlArr.length;a++){
				FSignFuncCustSignChnlListReqDTO chnlDto = new FSignFuncCustSignChnlListReqDTO();
				chnlDto.setCHNL_NO(chnlArr[a]);
				reqList.getCHNL_LIST().add(chnlDto);
			}
		}

		JSONArray jsonArr = JSONArray.fromObject(LIST_STR);
		@SuppressWarnings("unchecked")
		List<FSignFuncCustSignLimitListReqDTO> LIMIT_LIST = JSONArray.toList(jsonArr,
				FSignFuncCustSignLimitListReqDTO.class);
		reqList.setLIMIT_LIST(LIMIT_LIST);
		
		/*JSONArray jsonArr2 = JSONArray.fromObject(DYN_LIST_STR);
		@SuppressWarnings("unchecked")
		List<FSignFuncCustQryDynListReqDTO> DYN_LIST = JSONArray.toList(jsonArr2,
				FSignFuncCustQryDynListReqDTO.class);
		reqList.setDYN_LIST(DYN_LIST);*/
		
		reqBody.getLIST().add(reqList);

		return reqBody;
	}
	
	/**
	 * 新增获取数据
	 */
	public FSignFuncCustModSimpleReqDTO getReqRevBody(IDataset reqDs) {
	
		long SER = reqDs.getLong("SER");
		String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String OTH_CUST_NO = reqDs.getString("OTH_CUST_NO");
		String SIGN_CTRCT_NO = reqDs.getString("SIGN_CTRCT_NO");
		String OTH_ENTR_NO = reqDs.getString("OTH_ENTR_NO");
		String PROT_EFFT_DATE = reqDs.getString("PROT_EFFT_DATE");
		String PROT_END_DATE = reqDs.getString("PROT_END_DATE");
		
		String ACCT = reqDs.getString("ACCT");
		String ACCT_NAME = reqDs.getString("ACCT_NAME");
		String ACCT_TP = reqDs.getString("ACCT_TP");
		String CERT_TP = reqDs.getString("CERT_TP");
		String CERT_NO = reqDs.getString("CERT_NO");
		String PHONE_NO = reqDs.getString("PHONE_NO");
		String COMM_ADDR = reqDs.getString("COMM_ADDR");
		String EMAIL_ADDR = reqDs.getString("EMAIL_ADDR");
		String CHNL_NO1 = reqDs.getString("CHNL_NO1");
		String LIST_STR = reqDs.getString("LIST");
		/*String DYN_LIST_STR = reqDs.getString("LIST2");*/

		FSignFuncCustModSimpleReqDTO reqBody = new FSignFuncCustModSimpleReqDTO();
//		reqBody.setFLG(FLG);
		FSignFuncCustModSimpleListReqDTO reqList = new FSignFuncCustModSimpleListReqDTO();
		reqList.setSER(SER);
		reqList.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
		reqList.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqList.setOTH_CUST_NO(OTH_CUST_NO);
		reqList.setSIGN_CTRCT_NO(SIGN_CTRCT_NO);
		reqList.setOTH_ENTR_NO(OTH_ENTR_NO);
		reqList.setPROT_EFFT_DATE(PROT_EFFT_DATE);
		reqList.setPROT_END_DATE(PROT_END_DATE);
		reqList.setSIGN_CTRCT_NO(SIGN_CTRCT_NO);


		FSignFuncCustModSimpleANReqDTO ACCT_NODE = new FSignFuncCustModSimpleANReqDTO();
		ACCT_NODE.setACCT(ACCT);
		ACCT_NODE.setACCT_NAME(ACCT_NAME);
		ACCT_NODE.setACCT_TP(ACCT_TP);
		ACCT_NODE.setCERT_TP(CERT_TP);
		ACCT_NODE.setCERT_NO(CERT_NO);
		ACCT_NODE.setPHONE_NO(PHONE_NO);
		ACCT_NODE.setCOMM_ADDR(COMM_ADDR);
		ACCT_NODE.setEMAIL_ADDR(EMAIL_ADDR);
		reqList.getACCT_NODE().add(ACCT_NODE); 
		
		if(!StringUtils.isEmpty(CHNL_NO1)){
			String[] chnlArr = CHNL_NO1.split(";",-1);
			for(int a =0;a<chnlArr.length;a++){
				FSignFuncCustModSimpleChnlListReqDTO chnlDto = new FSignFuncCustModSimpleChnlListReqDTO();
				chnlDto.setCHNL_NO(chnlArr[a]);
				reqList.getCHNL_LIST().add(chnlDto);
			}
		}

		JSONArray jsonArr = JSONArray.fromObject(LIST_STR);
		@SuppressWarnings("unchecked")
		List<FSignFuncCustModSimpleLimitListReqDTO> LIMIT_LIST = JSONArray.toList(jsonArr,
				FSignFuncCustModSimpleLimitListReqDTO.class);
		reqList.setLIMIT_LIST(LIMIT_LIST);
		
		
		/*JSONArray jsonArr2 = JSONArray.fromObject(DYN_LIST_STR);
		@SuppressWarnings("unchecked")
		List<FSignFuncCustQryDynListReqDTO> DYN_LIST = JSONArray.toList(jsonArr2,
				FSignFuncCustQryDynListReqDTO.class);
		reqList.setDYN_LIST(DYN_LIST);*/
		
		reqBody.getLIST().add(reqList);

		return reqBody;
	}

	/**
	 * 新增提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FSignFuncCustSignReqDTO reqBody = getReqBody(reqDs);

		IDataset resDs = signFuncCustService.add(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 预签约
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "preSign" })
	public void preSign(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FSignFuncCustSignReqDTO reqBody = getReqBody(reqDs);
		
		IDataset resDs = signFuncCustService.preSign(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 预签约确认
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "Confirm" })
	public void Confirm(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		long SER = reqDs.getLong("SER");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
		String OTH_CUST_NO = reqDs.getString("OTH_CUST_NO");
		String ACCT = reqDs.getString("ACCT");


		FSignFuncCustPreSignconfReqDTO reqBody = new FSignFuncCustPreSignconfReqDTO();
		FSignFuncCustPreSignConfListReqDTO reqList = new FSignFuncCustPreSignConfListReqDTO();
		
		reqList.setSER(SER); 
		reqList.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
		reqList.setSIGN_PROT_NO(SIGN_PROT_NO);
		reqList.setOTH_CUST_NO(OTH_CUST_NO);
		reqList.setACCT(ACCT);
		reqBody.getLIST().add(reqList);

		IDataset resDs = signFuncCustService.Confirm(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 修改提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "revice" })
	public void revice(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FSignFuncCustModSimpleReqDTO reqBody = getReqRevBody(reqDs);

		IDataset resDs = signFuncCustService.revice(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	// 解约
	@RequiresPermissions("user")
	@RequestMapping(value = "Can")
	public void Can(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FLG = reqDs.getString("FLG");
		long SER = reqDs.getLong("SER");
		String SIGN_PROT_NO = reqDs.getString("SIGN_PROT_NO");
		String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");


		FSignFuncCustCanReqDTO reqBody = new FSignFuncCustCanReqDTO();
		reqBody.setFLG(FLG);
		FSignFuncCustCanListReqDTO reqList = new FSignFuncCustCanListReqDTO();
		
		reqList.setSER(SER); 
		reqList.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
		reqList.setSIGN_PROT_NO(SIGN_PROT_NO);
		
		reqBody.getLIST().add(reqList);

		IDataset resDs = signFuncCustService.Can(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 账户基本信息查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "FactQryAcctInfo","" })
	public void FactQryAcctInfo(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ACCT = reqDs.getString("ACCT");
		FSignFactQryAcctInfoReqDTO req = new FSignFactQryAcctInfoReqDTO();
		req.setACCT(ACCT);
		
		IDataset resDs = signFuncCustService.acctInfo(req);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
