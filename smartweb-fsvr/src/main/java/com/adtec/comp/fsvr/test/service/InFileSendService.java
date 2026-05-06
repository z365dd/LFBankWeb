package com.adtec.comp.fsvr.test.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.fsvr.dto.TFmngTranDownloadReqDTO;
import com.adtec.comp.fsvr.dto.TFmngTranDownloadResDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPullReqDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPullResDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPushReqDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPushResDTO;
import com.adtec.comp.fsvr.dto.TFmngTranSearchReqDTO;
import com.adtec.comp.fsvr.dto.TFmngTranSearchResDTO;
import com.adtec.comp.fsvr.dto.TFmngTranSearchResListDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlStatsListResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranDownloadListResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranResultQueryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadListReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadResDTO;
import com.adtec.comp.fsvr.util.FsvrUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.starring_file.client.FSTransfer;
import com.adtec.starring_file.util.StringTool;

import net.sf.json.JSONObject;

@Service
public class InFileSendService {
	/**
	 * 文件上传并推送
	 * @param reqBody
	 * @return
	 * @throws UnknownHostException 
	 */
	public IDataset upLoadPutSend(TfmngTranUploadAndDownloadReqDTO reqBody) {
		/*IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req,"TFmngTranUploadAndPut");
		
		req.getLOCAL_HEAD().setBUSI_NO(reqBody.getBUSI_NO());
		req.getLOCAL_HEAD().setENTR_NO(reqBody.getENTR_NO());
		req.getLOCAL_HEAD().setCHNL_NO(reqBody.getCHNL_NO());
		req.getLOCAL_HEAD().setLEGA_NO(reqBody.getLEGA_NO());
		req.getSYS_HEAD().setREQ_SVC_CODE(reqBody.getTRAN_CODE());
		

		try {
			req.getSYS_HEAD().setREQ_IP(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new BaseException(SysErr.E_MESSAGE, "获取ip失败");
		}
		
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callServiceNoException("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngTranUploadAndPut", req, TfmngTranUploadAndPutResDTO.class,null);
		TfmngTranUploadAndPutResDTO resBody =new TfmngTranUploadAndPutResDTO();
		if(null != resDTO.getBODY()){
			resBody= (TfmngTranUploadAndPutResDTO) resDTO.getBODY();
		}
		
		//获取请求流水号和返回信息REQ_SEQ
		resBody.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		resBody.setTRAN_STAT(resDTO.getSYS_HEAD().getTRAN_STAT());
		if("F".equals(resDTO.getSYS_HEAD().getTRAN_STAT()) || "f".equals(resDTO.getSYS_HEAD().getTRAN_STAT())){
			resBody.setTRAN_MSG("错误码："+resDTO.getSYS_HEAD().getTRAN_RET().get(0).getRET_CODE()+ ".错误信息：" +resDTO.getSYS_HEAD().getTRAN_RET().get(0).getRET_MSG());
		}
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadAndPutResDTO.class);
		*/
		
		String savePath = ParamUtil.getUploadFile() + "/fsvr";
		
		List<String> localFileList = new ArrayList<String>();
		List<String> remoteFileList = new ArrayList<String>();
		for(TfmngTranUploadAndDownloadListReqDTO tempDTO:reqBody.getLIST()){
			localFileList.add(tempDTO.getSUB_FILE_PATH() + "/" + tempDTO.getFILE_NAME());
			remoteFileList.add(tempDTO.getALIAS_FILE_NAME());
			
			try {
				FSTransfer fsTrans=FSTransfer.getInstance();
				fsTrans.initFSClient();
				String filePath=fsTrans.getFsClient().getSendPath();
				System.out.println("filePath文件路径:"+filePath);
				//复制文件
				FsvrUtil.copyFileCover(savePath + "/" + tempDTO.getFILE_NAME(), filePath + reqBody.getLOCAL_FULL_FILE_PATH()+ "/"+tempDTO.getSUB_FILE_PATH() + "/" + tempDTO.getFILE_NAME(), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		TfmngTranUploadAndDownloadResDTO resBody =new TfmngTranUploadAndDownloadResDTO();
		
		try {
			//TransInfo tranInfo = FileTransfer.getInstance().uploadExternalFile(seqNo, reqBody.getBUSI_NO(), reqBody.getENTR_NO(), reqBody.getCHNL_NO(), reqBody.getLEGA_NO(), reqBody.getTRAN_CODE(), reqBody.getDEF_VAL(), reqBody.getFILE_TRANS_TP(), reqBody.getLOCAL_FULL_FILE_PATH(), localFileList, reqBody.getRMT_FULL_FILE_PATH(), remoteFileList, null);
			//发报文
			TfmngTranUploadAndDownloadReqDTO tfmngTranUploadAndDownloadReqDTO=new TfmngTranUploadAndDownloadReqDTO();
			tfmngTranUploadAndDownloadReqDTO.setFILE_TRANS_TP(reqBody.getFILE_TRANS_TP());
			tfmngTranUploadAndDownloadReqDTO.setLOCAL_FULL_FILE_PATH(reqBody.getLOCAL_FULL_FILE_PATH());
			tfmngTranUploadAndDownloadReqDTO.setRMT_FULL_FILE_PATH(reqBody.getRMT_FULL_FILE_PATH());
			System.out.println("reqBody.getFILE_SVR_ID():"+reqBody.getFILE_SVR_ID());
			tfmngTranUploadAndDownloadReqDTO.setFILE_SVR_ID(reqBody.getFILE_SVR_ID());
			tfmngTranUploadAndDownloadReqDTO.setFILE_NUM(reqBody.getLIST().size());
			tfmngTranUploadAndDownloadReqDTO.setLIST(reqBody.getLIST());
			
			resBody.setFILE_SET_SEQ(getFileSeqNo(tfmngTranUploadAndDownloadReqDTO,resBody));
		} catch (Exception e) {
			e.printStackTrace();
			resBody.setTRAN_STAT("F");
			resBody.setTRAN_MSG("错误信息：" +e.getMessage());
		}
		IDataset responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadAndDownloadResDTO.class);
		return responseData;
	}
	
	/**
	 * 上传并推送
	 */
	public String getFileSeqNo(TfmngTranUploadAndDownloadReqDTO reqBody,TfmngTranUploadAndDownloadResDTO resBody1) {
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranUploadAndPush");
		resBody1.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngTranUploadAndPush", req, TfmngTranUploadAndDownloadResDTO.class,null);		
		TfmngTranUploadAndDownloadResDTO resBody = new TfmngTranUploadAndDownloadResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngTranUploadAndDownloadResDTO) resDTO.getBODY();
		}
		
		return resBody.getFILE_SET_SEQ();
	}
	
