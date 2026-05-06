package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.TParaSubSvcAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcAddResDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcDelResDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcModReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcModResDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryListDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class SubSvcService extends BaseService{

	/**
	 * 获取子服务码数据(下拉列表)
	 */
	public IDataset getSubSvcCode(TParaSubSvcQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSubSvcQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSubSvcQry", req, TParaSubSvcQryResDTO.class,null);
		TParaSubSvcQryResDTO resBody = (TParaSubSvcQryResDTO) res.getBODY();
		List<TParaSubSvcQryListDTO> list = resBody.getSUB_SVC_LIST();
		responseData = DatasetService.getInstace().getDataset(list, TParaSubSvcQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 查询子服务码列表
	 */
	public IDataset subSvcList(TParaSubSvcQryReqDTO reqBody, int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlParaSubSvcQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSubSvcQry", req, TParaSubSvcQryResDTO.class,null);
		TParaSubSvcQryResDTO resBody = (TParaSubSvcQryResDTO) res.getBODY();
		List<TParaSubSvcQryListDTO> list = resBody.getSUB_SVC_LIST();
		List<TParaSubSvcQryListDTO> listPage = new ArrayList<TParaSubSvcQryListDTO>();
		int total=0;
		if(list!=null){
			for(int i=start-1;i<list.size()&&i<start+limit-1;i++){
				list.get(i).setORDER_NO(i+1);
				listPage.add(list.get(i));
			}
			total = list.size();
			//设置操作
			Action(listPage);
		}
		
		responseData = DatasetService.getInstace().getDataset(listPage, TParaSubSvcQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	private List<TParaSubSvcQryListDTO> Action(List<TParaSubSvcQryListDTO> list){
		for(TParaSubSvcQryListDTO dto:list){
			StringBuilder action = new StringBuilder();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateAction('"+dto.getCOMP_NO()+","+dto.getCOMP_NAME()+","+dto.getSVC_CODE()+","+dto.getSVC_DESC()+","+dto.getSUB_SVC_CODE()+","+dto.getSUB_SVC_DESC()+"')\">修改</a>");
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +dto.getCOMP_NO()+ "," + dto.getSVC_CODE()+","+dto.getSUB_SVC_CODE()+"')\">删除</a>");
			dto.setACTION(action.toString());
		}
		return list;
	}
	
	/**
	 * 新增子服务码
	 */
	public IDataset subSvcAdd(TParaSubSvcAddReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSubSvcAdd");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSubSvcAdd", req, TParaSubSvcAddResDTO.class,null);
		TParaSubSvcAddResDTO resBody = (TParaSubSvcAddResDTO) res.getBODY();
		
		responseData = DatasetService.getInstace().getDataset(resBody, TParaSubSvcAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 修改子服务码
	 */
	public IDataset subSvcUpdate(TParaSubSvcModReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSubSvcMod");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSubSvcMod", req, TParaSubSvcModResDTO.class,null);
		TParaSubSvcModResDTO resBody = (TParaSubSvcModResDTO) res.getBODY();
		
		responseData = DatasetService.getInstace().getDataset(resBody, TParaSubSvcModResDTO.class);
		return responseData;
	}
	
	/**
	 * 删除子服务码
	 */
	public IDataset subSvcDel(TParaSubSvcDelReqDTO reqBody,String reqCompNo){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSubSvcDel");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSubSvcDel", req, TParaSubSvcDelResDTO.class,null);
		TParaSubSvcDelResDTO resBody = (TParaSubSvcDelResDTO) res.getBODY();
		
		responseData = DatasetService.getInstace().getDataset(resBody, TParaSubSvcDelResDTO.class);
		return responseData;
	}
}
