package com.adtec.sys.common.utils.excel.fieldtype;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.modules.sys.entity.Corporation;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 */
public class CorporationType {

    /**
     * 获取对象值（导入）
     */
    public static Object getValue(String val) {
        for (Corporation e : UserUtils.getCorporationList()) {
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
        if (val != null && ((Corporation) val).getName() != null) {
            return ((Corporation) val).getName();
        }
        return "";
    }
}
