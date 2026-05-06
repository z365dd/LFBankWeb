package com.adtec.sys.modules.sys.web;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.google.common.collect.Lists;

/**
 * 单表生成Controller
 * 
 * @author xiaofang
 * @version 20170808
 */
@Controller
@RequestMapping(value = "${adminPath}/msmall/preview")
public class PreviewController extends BaseController {
	
	/**
	 * 获取文件列表(ppt、word、excel、pdf)
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset responseData = DatasetService.getInstace().getDataset();
		
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		int total = 0;
		logger.debug("start=" + start + ",limit=" + limit);
		
		// 预览原文件路径
		String path = ParamUtil.getUploadFile()+"/preview/";
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		
		// 获取文件列表
		File[] files = f.listFiles(new FilenameFilter(){
			/* (non-Javadoc)
			 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
			 */
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.toLowerCase().endsWith(".doc") || name.toLowerCase().endsWith(".docx") ||
						name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx") ||
						name.toLowerCase().endsWith(".ppt") || name.toLowerCase().endsWith(".pptx") ||
						name.toLowerCase().endsWith(".pdf")){
					return true;
				}else{
					return false;
				}
			}
		});
		
		// 创建返回的列
		responseData.addColumn("stat");
		responseData.addColumn("file");
		responseData.addColumn("fileUrl");
		responseData.addColumn("action");
		responseData.beforeFirst();
		if(null!=files && files.length>0){
			int len = files.length;
			for(int i=start-1;i<len && i<(start+limit);i++){
				responseData.appendRow();
				responseData.updateString("stat", ""+i);
				responseData.updateString("file", files[i].getName());
				responseData.updateString("fileUrl", files[i].getAbsolutePath());
				responseData.updateString("action", "<a href=\"#\" onclick=\"previewFile('"+FileUtil.path(files[i].getAbsolutePath())+"')\">预览</a>");
				total++;
			}
		}
		responseData.setTotalCount(total);
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "查询成功");
	}

	/**
	 * 文件预览
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = { "show" })
	public void show(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		String file = reqDs.getString("file");	// 获取对应文件的绝对路径
		String isPdf = "";
		if(file.toLowerCase().endsWith(".pdf")){
			// 如果为pdf文件时只需要拷贝到预览目录
			isPdf = "1";
		}else{
			isPdf = "0";
		}
    	String preUrl = request.getRequestURL().toString().split(request.getContextPath())[0] + request.getContextPath();
		String htmlUrl = FileUtil.getPreviewFilePath(file, true);
		// 把相对路径替换成绝对访问路径
		Map row = new HashMap();
		row.put("previewUrl", htmlUrl);
		row.put("isPdf", isPdf);

		IDataset responseData = DatasetService.getInstace().getDataset(row);

		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "预览交易成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);		
		String fileIds = reqDs.getString("__FILE_IDS");
		String downloadFileName = reqDs.getString("__DOWNLOAD_FILENAME");
		if(DataUtil.isNullStr(fileIds)){
			throw new BaseException(SysErr.E_MESSAGE, "文件ID列表不能空");
		}
		String[] fIds = fileIds.split(",");
		if(fIds.length>1){
			// 多文件下载
			List<String> uriList = Lists.newArrayList();
			for(String fileId:fIds){
				uriList.add(fileId);
			}
			FileUtil.DownLoadFileByUri(uriList, downloadFileName, response);
		} else {
			FileUtil.DownLoadFileByUri(fIds[0], downloadFileName, response);
		}
	}
}