	/**
	 * 文件上传
	 * @param reqBody
	 * @return
	 * @throws UnknownHostException 
	 */
	public IDataset fsvrFildUpLoad(TfmngTranUploadReqDTO reqBody) {
		
		String savePath = ParamUtil.getUploadFile() + "/fsvr";
		
		for(TfmngTranUploadAndDownloadListReqDTO tempDTO:reqBody.getLIST()){
			try {
				FSTransfer fsTrans=FSTransfer.getInstance();
				fsTrans.initFSClient();
				String filePath=fsTrans.getFsClient().getSendPath();
				System.out.println("filePath文件路径:"+filePath);
				//复制文件
				FileUtil.copyFile(savePath + "/" + tempDTO.getFILE_NAME(), filePath + reqBody.getPUB_FILE_PATH()+ "/"+tempDTO.getSUB_FILE_PATH() + "/" + tempDTO.getFILE_NAME());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		TfmngTranUploadResDTO resBody =new TfmngTranUploadResDTO();
		
		try {
			//发报文
			TfmngTranUploadReqDTO tfmngTranUploadReqDTO=new TfmngTranUploadReqDTO();
			tfmngTranUploadReqDTO.setFILE_TRANS_TP(reqBody.getFILE_TRANS_TP());
			tfmngTranUploadReqDTO.setPUB_FILE_PATH(reqBody.getPUB_FILE_PATH());
			System.out.println("reqBody.getFILE_SVR_ID():"+reqBody.getFILE_SVR_ID());
			tfmngTranUploadReqDTO.setFILE_SVR_ID(reqBody.getFILE_SVR_ID());
			tfmngTranUploadReqDTO.setFILE_NUM(reqBody.getLIST().size());
			tfmngTranUploadReqDTO.setLIST(reqBody.getLIST());
			
			resBody.setFILE_SET_SEQ(getUpload(tfmngTranUploadReqDTO,resBody));
		} catch (Exception e) {
			e.printStackTrace();
			resBody.setTRAN_STAT("F");
			resBody.setTRAN_MSG("错误信息：" +e.getMessage());
		}
		IDataset responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadResDTO.class);
		return responseData;
	}
	
	/**
	 * 上传
	 */
	public String getUpload(TfmngTranUploadReqDTO reqBody,TfmngTranUploadResDTO resBody1) {
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranUpload");
		resBody1.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngTranUpload", req, TfmngTranUploadResDTO.class,null);		
		TfmngTranUploadResDTO resBody = new TfmngTranUploadResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngTranUploadResDTO) resDTO.getBODY();
		}
		
