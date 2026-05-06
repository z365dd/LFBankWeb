package com.adtec.comp.sign.test.service;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.SignChkReqDTO;
import com.adtec.comp.sign.dto.SignChkResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class SignChkService extends BaseService{

	/**
	 * 签约检查
	 */
	public IDataset chk(SignChkReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignTranSignChk");
		req.getLOCAL_HEAD().setCHNL_NO(reqBody.getLIST().get(0).getCHNL_NO());
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranSignChk", req, SignChkResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), SignChkResDTO.class);
		return responseData;
	}
	
	
}
