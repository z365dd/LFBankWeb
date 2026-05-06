/**
 * 系统名称: SmartWeb平台
 * 模块名称: sys持久化模块
 * 功能描述: 权限维度数据库操作
 * 类 名 称  : SysPermissionWeightDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 20190901<br>
 * 系统版本: V1.0.0<br>
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * <p>
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.SysPermissionWeightDO;
import com.adtec.sys.modules.sys.entity.User;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

/**
 * 权限维度Dao接口
 * @author 权限维度
 * @version 20190901
 */
@Component
public class SysPermissionWeightDao implements IBaseDao<SysPermissionWeightDO> {
    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(SysPermissionWeightDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_PERMISSION_WEIGHT";

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public SysPermissionWeightDO get(String id) {
        SysPermissionWeightDO obj = new SysPermissionWeightDO();
        obj.setId(id);
        return get(obj);
    }

    /**
     * 获取单条数据
     * @param obj
     * @return
     */
    @Override
    public SysPermissionWeightDO get(SysPermissionWeightDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? AND del_flg='" + DEL_FLAG_NORMAL + "'");
        SysPermissionWeightDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), SysPermissionWeightDO.class, obj.getId());
        } catch (Exception e) {
            logger.error("获取权限维度异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
        }
        return rs;
    }

    public boolean initPermission(String isAll) {
        StringBuilder sql = new StringBuilder();
        IDBSession session = DBSessionFactory.getSession();
        ResultSet rs = null;
        try {
            // 检查菜单创建者
            sql.append("select crtr from T_SYS_MENU where crtr not in (select id from T_SYS_USER)");
            rs = session.getResultSet(sql.toString());
            if (rs.next()) {
                throw new BaseException(SysErr.E_MESSAGE, "菜单创建者不是系统的用户，请检查创建者ID：【" + rs.getString(1) + "】");
            }

            // 清空所有权限
            if ("true".equals(isAll)) {
                // 清理所有权限组
                session.execute("delete from T_SYS_PERMISSION_GROUP where 1=1");
            } else {
                List<String> sqlList = Lists.newArrayList();
                sqlList.add("DELETE from T_SYS_PERMISSION_GROUP WHERE BRCH_ID NOT IN (select ID from T_SYS_OFFICE)");
                sqlList.add("DELETE from T_SYS_PERMISSION_GROUP WHERE TNT_ID NOT IN (select ID from T_SYS_RENT)");
                sqlList.add("DELETE from T_SYS_PERMISSION_GROUP WHERE LEGA_ID NOT IN (select ID from T_SYS_CORPORATION)");
                sqlList.add("DELETE from T_SYS_PERMISSION_GROUP WHERE ROLE_ID NOT IN (select ID from T_SYS_ROLE)");
                session.executeBatch(sqlList);
            }

            // 初始化部分权限
            if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype()) || "opengauss".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();
                // 初始化机构维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'own', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where brch_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'transfer', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where brch_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where brch_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化租户维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'own', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where tnt_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'transfer', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where tnt_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where tnt_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化法人维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'own', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where lega_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'transfer', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where lega_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where lega_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化角色维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'own', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur  WHERE a.crtr = u.ID  AND u.ID = ur.USER_ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where role_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'transfer', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur  WHERE a.crtr = u.ID  AND u.ID = ur.USER_ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where role_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur  WHERE a.crtr = u.ID  AND u.ID = ur.USER_ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where role_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 已经创建二级菜单的权限组，增加一级菜单的权限
                // 初始化机构维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'own', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.BRCH_ID not in (select brch_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and brch_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'transfer', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.BRCH_ID not in (select brch_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and brch_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'use', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.BRCH_ID not in (select brch_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and brch_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化租户维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'own', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.TNT_ID not in (select tnt_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and tnt_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'transfer', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.TNT_ID not in (select tnt_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and tnt_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'use', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.TNT_ID not in (select tnt_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and tnt_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化法人维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'own', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.LEGA_ID not in (select lega_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and lega_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'transfer', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.LEGA_ID not in (select lega_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and lega_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'use', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.LEGA_ID not in (select lega_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and lega_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化角色维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'own', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' AND u.ID = ur.USER_ID and ur.ROLE_ID not in (select role_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and role_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'transfer', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' AND u.ID = ur.USER_ID and ur.ROLE_ID not in (select role_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and role_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'use', '1', '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' AND u.ID = ur.USER_ID and ur.ROLE_ID not in (select role_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and role_id is not null and AUTH_TP = 'use') ORDER BY a.ID");
                session.executeBatch(sqlList);

            } else if ("mysql".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();
                // 初始化机构维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'own', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where brch_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'transfer', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where brch_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where brch_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化租户维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'own', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where tnt_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'transfer', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where tnt_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where tnt_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化法人维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'own', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where lega_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'transfer', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where lega_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u  WHERE a.crtr = u.ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where lega_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化角色维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'own', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur  WHERE a.crtr = u.ID  AND u.ID = ur.USER_ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where role_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'transfer', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur  WHERE a.crtr = u.ID  AND u.ID = ur.USER_ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where role_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur  WHERE a.crtr = u.ID  AND u.ID = ur.USER_ID and a.id not in (select menu_id from T_SYS_PERMISSION_GROUP where role_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 已经创建二级菜单的权限组，增加一级菜单的权限
                // 初始化机构维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'own', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.BRCH_ID not in (select brch_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and brch_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'transfer', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.BRCH_ID not in (select brch_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and brch_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT u.BRCH_ID, NULL, NULL, NULL, 'use', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.BRCH_ID not in (select brch_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and brch_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化租户维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'own', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.TNT_ID not in (select tnt_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and tnt_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'transfer', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.TNT_ID not in (select tnt_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and tnt_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, u.TNT_ID, NULL, NULL, 'use', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.TNT_ID not in (select tnt_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and tnt_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化法人维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'own', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.LEGA_ID not in (select lega_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and lega_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'transfer', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.LEGA_ID not in (select lega_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and lega_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, u.LEGA_ID, NULL, 'use', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' and u.LEGA_ID not in (select lega_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and lega_id is not null and AUTH_TP = 'use') ORDER BY a.ID");

                // 初始化角色维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'own', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' AND u.ID = ur.USER_ID and ur.ROLE_ID not in (select role_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and role_id is not null and AUTH_TP = 'own') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'transfer', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' AND u.ID = ur.USER_ID and ur.ROLE_ID not in (select role_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and role_id is not null and AUTH_TP = 'transfer') ORDER BY a.ID");
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, ur.ROLE_ID, 'use', '1', '1', NOW(), '1', NOW(), NULL, a.del_flg FROM T_SYS_MENU a, T_SYS_USER u, T_SYS_USER_ROLE ur WHERE a.crtr = u.ID and a.PARENT_ID = '1' and a.crtr != '1' AND u.ID = ur.USER_ID and ur.ROLE_ID not in (select role_id from T_SYS_PERMISSION_GROUP where menu_id = '1' and role_id is not null and AUTH_TP = 'use') ORDER BY a.ID");
                session.executeBatch(sqlList);
                
            }
        } catch (Exception e) {
            logger.error("", e);
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return initMenuPermission();
    }

    /**
     * 初始化菜单“我的面板”使用权限，给所有的机构、租户、法人、角色
     * @return
     */
    public boolean initMenuPermission() {
        IDBSession session = DBSessionFactory.getSession();
        ResultSet rs = null;
        try {
            session.getConnection();
            // 初始化部分权限
            if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();
                // 初始化机构维度权限
                rs = session.getResultSet("select id from t_sys_office where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT '" + id + "', NULL, NULL, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a  WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE BRCH_ID IS NOT NULL and BRCH_ID='" +  id + "' AND AUTH_TP = 'use')");
                }

                // 初始化租户维度权限
                rs = session.getResultSet("select id from t_sys_rent where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, '" +  id + "', NULL, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a  WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE TNT_ID IS NOT NULL and TNT_ID='" +  id + "' AND AUTH_TP = 'use')");
                }

                // 初始化法人维度权限
                rs = session.getResultSet("select i d from t_sys_corporation where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, '" +  id + "', NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a  WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE LEGA_ID IS NOT NULL and LEGA_ID='" +  id + "' AND AUTH_TP = 'use')");
                }

                // 初始化角色维度权限
                rs = session.getResultSet("select id from t_sys_role where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, '" +  id + "', 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE ROLE_ID IS NOT NULL and ROLE_ID='" +  id + "' AND AUTH_TP = 'use')");
                }
                session.executeBatch(sqlList);

            } else if ("mysql".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();
                // 初始化机构维度权限
                rs = session.getResultSet("select id from t_sys_office where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT '" +  id + "', NULL, NULL, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE BRCH_ID IS NOT NULL and BRCH_ID='" +  id + "' AND AUTH_TP = 'use')");
                }

                // 初始化租户维度权限
                rs = session.getResultSet("select id from t_sys_rent where id != '1' ");
                while (rs.next()) {
                    //判空处理
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, '" +  id + "', NULL, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE TNT_ID IS NOT NULL and TNT_ID='" +  id + "' AND AUTH_TP = 'use')");
                }

                // 初始化法人维度权限
                rs = session.getResultSet("select id from t_sys_corporation where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, '" +  id + "', NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE LEGA_ID IS NOT NULL and LEGA_ID='" +  id + "' AND AUTH_TP = 'use')");
                }

                // 初始化角色维度权限
                rs = session.getResultSet("select id from t_sys_role where id != '1' ");
                while (rs.next()) {
                    String id = rs.getString(1);
                    id = id == null ? "" : id;
                    sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, '" +  id + "', 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE ROLE_ID IS NOT NULL and ROLE_ID='" +  id + "' AND AUTH_TP = 'use')");
                }
                session.executeBatch(sqlList);
            }
        } catch (Exception e) {
            logger.error("初始化菜单“我的面板”使用权限", e);
            return false;
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return true;
    }

    /**
     * 初始化菜单“我的面板”使用权限，给当前新增的机构
     * @return
     */
    public boolean initMenuPermissionToOffice(String id) {
        IDBSession session = DBSessionFactory.getSession();
        try {
            // 初始化部分权限
            if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();
                // 初始化机构维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT '"+id+"', NULL, NULL, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a  WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE BRCH_ID IS NOT NULL and BRCH_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);

            } else if ("mysql".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();
                // 初始化机构维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT '"+id+"', NULL, NULL, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE BRCH_ID IS NOT NULL and BRCH_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return true;
    }

    /**
     * 初始化菜单“我的面板”使用权限，给当前新增的租户
     * @return
     */
    public boolean initMenuPermissionToRent(String id) {
        IDBSession session = DBSessionFactory.getSession();
        try {
            // 初始化部分权限
            if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();

                // 初始化租户维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, '"+id+"', NULL, NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a  WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE TNT_ID IS NOT NULL and TNT_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);

            } else if ("mysql".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();

                // 初始化租户维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, '"+id+"', NULL, NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE TNT_ID IS NOT NULL and TNT_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return true;
    }

    /**
     * 初始化菜单“我的面板”使用权限，给当前新增的法人
     * @return
     */
    public boolean initMenuPermissionToCorporation(String id) {
        IDBSession session = DBSessionFactory.getSession();
        try {
            // 初始化部分权限
            if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();

                // 初始化法人维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, '"+id+"', NULL, 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a  WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE LEGA_ID IS NOT NULL and LEGA_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);

            } else if ("mysql".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();

                // 初始化法人维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, '"+id+"', NULL, 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE LEGA_ID IS NOT NULL and LEGA_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return true;
    }

    /**
     * 初始化菜单“我的面板”使用权限，给当前新增的角色
     * @return
     */
    public boolean initMenuPermissionToRole(String id) {
        IDBSession session = DBSessionFactory.getSession();
        try {
            // 初始化部分权限
            if ("oracle".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();

                // 初始化角色维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, '"+id+"', 'use', a.ID, '1', SYSDATE, '1', SYSDATE, NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE ROLE_ID IS NOT NULL and ROLE_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);

            } else if ("mysql".equals(session.getDbtype())) {
                List<String> sqlList = Lists.newArrayList();

                // 初始化角色维度权限
                sqlList.add("INSERT INTO T_SYS_PERMISSION_GROUP ( BRCH_ID, TNT_ID, LEGA_ID, ROLE_ID, AUTH_TP, MENU_ID, crtr, crt_time, uptr, upt_time, rmrk, del_flg ) SELECT NULL, NULL, NULL, '"+id+"', 'use', a.ID, '1', NOW(), '1', NOW(), NULL, a.del_flg  FROM T_SYS_MENU a WHERE a.ID IN ('1','27','28','29','30','57','58','59','d9aa04cd02154661aa23256e23735902') and a.ID NOT IN (SELECT MENU_ID FROM T_SYS_PERMISSION_GROUP WHERE ROLE_ID IS NOT NULL and ROLE_ID='"+id+"' AND AUTH_TP = 'use')");

                session.executeBatch(sqlList);
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return true;
    }

    /**
     * 插入数据
     * @param obj
     * @return
     */
    @Override
    public int insert(SysPermissionWeightDO obj) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
        } catch (Exception e) {
            logger.error("新增权限维度交易异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增权限维度交易失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return rs;
    }

    /**
     * 更新数据
     * @param obj
     * @return
     */
    @Override
    public int update(SysPermissionWeightDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            List<String> matchFields = obj.getMatchFields();
            rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
        } catch (Exception e) {
            logger.error("修改权限维度异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改权限维度失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return rs;
    }

    /**
     * 根据主键id删除数据（一般为逻辑删除，更新del_flg字段为1）
     * @param id
     * @see public int delete(T entity)
     * @return
     */
    public int delete(String id) {
        SysPermissionWeightDO obj = new SysPermissionWeightDO();
        obj.setId(id);
        return delete(obj);
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flg字段为1）
     * @param obj
     * @return
     */
    @Override
    public int delete(SysPermissionWeightDO obj) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            String sql = "delete from T_SYS_PERMISSION_WEIGHT where id=?";
            rs = session.execute(sql, obj.getId());
            if (rs == 0) {
                throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数：" + rs);
            }
        } catch (SQLException e) {
            logger.error("删除交易异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return rs;
    }

    /**
     * 数据库多笔查询，不分页
     * @param obj 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<SysPermissionWeightDO> list(SysPermissionWeightDO obj) {
        return list(obj, 0, 0);
    }

    /**
     * 数据库多笔查询，支持分页
     * @param obj 数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<SysPermissionWeightDO> list(SysPermissionWeightDO obj, int start, int limit) {
        logger.debug("SysPermissionWeightDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<SysPermissionWeightDO> list = null;
        IDBSession session = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj));
            sql.append(" order by upt_time desc");
            logger.debug("sql=" + sql.toString());
            session = DBSessionFactory.getSession();
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), SysPermissionWeightDO.class);
            } else {
                list = session.getObjectListForPage(sql.toString(), SysPermissionWeightDO.class, start, limit);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
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
     * 数据库多笔查询，支持分页
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    @Override
    public List<SysPermissionWeightDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(SysPermissionWeightDO obj) {
        logger.debug("SysPermissionWeightDO=" + obj);
		String countSql = "select count(1) from (" + "select * from " + TABLE_NAME + getWhereSql(obj) + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total;
        try {
            total = session.account(countSql);
        } catch (Exception e) {
            logger.error("总记录数查询T_SYS_PERMISSION_WEIGHT异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询T_SYS_PERMISSION_WEIGHT失败！");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return total;
    }

    /**
     * 根据数据对象产生对应的查询SQL
     * @param obj 数据对象DO
     * @return SQL
     */
    public String getWhereSql(SysPermissionWeightDO obj) {
		return " where 1=1 " + " AND del_flg='" + DEL_FLAG_NORMAL + "' ";
    }

}