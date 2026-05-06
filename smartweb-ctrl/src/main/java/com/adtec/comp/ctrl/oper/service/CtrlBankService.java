package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.CommonType;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaBankListResDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaBankReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaBankResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class CtrlBankService {
	/**
	 * 查询、新增、修改、删除
	 */
	public IDataset action(TParaCompDealTParaBankReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("", req, "TParaCompDealTParaBank", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"TParaCompDealTParaBank", req, TParaCompDealTParaBankResDTO.class, null);
		TParaCompDealTParaBankResDTO resBody = new TParaCompDealTParaBankResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (TParaCompDealTParaBankResDTO) resDTO.getBODY();
		}
		List<TParaCompDealTParaBankListResDTO> list = new ArrayList<TParaCompDealTParaBankListResDTO>();
		if(resBody.getNUM()>0){
			list= resBody.getLIST();
		}
		if (CommonType.QRY.equals(reqBody.getOPER_TP())) {
			// 查询列表
			int total = list.size();
			// 取指定条数，设置操作,转换显示
			List<TParaCompDealTParaBankListResDTO> listPage = setAction(list, start, limit);

			responseData = DatasetService.getInstace().getDataset(listPage, TParaCompDealTParaBankListResDTO.class);
			responseData.setTotalCount(total);
		}else{
			responseData = DatasetService.getInstace().getDataset(list, TParaCompDealTParaBankListResDTO.class);
		}
		return responseData;
	}

	/* 分页取条数 设置查询的操作和显示 */
	private List<TParaCompDealTParaBankListResDTO> setAction(List<TParaCompDealTParaBankListResDTO> list, int start, int limit) {
		List<TParaCompDealTParaBankListResDTO> listPage = new ArrayList<TParaCompDealTParaBankListResDTO>();
		for (int i = start - 1; i < start + limit - 1 && i < list.size(); i++) {
			TParaCompDealTParaBankListResDTO dto = list.get(i);
			StringBuilder action = new StringBuilder();
			action.append("<a onClick=\"Revice('" + dto.getBANK_NAME() + "','" + dto.getBANK() + "','" + dto.getAPP_NAME() + "')\">修改</a> ");
			action.append("<a onClick=\"Delete('" + dto.getBANK_NAME() + "','" + dto.getBANK() + "')\">删除</a> ");
			// 设置操作
			dto.setACTION(action.toString());
			
			listPage.add(dto);
		}
		return listPage;
	}
}
