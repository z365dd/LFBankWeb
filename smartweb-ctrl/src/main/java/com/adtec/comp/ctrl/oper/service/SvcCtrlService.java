package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.CommonType;
import com.adtec.comp.ctrl.dto.TParaCompDealTCtrlSvcCtrlListResDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTCtrlSvcCtrlReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTCtrlSvcCtrlResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class SvcCtrlService {
	/**
	 * 查询、新增、修改、删除
	 */
	public IDataset action(TParaCompDealTCtrlSvcCtrlReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("", req, "TParaCompDealTCtrlSvcCtrl", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"TParaCompDealTCtrlSvcCtrl", req, TParaCompDealTCtrlSvcCtrlResDTO.class, null);
		TParaCompDealTCtrlSvcCtrlResDTO resBody = new TParaCompDealTCtrlSvcCtrlResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (TParaCompDealTCtrlSvcCtrlResDTO) resDTO.getBODY();
		}
		List<TParaCompDealTCtrlSvcCtrlListResDTO> list = new ArrayList<TParaCompDealTCtrlSvcCtrlListResDTO>();
		if(resBody.getNUM()>0){
			list= resBody.getLIST();
		}
		if (CommonType.QRY.equals(reqBody.getOPER_TP())) {
			// 查询列表
			int total = list.size();
			// 取指定条数，设置操作,转换显示
			List<TParaCompDealTCtrlSvcCtrlListResDTO> listPage = setAction(list, start, limit);

			responseData = DatasetService.getInstace().getDataset(listPage, TParaCompDealTCtrlSvcCtrlListResDTO.class);
			responseData.setTotalCount(total);
		}else{
			responseData = DatasetService.getInstace().getDataset(list, TParaCompDealTCtrlSvcCtrlListResDTO.class);
		}
		return responseData;
	}

	/* 分页取条数 设置查询的操作和显示 */
	private List<TParaCompDealTCtrlSvcCtrlListResDTO> setAction(List<TParaCompDealTCtrlSvcCtrlListResDTO> list, int start, int limit) {
		List<TParaCompDealTCtrlSvcCtrlListResDTO> listPage = new ArrayList<TParaCompDealTCtrlSvcCtrlListResDTO>();
		for (int i = start - 1; i < start + limit - 1 && i < list.size(); i++) {
			TParaCompDealTCtrlSvcCtrlListResDTO dto = list.get(i);
			StringBuilder action = new StringBuilder();
			action.append("<a onClick=\"Revice('" + dto.getCOMP_NO() + "','" + dto.getSVC_CODE() + "','" + dto.getSTAT() + "')\">修改</a> ");
			action.append("<a onClick=\"Delete('" + dto.getCOMP_NO() + "','" + dto.getSVC_CODE() + "','" + dto.getSTAT() + "')\">删除</a> ");
			// 设置操作
			dto.setACTION(action.toString());
			// 键值类型
			if ("0".equals(dto.getSTAT())) {
				dto.setSTAT("关闭");
			} else if ("1".equals(dto.getSTAT())) {
				dto.setSTAT("开通");
			} else {
				dto.setSTAT("未知状态");
			}
			listPage.add(dto);
		}
		return listPage;
	}
}
