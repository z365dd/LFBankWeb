package com.adtec.comp.sign.tec.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.ParaSubBusiAddReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiAddResDTO;
import com.adtec.comp.sign.dto.ParaSubBusiDelReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiDelResDTO;
import com.adtec.comp.sign.dto.ParaSubBusiModReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiModResDTO;
import com.adtec.comp.sign.dto.ParaSubBusiPubDTO;
import com.adtec.comp.sign.dto.ParaSubBusiPubListDTO;
import com.adtec.comp.sign.dto.ParaSubBusiQryReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiQryResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.service.OfficeService;

@Service
public class SignSubBusiService extends BaseService{
	
	@Autowired
	private OfficeService officeService;
	

	/**
	 * 子业务查询
	 */
	public IDataset qry(ParaSubBusiQryReqDTO reqBody, int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaSubBusiQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaSubBusiQry", req, ParaSubBusiQryResDTO.class,null);
		ParaSubBusiQryResDTO resBody = (ParaSubBusiQryResDTO) res.getBODY();
		List<ParaSubBusiPubListDTO> tempList = new ArrayList<ParaSubBusiPubListDTO>();
		List<ParaSubBusiPubListDTO> resList = new ArrayList<ParaSubBusiPubListDTO>();
		int total = 0;
		if(resBody!=null){
			List<ParaSubBusiPubDTO> list = resBody.getBUSI_LIST();
			if (list != null  && list.size()>0) {
				for(ParaSubBusiPubDTO busiDTO : list){
					List<ParaSubBusiPubListDTO> subList = busiDTO.getSUB_BUSI_LIST();
					for(ParaSubBusiPubListDTO subBusiDTO : subList){
						ParaSubBusiPubListDTO tempDTO = new ParaSubBusiPubListDTO();
						tempDTO.setCOMP_NO(reqBody.getCOMP_NO());
						tempDTO.setBUSI_NO(busiDTO.getBUSI_NO());
						tempDTO.setBUSI_NAME(busiDTO.getBUSI_NAME());
						tempDTO.setSUB_BUSI_NO(subBusiDTO.getSUB_BUSI_NO());
						tempDTO.setSUB_BUSI_NAME(subBusiDTO.getSUB_BUSI_NAME());
						tempDTO.setOPEN_STAT(subBusiDTO.getOPEN_STAT());
						tempDTO.setMNG_BRCH(subBusiDTO.getMNG_BRCH());
						tempDTO.setBUSI_BRCH(subBusiDTO.getBUSI_BRCH());
						tempDTO.setCLR_BRCH(subBusiDTO.getCLR_BRCH());
						tempDTO.setSIGN_FLG(subBusiDTO.getSIGN_FLG());
						tempList.add(tempDTO);
					}
				}
			}
			total = tempList.size();
			if(limit > 0){
				for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
					resList.add(tempList.get(i));
				}
				//设置操作
				action(resList);
				responseData = DatasetService.getInstace().getDataset(resList, ParaSubBusiPubListDTO.class);
				responseData.setTotalCount(total);
			}else{
				responseData = DatasetService.getInstace().getDataset(tempList, ParaSubBusiPubListDTO.class);
				responseData.setTotalCount(total);
			}
		}
		
