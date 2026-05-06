package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色DAO接口
 *
 * @version 2013-12-05
 */
@Component
public class RoleDao {
    private final static Logger logger = LoggerFactory.getLogger(RoleDao.class);

    public int delete(Role role) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_ROLE WHERE id=?";
        Object[] params = {role.getId()};
        try {
            i = session.execute(sql, params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int deleteUserRole(Role role) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "DELETE FROM T_SYS_USER_ROLE WHERE role_id=?";
        try {
            i = session.execute(sql, role.getId());
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<Role> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "SELECT a.* FROM T_SYS_ROLE a WHERE a.del_flg = '0' ORDER BY a.id";
        List<Role> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql);
            while (rs.next()) {
                Role role1 = new Role();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    session.setProperty(role1, fieldName, rs, i);
                }
                list.add(role1);
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

    public List<Role> findAllRoleWithUser(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT a.*, ur.user_id AS \"user.id\" ")
                .append("FROM T_SYS_ROLE a ")
                .append("LEFT JOIN T_SYS_USER_ROLE ur ON ur.role_id = a.id ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.VALID_SWITCH_FLG=? ");
        List<Object> params = Lists.newArrayList();
        /*20200317 add by chenyl for 缺少参数*/
        params.add(role.getValidSwitchFlg());
        if (role.getUser() != null && StringUtil.isNotBlank(role.getUser().getId())) {
            sql.append("AND ur.user_id=? ");
            params.add(role.getUser().getId());
        }
        sql.append("ORDER BY a.id");
        List<Role> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Role role1 = new Role();
                User user = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("user.")) {
                        session.setProperty(user, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(role1, fieldName, rs, i);
                    }
                }
                role1.setUser(user);
                list.add(role1);
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

    public List<Role> findList(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT a.* ")
                .append("FROM T_SYS_ROLE a ")
                .append("LEFT JOIN T_SYS_USER_ROLE ur ON ur.role_id = a.id ")
                .append("LEFT JOIN T_SYS_USER u ON u.id = ur.user_id ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        List<Object> params = Lists.newArrayList();
        if (role.getUser() != null && StringUtil.isNotBlank(role.getUser().getId())) {
            sql.append("AND u.id = ? ");
            params.add(role.getUser().getId());
        }
        if (role.getUser() != null && StringUtil.isNotBlank(role.getUser().getLoginName())) {
            sql.append("AND u.login_name = ? ");
            params.add(role.getUser().getLoginName());
        }
        if (StringUtil.isNotBlank(role.getRoleTp())) {
            sql.append("AND a.role_tp = ? ");
            params.add(role.getRoleTp());
        }
        sql.append("ORDER BY a.id");
        List<Role> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Role role1 = new Role();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    session.setProperty(role1, fieldName, rs, i);
                }
                list.add(role1);
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

    /**
     * 查询除管理员以外所有角色
     * @param role
     * @return
     */
    public List<Role> findListExceptAdmin(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT a.* ")
                .append("FROM T_SYS_ROLE a ")
                .append("LEFT JOIN T_SYS_USER_ROLE ur ON ur.role_id = a.id ")
                .append("LEFT JOIN T_SYS_USER u ON u.id = ur.user_id ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' and a.id != '1'");
        List<Object> params = Lists.newArrayList();
        if (role.getUser() != null && StringUtil.isNotBlank(role.getUser().getId())) {
            sql.append("AND u.id = ? ");
            params.add(role.getUser().getId());
        }
        if (role.getUser() != null && StringUtil.isNotBlank(role.getUser().getLoginName())) {
            sql.append("AND u.login_name = ? ");
            params.add(role.getUser().getLoginName());
        }
        if (StringUtil.isNotBlank(role.getRoleTp())) {
            sql.append("AND a.role_tp = ? ");
            params.add(role.getRoleTp());
        }
        sql.append("ORDER BY a.id");
        List<Role> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Role role1 = new Role();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    session.setProperty(role1, fieldName, rs, i);
                }
                list.add(role1);
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


    public List<Role> findListWithUser(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, ur.user_id AS \"user.id\" ")
                .append("FROM T_SYS_ROLE a ")
                .append("LEFT JOIN T_SYS_USER_ROLE ur ON ur.role_id = a.id AND ur.user_id =? ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        List<Object> params = Lists.newArrayList();
        params.add(role.getUser().getId());
        if (StringUtil.isNotBlank(role.getRoleTp())) {
            sql.append("AND a.role_tp = ? ");
            params.add(role.getRoleTp());
        }
        sql.append("ORDER BY a.id");
        List<Role> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Role role1 = new Role();
                User user = new User();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("user.")) {
                        session.setProperty(user, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(role1, fieldName, rs, i);
                    }
                }
                role1.setUser(user);
                list.add(role1);
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

    public Role findRole(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_ROLE a ")
                .append("LEFT JOIN T_SYS_USER_ROLE ur ON ur.role_id = a.id ")
                .append("LEFT JOIN T_SYS_USER u ON u.id = ur.user_id ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.VALID_SWITCH_FLG=? AND a.id = ? ");
        Role role1 = new Role();
        List<Menu> menuList = Lists.newArrayList();
        Object[] params = {role.getValidSwitchFlg(), role.getId()};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Menu menu = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("office.")) {

                    } else if (fieldName.startsWith("menuList.")) {
                        session.setProperty(menu, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(role1, fieldName, rs, i);
                    }
                }
                menuList.add(menu);
            }
            role1.setMenuList(menuList);
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
        return role1;
    }

