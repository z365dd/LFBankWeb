package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * 机构DAO接口
 *
 * @version 2014-05-16
 */
@Component
public class OfficeDao {
    private final static Logger logger = LoggerFactory.getLogger(OfficeDao.class);

    public int delete(Office office) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_OFFICE WHERE id=? OR parent_id_list LIKE ?";
        Object[] params = {office.getId(), "%," + office.getId() + ",%"};
        try {
            i = session.execute(sql, params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<Office> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.del_flg = ? ")
                .append("ORDER BY a.brch_code");
        List<Office> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), DEL_FLAG_NORMAL);
            while (rs.next()) {
                Office office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
                list.add(office1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }  finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }

    public List<Office> findByCode(Office office) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.del_flg = ? ")
                .append("AND a.brch_code = ? ")
                .append("ORDER BY a.brch_code");
        List<Office> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), DEL_FLAG_NORMAL, office.getBrchCode());
            while (rs.next()) {
                Office office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
                list.add(office1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
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

    public List<Office> findByParentId(Office office) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.del_flg = ? ")
                .append("AND a.parent_id = ? ")
                .append("ORDER BY a.brch_code");
        List<Office> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), DEL_FLAG_NORMAL, office.getId());
            while (rs.next()) {
                Office office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
                list.add(office1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
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

    public List<Office> findByParentIdsLike(Office office) {
        List<Object> filterParams = Lists.newArrayList();
        return findByParentIdsLike(office, filterParams);
    }

    public List<Office> findByParentIdsLike(Office office, List<Object> fileterParams) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.del_flg = ? ")
                .append("AND (a.parent_id_list LIKE ? ")
                .append("OR a.id=? ) ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        params.add(office.getParentIdList());
        params.add(office.getId());
        if (office.getSqlMap().get("dsf") != null) {
            sql.append(office.getSqlMap().get("dsf")).append(" ");
            params.addAll(fileterParams);
        }
        sql.append("ORDER BY a.brch_code");
        List<Office> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Office office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
                list.add(office1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
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

    public List<Office> findList(Office office) {
        List<Object> filterParams = Lists.newArrayList();
        return findList(office, filterParams);
    }

    public List<Office> findList(Office office, List<Object> filterParams) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.del_flg = ? ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        if (office.getSqlMap().get("dsf") != null) {
            sql.append(office.getSqlMap().get("dsf")).append(" ");
            params.addAll(filterParams);
        }
        sql.append("ORDER BY a.brch_code");
        List<Office> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Office office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
                list.add(office1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
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

    public String findBrchIdByRoleId(String roleId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select o.id from T_SYS_OFFICE o ")
                .append("LEFT JOIN T_SYS_ROLE r ON o.id=r.brch_id ")
                .append("where r.id=? ")
                .append("AND o.del_flg = '0'");
        String brchId = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), roleId);
            while (rs.next()) {
                brchId = rs.getString(1);
            }
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除自动任务数据异常：" + e.getMessage());
            }
        }
        return brchId;
    }

    public Office findParentOfficeById(String brchId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from T_SYS_OFFICE where id=(select o.parent_id from T_SYS_OFFICE o where o.id=?) ");
        Office office;
        try {
            office = session.getObject(sql.toString(), Office.class, brchId);
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return office;
    }

    public Office get(Office office) {
        return get(office.getId());
    }

    public Office get(String brchId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.id = ? ");
        Office office = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), brchId);
            while (rs.next()) {
                office = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office, fieldName, rs, i);
                    }
                }
                office.setParent(parent);
                office.setArea(area);
                office.setOneRspbtPer(oneRspbtPer);
                office.setTwoRspbtPer(twoRspbtPer);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return office;
    }

    public Office getByCode(Office office) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.brch_code = ? ")
                .append("and a.del_flg = ? ");
        Office office1 = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), office.getBrchCode(), DEL_FLAG_NORMAL);
            while (rs.next()) {
                office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return office1;
    }

    public Office getByName(Office office) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_OFFICE a ")
                .append("LEFT JOIN T_SYS_OFFICE p ON p.id = a.parent_id ")
                .append("LEFT JOIN T_SYS_AREA ar ON ar.id = a.region_id ")
                .append("LEFT JOIN T_SYS_USER pp ON pp.id = a.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER dp ON dp.id = a.two_rspbt_per ")
                .append("WHERE a.name = ? ")
                .append("and a.del_flg = ? ");
        Office office1 = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), office.getName(), DEL_FLAG_NORMAL);
            while (rs.next()) {
                office1 = new Office();
                Office parent = new Office();
                Area area = new Area();
                User oneRspbtPer = new User();
                User twoRspbtPer = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("area.")) {
                        session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(office1, fieldName, rs, i);
                    }
                }
                office1.setParent(parent);
                office1.setArea(area);
                office1.setOneRspbtPer(oneRspbtPer);
                office1.setTwoRspbtPer(twoRspbtPer);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return office1;
    }

    public List<Office> getOffice(String brchId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "SELECT * FROM T_SYS_OFFICE WHERE id=?";
        List<Office> list;
        try {
            list = session.getObjectList(sql, Office.class, brchId);
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }

    public Office getOfficeByRegionId(String regionId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "select * from T_SYS_OFFICE where region_id=? and del_flg=?";
        Office office;
        Object[] params = {regionId, DEL_FLAG_NORMAL};
        try {
            office = session.getObject(sql, Office.class, params);
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return office;
    }

    public Office getOfficeById(String brchId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "select * from T_SYS_OFFICE where id=? and del_flg=?";
        Office office;
        Object[] params = {brchId, DEL_FLAG_NORMAL};
        try {
            office = session.getObject(sql, Office.class, params);
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return office;
    }

    public List<String> getOfficesByBrchId(Office office) {
        IDBSession session = DBSessionFactory.getSession();
        List<Object> params = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM T_SYS_OFFICE WHERE del_flg=? ");
        params.add(DEL_FLAG_NORMAL);
        if (StringUtil.isNotBlank(office.getId())) {
            sql.append("AND (id=? OR parent_id_list LIKE ?) ");
            params.add(office.getId());
            params.add("%," + office.getId() + ",%");
        }
        sql.append("ORDER BY brch_code");
        List<String> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
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

    public int insert(Office office) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String columns = "id, parent_id, parent_id_list, region_id, brch_code, name, sort, brch_tp, ctct_addr, post_ecd, rspbt_per, tel_no, fax_no, email, crtr, crt_time, uptr, upt_time, rmrk, del_flg, VALID_SWITCH_FLG, one_rspbt_per, two_rspbt_per";
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_OFFICE(").append(columns).append(") VALUES (");
        sql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?,'yyyy-mm-dd hh24:mi:ss'),?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?)");
        } else {
            sql.append("?,?,?,?,?,?,?,?)");
        }
        Object[] params = {
                office.getId(), office.getParentId(), office.getParentIdList(), office.getArea().getId(),
                office.getBrchCode(), office.getName(), office.getSort(), office.getBrchTp(),
                office.getCtctAddr(), office.getPostEcd(), office.getRspbtPer(), office.getTelNo(),
                office.getFaxNo(), office.getEmail(), office.getCrtr(), office.getCrtTime(),
                office.getUptr(), office.getUptTime(), office.getRmrk(), office.getDelFlg(),
                office.getValidSwitchFlg(), office.getOneRspbtPer().getId(), office.getTwoRspbtPer().getId()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return i;
    }

    public int update(Office office) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_OFFICE SET parent_id=?, parent_id_list=?, region_id=?, brch_code=?, name=?, brch_tp=?, ")
                .append("ctct_addr=?, post_ecd=?, rspbt_per=?, tel_no=?, fax_no=?, email=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
        } else {
            sql.append("upt_time=?, ");
        }
        sql.append("rmrk=?, VALID_SWITCH_FLG=?, one_rspbt_per=?, two_rspbt_per=? WHERE id=?");
        Object[] params = {
                office.getParentId(), office.getParentIdList(), office.getArea().getId(), office.getBrchCode(),
                office.getName(), office.getBrchTp(), office.getCtctAddr(), office.getPostEcd(),
                office.getRspbtPer(), office.getTelNo(), office.getFaxNo(), office.getEmail(),
                office.getUptr(), office.getUptTime(), office.getRmrk(), office.getValidSwitchFlg(),
                office.getOneRspbtPer().getId(), office.getTwoRspbtPer().getId(), office.getId()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return i;
    }

    public int updateParentIds(Office office) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "UPDATE T_SYS_OFFICE SET parent_id=?, parent_id_list=? WHERE id=?";
        Object[] params = {office.getParentId(), office.getParentIdList(), office.getId()};
        try {
            i = session.execute(sql, params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return i;
    }

    private static final String columnName = "a.id, a.parent_id AS \"parent.id\", a.parent_id_list, a.region_id AS \"area.id\", a.brch_code, a.name, a.sort, a.brch_tp, a.ctct_addr, a.post_ecd, a.rspbt_per, a.tel_no, a.fax_no, a.email, a.rmrk, a.crtr, a.crt_time, a.uptr, a.upt_time, a.del_flg, a.VALID_SWITCH_FLG, a.one_rspbt_per AS \"oneRspbtPer.id\", a.two_rspbt_per AS \"twoRspbtPer.id\", p.name AS \"parent.name\", ar.name AS \"area.name\", ar.parent_id_list AS \"area.parent_Id_List\", pp.name AS \"oneRspbtPer.name\", dp.name AS \"twoRspbtPer.name\" ";

}