		return responseData;
	}
	
	/**
	 * 子业务新增
	 */
	public IDataset add(ParaSubBusiAddReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaSubBusiAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaSubBusiAdd", req, ParaSubBusiAddResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaSubBusiAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 子业务修改
	 */
	public IDataset mod(ParaSubBusiModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaSubBusiMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaSubBusiMod", req, ParaSubBusiModResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaSubBusiModResDTO.class);
		return responseData;
	}
	
	/**
	 * 子业务删除
	 */
	public IDataset del(ParaSubBusiDelReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaSubBusiDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaSubBusiDel", req, ParaSubBusiDelResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaSubBusiDelResDTO.class);
		return responseData;
	}
	
	private void action(List<ParaSubBusiPubListDTO> list){
		for(ParaSubBusiPubListDTO tempDTO : list){
			StringBuilder action = new StringBuilder();
			
			//查询管理机构信息
			Office office = getBrchByCode(tempDTO.getMNG_BRCH());
			if(office!=null){
				tempDTO.setMNG_BRCH_NAME(office.getName());
				tempDTO.setMNG_BRCH(office.getId());
			}
			
			//查询业务机构信息
			office = getBrchByCode(tempDTO.getBUSI_BRCH());
			if(office!=null){
				tempDTO.setBUSI_BRCH_NAME(office.getName());
				tempDTO.setBUSI_BRCH(office.getId());
			}
			
			//查询清算机构信息
			office = getBrchByCode(tempDTO.getCLR_BRCH());
			if(office!=null){
				tempDTO.setCLR_BRCH_NAME(office.getName());
				tempDTO.setCLR_BRCH(office.getId());
			}
			
			if("1".equals(tempDTO.getOPEN_STAT())){
				action.append("<a href='#' onClick=\"Detail('" +tempDTO.getCOMP_NO()+ "','" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getBUSI_NAME()+ "','" +tempDTO.getSUB_BUSI_NO()+ "',"
						+ "'" +tempDTO.getSUB_BUSI_NAME()+ "','" +tempDTO.getOPEN_STAT()+ "','" +tempDTO.getMNG_BRCH()+ "','" +tempDTO.getMNG_BRCH_NAME()+ "','" +tempDTO.getBUSI_BRCH()+ "',"
						+ "'" +tempDTO.getBUSI_BRCH_NAME()+ "','" +tempDTO.getCLR_BRCH()+ "','" +tempDTO.getCLR_BRCH_NAME()+ "','" +tempDTO.getSIGN_FLG()+ "')\">详细</a> ");
				action.append("<a href='#' onClick=\"Revice('" +tempDTO.getCOMP_NO()+ "','" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getBUSI_NAME()+ "','" +tempDTO.getSUB_BUSI_NO()+ "',"
						+ "'" +tempDTO.getSUB_BUSI_NAME()+ "','" +tempDTO.getOPEN_STAT()+ "','" +tempDTO.getMNG_BRCH()+ "','" +tempDTO.getMNG_BRCH_NAME()+ "','" +tempDTO.getBUSI_BRCH()+ "',"
						+ "'" +tempDTO.getBUSI_BRCH_NAME()+ "','" +tempDTO.getCLR_BRCH()+ "','" +tempDTO.getCLR_BRCH_NAME()+ "','" +tempDTO.getSIGN_FLG()+ "')\">修改</a> ");
				action.append("<a href='#' onClick=\"Delete('" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getSUB_BUSI_NO()+ "')\">删除</a> ");
				tempDTO.setOPEN_STAT("开通");
			}else if("2".equals(tempDTO.getOPEN_STAT())){
				action.append("<a href='#' onClick=\"Detail('" +tempDTO.getCOMP_NO()+ "','" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getBUSI_NAME()+ "','" +tempDTO.getSUB_BUSI_NO()+ "',"
						+ "'" +tempDTO.getSUB_BUSI_NAME()+ "','" +tempDTO.getOPEN_STAT()+ "','" +tempDTO.getMNG_BRCH()+ "','" +tempDTO.getMNG_BRCH_NAME()+ "','" +tempDTO.getBUSI_BRCH()+ "',"
						+ "'" +tempDTO.getBUSI_BRCH_NAME()+ "','" +tempDTO.getCLR_BRCH()+ "','" +tempDTO.getCLR_BRCH_NAME()+ "','" +tempDTO.getSIGN_FLG()+ "')\">详细</a> ");
				action.append("<a href='#' onClick=\"Revice('" +tempDTO.getCOMP_NO()+ "','" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getBUSI_NAME()+ "','" +tempDTO.getSUB_BUSI_NO()+ "',"
						+ "'" +tempDTO.getSUB_BUSI_NAME()+ "','" +tempDTO.getOPEN_STAT()+ "','" +tempDTO.getMNG_BRCH()+ "','" +tempDTO.getMNG_BRCH_NAME()+ "','" +tempDTO.getBUSI_BRCH()+ "',"
						+ "'" +tempDTO.getBUSI_BRCH_NAME()+ "','" +tempDTO.getCLR_BRCH()+ "','" +tempDTO.getCLR_BRCH_NAME()+ "','" +tempDTO.getSIGN_FLG()+ "')\">修改</a> ");
				action.append("<a href='#' onClick=\"Delete('" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getSUB_BUSI_NO()+ "')\">删除</a> ");
				tempDTO.setOPEN_STAT("关闭");
			}else if("3".equals(tempDTO.getOPEN_STAT())){
				action.append("<a href='#' onClick=\"Detail('" +tempDTO.getCOMP_NO()+ "','" +tempDTO.getBUSI_NO()+ "','" +tempDTO.getBUSI_NAME()+ "','" +tempDTO.getSUB_BUSI_NO()+ "',"
						+ "'" +tempDTO.getSUB_BUSI_NAME()+ "','" +tempDTO.getOPEN_STAT()+ "','" +tempDTO.getMNG_BRCH()+ "','" +tempDTO.getMNG_BRCH_NAME()+ "','" +tempDTO.getBUSI_BRCH()+ "',"
						+ "'" +tempDTO.getBUSI_BRCH_NAME()+ "','" +tempDTO.getCLR_BRCH()+ "','" +tempDTO.getCLR_BRCH_NAME()+ "','" +tempDTO.getSIGN_FLG()+ "')\">详细</a> ");
				tempDTO.setOPEN_STAT("删除");
			}
			
			tempDTO.setAction(action.toString());
		}
	}
	
	public String getBrchCodeById(String id){
		Office office = officeService.get(id);
		return office.getBrchCode();
	}
	
	public Office getBrchByCode(String code){
		Office office = new Office();
		office.setBrchCode(code);
		List<Office> list = officeService.findListByCode(office);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/*子业务下拉框查询*/
	public IDataset qrySubBusiNo(ParaSubBusiQryReqDTO reqBody,String signFlg){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignParaSubBusiQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaSubBusiQry", req, ParaSubBusiQryResDTO.class,null);
		ParaSubBusiQryResDTO resBody = (ParaSubBusiQryResDTO) res.getBODY();
		List<ParaSubBusiPubDTO> list = resBody.getBUSI_LIST();
		List<ParaSubBusiPubListDTO> SUB_BUSI_LIST = new ArrayList<ParaSubBusiPubListDTO>();
		if(list!=null&&list.size()>0){
			if(!"".equals(signFlg) && "Y".equals(signFlg)){
				for(int i=0;i<list.size();i++){
					List<ParaSubBusiPubListDTO> subList = list.get(i).getSUB_BUSI_LIST();
					for(ParaSubBusiPubListDTO subBusiDTO : subList){
						if("Y".equals(subBusiDTO.getSIGN_FLG())){
							ParaSubBusiPubListDTO tempDTO = new ParaSubBusiPubListDTO();
							tempDTO.setSUB_BUSI_NO(subBusiDTO.getSUB_BUSI_NO());
							tempDTO.setSUB_BUSI_NAME(subBusiDTO.getSUB_BUSI_NAME());
							SUB_BUSI_LIST.add(tempDTO);
						}
					}
				}
			}else{
				for(int i=0;i<list.size();i++){
					List<ParaSubBusiPubListDTO> subList = list.get(i).getSUB_BUSI_LIST();
					for(ParaSubBusiPubListDTO subBusiDTO : subList){
						ParaSubBusiPubListDTO tempDTO = new ParaSubBusiPubListDTO();
						tempDTO.setSUB_BUSI_NO(subBusiDTO.getSUB_BUSI_NO());
						tempDTO.setSUB_BUSI_NAME(subBusiDTO.getSUB_BUSI_NAME());
						SUB_BUSI_LIST.add(tempDTO);
					}
				}
			}
		}
		responseData = DatasetService.getInstace().getDataset(SUB_BUSI_LIST, ParaSubBusiPubListDTO.class);
		return responseData;
	}
	
}
