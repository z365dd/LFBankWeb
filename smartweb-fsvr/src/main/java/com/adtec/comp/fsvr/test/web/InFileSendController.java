package com.adtec.comp.fsvr.test.web;

import com.adtec.comp.fsvr.dto.*;
import com.adtec.comp.fsvr.test.service.InFileSendService;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.starring_file.client.FSTransfer;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.net.HttpHeaders;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/test")
public class InFileSendController extends BaseController {
	@Autowired
	private InFileSendService inFileSendService;

	/**
	 * 返回文件上传推送页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "upLoadPut" })
	public String synChk(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/upLoadPut";
	}

	/**
	 * 返回文件获取下载页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "downLoadGet" })
	public String asynChk(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/downLoadGet";
	}

	/**
	 * 返回文件传输结果查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileResultQry" })
	public String fileResultQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fileResultQry";
	}
	
	/**
	 * 返回文件上传页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fsvrFileUpload" })
	public String gofsvrFileUpload(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrFileUpload";
	}
	
	/**
	 * 返回文件下载页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fsvrFileDownload" })
	public String gofsvrFileDownload(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrFileDownload";
	}
	
	/**
	 * 返回文件获取页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fsvrFileGet" })
	public String gofsvrFileGet(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrFileGet";
	}
	
	/**
	 * 返回文件推送页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fsvrFilePut" })
	public String fsvrFilePut(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrFilePut";
	}
	/**
	 * 返回文件搜索页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fsvrFileSearch" })
	public String fsvrFileSearch(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/test/fsvrFileSearch";
	}
	/**
	 * 上传文件 返回本地保存信息
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "upload")
	public void upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile[] file) {
		/** 文件保存路径 */
		String savePath = ParamUtil.getUploadFile() + "/fsvr";
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

	// 文件上传并推送
	// 文件获取并下载拼接报文体
	private TfmngTranUploadAndDownloadReqDTO pingBody(IDataset reqDs) {
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String LOCAL_FULL_FILE_PATH = reqDs.getString("LOCAL_FULL_FILE_PATH");
		String RMT_FULL_FILE_PATH = reqDs.getString("RMT_FULL_FILE_PATH");
		String FILE_NUM = reqDs.getString("FILE_NUM");
		String FILE_STR = reqDs.getString("FILE_STR");
		
	/*	String BUSI_NO = reqDs.getString("companyName");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String TRAN_CODE = reqDs.getString("TRAN_CODE");
		
		String DEF_VAL = reqDs.getString("DEF_VAL");*/
		
		
		//标志：1表示  获取并下载，ftp上传到  远程路径  给文件传输获取
		//    0表示  上传并推送，ftp上传到 本地路径  给文件传输拿到推送出去
		String FLG = reqDs.getString("FLG");

		TfmngTranUploadAndDownloadReqDTO reqBody = new TfmngTranUploadAndDownloadReqDTO();
		
		/*reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setTRAN_CODE(TRAN_CODE);
		String DIM_FLG = reqDs.getString("DIM_FLG");
		reqBody.setDIM_FLG(DIM_FLG);*/
		reqBody.setFILE_TRANS_TP(FILE_TRANS_TP);
		reqBody.setLOCAL_FULL_FILE_PATH(LOCAL_FULL_FILE_PATH);
		if("0".equals(FLG)){
			reqBody.setRMT_FULL_FILE_PATH(RMT_FULL_FILE_PATH);
		}else if("1".equals(FLG)){
			reqBody.setRMT_FULL_FILE_PATH(RMT_FULL_FILE_PATH);
		}
		reqBody.setFILE_SVR_ID(reqDs.getString("FILE_SVR_ID"));
		reqBody.setFILE_NUM(Long.parseLong(FILE_NUM));
		List<TfmngTranUploadAndDownloadListReqDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListReqDTO>();

		JSONArray jsonArray = JSONArray.fromObject(FILE_STR);
		for (int a = 0; a < jsonArray.size(); a++) {
			JSONObject json = jsonArray.getJSONObject(a);
			/* AUTH_LIST 内部list */
			TfmngTranUploadAndDownloadListReqDTO reqList = new TfmngTranUploadAndDownloadListReqDTO();
			reqList.setFILE_NAME(json.getString("FILE_NAME"));
			reqList.setSUB_FILE_PATH(json.getString("SUB_FILE_PATH"));
			reqList.setALIAS_FILE_NAME(json.getString("ALIAS_FILE_NAME"));
			LIST.add(reqList);
			
//			String ftpPath="";
//			if("0".equals(FLG)){
//				/* ftpPath ftp文件存在位置 */
//				ftpPath = "./print/"+reqBody.getLOCAL_FULL_FILE_PATH()+"/"+json.getString("SUB_FILE_PATH");
//			}else if("1".equals(FLG)){
//				ftpPath = "./print/"+reqBody.getRMT_FULL_FILE_PATH()+"/"+json.getString("SUB_FILE_PATH");
//			}
//
//			/* String ftpPath = "print";*/
//			/** 文件保存路径 */
//			String savePath = ParamUtil.getUploadFile() + "/fsvr";
//			// 提交时调文件传输组件
//			FtpUtil.uploadFtpFile(FtpUtil.getFtpClient("fsvr"),ftpPath,savePath,json.getString("FILE_NAME"));
		}

		reqBody.setLIST(LIST);
		return reqBody;
	}

