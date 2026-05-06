package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.TParaCompAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompAddResDTO;
import com.adtec.comp.ctrl.dto.TParaCompDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompDelResDTO;
import com.adtec.comp.ctrl.dto.TParaCompModReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompModResDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryListDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class CtrlModlService {

	
	/**
	 * @param reqBody
	 * @param limit 
	 * @param start 
	 * @return
	 * 查询模型组件
	 */
	public IDataset CtrlModlQry(TParaCompQryReqDTO reqBody,int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlParaCompQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaCompQry", req,TParaCompQryResDTO.class,null);		
		TParaCompQryResDTO resBody = (TParaCompQryResDTO) resDTO.getBODY();
		
		List<TParaCompQryListDTO> list = resBody.getCOMP_LIST();
		List<TParaCompQryListDTO> listPage = new ArrayList<TParaCompQryListDTO>();
		int total = 0;
		if(list!=null&&list.size()>0){
			total = list.size();
			for(int i=start-1;i<start+limit-1&&i<list.size();i++){
				listPage.add(list.get(i));
			}
			
			//设置操作
			Action(listPage);
			for(int i=0; i<listPage.size(); i++){
				listPage.get(i).setORDER_NO(i+1);
				if("1".equals(listPage.get(i).getFLG())){
					listPage.get(i).setFLG("流程");
				}else if("2".equals(listPage.get(i).getFLG())){
					listPage.get(i).setFLG("功能");
				}else{
					listPage.get(i).setFLG("技术");
				}
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TParaCompQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	private List<TParaCompQryListDTO> Action(List<TParaCompQryListDTO> list){
		for(TParaCompQryListDTO dto : list){
			StringBuilder action = new StringBuilder();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +dto.getCOMP_NO()+","+dto.getCOMP_NAME()+","+dto.getFLG()+"')\" >修改</a>");
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +dto.getCOMP_NO()+ "')\" >删除</a>");
			dto.setACTION(action.toString());
		}
		return list;
		
	}
	
	/**
	 * 新增模型组件
	 */
	public IDataset CtrlModlAdd(TParaCompAddReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaCompAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaCompAdd", req, TParaCompAddResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),TParaCompAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 修改模型组件
	 */
	public IDataset CtrlModlUpdate(TParaCompModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaCompMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaCompMod", req, TParaCompModResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),TParaCompModResDTO.class);
		return responseData;
	}
	
	/**
	 * 模型组件删除
	 */
	public IDataset CtrlModlDel(TParaCompDelReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaCompDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaCompDel", req, TParaCompDelResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),TParaCompDelResDTO.class);
		return responseData;
	}
}
