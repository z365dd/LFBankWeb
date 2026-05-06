package com.adtec.sys.modules.sys.dao;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.adtec.sys.modules.sys.entity.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.google.common.collect.Lists;

/**
 * 区域DAO接口
 *
 * @version 2014-05-16
 */
@Component
public class AreaDao {
    private final static Logger logger = LoggerFactory.getLogger(AreaDao.class);

    public int delete(Area area) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("delete from T_SYS_AREA WHERE id=? OR parent_id_list LIKE ?");
        Object[] params = {area.getId(), "%," + area.getId() + ",%"};
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<Area> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_AREA a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("WHERE a.del_flg =? ")
                .append("ORDER BY a.region_code");
        List<Area> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), DEL_FLAG_NORMAL);
            while (rs.next()) {
                Area area1 = new Area();
                Area parent = new Area();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(area1, fieldName, rs, i);
                    }
                }
                area1.setParent(parent);
                list.add(area1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally{
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }

    public List<Area> findByParentIdsLike(Area area) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id, a.parent_id AS \"parent.id\", a.parent_id_list ")
                .append("FROM T_SYS_AREA a ")
                .append("WHERE a.del_flg=? ")
                .append("AND a.parent_id_list LIKE ? ")
                .append("ORDER BY a.region_code");
        List<Area> list = Lists.newArrayList();
        Object[] params = {DEL_FLAG_NORMAL, area.getParentIdList()};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Area area1 = new Area();
                Area parent = new Area();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(area1, fieldName, rs, i);
                    }
                }
                area1.setParent(parent);
                list.add(area1);
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

    public Area get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_AREA a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("WHERE a.id =?");
        Area area = new Area();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), id);
            while (rs.next()) {
                Area parent = new Area();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(area, fieldName, rs, i);
                    }
                }
                area.setParent(parent);
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
        return area;
    }

    public Area getByCode(String regionCode) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM T_SYS_AREA WHERE region_code=? ");
        Area area;
        try {
            area = session.getObject(sql.toString(), Area.class, regionCode);
        } catch (Exception e) {
            logger.error("查询数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return area;
    }

    public int insert(Area area) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_AREA(id, parent_id, parent_id_list, region_code, name, sort, region_tp, rmrk, del_flg, crtr, crt_time) VALUES (");
        sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss')");
        } else {
            sql.append("?");
        }
        sql.append(")");
        Object[] params = {
                area.getId(), area.getParentId(), area.getParentIdList(), area.getRegionCode(), area.getName(),
                area.getSort(), area.getRegionTp(), area.getRmrk(), DEL_FLAG_NORMAL, area.getCrtr(), area.getCrtTime()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int update(Area area) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_AREA SET parent_id=?, parent_id_list=?, region_code=?, name=?, sort=?, region_tp=?, rmrk=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss')");
        } else {
            sql.append("upt_time=?");
        }
        sql.append("WHERE id=?");
        Object[] params = {
                area.getParentId(), area.getParentIdList(), area.getRegionCode(), area.getName(), area.getSort(),
                area.getRegionTp(), area.getRmrk(), area.getUptr(), area.getUptTime(), area.getId()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int updateParentIds(Area area) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "UPDATE T_SYS_AREA SET parent_id=?, parent_id_list=? WHERE id=?";
        Object[] params = {area.getParentId(), area.getParentIdList(), area.getId()};
        try {
            i = session.execute(sql, params);
        } catch (Exception e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }
}
