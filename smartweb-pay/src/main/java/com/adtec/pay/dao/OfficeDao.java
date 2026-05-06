package com.adtec.pay.dao;

import com.adtec.pay.entity.OfficeDo;
import com.adtec.sys.common.dao.IBaseDao;

import java.util.List;

public class OfficeDao implements IBaseDao<OfficeDo> {

    private static final OfficeDao officeDao;
    private static final String TABLE_NAME = "T_SYS_OFFICE";

    static {
        synchronized (OfficeDao.class) {
            officeDao = new OfficeDao();
        }
    }

    public static OfficeDao getInstance() {
        return officeDao;
    }

    private OfficeDao() {
    }

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(OfficeDo objDO) {
        return 0;
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(OfficeDo objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(OfficeDo objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public OfficeDo get(OfficeDo objDO) {
        return null;
    }

    /**
     * 数据库多笔查询
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<OfficeDo> list(OfficeDo objDO) {
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
    public List<OfficeDo> list(OfficeDo objDO, int start, int limit) {
        return null;
    }

    public List<OfficeDo> list() {
        ExecuteTask<SmsSqlTask<List<OfficeDo>>, List<OfficeDo>> task = new ExecuteTask();
        return task.sqlTaskNotNeedTransactional("获取数据", session -> {
            String sql = String.format("select id, parent_id, name, brch_code from %s", TABLE_NAME);
            return session.getObjectList(sql, OfficeDo.class);
        });
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
    public List<OfficeDo> list(int start, int limit, Object... param) {
        return null;
    }
}
