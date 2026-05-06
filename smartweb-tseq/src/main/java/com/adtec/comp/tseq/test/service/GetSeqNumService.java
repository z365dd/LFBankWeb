package com.adtec.comp.tseq.test.service;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.tseq.dto.TSeqTranGetSeqReqDTO;
import com.adtec.comp.tseq.dto.TSeqTranGetSeqResDTO;
import com.adtec.comp.tseq.util.TseqUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class GetSeqNumService {
	/**
	 * 获取流水号提交
	 * @param reqBody 
	 * @return
	 */
	public IDataset add(TSeqTranGetSeqReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		String comp_no = ParamUtil.getConfig("FLOW_PARTID"); 
		TseqUtil.setReqHead(req, "TSeqTranGetSeqList");
		req.getSYS_HEAD().setSND_COMP_NO(comp_no);     //TODO 流水号组件用于测试用
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FLOW_PARTID"),"TSeqTranGetSeqList", req, TSeqTranGetSeqResDTO.class,null);
		TSeqTranGetSeqResDTO resBody = new TSeqTranGetSeqResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TSeqTranGetSeqResDTO) resDTO.getBODY();
		}
		responseData = DatasetService.getInstace().getDataset(resBody,TSeqTranGetSeqResDTO.class);
		return responseData;
	}
	

	
}
