package com.adtec.comp.ctrl.oper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaModResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngPubParaQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class PubParaService {

	/**
	 * 查询
	 */
	public IDataset qry(FCtrlMngPubParaQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngPubParaQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngPubParaQry", req, FCtrlMngPubParaQryResDTO.class,null);
		FCtrlMngPubParaQryResDTO resBody = (FCtrlMngPubParaQryResDTO) res.getBODY();
		List<FCtrlMngPubParaQryListDTO> listDto = resBody.getKEY_LIST();
		responseData = DatasetService.getInstace().getDataset(listDto, FCtrlMngPubParaQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 修改
	 */
	public IDataset Mod(FCtrlMngPubParaModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngPubParaMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngPubParaMod", req, FCtrlMngPubParaModResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), FCtrlMngPubParaModResDTO.class);
		return responseData;
	}
}
