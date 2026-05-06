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
import com.adtec.comp.ctrl.dto.CtrlLimitParaAddReqDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaAddResDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaDelReqDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaDelResDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaEditReqDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaEditResDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaQryListDTO;
import com.adtec.comp.ctrl.dto.CtrlLimitParaQryReqDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;

import net.sf.json.JSONObject;

/**
 * @author 11093
 *
 */

@Service
public class QuotaService {
	/**
	 * @param reqBody
	 * @return
	 * 模拟查询返回信息
	 */
	private List<CtrlLimitParaQryListDTO> qryM() {
		List<CtrlLimitParaQryListDTO> list = new ArrayList<CtrlLimitParaQryListDTO>();
		
		CtrlLimitParaQryListDTO ctrlLimitParaQryListDTO = new CtrlLimitParaQryListDTO();
		ctrlLimitParaQryListDTO.setMODL_NO("0002");
		ctrlLimitParaQryListDTO.setSVC_CODE("0001000");
		ctrlLimitParaQryListDTO.setSUB_SVC("0003");
		ctrlLimitParaQryListDTO.setEXCT_SER("1");
		ctrlLimitParaQryListDTO.setSER_NO("1");
		ctrlLimitParaQryListDTO.setRULE_EXP("busiNo=12345");
		ctrlLimitParaQryListDTO.setEXP_DESC("中文描述");
		
		ctrlLimitParaQryListDTO.setAMT_LMT_DAY("50");
		ctrlLimitParaQryListDTO.setAMT_LMT_MONTH("100");
		ctrlLimitParaQryListDTO.setAMT_LMT_SEASON("150");
		ctrlLimitParaQryListDTO.setAMT_LMT_YEAR("200");
		ctrlLimitParaQryListDTO.setAMT_LMT_SIGL("300");
		
		ctrlLimitParaQryListDTO.setCNT_LMT_DAY("5");
		ctrlLimitParaQryListDTO.setCNT_LMT_MONTH("10");
		ctrlLimitParaQryListDTO.setCNT_LMT_SEASON("15");
		ctrlLimitParaQryListDTO.setCNT_LMT_YEAR("20");

		list.add(ctrlLimitParaQryListDTO);
		
		CtrlLimitParaQryListDTO ctrlLimitParaQryListDTO1 = new CtrlLimitParaQryListDTO();
		ctrlLimitParaQryListDTO1.setMODL_NO("0033");
		ctrlLimitParaQryListDTO1.setSVC_CODE("0001000");
		ctrlLimitParaQryListDTO1.setSUB_SVC("0002");
		ctrlLimitParaQryListDTO1.setEXCT_SER("2");
		ctrlLimitParaQryListDTO1.setSER_NO("2");
		ctrlLimitParaQryListDTO1.setRULE_EXP("busiNo=123 and entrNo=321");
		ctrlLimitParaQryListDTO1.setEXP_DESC("中文描述");
		
		ctrlLimitParaQryListDTO1.setAMT_LMT_DAY("50");
		ctrlLimitParaQryListDTO1.setAMT_LMT_MONTH("100");
		ctrlLimitParaQryListDTO1.setAMT_LMT_SEASON("150");
		ctrlLimitParaQryListDTO1.setAMT_LMT_YEAR("200");
		ctrlLimitParaQryListDTO1.setAMT_LMT_SIGL("300");
		
		ctrlLimitParaQryListDTO1.setCNT_LMT_DAY("5");
		ctrlLimitParaQryListDTO1.setCNT_LMT_MONTH("10");
		ctrlLimitParaQryListDTO1.setCNT_LMT_SEASON("15");
		ctrlLimitParaQryListDTO1.setCNT_LMT_YEAR("20");
		list.add(ctrlLimitParaQryListDTO1);
		
		CtrlLimitParaQryListDTO ctrlLimitParaQryListDTO2 = new CtrlLimitParaQryListDTO();
		ctrlLimitParaQryListDTO2.setMODL_NO("0202");
		ctrlLimitParaQryListDTO2.setSVC_CODE("0001000");
		ctrlLimitParaQryListDTO2.setSUB_SVC("0001");
		ctrlLimitParaQryListDTO2.setEXCT_SER("3");
		ctrlLimitParaQryListDTO2.setSER_NO("3");
		ctrlLimitParaQryListDTO2.setRULE_EXP("busiNo=1213223 and entrNo=orz or entrNo=bibibi");
		ctrlLimitParaQryListDTO2.setEXP_DESC("中文描述");
		
		ctrlLimitParaQryListDTO2.setAMT_LMT_DAY("50");
		ctrlLimitParaQryListDTO2.setAMT_LMT_MONTH("100");
		ctrlLimitParaQryListDTO2.setAMT_LMT_SEASON("150");
		ctrlLimitParaQryListDTO2.setAMT_LMT_YEAR("200");
		ctrlLimitParaQryListDTO2.setAMT_LMT_SIGL("300");
		
		ctrlLimitParaQryListDTO2.setCNT_LMT_DAY("5");
		ctrlLimitParaQryListDTO2.setCNT_LMT_MONTH("10");
		ctrlLimitParaQryListDTO2.setCNT_LMT_SEASON("15");
		ctrlLimitParaQryListDTO2.setCNT_LMT_YEAR("20");
		list.add(ctrlLimitParaQryListDTO2);
		
		
		return list;
	}
	
	
	

