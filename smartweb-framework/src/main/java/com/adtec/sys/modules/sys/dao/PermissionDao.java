package com.adtec.sys.modules.sys.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;


@Component
public class PermissionDao {
    private final static Logger logger = LoggerFactory.getLogger(PermissionDao.class);

    /**
     * 删除可被分配的所有菜单ID
     * @param dbFieldName
     * @param id
     * @param authTp
     * @param menuIds
     * @param isRole
     */
    public void deleteMenusByMenuIds(String dbFieldName, String id, String authTp, List<String> menuIds, boolean isRole) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM T_SYS_PERMISSION_GROUP WHERE ").append(dbFieldName).append("=? and auth_tp=? ");
        List<Object> params = Lists.newArrayList();
        params.add(id);
        params.add(authTp);
        if (processMenuIds(menuIds, sql, params, isRole)) return;
        try {
            session.executeByList(sql.toString(), params);
        } catch (SQLException e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
    }

    public void deleteMenusByMenuIds(String dbFieldName, String id, String authTp) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM T_SYS_PERMISSION_GROUP WHERE ").append(dbFieldName).append("=? and auth_tp=? ");
        List<Object> params = Lists.newArrayList();
        params.add(id);
        params.add(authTp);
        try {
            session.executeByList(sql.toString(), params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
    }

    private boolean processMenuIds(List<String> menuIds, StringBuilder sql, List<Object> params, boolean isRole) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return true;
        } else {
            // 如果是角色，不删除根菜单
            if (isRole && menuIds.contains("1") && menuIds.size() == 1) return true;
            sql.append(" AND menu_id IN  (");
            for (String menuId : menuIds) {
                // 如果是角色，不删除根菜单
                if (isRole && "1".equals(menuId)) continue;
                sql.append("?, ");
                params.add(menuId);
            }
            sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
        }
        return false;
    }

    public List<Menu> findMenuListByRoleIdByAuthTp(String roleId, String authTp) {
        String dbFieldName = "ROLE_ID";
        StringBuilder sql = getSelectSql(dbFieldName);
        sql.append(" ORDER BY a.sort ");
        return getMenus(roleId, sql, authTp);
    }

    public List<Menu> findMenuListCanAllotte(String authTp, String roleId) {
        StringBuilder sql = new StringBuilder();
        // 当前用户所在角色拥有的权限
        List<String> roleIdList = UserUtils.getUser().getRoleIdList();
        List<Menu> list = Lists.newArrayList();
        if (roleIdList.contains(roleId) && roleIdList.size() == 1) {
            return list;
        }
        List<Object> params = Lists.newArrayList();

        sql.append("select DISTINCT ")
                .append(columnName)
                .append("from T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.ID = a.PARENT_ID ")
                .append("LEFT JOIN T_SYS_PERMISSION_GROUP om ON a.ID = om.MENU_ID ")
                .append("WHERE om.ROLE_ID in (");
        for (String id : roleIdList) {
            // 排除当前被分配的角色ID
            if (id.equals(roleId)) continue;
            sql.append("?, ");
            params.add(id);
        }
        sql.deleteCharAt(sql.lastIndexOf(",")).append(") ");
        if ("transfer".equals(authTp)) {
            // 有所有权，或者有转授权
            sql.append("AND (om.auth_tp = ? or om.auth_tp = ? or om.auth_tp = ? or om.auth_tp = ?) ");
            params.add("own");
            params.add("OWN");
            params.add("transfer");
            params.add("TRANSFER");
        } else if ("use".equals(authTp)) {
            sql.append("AND (om.auth_tp = ? or om.auth_tp = ?) ");
            params.add("transfer");
            params.add("TRANSFER");
        }
        // 不包含当前角色有所有权的菜单
        sql.append("AND a.ID not in (SELECT MENU_ID from T_SYS_PERMISSION_GROUP where role_id=? and MENU_ID != '1' and (auth_tp=? or auth_tp=?) ) ");
        params.add(roleId);
        params.add("own");
        params.add("OWN");
        sql.append("AND a.del_flg = '0' ORDER BY a.sort ");
        ResultSet rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            processResultSet(list, rs, session);
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

        // 从list中移除父菜单ID在列表不存在的菜单
        removeParentIdNotExist(list);
        // 若返回数据只有1-功能菜单，则删除此数据
        if(list != null && list.size() == 1){
        	list.remove(0);
        }
        return list;
    }

    public List<Menu> findMenuListByRoleId(String roleId, String authTp, String dbFieldName) {
        StringBuilder sql = getSelectSql(dbFieldName);
        sql.append(" ORDER BY a.sort ");
        String authTpValue = "";
        if ("transfer".equals(authTp)) {
            authTpValue = "transfer";
        } else if ("use".equals(authTp)) {
            authTpValue = "use";
        }
        return getMenus(roleId, sql, authTpValue);
    }

    public List<Menu> findMenusById(String id, String authTp, boolean isParent, String dbFieldName) {
        StringBuilder sql = getSelectSql(dbFieldName);
        sql.append(" ORDER BY a.sort ");
        return getMenus(id, authTp, isParent, sql);
    }

    public List<Menu> findMenusByIdAndAuthTp(String id, String authTp, String dbFieldName) {
        StringBuilder sql = getSelectSql(dbFieldName);
        sql.append(" ORDER BY a.sort ");
        return getMenus(id, sql, authTp);
    }

    private List<Menu> getMenus(String id, String authTp, boolean isParent, StringBuilder sql) {
        String authTpValue = "";
        if ("transfer".equals(authTp)) {
            if (isParent) {
                authTpValue = "own";
            } else {
                authTpValue = "transfer";
            }
        } else if ("use".equals(authTp)) {
            if (isParent) {
                authTpValue = "transfer";
            } else {
                authTpValue = "use";
            }
        }
        return getMenus(id, sql, authTpValue);
    }

    private List<Menu> getMenus(String id, StringBuilder sql, String authTpValue) {
        List<Menu> list = Lists.newArrayList();
        Object[] params = {id, authTpValue};
        ResultSet rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getResultSet(sql.toString(), params);
            processResultSet(list, rs, session);
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

        // 从list中移除父菜单ID在列表不存在的菜单
        removeParentIdNotExist(list);

        return list;
    }

    private void processResultSet(List<Menu> list, ResultSet rs, IDBSession session) throws Exception {
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
    }

    private StringBuilder getSelectSql(String dbFieldName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT ")
                .append(columnName)
                .append("from T_SYS_MENU a ")
                .append("LEFT JOIN T_SYS_MENU p ON p.ID = a.PARENT_ID ")
                .append("LEFT JOIN T_SYS_PERMISSION_GROUP om ON a.ID = om.MENU_ID ")
                .append("WHERE om.").append(dbFieldName).append("=? ");
        sql.append("AND om.auth_tp=? AND a.del_flg = '0' ");
        return sql;
    }

    /**
     * 创建二级菜单时，增加当前权限组，一级菜单"1"的所有权、转授权、使用权
     *
     * @param user
     */
    public void insertOneLevelPermission(User user) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from T_SYS_PERMISSION_GROUP where menu_id='1' and (brch_id=? or tnt_id=? or lega_id=? or role_id in (");
        List<Object> params = Lists.newArrayList();
        params.add(user.getOffice().getId());
        params.add(user.getRent().getId());
        params.add(user.getCorporation().getId());
        for (String roleId : user.getRoleIdList()) {
            sql.append("?, ");
            params.add(roleId);
        }
        sql.deleteCharAt(sql.lastIndexOf(",")).append(") )");
        IDBSession session = DBSessionFactory.getSession();
        try {
            session.executeByList(sql.toString(), params);

            List<Menu> menuList = Lists.newArrayList();
            menuList.add(new Menu("1"));

            // 所属法人权限组
            PermissionDTO corporationVo = new PermissionDTO();
            corporationVo.setMenuList(menuList);
            corporationVo.setId(user.getCorporation().getId());
            corporationVo.setAuthTp("use");
            insertPermissionMenu(corporationVo, "LEGA_ID");
            corporationVo.setAuthTp("transfer");
            insertPermissionMenu(corporationVo, "LEGA_ID");
            corporationVo.setAuthTp("own");
            insertPermissionMenu(corporationVo, "LEGA_ID");

            // 所属租户权限组
            PermissionDTO rentVO = new PermissionDTO();
            rentVO.setMenuList(menuList);
            rentVO.setId(user.getRent().getId());
            rentVO.setAuthTp("use");
            insertPermissionMenu(rentVO, "TNT_ID");
            rentVO.setAuthTp("transfer");
            insertPermissionMenu(rentVO, "TNT_ID");
            rentVO.setAuthTp("own");
            insertPermissionMenu(rentVO, "TNT_ID");

            // 所属机构权限组
            PermissionDTO officeVO = new PermissionDTO();
            officeVO.setMenuList(menuList);
            officeVO.setId(user.getOffice().getId());
            officeVO.setAuthTp("use");
            insertPermissionMenu(officeVO, "BRCH_ID");
            officeVO.setAuthTp("transfer");
            insertPermissionMenu(officeVO, "BRCH_ID");
            officeVO.setAuthTp("own");
            insertPermissionMenu(officeVO, "BRCH_ID");

            // 所属角色权限组
            PermissionDTO roleVO = new PermissionDTO();
            roleVO.setMenuList(menuList);
            for (String roleId : user.getRoleIdList()) {
                roleVO.setId(roleId);
                roleVO.setAuthTp("use");
                insertPermissionMenu(roleVO, "ROLE_ID");
                roleVO.setAuthTp("transfer");
                insertPermissionMenu(roleVO, "ROLE_ID");
                roleVO.setAuthTp("own");
                insertPermissionMenu(roleVO, "ROLE_ID");
            }
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
    }

    public int insertPermissionMenu(PermissionDTO permissionDTO, String type) {
        int i;
        User user = UserUtils.getUser();
        String id = user.getId();
        String dateTime = DateUtil.getDateTime();
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_PERMISSION_GROUP(").append(type).append(", auth_tp, menu_id, crtr, crt_time, uptr, upt_time, del_flg) ");
        List<Object> params = Lists.newArrayList();
        for (Menu menu : permissionDTO.getMenuList()) {
            sql.append("SELECT ?,?,?,?,");
            params.add(permissionDTO.getId());
            params.add(permissionDTO.getAuthTp());
            params.add(menu.getId());
            params.add(id);
            if ("kingbase8".equals(session.getDbtype())) {
                sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), '0'");
            } else {
                sql.append("?, ?, ?, '0' ");
            }
            if ("db2".equals(session.getDbtype())) {
                sql.append("FROM sysibm.DUAL union all ");
            } else if ("sqlserver".equals(session.getDbtype())) {
                sql.append(" union all ");
            } else {
                sql.append("FROM dual union all ");
            }
            params.add(dateTime);
            params.add(id);
            params.add(dateTime);

            // 分配转授权时，默认加上菜单的使用权
            // 转授权不包含使用权
//            if ("transfer".equals(permissionDTO.getAuthTp())) {
//                sql.append("SELECT ?,?,?,?,");
//                params.add(permissionDTO.getId());
//                params.add("use");
//                params.add(menu.getId());
//                params.add(id);
//                if ("kingbase8".equals(session.getDbtype())) {
//                    sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), '0'");
//                } else {
//                    sql.append("?, ?, ?, '0'");
//                }
//                sql.append("FROM dual union all ");
//                params.add(dateTime);
//                params.add(id);
//                params.add(dateTime);
//            }
        }
        try {
            i = session.executeByList(sql.substring(0, sql.length() - 10), params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" , e);
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
