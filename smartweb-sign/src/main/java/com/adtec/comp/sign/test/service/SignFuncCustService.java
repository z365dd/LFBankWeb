package com.adtec.comp.sign.test.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dao.FSignPipSignRuleDao;
import com.adtec.comp.sign.dao.FSignTParaChnlDao;
import com.adtec.comp.sign.dto.FSignFactQryAcctInfoReqDTO;
import com.adtec.comp.sign.dto.FSignFactQryAcctInfoResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustCanListReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustCanListResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustCanReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustCanResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleListResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustModSimpleResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustPreSignconfReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustQryList1ResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustQryListResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustQryListToTableResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustQryReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustQryResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignFileRsltQryResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignListResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignResDTO;
import com.adtec.comp.sign.entity.FSignPipSignRuleDO;
import com.adtec.comp.sign.entity.FSignTParaChnlDO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

import net.sf.json.JSONObject;

@Service
public class SignFuncCustService {
	
	@Autowired
	private FSignPipSignRuleDao paraLoadDao;
	
	@Autowired
	private FSignTParaChnlDao chnlNoDao;
	
	/**
	 * 数据库多笔查询   获取签约协议类型ID
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FSignPipSignRuleDO> getParaLoadList(FSignPipSignRuleDO obj) {
		return paraLoadDao.list(obj);
	}
	
	/**
	 * 数据库单笔查询,返回签约类型
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public String getSignTp(FSignPipSignRuleDO obj, String ruleId) {
		return paraLoadDao.getSignTp(obj, ruleId);
	}
	
	/**
	 * 数据库单笔查询,返回签约ID名称
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public String getSignName(FSignPipSignRuleDO obj, String ruleId) {
		return paraLoadDao.getSignName(obj, ruleId);
	}
	
	/**
	 * 数据库多笔查询 获取渠道号
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FSignTParaChnlDO> getChnlNoList(FSignTParaChnlDO obj) {
		return chnlNoDao.list(obj);
	}
	
	/**
	 * 列表查询
	 * 
	 * @param reqBody
	 * @param start
	 * @param limit
	 * @return
	 */
	public IDataset qry(FSignFuncCustQryReqDTO reqBody, int start, int limit, String TP) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncSignQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"), "FSignFuncSignQry",
				req, FSignFuncCustQryResDTO.class, null);
		FSignFuncCustQryResDTO resBody = (FSignFuncCustQryResDTO) res.getBODY();
		List<FSignFuncCustQryListResDTO> list = new ArrayList<FSignFuncCustQryListResDTO>();
		if (resBody.getLIST() != null) {
			list = resBody.getLIST();
		}
		int total = list.size();
		// 取指定条数，设置操作,转换显示
		List<FSignFuncCustQryListToTableResDTO> listpage = action(list, start, limit, TP);

		responseData = DatasetService.getInstace().getDataset(listpage, FSignFuncCustQryListToTableResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}

	/* 分页取条数 设置查询的操作和显示 */
	private List<FSignFuncCustQryListToTableResDTO> action(List<FSignFuncCustQryListResDTO> list, int start, int limit,
			String TP) {
		List<FSignFuncCustQryListToTableResDTO> listPage = new ArrayList<FSignFuncCustQryListToTableResDTO>();
		for (int i = start - 1; i < start + limit - 1 && i < list.size(); i++) {
			FSignFuncCustQryListToTableResDTO tableDto = new FSignFuncCustQryListToTableResDTO();
			FSignPipSignRuleDO signRuleDo = new FSignPipSignRuleDO();
			FSignFuncCustQryListResDTO dto = list.get(i);
			JSONObject json = JSONObject.fromObject(dto);
			// String strJson=json.toString().replace("\"", "\\\"");
			String strJson = json.toString();

			FSignFuncCustQryList1ResDTO dtoC = dto.getACCT_NODE().get(0);
			tableDto.setACCT(dtoC.getACCT());
			tableDto.setACCT_NAME(dtoC.getACCT_NAME());
			tableDto.setPHONE_NO(dtoC.getPHONE_NO());

			// 重新设置值
			tableDto.setSIGN_PROT_TP_ID(dto.getSIGN_PROT_TP_ID() + "-" + paraLoadDao.getSignName(signRuleDo,dto.getSIGN_PROT_TP_ID()));
			tableDto.setSIGN_PROT_NO(dto.getSIGN_PROT_NO());
			tableDto.setSIGN_CTRCT_NO(dto.getSIGN_CTRCT_NO());
			tableDto.setOTH_CUST_NO(dto.getOTH_CUST_NO());
			tableDto.setSIGN_STAT(dto.getSIGN_STAT());

			StringBuilder action = new StringBuilder();
			try {
				action.append("<a href=\"javascript:void(0)\" onClick=\"Detail('" + URLEncoder.encode(strJson, "UTF-8") + "')\">详情</a> ");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BaseException("");
			}

			if ("00".equals(dto.getSIGN_STAT())) {
				tableDto.setSIGN_STAT("已签约");
				if ("qry".equals(TP)) {
					try {
						action.append("<a href=\"javascript:void(0)\" onClick=\"Revice('" + URLEncoder.encode(strJson, "UTF-8") + "')\">修改</a> ");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BaseException("");
					}
				}
					action.append("<a href=\"javascript:void(0)\" onClick=\"Can('" + dto.getSER() + "','" + dto.getSIGN_PROT_TP_ID() + "','"
							+ dto.getSIGN_PROT_NO() +  "')\">解约</a> ");
				
			} else if ("10".equals(dto.getSIGN_STAT())) {
				tableDto.setSIGN_STAT("已解约");
			} else if ("20".equals(dto.getSIGN_STAT())) {
				tableDto.setSIGN_STAT("待生效");
				if ("detailQry".equals(TP)) {
					action.append("<a href=\"javascript:void(0)\" onClick=\"Confirm('" + dto.getSER() + "','" + dto.getSIGN_PROT_TP_ID() + "','"
							+ dto.getSIGN_PROT_NO() +  "','" + dto.getOTH_CUST_NO() + "','" + dtoC.getACCT() + "')\">确认</a> ");
				}
			} else {
				tableDto.setSIGN_STAT("未知状态");
			}
			// 设置操作
			tableDto.setACTION(action.toString());
			
			//设置差错类型
			String errTp = dto.getERR_TP();
			if("00".equals(errTp)) {
				tableDto.setERR_TP("初始");
			}else if("01".equals(errTp)) {
				tableDto.setERR_TP("对账正常");
			}else if("11".equals(errTp)) {
				tableDto.setERR_TP("本行签约对方未签约");
			}else if("12".equals(errTp)) {
				tableDto.setERR_TP("本行未签约对方签约");
			}

			listPage.add(tableDto); 
		}
		return listPage;
	}

	/**
	 * 新增
	 * 
	 * @param reqBody
	 * @return
	 */
	public IDataset add(FSignFuncCustSignReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncSign");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"),
				"FSignFuncSign", req, FSignFuncCustSignResDTO.class, null);
		FSignFuncCustSignResDTO resBody = (FSignFuncCustSignResDTO) resDTO.getBODY();
		List<FSignFuncCustSignListResDTO> resList = new ArrayList<FSignFuncCustSignListResDTO>();
		if (resBody.getLIST() != null) {
			resList = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(resList, FSignFuncCustSignListResDTO.class);
		return responseData;
	}
	
	/**
	 * 预签约
	 * 
	 * @param reqBody
	 * @return
	 */
	public IDataset preSign(FSignFuncCustSignReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncPreSign");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"),
				"FSignFuncPreSign", req, FSignFuncCustSignResDTO.class, null);
		FSignFuncCustSignResDTO resBody = (FSignFuncCustSignResDTO) resDTO.getBODY();
		List<FSignFuncCustSignListResDTO> resList = new ArrayList<FSignFuncCustSignListResDTO>();
		if (resBody.getLIST() != null) {
			resList = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(resList, FSignFuncCustSignListResDTO.class);
		return responseData;
	}
	
	/**
	 * 预签约确认
	 * 
	 * @param reqBody
	 * @return
	 */
	public IDataset Confirm(FSignFuncCustPreSignconfReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncPreSignConf");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"),
				"FSignFuncPreSignConf", req, FSignFuncCustSignResDTO.class, null);
		FSignFuncCustSignResDTO resBody = (FSignFuncCustSignResDTO) resDTO.getBODY();
		List<FSignFuncCustSignListResDTO> resList = new ArrayList<FSignFuncCustSignListResDTO>();
		if (resBody.getLIST() != null) {
			resList = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(resList, FSignFuncCustSignListResDTO.class);
		return responseData;
	}

	/**
	 * 修改
	 * 
	 * @param reqBody
	 * @return
	 */
	public IDataset revice(FSignFuncCustModSimpleReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncSignMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"), "FSignFuncSignMod",
				req, FSignFuncCustModSimpleResDTO.class, null);
		FSignFuncCustModSimpleResDTO resBody = (FSignFuncCustModSimpleResDTO) resDTO.getBODY();
		List<FSignFuncCustModSimpleListResDTO> resList = new ArrayList<FSignFuncCustModSimpleListResDTO>();
		if (resBody.getLIST() != null) {
			resList = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(resList, FSignFuncCustModSimpleListResDTO.class);
		return responseData;
	}

	/**
	 * 解约
	 * 
	 * @param reqBody
	 * @return
	 */
	public IDataset Can(FSignFuncCustCanReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncSignCancl");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"), "FSignFuncSignCancl",
				req, FSignFuncCustCanResDTO.class, null);
		FSignFuncCustCanResDTO resBody = (FSignFuncCustCanResDTO) res.getBODY();
		List<FSignFuncCustCanListResDTO> resList = new ArrayList<FSignFuncCustCanListResDTO>();
		if (resBody.getLIST() != null) {
			resList = resBody.getLIST();
		}
		responseData = DatasetService.getInstace().getDataset(resList, FSignFuncCustCanResDTO.class);
		return responseData;
	}
	
	/**
	 * 账户基本信息查询
	 */
	public IDataset acctInfo(FSignFactQryAcctInfoReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		try{
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FActQryAcctInfo");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callServiceNoException("", ParamUtil.getConfig("HOST_PARTID"),
				"FActQryAcctInfo", req, FSignFactQryAcctInfoResDTO.class, null);
		FSignFactQryAcctInfoResDTO resBody = (FSignFactQryAcctInfoResDTO) res.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, FSignFactQryAcctInfoResDTO.class);
		}catch(Exception e){
			throw e;
		}
		return responseData;
	}

}
