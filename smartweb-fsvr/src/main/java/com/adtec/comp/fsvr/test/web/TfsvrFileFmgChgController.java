package com.adtec.comp.fsvr.test.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import com.adtec.comp.fsvr.dto.TfmngTranUploadReqDTO;
import com.adtec.comp.fsvr.test.service.TfsvrFileFmgChgService;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.starring_file.client.FSTransfer;
import com.adtec.sys.common.web.BaseController;
import com.google.common.net.HttpHeaders;

@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/fileFmgChgTest")
public class TfsvrFileFmgChgController extends BaseController {
	@Autowired
	private TfsvrFileFmgChgService fileFmgChgService;
	/**
	 * 返回文件格式转换页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileFmtSynChg" })
	public String fileFmtSynChg(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/fileFmgChgTest/fileFmtSynChg";
	}
	
	
	// 文件上传
/*	@RequiresPermissions("user")
	@RequestMapping(value = "fileUpLoad")
	public void fileUpLoad(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		TfmngTranUploadReqDTO reqBody = getUploadReqBody(reqDs);

		IDataset resDs = fileFmgChgService.fsvrFildUpLoad(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}*/
		
		
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