	// 文件上传并推送
	@RequiresPermissions("user")
	@RequestMapping(value = "upLoadPutSend")
	public void upLoadPutSend(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		TfmngTranUploadAndDownloadReqDTO reqBody = pingBody(reqDs);

		IDataset resDs = inFileSendService.upLoadPutSend(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	// 文件上传拼接报文体
	private TfmngTranUploadReqDTO getUploadReqBody(IDataset reqDs) {
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String PUB_FILE_PATH = reqDs.getString("PUB_FILE_PATH");
		String FILE_NUM = reqDs.getString("FILE_NUM");
		String FILE_STR = reqDs.getString("FILE_STR");
		
		TfmngTranUploadReqDTO reqBody = new TfmngTranUploadReqDTO();
		reqBody.setFILE_TRANS_TP(FILE_TRANS_TP);
		reqBody.setPUB_FILE_PATH(PUB_FILE_PATH);
		reqBody.setFILE_SVR_ID(reqDs.getString("FILE_SVR_ID"));
		reqBody.setFILE_NUM(Long.parseLong(FILE_NUM));
		List<TfmngTranUploadAndDownloadListReqDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListReqDTO>();

		JSONArray jsonArray = JSONArray.fromObject(FILE_STR);
		for (int a = 0; a < jsonArray.size(); a++) {
			JSONObject json = jsonArray.getJSONObject(a);
			/* AUTH_LIST 内部list */
			TfmngTranUploadAndDownloadListReqDTO reqList = new TfmngTranUploadAndDownloadListReqDTO();
			reqList.setFILE_NAME(json.getString("FILE_NAME"));
			reqList.setSUB_FILE_PATH(json.getString("SUB_FILE_PATH"));
			reqList.setALIAS_FILE_NAME(json.getString("ALIAS_FILE_NAME"));
			LIST.add(reqList);
			
		}

		reqBody.setLIST(LIST);
		return reqBody;
	}
	
	// 文件上传
	@RequiresPermissions("user")
	@RequestMapping(value = "fileUpLoad")
	public void fileUpLoad(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		TfmngTranUploadReqDTO reqBody = getUploadReqBody(reqDs);

		IDataset resDs = inFileSendService.fsvrFildUpLoad(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	// 文件获取并下载
	@RequiresPermissions("user")
	@RequestMapping(value = "downLoadGetSend")
	public void downLoadGetSend(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		TfmngTranUploadAndDownloadReqDTO reqBody = pingBody(reqDs);

		TfmngTranUploadAndDownloadResDTO resDs = inFileSendService.downLoadGetSend(reqBody);
		//setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
		String jsonStr =JSON.toJSONString(resDs);
	    System.out.println("JSON字符串:"+jsonStr);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "交易成功");
	}
	
	// 文件获取
	@RequiresPermissions("user")
	@RequestMapping(value = "fileGetSend")
	public void fileGetSend(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String PUB_FILE_PATH = reqDs.getString("PUB_FILE_PATH");
		String FILE_NUM = reqDs.getString("FILE_NUM");
		String FILE_STR = reqDs.getString("FILE_STR");
		TFmngTranPullReqDTO reqBody = new TFmngTranPullReqDTO();
		reqBody.setFILE_TRANS_TP(FILE_TRANS_TP);
		reqBody.setPUB_FILE_PATH(PUB_FILE_PATH);
		reqBody.setFILE_SVR_ID(reqDs.getString("FILE_SVR_ID"));
		reqBody.setFILE_NUM(Long.parseLong(FILE_NUM));
		List<TfmngTranUploadAndDownloadListReqDTO> LIST = new ArrayList<TfmngTranUploadAndDownloadListReqDTO>();

		JSONArray jsonArray = JSONArray.fromObject(FILE_STR);
		for (int a = 0; a < jsonArray.size(); a++) {
			JSONObject json = jsonArray.getJSONObject(a);
			/* AUTH_LIST 内部list */
			TfmngTranUploadAndDownloadListReqDTO reqList = new TfmngTranUploadAndDownloadListReqDTO();
			reqList.setFILE_NAME(json.getString("FILE_NAME"));
			reqList.setSUB_FILE_PATH(json.getString("SUB_FILE_PATH"));
			reqList.setALIAS_FILE_NAME(json.getString("ALIAS_FILE_NAME"));
			LIST.add(reqList);
		}
		reqBody.setLIST(LIST);
		IDataset resDs = inFileSendService.fileGet(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	// 文件推送
	@RequiresPermissions("user")
	@RequestMapping(value = "filePush")
	public void filePush(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String PUB_FILE_PATH = reqDs.getString("PUB_FILE_PATH");
		String FILE_SET_SEQ = reqDs.getString("FILE_SET_SEQ");
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		TFmngTranPushReqDTO reqBody = new TFmngTranPushReqDTO();
		reqBody.setFILE_TRANS_TP(FILE_TRANS_TP);
		reqBody.setFILE_SET_SEQ(FILE_SET_SEQ);
		reqBody.setPUB_FILE_PATH(PUB_FILE_PATH);
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		IDataset resDs = inFileSendService.filePush(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	// 文件搜索
	@RequiresPermissions("user")
	@RequestMapping(value = "fileSearch")
	public void fileSearch(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String SEARCH_TP = reqDs.getString("SEARCH_TP");
		String FILE_PATH = reqDs.getString("FILE_PATH");
		String EXPR = reqDs.getString("EXPR");
		TFmngTranSearchReqDTO reqBody = new TFmngTranSearchReqDTO();
		reqBody.setFLG("0");
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setSEARCH_TP(SEARCH_TP);
		reqBody.setFILE_PATH(FILE_PATH);
		reqBody.setEXPR(EXPR);
		IDataset resDs = inFileSendService.fileSearch(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	

	//文件结果查询
	@RequiresPermissions("user")
	@RequestMapping(value = "fileResultQrySend")
	public void fileResultQrySend(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		String SEQ_NO = reqDs.getString("SEQ_NO");
		TfmngTranResultQueryReqDTO reqBody = new TfmngTranResultQueryReqDTO();
		//reqBody.setSEQ_NO(SEQ_NO);
		reqBody.setREQ_SEQ(SEQ_NO);

		IDataset resDs = inFileSendService.fileResultQrySend(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	// 文件下载接口调用
	@RequiresPermissions("user")
	@RequestMapping(value = "fileDownload")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) {

		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_TRANS_TP = reqDs.getString("FILE_TRANS_TP");
		String FILE_SET_SEQ = reqDs.getString("FILE_SET_SEQ");
		String PUB_FILE_PATH = reqDs.getString("PUB_FILE_PATH");
		TFmngTranDownloadReqDTO reqBody = new TFmngTranDownloadReqDTO();
		reqBody.setFILE_TRANS_TP(FILE_TRANS_TP);
		reqBody.setFILE_SET_SEQ(FILE_SET_SEQ);
		reqBody.setPUB_FILE_PATH(PUB_FILE_PATH);
		TFmngTranDownloadResDTO resDs = inFileSendService.fileDownload(reqBody); 
		String jsonStr =JSON.toJSONString(resDs);
	    System.out.println("JSON字符串:"+jsonStr);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "交易成功");
	}

	
	//下载文件
	@RequiresPermissions("user")
	@RequestMapping(value = "downLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String file) throws Exception {
		//IDataset reqDs = DatasetService.getInstace().getDataset(request);
		//String file = reqDs.getString("file");
		String fileName = "";
		try {
			System.out.println("fileName值:"+java.net.URLDecoder.decode(file, "UTF-8"));
			fileName = java.net.URLDecoder.decode(file, "UTF-8");
			fileName = fileName.substring(fileName.lastIndexOf("/")+1);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+new String(fileName.getBytes("GBK"),"iso8859-1"));
		FSTransfer fsTrans=FSTransfer.getInstance();
		fsTrans.initFSClient();
		String filePath=fsTrans.getFsClient().getRecvPath();
		File localFile = new File(filePath+file);
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
		} finally {
			try {
				if (in != null) in.close();
			} catch (Exception e) {
				System.out.println("出现异常");
			}
		}
	}
	//下载文件
	@RequestMapping(value = "downLoadPub")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String filePath, String aliasFileName) throws Exception {
		//IDataset reqDs = DatasetService.getInstace().getDataset(request);
		//String file = reqDs.getString("file");
		String fileName = "";
		String aliasFileNameTmp = "";
		String newFilePath = "";
		try {
			System.out.println("fileName值:"+java.net.URLDecoder.decode(filePath, "UTF-8"));
			newFilePath = fileName = java.net.URLDecoder.decode(filePath, "UTF-8");
			fileName = fileName.substring(fileName.lastIndexOf("/")+1);
			aliasFileNameTmp = java.net.URLDecoder.decode(aliasFileName,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+new String(aliasFileNameTmp.getBytes("GBK"),"iso8859-1"));
		FSTransfer fsTrans=FSTransfer.getInstance();
		fsTrans.initFSClient();
		String tmpFilePath=fsTrans.getFsClient().getRecvPath();
		System.out.println("FILE_URL:"+tmpFilePath+filePath+"|"+aliasFileNameTmp);
		File localFile = new File(tmpFilePath+newFilePath);
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
}
