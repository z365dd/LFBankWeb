/**
 *
 */
package com.adtec.sys.modules.gen.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.gen.entity.GenTable;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_DELETE;
import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.util.List;

/**
 * 业务表DAO接口
 *
 * @version 2013-10-15
 */
@Component
public class GenTableDao {
    private final static Logger logger = LoggerFactory.getLogger(GenTableDao.class);

    public int delete(GenTable genTable) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("delete from T_SYS_GEN_TABLE WHERE id = ? ");
        try {
            i = session.execute(sql.toString(), genTable.getId());
        } catch (Exception e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public List<GenTable> findAllList(GenTable genTable) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_GEN_TABLE a ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        if (genTable.getPage() != null && !DataUtil.isNullStr(genTable.getPage().getOrderBy())) {
            sql.append("OORDER BY ").append(genTable.getPage().getOrderBy());
        } else {
            sql.append("ORDER BY a.name ASC");
        }
        List<GenTable> list;
        try {
            list = session.getObjectList(sql.toString(), GenTable.class);
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return list;
    }

    public List<GenTable> findList(GenTable genTable) {
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_GEN_TABLE a ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        if (!DataUtil.isNullStr(genTable.getName())) {
            sql.append("AND a.name = ? ");
            parameters.add(genTable.getName());
        }
        if (!DataUtil.isNullStr(genTable.getNameLike())) {
            if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                sql.append("AND a.name LIKE ? ");
                parameters.add("%"+genTable.getNameLike()+"%");
            } else if ("mysql".equals(session.getDbtype())) {
                sql.append("AND a.name LIKE ? ");
                parameters.add("%"+genTable.getNameLike()+"%");
            }
        }
        if (!DataUtil.isNullStr(genTable.getTabDesc())) {
            if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                sql.append("AND a.tab_desc LIKE  ? ");
                parameters.add("%"+genTable.getTabDesc()+"%");
            } else if ("mysql".equals(session.getDbtype())) {
                sql.append("AND a.tab_desc LIKE  ? ");
                parameters.add("%"+genTable.getTabDesc()+"%");
            }
        }
        if (!DataUtil.isNullStr(genTable.getParentTabName())) {
            sql.append("AND a.parent_tab_name = ? ");
            parameters.add(genTable.getParentTabName());
        }
        if (genTable.getPage() != null && !DataUtil.isNullStr(genTable.getPage().getOrderBy())) {
            sql.append("ORDER BY ").append(genTable.getPage().getOrderBy());
        } else {
            sql.append("ORDER BY a.name ASC");
        }
        List<GenTable> list;
        try {
        	int start = 0;
        	int limit = 0;
        	if(null!=genTable && null!=genTable.getPage()){
        		/* 20200207 mod by chenyl for 每页记录数大于0时才执行分页 */
        		if(genTable.getPage().getPageSize()>0){
        			limit = genTable.getPage().getPageSize();
        		}
        		start = limit * (genTable.getPage().getPageNo() -1) + 1;
        		// 获取总记录数
        		String countSql = "select count(1) from (" + sql.toString() + ") ct";
        		int total = session.accountByList(countSql, parameters);
        		genTable.getPage().setCount(Long.parseLong(""+total));
        	}
        	
            list = session.getObjectListByListForPage(sql.toString(), GenTable.class, start, limit, parameters);
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return list;
    }

    public GenTable get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_GEN_TABLE a ")
                .append("WHERE a.id = ? ");
        GenTable genTable;
        try {
            genTable = session.getObject(sql.toString(), GenTable.class, id);
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return genTable;
    }

    public int insert(GenTable genTable) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_GEN_TABLE( ")
                .append("id,  ")
                .append("name,  ")
                .append("tab_desc,  ")
                .append("proc_clss_name,  ")
                .append("parent_tab_name,  ")
                .append("parent_tab_out_key,  ")
                .append("crtr,  ")
                .append("crt_time,  ")
                .append("uptr,  ")
                .append("upt_time,  ")
                .append("rmrk,  ")
                .append("del_flg ")
                .append(") VALUES ( ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ");
        parameters.add(genTable.getId());
        parameters.add(genTable.getName());
        parameters.add(genTable.getTabDesc());
        parameters.add(genTable.getProcClssName());
        parameters.add(genTable.getParentTabName());
        parameters.add(genTable.getParentTabOutKey());
        parameters.add(genTable.getCrtr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genTable.getCrtTime());
        } else {
            sql.append("?, ");
            parameters.add(genTable.getCrtTime());
        }
        sql.append("?, ");
        parameters.add(genTable.getUptr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genTable.getUptTime());
        } else {
            sql.append("?, ");
            parameters.add(genTable.getUptTime());
        }
        sql.append("?, ?) ");
        parameters.add(genTable.getRmrk());
        parameters.add(genTable.getDelFlg());
        
        try {
            i = session.executeByList(sql.toString(), parameters);
        } catch (Exception e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int update(GenTable genTable) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_GEN_TABLE SET  ")
                .append("tab_desc = ?, ")
                .append("proc_clss_name = ?, ")
                .append("parent_tab_name = ?, ")
                .append("parent_tab_out_key = ?, ")
                .append("uptr = ?, ");
        parameters.add(genTable.getTabDesc());
        parameters.add(genTable.getProcClssName());
        parameters.add(genTable.getParentTabName());
        parameters.add(genTable.getParentTabOutKey());
        parameters.add(genTable.getUptr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time = to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genTable.getUptTime());
        } else {
            sql.append("upt_time = ?, ");
            parameters.add(genTable.getUptTime());
        }
        sql.append("rmrk = ? ")
                .append("WHERE id = ? ");
        parameters.add(genTable.getRmrk());
        parameters.add(genTable.getId());
        
        try {
            i = session.executeByList(sql.toString(), parameters);
        } catch (Exception e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }
}
