package com.adtec.sys.common.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.tags.PermissionTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shiro HasPermissions Tag.
 * 
 * 
 */
public class HasPermissionsTag extends PermissionTag {
	private final static Logger logger = LoggerFactory.getLogger(HasPermissionsTag.class);
	private static final long serialVersionUID = 1L;
	private static final String PERMISSION_NAMES_DELIMETER = ",";
	private static final String PERMISSION_ANNO = "anno";

	protected boolean showTagBody(String permissionNames) {
		boolean hasAnyPermission = false;

		Subject subject = getSubject();

		if (subject != null) {
			try{
				// 如果是预览时不进行权限拦截
				ShiroHttpServletRequest request = (ShiroHttpServletRequest)((WebSubject) SecurityUtils.getSubject())
					.getServletRequest();
				logger.debug("request:"+request.getRequestURI());
				if(null!=request.getRequestURI() && request.getRequestURI().indexOf("/b_ide/bootstrap/previewContent.jsp")>-1){
					hasAnyPermission = true;
					return hasAnyPermission;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			// Iterate through permissions and check to see if the user has one
			// of the permissions
			for (String permission : permissionNames
					.split(PERMISSION_NAMES_DELIMETER)) {
				//add by chenyl for 对前台按钮权限为anno的做过滤允许操作
				if (subject.isPermitted(permission.trim()) || PERMISSION_ANNO.equals(permission.trim())) {
					hasAnyPermission = true;
					break;
				}

			}
		}

		return hasAnyPermission;
	}

}
