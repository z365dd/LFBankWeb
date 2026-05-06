package com.adtec.comp.sign.tec.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.SignCustCtrlListDTO;
import com.adtec.comp.sign.dto.SignEntrBusiListDTO;
import com.adtec.comp.sign.dto.SignEntrCanReqDTO;
import com.adtec.comp.sign.dto.SignEntrCanResDTO;
import com.adtec.comp.sign.dto.SignEntrChnlListDTO;
import com.adtec.comp.sign.dto.SignEntrListDTO;
import com.adtec.comp.sign.dto.SignEntrModReqDTO;
import com.adtec.comp.sign.dto.SignEntrModResDTO;
import com.adtec.comp.sign.dto.SignEntrQryReqDTO;
import com.adtec.comp.sign.dto.SignEntrQryResDTO;
import com.adtec.comp.sign.dto.SignEntrSignReqDTO;
import com.adtec.comp.sign.dto.SignEntrSignResDTO;
import com.adtec.comp.sign.dto.SignEntrSubBusiListDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

import net.sf.json.JSONObject;

@Service
public class SignEntrSignService extends BaseService{
	
	//模拟数据返回
	public List<SignEntrListDTO> setList(){
		
		/*渠道*/
		SignEntrChnlListDTO chnl1 = new SignEntrChnlListDTO();
		chnl1.setCHNL_NO("0001");
		chnl1.setCHNL_NAME("柜面");
		chnl1.setSIGN_FLG("01");
		SignEntrChnlListDTO chnl2 = new SignEntrChnlListDTO();
		chnl2.setCHNL_NO("0001");
		chnl2.setCHNL_NAME("柜面");
		chnl2.setSIGN_FLG("01");
		List<SignEntrChnlListDTO> CHNL_LIST = new ArrayList<SignEntrChnlListDTO>();
		CHNL_LIST.add(chnl1);
		CHNL_LIST.add(chnl2);
		/*检查信息*/
		SignCustCtrlListDTO ctrl1 = new SignCustCtrlListDTO();
		ctrl1.setKEY("CHKTP");
		ctrl1.setKEY_NAME("是否校验证件类型和号码");
		ctrl1.setKV("N");
		ctrl1.setKV_DESC("Y-是|N-否|");
		SignCustCtrlListDTO ctrl2 = new SignCustCtrlListDTO();
		ctrl2.setKEY("CHKSTAT");
		ctrl2.setKEY_NAME("是否校验客户签约状态");
		ctrl2.setKV("Y");
		ctrl2.setKV_DESC("Y-是|N-否|");
		List<SignCustCtrlListDTO> CTRL_LIST = new ArrayList<SignCustCtrlListDTO>();
		CTRL_LIST.add(ctrl1);
		CTRL_LIST.add(ctrl2);
		/*子业务*/
		SignEntrSubBusiListDTO sub1 = new SignEntrSubBusiListDTO();
		sub1.setSUB_BUSI_NO("1234567890");
		sub1.setSUB_BUSI_NAME("东方东风");
		sub1.setSTR_DATE("20181001");
		sub1.setEND_DATE("20180322");
		sub1.setCHNL_LIST(CHNL_LIST);
		sub1.setCTRL_LIST(CTRL_LIST);
		SignEntrSubBusiListDTO sub2 = new SignEntrSubBusiListDTO();
		sub2.setSUB_BUSI_NO("1212112");
		sub2.setSUB_BUSI_NAME("杀杀杀");
		sub2.setSTR_DATE("20181001");
		sub2.setEND_DATE("20180322");
		sub2.setCHNL_LIST(CHNL_LIST);
		sub2.setCTRL_LIST(CTRL_LIST);
		List<SignEntrSubBusiListDTO> SUB_LIST = new ArrayList<SignEntrSubBusiListDTO>();
		SUB_LIST.add(sub1);
		SUB_LIST.add(sub2);
		/*业务*/
		SignEntrBusiListDTO bu1 = new SignEntrBusiListDTO();
		bu1.setBUSI_NO("00001");
		bu1.setBUSI_NAME("水费1111111");
		bu1.setSUB_BUSI_LIST(SUB_LIST);
		List<SignEntrBusiListDTO> BUSI_LIST = new ArrayList<SignEntrBusiListDTO>();
		BUSI_LIST.add(bu1);
		/*单位*/
		SignEntrListDTO e = new SignEntrListDTO();
		e.setBUSI_LIST(BUSI_LIST);
		e.setCERT_TP("P001");
		e.setCERT_NO("1234567890");
		e.setENTR_NO("1001000000025");
		e.setENTR_NAME("菲儿1");
		e.setLEGA_NO("1001");
		e.setLEGA_NAME("法人1");
		List<SignEntrListDTO> list = new ArrayList<SignEntrListDTO>();
		list.add(e);
		return list;
	}
	
	//单位签约查询
	public IDataset qry(SignEntrQryReqDTO reqBody,int start,int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignTranEntrQry", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrQry", req,SignEntrQryResDTO.class,null);
		SignEntrQryResDTO resBody = new SignEntrQryResDTO();
		if(resDTO.getBODY()!=null){
			resBody = (SignEntrQryResDTO) resDTO.getBODY();
		}
		List<SignEntrListDTO> list = resBody.getENTR_LIST();
		List<SignEntrListDTO> listPage = new ArrayList<SignEntrListDTO>();
		int total = 0;
		if(list!=null && list.size()>0){
			for(int i=start-1;i<start+limit-1&&i<list.size();i++){
				listPage.add(list.get(i));
			}
			Action(listPage);
			total = list.size();
		}
		responseData = DatasetService.getInstace().getDataset(listPage, SignEntrListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
		
	}
	
	//设置Action
	public void Action(List<SignEntrListDTO> list){
//		List<SignEntrListDTO> resList = new ArrayList<SignEntrListDTO>();
		for(SignEntrListDTO dto:list){
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
			}else{
				action.append("<a href=\"JavaScript:void(0);\" onClick='detailAction(\"" + strJson +"\")'>详情</a> ");
				action.append("<a href=\"JavaScript:void(0);\" onClick='modAction(\"" + strJson +"\")'>修改</a> ");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +strJson+ "')\" >解约</a>");
				dto.setSIGN_STAT("暂停");
			}
			dto.setACTION(action.toString());
		}
	}
	
	//单位签约新增
	public IDataset add(SignEntrSignReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignTranEntrSign");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrSign", req,SignEntrSignResDTO.class,null);
		SignEntrSignResDTO resBody = (SignEntrSignResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, SignEntrSignResDTO.class);
		return responseData;
	}
	
	//单位签约修改
	public IDataset mod(SignEntrModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignTranEntrMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrMod", req,SignEntrModResDTO.class,null);
		SignEntrModResDTO resBody = (SignEntrModResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, SignEntrModResDTO.class);
		return responseData;
	}
	
	//单位解约
	public IDataset del(SignEntrCanReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignTranEntrCan");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignTranEntrCan", req,SignEntrCanResDTO.class,null);
		SignEntrCanResDTO resBody = (SignEntrCanResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, SignEntrCanResDTO.class);
		return responseData;
	}
	

}
