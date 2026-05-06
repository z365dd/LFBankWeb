package com.adtec.comp.sign.om.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dto.SignStatsListResDTO;
import com.adtec.comp.sign.dto.SignStatsReqDTO;
import com.adtec.comp.sign.dto.SignStatsResDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class SignStatsService extends BaseService{

	/**
	 * 统计信息查询
	 */
	public IDataset qry(SignStatsReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignSignStats");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignSignStats", req, SignStatsResDTO.class,null);
		SignStatsResDTO resBody = (SignStatsResDTO) res.getBODY();
		List<SignStatsListResDTO> list = new ArrayList<SignStatsListResDTO>();
		int total = 0;
		if(resBody!=null){
			list = resBody.getLIST();
			if(list!=null&&list.size()>0){
				total = list.size();
			}
		}
		responseData = DatasetService.getInstace().getDataset(list, SignStatsListResDTO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	
}
