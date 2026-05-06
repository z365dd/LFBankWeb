package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.*;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

/**
 * 用户DAO接口
 *
 * @version 2014-05-16
 */
@Component
public class UserDao {
    private final static Logger logger = LoggerFactory.getLogger(UserDao.class);

    public int delete(User user) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_USER WHERE id=?";
        Object[] params = {user.getId()};
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

    public int deleteUserRole(User user, String roleId) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM T_SYS_USER_ROLE WHERE user_id=? ");
        List<Object> params = Lists.newArrayList();
        params.add(user.getId());
        if (!DataUtil.isNullStr(roleId)) {
            sql.append("AND role_id=?");
            params.add(roleId);
        }
        try {
            i = session.executeByList(sql.toString(), params);
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

    public List<User> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = getSelectSql();
        sql.append("WHERE a.del_flg='").append(DEL_FLAG_NORMAL).append("' ");
        sql.append("ORDER BY o.brch_code, a.name");
        List<User> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString());
            processResultSet(session, list, rs);
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

    private StringBuilder getSelectSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, a.lega_id AS \"corporation.id\", ")
                .append("co.lega_no AS \"corporation.lega_No\", ")
                .append("co.name AS \"corporation.name\", ")
                .append("co.parent_id_list AS \"corporation.parent_Id_List\", ")
                .append("co.VALID_SWITCH_FLG AS \"corporation.valid_Switch_Flg\", ")
                .append("a.brch_id AS \"office.id\", ")
                .append("a.tnt_id AS \"rent.id\", ")
                .append("o.name AS \"office.name\", ")
                .append("o.BRCH_TP AS \"office.brch_Tp\", ")
                .append("o.parent_id AS \"office.parent.id\", ")
                .append("o.parent_id_list AS \"office.parent_Id_List\", ")
                .append("o.brch_code AS \"office.brch_Code\", ")
                .append("o.VALID_SWITCH_FLG AS \"office.valid_Switch_Flg\", ")
                .append("oa.id AS \"office.area.id\", ")
                .append("oa.name AS \"office.area.name\", ")
                .append("oa.parent_id AS \"office.area.parent.id\", ")
                .append("oa.parent_id_list AS \"office.area.parent_Id_List\", ")
                .append("orent.name AS \"rent.name\", ")
                .append("orent.eng_name AS \"rent.eng_Name\", ")
                .append("orent.parent_id_list AS \"rent.parent_Id_List\", ")
                .append("orent.url AS \"rent.url\", ")
                .append("ou.id AS \"office.oneRspbtPer.id\", ")
                .append("ou.name AS \"office.oneRspbtPer.name\", ")
                .append("ou2.id AS \"office.twoRspbtPer.id\", ")
                .append("ou2.name AS \"office.twoRspbtPer.name\", ")
                .append("r.id AS \"roleList.id\", ")
                .append("r.name AS \"roleList.name\", ")
                .append("r.eng_name AS \"roleList.eng_Name\", ")
                .append("r.role_tp AS \"roleList.role_Tp\", ")
                .append("r.role_tp AS \"role_Tp\", ")
                .append("r.VALID_SWITCH_FLG AS \"roleList.valid_Switch_Flg\", ")
                .append("r.data_scp AS \"roleList.data_Scp\", ")
                .append("b.busi_Name ")
                .append("FROM T_SYS_USER a ")
                .append("JOIN T_SYS_OFFICE o ON o.id = a.brch_id ")
                .append("JOIN T_SYS_AREA oa ON oa.id = o.region_id ")
                .append("JOIN T_SYS_RENT orent ON orent.id = a.tnt_id ")
                .append("LEFT JOIN T_SYS_CORPORATION co ON co.id = a.lega_id ")
                .append("LEFT JOIN T_SYS_USER ou ON ou.id = o.one_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER ou2 ON ou2.id = o.two_rspbt_per ")
                .append("LEFT JOIN T_SYS_USER_ROLE ur ON ur.user_id = a.id ")
                .append("LEFT JOIN T_SYS_ROLE r ON r.id = ur.role_id ")
                .append("LEFT JOIN T_SYS_BUSI_USER bu ON a.id = bu.user_id ")
                .append("LEFT JOIN T_PIP_BUSI b ON bu.busi_id = b.busi_no ");
        return sql;
    }

    public List<User> findList(User user) {
        List<Object> filterParams = Lists.newArrayList();
        return findList(user, filterParams);
    }

    public List<User> findList(User user, List<Object> filterParams) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = getSelectSql();
        sql.append("WHERE a.del_flg='").append(DEL_FLAG_NORMAL).append("' ");
        List<Object> params = Lists.newArrayList();
        if (user.getRent() != null && StringUtils.isNotBlank(user.getRent().getId())) {
            sql.append("AND orent.id=? ");
            params.add(user.getRent().getId());
        }
        if (user.getRole() != null && StringUtils.isNotBlank(user.getRole().getId())) {
            sql.append("AND ur.role_id=? ");
            params.add(user.getRole().getId());
        }
        if (user.getOffice() != null && StringUtils.isNotBlank(user.getOffice().getId())) {
            sql.append("AND (o.id=? OR o.parent_id_list LIKE ?) ");
            params.add(user.getOffice().getId());
            params.add("%," + user.getOffice().getId() + ",%");
        }
        if (!UserUtils.getUser().isAdmin()) {
            sql.append("AND a.id!='1' ");
        }
        if (StringUtils.isNotBlank(user.getLoginName())) {
            sql.append("AND a.login_name LIKE ? ");
            params.add("%" + user.getLoginName() + "%");
        }
        if (StringUtils.isNotBlank(user.getName())) {
            sql.append("AND a.name LIKE ? ");
            params.add("%" + user.getName() + "%");
        }

        if (StringUtils.isNotBlank(user.getBusiName())) {
            sql.append("AND b.busi_name LIKE ? ");
            params.add("%" + user.getBusiName() + "%");
        }

        if (user.getSqlMap().get("dsf") != null) {
            sql.append(user.getSqlMap().get("dsf")).append(" ");
        }
        if (user.getSqlMap().get("dsfr") != null) {
            sql.append(user.getSqlMap().get("dsfr")).append(" ");
        }
        if (user.getSqlMap().get("dsfc") != null) {
            sql.append(user.getSqlMap().get("dsfc")).append(" ");
        }
        params.addAll(filterParams);
        if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
            sql.append("ORDER BY nvl(length(trim(o.parent_id_list)),0) asc,o.name,a.login_name");
        } else if ("sqlserver".equals(session.getDbtype())){
            sql.append("ORDER BY o.parent_id_list asc,o.name,a.login_name");
        } else {
            sql.append("ORDER BY length(trim(o.parent_id_list)) asc,o.name,a.login_name");
        }
        List<User> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            processResultSet(session, list, rs);
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

    private void processResultSet(IDBSession session, List<User> list, ResultSet rs) throws Exception {
        while (rs.next()) {
            User user1 = new User(); // 用户
            Office office = new Office();//用户所属机构
            Office officeParent = new Office();//用户所属机构的父级
            User oneRspbtPer = new User();// 用户所属机构的第一责任人
            User twoRspbtPer = new User();// 用户所属机构的第二责任人
            Area area = new Area();// 用户所属机构的区域
            Area areaParent = new Area();// 用户所属机构的区域的父级

            Rent rent = new Rent();// 用户所属租户
            Corporation corporation = new Corporation();

            List<Role> roleList = Lists.newArrayList(); // 用户所属角色
            Role role = new Role();

            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                if (fieldName.startsWith("office.")) {
                    if (fieldName.startsWith("office.parent.")) {
                        session.setProperty(officeParent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("office.onerspbtper.")) {
                        session.setProperty(oneRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("office.tworspbtper.")) {
                        session.setProperty(twoRspbtPer, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else if (fieldName.startsWith("office.area.")) {
                        if (fieldName.startsWith("office.area.parent.")) {
                            session.setProperty(areaParent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                        } else {
                            session.setProperty(area, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                        }
                    } else {
                        session.setProperty(office, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    }
                } else if (fieldName.startsWith("rent.")) {
                    session.setProperty(rent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                } else if (fieldName.startsWith("corporation.")) {
                    session.setProperty(corporation, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                } else if (fieldName.startsWith("rolelist.")) {
                    session.setProperty(role, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                } else {
                    session.setProperty(user1, fieldName, rs, i);
                }
            }
            area.setParent(areaParent);
            office.setOneRspbtPer(oneRspbtPer);
            office.setTwoRspbtPer(twoRspbtPer);
            office.setArea(area);
            office.setParent(officeParent);

            user1.setOffice(office);
            user1.setRent(rent);
            user1.setCorporation(corporation);

            int index = list.indexOf(user1);
            if (index > -1) {
                list.get(index).getRoleList().add(role);
            } else {
                roleList.add(role);
                user1.setRoleList(roleList);
                list.add(user1);
            }
        }
    }

    public List<User> findUserByBrchId(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id, a.name, a.login_name ")
                .append("FROM T_SYS_USER a ")
                .append("WHERE a.del_flg='").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.brch_id=? ")
                .append("ORDER BY a.name");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, user.getOffice().getId());
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

    public List<User> findUserByTntId(Rent rent) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id, a.name, a.login_name ")
                .append("FROM T_SYS_USER a ")
                .append("WHERE a.del_flg='").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.tnt_id=? ")
                .append("ORDER BY a.name");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, rent.getId());
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

    public List<User> findUserByLegaId(Corporation corporation) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id, a.name, a.login_name ")
                .append("FROM T_SYS_USER a ")
                .append("WHERE a.del_flg='").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.lega_id=? ")
                .append("ORDER BY a.name");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, corporation.getId());
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

    public List<User> findUserByOfficeId(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id, a.name, a.login_name ")
                .append("FROM t_sys_user a ")
                .append("WHERE a.del_flg='").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.brch_id=? ")
                .append("ORDER BY a.name");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, user.getOffice().getId());
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
    
    public List<User> findUserByRentId(Rent rent) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id,name,login_name FROM t_sys_user WHERE del_flg = '").append(DEL_FLAG_NORMAL)
                .append("' AND tnt_id =? ORDER BY name");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, rent.getId());
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
    
    public List<User> findUserByCorId(Corporation cor) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id,name,login_name FROM t_sys_user WHERE del_flg = '").append(DEL_FLAG_NORMAL)
        .append("' AND lega_id =? ORDER BY name");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, cor.getId());
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
    
    /**
     * 根据角色Id查找用户列表
     *
     * @param roleId
     * @return
     */
    public List<User> findUserByRoleId(String roleId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("a.id, a.name, a.login_name ")
                .append("FROM t_sys_user a ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND a.id in (select r.user_id from t_sys_user_role r where r.role_id=?)");
        List<User> list;
        try {
            list = session.getObjectList(sql.toString(), User.class, roleId);
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

    public User get(String userId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = getSelectSql();
        sql.append("WHERE a.id=? ");
        List<User> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), userId);
            processResultSet(session, list, rs);
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
        return list.isEmpty() ? null : list.get(0);
    }

    public User getByLoginName(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = getSelectSql();
        sql.append("WHERE a.login_name=? ");
        sql.append("AND a.del_flg='").append(DEL_FLAG_NORMAL).append("' ");
        List<User> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), user.getLoginName());
            processResultSet(session, list, rs);
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
        return list.isEmpty() ? null : list.get(0);
    }

    public User getByName(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = getSelectSql();
        sql.append("WHERE a.name=? ");
        sql.append("AND a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        List<User> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), user.getName());
            processResultSet(session, list, rs);
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
        return list.isEmpty() ? null : list.get(0);
    }

    public User getByUserNo(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = getSelectSql();
        sql.append("WHERE a.user_no=? ");
        sql.append("AND a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        List<User> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), user.getUserNo());
            processResultSet(session, list, rs);
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
        return list.isEmpty() ? null : list.get(0);
    }

    public List<String> getUserIdFromUserRole(String roleId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "select user_id from T_SYS_USER_ROLE where role_id=? ";
        List<String> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql, roleId);
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

    public int insert(User user) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_USER(id, lega_id, brch_id, tnt_id, login_name, pwd, user_no, name, email, tel_no, phone_no, crtr, crt_time, uptr, upt_time, rmrk, LOGIN_SWITCH_FLG, img, del_flg, user_lvl")
                .append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?, ?)");
        } else {
            sql.append("?, ?, ?, ?, ?, ?, ?, ?)");
        }
        Object[] params = {
                user.getId(), user.getCorporation()==null?"":user.getCorporation().getId(), user.getOffice().getId(), user.getRent()==null?"":user.getRent().getId(), user.getLoginName(),
                user.getPwd(), user.getUserNo(), user.getName(), user.getEmail(), user.getTelNo(), user.getPhoneNo(),
                user.getCrtr(), user.getCrtTime(), user.getUptr(), user.getUptTime(), user.getRmrk(), user.getLoginSwitchFlg(),
                user.getImg(), user.getDelFlg(), user.getUserLvl()
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

    public void insertUserRole(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_USER_ROLE(user_id, role_id) ");
        List<Object> params = Lists.newArrayList();
        for (Role role : user.getRoleList()) {
            if ("db2".equals(session.getDbtype())) {
                sql.append("SELECT ?,? FROM sysibm.DUAL union all ");
            } else if ("sqlserver".equals(session.getDbtype())) {
                sql.append(" SELECT ?,? union all ");
            }  else {
                sql.append("SELECT ?,? FROM dual union all ");
            }
            params.add(user.getId());
            params.add(role.getId());
        }
        try {
            session.executeByList(sql.substring(0, sql.length() - 10), params);
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
    }

    public void saveUserRole(String userId, String roleId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "INSERT INTO T_SYS_USER_ROLE(user_id, role_id) VALUES(?,?)";
        try {
            session.execute(sql, userId, roleId);
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
    }

    public int update(User user) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_USER SET lega_id=?, brch_id=?, tnt_id=?, login_name=?, pwd=?, user_no=?, name=?, email=?, tel_no=?, phone_no=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
        } else {
            sql.append("upt_time=?, ");
        }
        sql.append("rmrk=?, LOGIN_SWITCH_FLG=?, img=?, user_lvl=? WHERE id=? ");
        Object[] params = {
                user.getCorporation().getId(), user.getOffice().getId(), user.getRent().getId(), user.getLoginName(),
                user.getPwd(), user.getUserNo(), user.getName(), user.getEmail(), user.getTelNo(), user.getPhoneNo(),
                user.getUptr(), user.getUptTime(), user.getRmrk(), user.getLoginSwitchFlg(),
                user.getImg(), user.getUserLvl(), user.getId()
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

    public void updateLoginInfo(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_USER SET login_ip=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("login_Date=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss') ");
        } else {
            sql.append("login_Date=? ");
        }
        sql.append("WHERE id=? ");
        Object[] params = {user.getLoginIp(), user.getLoginDate(), user.getId()};
        try {
            session.execute(sql.toString(), params);
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
    }

    public int updateOffice(String userId, String brchId) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "update T_SYS_USER set brch_id=? where id=?";
        try {
            i = session.execute(sql, brchId, userId);
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

    public void updatePasswordById(User user) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "UPDATE T_SYS_USER SET pwd=? WHERE id=?";
        try {
            session.execute(sql, user.getPwd(), user.getId());
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
    }

    public void updateUserInfo(User user) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_USER SET email=?, tel_no=?, phone_no=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
        } else {
            sql.append("upt_time=?, ");
        }
        sql.append("rmrk=?, img=? WHERE id=?");
        Object[] params = {
                user.getEmail(), user.getTelNo(), user.getPhoneNo(), user.getUptr(),
                user.getUptTime(), user.getRmrk(), user.getImg(), user.getId()
        };
        try {
            session.execute(sql.toString(), params);
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
    }
    
    /**
     * 获取当前用户所属租户和下级租户的所有用户
     * @param user
     * @return
     */
    public List<User> getOwnAndSubUser(User user){
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from T_SYS_USER u where u.del_flg='0' and u.tnt_id in ( ")
        .append(" select a.id from T_SYS_RENT a left join T_SYS_RENT p on p.id = a.parent_id where ")
        .append(" a.del_flg = '0' and a.open_stat = '0' and (a.id = ? or a.parent_id_list like ? ))");        
        List<Object> params = Lists.newArrayList();
        if(user.getRent()==null){
            return new ArrayList<User>();
        }else{
            params.add(user.getRent().getId());
            params.add(user.getRent().getParentIdList() + user.getRent().getId() + ",%");            
        }
        logger.info("sql="+sql.toString());
        List<User> list = Lists.newArrayList();
        try {
            list = session.getObjectListByList(sql.toString(), User.class, params);
        } catch (Exception e) {
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
}