		return resBody.getFILE_SET_SEQ();
	}
	/**
	 * 获取并下载
	 */
//	public String getFileSeqNo(TfmngTranUploadAndDownloadReqDTO reqBody) {
//		ReqDTO req = new ReqDTO(reqBody);
//		FsvrUtil.setReqHead(req, "TFmngTranUploadAndPush");
//		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
//		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngTranUploadAndPush", req, TfmngTranUploadAndDownloadResDTO.class,null);		
//		TfmngTranUploadAndDownloadResDTO resBody = new TfmngTranUploadAndDownloadResDTO();
//		if(resDTO.getBODY() != null){
//			resBody = (TfmngTranUploadAndDownloadResDTO) resDTO.getBODY();
//		}
//		
//		return resBody.getFILE_SET_SEQ();
//	}
	
	/**
	 * 文件获取并下载
	 * @param reqBody
	 * @return
	 * @throws UnknownHostException 
	 */
	public TfmngTranUploadAndDownloadResDTO downLoadGetSend(TfmngTranUploadAndDownloadReqDTO reqBody) {
		/*IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req,"TFmngTranGetAndDownload");
		
		req.getLOCAL_HEAD().setBUSI_NO(reqBody.getBUSI_NO());
		req.getLOCAL_HEAD().setENTR_NO(reqBody.getENTR_NO());
		req.getLOCAL_HEAD().setCHNL_NO(reqBody.getCHNL_NO());
		req.getLOCAL_HEAD().setLEGA_NO(reqBody.getLEGA_NO());
		req.getSYS_HEAD().setREQ_SVC_CODE(reqBody.getTRAN_CODE());
		
		try {
			req.getSYS_HEAD().setREQ_IP(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new BaseException(SysErr.E_MESSAGE, "获取ip失败");
		}
		
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callServiceNoException("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngTranGetAndDownload", req, TfmngTranUploadAndPutResDTO.class,null);
		TfmngTranUploadAndPutResDTO resBody =new TfmngTranUploadAndPutResDTO();
		if(null != resDTO.getBODY()){
			resBody= (TfmngTranUploadAndPutResDTO) resDTO.getBODY();
		}
		
		//获取请求流水号和返回信息REQ_SEQ
		resBody.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		resBody.setTRAN_STAT(resDTO.getSYS_HEAD().getTRAN_STAT());
		if("F".equals(resDTO.getSYS_HEAD().getTRAN_STAT()) || "f".equals(resDTO.getSYS_HEAD().getTRAN_STAT())){
			resBody.setTRAN_MSG("错误码："+resDTO.getSYS_HEAD().getTRAN_RET().get(0).getRET_CODE()+ ".错误信息：" +resDTO.getSYS_HEAD().getTRAN_RET().get(0).getRET_MSG());
		}
		
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadAndPutResDTO.class);*/
		
		
		
		
		TfmngTranUploadAndDownloadResDTO resBody =new TfmngTranUploadAndDownloadResDTO();
	/*	String seqNo = SeqUtil.getMBCSeq();
		List<String> localFileList = new ArrayList<String>();
		List<String> remoteFileList = new ArrayList<String>();
		for(TfmngTranUploadAndDownloadListReqDTO tempDTO:reqBody.getLIST()){
			remoteFileList.add(tempDTO.getSUB_FILE_PATH() + "/" + tempDTO.getFILE_NAME());
			localFileList.add(tempDTO.getALIAS_FILE_NAME());
		}
		
		resBody.setREQ_SEQ(seqNo);*/
		try {
			//TransInfo tranInfo = FileTransfer.getInstance().downloadExternalFile(seqNo, reqBody.getBUSI_NO(), reqBody.getENTR_NO(), reqBody.getCHNL_NO(), reqBody.getLEGA_NO(), reqBody.getTRAN_CODE(), reqBody.getDEF_VAL(), reqBody.getFILE_TRANS_TP(), reqBody.getLOCAL_FULL_FILE_PATH(), localFileList, reqBody.getRMT_FULL_FILE_PATH(), remoteFileList, null);
			//发报文
			TfmngTranUploadAndDownloadReqDTO tfmngTranUploadAndDownloadReqDTO=new TfmngTranUploadAndDownloadReqDTO();
			tfmngTranUploadAndDownloadReqDTO.setFILE_TRANS_TP(reqBody.getFILE_TRANS_TP());
			tfmngTranUploadAndDownloadReqDTO.setLOCAL_FULL_FILE_PATH(reqBody.getLOCAL_FULL_FILE_PATH());
			tfmngTranUploadAndDownloadReqDTO.setRMT_FULL_FILE_PATH(reqBody.getRMT_FULL_FILE_PATH());
			System.out.println("reqBody.getFILE_SVR_ID():"+reqBody.getFILE_SVR_ID());
			tfmngTranUploadAndDownloadReqDTO.setFILE_SVR_ID(reqBody.getFILE_SVR_ID());
			tfmngTranUploadAndDownloadReqDTO.setFILE_NUM(reqBody.getLIST().size());
			tfmngTranUploadAndDownloadReqDTO.setLIST(reqBody.getLIST());
			
			getAndDownload(tfmngTranUploadAndDownloadReqDTO,resBody);
		} catch (Exception e) {
			e.printStackTrace();
			resBody.setTRAN_STAT("F");
			resBody.setTRAN_MSG("错误信息：" +e.getMessage());
		}
	/*	IDataset responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadAndDownloadResDTO.class);
		return responseData;*/
		return resBody;
	}

	/**
	 * 获取并下载
	 */
	public void getAndDownload(TfmngTranUploadAndDownloadReqDTO reqBody,TfmngTranUploadAndDownloadResDTO resBody1) {
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranPullAndDownload");
		resBody1.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("FSVR_PARTID"),"TFmngTranPullAndDownload", req, TfmngTranUploadAndDownloadResDTO.class,null);		
		TfmngTranUploadAndDownloadResDTO resBody = new TfmngTranUploadAndDownloadResDTO();
		if(resDTO.getBODY() != null){
			resBody = (TfmngTranUploadAndDownloadResDTO) resDTO.getBODY();
		}
		resBody1.setFILE_SET_SEQ(resBody.getFILE_SET_SEQ());
		resBody1.setLIST(resBody.getLIST());
		/*String fileStr = JSONArray.fromObject(resBody.getLIST()).toString();
		resBody1.setFILE_LIST(fileStr);*/
		
	}
	
	/**
	 * 文件获取
	 */
	public IDataset fileGet(TFmngTranPullReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranPull");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranPull", req,
				TFmngTranPullResDTO.class, null);
		TFmngTranPullResDTO resBody = (TFmngTranPullResDTO) resDTO.getBODY();
		resBody.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		responseData = DatasetService.getInstace().getDataset(resBody, TFmngTranPullResDTO.class);
		return responseData;
	}
	
	/**
	 * 文件推送
	 */
	public IDataset filePush(TFmngTranPushReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranPush");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranPush", req,
				TFmngTranPushResDTO.class, null);
		TFmngTranPushResDTO resBody = (TFmngTranPushResDTO) resDTO.getBODY();
		if ( resBody == null ) {
			resBody = new TFmngTranPushResDTO();
		}
		resBody.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		responseData = DatasetService.getInstace().getDataset(resBody, TFmngTranPushResDTO.class);
		return responseData;
	}
	
	/**
	 * 文件搜索
	 */
	public IDataset fileSearch(TFmngTranSearchReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranSearch");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranSearch", req,
				TFmngTranSearchResDTO.class, null);
		TFmngTranSearchResDTO resBody = (TFmngTranSearchResDTO) resDTO.getBODY();
