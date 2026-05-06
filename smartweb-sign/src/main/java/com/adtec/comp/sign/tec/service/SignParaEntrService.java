package com.adtec.comp.sign.tec.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.ParaEntrAddReqDTO;
import com.adtec.comp.sign.dto.ParaEntrAddResDTO;
import com.adtec.comp.sign.dto.ParaEntrDelReqDTO;
import com.adtec.comp.sign.dto.ParaEntrDelResDTO;
import com.adtec.comp.sign.dto.ParaEntrModReqDTO;
import com.adtec.comp.sign.dto.ParaEntrModResDTO;
import com.adtec.comp.sign.dto.ParaEntrPubListDTO;
import com.adtec.comp.sign.dto.ParaEntrQryReqDTO;
import com.adtec.comp.sign.dto.ParaEntrQryResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

import net.sf.json.JSONObject;

@Service
public class SignParaEntrService extends BaseService{
	
	
	/**
	 * 单位查询
	 */
	public IDataset qry(ParaEntrQryReqDTO reqBody, int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaEntrQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaEntrQry", req, ParaEntrQryResDTO.class,null);
		ParaEntrQryResDTO resBody = (ParaEntrQryResDTO) res.getBODY();
		List<ParaEntrPubListDTO> tempList = new ArrayList<ParaEntrPubListDTO>();
		List<ParaEntrPubListDTO> resList = new ArrayList<ParaEntrPubListDTO>();
		int total = 0;
		if(resBody!=null){
			tempList = resBody.getENTR_LIST();
			total = tempList.size();
			if(limit > 0){
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					resList.add(tempList.get(i));
				}
				//设置操作
				action(resList);
				responseData = DatasetService.getInstace().getDataset(resList, ParaEntrPubListDTO.class);
				responseData.setTotalCount(total);
			}else{
				responseData = DatasetService.getInstace().getDataset(tempList, ParaEntrPubListDTO.class);
				responseData.setTotalCount(total);
			}
		}
		
		return responseData;
	}
	
	/**
	 * 单位新增
	 */
	public IDataset add(ParaEntrAddReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaEntrAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaEntrAdd", req, ParaEntrAddResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaEntrAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 单位修改
	 */
	public IDataset mod(ParaEntrModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaEntrMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaEntrMod", req, ParaEntrModResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaEntrModResDTO.class);
		return responseData;
	}
	
	/**
	 * 单位删除
	 */
	public IDataset del(ParaEntrDelReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaEntrDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaEntrDel", req, ParaEntrDelResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaEntrDelResDTO.class);
		return responseData;
	}
	
	private void action(List<ParaEntrPubListDTO> list){
		for(ParaEntrPubListDTO tempDTO : list){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(tempDTO);
			String strJson = "";
			try {
				strJson = URLEncoder.encode(json.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			if("1".equals(tempDTO.getOPEN_STAT())){
				action.append("<a href='#' onClick='Detail(\"" + strJson +"\")'>详细</a> ");
				action.append("<a href='#' onClick='Revice(\"" + strJson +"\")'>修改</a> ");
				action.append("<a href='#' onClick=\"Delete('" +tempDTO.getENTR_NO()+ "')\">删除</a> ");
				tempDTO.setOPEN_STAT("开通");
			}else if("2".equals(tempDTO.getOPEN_STAT())){
				action.append("<a href='#' onClick='Detail(\"" + strJson +"\")'>详细</a> ");
				action.append("<a href='#' onClick='Revice(\"" + strJson +"\")'>修改</a> ");
				action.append("<a href='#' onClick=\"Delete('" +tempDTO.getENTR_NO()+ "')\">删除</a> ");
				tempDTO.setOPEN_STAT("关闭");
			}else if("3".equals(tempDTO.getOPEN_STAT())){
				action.append("<a href='#' onClick='Detail(\"" + strJson +"\")'>详细</a> ");
				tempDTO.setOPEN_STAT("删除");
			}
			
			tempDTO.setAction(action.toString());
		}
	}
	
}
