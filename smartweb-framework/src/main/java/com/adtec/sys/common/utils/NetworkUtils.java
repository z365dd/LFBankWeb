package com.adtec.sys.common.utils;

import com.adtec.framework.common.util.ParamUtil;

/**
 * @author chenyl
 * @version 1.0
 * @date 2019-11-11 9:38
 */
public class NetworkUtils {

    /**
     * 获取本地ip
     *
     * @return
     */
    public static String getLocalIp() {
        return ParamUtil.getLocalAddr();
    }

    public static String getLocalPort() {
        return ParamUtil.getLocalPort();
    }
}