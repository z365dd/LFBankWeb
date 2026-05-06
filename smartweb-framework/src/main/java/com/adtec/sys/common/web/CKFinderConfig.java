package com.adtec.sys.common.web;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.ckfinder.connector.configuration.Configuration;
import com.ckfinder.connector.data.AccessControlLevel;
import com.ckfinder.connector.utils.AccessControlUtil;

/**
 * CKFinder配置
 * 
 * @version 2014-06-25
 */
public class CKFinderConfig extends Configuration {
	private final static Logger log = LoggerFactory.getLogger(CKFinderConfig.class);
	public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);
    }
	
	@Override
    protected Configuration createConfigurationInstance() {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return new CKFinderConfig(this.servletConf);
		}
		boolean isView = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:view");
		boolean isUpload = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:upload");
		boolean isEdit = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:edit");
		boolean notAllow = false;
		AccessControlLevel alc = this.getAccessConrolLevels().get(0);
		/*20181226 add by chenyl for 添加对文件上传组件的权限控制设置*/
		boolean __IsAllVisiable = (Boolean)GVarContainer.getVar("__IsAllVisiable");
		User user = UserUtils.get(principal.getId());
		if(null != user && user.isManager()){
			// 如果当前用户为超级管理员，则允许所有操作
			alc.setFolderView(isView);
			alc.setFolderCreate(isEdit);
			alc.setFolderRename(isEdit);
			alc.setFolderDelete(isEdit);
			alc.setFileView(isView);
			alc.setFileUpload(isUpload);
			alc.setFileRename(isEdit);
			alc.setFileDelete(isEdit);
		}else{
			// 非超级管理员用户的对于当前私有的用户目录下的文件夹和文件拥有所有操作权限
			if(!__IsAllVisiable){
				alc.setFolderView(isView);
				alc.setFolderCreate(isEdit);
				alc.setFolderRename(isEdit);
				alc.setFolderDelete(isEdit);
				alc.setFileView(isView);
				alc.setFileUpload(isUpload);
				alc.setFileRename(isEdit);
				alc.setFileDelete(isEdit);
			}else{
				// 非超级管理员的对于公共目录下的只有对文件的上传、查看、下载权限
				alc.setFolderView(isView);
				alc.setFolderCreate(notAllow);
				alc.setFolderRename(notAllow);
				alc.setFolderDelete(notAllow);
				alc.setFileView(isView);
				alc.setFileUpload(isUpload);
				alc.setFileRename(notAllow);
				alc.setFileDelete(notAllow);
			}
		}
		log.info("原始resourceType："+alc.getResourceType()+",原始folder："+alc.getFolder());

		AccessControlUtil.getInstance(this).loadACLConfig();
		try {
			// 20180620 add by chenyl for 
			log.info("当前用户["+principal.getLoginName()+"]是否读取共目录["+__IsAllVisiable+"]");
			this.baseURL = FileUtil.path(FileUtil.path(Servlets.getRequest().getContextPath() + ParamUtil.USERFILES_BASE_URL + (__IsAllVisiable?"":(principal+ "/"))));
			this.baseDir = FileUtil.path(FileUtil.path(ParamUtil.getUserfilesBaseDir() + ParamUtil.USERFILES_BASE_URL + (__IsAllVisiable?"":(principal+ "/"))));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new CKFinderConfig(this.servletConf);
    }

    @Override  
    public boolean checkAuthentication(final HttpServletRequest request) {
        return UserUtils.getPrincipal()!=null;
    }

}
