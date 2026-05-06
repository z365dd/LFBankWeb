package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthAddResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthDelResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthModResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngAuthQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

import net.sf.json.JSONObject;


@Service
public class EmpowerService {
	
	/**
	 * @param reqBody
	 * @return
	 * 授权规则新增
	 */
	public IDataset save(FCtrlMngAuthAddReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngAuthAdd");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngAuthAdd", req, FCtrlMngAuthAddResDTO.class,null);		
		FCtrlMngAuthAddResDTO resBody = (FCtrlMngAuthAddResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngAuthAddResDTO.class);
		return responseData;
	}

	/**
	 * 查询授权执行号(同一组件号、服务码、子服务码条件下)
	 */
	public long qrySer(FCtrlMngAuthQryReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req,"FCtrlMngAuthQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngAuthQry", req,FCtrlMngAuthQryResDTO.class,null);		
		FCtrlMngAuthQryResDTO resBody = (FCtrlMngAuthQryResDTO) resDTO.getBODY();
		List<FCtrlMngAuthQryListDTO> list = new ArrayList<FCtrlMngAuthQryListDTO>();
		if(resBody!=null){
			list = resBody.getAUTH_LIST();
			int index = list.size()-1;
			return list.get(index).getEXEC_SER();
		}else{
			return 0;
		}
	}
	
	/**
	 * @param reqBody
	 * @param limit 
	 * @param start 
	 * @return
	 * 查询授权规则
	 */
	public IDataset qry(FCtrlMngAuthQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlMngAuthQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngAuthQry", req,FCtrlMngAuthQryResDTO.class,null);		
		FCtrlMngAuthQryResDTO resBody = (FCtrlMngAuthQryResDTO) resDTO.getBODY();
		List<FCtrlMngAuthQryListDTO> list = new ArrayList<FCtrlMngAuthQryListDTO>();
		if(resBody!=null){
			list = resBody.getAUTH_LIST();
		}
		List<FCtrlMngAuthQryListDTO> listPage = new ArrayList<FCtrlMngAuthQryListDTO>();
		int total = 0;
		if(list!=null && list.size()>0){
			for(int i=start-1;i<start+limit-1&&i<list.size();i++){
				list.get(i).setORDER_NO(i+1);
				String EXPR = list.get(i).getEXPR().replace("\"", "&#&");
				String TRANL_EXPR = list.get(i).getTRANL_EXPR();
				if(TRANL_EXPR!=null){
					TRANL_EXPR = TRANL_EXPR.replace("\"", "&#&");
				}
				String EXPR_DESC = list.get(i).getEXPR_DESC().replace("\"", "&#&");
				list.get(i).setEXPR(EXPR);
				list.get(i).setTRANL_EXPR(TRANL_EXPR);
				list.get(i).setEXPR_DESC(EXPR_DESC);
				listPage.add(list.get(i));
			}
			//设置操作
			action(listPage);
			total = list.size();
		}
		
		responseData = DatasetService.getInstace().getDataset(listPage, FCtrlMngAuthQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/*
	 * 设置操作
	 */
	private List<FCtrlMngAuthQryListDTO> action(List<FCtrlMngAuthQryListDTO> list) {
		for(FCtrlMngAuthQryListDTO dto:list){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(dto);
			String strJson=json.toString().replace("\"", "\\\"");
			/*action.append("<a href=\"JavaScript:void(0);\" onClick='Detail(\"" + strJson +"\")'>详细</a> ");*/
			action.append("<a href=\"JavaScript:void(0);\" onClick='Revice(\"" + strJson +"\")'>修改</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Delete('" + dto.getCOMP_NO() + "','" + dto.getSVC_CODE() + "','" + dto.getSUB_SVC_CODE() + "','" + dto.getEXEC_SER() + "')\">删除</a>");
			dto.setACTION(action.toString());
			
			String AUTH_AMT_LIST_STR = json.get("AUTH_AMT_LIST").toString();
			dto.setAUTH_AMT_LIST_STR(AUTH_AMT_LIST_STR);
			
			//规则正常显示
			String EXPR = dto.getEXPR().replace("&#&", "\"");
			String TRANL_EXPR = dto.getTRANL_EXPR();
			if(TRANL_EXPR!=null){
				TRANL_EXPR = TRANL_EXPR.replace("&#&", "\"");
			}
			String EXPR_DESC = dto.getEXPR_DESC().replace("&#&", "\"");
			dto.setEXPR(EXPR);
			dto.setTRANL_EXPR(TRANL_EXPR);
			dto.setEXPR_DESC(EXPR_DESC);
			
			//设置授权模式 中文显示
			String authNo = dto.getAUTH_METH();
			if("01".equals(authNo)){
				dto.setAUTH_METH("有金额授权");
			}else{
				dto.setAUTH_METH("无金额授权");
			}
		}
		/*for(int i=0;i<list.size();i++){*/
			/*String EXPR = list.get(i).getEXPR().replace("&#&", "\"");
			String TRANL_EXPR = list.get(i).getTRANL_EXPR().replace("&#&", "\"");
			String EXPR_DESC = list.get(i).getEXPR_DESC().replace("&#&", "\"");
			list.get(i).setEXPR(EXPR);
			list.get(i).setTRANL_EXPR(TRANL_EXPR);
			list.get(i).setEXPR_DESC(EXPR_DESC);
			
			//设置授权模式 中文显示
			String authNo = list.get(i).getAUTH_METH();
			if("01".equals(authNo)){
				list.get(i).setAUTH_METH("有金额授权");
			}else{
				list.get(i).setAUTH_METH("无金额授权");
			}*/
			/*//设置模型显示形式：模型号-模型名
			String compNO = list.get(i).getCOMP_NO();
			String compNanme = list.get(i).getCOMP_NAME();
			list.get(i).setCOMP_NO(compNO+"-"+compNanme);
			
			String svcCode = list.get(i).getSVC_CODE();
			String svcDesc = list.get(i).getSVC_DESC();
			list.get(i).setSVC_CODE(svcCode+"-"+svcDesc);
			
			String subSvcCode = list.get(i).getSUB_SVC_CODE();
			String subSvcDesc = list.get(i).getSUB_SVC_DESC();
			list.get(i).setSUB_SVC_CODE(subSvcCode+"-"+subSvcDesc);*/
		/*}*/
		return list;
	}


	
	/**
	 * @param reqBody
	 * @return
	 * 规则删除
	 */
	public IDataset Delete(FCtrlMngAuthDelReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngAuthDel");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngAuthDel", req, FCtrlMngAuthDelResDTO.class,null);		
		FCtrlMngAuthDelResDTO resBody = (FCtrlMngAuthDelResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngAuthDelResDTO.class);
		return responseData;
	}

	/*
	 * 授权规则修改
	 */
	public IDataset revice(FCtrlMngAuthModReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngAuthMod");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngAuthMod", req, FCtrlMngAuthModResDTO.class,null);		
		FCtrlMngAuthModResDTO resBody = (FCtrlMngAuthModResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngAuthModResDTO.class);
		return responseData;
	}
}
