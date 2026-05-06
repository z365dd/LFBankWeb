package com.adtec.comp.sign.test.service;

import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.pub.dto.TfmngTranGetListReqDTO;
import com.adtec.comp.pub.dto.TfmngTranGetResDTO;
import com.adtec.comp.pub.dto.TfmngTranPutResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignFileReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignFileResDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignFileRsltQryReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignFileRsltQryResDTO;
import com.adtec.comp.sign.dto.FSignSignVerifyFileReqDTO;
import com.adtec.comp.sign.dto.FSignTfmngTranGetReqDTO;
import com.adtec.comp.sign.dto.FSignTfmngTranPutReqDTO;
import com.adtec.comp.sign.util.SignUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

@Service
public class SignFileService {

	// 文件传输组件 文件获取
	public String getFile(String filePath) {
		FSignTfmngTranGetReqDTO  reqBody= new FSignTfmngTranGetReqDTO();
		reqBody.setFILE_TRANS_TP("01");
		/*reqBody.setDIM_FLG("000001");
		reqBody.setDEF_VAL("smartweb.sign");*/
		reqBody.setFILE_NUM(1);
		reqBody.setFILE_SVR_ID("smartweb.sign");
		
		TfmngTranGetListReqDTO fileListDto = new TfmngTranGetListReqDTO();
		fileListDto.setFILE_NAME(filePath);
		fileListDto.setSUB_FILE_PATH("sign");
		reqBody.getLIST().add(fileListDto); 
		
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "TFmngTranPull");
		req.getSYS_HEAD().setREQ_COMP_NO(ParamUtil.getConfig("SIGN_PARTID"));

		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranPull",
				req, TfmngTranGetResDTO.class, null);
		TfmngTranGetResDTO resBody = (TfmngTranGetResDTO) resDTO.getBODY();
		return resBody.getFILE_SET_SEQ();
	}
 
	// 批量测试提交
	public IDataset signFileAdd(FSignFuncCustSignFileReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();

		ReqDTO req = new ReqDTO(reqBody);
		
//		SignUtil.setReqHead(req, "FSignFuncSignBatSubmit");
		SignUtil.setReqHead(req, "FSignFuncSignBatSubmit");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();

		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignFuncSignBatSubmit", req, FSignFuncCustSignFileResDTO.class,null);
		FSignFuncCustSignFileResDTO resBody = new FSignFuncCustSignFileResDTO();
//		FSignFuncCustSignFileResDTO resBody = (FSignFuncCustSignFileResDTO) resDTO.getBODY();
		/*resBody.setFILE_SET_SEQ(reqBody.getREQ_FILE_SET_SEQ());
		resBody.setREQ_SEQ_NO(req.getSYS_HEAD().getREQ_SEQ_NO());	*/	
		resBody.setREQ_SEQ_NO(req.getAPP_HEAD().getREQ_SEQ());
		/*resBody.setRET_CODE(req.getSYS_HEAD().getTRAN_RET());
		resBody.setRET_MSG(req.getSYS_HEAD().getTRAN_STAT());*/
		responseData = DatasetService.getInstace().getDataset(resBody, FSignFuncCustSignFileResDTO.class);
		return responseData;
	}
	
	// 批量签约校验提交
	public IDataset verifFileAdd(FSignSignVerifyFileReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		
		ReqDTO req = new ReqDTO(reqBody);
		
//		SignUtil.setReqHead(req, "FSignFuncSignBatSubmit");
		SignUtil.setReqHead(req, "FSignVerifBatSubmit");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignVerifBatSubmit", req, FSignFuncCustSignFileResDTO.class,null);
		FSignFuncCustSignFileResDTO resBody = new FSignFuncCustSignFileResDTO();
//		FSignFuncCustSignFileResDTO resBody = (FSignFuncCustSignFileResDTO) resDTO.getBODY();
		/*resBody.setFILE_SET_SEQ(reqBody.getREQ_FILE_SET_SEQ());
		resBody.setREQ_SEQ_NO(req.getSYS_HEAD().getREQ_SEQ_NO());	*/	
		resBody.setREQ_SEQ_NO(req.getAPP_HEAD().getREQ_SEQ());
		/*resBody.setRET_CODE(req.getSYS_HEAD().getTRAN_RET());
		resBody.setRET_MSG(req.getSYS_HEAD().getTRAN_STAT());*/
		responseData = DatasetService.getInstace().getDataset(resBody, FSignFuncCustSignFileResDTO.class);
		return responseData;
	}

	
	//批量结果查询
	public IDataset signFileQry(FSignFuncCustSignFileRsltQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();

		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignFuncSignBatRsltQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignFuncSignBatRsltQry", req, FSignFuncCustSignFileRsltQryResDTO.class,null);
		FSignFuncCustSignFileRsltQryResDTO resBody = (FSignFuncCustSignFileRsltQryResDTO) resDTO.getBODY();
		
		//文件推送,返回文件路径
		if("S".equals(resBody.getBAT_STAT())){
			resBody.setFILE_SET_SEQ(sendFile(resBody.getFILE_SET_SEQ()));
			/*resBody.setBAT_STAT("处理成功");*/
		}
		/*else if("P".equals(resBody.getBAT_STAT())){
			resBody.setBAT_STAT("处理中");
		}else if("F".equals(resBody.getBAT_STAT())){
			resBody.setBAT_STAT("处理失败");
		}else{
			resBody.setBAT_STAT("未知状态");
		}*/
		
		responseData = DatasetService.getInstace().getDataset(resBody, FSignFuncCustSignFileRsltQryResDTO.class);
		return responseData;
	}
	
	//批量签约校验结果查询
	public IDataset signVerifFileQry(FSignFuncCustSignFileRsltQryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "FSignVerifBatRsltQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("SIGN_PARTID"),"FSignVerifBatRsltQry", req, FSignFuncCustSignFileRsltQryResDTO.class,null);
		FSignFuncCustSignFileRsltQryResDTO resBody = (FSignFuncCustSignFileRsltQryResDTO) resDTO.getBODY();
		
		//文件推送,返回文件路径
		if("S".equals(resBody.getBAT_STAT())){
			resBody.setFILE_SET_SEQ(sendFile(resBody.getFILE_SET_SEQ()));
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, FSignFuncCustSignFileRsltQryResDTO.class);
		return responseData;
	}


	// 文件传输组件 推送文件
	public String sendFile(String FILE_SET_SEQ) {
		FSignTfmngTranPutReqDTO reqBody = new FSignTfmngTranPutReqDTO();
		reqBody.setFILE_SET_SEQ(FILE_SET_SEQ);
		reqBody.setFILE_TRANS_TP("01");
		/*reqBody.setDIM_FLG("000001");
		reqBody.setDEF_VAL("smartweb.sign");*/
		reqBody.setFILE_SVR_ID("smartweb.sign");
		ReqDTO req = new ReqDTO(reqBody);
		SignUtil.setReqHead(req, "TFmngTranPush");

		req.getSYS_HEAD().setREQ_COMP_NO(ParamUtil.getConfig("SIGN_PARTID"));

		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranPush",
				req, TfmngTranPutResDTO.class, null);
		TfmngTranPutResDTO resBody = (TfmngTranPutResDTO) resDTO.getBODY();
		return resBody.getLIST().get(0).getFULL_FILE_PATH();
	}

	
	
}
