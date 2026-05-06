package com.adtec.scheduler.dao;
/**
 * 系统名称: SmartWeb平台
 * 模块名称:自动任务日志表数据库操作类
 * 类  名  称: UleJobLogDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2019-07-31 21:56:42
 * 系统版本: V1.0.0
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.scheduler.entity.ScheduleJobLogDO;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.List;

@Component
public class ScheduleJobLogDao implements IBaseDao<ScheduleJobLogDO> {

    /* 日志对象 */
    protected final static Logger logger = LoggerFactory.getLogger(ScheduleJobLogDao.class);
    /* 表名称 */
    public static final String TABLE_NAME = "T_SYS_SCHEDULE_JOB_LOG";

    @Override
    public int insert(ScheduleJobLogDO scheduleJobLogDO) {
        scheduleJobLogDO.preInsert();
        int rs = 1;
        IDBSession session = DBSessionFactory.getNewSession();
        PreparedStatement pst = null;
        String dbtype = session.getDbtype();
        Connection conn = null;
        OutputStream os = null;
        ResultSet res = null;
        try {
            conn = session.getConnection();
            conn.setAutoCommit(false);
            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO ").append(TABLE_NAME);
            insertSql.append(
                    "(id,plat_seq,job_id,task_ins_no,ip,port,bean_name,str_time,end_time,SUCC_SWITCH_FLG,err_code,tran_time,err_msg_byte_data)");
            if ("oracle".equalsIgnoreCase(dbtype)) {
                insertSql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,EMPTY_BLOB())");
                pst = conn.prepareStatement(insertSql.toString());
                pst.setString(1, scheduleJobLogDO.getId());
                pst.setString(2, scheduleJobLogDO.getPlatSeq());
                pst.setString(3, scheduleJobLogDO.getJobId());
                pst.setString(4, scheduleJobLogDO.getTaskInsNo());
                pst.setString(5, scheduleJobLogDO.getIp());
                pst.setString(6, scheduleJobLogDO.getPort());
                pst.setString(7, scheduleJobLogDO.getBeanName());
                pst.setString(8, scheduleJobLogDO.getStrTime());
                pst.setString(9, scheduleJobLogDO.getEndTime());
                pst.setString(10, scheduleJobLogDO.getSuccSwitchFlg());
                pst.setString(11, scheduleJobLogDO.getErrCode());
                pst.setString(12, scheduleJobLogDO.getTranTime());
                pst.execute();

                String sql = "select err_msg_byte_data from T_SYS_SCHEDULE_JOB_LOG where id=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, scheduleJobLogDO.getId());
                res = pst.executeQuery();
                res.next();
                Blob errorMsgBlob = res.getBlob(1);
                os = errorMsgBlob.setBinaryStream(0);
                byte[] errorMsg = scheduleJobLogDO.getErrMsgByteData();
                ByteArrayInputStream bis = new ByteArrayInputStream(errorMsg);
                // 依次读取流字节,并输出到已定义好的数据库字段中.
                int i = 0;
                while ((i = bis.read()) != -1) {
                    os.write(i);
                }
                os.flush();
                conn.commit();
                conn.setAutoCommit(true);


            }
        } catch (Exception e) {
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new BaseException(SysErr.E_MESSAGE, "新增自动任务日志失败:" + e.getMessage());
        } finally {
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if(null != res){
                try {
                    res.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(null != conn){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public void insertLog(ScheduleJobLogDO log) {
        IDBSession session = DBSessionFactory.getNewSession();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO T_SYS_SCHEDULE_JOB_LOG(")
                .append("id,plat_seq,job_id,task_ins_no,ip,port,bean_name,str_time,end_time,SUCC_SWITCH_FLG,err_code,tran_time,err_msg_byte_data")
                .append(") VALUES ( ")
                .append("?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] params = {
                log.getId(), log.getPlatSeq(), log.getJobId(), log.getTaskInsNo(), log.getIp(), log.getPort(),
                log.getBeanName(), log.getStrTime(), log.getEndTime(), log.getSuccSwitchFlg(), log.getErrCode(), log.getTranTime(),
        };
        try {
            PreparedStatement ps = session.getConnection().prepareStatement(sql.toString());
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, String.valueOf(params[i]));
            }
            ps.setBytes(params.length + 1, log.getErrMsgByteData());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭更新自动任务数据Session异常：" + e.getMessage());
            }
        }
    }

    @Override
    public int update(ScheduleJobLogDO scheduleJobLogDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, scheduleJobLogDO, scheduleJobLogDO.getMatchFields(),
                    scheduleJobLogDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改自动任务日志表异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改自动任务日志表失败！");
        }
        return rs;
    }

    @Override
    public int delete(ScheduleJobLogDO scheduleJobLogDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id=?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), scheduleJobLogDO.getId());
        } catch (SQLException e) {
            logger.error("删除自动任务日志表异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除自动任务日志表失败！");
        }
        return rs;
    }

    public ScheduleJobLogDO get(String id) {
        ScheduleJobLogDO obj = new ScheduleJobLogDO();
        obj.setId(id);
        return get(obj);
    }

    @Override
    public ScheduleJobLogDO get(ScheduleJobLogDO scheduleJobLogDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=?");
        ScheduleJobLogDO rs = new ScheduleJobLogDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ScheduleJobLogDO.class, scheduleJobLogDO.getId());
        } catch (SQLException e) {
            logger.error("查询自动任务日志表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询自动任务日志表失败！");
        }
        return rs;
    }

    @Override
    public List<ScheduleJobLogDO> list(ScheduleJobLogDO scheduleJobLogDO) {
        return list(scheduleJobLogDO, 0, 0);
    }

    @Override
    public List<ScheduleJobLogDO> list(ScheduleJobLogDO scheduleJobLogDO, int start, int limit) {
        logger.debug("scheduleJobLogDO=" + scheduleJobLogDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> params = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(scheduleJobLogDO, params));
        sql.append(" order by str_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<ScheduleJobLogDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ScheduleJobLogDO.class, params);
            } else {
                list = session.getObjectListForPage(sql.toString(), ScheduleJobLogDO.class, start, limit);
            }
        } catch (SQLException e) {
            logger.error("列表查询自动任务日志表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询自动任务日志表失败！");
        }
        return list;
    }

    @Override
    public List<ScheduleJobLogDO> list(int start, int limit, Object... param) {
        return null;
    }

    public int getTotal(ScheduleJobLogDO scheduleJobLogDO) {
        logger.debug("scheduleJobLogDO=" + scheduleJobLogDO);
        List<Object> params = Lists.newArrayList();
        String countSql = "select count(1) from (" + "select * from " + TABLE_NAME + getWhereSql(scheduleJobLogDO, params) + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, params);
        } catch (SQLException e) {
            logger.error("总记录数查询自动任务日志表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询自动任务日志表失败！");
        }
        return total;
    }

    /* 根据传入的数据对象，对应属性值不空时拼接查询条件 */
    private String getWhereSql(ScheduleJobLogDO scheduleJobLogDO, List<Object> params) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        if (!DataUtil.isNullStr(scheduleJobLogDO.getId())) {
            /* 添加查询条件：任务日志id */
            whereSql.append(" and id=?");
            params.add(scheduleJobLogDO.getId().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getPlatSeq())) {
            /* 添加查询条件：平台流水号 */
            whereSql.append(" and plat_seq=?");
            params.add(scheduleJobLogDO.getPlatSeq().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getJobId())) {
            /* 添加查询条件：任务id */
            whereSql.append(" and job_id=?");
            params.add(scheduleJobLogDO.getJobId().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getTaskInsNo())) {
            /* 添加查询条件：实例号 */
            whereSql.append(" and task_ins_no=?");
            params.add(scheduleJobLogDO.getTaskInsNo().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getIp())) {
            /* 添加查询条件：ip */
            whereSql.append(" and ip=?");
            params.add(scheduleJobLogDO.getIp().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getPort())) {
            /* 添加查询条件：端口号 */
            whereSql.append(" and port=?");
            params.add(scheduleJobLogDO.getPort().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getBeanName())) {
            /* 添加查询条件：类名 */
            whereSql.append(" and bean_name=?");
            params.add(scheduleJobLogDO.getBeanName().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getStrTime())) {
            /* 添加查询条件：开始时间 */
            whereSql.append(" and str_time>=?");
            params.add(scheduleJobLogDO.getStrTime().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getEndTime())) {
            /* 添加查询条件：结束时间 */
            whereSql.append(" and end_time<=?");
            params.add(scheduleJobLogDO.getEndTime().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getSuccSwitchFlg())) {
            /* 添加查询条件：任务状态 */
            whereSql.append(" and SUCC_SWITCH_FLG=?");
            params.add(scheduleJobLogDO.getSuccSwitchFlg().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getErrCode())) {
            /* 添加查询条件：错误码 */
            whereSql.append(" and err_code=?");
            params.add(scheduleJobLogDO.getErrCode().trim());
        }
        if (!DataUtil.isNullStr(scheduleJobLogDO.getTranTime())) {
            /* 添加查询条件：交易耗时 */
            whereSql.append(" and tran_time=?");
            params.add(scheduleJobLogDO.getTranTime().trim());
        }
        return whereSql.toString();
    }

    public List<ScheduleJobLogDO> getLog(ScheduleJobLogDO scheduleJobLogDO, int start, int limit) {
        logger.debug("scheduleJobLogDO=" + scheduleJobLogDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> params = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(scheduleJobLogDO, params));
        sql.append(" order by str_time desc, SUCC_SWITCH_FLG desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<ScheduleJobLogDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ScheduleJobLogDO.class, params);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ScheduleJobLogDO.class, start, limit, params);
            }
        } catch (SQLException e) {
            logger.error("列表查询自动任务日志表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询自动任务日志表失败！");
        }
        return list;
    }

}
