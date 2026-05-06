package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlMngDimAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimAddResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimDelResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimModResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class CtrlMngDimService extends BaseService{
	
	/**
	 * 获取维度
	 */
	public IDataset getDim(FCtrlMngDimQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimQry", req, FCtrlMngDimQryResDTO.class,null);
		FCtrlMngDimQryResDTO resBody = (FCtrlMngDimQryResDTO) res.getBODY();
		List<FCtrlMngDimQryListDTO> flist = resBody.getDIM_LIST();
		responseData = DatasetService.getInstace().getDataset(flist, FCtrlMngDimQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 查询维度
	 */
	public IDataset dimQry(FCtrlMngDimQryReqDTO reqBody,int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlMngDimQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimQry", req, FCtrlMngDimQryResDTO.class,null);
		FCtrlMngDimQryResDTO resBody = (FCtrlMngDimQryResDTO) res.getBODY();
		List<FCtrlMngDimQryListDTO> flist = new ArrayList<FCtrlMngDimQryListDTO>();
		List<FCtrlMngDimQryListDTO> listPage = new ArrayList<FCtrlMngDimQryListDTO>();
		int total = 0;
		if(resBody!=null){
			flist = resBody.getDIM_LIST();
			if(flist!=null&&flist.size()>0){
				total = flist.size();
				for(int i=start-1;i<start+limit-1&&i<flist.size();i++){
					listPage.add(flist.get(i));
				}
				action(listPage);
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage, FCtrlMngDimQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
		
	}
	
	//设置操作
	private List<FCtrlMngDimQryListDTO> action(List<FCtrlMngDimQryListDTO> list){
		for(int i=0,j=1;i<list.size();i++,j++){
			StringBuilder sb = new StringBuilder();
			sb.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +list.get(i).getCOMP_NO()+","+list.get(i).getCOMP_NAME()+","+list.get(i).getDIM_KEY()+","+list.get(i).getDIM_DESC()+","+list.get(i).getTAB_NAME()+"')\">修改</a>");
			sb.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +list.get(i).getCOMP_NO()+","+list.get(i).getDIM_KEY()+","+list.get(i).getDIM_DESC()+","+list.get(i).getTAB_NAME()+"')\">删除</a>");
			list.get(i).setORDER_NO(j);
			list.get(i).setACTION(sb.toString());
		}
		return list;
		
	}
	
	/**
	 * 新增维度
	 */
	public IDataset dimAdd(FCtrlMngDimAddReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimAdd");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimAdd", req, FCtrlMngDimAddResDTO.class,null);
		FCtrlMngDimAddResDTO resBody = (FCtrlMngDimAddResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngDimAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 修改维度
	 */
	public IDataset dimMod(FCtrlMngDimModReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimMod");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimMod", req, FCtrlMngDimModResDTO.class,null);
		FCtrlMngDimModResDTO resBody = (FCtrlMngDimModResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngDimModResDTO.class);
		return responseData;
	}
	
	/**
	 * 删除维度
	 */
	public IDataset dimDel(FCtrlMngDimDelReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimDel");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimDel", req, FCtrlMngDimDelResDTO.class,null);
		FCtrlMngDimDelResDTO resBody = (FCtrlMngDimDelResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngDimDelResDTO.class);
		return responseData;
	}
}
