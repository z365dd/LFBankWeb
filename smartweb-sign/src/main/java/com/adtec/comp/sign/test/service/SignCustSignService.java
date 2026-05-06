package com.adtec.comp.sign.test.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.SignCustAcctListDTO;
import com.adtec.comp.sign.dto.SignCustBusiListDTO;
import com.adtec.comp.sign.dto.SignCustCanReqDTO;
import com.adtec.comp.sign.dto.SignCustCanResDTO;
import com.adtec.comp.sign.dto.SignCustModReqDTO;
import com.adtec.comp.sign.dto.SignCustModResDTO;
import com.adtec.comp.sign.dto.SignCustQryReqDTO;
import com.adtec.comp.sign.dto.SignCustQryResDTO;
import com.adtec.comp.sign.dto.SignCustSignReq1DTO;
import com.adtec.comp.sign.dto.SignCustSignResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

import net.sf.json.JSONObject;

@Service
public class SignCustSignService extends BaseService{

	
	//客户签约查询
	public IDataset qry(SignCustQryReqDTO reqBody,int start,int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignTranCustQry", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranCustQry", req,SignCustQryResDTO.class,null);
		SignCustQryResDTO resBody = (SignCustQryResDTO) resDTO.getBODY();
		List<SignCustAcctListDTO> list = resBody.getACCT_LIST();
		List<SignCustAcctListDTO> listPage = new ArrayList<SignCustAcctListDTO>();
		int total = 0;
		if(list!=null&&list.size()>0){
			for(int i=start-1;i<start+limit-1 && i<list.size();i++){
				listPage.add(list.get(i));
			}
			Action(listPage);
			total = list.size();
		}
		responseData = DatasetService.getInstace().getDataset(listPage, SignCustAcctListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
		
	}
	
	public void Action(List<SignCustAcctListDTO> list){
		for(SignCustAcctListDTO dto:list){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(dto);
			String strJson = "";
			try {
				strJson = URLEncoder.encode(json.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if("0".equals(dto.getSIGN_STAT())){
				action.append("<a href=\"JavaScript:void(0);\" onClick='detailAction(\"" + strJson +"\")'>详情</a> ");
				action.append("<a href=\"JavaScript:void(0);\" onClick='modAction(\"" + strJson +"\")'>修改</a> ");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +strJson+ "')\" >解约</a>");
				dto.setSIGN_STAT("已签约");
			}else if("1".equals(dto.getSIGN_STAT())){
				action.append("<a href=\"JavaScript:void(0);\" onClick='detailAction(\"" + strJson +"\")'>详情</a> ");
				dto.setSIGN_STAT("已解约");
			}else if("2".equals(dto.getSIGN_STAT())){
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detailAction('" +strJson+ "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +strJson+ "')\" >解约</a>");
				dto.setSIGN_STAT("暂停");
			}
			List<SignCustBusiListDTO> BUSI_LIST = dto.getBUSI_LIST();
			String entrNo = BUSI_LIST.get(0).getENTR_NO();
			String entrName = BUSI_LIST.get(0).getENTR_NAME();
			dto.setENTR_NO(entrNo);
			dto.setENTR_NAME(entrName);
			dto.setACTION(action.toString());
		}
	}
	
	//客户签约新增
	public IDataset add(SignCustSignReq1DTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignTranCustSign");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranCustSign", req,SignCustSignResDTO.class,null);
		SignCustSignResDTO resBody = (SignCustSignResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, SignCustSignResDTO.class);
		return responseData;
	}
	
	//客户签约修改
	public IDataset mod(SignCustModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignTranCustMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranCustMod", req,SignCustModResDTO.class,null);
		SignCustModResDTO resBody = (SignCustModResDTO) resDTO.getBODY();
//		List<SignCustAcctListResDTO> ACCT_LIST = resBody.getACCT_LIST();
		responseData = DatasetService.getInstace().getDataset(resBody, SignCustModResDTO.class);
		return responseData;
	}
	
	//客户解约
	public IDataset del(SignCustCanReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignTranCustCan");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranCustCan", req,SignCustCanResDTO.class,null);
		SignCustCanResDTO resBody = (SignCustCanResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, SignCustCanResDTO.class);
		return responseData;
	}
}
