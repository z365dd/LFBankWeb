package com.adtec.comp.ctrl.om.service;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FChkMngRegRedisParaReqDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class FreshChkParaService {
	/**
	 * 刷新
	 */
	public IDataset fresh(FChkMngRegRedisParaReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FChkMngRegRedisPara");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FChkMngRegRedisPara", req, FChkMngRegRedisParaReqDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),FChkMngRegRedisParaReqDTO.class);
		return responseData;
	}
	
	
}
