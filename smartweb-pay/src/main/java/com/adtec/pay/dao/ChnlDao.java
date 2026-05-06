package com.adtec.pay.dao;

import com.adtec.pay.entity.CtrlTParaChnlDO;
import com.adtec.sys.common.dao.IBaseDao;

import java.util.List;

public class ChnlDao implements IBaseDao<CtrlTParaChnlDO> {

    private static final ChnlDao dao;
    private static final String TABLE_NAME = "T_PARA_CHNL";

    static {
        synchronized (ChnlDao.class) {
            dao = new ChnlDao();
        }
    }

    public static ChnlDao getInstance() {
        return dao;
    }

    private ChnlDao() {
    }

    public List<CtrlTParaChnlDO> getChnlInfo() {
        ExecuteTask<SmsSqlTask<List<CtrlTParaChnlDO>>, List<CtrlTParaChnlDO>> executeTask = new ExecuteTask();
        return executeTask.sqlTaskNotNeedTransactional("查询", session -> {
            String sql = String.format("select chnl_no, chnl_name from %s", TABLE_NAME);
            return session.getObjectList(sql, CtrlTParaChnlDO.class);
        });
    }

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(CtrlTParaChnlDO objDO) {
        return 0;
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(CtrlTParaChnlDO objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(CtrlTParaChnlDO objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public CtrlTParaChnlDO get(CtrlTParaChnlDO objDO) {
        return null;
    }

    /**
     * 数据库多笔查询
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<CtrlTParaChnlDO> list(CtrlTParaChnlDO objDO) {
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
    public List<CtrlTParaChnlDO> list(CtrlTParaChnlDO objDO, int start, int limit) {
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
    public List<CtrlTParaChnlDO> list(int start, int limit, Object... param) {
        return null;
    }
}
