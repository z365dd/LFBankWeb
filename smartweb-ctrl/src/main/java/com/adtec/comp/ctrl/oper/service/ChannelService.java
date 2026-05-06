package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.TParaChnlAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlAddResDTO;
import com.adtec.comp.ctrl.dto.TParaChnlDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlDelResDTO;
import com.adtec.comp.ctrl.dto.TParaChnlModReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlModResDTO;
import com.adtec.comp.ctrl.dto.TParaChnlQryListDTO;
import com.adtec.comp.ctrl.dto.TParaChnlQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class ChannelService extends BaseService{
	
	
	/**
	 * 获取渠道(下拉框)
	 */
	public IDataset getChnl(TParaChnlQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaChnlQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaChnlQry", req, TParaChnlQryResDTO.class,null);
		TParaChnlQryResDTO resBody = (TParaChnlQryResDTO) res.getBODY();
		List<TParaChnlQryListDTO> tlist = resBody.getCHNL_LIST();
		
		responseData = DatasetService.getInstace().getDataset(tlist, TParaChnlQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 查询渠道
	 */
	public IDataset chnlQry(TParaChnlQryReqDTO reqBody,int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlParaChnlQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaChnlQry", req, TParaChnlQryResDTO.class,null);
		TParaChnlQryResDTO resBody = (TParaChnlQryResDTO) res.getBODY();
		List<TParaChnlQryListDTO> tlist = resBody.getCHNL_LIST();
		List<TParaChnlQryListDTO> listPage = new ArrayList<TParaChnlQryListDTO>();
		int total = 0;
		if(tlist!=null&&tlist.size()>0){
			total = tlist.size();
			for(int i=start-1;i<start+limit-1&&i<tlist.size();i++){
				listPage.add(tlist.get(i));
			}
			action(listPage);
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TParaChnlQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	private List<TParaChnlQryListDTO> action(List<TParaChnlQryListDTO> list){
		for(int i=0,j=1;i<list.size();i++,j++){
			StringBuilder sb = new StringBuilder();
			sb.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +list.get(i).getCHNL_NO()+","+list.get(i).getCHNL_NAME()+","+list.get(i).getCHNL_TP()+","+list.get(i).getSEQ_CRT_ID()+"')\">修改</a>");
			sb.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +list.get(i).getCHNL_NO()+"')\">删除</a>");
			list.get(i).setORDER_NO(j);
			String CHNL_TP = list.get(i).getCHNL_TP();
			if("01".equals(CHNL_TP)){
				CHNL_TP = "行内渠道";
			}else{
				CHNL_TP = "行外渠道";
			}
			list.get(i).setCHNL_TP(CHNL_TP);
			list.get(i).setACTION(sb.toString());
		}
		return list;
	}
	
	/**
	 * 新增渠道
	 */
	public IDataset chnlAdd(TParaChnlAddReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaChnlAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaChnlAdd", req, TParaChnlAddResDTO.class,null);
		TParaChnlAddResDTO resBody = (TParaChnlAddResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, TParaChnlAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 修改渠道
	 */
	public IDataset chnlMod(TParaChnlModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaChnlMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaChnlMod", req, TParaChnlModResDTO.class,null);
		TParaChnlModResDTO resBody = (TParaChnlModResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, TParaChnlModResDTO.class);
		return responseData;
	}
	
	/**
	 * 删除渠道
	 */
	public IDataset chnlDel(TParaChnlDelReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaChnlDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaChnlDel", req, TParaChnlDelResDTO.class,null);
		TParaChnlDelResDTO resBody = (TParaChnlDelResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, TParaChnlDelResDTO.class);
		return responseData;
	}
}
