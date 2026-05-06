package com.adtec.pay.dao;

import com.adtec.pay.entity.BusiUserDo;
import com.adtec.sys.common.dao.IBaseDao;

import java.util.List;

public class BusiUserDao implements IBaseDao<BusiUserDo> {
    private static String TABLE_NAME = "T_SYS_BUSI_USER";
    private static BusiUserDao dao;


    static {
        synchronized (BusiUserDao.class) {
            dao = new BusiUserDao();
        }
    }

    public static BusiUserDao getInstance() {
        return dao;
    }

    private BusiUserDao() {
    }

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(BusiUserDo objDO) {
        return 0;
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(BusiUserDo objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(BusiUserDo objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public BusiUserDo get(BusiUserDo objDO) {
        return null;
    }

    /**
     * 数据库多笔查询
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<BusiUserDo> list(BusiUserDo objDO) {
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
    public List<BusiUserDo> list(BusiUserDo objDO, int start, int limit) {
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
    public List<BusiUserDo> list(int start, int limit, Object... param) {
        return null;
    }

    public BusiUserDo get(String userId) {
        ExecuteTask<SmsSqlTask<BusiUserDo>, BusiUserDo> task = new ExecuteTask();
        return task.sqlTaskNeedTransactional("获取数据",
                session -> session.getObject(String.format("select * from %s where user_id = ?", TABLE_NAME), BusiUserDo.class, userId));
    }
}
