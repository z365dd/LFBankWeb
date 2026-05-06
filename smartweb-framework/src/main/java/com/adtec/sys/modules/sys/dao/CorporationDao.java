package com.adtec.sys.modules.sys.dao;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Corporation;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.google.common.collect.Lists;


@Component
public class CorporationDao {
    private final static Logger logger = LoggerFactory.getLogger(CorporationDao.class);

    public int delete(Corporation corporation) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_CORPORATION WHERE id=? OR parent_id_list LIKE ?";
        Object[] params = {corporation.getId(), "%," + corporation.getId() + ",%"};
        try {
            rs = session.execute(sql, params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return rs;
    }

    public List<Corporation> findAllList(Corporation corporation) {
        List<Object> filterParams = Lists.newArrayList();
        return findAllList(corporation, filterParams);
    }

    public List<Corporation> findAllList(Corporation corporation, List<Object> filterParams) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_CORPORATION a ")
                .append("LEFT JOIN T_SYS_CORPORATION p ON p.id = a.parent_id ")
                .append("WHERE a.del_flg=? ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        if (corporation.getSqlMap().get("dsf") != null) {
            sql.append(corporation.getSqlMap().get("dsf")).append(" ");
            params.addAll(filterParams);
        }
        sql.append("ORDER BY a.id");
        List<Corporation> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Corporation corporation2 = new Corporation();
                Corporation parent = new Corporation();
                processMetaData(session, rs, corporation2, parent);
                corporation2.setParent(parent);
                list.add(corporation2);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
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

    private void processMetaData(IDBSession session, ResultSet rs, Corporation corporation, Corporation parent) throws Exception {
        ResultSetMetaData meta = rs.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
            if (fieldName.startsWith("parent.")) {
                session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
            } else {
                session.setProperty(corporation, fieldName, rs, i);
            }
        }
    }

    public List<Corporation> findByParentIdsLike(Corporation corporation) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_CORPORATION a ")
                .append("LEFT JOIN T_SYS_CORPORATION p ON p.id = a.parent_id ")
                .append("WHERE a.del_flg =? ")
                .append("AND (a.parent_id_list LIKE ? OR a.id=?)");
        List<Corporation> list = Lists.newArrayList();
        Object[] params = {DEL_FLAG_NORMAL, corporation.getParentIdList(), corporation.getId()};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Corporation corporation1 = new Corporation();
                Corporation parent = new Corporation();
                processMetaData(session, rs, corporation1, parent);
                corporation1.setParent(parent);
                list.add(corporation1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
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

    public Corporation findParentCorporationById(String legaId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from T_SYS_CORPORATION where id=(select o.parent_id from T_SYS_CORPORATION o where o.id=?) ");
        Corporation corporation;
        try {
            corporation = session.getObject(sql.toString(), Corporation.class, legaId);
        } catch (SQLException e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return corporation;
    }

    public Corporation get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_CORPORATION a ")
                .append("LEFT JOIN T_SYS_CORPORATION p ON p.id = a.parent_id ")
                .append("WHERE a.id =? ");
        Corporation corporation = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), id);
            while (rs.next()) {
                corporation = new Corporation();
                Corporation parent = new Corporation();
                processMetaData(session, rs, corporation, parent);
                corporation.setParent(parent);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return corporation;
    }

    public Corporation getByNumber(String number) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_CORPORATION a ")
                .append("LEFT JOIN T_SYS_CORPORATION p ON p.id = a.parent_id ")
                .append("WHERE a.lega_no =?");
        Corporation corporation = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), number);
            while (rs.next()) {
                corporation = new Corporation();
                Corporation parent = new Corporation();
                processMetaData(session, rs, corporation, parent);
                corporation.setParent(parent);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return corporation;
    }

    public Corporation getByEngName(String engName) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_CORPORATION a ")
                .append("LEFT JOIN T_SYS_CORPORATION p ON p.id = a.parent_id ")
                .append("WHERE a.eng_name =?");
        Corporation corporation = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), engName);
            while (rs.next()) {
                corporation = new Corporation();
                Corporation parent = new Corporation();
                processMetaData(session, rs, corporation, parent);
                corporation.setParent(parent);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return corporation;
    }

    public List<String> getCorporationsByLegaId(Corporation corporation) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM T_SYS_CORPORATION WHERE del_flg =? ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        if (StringUtil.isNotBlank(corporation.getId())) {
            sql.append("AND (id =? OR parent_id_list LIKE ?) ");
            params.add(corporation.getId());
            params.add("%," + corporation.getId() + ",%");
        }
        List<String> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            if(null == rs){
                throw new BaseException(SysErr.E_MESSAGE, "查询失败");
            }
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
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

    public int insert(Corporation c) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_CORPORATION(id,lega_no,name,eng_name,ctct_addr,parent_id,parent_id_list,crtr,del_flg,crt_time, uptr, upt_time, VALID_SWITCH_FLG) ")
                .append("VALUES (?,?,?,?,?,?,?,?,?,");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?,'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?,'yyyy-mm-dd hh24:mi:ss'), ?)");
        } else {
            sql.append("?, ?, ?, ?)");
        }
        Object[] params = {
                c.getId(), c.getLegaNo(), c.getName(), c.getEngName(), c.getCtctAddr(), c.getParentId(),
                c.getParentIdList(), c.getCrtr(), DEL_FLAG_NORMAL,
                c.getCrtTime(), c.getUptr(), c.getUptTime(), c.getValidSwitchFlg()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("新增失败！", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增失败！" + e.getMessage());
        }
        return i;
    }

    public int update(Corporation c) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_CORPORATION SET parent_id=?,parent_id_list=?,lega_no=?,name=?,eng_name=?,ctct_addr=?,uptr=?,VALID_SWITCH_FLG=?,");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time = to_timestamp(?,'yyyy-mm-dd hh24:mi:ss') ");
        } else {
            sql.append("upt_time =? ");
        }
        sql.append("WHERE id =? ");
        Object[] params = {c.getParentId(), c.getParentIdList(), c.getLegaNo(), c.getName(), c.getEngName(),
                c.getCtctAddr(), c.getUptr(), c.getValidSwitchFlg(), c.getUptTime(), c.getId()};
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("新增失败！", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增失败！");
        }
        return i;
    }

    public int updateParentIds(Corporation c) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_CORPORATION SET parent_id=?,parent_id_list=? WHERE id=?");
        Object[] params = {c.getParentId(), c.getParentIdList(), c.getId()};
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }
}
