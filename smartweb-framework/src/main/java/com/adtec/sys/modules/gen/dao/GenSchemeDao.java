/**
 *
 */
package com.adtec.sys.modules.gen.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.gen.entity.GenScheme;
import com.adtec.sys.modules.gen.entity.GenTable;
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
 * 生成方案DAO接口
 *
 * @version 2013-10-15
 */
@Component
public class GenSchemeDao {
    private final static Logger logger = LoggerFactory.getLogger(GenSchemeDao.class);

    public int delete(GenScheme genScheme) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        StringBuilder sql = new StringBuilder();
        sql.append("delete from T_SYS_GEN_SCHEME WHERE id = ? ");
        try {
            i = session.execute(sql.toString(), genScheme.getId());
        } catch (SQLException e) {
            logger.error("删除失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "删除失败！");
        }
        return i;
    }

    public List<GenScheme> findList(GenScheme genScheme) {
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* ")
                .append("FROM T_SYS_GEN_SCHEME a ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        if (!DataUtil.isNullStr(genScheme.getName())) {
            if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                sql.append("AND a.name LIKE  ? ");
                parameters.add("%"+genScheme.getName()+"%");
            } else if ("mysql".equals(session.getDbtype())) {
                sql.append("AND a.name LIKE  ? ");
                parameters.add("%"+genScheme.getName()+"%");
            }
        }
        sql.append("ORDER BY a.upt_time DESC");

        List<GenScheme> list = null;
        try {
        	int start = 0;
        	int limit = 0;
        	if(null!=genScheme && null!=genScheme.getPage()){
        		/* 20200207 mod by chenyl for 每页记录数大于0时才执行分页 */
        		if(genScheme.getPage().getPageSize()>0){
        			limit = genScheme.getPage().getPageSize();
        		}
        		start = limit * (genScheme.getPage().getPageNo() -1) + 1;
        		// 获取总记录数
        		String countSql = "select count(1) from (" + sql.toString() + ") ct";
        		int total = session.accountByList(countSql, parameters);
        		genScheme.getPage().setCount(Long.parseLong(""+total));
        	}
        	list = session.getObjectListByListForPage(sql.toString(), GenScheme.class, start, limit, parameters);
        	if(null!=list && !list.isEmpty()){
        		for(int i=0;i<list.size();i++){
        			GenTable genTable = new GenTable();
        			genTable.setId(list.get(i).getTabId());
        			list.get(i).setGenTable(genTable);
        		}
        	}else{
        		list = new ArrayList<GenScheme>();
        	}
        } catch (Exception e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return list;
    }

    public GenScheme get(String id) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        sql.append("select a.*, a.tab_id AS \"genTable.id\" ")
                .append("FROM T_SYS_GEN_SCHEME a WHERE a.id = ? ");
        GenScheme genScheme = new GenScheme();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), id);

            while (rs.next()) {
                GenTable genTable = new GenTable();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("gentable.")) {
                        session.setProperty(genTable, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(genScheme, fieldName, rs, i);
                    }
                }
                genScheme.setGenTable(genTable);
            }
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
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
        return genScheme;
    }

    public int insert(GenScheme genScheme) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_GEN_SCHEME( ")
                .append("id, ")
                .append("name, ")
                .append("clss_code, ")
                .append("pack_name, ")
                .append("modl_name, ")
                .append("sub_modl_name, ")
                .append("func_name, ")
                .append("func_name_abbr, ")
                .append("func_crtr, ")
                .append("tab_id, ")
                .append("crtr, ")
                .append("crt_time, ")
                .append("uptr, ")
                .append("upt_time, ")
                .append("rmrk, ")
                .append("del_flg ")
                .append(") VALUES ( ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ")
                .append("?, ");
        parameters.add(genScheme.getId());
        parameters.add(genScheme.getName());
        parameters.add(genScheme.getClssCode());
        parameters.add(genScheme.getPackName());
        parameters.add(genScheme.getModlName());
        parameters.add(genScheme.getSubModlName());
        parameters.add(genScheme.getFuncName());
        parameters.add(genScheme.getFuncNameAbbr());
        parameters.add(genScheme.getFuncCrtr());
        parameters.add(genScheme.getGenTable().getId());
        parameters.add(genScheme.getCrtr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genScheme.getCrtTime());
        } else {
            sql.append("?, ");
            parameters.add(genScheme.getCrtTime());
        }
        sql.append("?, ");
        parameters.add(genScheme.getUptr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genScheme.getUptTime());
        } else {
            sql.append("?, ");
            parameters.add(genScheme.getUptTime());
        }
        sql.append("?, ?)");
        parameters.add(genScheme.getRmrk());
        parameters.add(genScheme.getDelFlg());

        try {
            i = session.executeByList(sql.toString(), parameters);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int update(GenScheme genScheme) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_GEN_SCHEME SET ")
                .append("name = ?, ")
                .append("clss_code = ?, ")
                .append("pack_name = ?, ")
                .append("modl_name = ?, ")
                .append("sub_modl_name = ?, ")
                .append("func_name = ?, ")
                .append("func_name_abbr = ?, ")
                .append("func_crtr = ?, ")
                .append("tab_id = ?, ")
                .append("uptr = ?, ");
        parameters.add(genScheme.getName());
        parameters.add(genScheme.getClssCode());
        parameters.add(genScheme.getPackName());
        parameters.add(genScheme.getModlName());
        parameters.add(genScheme.getSubModlName());
        parameters.add(genScheme.getFuncName());
        parameters.add(genScheme.getFuncNameAbbr());
        parameters.add(genScheme.getFuncCrtr());
        parameters.add(genScheme.getGenTable().getId());
        parameters.add(genScheme.getUptr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time = to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genScheme.getUptTime());
        } else {
            sql.append("upt_time = ?, ");
            parameters.add(genScheme.getUptTime());
        }
        sql.append("rmrk = ? ")
                .append("WHERE id = ? ");
        parameters.add(genScheme.getRmrk());
        parameters.add(genScheme.getId());
        
        try {
            i = session.executeByList(sql.toString(), parameters);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

}
