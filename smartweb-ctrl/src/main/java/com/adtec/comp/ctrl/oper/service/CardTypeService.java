package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.CommonType;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCardTypeListResDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCardTypeReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCardTypeResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class CardTypeService {
	/**
	 * 查询、新增、修改、删除
	 */
	public IDataset action(TParaCompDealTParaCardTypeReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("", req, "TParaCompDealTParaCardType", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"TParaCompDealTParaCardType", req, TParaCompDealTParaCardTypeResDTO.class, null);
		TParaCompDealTParaCardTypeResDTO resBody = new TParaCompDealTParaCardTypeResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (TParaCompDealTParaCardTypeResDTO) resDTO.getBODY();
		}
		List<TParaCompDealTParaCardTypeListResDTO> list = new ArrayList<TParaCompDealTParaCardTypeListResDTO>();
		if(resBody.getNUM()>0){
			list= resBody.getLIST();
		}
		if (CommonType.QRY.equals(reqBody.getOPER_TP())) {
			// 查询列表
			int total = list.size();
			// 取指定条数，设置操作,转换显示
			List<TParaCompDealTParaCardTypeListResDTO> listPage = setAction(list, start, limit);

			responseData = DatasetService.getInstace().getDataset(listPage, TParaCompDealTParaCardTypeListResDTO.class);
			responseData.setTotalCount(total);
		}else{
			responseData = DatasetService.getInstace().getDataset(list, TParaCompDealTParaCardTypeListResDTO.class);
		}
		return responseData;
	}

	/* 分页取条数 设置查询的操作和显示 */
	private List<TParaCompDealTParaCardTypeListResDTO> setAction(List<TParaCompDealTParaCardTypeListResDTO> list, int start, int limit) {
		List<TParaCompDealTParaCardTypeListResDTO> listPage = new ArrayList<TParaCompDealTParaCardTypeListResDTO>();
		for (int i = start - 1; i < start + limit - 1 && i < list.size(); i++) {
			TParaCompDealTParaCardTypeListResDTO dto = list.get(i);
			StringBuilder action = new StringBuilder();
			action.append("<a onClick=\"Revice('" + dto.getCARD_BIN_NO() + "','" + dto.getCARD_TP() + "','" + dto.getACCT_CARD_FLG() + "','" + dto.getNET_NO() + "','" + dto.getLEGA_NO() + "')\">修改</a> ");
			action.append("<a onClick=\"Delete('" + dto.getCARD_BIN_NO() + "','" + dto.getCARD_TP() + "','" + dto.getACCT_CARD_FLG() + "','" + dto.getNET_NO() + "','" + dto.getLEGA_NO() + "')\">删除</a> ");
			// 设置操作
			dto.setACTION(action.toString());
			//卡类型
			if("00".equals(dto.getCARD_TP())){
				dto.setCARD_TP("磁条借记卡");
			}else if("01".equals(dto.getCARD_TP())){
				dto.setCARD_TP("IC借记卡");
			}else if("10".equals(dto.getCARD_TP())){
				dto.setCARD_TP("磁条贷记卡");
			}else if("11".equals(dto.getCARD_TP())){
				dto.setCARD_TP("IC贷记卡");
			}else if("21".equals(dto.getCARD_TP())){
				dto.setCARD_TP("银联数据IC借记卡（托管IC借记卡）");
			}else if("31".equals(dto.getCARD_TP())){
				dto.setCARD_TP("银联数据IC贷记卡（托管IC贷记卡）");
			}
			//账卡号标志
			if("1".equals(dto.getACCT_CARD_FLG())){
				dto.setACCT_CARD_FLG("账号");
			}else if("2".equals(dto.getACCT_CARD_FLG())){
				dto.setACCT_CARD_FLG("卡");
			}
			listPage.add(dto);
		}
		return listPage;
	}
}
