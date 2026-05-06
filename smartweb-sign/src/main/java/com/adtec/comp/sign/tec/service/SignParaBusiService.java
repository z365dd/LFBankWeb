package com.adtec.comp.sign.tec.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.ParaBusiAddReqDTO;
import com.adtec.comp.sign.dto.ParaBusiAddResDTO;
import com.adtec.comp.sign.dto.ParaBusiDelReqDTO;
import com.adtec.comp.sign.dto.ParaBusiDelResDTO;
import com.adtec.comp.sign.dto.ParaBusiModReqDTO;
import com.adtec.comp.sign.dto.ParaBusiModResDTO;
import com.adtec.comp.sign.dto.ParaBusiNoCrtReqDTO;
import com.adtec.comp.sign.dto.ParaBusiNoCrtResDTO;
import com.adtec.comp.sign.dto.ParaBusiQryListDTO;
import com.adtec.comp.sign.dto.ParaBusiQryReqDTO;
import com.adtec.comp.sign.dto.ParaBusiQryResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

import net.sf.json.JSONObject;

@Service
public class SignParaBusiService extends BaseService{

	
	//模拟业务查询数据返回
	public List<ParaBusiQryListDTO> reList(){
		List<ParaBusiQryListDTO> list = new ArrayList<ParaBusiQryListDTO>();
		ParaBusiQryListDTO p1 = new ParaBusiQryListDTO();
		p1.setCOMP_NO("999300");
		p1.setCOMP_NAME("支付");
		p1.setBUSI_NO("99930011100");
		p1.setBUSI_NAME("批量代扣业务");
		p1.setOPEN_STAT("1");
		p1.setLEGA_NO("0001");
		p1.setSIGN_FLG("Y");
		p1.setFLG("Y");
		list.add(p1);
		return list;
	}
	
	/**
	 * 业务查询
	 */
	public IDataset qry(ParaBusiQryReqDTO reqBody,int start, int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignParaBusiQry", start, limit);
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiQry", req,ParaBusiQryResDTO.class,null);
		ParaBusiQryResDTO resBody = (ParaBusiQryResDTO) resDTO.getBODY();
		List<ParaBusiQryListDTO> list = resBody.getBUSI_LIST();
		List<ParaBusiQryListDTO> listPage = new ArrayList<ParaBusiQryListDTO>();
		int total = 0;
		if(list!=null&&list.size()>0){
			//分页
			for(int i=start-1; i<start+limit-1&&i<list.size();i++){
				list.get(i).setCOMP_NAME(reqBody.getCOMP_NAME());
				listPage.add(list.get(i));
			}
			setAction(listPage);
			total = list.size();
		}
		responseData = DatasetService.getInstace().getDataset(listPage, ParaBusiQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	//设置操作
	private List<ParaBusiQryListDTO> setAction(List<ParaBusiQryListDTO> list){
		for(ParaBusiQryListDTO dto : list){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(dto);
			String strJson=json.toString().replace("\"", "\\\"");
			//开通状态数字和中文的映射配置
			String openStat = dto.getOPEN_STAT();
			if("1".equals(openStat)){
				action.append("<a href=\"JavaScript:void(0);\" onClick='detailAction(\"" + strJson +"\")'>详细</a> ");
				action.append("<a href=\"JavaScript:void(0);\" onClick='modAction(\"" + strJson +"\")'>修改</a> ");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +dto.getBUSI_NO()+ "')\" >删除</a>");
				dto.setOPEN_STAT("开通");
			}else if("2".equals(openStat)){
				action.append("<a href=\"JavaScript:void(0);\" onClick='detailAction(\"" + strJson +"\")'>详细</a> ");
				action.append("<a href=\"JavaScript:void(0);\" onClick='modAction(\"" + strJson +"\")'>修改</a> ");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delAction('" +dto.getBUSI_NO()+ "')\" >删除</a>");
				dto.setOPEN_STAT("关闭");
			}else if("3".equals(openStat)){
				action.append("<a href=\"JavaScript:void(0);\" onClick='detailAction(\"" + strJson +"\")'>详细</a> ");
				dto.setOPEN_STAT("删除");
			}
			dto.setACTION(action.toString());
		}
		return list;
	}
	
	/**
	 * 业务新增
	 */
	public IDataset add(ParaBusiAddReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignParaBusiAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiAdd", req,ParaBusiAddResDTO.class,null);
		ParaBusiAddResDTO resBody = (ParaBusiAddResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, ParaBusiAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 业务修改
	 */
	public IDataset mod(ParaBusiModReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignParaBusiMod");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiMod", req,ParaBusiModResDTO.class,null);
		ParaBusiModResDTO resBody = (ParaBusiModResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, ParaBusiModResDTO.class);
		return responseData;
	}
	
	/**
	 * 业务删除
	 */
	public IDataset del(ParaBusiDelReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignParaBusiDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignParaBusiDel", req,ParaBusiDelResDTO.class,null);
		ParaBusiDelResDTO resBody = (ParaBusiDelResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, ParaBusiDelResDTO.class);
		return responseData;
	}
	
	/**
	 * 业务新增时生成业务编号
	 */
	public IDataset creatBusiNo(ParaBusiNoCrtReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead("", req, "FSignSignBusiNoCrt");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignBusiNoCrt", req,ParaBusiNoCrtResDTO.class,null);
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
	
}
