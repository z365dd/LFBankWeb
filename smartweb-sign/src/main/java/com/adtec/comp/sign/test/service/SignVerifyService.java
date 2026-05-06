package com.adtec.comp.sign.test.service;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.FSignVerifSignVerifyReqDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

import net.sf.json.JSONObject;

@Service
public class SignVerifyService extends BaseService{

	/**
	 * 签约检查
	 * @param cHNL_NO 
	 * @param fUNCT_NO 
	 * @param bUSI_NO 
	 * @param eNTR_NO 
	 */
	public IDataset add(FSignVerifSignVerifyReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignVerifSgl");
		/*req.getLOCAL_HEAD().setENTR_NO(eNTR_NO);
		req.getLOCAL_HEAD().setCHNL_NO(cHNL_NO);
		req.getLOCAL_HEAD().setFUNC_NO(fUNCT_NO);*/
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		
		JSONObject resJSON = httpJsonFactory.callService("FSignVerifSgl",req,"",ParamUtil.getConfig("SIGN_PARTID"),null);
		JSONObject resBody = (JSONObject) resJSON.get("BODY");

		
		responseData.addColumn("FSignVerifSignVerifyRes");
		responseData.beforeFirst();
		if(!responseData.hasNext()){
			responseData.appendRow();
		}
		responseData.next();
		responseData.updateValue("FSignVerifSignVerifyRes", resBody);
		return responseData;
	}
	
	
}
