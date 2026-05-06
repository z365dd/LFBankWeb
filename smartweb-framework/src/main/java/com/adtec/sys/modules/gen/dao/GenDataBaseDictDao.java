/**
 *
 */
package com.adtec.sys.modules.gen.dao;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.gen.entity.GenTable;
import com.adtec.sys.modules.gen.entity.GenTableColumn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务表字段DAO接口
 *
 * @version 2013-10-15
 */
@Component
public class GenDataBaseDictDao {
    private final static Logger logger = LoggerFactory.getLogger(GenDataBaseDictDao.class);

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
    public List<GenTableColumn> findTableColumnList(GenTable genTable) {
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
            sql.append("SELECT t.COLUMN_NAME AS name, ")
                    .append("(CASE WHEN t.NULLABLE = 'Y' THEN '1' ELSE '0' END) AS null_flg, ")
                    .append("(t.COLUMN_ID * 10) AS sort, ")
                    .append("c.COMMENTS AS tab_desc, ")
                    .append("decode(t.DATA_TYPE,'DATE',t.DATA_TYPE || '(' || t.DATA_LENGTH || ')', ")
                    .append("'VARCHAR2', t.DATA_TYPE || '(' || t.DATA_LENGTH || ')', ")
                    .append("'VARCHAR', t.DATA_TYPE || '(' || t.DATA_LENGTH || ')', ")
                    .append("'NVARCHAR2', t.DATA_TYPE || '(' || t.DATA_LENGTH/2 || ')', ")
                    .append("'CHAR', t.DATA_TYPE || '(' || t.DATA_LENGTH || ')', ")
                    .append("'NUMBER',t.DATA_TYPE || (nvl2(t.DATA_PRECISION,nvl2(decode(t.DATA_SCALE,0,null,t.DATA_SCALE), ")
                    .append("'(' || t.DATA_PRECISION || ',' || t.DATA_SCALE || ')',  ")
                    .append("'(' || t.DATA_PRECISION || ')'),'(18)')),t.DATA_TYPE) AS db_field_tp  ")
                    .append("FROM user_tab_columns t, user_col_comments c  ")
                    .append("WHERE t.TABLE_NAME = c.table_name  AND t.COLUMN_NAME = c.column_name ");
            if (genTable.getName() != null && genTable.getName() != "") {
                sql.append("AND t.TABLE_NAME = ? ");
                parameters.add(genTable.getName().toUpperCase());
            }
            sql.append("ORDER BY t.COLUMN_ID");
        } else if ("mysql".equals(session.getDbtype())) {
            sql.append("SELECT t.COLUMN_NAME AS name, (CASE WHEN t.IS_NULLABLE = 'YES' THEN '1' ELSE '0' END) AS null_flg, ")
                    .append("(t.ORDINAL_POSITION * 10) AS sort,t.COLUMN_COMMENT AS tab_desc,t.COLUMN_TYPE AS db_field_tp ")
                    .append("FROM information_schema.`COLUMNS` t ")
                    .append("WHERE t.TABLE_SCHEMA = (select database()) ");
            if (!DataUtil.isNullStr(genTable.getName())) {
                sql.append("AND t.TABLE_NAME = ? ");
                parameters.add(genTable.getName().toUpperCase());
            }
            sql.append("ORDER BY t.ORDINAL_POSITION");
        }
        List<GenTableColumn> list;
        try {
        	list = session.getObjectListByList(sql.toString(), GenTableColumn.class, parameters);
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return list;
    }

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
    public List<GenTable> findTableList(GenTable genTable) {
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
            sql.append("SELECT t.TABLE_NAME AS name, c.COMMENTS AS tab_desc ")
                    .append("FROM user_tables t, user_tab_comments c WHERE t.table_name = c.table_name ");
            if (!DataUtil.isNullStr(genTable.getName())) {
                sql.append("AND t.TABLE_NAME like ? ");
                parameters.add("%"+genTable.getName().toUpperCase()+"%");
            }
            sql.append("ORDER BY t.TABLE_NAME");
        } else if ("mysql".equals(session.getDbtype())) {
            sql.append("SELECT t.table_name AS name,t.TABLE_COMMENT AS tab_desc ")
                    .append("FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = (select database()) ");
            if (!DataUtil.isNullStr(genTable.getName())) {
                sql.append("AND t.TABLE_NAME like ? ");
                parameters.add("%"+genTable.getName().toUpperCase()+"%");
            }
            sql.append("ORDER BY t.TABLE_NAME");
        }
        List<GenTable> list;
        try {
            list = session.getObjectListByList(sql.toString(), GenTable.class, parameters);
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }
        return list;
    }

	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
    public List<String> findTablePK(GenTable genTable) {
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
            sql.append("SELECT lower(cu.COLUMN_NAME) AS columnName ")
                    .append("FROM user_cons_columns cu, user_constraints au ")
                    .append("WHERE cu.constraint_name = au.constraint_name ")
                    .append("AND au.constraint_type = 'P' ");
            if (!DataUtil.isNullStr(genTable.getName())) {
                sql.append("AND au.table_name = ? ");
                parameters.add(genTable.getName().toUpperCase());
            }
        } else if ("mysql".equals(session.getDbtype())) {
            sql.append("SELECT lower(au.COLUMN_NAME) AS columnName  ")
                    .append("FROM information_schema.`COLUMNS` au ")
                    .append("WHERE au.TABLE_SCHEMA = (select database())  ")
                    .append("AND au.COLUMN_KEY='PRI' ");
            if (!DataUtil.isNullStr(genTable.getName())) {
                sql.append("AND au.TABLE_NAME = ? ");
                parameters.add(genTable.getName().toUpperCase());
            }
        }
        List<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), parameters);
            if(null == rs ){
                throw new  BaseException(SysErr.E_NULL_POINTER, "查询失败！");
            }
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            logger.error("查询数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询失败！");
        }finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                System.out.println("sql异常");
            }
        }
        return list;
    }
}
