/**
 * 
 */
package com.adtec.sys.common.utils.excel.fieldtype;

import java.util.List;

import com.adtec.framework.common.util.CollectionUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.service.SystemService;
import com.google.common.collect.Lists;

/**
 * 字段类型转换
 * 
 * @version 20170425
 * @author chenyl
 */
public class RentListType {

	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
	
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Rent> rentList = Lists.newArrayList();
		List<Rent> allRentList = systemService.findAllRent();
		for (String s : StringUtil.split(val, ",")){
			for (Rent e : allRentList){
				if (StringUtil.trimToEmpty(s).equals(e.getName())){
					rentList.add(e);
				}
			}
		}
		return rentList.size()>0?rentList:null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			@SuppressWarnings("unchecked")
			List<Rent> rentList = (List<Rent>)val;
			return CollectionUtil.extractToString(rentList, "name", ", ");
		}
		return "";
	}
	
}
