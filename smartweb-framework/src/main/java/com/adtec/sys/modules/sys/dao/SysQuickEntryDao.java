/**
 * 系统名称: SmartWeb平台
 * 模块名称: sys-modules持久化模块
 * 功能描述: 快捷菜单入口数据库操作
 * 类 名 称  : SysQuickEntryDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 20190904<br>
 * 系统版本: V1.0.0<br>
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * <p>
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.SysQuickEntryDO;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

/**
 * 快捷菜单入口Dao接口
 *
 * @author zx
 * @version 20190904
 */
@Component
public class SysQuickEntryDao implements IBaseDao<SysQuickEntryDO> {
    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(SysQuickEntryDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_QUICK_ENTRY";

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public SysQuickEntryDO get(String id) {
        SysQuickEntryDO obj = new SysQuickEntryDO();
        obj.setId(id);
        return get(obj);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    @Override
    public SysQuickEntryDO get(SysQuickEntryDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? AND del_flg='" + DEL_FLAG_NORMAL + "'");
        SysQuickEntryDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), SysQuickEntryDO.class, obj.getId());
        } catch (Exception e) {
            logger.error("获取快捷菜单入口异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取快捷菜单入口失败！");
        }
        return rs;
    }

    /**
     * 插入数据
     *
     * @param obj
     * @return
     */
    @Override
    public int insert(SysQuickEntryDO obj) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
        } catch (Exception e) {
            logger.error("新增快捷菜单入口交易异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增快捷菜单入口交易失败！");
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    @Override
    public int update(SysQuickEntryDO obj) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            List<String> matchFields = obj.getMatchFields();
            rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
        } catch (Exception e) {
            logger.error("修改快捷菜单入口异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改快捷菜单入口失败！");
        }
        return rs;
    }

    /**
     * 根据主键id删除数据（一般为逻辑删除，更新del_flg字段为1）
     *
     * @param id
     * @return
     * @see public int delete(T entity)
     */
    public int delete(String id) {
        SysQuickEntryDO obj = new SysQuickEntryDO();
        obj.setId(id);
        return delete(obj);
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flg字段为1）
     *
     * @param obj
     * @return
     */
    @Override
    public int delete(SysQuickEntryDO obj) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            String sql = "delete from T_SYS_QUICK_ENTRY where id=?";
            rs = session.execute(sql, obj.getId());
            if (rs == 0) {
                throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数：" + rs);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("删除交易异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
        }
        return rs;
    }

    /**
     * 数据库多笔查询，不分页
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<SysQuickEntryDO> list(SysQuickEntryDO obj) {
        return list(obj, 0, 0);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<SysQuickEntryDO> list(SysQuickEntryDO obj, int start, int limit) {
        logger.debug("SysQuickEntryDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<SysQuickEntryDO> list;
        List<Object> params = Lists.newArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, params));
            sql.append(" order by upt_time desc");
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), SysQuickEntryDO.class, params);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), SysQuickEntryDO.class, start, limit, params);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
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
    public List<SysQuickEntryDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(SysQuickEntryDO obj) {
        logger.debug("SysQuickEntryDO=" + obj);
        List<Object> params = Lists.newArrayList();
        String countSql = "select count(1) from (" + "select * from " + TABLE_NAME + getWhereSql(obj, params) + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total;
        try {
            total = session.accountByList(countSql, params);
        } catch (Exception e) {
            logger.error("总记录数查询T_SYS_QUICK_ENTRY异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询T_SYS_QUICK_ENTRY失败！");
        }
        return total;
    }

    /**
     * 根据数据对象产生对应的查询SQL
     *
     * @param obj    数据对象DO
     * @param params
     * @return SQL
     */
    public String getWhereSql(SysQuickEntryDO obj, List<Object> params) {
        StringBuffer sql = new StringBuffer(" where 1=1 ");
        sql.append(" AND del_flg='").append(DEL_FLAG_NORMAL).append("' ");
        sql.append(" AND user_id=? ");
        params.add(obj.getUserId());
        return sql.toString();
    }

}