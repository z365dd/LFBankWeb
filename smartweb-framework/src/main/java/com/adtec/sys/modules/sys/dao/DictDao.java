package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Dict;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典DAO接口
 *
 * @version 2014-05-16
 */
@Component
public class DictDao {
    private final static Logger logger = LoggerFactory.getLogger(DictDao.class);

    public int delete(Dict dict) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_DICT WHERE id=?";
        try {
            i = session.execute(sql, dict.getId());
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<Dict> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ")
                .append(columnName)
                .append(" FROM T_SYS_DICT WHERE del_flg=? ORDER BY dict_tp, sort, upt_time DESC");
        List<Dict> list;
        try {
            list = session.getObjectList(sql.toString(), Dict.class, DEL_FLAG_NORMAL);
        } catch (Exception e) {
            logger.error("查询数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！" + e.getMessage());
        }
        return list;
    }

    public List<Dict> findList(Dict dict) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append(" FROM T_SYS_DICT a WHERE del_flg=? ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        if (StringUtil.isNotBlank(dict.getDictTp())) {
            sql.append("AND dict_tp =? ");
            params.add(dict.getDictTp());
        }
        if (StringUtil.isNotBlank(dict.getDictInfo())) {
            sql.append("AND dict_info LIKE ? ");
            params.add("%" + dict.getDictInfo()+"%");
        }
        sql.append("ORDER BY dict_tp, sort, upt_time DESC");
        List<Dict> list;
        try {
            list = session.getObjectListByList(sql.toString(), Dict.class, params);
        } catch (Exception e) {
            logger.error("查询数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！" + e.getMessage());
        }
        return list;
    }

    public List<String> findTypeList() {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "SELECT dict_tp FROM T_SYS_DICT WHERE del_flg=? GROUP BY dict_tp ORDER BY dict_tp";
        List<String> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql, DEL_FLAG_NORMAL);
            if(null == rs){
                throw new BaseException(SysErr.E_MESSAGE, "查询失败");
            }
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            logger.error("查询数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }

    public Dict get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ")
                .append(columnName)
                .append(" FROM T_SYS_DICT WHERE id=?");
        Dict dict;
        try {
            dict = session.getObject(sql.toString(), Dict.class, id);
        } catch (Exception e) {
            logger.error("查询数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return dict;
    }

    public Dict get(String dictTp, String dictVal) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ")
                .append(columnName)
                .append(" FROM T_SYS_DICT WHERE dict_tp=? and dict_val=? ");
        Dict dict;
        try {
            dict = session.getObject(sql.toString(), Dict.class, dictTp, dictVal);
        } catch (Exception e) {
            logger.error("查询数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return dict;
    }

    public int insert(Dict dict) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_DICT(id, dict_val, dict_label, dict_tp, dict_info, sort, rmrk, del_flg, crtr, uptr, crt_time, upt_time) VALUES (")
                .append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'))");
        } else {
            sql.append("?, ?)");
        }
        Object[] params = {
                dict.getId(), dict.getValue(), dict.getLabel(), dict.getDictTp(), dict.getDictInfo(),
                dict.getSort(), dict.getRmrk(), DEL_FLAG_NORMAL, dict.getCrtr(), dict.getUptr(),
                dict.getCrtTime(), dict.getUptTime()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int update(Dict dict) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_DICT SET dict_val=?, dict_label=?, dict_tp=?, dict_info=?, sort=?, rmrk=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss') WHERE id=?");
        } else {
            sql.append("upt_time=? WHERE id=?");
        }
        Object[] params = {
                dict.getValue(), dict.getLabel(), dict.getDictTp(), dict.getDictInfo(),
                dict.getSort(), dict.getRmrk(), dict.getUptr(), dict.getUptTime(), dict.getId()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    private static final String columnName = "ID,DICT_VAL as VALUE,DICT_LABEL as LABEL,DICT_TP,DICT_INFO,SORT,PARENT_ID,CRTR,CRT_TIME,UPTR,UPT_TIME,RMRK,DEL_FLG ";

}
