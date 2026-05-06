package com.adtec.framework.interfaces.db.handler;

import java.sql.ResultSet;

/**
 * ResultSet处理接口
 *
 * @author lijunbin
 */
public interface ResultSetHandler<T> {

    /**
     * 处理结果集<br>
     * 结果集处理后不需要关闭
     *
     * @param rs 结果集
     * @return 处理后生成的对象
     */
    T apply(ResultSet rs);
}
