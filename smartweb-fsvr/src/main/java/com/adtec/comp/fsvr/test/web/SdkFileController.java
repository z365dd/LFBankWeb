package com.adtec.comp.fsvr.test.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.comp.fsvr.dto.FileMsgDTO;
import com.adtec.comp.fsvr.dto.SdkFileInfo;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadListReqDTO;
import com.adtec.comp.fsvr.dto.TfmngTranUploadAndDownloadReqDTO;
import com.adtec.comp.fsvr.test.service.SdkFileService;
import com.adtec.comp.fsvr.util.RedisClusterOperator;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.starring_file.client.FSTransfer;
import com.adtec.starring_file.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.google.common.net.HttpHeaders;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/testSdk")
public class SdkFileController extends BaseController {
	@Autowired
	private SdkFileService sdkFileService;

	/**
	 * 返回文件上传页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sdkUpload" })
	public String sdkUpload(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/sdkUpload";
	}


	/**
	 * 返回文件下载页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sdkDownload" })
	public String sdkDownload(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/sdkDownload";
	}
	
	/**
	 * 返回文件上传并推送页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sdkUploadPut" })
	public String sdkUploadPut(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrSdkUploadPut";
	}
	
	/**
	 * 返回文件下载并获取页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sdkDownloadGet" })
	public String sdkDownloadGet(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrSdkDownLoadGet";
	}
	/**
	 * 上传文件 返回本地保存信息
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "upload")
	public void upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile[] file) {
		/** 文件保存路径 */
		//String savePath = String.format(System.getenv("JAVAWORKDIR")) + "/sendFile/";
		FSTransfer fsTrans;
		String savePath="";
		try {
			fsTrans = FSTransfer.getInstance();
			fsTrans.initFSClient();
			savePath=fsTrans.getFsClient().getSendPath();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// 创建文件保存目录
		FileUtil.createDirectory(savePath);
		if (file != null && file.length > 0) {
			try {

				FileMsgDTO fileDao = new FileMsgDTO();

				// fileName = file[i].getOriginalFilename();
				fileDao.setFileName((file[0].getOriginalFilename()).substring(0,file[0].getOriginalFilename().indexOf(".")));
				// type = fileName.substring(fileName.indexOf(".") + 1);
				fileDao.setType(
						file[0].getOriginalFilename().substring(file[0].getOriginalFilename().indexOf(".") + 1));
				// saveName = UUID.randomUUID().toString();
				fileDao.setSaveName(UUID.randomUUID().toString());
				fileDao.setPath(savePath);
				// 保存文件
				FileUtil.SaveFileFromInputStream(file[0].getInputStream(), savePath,
						fileDao.getFileName()+ "." + fileDao.getType());
				
				IDataset responseData = DatasetService.getInstace().getDataset(fileDao, FileMsgDTO.class);
				setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "上传成功");

			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseException(SysErr.E_MESSAGE, "上传出现异常！本地保存文件失败。");
			}
		} else {
			throw new BaseException(SysErr.E_MESSAGE, "没有检测到文件！");
		}
	}


	// SDK文件上传
	@RequiresPermissions("user")
	@RequestMapping(value = "upLoadFile")
	public void upLoadFile(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SVR_NO = reqDs.getString("SVR_NO");
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String FILE_STR = reqDs.getString("FILE_STR");
		
		System.out.println("FILE_STR文件数:"+FILE_STR);
		String seqNo = "";
		JSONArray jsonArray = JSONArray.fromObject(FILE_STR);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			String fileName = json.getString("FILE_NAME");
			System.out.println("FILE_NAME文件名:"+fileName);
			try {
				seqNo += (sdkFileService.uploadFile(fileName, SVR_NO, "02".equals(FILE_TRANS_TP)) + ",");
			} catch (Exception e) {
				setResponseDataset(request, response, DatasetService.getInstace().getDataset(), SysErr.E_DEFAULT, "交易失败");
				return;
			}
		}
		SdkFileInfo info = new SdkFileInfo();
		info.setSeqNo(seqNo.substring(0, seqNo.length()-1));
		IDataset responseData = DatasetService.getInstace().getDataset(info, SdkFileInfo.class);
		
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "交易成功");
	}
	
		// 文件上传并推送
		// 文件获取并下载拼接报文体
		private TfmngTranUploadAndDownloadReqDTO pingBody(IDataset reqDs) {
			String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
			String LOCAL_FULL_FILE_PATH = reqDs.getString("LOCAL_FULL_FILE_PATH");
			String RMT_FULL_FILE_PATH = reqDs.getString("RMT_FULL_FILE_PATH");
			String FILE_NUM = reqDs.getString("FILE_NUM");
			String FILE_STR = reqDs.getString("FILE_STR");
			

			TfmngTranUploadAndDownloadReqDTO reqBody = new TfmngTranUploadAndDownloadReqDTO();
			reqBody.setFILE_TRANS_TP(FILE_TRANS_TP);
			reqBody.setLOCAL_FULL_FILE_PATH(LOCAL_FULL_FILE_PATH);
			reqBody.setRMT_FULL_FILE_PATH(RMT_FULL_FILE_PATH);
			reqBody.setFILE_SVR_ID(reqDs.getString("FILE_SVR_ID"));
			reqBody.setFILE_NUM(Long.parseLong(FILE_NUM));
			List<TfmngTranUploadAndDownloadListReqDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListReqDTO>();

			JSONArray jsonArray = JSONArray.fromObject(FILE_STR);
			for (int a = 0; a < jsonArray.size(); a++) {
				JSONObject json = jsonArray.getJSONObject(a);
				/* AUTH_LIST 内部list */
				TfmngTranUploadAndDownloadListReqDTO reqList = new TfmngTranUploadAndDownloadListReqDTO();
				if (DataUtil.isNullStr(json.getString("ALIAS_FILE_NAME"))) {
					reqList.setFILE_NAME(json.getString("FILE_NAME"));
				} else {
					reqList.setFILE_NAME(json.getString("ALIAS_FILE_NAME"));
				}
				reqList.setSUB_FILE_PATH(json.getString("SUB_FILE_PATH"));
				LIST.add(reqList);
			}

			reqBody.setLIST(LIST);
			return reqBody;
		}
		
	// SDK文件上传并推送
	@RequiresPermissions("user")
	@RequestMapping(value = "upLoadAndPush")
	public void upLoadAndPush(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		//String FILE_MNG_FLG = reqDs.getString("FILE_MNG_FLG");
		
		/*SdkFileInfo info = new SdkFileInfo();
		info.setSeqNo(seqNo.substring(0, seqNo.length()-1));
		IDataset responseData = DatasetService.getInstace().getDataset(info, SdkFileInfo.class);
		
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "交易成功");*/
		TfmngTranUploadAndDownloadReqDTO reqBody = pingBody(reqDs);
		IDataset responseData = null;
		try {
			responseData = sdkFileService.uploadAndPush(reqBody);
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "交易成功");
		} catch (Exception e) {
			setResponseDataset(request, response, DatasetService.getInstace().getDataset(), SysErr.E_DEFAULT, "交易失败:" + e.getMessage() );
		}
	}
	
	// SDK文件下载
	@RequiresPermissions("user")
	@RequestMapping(value = "downLoadFile")
	public void downLoadFile(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SVR_NO = reqDs.getString("SVR_NO");
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String FILE_STR = reqDs.getString("FILE_STR");
		String seqNo = "";
		String fileName = "";
		JSONArray jsonArray = JSONArray.fromObject(FILE_STR);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			fileName = json.getString("FILE_NAME");
			try {
				seqNo += (sdkFileService.downloadFile(fileName, SVR_NO, "02".equals(FILE_TRANS_TP)) + ",");
			} catch (Exception e) {
				setResponseDataset(request, response, DatasetService.getInstace().getDataset(), SysErr.E_DEFAULT, e.getMessage());
				return;
			}
		}
		SdkFileInfo info = new SdkFileInfo();
		info.setFileName(fileName);
		info.setSeqNo(seqNo.substring(0, seqNo.length()-1));
		IDataset responseData = DatasetService.getInstace().getDataset(info, SdkFileInfo.class);
		
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "交易成功");
	}

	//下载文件
	@RequiresPermissions("user")
	@RequestMapping(value = "downLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String file) throws Exception {
		//IDataset reqDs = DatasetService.getInstace().getDataset(request);
		//String file = reqDs.getString("file");
		/** 文件保存路径 */
		//String savePath = String.format(System.getenv("JAVAWORKDIR")) + "/recvFile/";
		//String savePath = String.format(System.getenv("JAVAWORKDIR")) + "/sendFile/";
		FSTransfer fsTrans;
		String savePath="";
		try {
			fsTrans = FSTransfer.getInstance();
			fsTrans.initFSClient();
			savePath=fsTrans.getFsClient().getRecvPath();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String fileName = "";
		try {
			System.out.println(java.net.URLDecoder.decode(file, "UTF-8"));
			fileName = java.net.URLDecoder.decode(file, "UTF-8");
			fileName = fileName.substring(fileName.lastIndexOf("/")+1);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+new String(fileName.getBytes("GBK"),"iso8859-1"));
		File localFile = new File(savePath+file);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(localFile);
			int len = 0;
			byte buffer[] = new byte[1024];
			out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            }finally{
			if(null != in){
				try {
					in.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
	}
	
	// SDK文件上传并推送
	@RequiresPermissions("user")
	@RequestMapping(value = "pullAndDownload")
	public void pullAndDownload(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		//String FILE_MNG_FLG = reqDs.getString("FILE_MNG_FLG");
		TfmngTranUploadAndDownloadReqDTO reqBody = pingBody(reqDs);
		try {
			IDataset responseData = sdkFileService.pullAndDownload(reqBody);
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "交易成功");
		} catch (Exception e) {
			setResponseDataset(request, response, DatasetService.getInstace().getDataset(), SysErr.E_DEFAULT, "交易失败:" + e.getMessage() );
			return;
		}
	}
	
	// SDK文件进度查询
	@RequiresPermissions("user")
	@RequestMapping(value = "scheduleQry")
	public void scheduleQry(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SEQ_NO = reqDs.getString("SEQ_NO");
		
		RedisClusterOperator oper = RedisClusterOperator.getInstance();
		String rdJson = oper.getJedisCluster().get(SEQ_NO);
		System.out.println(SEQ_NO+":"+rdJson);
		
		SdkFileInfo info = new SdkFileInfo();
		if(rdJson == null || "".equals(rdJson)){
			info.setCmpleSize(0);
			info.setTotSize(0);
		}else{
			JSONObject jsonObj = JSONObject.fromObject(rdJson);
			info.setFileName(jsonObj.getString("fileName"));
			info.setCmpleSize(jsonObj.getLong("completeSize"));
			info.setTotSize(jsonObj.getLong("totalSize"));
		}
		
		System.out.println("completeSize["+info.getCmpleSize()+"],totalSize["+info.getTotSize()+"]");
		
		IDataset responseData = DatasetService.getInstace().getDataset(info, SdkFileInfo.class);
		
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "交易成功");
	}
	
}
