/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: SwitchesService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年1月2日 下午7:29:59<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlMngDimVauleQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimVauleQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimVauleQryResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngRuleQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngRuleQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngRuleQryResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngOprQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngOprQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngOprQryResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchAddReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchAddResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchDelReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchDelResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchModReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchModResDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngSwitchQryResDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryListDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaCompQryResDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryListDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSubSvcQryResDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryListDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaSvcQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

import net.sf.json.JSONObject;

/**
 * @author 11093
 *
 */

@Service
public class SwitchesService {
	
	/**
	 * 获取模型号
	 */
	public IDataset modelNo(TParaCompQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaCompQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaCompQry", req, TParaCompQryResDTO.class,null);
				
		TParaCompQryResDTO resBody = (TParaCompQryResDTO) resDTO.getBODY();
		List<TParaCompQryListDTO> list = resBody.getCOMP_LIST();
		responseData = DatasetService.getInstace().getDataset(list, TParaCompQryListDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 获取服务码
	 */
	public IDataset svcCode(TParaSvcQryReqDTO reqBody) {
		// TODO Auto-generated method stub
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSvcQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSvcQry", req, TParaSvcQryResDTO.class,null);
				
		TParaSvcQryResDTO resBody = (TParaSvcQryResDTO) resDTO.getBODY();
		List<TParaSvcQryListDTO> list = resBody.getSVC_LIST();
		responseData = DatasetService.getInstace().getDataset(list, TParaSvcQryListDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 子服务码
	 */
	public IDataset subSvc(TParaSubSvcQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaSubSvcQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaSubSvcQry", req, TParaSubSvcQryResDTO.class,null);
				
		TParaSubSvcQryResDTO resBody = (TParaSubSvcQryResDTO) resDTO.getBODY();
		List<TParaSubSvcQryListDTO> list = new ArrayList<TParaSubSvcQryListDTO>();
		if(resBody.getNUM()>0){
			list = resBody.getSUB_SVC_LIST();
		}
		
		responseData = DatasetService.getInstace().getDataset(list, TParaSubSvcQryListDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 规则下拉
	 */
	public IDataset expName(FCtrlMngRuleQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngRuleQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngRuleQry", req, FCtrlMngRuleQryResDTO.class,null);
				
		FCtrlMngRuleQryResDTO resBody = (FCtrlMngRuleQryResDTO) resDTO.getBODY();
		List<FCtrlMngRuleQryListDTO> list = resBody.getEXP_LIST();
		
		responseData = DatasetService.getInstace().getDataset(list, FCtrlMngRuleQryListDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 维度查询
	 */
	public IDataset dimNo(FCtrlMngDimQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimQry", req, FCtrlMngDimQryResDTO.class,null);
				
		FCtrlMngDimQryResDTO resBody = (FCtrlMngDimQryResDTO) resDTO.getBODY();
		List<FCtrlMngDimQryListDTO> list =  resBody.getDIM_LIST();
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				String compNo = list.get(i).getCOMP_NO();
				String dimKey = list.get(i).getDIM_KEY();
				String tabName = list.get(i).getTAB_NAME();
				//DIM_KEY返回形式:模型号+维度名+表名
				String str = compNo+"##"+dimKey+"##"+tabName;
				list.get(i).setDIM_KEY(str);
				
			}
		}
		responseData = DatasetService.getInstace().getDataset(list, FCtrlMngDimQryListDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 查询预算符
	 */
	public IDataset oprNo(FCtrlMngOprQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngOprQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngOprQry", req, FCtrlMngOprQryResDTO.class,null);
				
		FCtrlMngOprQryResDTO resBody = (FCtrlMngOprQryResDTO) resDTO.getBODY();
		List<FCtrlMngOprQryListDTO> list = resBody.getOPR_LIST();
		
		responseData = DatasetService.getInstace().getDataset(list, FCtrlMngOprQryListDTO.class);
		return responseData;
	}
	
	/**
	 * @param reqBody
	 * @return
	 * 查询维度的值
	 */
	public IDataset dimVal(FCtrlMngDimVauleQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimValueQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimValueQry", req, FCtrlMngDimVauleQryResDTO.class,null);
				
		FCtrlMngDimVauleQryResDTO resBody = (FCtrlMngDimVauleQryResDTO) resDTO.getBODY();
		List<FCtrlMngDimVauleQryListDTO> list = resBody.getDIM_LIST();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				//维度名回显格式:dkey-dkv
				String dkey = list.get(i).getDIM_KEY();
				String dkv = list.get(i).getDIM_KV();
				String str = dkey+"-"+dkv;
				list.get(i).setDIM_KEY(str);
			}
		}
		
		responseData = DatasetService.getInstace().getDataset(list, FCtrlMngDimVauleQryListDTO.class);
		return responseData;
	}

	/**
	 * 根据组件号、服务码、子服务码查询最大执行号
	 */
	public long qrySer(FCtrlMngSwitchQryReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req,"FCtrlMngSwitchQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngSwitchQry", req,FCtrlMngSwitchQryResDTO.class,null);		
		FCtrlMngSwitchQryResDTO resBody = (FCtrlMngSwitchQryResDTO) resDTO.getBODY();
		List<FCtrlMngSwitchQryListDTO> list = new ArrayList<FCtrlMngSwitchQryListDTO>();
		if(resBody!=null){
			list = resBody.getSWITCH_LIST();
			if(list!=null&&list.size()>0){
				int index = list.size()-1;
				return list.get(index).getEXEC_SER();
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}

	/**
	 * @param reqBody
	 * @return
	 * 开关规则新增
	 */
	public IDataset save(FCtrlMngSwitchAddReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngSwitchAdd");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngSwitchAdd", req, FCtrlMngSwitchAddResDTO.class,null);		
		FCtrlMngSwitchAddResDTO resBody = (FCtrlMngSwitchAddResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngSwitchAddResDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @param limit 
	 * @param start 
	 * @return
	 * 查询开关规则
	 */
	public IDataset qry(FCtrlMngSwitchQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead("",req, "FCtrlMngSwitchQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngSwitchQry", req,FCtrlMngSwitchQryResDTO.class,null);		
		FCtrlMngSwitchQryResDTO resBody = (FCtrlMngSwitchQryResDTO) resDTO.getBODY();
		List<FCtrlMngSwitchQryListDTO> list = resBody.getSWITCH_LIST();
		List<FCtrlMngSwitchQryListDTO> listPage = new ArrayList<FCtrlMngSwitchQryListDTO>();
		int total = 0;
		if(list!=null && list.size()>0){
			for(int i=start-1;i<start+limit-1&&i<list.size();i++){
				String EXPR = list.get(i).getEXPR().replace("\"", "&#&");
				String TRANL_EXPR="";
				if(null!=list.get(i).getTRANL_EXPR()){
					TRANL_EXPR = list.get(i).getTRANL_EXPR().replace("\"", "&#&");
				}
				
				String EXPR_DESC = list.get(i).getEXPR_DESC().replace("\"", "&#&");
				list.get(i).setEXPR(EXPR);
				list.get(i).setTRANL_EXPR(TRANL_EXPR);
				list.get(i).setEXPR_DESC(EXPR_DESC);
				listPage.add(list.get(i));
			}
			total = list.size();
			//设置操作
			action(listPage);
		}
		responseData = DatasetService.getInstace().getDataset(listPage, FCtrlMngSwitchQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/*
	 * 设置操作
	 */
	private List<FCtrlMngSwitchQryListDTO> action(List<FCtrlMngSwitchQryListDTO> list) {
		for(FCtrlMngSwitchQryListDTO dto:list){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(dto);
			String strJson=json.toString().replace("\"", "\\\"");
			action.append("<a href=\"JavaScript:void(0);\" onClick='Revice(\"" + strJson +"\")'>修改</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Delete('" +dto.getCOMP_NO()+ "','" + dto.getSVC_CODE()+"','"+dto.getSUB_SVC_CODE()+"','"+dto.getEXEC_SER()+"')\">删除</a>");
			dto.setACTION(action.toString());
		}
		
		for(int i=0;i<list.size();i++){
			String EXPR = list.get(i).getEXPR().replace("&#&", "\"");
			String TRANL_EXPR = list.get(i).getTRANL_EXPR().replace("&#&", "\"");
			String EXPR_DESC = list.get(i).getEXPR_DESC().replace("&#&", "\"");
			list.get(i).setEXPR(EXPR);
			list.get(i).setTRANL_EXPR(TRANL_EXPR);
			list.get(i).setEXPR_DESC(EXPR_DESC);
			//设置序号
			list.get(i).setORDER_NO(i+1);
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
		}
		return list;
	}

	/**
	 * @param reqBody
	 * @return
	 * 规则删除
	 */
	public IDataset Delete(FCtrlMngSwitchDelReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngSwitchDel");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngSwitchDel", req, FCtrlMngSwitchDelResDTO.class,null);		
		FCtrlMngSwitchDelResDTO resBody = (FCtrlMngSwitchDelResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngSwitchDelResDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 修改
	 */
	public IDataset revice(FCtrlMngSwitchModReqDTO reqBody,String reqCompNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngSwitchMod");
		req.getSYS_HEAD().setREQ_COMP_NO(reqCompNo);
		req.getSYS_HEAD().setREQ_MODL_NO(reqCompNo);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngSwitchMod", req, FCtrlMngSwitchModResDTO.class,null);		
		FCtrlMngSwitchModResDTO resBody = (FCtrlMngSwitchModResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FCtrlMngSwitchModResDTO.class);
		return responseData;
	}
}
