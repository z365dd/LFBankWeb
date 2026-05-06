package com.adtec.framework.common.config;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;

public class XSSFilterConfigUtil {

    public static Boolean getOpenXssProtect() {
        if(StringUtil.isNotEmpty(ParamUtil.getConfig("sm.xss.protect.open"))){
            return Boolean.parseBoolean(ParamUtil.getConfig("sm.xss.protect.open"));
        }else{
            return false;
        }
    }
}
