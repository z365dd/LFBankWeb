package com.adtec.comp.ctrl.oper.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.CommonType;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCompParaListResDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCompParaReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDealTParaCompParaResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;

import net.sf.json.JSONObject;

@Service
public class CompParaService {
	/**
	 * 查询、新增、修改、删除
	 */
	public IDataset action(TParaCompDealTParaCompParaReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("", req, "TParaCompDealTParaCompPara", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"TParaCompDealTParaCompPara", req, TParaCompDealTParaCompParaResDTO.class, null);
		TParaCompDealTParaCompParaResDTO resBody = new TParaCompDealTParaCompParaResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (TParaCompDealTParaCompParaResDTO) resDTO.getBODY();
		}
		List<TParaCompDealTParaCompParaListResDTO> list = new ArrayList<TParaCompDealTParaCompParaListResDTO>();
		if(resBody.getNUM()>0){
			list= resBody.getLIST();
		}
		if (CommonType.QRY.equals(reqBody.getOPER_TP())) {
			// 查询列表
			int total = list.size();
			// 取指定条数，设置操作,转换显示
			List<TParaCompDealTParaCompParaListResDTO> listPage = setAction(list, start, limit);

			responseData = DatasetService.getInstace().getDataset(listPage, TParaCompDealTParaCompParaListResDTO.class);
			responseData.setTotalCount(total);
		}else{
			responseData = DatasetService.getInstace().getDataset(list, TParaCompDealTParaCompParaListResDTO.class);
		}
		return responseData;
	}

	/* 分页取条数 设置查询的操作和显示 */
	private List<TParaCompDealTParaCompParaListResDTO> setAction(List<TParaCompDealTParaCompParaListResDTO> list, int start, int limit) {
		List<TParaCompDealTParaCompParaListResDTO> listPage = new ArrayList<TParaCompDealTParaCompParaListResDTO>();
		for (int i = start - 1; i < start + limit - 1 && i < list.size(); i++) {
			TParaCompDealTParaCompParaListResDTO dto = list.get(i);
			
			long num =0;
			if(dto.getDYN_LIST()!=null){
				num = dto.getDYN_LIST().size();
			}
			dto.setKEY_NUM(num);
			
			JSONObject json = JSONObject.fromObject(dto);
			String strJson = json.toString();
			StringBuilder action = new StringBuilder();
			try {
				action.append("<a onClick=\"Revice('" + URLEncoder.encode(strJson, "UTF-8") + "')\">修改</a> ");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BaseException("");
			}
			action.append("<a onClick=\"Delete('" + dto.getCOMP_NO() + "')\">删除</a> ");
			// 设置操作
			dto.setACTION(action.toString());
			
			listPage.add(dto);
		}
		return listPage;
	}
}
