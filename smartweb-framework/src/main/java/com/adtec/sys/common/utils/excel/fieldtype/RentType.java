package com.adtec.sys.common.utils.excel.fieldtype;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 */
public class RentType {

    /**
     * 获取对象值（导入）
     */
    public static Object getValue(String val) {
        for (Rent e : UserUtils.getRentList()) {
            if (StringUtil.trimToEmpty(val).equals(e.getName())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 设置对象值（导出）
     */
    public static String setValue(Object val) {
        if (val != null && ((Rent) val).getName() != null) {
            return ((Rent) val).getName();
        }
        return "";
    }
}
