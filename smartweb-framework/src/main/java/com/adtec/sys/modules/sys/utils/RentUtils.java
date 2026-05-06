package com.adtec.sys.modules.sys.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.sys.dao.OfficeDao;
import com.adtec.sys.modules.sys.dao.RentDao;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Rent;

public class RentUtils {
	private static RentDao rentDao = SpringContextHolder.getBean(RentDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	
	public static String getRentAction(String id, String stat, String defaultValue){
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(stat)){
			String ret;
			if("0".equals(stat)){
				ret = "<a href=\""
						+ Servlets.getRequest().getContextPath()+ParamUtil.getAdminPath()
						+ "/sys/rent/assign?id="
						+ id
						+ "\">分配</a>";
			}
			else{
				ret = "分配";
			}
			return ret;
		}
		return defaultValue;
	}
	
	/**
	 * 根据租户id获取租户信息
	 * @param id
	 * @return
	 * add by chenyl--2018-5-16
	 */
	public static Rent getRent(String id){
		Rent rent = rentDao.get(id);
		if (rent == null ) {
			return null;
		}
		return rent;
	}
}
