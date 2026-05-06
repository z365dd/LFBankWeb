package com.adtec.comp.sign.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.sign.dao.FSignTPipBusiDao;
import com.adtec.comp.sign.dto.FSignChkRecvOthChkReqDTO;
import com.adtec.comp.sign.dto.FSignChkRecvOthChkResDTO;
import com.adtec.comp.sign.entity.FSignTPipBusiDO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class SignChkOthService {
	@Autowired
	private FSignTPipBusiDao busiManageDao;
	
	//获取业务编号信息(91)
	public List<Map<String, Object>> busiList(FSignTPipBusiDO reqBody) {
		List<Map<String, Object>> list = new ArrayList();
		List<FSignTPipBusiDO> busiList = busiManageDao.list(reqBody,0,0);
		for (FSignTPipBusiDO DO : busiList) {
			HashMap<String, Object> tempMap = new HashMap();
			tempMap.put("name", DO.getBusiNo() + "-" + DO.getBusiName());
			tempMap.put("BUSI_NO", DO.getBusiNo());
			tempMap.put("BUSI_NAME", DO.getBusiName());
			tempMap.put("ENTR_NO", DO.getBusiName());
			list.add(tempMap);
		}
		return list;
	}
	// 提交
	public IDataset add(FSignChkRecvOthChkReqDTO reqBody, String busiNo) {
		IDataset responseData = DatasetService.getInstace().getDataset();

		ReqDTO req = new ReqDTO(reqBody);
		
		SignUtil.setReqHead(busiNo,req, "FSignChkRecvOthChk");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();

		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignChkRecvOthChk", req, FSignChkRecvOthChkResDTO.class,null);
		FSignChkRecvOthChkResDTO resBody = (FSignChkRecvOthChkResDTO) resDTO.getBODY();
		//resBody.setFILE_SET_SEQ(reqBody.getREQ_FILE_SET_SEQ());	
		responseData = DatasetService.getInstace().getDataset(resBody, FSignChkRecvOthChkResDTO.class);
		return responseData;
	}
}
