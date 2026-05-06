package com.adtec.sys.modules.sys.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.framework.common.constant.Constants;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.ms.msagent.util.Encodes;
import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.modules.sys.dao.FileDao;
import com.adtec.sys.modules.sys.entity.FileDO;

@Service
public class FileService {
	@Autowired
	private FileDao fileDao;
	/**
	 * 根据ID获取文件信息
	 * @param fileId
	 * @return
	 */
	public FileDO get(String fileId){
		FileDO fileDO = new FileDO();
		fileDO.setId(fileId);
		return fileDao.get(fileDO);
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @param file
	 */
	public FileDO upload(String savePath, MultipartFile[] file) {
		// 创建文件保存目录
		FileUtil.createDirectory(savePath);

		FileDO fileDO = null;
		if (file != null && file.length > 0) {
			try {
				fileDO = new FileDO();
				// 文件名
				fileDO.setFileName((file[0].getOriginalFilename()).substring(0,file[0].getOriginalFilename().indexOf(".")));
				String fileType = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().indexOf(".") + 1);
				fileDO.setFileTp(fileType);
				// saveName = UUID.randomUUID().toString();
				fileDO.setSaveName(UUID.randomUUID().toString() + fileType);
				fileDO.setSavePath(savePath);
				// 保存文件
				FileUtil.SaveFileFromInputStream(file[0].getInputStream(), savePath,
						fileDO.getSaveName()+ "." + fileDO.getFileTp());

			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseException(SysErr.E_MESSAGE, "上传出现异常！本地保存文件失败。");
			}
		} else {
			throw new BaseException(SysErr.E_MESSAGE, "没有检测到文件！");
		}
		return fileDO;
	}
	
	/**
	 * 新增文件
	 * @param fileDO
	 * @return
	 */
	public boolean insert(FileDO fileDO){
		fileDO.preInsert();
		int rs = fileDao.insert(fileDO);
		return rs>0? true:false;
	}
	/**
	 * 新增文件
	 * @param fileDO
	 * @return
	 */
	public boolean insertByNewSession(FileDO fileDO){
		fileDO.preInsert();
		int rs = fileDao.insertByNewSession(fileDO);
		return rs>0? true:false;
	}
	
	/**
	 * 更新文件
	 * @param fileDO
	 * @return
	 */
	public boolean update(FileDO fileDO){
		fileDO.preUpdate();
		int rs = fileDao.update(fileDO);
		return rs>0? true:false;
	}
	
	/**
	 * 根据文件id获取描述 TXT文件
	 * @param fileId
	 */
	public String getTXTContent(String fileId){
		FileDO contentFileDO = get(fileId);	// 获取对应存储描述文件路径并读取相关内容
		String contentStr = "";
		if(null!=contentFileDO && !DataUtil.isNullStr(contentFileDO.getSavePath())){
			contentStr = FileUtil.readTxtFileByPath(contentFileDO.getSavePath());
		}
		return contentStr;
	}

	public boolean delete(FileDO file) {
		int rs = fileDao.delete(file);
		return rs>0?true:false;
	}
	
	/**
	 * 根据条件查询文件是否存在
	 * @param fileDO
	 * @return
	 */
	public boolean isExist(FileDO fileDO){
		return fileDao.isExist(fileDO)>0? true:false;
	}

	public int updateForHandler(FileDO file) {
		return fileDao.updateForHandler(file);
	}
	
	/**
	 * 列表查询字典数据条数
	 * @param fileDO	存放查询的条件
	 * @param start		开始条数
	 * @param limit		结束条数
	 * @return
	 */
	public List<FileDO> list(FileDO fileDO, int start, int limit){
		return fileDao.list(fileDO, start, limit);
	}
	
	public int getListTotal(FileDO fileDO){
		return fileDao.getTotal(fileDO);
	}
	
	/**
	 * 通过文件上传组件保存文件到文件管理表中
	 * @param uploadFileUrl		文件上传路径
	 * @param resType			保存的文件资源类型，当空时默认为公共级
	 * @param errorMsg			错误信息
	 * @return	返回保存的文件ID
	 */
	public String saveUploadFile(String uploadFileUrl, String resType, String errorMsg){
		// 获取文件名
		String fileUrl = Encodes.urlDecode(uploadFileUrl);
		String fileName = FileUtil.getFileName(fileUrl);
		// 获取文件类型
		String fileType = FileUtil.getFileType(fileUrl);
		// 通过文件管理器上传的图片保存的真实路径
		String realPath = ParamUtil.getUploadFile() + ParamUtil.USERFILES_BASE_URL + fileUrl.split(ParamUtil.USERFILES_BASE_URL)[1];
		// 获取对应的预览路径
		String previewPath = FileUtil.getPreviewFilePath(realPath, false);
		FileDO file = new FileDO();
		file.setFileName(fileName);
		file.setSaveName(IdGen.uuid()+"."+fileType);
		file.setFileTp(fileType);
		file.setSavePath(realPath.replaceAll(ParamUtil.getUploadFile(), ""));
		file.setPrevPath(previewPath);
		file.setQuoteFlg(FileDO.IS_USED_Y);// 设置为已引用
		if(!DataUtil.isNullStr(resType)){
			file.setResTp(resType);
		}else{
			file.setResTp(Constants.PUB_RES);
		}	
		if(!insert(file)){
			if(!DataUtil.isNullStr(errorMsg)){
				throw new BaseException(SysErr.E_MESSAGE, errorMsg);
			}else{
				throw new BaseException(SysErr.E_MESSAGE, "保存文件失败!");
			}
		}
		return file.getId();
	}
}
