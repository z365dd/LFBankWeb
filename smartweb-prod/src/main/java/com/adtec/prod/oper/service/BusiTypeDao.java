package com.adtec.prod.oper.service;

import java.sql.SQLException;
import java.util.List;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.prod.oper.entity.BusiTypeDo;
import com.adtec.sys.common.dao.IBaseDao;

public class BusiTypeDao implements IBaseDao<BusiTypeDo>{
	private static final String TABLE_NAME = "T_BUSI_TYPE";

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(BusiTypeDo objDO) {
        return 0;
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(BusiTypeDo objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(BusiTypeDo objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public BusiTypeDo get(BusiTypeDo objDO) {
        return null;
    }

    /**
     * 数据库多笔查询
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<BusiTypeDo> list(BusiTypeDo objDO) {
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
    public List<BusiTypeDo> list(BusiTypeDo objDO, int start, int limit) {
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
    public List<BusiTypeDo> list(int start, int limit, Object... param) {
        return null;
    }

    public List<BusiTypeDo> list() {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("select busi_type_id, busi_type_name, remark from %s ", TABLE_NAME);
        try {
            return session.getObjectList(sql, BusiTypeDo.class);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询时发生异常，" + e.getMessage());
        }
    }
}
