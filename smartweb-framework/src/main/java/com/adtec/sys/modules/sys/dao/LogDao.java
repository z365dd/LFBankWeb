package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.Log;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.adtec.sys.modules.sys.entity.Log.TYPE_EXCEPTION;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 日志DAO接口
 *
 * @version 2014-05-16
 */
@Component
public class LogDao {
    private final static Logger logger = LoggerFactory.getLogger(LogDao.class);

    public List<Log> findList(final Log log) {
        List<Object> filterParams = Lists.newArrayList();
        return findList(log, filterParams);
    }

    public List<Log> findList(final Log log, List<Object> filterParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*, u.name AS create_by_name, o.name AS create_by_brch_name, r.name AS create_by_tnt_name ")
                .append("FROM T_SYS_LOG a ")
                .append("JOIN T_SYS_USER u ON u.id = a.crtr ")
                .append("JOIN T_SYS_OFFICE o ON o.id = u.brch_id ")
                .append("JOIN T_SYS_RENT r ON r.id = u.tnt_id ");
        List<Object> params = Lists.newArrayList();
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("WHERE a.crt_time BETWEEN to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss') AND to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss') ");
        } else {
            sql.append("WHERE a.crt_time BETWEEN ? AND ? ");
        }
        params.add(simpleDateFormat.format(log.getBeginDate()));
        params.add(simpleDateFormat.format(log.getEndDate()));

        if (StringUtil.isNotBlank(log.getInfoTitle())) {
            sql.append("AND a.info_title LIKE ? ");
            params.add("%"+log.getInfoTitle()+"%");
        }
        if (log.getCrtr() != null && StringUtil.isNotBlank(log.getCrtr())) {
            sql.append("AND u.USER_NO=? ");
            params.add(log.getCrtr());
        }
        if (StringUtil.isNotBlank(log.getReqUrl())) {
            sql.append("AND a.req_url LIKE ? ");
            params.add("%"+log.getReqUrl()+"%");
        }
        if (log.getJavaExctByteData() != null) {
            sql.append("AND a.log_tp=? ");
            params.add(TYPE_EXCEPTION);
        }
        if (!DataUtil.isNullStr(log.getSeq())) {
            sql.append("AND a.seq like ? ");
            params.add("%"+log.getSeq()+"%");
        }
        if (log.getSqlMap().get("dsf") != null) {
            sql.append(log.getSqlMap().get("dsf")).append(" ");
            params.addAll(filterParams);
        }
        sql.append("ORDER BY a.crt_time DESC");
        List<Log> list = Lists.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), params);
            while (rs.next()) {
                Log log1 = new Log();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String fieldName = ObjectTransUtils.getPropertyName(meta.getColumnLabel(i).toLowerCase());
                    if ("paraDescByteData".equals(fieldName)) {
                        log1.setParaDescByteData(rs.getBytes(meta.getColumnLabel(i)));
                    } else if ("javaExctByteData".equals(fieldName)) {
                        log1.setJavaExctByteData(rs.getBytes(meta.getColumnLabel(i)));
                    } else {
                        session.setProperty(log1, fieldName, rs, i);
                    }
                }
                list.add(log1);
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

    public int insert(final Log log) {
        int i;
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_LOG(id, log_tp, info_title, crtr, crt_time, term_ip, user_agent_msg, req_url, req_meth, para_desc_byte_data, java_exct_byte_data, seq) VALUES (")
                .append("?, ?, ?, ?, ");
        if ("kingbase8".equals(session.getDbtype())) {
            sql.append("to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?, ?, ?, ?)");
        } else {
            sql.append("?, ?, ?, ?, ?, ?, ?, ?)");
        }
        Object[] params = {
                log.getId(), log.getLogTp(), log.getInfoTitle(), log.getCrtr(), log.getCrtTime(),
                log.getTermIp(), log.getUserAgentMsg(), log.getReqUrl(), log.getReqMeth(),
                log.getParaDescByteData(), log.getJavaExctByteData(), log.getSeq()
        };
        try {
            i = session.execute(sql.toString(), params);
        } catch (Exception e) {
            logger.error("更新数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }
}
