package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.TParaCompQryListDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryResDTO;
import com.adtec.comp.ctrl.dto.TParaSvcAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcAddResDTO;
import com.adtec.comp.ctrl.dto.TParaSvcDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcDelResDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryListDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryResDTO;
import com.adtec.comp.ctrl.dto.TParaSvcModReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcModResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class CtrlSvcService {
	
	/**
	 * 获取模型号
	 * @return
	 */
	public IDataset getModlNo(TParaCompQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req,"FCtrlParaCompQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaCompQry", req, TParaCompQryResDTO.class,null);
		TParaCompQryResDTO resBody = (TParaCompQryResDTO) res.getBODY();
		List<TParaCompQryListDTO> list = new ArrayList<TParaCompQryListDTO>();
		if(resBody.getNUM()>0){
			list = resBody.getCOMP_LIST();
			filterCompNo(list);
		}
		
		responseData = DatasetService.getInstace().getDataset(list,TParaCompQryListDTO.class);
		return responseData;
	}
	
	//对模型号list筛选出流程的记录
	public void filterCompNo(List<TParaCompQryListDTO> list){
		for(int a=0;a<list.size();a++){
			if(!"1".equals(list.get(a).getFLG())){
				list.remove(a);
				a--;
			}
		}
	}
	
	
	/**
	 * 获取服务码下拉框
	 */
	public IDataset getSvcCode(TParaSvcQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req,"FCtrlParaSvcQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSvcQry", req,TParaSvcQryResDTO.class,null);		
		TParaSvcQryResDTO tparaSvcQryResDto = (TParaSvcQryResDTO) resDTO.getBODY();
		List<TParaSvcQryListDTO> tplist = tparaSvcQryResDto.getSVC_LIST();
		
		responseData = DatasetService.getInstace().getDataset(tplist,TParaSvcQryListDTO.class);
		return responseData;
	}

	/**
	 * 查阅服务码
	 */
	public IDataset SvcCodeList(TParaSvcQryReqDTO reqBody,int start,int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlParaSvcQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSvcQry", req,TParaSvcQryResDTO.class,null);		
		TParaSvcQryResDTO tparaSvcQryResDto = (TParaSvcQryResDTO) resDTO.getBODY();
		List<TParaSvcQryListDTO> tplist = tparaSvcQryResDto.getSVC_LIST();
		List<TParaSvcQryListDTO> listPage = new ArrayList<TParaSvcQryListDTO>();
		int num = (int) tparaSvcQryResDto.getNUM();
		if(tplist!=null&&tplist.size()>0){
			for(int i=start-1;i<start+limit-1&&i<tplist.size();i++){
				listPage.add(tplist.get(i));
			}
			Action(listPage);
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TParaSvcQryListDTO.class);
		responseData.setTotalCount(num);
		return responseData;
	}
	
	private List<TParaSvcQryListDTO> Action(List<TParaSvcQryListDTO> list){
		for(TParaSvcQryListDTO dto:list){
			StringBuilder action = new StringBuilder();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateAction('"+dto.getCOMP_NO()+","+dto.getCOMP_NAME()+","+dto.getSVC_CODE()+","+dto.getSVC_DESC()+"')\">修改</a>");
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +dto.getCOMP_NO()+ "," + dto.getSVC_CODE()+"')\">删除</a>");
			dto.setACTION(action.toString());
		}
		return list;
	}
	
	/**
	 * 新增服务码
	 */
	public IDataset SvcAdd(TParaSvcAddReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSvcAdd");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSvcAdd", req, TParaSvcAddResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),TParaSvcAddResDTO.class);
		return responseData;
	}
	/**
	 * 修改服务码
	 */
	public IDataset SvcUpdate(TParaSvcModReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSvcMod");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSvcMod", req, TParaSvcModResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),TParaSvcModResDTO.class);
		return responseData;
	}
	/**
	 * 删除服务码
	 */
	public IDataset SvcDel(TParaSvcDelReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSvcDel");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSvcDel", req, TParaSvcDelResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),TParaSvcDelResDTO.class);
		return responseData;
	}
}
