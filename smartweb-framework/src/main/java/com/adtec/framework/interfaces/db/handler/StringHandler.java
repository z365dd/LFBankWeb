package com.adtec.framework.interfaces.db.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ResultSet处理接口，返回结果转换成String
 *
 * @author lijunbin
 */
public class StringHandler implements ResultSetHandler<String> {
    @Override
    public String apply(ResultSet rs) {
        try {
            if(null != rs){
                return rs.next() ? rs.getString(1) : null;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
