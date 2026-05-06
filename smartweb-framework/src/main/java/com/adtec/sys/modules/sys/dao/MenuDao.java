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
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

/**
 * 菜单DAO接口
 *
 * @version 2014-05-16
 */
@Component
public class MenuDao {
    private final static Logger logger = LoggerFactory.getLogger(MenuDao.class);

    private final static String CACHE_TYPE = "menuCache";

    public int delete(Menu menu) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("delete from T_SYS_MENU WHERE id=? OR parent_id_list LIKE ?");
        Object[] params = {menu.getId(), "%," + menu.getId() + ",%"};
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<Menu> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ")
                .append(columnName)
                .append("FROM T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.id = a.parent_id ")
                .append("WHERE a.del_flg =? ")
                .append("ORDER BY a.sort");
        List<Menu> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), DEL_FLAG_NORMAL);
            while (rs.next()) {
                Menu menu1 = new Menu();
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu1, fieldName, rs, i);
                    }
                }
                menu1.setParent(parent);
                list.add(menu1);
            }

            sql = new StringBuilder();
            sql.append("SELECT ")
                    .append("A.menu_id, ")
                    .append("sc.id AS legaId, ")
                    .append("sc.NAME AS legaName, ")
                    .append("so.id AS brchId, ")
                    .append("so.NAME AS brchName, ")
                    .append("sr.id AS tntId, ")
                    .append("sr.NAME AS tntName, ")
                    .append("sro.id AS roleId, ")
                    .append("sro.NAME AS roleName ")
                    .append("FROM ")
                    .append("T_SYS_PERMISSION_GROUP A ")
                    .append("LEFT JOIN T_SYS_CORPORATION sc ON sc.ID = A.LEGA_ID ")
                    .append("LEFT JOIN T_SYS_OFFICE so ON so.ID = A.brch_id ")
                    .append("LEFT JOIN T_SYS_RENT sr ON sr.ID = A.tnt_id ")
                    .append("LEFT JOIN T_SYS_ROLE sro ON sro.ID = A.role_id ")
                    .append("WHERE ")
                    .append("(A.AUTH_TP = ? or A.AUTH_TP = ?) ORDER BY A.MENU_ID");
            rs = session.getResultSet(sql.toString(), "own", "OWN");
            if(null == rs){
                throw new BaseException(SysErr.E_MESSAGE, "查询失败");
            }
            while (rs.next()) {
                String menuId = rs.getString("menu_id");
                for (Menu temp : list) {
                    
                    if (menuId.equals(temp.getId())) {
                        String legaId = rs.getString("legaId");
                        String legaName = rs.getString("legaName");
                        String brchId = rs.getString("brchId");
                        String brchName = rs.getString("brchName");
                        String tntId = rs.getString("tntId");
                        String tntName = rs.getString("tntName");
                        String roleId = rs.getString("roleId");
                        String roleName = rs.getString("roleName");
                        if (null != legaName) {
                            temp.setLegaId(legaId);
                            temp.setLegaName(legaName);
                        }
                        if (null != brchName) {
                            temp.setBrchId(brchId);
                            temp.setBrchName(brchName);
                        }
                        if (null != tntName) {
                            temp.setRentid(tntId);
                            temp.setTntName(tntName);
                        }
                        if (null != roleName) {
                            temp.setRoleId(roleId);
                            temp.setRoleName(roleName);
                        }
                        break;
                    }
                }
            }

            // 计算当前用户是否有菜单操作权限
            for (Menu temp : list) {
                User user = UserUtils.getUser();
                if(user.isAdmin()){
                	temp.setIsOwn("1");
                }else{
                	if (user.getCorporation().getId().equals(temp.getLegaId())
                			&& user.getRent().getId().equals(temp.getRentid())
                			&& user.getOffice().getId().equals(temp.getBrchId())) {
                		List<String> roleIdList = user.getRoleIdList();
                		// 菜单拥有者的角色ID，用户需要都拥有
                		if (roleIdList.containsAll(temp.getRoleId())) {
                			temp.setIsOwn("1");
                		}
                	}
                }
            }

            // 从list中移除父菜单ID在列表不存在的菜单
            removeParentIdNotExist(list);

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

    public List<Menu> findByParentIdsLike(Menu menu) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("a.id, ")
                .append("a.parent_id AS \"parent.id\", ")
                .append("a.parent_id_list ")
                .append("FROM T_SYS_MENU a ")
                .append("WHERE a.del_flg = ? ")
                .append("AND a.parent_id_list ")
                .append("LIKE ? ")
                .append("ORDER BY a.sort");
        List<Menu> list = Lists.newArrayList();
        ResultSet rs = null;
        Object[] params = {DEL_FLAG_NORMAL, menu.getParentIdList()};
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Menu menu1 = new Menu();
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu1, fieldName, rs, i);
                    }
                }
                menu1.setParent(parent);
                list.add(menu1);
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

    /**
     * 计算用户菜单
     *
     * @param user
     * @return
     */
    public List<Menu> findByUser(User user) {
        // 权限组权重
        String officeWeight = "0";
        String rentWeight = "0";
        String corporationWeight = "0";
        String roleWeight = "0";
        IDBSession session = DBSessionFactory.getSession();
        ResultSet rs = null;
        try {
            rs = session.getResultSet("select * from T_SYS_PERMISSION_WEIGHT");
            while (rs.next()) {
                if ("office".equals(rs.getString("wght_name"))) {
                    officeWeight = rs.getString("SWITCH_FLG");
                } else if ("rent".equals(rs.getString("wght_name"))) {
                    rentWeight = rs.getString("SWITCH_FLG");
                } else if ("corporation".equals(rs.getString("wght_name"))) {
                    corporationWeight = rs.getString("SWITCH_FLG");
                } else if ("role".equals(rs.getString("wght_name"))) {
                    roleWeight = rs.getString("SWITCH_FLG");
                }
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
        // 菜单权限，求交集
        List<Menu> menuList = Lists.newArrayList();
        if ("1".equals(officeWeight)) {
            String brchId = user.getOffice().getId();
            List<Menu> menuListOfficePermissionGroup = findMenuListByBrchIdInPermissionGroup(brchId);
            menuList = menuListOfficePermissionGroup;
        }
        if ("1".equals(rentWeight)) {
            String tntId = user.getRent().getId();
            List<Menu> menuListRentPermissionGroup = findMenuListByTntIdInPermissionGroup(tntId);
            menuList.retainAll(menuListRentPermissionGroup);
            if (!"1".equals(officeWeight)) {
                menuList = menuListRentPermissionGroup;
            }
        }
        if ("1".equals(corporationWeight)) {
            String legaId = user.getCorporation().getId();
            List<Menu> menuListCorporationPermissionGroup = findMenuListByLegaIdInPermissionGroup(legaId);
            menuList.retainAll(menuListCorporationPermissionGroup);
            if (!"1".equals(officeWeight) && !"1".equals(rentWeight)) {
                menuList = menuListCorporationPermissionGroup;
            }
        }
        if ("1".equals(roleWeight)) {
            List<Menu> menuListRoles = Lists.newArrayList();
            for (Role role : user.getRoleList()) {
                // 角色是否可用，控制菜单的权限
                if ("1".equals(role.getValidSwitchFlg())) {
                    List<Menu> menuListRolePermissionGroup = findMenuListByRoleIdInPermissionGroup(role.getId());
                    menuListRoles.removeAll(menuListRolePermissionGroup);
                    menuListRoles.addAll(menuListRolePermissionGroup);
                }
            }
            menuList.retainAll(menuListRoles);
            if (!"1".equals(officeWeight) && !"1".equals(rentWeight) && !"1".equals(corporationWeight)) {
                menuList = menuListRoles;
            }
        }

        // 从list中移除父菜单ID在列表不存在的菜单
        removeParentIdNotExist(menuList);

        // 对菜单进行排序
        Collections.sort(menuList, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 == null && o2 != null) {
                    return -1;
                } else if (o1 != null && o2 == null) {
                    return 1;
                }
                // 按照sort进行升序排序 1是升序，-1是降序，0是不变
                if (o1.getSort() > o2.getSort()) {
                    return 1;
                } else if (o1.getSort() == o2.getSort()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        return menuList;
    }

    public List<Menu> findMenuListByLegaIdInPermissionGroup(String legaId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT ")
                .append(columnName)
                .append("from T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.ID = a.PARENT_ID ")
                .append("LEFT JOIN T_SYS_PERMISSION_GROUP om ON a.ID = om.MENU_ID ")
                .append("WHERE om.LEGA_ID = ? ")
                .append("AND (om.auth_tp = ? or om.auth_tp = ?) ")
                .append("AND a.del_flg = '0' ");
        sql.append("ORDER BY a.sort");
        List<Menu> list = Lists.newArrayList();
        Object[] params = {legaId, "use", "USE"};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Menu menu1 = new Menu();
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu1, fieldName, rs, i);
                    }
                }
                menu1.setParent(parent);
                list.add(menu1);
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

    public List<Menu> findMenuListByBrchIdInPermissionGroup(String brchId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT ")
                .append(columnName)
                .append("from T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.ID = a.PARENT_ID ")
                .append("LEFT JOIN T_SYS_PERMISSION_GROUP om ON a.ID = om.MENU_ID ")
                .append("WHERE om.BRCH_ID = ? ")
                .append("AND (om.auth_tp = ? or om.auth_tp = ?) ")
                .append("AND a.del_flg = '0' ");
        sql.append("ORDER BY a.sort");
        List<Menu> list = Lists.newArrayList();
        Object[] params = {brchId, "use", "USE"};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Menu menu1 = new Menu();
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu1, fieldName, rs, i);
                    }
                }
                menu1.setParent(parent);
                list.add(menu1);
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

    public List<Menu> findMenuListByTntIdInPermissionGroup(String tntId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT ")
                .append(columnName)
                .append("from T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.ID = a.PARENT_ID ")
                .append("LEFT JOIN T_SYS_PERMISSION_GROUP om ON a.ID = om.MENU_ID ")
                .append("WHERE om.TNT_ID = ? ")
                .append("AND (om.auth_tp = ? or om.auth_tp = ?) ")
                .append("AND a.del_flg = '0' ");
        sql.append("ORDER BY a.sort");
        List<Menu> list = Lists.newArrayList();
        Object[] params = {tntId, "use", "USE"};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Menu menu1 = new Menu();
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu1, fieldName, rs, i);
                    }
                }
                menu1.setParent(parent);
                list.add(menu1);
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

    /**
     * 用户可以使用的菜单与角色类型一致
     *
     * @param roleId
     * @return
     */
    public List<Menu> findMenuListByRoleIdInPermissionGroup(String roleId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT ")
                .append(columnName)
                .append("from T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.ID = a.PARENT_ID ")
                .append("LEFT JOIN T_SYS_PERMISSION_GROUP om ON a.ID = om.MENU_ID ")
                .append("WHERE om.ROLE_ID = ? ")
                .append("AND (om.auth_tp = ? or om.auth_tp = ?) ");
        sql.append("AND a.del_flg = '0' ").append("ORDER BY a.sort");
        List<Menu> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), roleId, "use", "USE");
            while (rs.next()) {
                Menu menu1 = new Menu();
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu1, fieldName, rs, i);
                    }
                }
                menu1.setParent(parent);
                list.add(menu1);
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

    public Menu get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.id = a.parent_id ")
                .append("WHERE a.id = ? ");
        Menu menu = new Menu();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), id);
            while (rs.next()) {
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu, fieldName, rs, i);
                    }
                }
                menu.setParent(parent);
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
        return menu;
    }

    public Menu getByName(String name) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnName)
                .append("FROM T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.id = a.parent_id ")
                .append("WHERE a.name = ? ");
        Menu menu = new Menu();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), name);
            while (rs.next()) {
                Menu parent = new Menu();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(menu, fieldName, rs, i);
                    }
                }
                menu.setParent(parent);
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
        return menu;
    }

    public int insert(Menu menu) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_MENU (");
        sql.append("id, parent_id, parent_id_list, name, menu_link, window_val, app_icon, menu_icon, sort, " +
                "DPY_FLG, auth, crtr, crt_time, uptr, upt_time, rmrk, del_flg) VALUES (");
        sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        Object[] params = {
                menu.getId(), menu.getParentId(), menu.getParentIdList(),
                menu.getName(), menu.getMenuLink(), menu.getWindowVal(), menu.getAppIcon(),
                menu.getMenuIcon(), menu.getSort(), menu.getDpyFlg(), menu.getAuth(),
                menu.getCrtr(), menu.getCrtTime(), menu.getUptr(), menu.getUptTime(),
                menu.getRmrk(), DEL_FLAG_NORMAL
        };
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?)");
        } else {
            sql.append("?, ?, ?, ?, ?)");
        }
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int update(Menu menu) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_MENU SET parent_id=?, parent_id_list=?, name=?, menu_link=?, ")
                .append("window_val=?, app_icon=?, menu_icon=?, sort=?, DPY_FLG=?, auth=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time = to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), rmrk=? WHERE id =?");
        } else {
            sql.append("upt_time = ?, rmrk=? WHERE id =?");
        }
        Object[] params = {
                menu.getParentId(), menu.getParentIdList(), menu.getName(),
                menu.getMenuLink(), menu.getWindowVal(), menu.getAppIcon(), menu.getMenuIcon(),
                menu.getSort(), menu.getDpyFlg(), menu.getAuth(), menu.getUptr(),
                menu.getUptTime(), menu.getRmrk(), menu.getId()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int updateParentIds(Menu menu) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "UPDATE T_SYS_MENU SET parent_id=?, parent_id_list=? WHERE id=?";
        Object[] params = {menu.getParentId(), menu.getParentIdList(), menu.getId()};
        try {
            i = session.execute(sql, params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int updateSort(Menu menu) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "UPDATE T_SYS_MENU SET sort=? WHERE id=?";
        Object[] params = {menu.getSort(), menu.getId()};
        try {
            i = session.execute(sql, params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    /**
     * 从list中移除父菜单列表不存在的菜单
     * @param list
     */
    private void removeParentIdNotExist(List<Menu> list) {

        // 存放菜单ID、父菜单ID
        Map<String, String> idParentIdMap = new HashMap<>();
        for (Menu menu : list) {
            idParentIdMap.put(menu.getId(), menu.getParentId());
        }
        // 去掉idParentIdMap中父菜单ID不存在的菜单，不包含根菜单"1"
        Iterator it = idParentIdMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            if (!"1".equals(entry.getKey()) && !idParentIdMap.containsKey(entry.getValue())) {
                it.remove();
            }
        }
        // 去掉父菜单ID不存在的菜单
        Iterator menuIt = list.iterator();
        while (menuIt.hasNext()) {
            Menu temp = (Menu) menuIt.next();
            if (!idParentIdMap.containsKey(temp.getId())) {
                menuIt.remove();
            }
        }

    }

    private static final String columnName = "a.id, a.parent_id AS \"parent.id\", a.parent_id_list, a.name, a.menu_link, a.window_val, a.app_icon, a.menu_icon, a.sort, a.DPY_FLG, a.auth, a.rmrk, a.crtr, a.crt_time, a.uptr, a.upt_time, a.del_flg, p.name AS \"parent.name\" ";
}
