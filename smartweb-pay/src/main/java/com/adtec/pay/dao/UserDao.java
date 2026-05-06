package com.adtec.pay.dao;

import com.adtec.pay.entity.UserDo;
import com.adtec.sys.common.dao.IBaseDao;

import java.util.List;

public class UserDao implements IBaseDao<UserDo> {

    private static String TABLE_NAME = "T_SYS_USER";
    private static UserDao userDao;


    static {
        synchronized (UserDao.class) {
            userDao = new UserDao();
        }
    }

    public static UserDao getInstance() {
        return userDao;
    }

    private UserDao() {
    }

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(UserDo objDO) {
        return 0;
    }

    public int insert(UserDo objDO, String busiNo, String roleId) {
        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();
        return task.sqlTaskNeedTransactional("创建用户", session -> {
            session.execute("insert into T_SYS_BUSI_USER(user_id, busi_id) values(?, ?)", objDO.getId(), busiNo);
            session.execute("insert into T_SYS_USER_ROLE(user_id, role_id) values(?, ?)", objDO.getId(), roleId);
            String sql = String.format("insert into %s(id, BRCH_ID, LOGIN_NAME, PWD, EMAIL, PHONE_NO, CRTR, CRT_TIME, RMRK, DEL_FLG, TNT_ID, USER_NO,NAME,LEGA_ID) " +
                    "values (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, '1')", TABLE_NAME);
            return session.execute(sql,
                    objDO.getId(), objDO.getBrchId(), objDO.getLoginName(), objDO.getPwd(), objDO.getEmail(), objDO.getPhoneNo(), objDO.getCrtr(),
                    objDO.getCrtTime(), objDO.getRmrk(), objDO.getDelFlg(), objDO.getTntId(), objDO.getUserNo(), objDO.getName());
        });
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(UserDo objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(UserDo objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public UserDo get(UserDo objDO) {
        return null;
    }

    public Integer get(String loginName) {
        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();
        return task.sqlTaskNotNeedTransactional("获取数据", session -> {
            String sql = String.format("select count(1) from %s where LOGIN_NAME = ?", TABLE_NAME);
            return session.account(sql, loginName);
        });
    }

    /**
     * 数据库多笔查询
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<UserDo> list(UserDo objDO) {
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
    public List<UserDo> list(UserDo objDO, int start, int limit) {
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
    public List<UserDo> list(int start, int limit, Object... param) {
        return null;
    }
}
