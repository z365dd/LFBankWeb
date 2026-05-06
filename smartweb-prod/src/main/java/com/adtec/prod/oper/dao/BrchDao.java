package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.BrchDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BrchDao implements IBaseDao<BrchDO> {
    private final static Logger logger = LoggerFactory.getLogger(BrchDao.class);
    private static final String TABLE_NAME = "T_PARA_BRCH";

    @Override
    public int insert(BrchDO objDO) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(BrchDO objDO) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(BrchDO objDO) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public BrchDO get(BrchDO objDO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BrchDO> list(BrchDO objDO) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "select brch,brch_name from " + TABLE_NAME;
        List<BrchDO> list = new ArrayList<BrchDO>();
        try {
            list = session.getObjectList(sql, BrchDO.class);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("获取文件数据：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
        }
        return list;
    }

    @Override
    public List<BrchDO> list(BrchDO objDO, int start, int limit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BrchDO> list(int start, int limit, Object... param) {
        // TODO Auto-generated method stub
        return null;
    }

}
