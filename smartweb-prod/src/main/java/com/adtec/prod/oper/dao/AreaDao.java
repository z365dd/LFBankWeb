package com.adtec.prod.oper.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.prod.oper.entity.AreaDo;
import com.adtec.sys.common.dao.IBaseDao;

import java.sql.SQLException;
import java.util.List;

public class AreaDao implements IBaseDao<AreaDo> {
    private static final String TABLE_NAME = "T_SYS_AREA";

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(AreaDo objDO) {
        return 0;
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(AreaDo objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(AreaDo objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public AreaDo get(AreaDo objDO) {
        return null;
    }

    /**
     * 数据库多笔查询
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<AreaDo> list(AreaDo objDO) {
        return null;
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param objDO 数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<AreaDo> list(AreaDo objDO, int start, int limit) {
        return null;
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    @Override
    public List<AreaDo> list(int start, int limit, Object... param) {
        return null;
    }

    public List<AreaDo> list(String parentId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("select id, parent_id, name from %s where parent_id = ?", TABLE_NAME);
        try {
            return session.getObjectList(sql, AreaDo.class, parentId);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询时发生异常，" + e.getMessage());
        }
    }
}
