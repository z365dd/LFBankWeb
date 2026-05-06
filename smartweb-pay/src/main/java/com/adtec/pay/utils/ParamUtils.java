package com.adtec.pay.utils;

import com.adtec.pay.dao.ParamDao;
import com.adtec.pay.entity.ParamDO;

/**
 * 参数工具类 获取配置的参数
 */
public class ParamUtils {


    /**
     * 获取T_MLPP_PARA中的配置信息
     *
     * @param name
     * @return
     */
    public static ParamDO getParams(String name) {
        return ParamDao.getParam(name);
    }



}
