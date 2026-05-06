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
import com.adtec.sys.modules.gen.entity.GenTableColumn;
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
 * 业务表字段DAO接口
 *
 * @version 2013-10-15
 */
@Component
public class GenTableColumnDao {
    private final static Logger logger = LoggerFactory.getLogger(GenTableColumnDao.class);

    public void deleteByGenTableId(GenTable genTable) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("delete from T_SYS_GEN_TABLE_COLUMN WHERE tab_id = ? ");
        try {
            session.execute(sql.toString(), genTable.getId());
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
    }

    public List<GenTableColumn> findList(GenTableColumn genTableColumn) {
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, ")
                .append("b.id AS \"genTable.id\", ")
                .append("b.name AS \"genTable.name\", ")
                .append("b.tab_desc AS \"genTable.tab_Desc\", ")
                .append("b.proc_clss_name AS \"genTable.proc_Clss_Name\", ")
                .append("b.parent_tab_name AS \"genTable.parent_Tab_Name\", ")
                .append("b.parent_tab_out_key AS \"genTable.parent_Tab_Out_Key\" ")
                .append("FROM T_SYS_GEN_TABLE_COLUMN a ")
                .append("JOIN T_SYS_GEN_TABLE b ON b.id = a.tab_id ")
                .append("WHERE a.del_flg = '").append(DEL_FLAG_NORMAL).append("' ");
        if (!DataUtil.isNullStr(genTableColumn.getName())) {
            if ("oracle".equals(session.getDbtype()) || "db2".equals(session.getDbtype()) || "kingbase8".equals(session.getDbtype())) {
                sql.append("AND a.name LIKE ? ");
                parameters.add("%"+genTableColumn.getName()+"%");
            } else if ("mysql".equals(session.getDbtype())) {
                sql.append("AND a.name LIKE ? ");
                parameters.add("%"+genTableColumn.getName()+"%");
            }
        }
        if (genTableColumn.getGenTable() != null && !DataUtil.isNullStr(genTableColumn.getGenTable().getId())) {
            sql.append("AND a.tab_id = ? ");
            parameters.add(genTableColumn.getGenTable().getId());
        }
        sql.append("ORDER BY a.sort ASC");

        List<GenTableColumn> list = new ArrayList<GenTableColumn>();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), parameters);
            while (rs.next()) {
                GenTableColumn genTableColumn1 = new GenTableColumn();
                GenTable genTable = new GenTable();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if (fieldName.startsWith("gentable.")) {
                        session.setProperty(genTable, fieldName.substring(fieldName.lastIndexOf(".") + 1), rs, i);
                    } else {
                        session.setProperty(genTableColumn1, fieldName, rs, i);
                    }
                }
                genTableColumn1.setGenTable(genTable);
                list.add(genTableColumn1);
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
        return list;
    }

    public int insert(GenTableColumn genTableColumn) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_GEN_TABLE_COLUMN( ")
                .append("id,  ")
                .append("tab_id,  ")
                .append("name,  ")
                .append("tab_desc,  ")
                .append("db_field_tp,  ")
                .append("java_tp,  ")
                .append("java_field,  ")
                .append("main_key_flg,  ")
                .append("null_flg,  ")
                .append("insert_flg,  ")
                .append("edit_flg,  ")
                .append("list_flg,  ")
                .append("qry_flg,  ")
                .append("qry_tp,  ")
                .append("dpy_tp,  ")
                .append("dict_tp,  ")
                .append("sort,  ")
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
                .append("?, ")
                .append("?, ");
        parameters.add(genTableColumn.getId());
        parameters.add(genTableColumn.getGenTable().getId());
        parameters.add(genTableColumn.getName());
        parameters.add(genTableColumn.getTabDesc());
        parameters.add(genTableColumn.getDbFieldTp());
        parameters.add(genTableColumn.getJavaTp());
        parameters.add(genTableColumn.getJavaField());
        parameters.add(genTableColumn.getMainKeyFlg());
        parameters.add(genTableColumn.getNullFlg());
        parameters.add(genTableColumn.getInsertFlg());
        parameters.add(genTableColumn.getEditFlg());
        parameters.add(genTableColumn.getListFlg());
        parameters.add(genTableColumn.getQryFlg());
        parameters.add(genTableColumn.getQryTp());
        parameters.add(genTableColumn.getDpyTp());
        parameters.add(genTableColumn.getDictTp());
        parameters.add(genTableColumn.getSort());
        parameters.add(genTableColumn.getCrtr());
        
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genTableColumn.getCrtTime());
        } else {
            sql.append("?, ");
            parameters.add(genTableColumn.getCrtTime());
        }
        sql.append("?, ");
        parameters.add(genTableColumn.getUptr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genTableColumn.getUptTime());
        } else {
            sql.append("?, ");
            parameters.add(genTableColumn.getUptTime());
        }
        sql.append("?, ")
                .append("?) ");
        parameters.add(genTableColumn.getRmrk());
        parameters.add(genTableColumn.getDelFlg());
        try {
            i = session.executeByList(sql.toString(), parameters);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }

    public int update(GenTableColumn genTableColumn) {
        int i = 0;
        IDBSession session = DBSessionFactory.getSession();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_SYS_GEN_TABLE_COLUMN SET ")
                .append("tab_desc = ?, ")
                .append("db_field_tp = ?, ")
                .append("java_tp = ?, ")
                .append("java_field = ?, ")
                .append("main_key_flg = ?, ")
                .append("null_flg = ?, ")
                .append("insert_flg = ?, ")
                .append("edit_flg = ?, ")
                .append("list_flg = ?, ")
                .append("qry_flg = ?, ")
                .append("qry_tp = ?, ")
                .append("dpy_tp = ?, ")
                .append("dict_tp = ?, ")
                .append("sort = ?, ")
                .append("uptr = ?, ");
        parameters.add(genTableColumn.getTabDesc());
        parameters.add(genTableColumn.getDbFieldTp());
        parameters.add(genTableColumn.getJavaTp());
        parameters.add(genTableColumn.getJavaField());
        parameters.add(genTableColumn.getMainKeyFlg());
        parameters.add(genTableColumn.getNullFlg());
        parameters.add(genTableColumn.getInsertFlg());
        parameters.add(genTableColumn.getEditFlg());
        parameters.add(genTableColumn.getListFlg());
        parameters.add(genTableColumn.getQryFlg());
        parameters.add(genTableColumn.getQryTp());
        parameters.add(genTableColumn.getDpyTp());
        parameters.add(genTableColumn.getDictTp());
        parameters.add(genTableColumn.getSort());
        parameters.add(genTableColumn.getUptr());
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("upt_time = to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ");
            parameters.add(genTableColumn.getUptTime());
        } else {
            sql.append("upt_time = ?, ");
            parameters.add(genTableColumn.getUptTime());
        }
        sql.append("rmrk = ?, ")
                .append("del_flg = ? ")
                .append("WHERE id = ? ");
        parameters.add(genTableColumn.getRmrk());
        parameters.add(genTableColumn.getDelFlg());
        parameters.add(genTableColumn.getId());
        
        try {
            i = session.executeByList(sql.toString(), parameters);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }
}
