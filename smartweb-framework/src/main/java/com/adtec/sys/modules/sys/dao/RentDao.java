package com.adtec.sys.modules.sys.dao;


import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.adtec.framework.impl.dbspring.session.DBSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.StoreEnv;
import com.adtec.sys.modules.sys.entity.UserAcctDO;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.google.common.collect.Lists;


/**
 * 租户DAO接口
 *
 * @author chenyl
 * @version 20170425
 */
@Component
public class RentDao {
    private final static Logger logger = LoggerFactory.getLogger(RentDao.class);

    private final static String TABLE_NAME = "T_SYS_RENT";
    /**
     * 删除文件信息
     * @param id
     */
    private void delFile(String id){
        IDBSession session = DBSessionFactory.getSession();
        try {
            String sql = "DELETE FROM t_sys_file where id=?";
            session.execute(sql, id);
        } catch (Exception e) {
            throw new BaseException(SysErr.E_MESSAGE, "删除文件异常");
        }
    }

    public int delete(Rent rent) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_RENT WHERE id=? OR parent_id_list LIKE ?";
        Object[] params = {rent.getId(), "%," + rent.getId() + ",%"};
        try {
        	session.beginTransaction();
        	//删除背景图和描述文件
        	if(rent !=null && !DataUtil.isNullStr(rent.getBgImg())){
        		delFile(rent.getBgImg());
        	}
        	if(rent !=null && !DataUtil.isNullStr(rent.getTntDesc())){
        		delFile(rent.getTntDesc());
        		FileUtil.deleteFile(rent.getId()+".txt");
        	}
            i = session.execute(sql, params);
            session.endTransaction();
        } catch (SQLException e) {
            logger.error("更新数据失败：" , e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<Rent> findAllList() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columnName).append(", a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_RENT a ")
                .append("LEFT JOIN T_SYS_RENT p ON p.id = a.parent_id ")
                .append("WHERE a.del_flg=? ")
                .append("ORDER BY a.name");
        List<Rent> list = Lists.newArrayList();
        Object[] params = {DEL_FLAG_NORMAL};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Rent rent1 = new Rent();
                Rent parent = new Rent();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(rent1, fieldName, rs, i);
                    }
                }
                rent1.setParent(parent);
                list.add(rent1);
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

    public List<Rent> findByParentIdsLike(Rent rent) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columnName).append(", a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_RENT a ")
                .append("LEFT JOIN T_SYS_RENT p ON p.id = a.parent_id ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ")
                .append("AND (a.parent_id_list LIKE ? OR a.id=?)");
        List<Rent> list = Lists.newArrayList();
        Object[] params = {"%," + rent.getId() + ",%", rent.getId()};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                Rent rent1 = new Rent();
                Rent parent = new Rent();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(rent1, fieldName, rs, i);
                    }
                }
                rent1.setParent(parent);
                list.add(rent1);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" , e);
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
     * 获取当前租户的子租户列表
     *
     * @param rent
     * @return
     */
    public List<Rent> findChildRentList(Rent rent) {
        List<Rent> rentList = Lists.newArrayList();
        if (null != rent && !DataUtil.isNullStr(rent.getId())) {
            IDBSession session = DBSessionFactory.getSession();
            StringBuilder sql = new StringBuilder();
            /*20190930 mod by chenyl for 修改根据租户英文名称获取租户信息*/
            sql.append("SELECT ").append(columnName).append(" from ").append(TABLE_NAME).append(" a where a.parent_id=? and a.del_flg=?");
            Object[] params = {rent.getId(), DEL_FLAG_NORMAL};
            try {
                rentList = session.getObjectList(sql.toString(), Rent.class, params);
            } catch (Exception e) {
                logger.error("查询数据失败：" , e);
                throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
            }
        } else {
            logger.warn("查询参数：租户ID为空，不再获取子租户列表");
        }
        return rentList;
    }

    /**
     * 获取当前租户的所有下级租户列表（一直到叶子节点）
     *
     * @param rent
     * @return
     */
    public List<Rent> findChildsRentList(Rent rent) {
        List<Rent> rentList = Lists.newArrayList();
        if (null != rent && !DataUtil.isNullStr(rent.getId())) {
            IDBSession session = DBSessionFactory.getSession();
            StringBuilder sql = new StringBuilder();
            /*20190930 mod by chenyl for 修改根据租户英文名称获取租户信息*/
            sql.append("SELECT ").append(columnName).append(" from ").append(TABLE_NAME).append(" a where a.parent_id_list like ? and a.del_flg=?");
            Object[] params = {"%" + rent.getId() + ",", DEL_FLAG_NORMAL};
            try {
                rentList = session.getObjectList(sql.toString(), Rent.class, params);
            } catch (Exception e) {
                logger.error("查询数据失败：", e);
                throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
            }
        } else {
            logger.warn("查询参数：租户ID为空，不再获取子租户列表");
        }
        return rentList;
    }

    public List<Rent> findList(Rent rent) {
        List<Object> filterParams = Lists.newArrayList();
        return findList(rent, filterParams);
    }

    public List<Rent> findList(Rent rent, List<Object> filterParams) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columnName).append(", a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_RENT a ")
                .append("LEFT JOIN T_SYS_RENT p ON p.id=a.parent_id ")
                .append("WHERE a.del_flg=? ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        if (rent.getSqlMap().get("dsf") != null) {
            sql.append(rent.getSqlMap().get("dsf")).append(" ");
            params.addAll(filterParams);
        }
        sql.append("ORDER BY a.name");
        List<Rent> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Rent rent1 = new Rent();
                Rent parent = new Rent();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(rent1, fieldName, rs, i);
                    }
                }
                rent1.setParent(parent);
                list.add(rent1);
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

    public Rent findParentRentById(String tntId) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = "select "+columnName+" from T_SYS_RENT a where a.id=(select o.parent_id from T_SYS_RENT o where o.id=?)";
        Rent rent;
        try {
            rent = session.getObject(sql, Rent.class, tntId);
        } catch (SQLException e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return rent;
    }

    public Rent get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columnName).append(", a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_RENT a ")
                .append("LEFT JOIN T_SYS_RENT p ON p.id=a.parent_id ")
                .append("WHERE a.id=?");
        Rent rent = new Rent();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), id);
            while (rs.next()) {
                Rent parent = new Rent();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(rent, fieldName, rs, i);
                    }
                }
                rent.setParent(parent);
            }
        } catch (Exception e) {
            logger.error("查询数据失败：" , e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return rent;
    }

    public Rent get(Rent rent) {
        return get(rent.getId());
    }

    public Rent getByEngName(Rent rent) {
        if (!(null != rent && !DataUtil.isNullStr(rent.getEngName()))) {
            throw new BaseException(SysErr.E_MESSAGE, "查询参数：租户英文名不能空");
        }
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        Rent qryRent = new Rent();
        /*20190930 mod by chenyl for 修改根据租户英文名称获取租户信息*/
        sql.append("SELECT ").append(columnName).append(" from ").append(TABLE_NAME).append(" a where a.eng_name=? and a.del_flg=?");
        try {
            qryRent = session.getObject(sql.toString(), Rent.class, rent.getEngName(), DEL_FLAG_NORMAL);
            if (null != qryRent) {
                // 获取父节点租户信息
                sql.setLength(0);
                sql.append("SELECT ").append(columnName).append(" from ").append(TABLE_NAME).append(" a where a.id=? and a.del_flg=?");
                Rent parentRent = session.getObject(sql.toString(), Rent.class, qryRent.getParentId(), DEL_FLAG_NORMAL);
                if (null != parentRent) {
                    qryRent.setParent(parentRent);
                }
            }
        } catch (Exception e) {
            logger.error("查询数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return qryRent;
    }

    public Rent getByName(Rent rent) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columnName).append(", a.parent_id AS \"parent.id\", p.name AS \"parent.name\" ")
                .append("FROM T_SYS_RENT a ")
                .append("LEFT JOIN T_SYS_RENT p ON p.id=a.parent_id ")
                .append("WHERE a.name=? AND a.del_flg=?");
        Rent rent1 = null;
        Object[] params = {rent.getName(), DEL_FLAG_NORMAL};
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), params);
            while (rs.next()) {
                rent1 = new Rent();
                Rent parent = new Rent();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("parent.")) {
                        session.setProperty(parent, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(rent1, fieldName, rs, i);
                    }
                }
                rent1.setParent(parent);
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
        return rent1;
    }

    public List<String> getRentsByTntId(Rent rent) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM T_SYS_RENT WHERE del_flg=? ");
        List<Object> params = Lists.newArrayList();
        params.add(DEL_FLAG_NORMAL);
        if (StringUtil.isNotBlank(rent.getId())) {
            sql.append("AND (id=? OR parent_id_list LIKE ?)");
            params.add(rent.getId());
            params.add("%," + rent.getId() + ",%");
        }
        List<String> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
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

    public int insert(Rent rent) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_RENT(id, name, eng_name, url, parent_id, parent_id_list, open_stat, one_extra_rmrk, two_extra_rmrk, crtr, crt_time, uptr, upt_time, rmrk, del_flg, bg_img, tnt_desc")
                .append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?)");
        } else {
            sql.append("?, ?, ?, ?, ?, ?, ?)");
        }
        Object[] params = {
                rent.getId(), rent.getName(), rent.getEngName(), rent.getUrl(), rent.getParentId(),
                rent.getParentIdList(), rent.getStat(), rent.getOneExtraRmrk(), rent.getTwoExtraRmrk(), rent.getCrtr(),
                rent.getCrtTime(), rent.getUptr(), rent.getUptTime(), rent.getRmrk(), rent.getDelFlg(),rent.getBgImg(),rent.getTntDesc()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (SQLException e) {
            logger.error("新增失败！", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增失败！" + e.getMessage());
        }
        return i;
    }

    public int update(Rent rent) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_RENT SET parent_id=?, parent_id_list=?, name=?, eng_name=?, url=?, open_stat=?, one_extra_rmrk=?, two_extra_rmrk=?, uptr=?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time=to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
        } else {
            sql.append("upt_time=?, ");
        }
        sql.append("rmrk=?,bg_img=?,tnt_desc=? WHERE id=?");
        Object[] params = {
                rent.getParentId(), rent.getParentIdList(), rent.getName(), rent.getEngName(), rent.getUrl(),
                rent.getStat(), rent.getOneExtraRmrk(), rent.getTwoExtraRmrk(), rent.getUptr(), rent.getUptTime(),
                rent.getRmrk(), rent.getBgImg(),rent.getTntDesc(),rent.getId()
        };
        try {
            i = session.execute(sql.toString(), params);
            updateStat(rent);
        } catch (SQLException e) {
            logger.error("新增失败！", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增失败！" + e.getMessage());
        }
        return i;
    }

    public int updateParentIds(Rent rent) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "UPDATE T_SYS_RENT SET parent_id=?, parent_id_list=? WHERE id=?";
        Object[] params = {rent.getParentId(), rent.getParentIdList(), rent.getId()};
        try {
            i = session.execute(sql, params);
        } catch (SQLException e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public void updateStat(Rent rent) {
        IDBSession session = DBSessionFactory.getSession();
        try {
            Object[] params = {rent.getStat(), rent.getParentIdList()+rent.getId()+"%"};
            session.execute("update t_sys_rent set open_stat=? where parent_id_list like ? ", params);
        } catch (SQLException e) {
            logger.error("更新状态失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新状态失败！");
        }
    }
    
    public void syncStoreEnv(Rent rent){
        try {
            /* 检查软件仓库是否存在 */
            Class<?> envDO = Class.forName("com.adtec.store.env.entity.EnvDO");
            if (null != envDO) {
            	int count = getEnvTotal(rent.getEngName());
            	if(count > 0){
            		uptStoreEnv(rent.getName(),rent.getEngName());
            	}else{
            		StoreEnv env = new StoreEnv();
            		env.preInsert();
            		env.setChName(rent.getName());
            		env.setEngName(rent.getEngName());
            		istStoreEnv(env);
            	}
            }
        } catch (Exception e) {
        	logger.error("租户同步软件仓库环境表失败："+e.getMessage());
        }
    }
    
	public int getEnvTotal(String engName) {
		logger.debug("getEngTotal=" + engName);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from t_store_env t where t.eng_name=? ");
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        parameters.add(engName);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	logger.error("总记录数查询t_store_env异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_store_env失败！");
        }
        return total;
	}
	
    public void istStoreEnv(StoreEnv env){
        List<String> ignoreFields = env.getIgnoreFields();
        try {
        	IDBSession session = DBSessionFactory.getSession(); 
        	session.saveObject("t_store_env", env, ignoreFields);
		} catch (Exception e) {
        	logger.error("租户同步软件仓库环境表异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "租户同步软件仓库环境表失败！");
		}
    }
    
    public void uptStoreEnv(String chName,String engName){
        String sql = "update t_store_env t set t.ch_name=? where t.eng_name=?";
        Object[] params = {chName, engName};
        try {
        	IDBSession session = DBSessionFactory.getSession(); 
            session.execute(sql, params);
        } catch (SQLException e) {
            logger.error("更新数据失败：", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
    }

    private static final String columnName = "a.ID,a.NAME,a.ENG_NAME,a.URL,a.OPEN_STAT AS STAT,a.ONE_EXTRA_RMRK,a.TWO_EXTRA_RMRK,a.CRTR,a.CRT_TIME,a.UPTR,a.UPT_TIME,a.RMRK,a.DEL_FLG,a.PARENT_ID,a.PARENT_ID_LIST,a.BG_IMG,a.TNT_DESC";
}