	/**
	 * @param reqBody
	 * @return
	 * 限额规则新增
	 */
	public IDataset save(CtrlLimitParaAddReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		/*ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "CtrlLimitParaAdd");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"CtrlLimitParaAdd", req, CtrlLimitParaAddResDTO.class,null);		
		CtrlLimitParaAddResDTO resBody = (CtrlLimitParaAddResDTO) resDTO.getBODY();*/
		//模拟返回成功
		CtrlLimitParaAddResDTO resBody = new CtrlLimitParaAddResDTO();
		responseData = DatasetService.getInstace().getDataset(resBody, CtrlLimitParaAddResDTO.class);
		return responseData;
		
	}

	/**
	 * @param reqBody
	 * @param limit 
	 * @param start 
	 * @return
	 * 查询限额规则
	 */
	public IDataset qry(CtrlLimitParaQryReqDTO reqBody, int start, int limit) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		/*ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "CtrlLimitParaQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"CtrlLimitParaQry", req,CtrlLimitParaQryResDTO.class,null);		
		CtrlLimitParaQryResDTO resBody = (CtrlLimitParaQryResDTO) resDTO.getBODY();
		List<CtrlLimitParaQryListDTO> list = new ArrayList<CtrlLimitParaQryListDTO>();
		int total = Integer.parseInt(resBody.getLIMIT_LIST_NUM());
		if(total!=0){
			list = resBody.getLIMIT_LIST();
		}*/
		//模拟返回查询信息
		List<CtrlLimitParaQryListDTO> list  = qryM();
		int total = list.size();
		
		//分页
		List<CtrlLimitParaQryListDTO> listPage = new ArrayList<CtrlLimitParaQryListDTO>();
		if (null != list && list.size() > 0) {
			for (int i = start - 1; i < start + limit - 1 && i < total; i++) {
				CtrlLimitParaQryListDTO listDTO = list.get(i);
				listPage.add(list.get(i));
			}
		}
		
		//设置操作
		action(listPage);
		
		responseData = DatasetService.getInstace().getDataset(listPage, CtrlLimitParaQryListDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/*
	 * 设置操作
	 */
	private List<CtrlLimitParaQryListDTO> action(List<CtrlLimitParaQryListDTO> listPage) {
		for(CtrlLimitParaQryListDTO dto:listPage){
			StringBuilder action = new StringBuilder();
			JSONObject json = JSONObject.fromObject(dto);
			String strJson=json.toString();
			action.append("<a  onClick='Revice(\"" + strJson.replace("\"", "\\\"") +"\")'>修改</a> ");
			action.append("<a  onClick=\"Delete('" + dto.getMODL_NO() + "','" + dto.getSVC_CODE() + "','" + dto.getSUB_SVC() + "','" + dto.getRULE_EXP()+ "','" + dto.getSER_NO() + "')\">删除</a>");
			dto.setACTION(action.toString());
		}
		return listPage;
	}

	/**
	 * @param reqBody
	 * @return
	 * 规则删除
	 */
	public IDataset Delete(CtrlLimitParaDelReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		/*ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "CtrlLimitParaDel");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"CtrlLimitParaDel", req, CtrlLimitParaDelResDTO.class,null);		
		CtrlLimitParaDelResDTO resBody = (CtrlLimitParaDelResDTO) resDTO.getBODY();*/
		//模拟返回成功
		CtrlLimitParaDelResDTO resBody = new CtrlLimitParaDelResDTO();
		responseData = DatasetService.getInstace().getDataset(resBody, CtrlLimitParaDelResDTO.class);
		return responseData;
	}

	/**
	 * @param reqBody
	 * @return
	 * 修改
	 */
	public IDataset revice(CtrlLimitParaEditReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		/*ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "CtrlLimitParaEdit");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"CtrlLimitParaEdit", req, CtrlLimitParaEditResDTO.class,null);		
		CtrlLimitParaEditResDTO resBody = (CtrlLimitParaEditResDTO) resDTO.getBODY();*/
		//模拟返回成功
		CtrlLimitParaEditResDTO resBody = new CtrlLimitParaEditResDTO();
		responseData = DatasetService.getInstace().getDataset(resBody, CtrlLimitParaEditResDTO.class);
		return responseData;
	}
	

}
