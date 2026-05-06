package com.adtec.comp.sign.tec.service;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.SignParaListResDTO;
import com.adtec.comp.sign.dto.SignParaModReqDTO;
import com.adtec.comp.sign.dto.SignParaModResDTO;
import com.adtec.comp.sign.dto.SignParaQryReqDTO;
import com.adtec.comp.sign.dto.SignParaQryResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class SignParaService extends BaseService{

	/**
	 * 签约参数查询
	 */
	public IDataset qry(SignParaQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignSignParaQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignParaQry", req, SignParaQryResDTO.class,null);
		SignParaQryResDTO resBody = (SignParaQryResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody.getKEY_LIST(), SignParaListResDTO.class);
		return responseData;
	}
	
	/**
	 * 签约参数修改
	 */
	public IDataset mod(SignParaModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignSignParaMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignParaMod", req, SignParaModResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), SignParaModResDTO.class);
		return responseData;
	}
	
}
