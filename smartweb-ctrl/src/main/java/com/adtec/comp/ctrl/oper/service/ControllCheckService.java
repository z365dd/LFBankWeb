package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowAddResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowDelResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowModResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowQryList1DTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngFlowQryResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngTypeQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngTypeQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngTypeQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

import net.sf.json.JSONObject;


@Service
public class ControllCheckService {
	
	
	/**
	 * @param reqBody
	 * @return
	 * 控制检查新增
	 */
	public IDataset save(FCtrlMngFlowAddReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngFlowAdd");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngFlowAdd", req, FCtrlMngFlowAddResDTO.class,null);		
		FCtrlMngFlowAddResDTO resBody = (FCtrlMngFlowAddResDTO) resDTO.getBODY();
		
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngFlowAddResDTO.class);
		return responseData;
	}

	/**
	 * 根据组件号、服务码、自服务码查最大执行号
	 * @return
	 */
	public long qryMaxSer(FCtrlMngFlowQryReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req,"FCtrlMngFlowQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngFlowQry", req,FCtrlMngFlowQryResDTO.class,null);		
		FCtrlMngFlowQryResDTO resBody = (FCtrlMngFlowQryResDTO) resDTO.getBODY();
		List<FCtrlMngFlowQryListDTO> list = new ArrayList<FCtrlMngFlowQryListDTO>();
		if(resBody!=null){
			list = resBody.getFLOW_LIST();
			return list.get(0).getTP_NUM();
		}else{
			return 0;
		}
	}
	
	/**
	 * @param reqBody
	 * @param limit 
	 * @param start 
	 * @return
	 * 查询流程
	 */
	public IDataset qry(FCtrlMngFlowQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlMngFlowQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngFlowQry", req,FCtrlMngFlowQryResDTO.class,null);		
		FCtrlMngFlowQryResDTO resBody = (FCtrlMngFlowQryResDTO) resDTO.getBODY();
		List<FCtrlMngFlowQryListDTO> list = new ArrayList<FCtrlMngFlowQryListDTO>();
		int total = 0;
		List<FCtrlMngFlowQryListDTO> listPage = new ArrayList<FCtrlMngFlowQryListDTO>();
		if(resBody!=null){
			list = resBody.getFLOW_LIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					FCtrlMngFlowQryListDTO listDTO = list.get(i);
					listPage.add(listDTO);
				}
				//设置操作
				action(listPage);
			}
		}
		
		//分页
		/*List<FCtrlMngFlowQryListDTO> listPage = new ArrayList<FCtrlMngFlowQryListDTO>();
		if (list.size() > 0 && list != null) {
			for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
				FCtrlMngFlowQryListDTO listDTO = list.get(i);
				listPage.add(listDTO);
			}
		}*/
		responseData = DatasetService.getInstace().getDataset(listPage, FCtrlMngFlowQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/*
	 * 设置操作
	 */
	private List<FCtrlMngFlowQryListDTO> action(List<FCtrlMngFlowQryListDTO> listPage) {
		for(FCtrlMngFlowQryListDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(dto);
			String strJson=json.toString().replace("\"", "\\\"");
			action.append("<a href=\"JavaScript:void(0);\" onClick='Revice(\"" + strJson +"\")'>修改</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Delete('" +dto.getCOMP_NO()+ "','" + dto.getSVC_CODE()+"','"+dto.getSUB_SVC_CODE()+"')\">删除</a>");
			dto.setACTION(action.toString());
			//设置流程定义
			List<FCtrlMngFlowQryList1DTO> list1 = new ArrayList<FCtrlMngFlowQryList1DTO>();
			list1 = dto.getTYPE_LIST();
			StringBuilder typeAction = new StringBuilder();
			for(int a=0;a<list1.size();a++){
				typeAction.append(list1.get(a).getTP_DESC());
				if(a != list1.size()-1){
					typeAction.append("->");
				}
				/*doAction.append("<a href=\"JavaScript:void(0);\" onClick=\"modAction('" +list1.get(a).getCTRL_TP()+","+list1.get(a).getTP_DESC()+"')\">修改</a>");
				doAction.append("<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +list1.get(a).getORDER_NO()+"')\">删除</a>");
				list1.get(a).setORDER_NO(a+1);
				list1.get(a).setACTION(doAction.toString());*/
			}
			dto.setTYPE_ACTION(typeAction.toString());
		}
		for(int i=0;i<listPage.size();i++){
			listPage.get(i).setORDER_NO(i+1);
		}
		return listPage;
	}


	
	/**
	 * @param reqBody
	 * @return
	 * 流程删除
	 */
	public IDataset Delete(FCtrlMngFlowDelReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngFlowDel");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngFlowDel", req, FCtrlMngFlowDelResDTO.class,null);		
		FCtrlMngFlowDelResDTO resBody = (FCtrlMngFlowDelResDTO) resDTO.getBODY();
		
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngFlowDelResDTO.class);
		return responseData;
	}

	/**
	 * 流程修改
	 */
	public IDataset revice(FCtrlMngFlowModReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngFlowMod");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngFlowMod", req, FCtrlMngFlowModResDTO.class,null);		
		FCtrlMngFlowModResDTO resBody = (FCtrlMngFlowModResDTO) resDTO.getBODY();
		//模拟返回成功
		/*FCtrlMngAuthModResDTO resBody = new FCtrlMngAuthModResDTO();*/
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngFlowModResDTO.class);
		return responseData;
	}

	/**
	 * 获取控制类型加载下拉框
	 * */
	public IDataset ctrlTp(FCtrlMngTypeQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngTypeQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngTypeQry", req, FCtrlMngTypeQryResDTO.class,null);		
		FCtrlMngTypeQryResDTO resBody = (FCtrlMngTypeQryResDTO) resDTO.getBODY();
		List<FCtrlMngTypeQryListDTO> list = new ArrayList<FCtrlMngTypeQryListDTO>();
		if(resBody != null){
			list  = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(list, FCtrlMngTypeQryListDTO.class);
		return responseData;
	}
}