//		System.out.println("resBody:"+resBody.toString());
		List<TFmngTranSearchResListDTO> listData = new ArrayList<TFmngTranSearchResListDTO>();
		int total = 0;
		if(resBody!=null){
			List<TFmngTranSearchResListDTO> list = resBody.getLIST();
			if (list != null  && list.size()>0) {
				total = list.size();
				for (int i = 0;i<total; i++) {
					TFmngTranSearchResListDTO listDTO = list.get(i);
					listData.add(listDTO);
				}
			}
		}
		responseData = DatasetService.getInstace().getDataset(listData, TFmngTranSearchResListDTO.class);
		responseData.setTotalCount((int) resBody.getFILE_NUM());
		return responseData;
	}
	
	/**
	 * 文件下载
	 */
	public TFmngTranDownloadResDTO fileDownload(TFmngTranDownloadReqDTO reqBody) {
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranDownload");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranDownload", req,
				TFmngTranDownloadResDTO.class, null);
		TFmngTranDownloadResDTO resBody = (TFmngTranDownloadResDTO) resDTO.getBODY();
		//拼接文件路径
		if(resBody.getFILE_NUM() > 0){
			try{
				FSTransfer fsTrans=FSTransfer.getInstance();
				fsTrans.initFSClient();
				String filePath=fsTrans.getFsClient().getRecvPath();
				String pubFilePath = reqBody.getPUB_FILE_PATH();
				if (!StringTool.isNullOrEmpty(pubFilePath) && !("/".equals(pubFilePath.substring(pubFilePath.length() - 1)))) {
					pubFilePath = pubFilePath + "/";
				}
				List<TfmngTranDownloadListResDTO> list = resBody.getLIST();
				for(int i = 0; i < list.size(); i++) {
					String fullFilePath = filePath + pubFilePath + list.get(i).getSRC_SUB_FILE_PATH() + list.get(i).getSRC_FILE_NAME();
					list.get(i).setFULL_FILE_PATH(fullFilePath);
				}
			}catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		return resBody;
	}
	
	/**
	 * 文件结果查询
	 */
	public IDataset fileResultQrySend(TfmngTranResultQueryReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranResultQuery");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		JSONObject resJSON = httpJsonFactory.callService("TFmngTranResultQuery",req,"",ParamUtil.getConfig("FSVR_PARTID"),null);
		JSONObject resBody = (JSONObject) resJSON.get("BODY");
		
		
		responseData.addColumn("TfmngTranResultQueryRes");
		responseData.beforeFirst();
		if(!responseData.hasNext()){
			responseData.appendRow();
		}
		responseData.next();
		responseData.updateValue("TfmngTranResultQueryRes", resBody);
		return responseData;
	}

}
