package com.adtec.pay.dao;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.entity.ParamDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 配置参数数据库操作
 */
public class ParamDao {

    private final static Logger logger = LoggerFactory.getLogger(ParamDao.class);

    private static final String TABLE_NAME = "T_MLPP_PARA";

    public static ParamDO getParam(String name) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("SELECT PARA_VAL FROM %s WHERE  KEY = ? ", TABLE_NAME);
        if(ParamUtil.getString("datasource.driverClassName").contains("mysql")){
           sql = String.format("SELECT PARA_VAL FROM %s WHERE  `KEY` = ? ", TABLE_NAME);
        }
        ParamDO rs = null;
        try {
            rs = session.getObject(sql, ParamDO.class, new MBCCaseStrategy(), name);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return rs;


    }

}