    public Role get(String roleId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_ROLE a ")
                .append("WHERE a.id = ? ");
        Role role = new Role();
        List<Menu> menuList = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), roleId);
            while (rs.next()) {
                Menu menu = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("menuList.")) {
                        session.setProperty(menu, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(role, fieldName, rs, i);
                    }
                }
                menuList.add(menu);
            }
            role.setMenuList(menuList);
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
        return role;
    }

    public Role get(Role role) {
        return get(role.getId());
    }

    public Role getByEngName(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_ROLE a ")
                .append("WHERE a.eng_name = ? ")
                .append("AND a.del_flg = ? ");
        Role role1 = null;
        Object[] params = {role.getEngName(), DEL_FLAG_NORMAL};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                role1 = new Role();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    session.setProperty(role1, fieldName, rs, i);
                }
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
        return role1;
    }

    public Role getByName(Role role) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_ROLE a ")
                .append("WHERE a.name = ? ")
                .append("AND a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        Role role1 = null;
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), role.getName());
            while (rs.next()) {
                role1 = new Role();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    session.setProperty(role1, fieldName, rs, i);
                }
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
        return role1;
    }

    public int insert(Role role) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_ROLE(id, name, eng_name, role_tp, data_scp, crtr, crt_time, uptr, upt_time, rmrk, del_flg, DATA_SWITCH_FLG, VALID_SWITCH_FLG")
                .append(") VALUES (?, ?, ?, ?, ?, ?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?)");
        } else {
            sql.append("?, ?, ?, ?, ?, ?, ?)");
        }
        Object[] params = {
                role.getId(), role.getName(), role.getEngName(), role.getRoleTp(),
                role.getDataScp(), role.getCrtr(), role.getCrtTime(), role.getUptr(), role.getUptTime(),
                role.getRmrk(), role.getDelFlg(), role.getDataSwitchFlg(), role.getValidSwitchFlg()
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

    public List<Role> selectRoleByBrchId(String brchId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select a.* from T_SYS_ROLE a ");
        List<Object> params = Lists.newArrayList();
        if (brchId != null) {
            sql.append("where a.brch_id = ? and ");
            params.add(brchId);
        } else {
            sql.append("where ");
        }
        sql.append("a.del_flg = '0' ");
        List<Role> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Role role1 = new Role();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    session.setProperty(role1, fieldName, rs, i);
                }
                list.add(role1);
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

    public int update(Role role) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_ROLE SET name=?, eng_name=?, role_tp=?, data_scp=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time = to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
        } else {
            sql.append("upt_time = ?, ");
        }
        sql.append("rmrk=?, DATA_SWITCH_FLG =?, VALID_SWITCH_FLG=? WHERE id=? ");
        Object[] params = {
                role.getName(), role.getEngName(), role.getRoleTp(), role.getDataScp(), role.getUptr(),
                role.getUptTime(), role.getRmrk(), role.getDataSwitchFlg(), role.getValidSwitchFlg(), role.getId()
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
}
