package com.adtec.comp.ctrl.om.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.TParaBrchQryListResDTO;
import com.adtec.comp.ctrl.dto.TParaBrchQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaBrchQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class BrchQryService {
	/**
	 * 查询机构
	 */
	public IDataset getBrch(TParaBrchQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "TParaBrchQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaBrchQry", req, TParaBrchQryResDTO.class,null);
		TParaBrchQryResDTO resBody = new TParaBrchQryResDTO();
		if(res.getBODY()!=null){
			resBody = (TParaBrchQryResDTO) res.getBODY();
		}
		List<TParaBrchQryListResDTO> LIST = resBody.getBRCH_LIST();
		responseData = DatasetService.getInstace().getDataset(LIST,TParaBrchQryListResDTO.class);
		return responseData;
	}
	
	
}
