package com.adtec.comp.fsvr.tec.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimAddModReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimAddModResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDelReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDelResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDtlQryListResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDtlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDtlQryResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimQryListResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimQryResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaAddModReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaAddModResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaDelReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaDelResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaDtlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaDtlQryResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaQryListResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaQryResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaAddModReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaAddModResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaDelReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaDelResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaQryListResDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaQryResDTO;
import com.adtec.comp.fsvr.tec.dao.TfsvrSvrDeponNetParaDao;
import com.adtec.comp.fsvr.tec.dao.TfsvrSvrDeponParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.util.FsvrUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

import net.sf.json.JSONObject;

@Service
public class FileTecService {
	
	@Autowired
	private TfsvrSvrDeponNetParaDao deponNetDao;
	@Autowired
	private TfsvrSvrDeponParaDao deponDao;
	
	/**
	 * 网络区域关系表多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<TfsvrSvrDeponNetParaDO> deponNetQry(TfsvrSvrDeponNetParaDO obj) {
		return deponNetDao.list(obj);
	}
	
	/**
	 * 网络依赖关系表多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<TfsvrSvrDeponParaDO> deponQry(TfsvrSvrDeponParaDO obj) {
		return deponDao.list(obj);
	}

	
	/**
	 * 内部服务新增
	 */
	public IDataset add(TfmngOmngSvrParaAddModReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrParaAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrParaAdd", req, TfmngOmngSvrParaAddModResDTO.class,null);
		TfmngOmngSvrParaAddModResDTO resBody = new TfmngOmngSvrParaAddModResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrParaAddModResDTO) resDTO.getBODY();
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrParaAddModResDTO.class);
		return responseData;
	}
	
	/**
	 * 内部服务修改提交
	 * 状态修改
	 */
	public IDataset revice(TfmngOmngSvrParaAddModReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrParaMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrParaMod", req, TfmngOmngSvrParaAddModResDTO.class,null);		
		TfmngOmngSvrParaAddModResDTO resBody = new TfmngOmngSvrParaAddModResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrParaAddModResDTO) resDTO.getBODY();
		}
		
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrParaAddModResDTO.class);
		return responseData;
	}

	
	/**
	 * 内部服务列表查询
	 */
	public IDataset qry(TfmngOmngSvrParaQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead("",req, "TFmngOmngSvrParaQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrParaQry", req,TfmngOmngSvrParaQryResDTO.class,null);		
		TfmngOmngSvrParaQryResDTO resBody = (TfmngOmngSvrParaQryResDTO) resDTO.getBODY();
		List<TfmngOmngSvrParaQryListResDTO> list = new ArrayList<TfmngOmngSvrParaQryListResDTO>();
		int total = 0;
		List<TfmngOmngSvrParaQryListResDTO> listPage = new ArrayList<TfmngOmngSvrParaQryListResDTO>();
		
		if(resBody!=null){
			list = resBody.getLIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					TfmngOmngSvrParaQryListResDTO listDTO = list.get(i);
					listPage.add(listDTO);
				}
				//设置操作
				action(listPage);
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TfmngOmngSvrParaQryListResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/**
	 *  设置操作
	 */
	private void action(List<TfmngOmngSvrParaQryListResDTO> listPage) {
		for(TfmngOmngSvrParaQryListResDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Detail('" +dto.getFILE_SVR_ID()+ "')\">详细</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Revice('" +dto.getFILE_SVR_ID()+ "')\">修改</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Delete('" +dto.getFILE_SVR_ID()+ "')\">删除</a> ");
			if("01".equals(dto.getSTAT())){
				action.append("<a href=\"JavaScript:void(0);\" onClick=\"ChangeStat('" +dto.getFILE_SVR_ID()+ "','02')\">关闭</a>");
				dto.setSTAT("开通");
			}else if("02".equals(dto.getSTAT())){
				action.append("<a href=\"JavaScript:void(0);\" onClick=\"ChangeStat('" +dto.getFILE_SVR_ID()+ "','01')\">开通</a>");
				dto.setSTAT("关闭");
			}
			dto.setACTION(action.toString());
		}
	}

	
	
	/**
	 * 内部服务 详细查询
	 */
	public IDataset getDetail(TfmngOmngSvrParaDtlQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrParaDtlQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrParaDtlQry", req, TfmngOmngSvrParaDtlQryResDTO.class,null);		
		TfmngOmngSvrParaDtlQryResDTO resBody = new TfmngOmngSvrParaDtlQryResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrParaDtlQryResDTO) resDTO.getBODY();
		}
		/*JSONObject json =JSONObject.fromObject(resDTO);
		
		JSONObject jsonBody =(JSONObject) json.get("BODY");
		if(jsonBody!=null){
			JSONArray array = (JSONArray) jsonBody.get("LIST");
			if(array!=null){
				List<TfmngOmngSvrParaAddModListReqDTO>listDto= array.toList(array, TfmngOmngSvrParaAddModListReqDTO.class);
				resBody.setLIST(listDto);
			}
		}*/
		JSONObject json =JSONObject.fromObject(resBody);
		
		responseData = DatasetService.getInstace().getDataset(json);
		return responseData;
	}

	
	/**
	 * 内部服务 删除
	 */
	public IDataset Delete(TfmngOmngSvrParaDelReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrParaDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrParaDel", req, TfmngOmngSvrParaDelResDTO.class,null);		
		TfmngOmngSvrParaDelResDTO resBody = new TfmngOmngSvrParaDelResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrParaDelResDTO) resDTO.getBODY();
		}
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrParaDelResDTO.class);
		return responseData;
	}

	
	/**
	 * 端口管理 新增
	 * @param reqBody
	 * @return
	 */
	public IDataset portAdd(TfmngOmngSvrPortParaAddModReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrPortParaAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrPortParaAdd", req, TfmngOmngSvrPortParaAddModResDTO.class,null);
		TfmngOmngSvrPortParaAddModResDTO resBody = new TfmngOmngSvrPortParaAddModResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrPortParaAddModResDTO) resDTO.getBODY();
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrPortParaAddModResDTO.class);
		return responseData;
	}
	
	/**
	 * 端口管理 修改
	 * @param reqBody
	 * @return
	 */
	public IDataset portRevice(TfmngOmngSvrPortParaAddModReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrPortParaMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrPortParaMod", req, TfmngOmngSvrPortParaAddModResDTO.class,null);
		TfmngOmngSvrPortParaAddModResDTO resBody = new TfmngOmngSvrPortParaAddModResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrPortParaAddModResDTO) resDTO.getBODY();
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrPortParaAddModResDTO.class);
		return responseData;
	}

	/**
	 * 端口列表查询
	 * @param reqBody
	 * @param start
	 * @param limit
	 * @return
	 */
	public IDataset portQry(TfmngOmngSvrPortParaQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead("",req, "TFmngOmngSvrPortParaQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrPortParaQry", req,TfmngOmngSvrPortParaQryResDTO.class,null);		
		TfmngOmngSvrPortParaQryResDTO resBody = (TfmngOmngSvrPortParaQryResDTO) resDTO.getBODY();
		List<TfmngOmngSvrPortParaQryListResDTO> list = new ArrayList<TfmngOmngSvrPortParaQryListResDTO>();
		int total = 0;
		List<TfmngOmngSvrPortParaQryListResDTO> listPage = new ArrayList<TfmngOmngSvrPortParaQryListResDTO>();
		
		if(resBody!=null){
			list = resBody.getLIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					TfmngOmngSvrPortParaQryListResDTO listDTO = list.get(i);
					listPage.add(listDTO);
				}
				//设置操作
				portAction(listPage);
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TfmngOmngSvrPortParaQryListResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}

	/**
	 * 端口管理 查询设置操作
	 * @param listPage
	 */
	private void portAction(List<TfmngOmngSvrPortParaQryListResDTO> listPage) {
		for(TfmngOmngSvrPortParaQryListResDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Revice('" +dto.getCOMP_NO()+ "','"+dto.getPORT()+"')\">修改</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Delete('" +dto.getCOMP_NO()+ "')\">删除</a> ");
			dto.setACTION(action.toString());
		}
	}

	
	/**
	 * 端口管理 删除
	 * @param reqBody
	 * @return
	 */
	public IDataset portDelete(TfmngOmngSvrPortParaDelReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrPortParaDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrPortParaDel", req, TfmngOmngSvrPortParaDelResDTO.class,null);		
		TfmngOmngSvrPortParaDelResDTO resBody = new TfmngOmngSvrPortParaDelResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrPortParaDelResDTO) resDTO.getBODY();
		}
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrPortParaDelResDTO.class);
		return responseData;
	}

	/**
	 * 维度新增
	 * @param reqBody
	 * @return
	 */
	public IDataset dimensionAdd(TfmngOmngSvrDimAddModReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrDimAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrDimAdd", req, TfmngOmngSvrDimAddModResDTO.class,null);
		TfmngOmngSvrDimAddModResDTO resBody = new TfmngOmngSvrDimAddModResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrDimAddModResDTO) resDTO.getBODY();
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrDimAddModResDTO.class);
		return responseData;
	}

	/**
	 * 维度修改
	 * @param reqBody
	 * @return
	 */
	public IDataset dimensionRevice(TfmngOmngSvrDimAddModReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrDimMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrDimMod", req, TfmngOmngSvrDimAddModResDTO.class,null);
		TfmngOmngSvrDimAddModResDTO resBody = new TfmngOmngSvrDimAddModResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrDimAddModResDTO) resDTO.getBODY();
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrDimAddModResDTO.class);
		return responseData;
	}

	/**
	 * 维度查询
	 * @param reqBody
	 * @param start
	 * @param limit
	 * @return
	 */
	public IDataset conDimensionQry(TfmngOmngSvrDimQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead("",req, "TFmngOmngSvrDimQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrDimQry", req,TfmngOmngSvrDimQryResDTO.class,null);		
		TfmngOmngSvrDimQryResDTO resBody = (TfmngOmngSvrDimQryResDTO) resDTO.getBODY();
		List<TfmngOmngSvrDimQryListResDTO> list = new ArrayList<TfmngOmngSvrDimQryListResDTO>();
		int total = 0;
		List<TfmngOmngSvrDimQryListResDTO> listPage = new ArrayList<TfmngOmngSvrDimQryListResDTO>();
		
		if(resBody!=null){
			list = resBody.getLIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					TfmngOmngSvrDimQryListResDTO listDTO = list.get(i);
					listPage.add(listDTO);
				}
				//设置操作
				dimensionAction(listPage);
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage,TfmngOmngSvrDimQryListResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}

	/**
	 * 维度查询设置操作
	 * @param listPage
	 */
	private void dimensionAction(List<TfmngOmngSvrDimQryListResDTO> listPage) {
		for(TfmngOmngSvrDimQryListResDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Detail('" +dto.getBUSI_NO()+ "','"+dto.getENTR_NO()+"','"+dto.getCHNL_NO()+"','"+dto.getLEGA_NO()+"','"+dto.getTRAN_CODE()+"','"+dto.getDEF_VAL()+"')\">详细</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Revice('" +dto.getBUSI_NO()+ "','"+dto.getENTR_NO()+"','"+dto.getCHNL_NO()+"','"+dto.getLEGA_NO()+"','"+dto.getTRAN_CODE()+"','"+dto.getDEF_VAL()+"')\">修改</a> ");
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Delete('" +dto.getBUSI_NO()+ "','"+dto.getENTR_NO()+"','"+dto.getCHNL_NO()+"','"+dto.getLEGA_NO()+"','"+dto.getTRAN_CODE()+"','"+dto.getDEF_VAL()+"')\">删除</a> ");
			if("01".equals(dto.getSTAT())){
				action.append("<a href=\"JavaScript:void(0);\" onClick=\"ChangeStat('" +dto.getBUSI_NO()+ "','"+dto.getENTR_NO()+"','"+dto.getCHNL_NO()+"','"+dto.getLEGA_NO()+"','"+dto.getTRAN_CODE()+"','"+dto.getDEF_VAL()+"','"+dto.getFILE_SVR_NO()+"','02')\">关闭</a>");
				dto.setSTAT("开通");
			}else if("02".equals(dto.getSTAT())){
				action.append("<a href=\"JavaScript:void(0);\" onClick=\"ChangeStat('" +dto.getBUSI_NO()+ "','"+dto.getENTR_NO()+"','"+dto.getCHNL_NO()+"','"+dto.getLEGA_NO()+"','"+dto.getTRAN_CODE()+"','"+dto.getDEF_VAL()+"','"+dto.getFILE_SVR_NO()+"','01')\">开通</a>");
				dto.setSTAT("关闭");
			}
			dto.setACTION(action.toString());
		}
	}

	/**
	 * 维度详细查询
	 * @param reqBody
	 * @return
	 */
	public IDataset dimensionGetDetail(TfmngOmngSvrDimDtlQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrDimDtlQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrDimDtlQry", req, TfmngOmngSvrDimDtlQryResDTO.class,null);		
		TfmngOmngSvrDimDtlQryResDTO resBody = new TfmngOmngSvrDimDtlQryResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrDimDtlQryResDTO) resDTO.getBODY();
			reToString(resBody);
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrDimDtlQryResDTO.class);
		return responseData;
	}
	
	/**
	 * 转换list为String ;分隔
	 */
	private void reToString(TfmngOmngSvrDimDtlQryResDTO resBody){
		List<TfmngOmngSvrDimDtlQryListResDTO> list = resBody.getLIST();
		StringBuilder compNo = new StringBuilder();
		StringBuilder compName = new StringBuilder();
		for(int a=0;a<list.size();a++){
			if(a==list.size()-1){
				compNo.append(list.get(a).getCOMP_NO());
				compName.append(list.get(a).getCOMP_NAME());
			}else{
				compNo.append(list.get(a).getCOMP_NO()).append(";");
				compName.append(list.get(a).getCOMP_NAME()).append(";");
			}
		}
		resBody.setCOMP_NAME(compName.toString());
		resBody.setCOMP_NO(compNo.toString());
	}

	
	/**
	 * 维度删除
	 * @param reqBody
	 * @return
	 */
	public IDataset dimensionDelete(TfmngOmngSvrDimDelReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrDimDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrDimDel", req, TfmngOmngSvrDimDelResDTO.class,null);		
		TfmngOmngSvrDimDelResDTO resBody = new TfmngOmngSvrDimDelResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngOmngSvrDimDelResDTO) resDTO.getBODY();
		}
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngOmngSvrDimDelResDTO.class);
		return responseData;
	}

	public IDataset qryFileSvr(TfmngOmngSvrParaQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngOmngSvrParaQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngOmngSvrParaQry", req,TfmngOmngSvrParaQryResDTO.class,null);		
		TfmngOmngSvrParaQryResDTO resBody = (TfmngOmngSvrParaQryResDTO) resDTO.getBODY();
		List<TfmngOmngSvrParaQryListResDTO> list = new ArrayList<TfmngOmngSvrParaQryListResDTO>();
		
		if(resBody!=null){
			list = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(list, TfmngOmngSvrParaQryListResDTO.class);
		return responseData;
	}
}
