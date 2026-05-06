package com.adtec.comp.sign.pub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.ParaBusiNoCrtReqDTO;
import com.adtec.comp.sign.dto.ParaBusiNoCrtResDTO;
import com.adtec.comp.sign.dto.ParaBusiQryListDTO;
import com.adtec.comp.sign.dto.ParaBusiQryReqDTO;
import com.adtec.comp.sign.dto.ParaBusiQryResDTO;
import com.adtec.comp.sign.dto.ParaEntrNoCrtReqDTO;
import com.adtec.comp.sign.dto.ParaEntrNoCrtResDTO;
import com.adtec.comp.sign.dto.ParaEntrPubListDTO;
import com.adtec.comp.sign.dto.ParaEntrQryReqDTO;
import com.adtec.comp.sign.dto.ParaEntrQryResDTO;
import com.adtec.comp.sign.dto.SignChnlQryListDTO;
import com.adtec.comp.sign.dto.SignChnlQryReqDTO;
import com.adtec.comp.sign.dto.SignChnlQryResDTO;
import com.adtec.comp.sign.dto.SignCustItemQryListDTO;
import com.adtec.comp.sign.dto.SignCustItemQryReqDTO;
import com.adtec.comp.sign.dto.SignCustItemQryResDTO;
import com.adtec.comp.sign.dto.SignEntrBusiListDTO;
import com.adtec.comp.sign.dto.SignEntrItemQryListDTO;
import com.adtec.comp.sign.dto.SignEntrItemQryReqDTO;
import com.adtec.comp.sign.dto.SignEntrItemQryResDTO;
import com.adtec.comp.sign.dto.SignEntrListDTO;
import com.adtec.comp.sign.dto.SignEntrQryReqDTO;
import com.adtec.comp.sign.dto.SignEntrQryResDTO;
import com.adtec.comp.sign.dto.SignEntrSubBusiListDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class SignPubService extends BaseService{
	
	/**
	 * 单位编号生成
	 */
	public IDataset crtEntrNo(ParaEntrNoCrtReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignSignEntrNoCrt");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignEntrNoCrt", req, ParaEntrNoCrtResDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(), ParaEntrNoCrtResDTO.class);
		return responseData;
	}
	
	/**
	 * 业务查询
	 */
	public List<Map<String, Object>> listBusiData(String compNo, String stat,String flg,String signFlg) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String[] statArr = stat.split(",");
		ParaBusiQryReqDTO reqBody = new ParaBusiQryReqDTO();
		reqBody.setCOMP_NO(compNo);
		for(int i=0;i<statArr.length;i++){
			reqBody.setOPEN_STAT(statArr[i]);
			
			ReqDTO req = new ReqDTO(reqBody);
			SignUtil.setReqHead("", req, "FSignParaBusiQry", 0, 0);
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiQry", req,ParaBusiQryResDTO.class,null);
			ParaBusiQryResDTO resBody = (ParaBusiQryResDTO) resDTO.getBODY();
			
			List<ParaBusiQryListDTO> busiList = resBody.getBUSI_LIST();
			/**
			 * flg 用于是否需要判断该业务编号下的是否有子业务
			 * signFlg 用于是否需要判断该业务编号下的签约标志
			 */
			//flg和signFlg都不要判断
			if("".equals(flg) && "".equals(signFlg)){
				for(ParaBusiQryListDTO tempDTO:busiList){
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.put("name", tempDTO.getBUSI_NO() + "-" + tempDTO.getBUSI_NAME());
					tempMap.put("BUSI_NO", tempDTO.getBUSI_NO());
					tempMap.put("BUSI_NAME", tempDTO.getBUSI_NAME());
					list.add(tempMap);
				}
			}else if(!"".equals(flg) && "Y".equals(flg) && "".equals(signFlg)){  //只判断flg
				for(ParaBusiQryListDTO tempDTO:busiList){
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					if("Y".equals(tempDTO.getFLG())){
						tempMap.put("name", tempDTO.getBUSI_NO() + "-" + tempDTO.getBUSI_NAME());
						tempMap.put("BUSI_NO", tempDTO.getBUSI_NO());
						tempMap.put("BUSI_NAME", tempDTO.getBUSI_NAME());
						list.add(tempMap);
					}
				}
			}else if(!"".equals(signFlg) && "Y".equals(signFlg) && "".equals(flg)){  //只判断signFLg
				for(ParaBusiQryListDTO tempDTO:busiList){
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					if("Y".equals(tempDTO.getSIGN_FLG())){
						tempMap.put("name", tempDTO.getBUSI_NO() + "-" + tempDTO.getBUSI_NAME());
						tempMap.put("BUSI_NO", tempDTO.getBUSI_NO());
						tempMap.put("BUSI_NAME", tempDTO.getBUSI_NAME());
						list.add(tempMap);
					}
				}
			}else{ //flg和signFlg都要判断
				for(ParaBusiQryListDTO tempDTO:busiList){
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					if("Y".equals(tempDTO.getFLG()) && "Y".equals(tempDTO.getSIGN_FLG())){
						tempMap.put("name", tempDTO.getBUSI_NO() + "-" + tempDTO.getBUSI_NAME());
						tempMap.put("BUSI_NO", tempDTO.getBUSI_NO());
						tempMap.put("BUSI_NAME", tempDTO.getBUSI_NAME());
						list.add(tempMap);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 搜索树 获取签约单位数据
	 * @param busiNo 业务编号
	 * @param subBusiNo 子业务编号
	 * @param acctNo 签约账号
	 * @param stat 业务状态  ---- 多个用  , 隔开
	 * @return list 单位名称和单位编号组成的list
	 */
	public List<Map<String, Object>> listEntrData(String busiNo, String subBusiNo, String acctNo, String stat) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String[] statArr = stat.split(",");
		SignEntrQryReqDTO reqBody = new SignEntrQryReqDTO();
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setACCT(acctNo);
		for(int i=0;i<statArr.length;i++){
			reqBody.setSIGN_STAT(statArr[i]);
			
			ReqDTO req = new ReqDTO(reqBody);
			SignUtil.setReqHead("", req, "FSignTranEntrQry", 0, 0);
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrQry", req,SignEntrQryResDTO.class,null);
			SignEntrQryResDTO resBody = (SignEntrQryResDTO) resDTO.getBODY();
			
			List<SignEntrListDTO> entrList = resBody.getENTR_LIST();
			
			for(SignEntrListDTO tempDTO:entrList){
				HashMap<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("name", tempDTO.getENTR_NO() + "-" + tempDTO.getENTR_NAME());
				tempMap.put("ENTR_NO", tempDTO.getENTR_NO());
				tempMap.put("ENTR_NAME", tempDTO.getENTR_NAME());
				tempMap.put("KEY_VALUE", tempDTO.getENTR_NO());
				tempMap.put("KEY_DESC", tempDTO.getENTR_NAME());
				list.add(tempMap);
			}
		}
		return list;
	}
	
	/**
	 * 搜索树 获取单位维护数据
	 * @param busiNo 业务编号
	 * @param subBusiNo 子业务编号
	 * @param acctNo 签约账号
	 * @param stat 业务状态  ---- 多个用  , 隔开
	 * @return list 单位名称和单位编号组成的list
	 */
	public List<Map<String, Object>> listEntrData(String legaNo, String stat) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String[] statArr = stat.split(",");
		ParaEntrQryReqDTO reqBody = new ParaEntrQryReqDTO();
		reqBody.setLEGA_NO(legaNo);
		for(int i=0;i<statArr.length;i++){
			reqBody.setOPEN_STAT(statArr[i]);
			
			ReqDTO req = new ReqDTO(reqBody);
			SignUtil.setReqHead("", req, "FSignParaEntrQry", 0, 0);
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaEntrQry", req,ParaEntrQryResDTO.class,null);
			ParaEntrQryResDTO resBody = (ParaEntrQryResDTO) resDTO.getBODY();
			
			List<ParaEntrPubListDTO> entrList = resBody.getENTR_LIST();
			
			for(ParaEntrPubListDTO tempDTO:entrList){
				HashMap<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("name", tempDTO.getENTR_NO() + "-" + tempDTO.getENTR_NAME());
				tempMap.put("ENTR_NO", tempDTO.getENTR_NO());
				tempMap.put("ENTR_NAME", tempDTO.getENTR_NAME());
				list.add(tempMap);
			}
		}
		return list;
	}
	
	
	
	/**
	 * 单位检查项查询
	 */
	public IDataset entrItemQry(SignEntrItemQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignSignEntrItemQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignEntrItemQry", req, SignEntrItemQryResDTO.class,null);
		SignEntrItemQryResDTO resBody = new SignEntrItemQryResDTO();
		List<SignEntrItemQryListDTO> LIST = new ArrayList<SignEntrItemQryListDTO>();
		if(res.getBODY()!=null){
			resBody = (SignEntrItemQryResDTO)res.getBODY();
			LIST = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(LIST, SignEntrItemQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 签约渠道查询
	 */
	public IDataset signChnlQry(SignChnlQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FsignSignChnlQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FsignSignChnlQry", req, SignChnlQryResDTO.class,null);
		SignChnlQryResDTO resBody = new SignChnlQryResDTO();
		if(res.getBODY()!=null){
			resBody = (SignChnlQryResDTO)res.getBODY();
		}
		responseData = DatasetService.getInstace().getDataset(resBody.getLIST(), SignChnlQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 客户签约检查项查询
	 */
	public IDataset SignCustItemQry(SignCustItemQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignSignCustItemQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignCustItemQry", req, SignCustItemQryResDTO.class,null);
		SignCustItemQryResDTO resBody = new SignCustItemQryResDTO();
		if(res.getBODY()!=null){
			resBody = (SignCustItemQryResDTO)res.getBODY();
		}
		responseData = DatasetService.getInstace().getDataset(resBody.getLIST(), SignCustItemQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 业务新增时生成业务编号
	 */
	public IDataset creatBusiNo(ParaBusiNoCrtReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignParaBusiNoCrt");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiNoCrt", req,ParaBusiNoCrtResDTO.class,null);
		ParaBusiNoCrtResDTO resBody = (ParaBusiNoCrtResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, ParaBusiNoCrtResDTO.class);
		return responseData;
	}
	
	/**
	 * 获取业务编号(下拉框)
	 */
	public IDataset qryBusiNo(ParaBusiQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignParaBusiQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiQry", req,ParaBusiQryResDTO.class,null);
		ParaBusiQryResDTO resBody = (ParaBusiQryResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody.getBUSI_LIST(), ParaBusiQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 搜索树 获取签约单位签约业务数据
	 * @param entrNo 单位编号
	 * @param busiNo 业务编号
	 * @param stat 业务状态  ---- 多个用  , 隔开
	 * @return list 业务名称和业务编号组成的list
	 */
	public List<Map<String, Object>> listEntrBusi(String entrNo, String busiNo, String stat) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String[] statArr = stat.split(",");
		SignEntrQryReqDTO reqBody = new SignEntrQryReqDTO();
		reqBody.setENTR_NO(entrNo);
		reqBody.setBUSI_NO(busiNo);
		for(int i=0;i<statArr.length;i++){
			reqBody.setSIGN_STAT(statArr[i]);
			
			ReqDTO req = new ReqDTO(reqBody);
			SignUtil.setReqHead("", req, "FSignTranEntrQry", 0, 0);
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			ResDTO resDTO = (ResDTO) httpJsonFactory.callServiceNoException("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrQry", req,SignEntrQryResDTO.class,null);
			
			if(resDTO == null || resDTO.getBODY() == null){
				continue;
			}
			SignEntrQryResDTO resBody = (SignEntrQryResDTO) resDTO.getBODY();
			
			List<SignEntrListDTO> entrList = resBody.getENTR_LIST();
			
			for(SignEntrListDTO tempDTO:entrList){
				for(SignEntrBusiListDTO busiDTO:tempDTO.getBUSI_LIST()){
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.put("name", busiDTO.getBUSI_NO() + "-" + busiDTO.getBUSI_NAME());
					tempMap.put("KEY_VALUE", busiDTO.getBUSI_NO());
					tempMap.put("KEY_DESC", busiDTO.getBUSI_NAME());
					list.add(tempMap);
				}
			}
		}
		return list;
	}
	
	/**
	 * 搜索树 获取签约单位数据
	 * @param entrNo 单位编号
	 * @param busiNo 业务编号
	 * @param subBusiNo 子业务编号
	 * @param stat 业务状态  ---- 多个用  , 隔开
	 * @return list 子业务名称和子业务编号组成的list
	 */
	public IDataset listEntrSubBusi(String entrNo, String busiNo, String subBusiNo, String stat) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		
		String[] statArr = stat.split(",");
		SignEntrQryReqDTO reqBody = new SignEntrQryReqDTO();
		reqBody.setENTR_NO(entrNo);
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		
		List<SignEntrSubBusiListDTO> sunBusiList = new ArrayList<SignEntrSubBusiListDTO>();
		
		for(int i=0;i<statArr.length;i++){
			reqBody.setSIGN_STAT(statArr[i]);
			
			ReqDTO req = new ReqDTO(reqBody);
			SignUtil.setReqHead("", req, "FSignTranEntrQry", 0, 0);
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			ResDTO resDTO = (ResDTO) httpJsonFactory.callServiceNoException("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrQry", req,SignEntrQryResDTO.class,null);
			
			if(resDTO == null || resDTO.getBODY() == null){
				continue;
			}
			
			SignEntrQryResDTO resBody = (SignEntrQryResDTO) resDTO.getBODY();
			
			List<SignEntrListDTO> entrList = resBody.getENTR_LIST();
			
			for(SignEntrListDTO tempDTO:entrList){
				for(SignEntrBusiListDTO busiDTO:tempDTO.getBUSI_LIST()){
					for(SignEntrSubBusiListDTO sunBusiDTO:busiDTO.getSUB_BUSI_LIST()){
						sunBusiList.add(sunBusiDTO);
					}
				}
			}
		}
		
		responseData = DatasetService.getInstace().getDataset(sunBusiList, SignEntrSubBusiListDTO.class);
		return responseData;
	}

}
