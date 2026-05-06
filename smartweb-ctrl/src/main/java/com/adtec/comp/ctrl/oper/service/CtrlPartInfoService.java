package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.CommonType;
import com.adtec.comp.ctrl.dto.TParaCompDealTParapartInfoListResDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParapartInfoReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParapartInfoResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class CtrlPartInfoService {
	/**
	 * 查询、新增、修改、删除
	 */
	public IDataset action(TParaCompDealTParapartInfoReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("", req, "TParaCompDealTParaPartInfo", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"TParaCompDealTParaPartInfo", req, TParaCompDealTParapartInfoResDTO.class, null);
		TParaCompDealTParapartInfoResDTO resBody = new TParaCompDealTParapartInfoResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (TParaCompDealTParapartInfoResDTO) resDTO.getBODY();
		}
		List<TParaCompDealTParapartInfoListResDTO> list = new ArrayList<TParaCompDealTParapartInfoListResDTO>();
		if(resBody.getNUM()>0){
			list= resBody.getLIST();
		}
		if (CommonType.QRY.equals(reqBody.getOPER_TP())) {
			// 查询列表
			int total = list.size();
			// 取指定条数，设置操作,转换显示
			List<TParaCompDealTParapartInfoListResDTO> listPage = setAction(list, start, limit);

			responseData = DatasetService.getInstace().getDataset(listPage, TParaCompDealTParapartInfoListResDTO.class);
			responseData.setTotalCount(total);
		}else{
			responseData = DatasetService.getInstace().getDataset(list, TParaCompDealTParapartInfoListResDTO.class);
		}
		return responseData;
	}

	/* 分页取条数 设置查询的操作和显示 */
	private List<TParaCompDealTParapartInfoListResDTO> setAction(List<TParaCompDealTParapartInfoListResDTO> list, int start, int limit) {
		List<TParaCompDealTParapartInfoListResDTO> listPage = new ArrayList<TParaCompDealTParapartInfoListResDTO>();
		for (int i = start - 1; i < start + limit - 1 && i < list.size(); i++) {
			TParaCompDealTParapartInfoListResDTO dto = list.get(i);
			StringBuilder action = new StringBuilder();
			action.append("<a onClick=\"Revice('" + dto.getCOMP_NO() + "','" + dto.getSVC_CODE() + "')\">修改</a> ");
			action.append("<a onClick=\"Delete('" + dto.getCOMP_NO() + "','" + dto.getSVC_CODE() + "')\">删除</a> ");
			// 设置操作
			dto.setACTION(action.toString());
			listPage.add(dto);
		}
		return listPage;
	}
}
