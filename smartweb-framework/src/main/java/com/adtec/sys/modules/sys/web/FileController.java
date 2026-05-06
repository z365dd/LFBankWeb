/**
 * 系统名称: SmartWeb平台
 * 模块名称: 文件控制类
 * 类  名  称: FileController.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年5月22日 下午16:45:35<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.sys.web;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.FileDO;
import com.adtec.sys.modules.sys.service.FileService;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value="${adminPath}/sys/file")
public class FileController extends BaseController{
	
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(FileController.class);
	@Autowired
	private FileService fileService;

	
	/**
	 * 管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"managePage"})
	public String managePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/msmall/file/fileManage";
	}

	/**
	 * 列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"listPage"})
	public String listPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/msmall/file/fileList";
	}

	/**
	 * 新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"addPage"})
	public String addPage(HttpServletRequest request, HttpServletResponse response) {	
		return "starring/msmall/file/fileAddForm";
	}

	/**
	 * 详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"detailPage"})
	public String detailPage(HttpServletRequest request, HttpServletResponse response) {	
		return "starring/msmall/dict/fileDetailForm";
	}
	/**
	 * 修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"updatePage"})
	public String updatePage(HttpServletRequest request, HttpServletResponse response) {	
		return "starring/msmall/dict/fileUpdateForm";
	}
	
	/**
	 * 词素上传文件 返回本地保存信息
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "upload/morpheme")
	public void uploadMorpheme(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile[] file) {
		/** 文件保存路径 */
		String savePath = ParamUtil.getUploadFile() + "/msmall/dict/morpheme";
		
		FileDO fileDO = fileService.upload(savePath, file);
		IDataset responseData = DatasetService.getInstace().getDataset(fileDO, FileDO.class);
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "上传成功");
		
		/*// 创建文件保存目录
		FileUtil.createDirectory(savePath);

		if (file != null && file.length > 0) {
			try {

				FileDO fileDO = new FileDO();

				// 文件名
				fileDO.setName((file[0].getOriginalFilename()).substring(0,file[0].getOriginalFilename().indexOf(".")));
				String fileType = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().indexOf(".") + 1);
				fileDO.setFileType(fileType);
				// saveName = UUID.randomUUID().toString();
				fileDO.setSaveName(UUID.randomUUID().toString() + fileType);
				fileDO.setSavePath(savePath);
				// 保存文件
				FileUtil.SaveFileFromInputStream(file[0].getInputStream(), savePath,
						fileDO.getSaveName()+ "." + fileDO.getFileType());
				
				IDataset responseData = DatasetService.getInstace().getDataset(fileDO, FileDO.class);
				setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "上传成功");

			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseException(SysErr.E_MESSAGE, "上传出现异常！本地保存文件失败。");
			}
		} else {
			throw new BaseException(SysErr.E_MESSAGE, "没有检测到文件！");
		}*/
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
				FileDO fileDO = fileService.get(fileId);
				if(null==fileDO){
					throw new BaseException(SysErr.E_MESSAGE, "文件ID["+fileId+"]不存在");
				}
				String copyPath = (fileDO.getSavePath()).substring(0,fileDO.getSavePath().lastIndexOf("/") +1) + fileDO.getFileName();
				FileUtil.copyFileCover(fileDO.getSavePath(), copyPath, true);
				uriList.add(copyPath);
			}
			FileUtil.DownLoadFileByUri(uriList, downloadFileName, response);
			for (int i = 0; i < uriList.size(); i++) {
				FileUtil.deleteFile(uriList.get(i));
			}
		} else {
			FileDO fileDO = fileService.get(fIds[0]);
			if(null==fileDO){
				throw new BaseException(SysErr.E_MESSAGE, "文件ID["+fIds[0]+"]不存在");
			}
			FileUtil.DownLoadFileByUri(fileDO.getSavePath(), downloadFileName, response);
		}
	}
	
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
		String folder = reqDs.getString("folder");
		int total = 0;
		logger.debug("start=" + start + ",limit=" + limit);
		
		// 预览原文件路径
		String path = ParamUtil.getUploadFile()+"/msmall/" + folder;
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
	 * 
	 * 保存文件信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "insert" })
	public void insert(FileDO fileDO,HttpServletRequest request, HttpServletResponse response) {
		StringBuffer msg = new StringBuffer();
		IDataset responseData = DatasetService.getInstace().getDataset();
		if (fileService.insert(fileDO)) {
			msg.append("文件添加成功");
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, msg.toString());
		}else {
			msg.append("文件添加失败");
			setResponseDataset(request, response, responseData, SysErr.E_DEFAULT, msg.toString());
		}
	}
	
	/**
	 * 文件预览
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "readPreview")
	public void readPreview(HttpServletRequest request, HttpServletResponse response){
	    
	}
	
	
}
