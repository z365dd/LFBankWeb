package com.adtec.sys.common.utils.excel.fieldtype;

import java.util.List;

import com.adtec.framework.common.util.CollectionUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.SystemService;
import com.google.common.collect.Lists;

/**
 * 字段类型转换
 * 
 * @version 2017-8-11
 * add by chenyl
 */
public class UserListType {

	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
	
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<User> userList = Lists.newArrayList();
		List<User> allUserList = systemService.findAllUser();
		for (String s : StringUtil.split(val, ",")){
			for (User e : allUserList){
				if (StringUtil.trimToEmpty(s).equals(e.getName())){
					userList.add(e);
				}
			}
		}
		return userList.size()>0?userList:null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			@SuppressWarnings("unchecked")
			List<User> userList = (List<User>)val;
			return CollectionUtil.extractToString(userList, "name", ", ");
		}
		return "";
	}
}
