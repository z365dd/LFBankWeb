package com.adtec.comp.fsvr.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.fsvr.dto.SdkFileInfo;
import com.adtec.comp.fsvr.dto.TFmngTranPullReqDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPullResDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPushReqDTO;
import com.adtec.comp.fsvr.dto.TFmngTranPushResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranConfmReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranConfmResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranDownloadApplyReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranDownloadApplyResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadListReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadResDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadApplyListReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadApplyReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadApplyResDTO;
import com.adtec.comp.fsvr.util.FsvrUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.starring_file.client.FSTransfer;
import com.adtec.starring_file.main.FileTransfer;
import com.adtec.starring_file.server.FileInfoBean;
import com.adtec.sys.common.utils.SeqUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class SdkFileService {

	public String uploadFile(String fileName, String svrNo, boolean isAysn) throws Exception{
		FSTransfer test = FSTransfer.getInstance(true);
		
		String taskNo = test.sendFile(fileName, isAysn);
		if("false".equals(taskNo)){
			throw new Exception("文件上传失败，请查看系统日志!");
		}
        //任务信息
        Map<String, FileInfoBean> mess = test.getFsClient().getInfoMap();
        if(isAysn){
        	System.out.println("任务号: "+taskNo);
        }else{
        	if(mess == null){
                System.out.println("同步发送未记录任务信息");
            }else{
                FileInfoBean info = mess.get(taskNo);
                if(info==null){
                    System.out.println("任务传输完毕");
                }else if(info.getStatus() == FileInfoBean.DOING){
                    System.out.println("任务发送中,任务信息：");
                    System.out.print(JSONObject.toJSON(info).toString());
                }
            }
        }
        return taskNo;
	}
	/*
	 * 上传并推送
	 */
	public TfmngTranUploadAndDownloadResDTO uploadAndPutFileMng(TfmngTranUploadAndDownloadReqDTO reqBody) throws Exception{
		List<TfmngTranUploadAndDownloadListReqDTO> list = reqBody.getLIST();
		//文件上传申请
		TfmngTranUploadApplyReqDTO uploadApplyReqBody = new TfmngTranUploadApplyReqDTO();
		uploadApplyReqBody.setFILE_NUM(reqBody.getFILE_NUM());
		uploadApplyReqBody.setFILE_TRANS_TP(reqBody.getFILE_TRANS_TP());
		List<TfmngTranUploadApplyListReqDTO> applyList = new ArrayList<TfmngTranUploadApplyListReqDTO>();
		for (int i = 0; i < list.size(); i++) {
			TfmngTranUploadApplyListReqDTO tempDTO = new TfmngTranUploadApplyListReqDTO();
			tempDTO.setFILE_NAME(list.get(i).getFILE_NAME());
			tempDTO.setSUB_FILE_PATH(list.get(i).getSUB_FILE_PATH());
			applyList.add(tempDTO);
		}
		uploadApplyReqBody.setLIST(applyList);
		TfmngTranUploadApplyResDTO uploadApplyResDto = uploadApply(uploadApplyReqBody);
		//文件上传
		String seqNo = "";
		for (int i = 0; i < list.size(); i++) {
			String fileName = list.get(i).getFILE_NAME();
			System.out.println("FILE_NAME文件名:"+fileName);
			try {
				seqNo += (uploadFile(fileName, reqBody.getFILE_SVR_ID(), "02".equals(reqBody.getFILE_TRANS_TP())) + ",");
			} catch (Exception e) {
				throw e;
			}
		}
		//文件传输确认
		TfmngTranConfmReqDTO confmDto = new TfmngTranConfmReqDTO();
		confmDto.setTRAN_TP("01");
		confmDto.setSTAT("02");
		confmDto.setTRAN_SEQ(seqNo);
		confmDto.setRET_CODE("000000");
		confmDto.setRET_CODE("交易成功");
		tranConfm(confmDto);
		//文件推送
		TFmngTranPushReqDTO pushReqBody = new TFmngTranPushReqDTO();
		pushReqBody.setFILE_SET_SEQ(uploadApplyResDto.getFILE_SET_SEQ());
		pushReqBody.setFILE_SVR_ID(reqBody.getFILE_SVR_ID());
		pushReqBody.setFILE_TRANS_TP(reqBody.getFILE_TRANS_TP());
		pushReqBody.setPUB_FILE_PATH(reqBody.getRMT_FULL_FILE_PATH());
		filePush(pushReqBody);
		TfmngTranUploadAndDownloadResDTO resBody = new TfmngTranUploadAndDownloadResDTO();
		resBody.setFILE_SET_SEQ(uploadApplyResDto.getFILE_SET_SEQ());
		return resBody;
	}
	
	public String downloadFile(String fileName, String svrNo, boolean isAysn) throws Exception{
		FSTransfer test = FSTransfer.getInstance(true);
		String taskNo = test.recvFile(fileName, isAysn);
        if("false".equals(taskNo)){
			throw new Exception("文件下载失败，请查看系统日志!");
		}
        //任务信息
        Map<String, FileInfoBean> mess = test.getFsClient().getInfoMap();
        if(isAysn){
        	System.out.println("任务号: "+taskNo);
        }else{
        	if(mess == null){
                System.out.println("同步发送未记录任务信息");
            }else{
                FileInfoBean info = mess.get(taskNo);
                if(info==null){
                    System.out.println("任务传输完毕");
                }else if(info.getStatus() == FileInfoBean.DOING){
                    System.out.println("任务发送中,任务信息：");
                    System.out.print(JSONObject.toJSON(info).toString());
                }
            }
        }
        return taskNo;
	}
	

	/**
	 * 文件上传申请
	 */
	public TfmngTranUploadApplyResDTO uploadApply(TfmngTranUploadApplyReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranUploadApply");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranUploadApply", req,
				TfmngTranUploadApplyResDTO.class, null);
		TfmngTranUploadApplyResDTO resBody = (TfmngTranUploadApplyResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadApplyResDTO.class);
		return resBody;
	}
	
	/**
	 * 文件下载申请
	 */
	public IDataset uploadApply(TfmngTranDownloadApplyReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranDownloadApply");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranDownloadApply", req,
				TfmngTranDownloadApplyResDTO.class, null);
		TfmngTranDownloadApplyResDTO resBody = (TfmngTranDownloadApplyResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranDownloadApplyResDTO.class);
		return responseData;
	}
	
	/**
	 * 文件传输确认
	 */
	public IDataset tranConfm(TfmngTranConfmReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FsvrUtil.setReqHead(req, "TFmngTranConfm");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FSVR_PARTID"), "TFmngTranConfm", req,
				TfmngTranConfmResDTO.class, null);
		TfmngTranConfmResDTO resBody = (TfmngTranConfmResDTO) resDTO.getBODY();
		responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranConfmResDTO.class);
		return responseData;
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
		resBody.setREQ_SEQ(req.getSYS_HEAD().getREQ_SEQ());
		responseData = DatasetService.getInstace().getDataset(resBody, TFmngTranPushResDTO.class);
		return responseData;
	}
	
	/**
	 * 文件上传并推送
	 */
	public IDataset uploadAndPush(TfmngTranUploadAndDownloadReqDTO reqBody) throws Exception{
		String seqNo = DateUtil.getDate() + SeqUtil.getMBCSeq();
		List<String> list = new ArrayList<String>();
		for(TfmngTranUploadAndDownloadListReqDTO dto:reqBody.getLIST()){
			if(StringUtil.isEmpty(dto.getSUB_FILE_PATH())){
				list.add(dto.getSUB_FILE_PATH() + "/" + dto.getFILE_NAME());
			}else{
				list.add(dto.getFILE_NAME());
			}
		}
		String fileSeqSet = "";
		if("02".equals(reqBody.getFILE_TRANS_TP())){	//异步传输
			fileSeqSet = new FileTransfer().uploadAndPushFileAsyn(list, reqBody.getFILE_SVR_ID(), null);
			seqNo = fileSeqSet;
		}else{
			fileSeqSet = new FileTransfer().uploadAndPushFile(list, reqBody.getFILE_SVR_ID(), null, seqNo);
		}
		TfmngTranUploadAndDownloadResDTO resBody = new TfmngTranUploadAndDownloadResDTO();
		resBody.setREQ_SEQ(seqNo);
		resBody.setFILE_SET_SEQ(fileSeqSet);
		IDataset responseData = DatasetService.getInstace().getDataset(resBody, TfmngTranUploadAndDownloadResDTO.class);
		return responseData;
	}
	
	/**
	 * 文件获取并下载
	 */
	public IDataset pullAndDownload(TfmngTranUploadAndDownloadReqDTO reqBody) throws Exception{
		String seqNo = DateUtil.getDate() + SeqUtil.getMBCSeq();
		List<String> list = new ArrayList<String>();
		for(TfmngTranUploadAndDownloadListReqDTO dto:reqBody.getLIST()){
			if(StringUtil.isEmpty(dto.getSUB_FILE_PATH())){
				list.add(dto.getSUB_FILE_PATH() + "/" + dto.getFILE_NAME());
			}else{
				list.add(dto.getFILE_NAME());
			}
		}
		
		List<SdkFileInfo> info = new ArrayList<SdkFileInfo>();
		if("02".equals(reqBody.getFILE_TRANS_TP())){	//异步传输
			seqNo = new FileTransfer().pullAndDownFileAsyn(reqBody.getFILE_SVR_ID(), list, null);
			SdkFileInfo tempInfo = new SdkFileInfo();
			tempInfo.setSeqNo(seqNo);
			tempInfo.setFileSeqSet(seqNo);
			info.add(tempInfo);
		}else{
			Object[] resArr = new FileTransfer().pullAndDownFile(reqBody.getFILE_SVR_ID(), list, null, seqNo);
			String fileSeqSet= String.valueOf(resArr[1]);
			for(String fileName:(List<String>)resArr[0]){
				SdkFileInfo tempInfo = new SdkFileInfo();
				tempInfo.setFileName(fileName);
				tempInfo.setSeqNo(seqNo);
				tempInfo.setFileSeqSet(fileSeqSet);
				info.add(tempInfo);
			}
		}
		
		IDataset responseData = DatasetService.getInstace().getDataset(info, SdkFileInfo.class);
		return responseData;
	}
	
}
