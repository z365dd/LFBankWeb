package com.adtec.comp.fsvr.om.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlDtlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlQryListResDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlQryResDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlStatsListResDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlStatsReqDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlStatsResDTO;
import com.adtec.comp.fsvr.om.dao.FsvrTPipBusiDao;
import com.adtec.comp.fsvr.om.dao.FsvrTPipCompDao;
import com.adtec.comp.fsvr.om.entity.FsvrTPipBusiDO;
import com.adtec.comp.fsvr.om.entity.FsvrTPipCompDO;
import com.adtec.comp.fsvr.util.FsvrUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;

import net.sf.json.JSONObject;

@Service
public class FileOmService {
	@Autowired
	private FsvrTPipBusiDao busiManageDao;
	@Autowired
	private FsvrTPipCompDao tPipCompDao;
	//获取组件编号信息(91)
	public List<FsvrTPipCompDO> getCompNo(FsvrTPipCompDO obj) {
		return tPipCompDao.list(obj);
	}
	
	//获取业务编号信息(91)
	public List<Map<String, Object>> busiList(FsvrTPipBusiDO reqBody) {
		List<Map<String, Object>> list = new ArrayList();
		List<FsvrTPipBusiDO> busiList = busiManageDao.list(reqBody,0,0);
		for (FsvrTPipBusiDO DO : busiList) {
			HashMap<String, Object> tempMap = new HashMap();
			tempMap.put("name", DO.getBusiNo() + "-" + DO.getBusiName());
			tempMap.put("BUSI_NO", DO.getBusiNo());
			tempMap.put("BUSI_NAME", DO.getBusiName());
			tempMap.put("ENTR_NO", DO.getBusiName());
			list.add(tempMap);
		}
		return list;
	}
	/**
	 * 统计查询
	 * @param reqBody
	 * @param start
	 * @param limit
	 * @return
	 */
	public IDataset getCountQry(TfmngMngJrnlStatsReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead("",req, "TFmngMngJrnlStats",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngMngJrnlStats", req,TfmngMngJrnlStatsResDTO.class,null);		
		TfmngMngJrnlStatsResDTO resBody = (TfmngMngJrnlStatsResDTO) resDTO.getBODY();
		List<TfmngMngJrnlStatsListResDTO> list = new ArrayList<TfmngMngJrnlStatsListResDTO>();
		int total = 0;
		List<TfmngMngJrnlStatsListResDTO> listPage = new ArrayList<TfmngMngJrnlStatsListResDTO>();
		
		if(resBody!=null){
			list = resBody.getLIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					TfmngMngJrnlStatsListResDTO listDTO = list.get(i);
					listPage.add(listDTO);
				}
				//设置操作
				action(listPage,reqBody);
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TfmngMngJrnlStatsListResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	/**
	 *  设置操作
	 * @param reqBody 
	 */
	private void action(List<TfmngMngJrnlStatsListResDTO> listPage, TfmngMngJrnlStatsReqDTO reqBody) {
		for(TfmngMngJrnlStatsListResDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			/*dto.setTRAN_DATE(FsvrUtil.addDate(dto.getTRAN_DATE()));*/
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Detail('" +dto.getFILE_TRAN_STAT()+ "','" +dto.getCOMP_NO()+ "','" +dto.getBUSI_NO()+ "','" +reqBody.getSTR_DATE()+ "','" +reqBody.getEND_DATE()+ "')\">详细</a> ");
			if("02".equals(dto.getFILE_TRAN_STAT())){
				dto.setFILE_TRAN_STAT("成功");
			}else if("03".equals(dto.getFILE_TRAN_STAT())){
				dto.setFILE_TRAN_STAT("失败");
			}
			dto.setACTION(action.toString());
		}
	}
	
	
	/**
	 * 流水查询
	 * @param reqBody
	 * @param start
	 * @param limit
	 * @return
	 */
	public IDataset getFlowQry(TfmngMngJrnlQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead("",req, "TFmngMngJrnlQry",start,limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngMngJrnlQry", req,TfmngMngJrnlQryResDTO.class,null);		
		TfmngMngJrnlQryResDTO resBody = (TfmngMngJrnlQryResDTO) resDTO.getBODY();
		List<TfmngMngJrnlQryListResDTO> list = new ArrayList<TfmngMngJrnlQryListResDTO>();
		int total = 0;
		List<TfmngMngJrnlQryListResDTO> listPage = new ArrayList<TfmngMngJrnlQryListResDTO>();
		
		if(resBody!=null){
			list = resBody.getLIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					TfmngMngJrnlQryListResDTO listDTO = list.get(i);
					listPage.add(listDTO);
				}
				//设置操作
				action(listPage);
			}
		}
		responseData = DatasetService.getInstace().getDataset(listPage, TfmngMngJrnlQryListResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/**
	 *  设置操作
	 * @param reqBody 
	 */
	private void action(List<TfmngMngJrnlQryListResDTO> listPage) {
		for(TfmngMngJrnlQryListResDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			dto.setTRAN_DATE(FsvrUtil.addDate(dto.getTRAN_DATE()));
			action.append("<a href=\"JavaScript:void(0);\" onClick=\"Detail('" +dto.getTRAN_SEQ()+ "')\">详细</a> ");
			dto.setACTION(action.toString());
		}
	}
	
	
	/**
	 * 流水详细
	 * @param reqBody
	 * @return
	 */
	public IDataset getFlowDetail(TfmngMngJrnlDtlQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngMngJrnlDtlQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		
		JSONObject resJSON = httpJsonFactory.callService("TFmngMngJrnlDtlQry",req,"",ParamUtil.getConfig("FSVR_PARTID"),null);
		JSONObject resBody =  (JSONObject)resJSON.get("BODY");
		
		responseData.addColumn("TFmngMngJrnlDtlQryRes");
		responseData.beforeFirst();
		if(!responseData.hasNext()){
			responseData.appendRow();
		}
		responseData.next();
		responseData.updateValue("TFmngMngJrnlDtlQryRes", resBody);
		return responseData;
	}
}
