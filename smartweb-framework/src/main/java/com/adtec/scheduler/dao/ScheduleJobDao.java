package com.adtec.scheduler.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.handler.ExceptionHandler;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public class ScheduleJobDao implements IBaseDao<ScheduleJobDO> {

    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(ScheduleJobDao.class);
    /**
     * 表名称
     */
    public static final String TABLE_NAME = "T_SYS_SCHEDULE_JOB";

    private static final String DATE_FMT = "yyyy-MM-dd HH:mm:ss";


    @Override
    public int insert(ScheduleJobDO scheduleJobDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, scheduleJobDO, scheduleJobDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增自动任务表异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增自动任务表失败！" + e.getMessage());
        }
        return rs;
    }


    @Override
    public int update(ScheduleJobDO scheduleJobDO) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, scheduleJobDO, scheduleJobDO.getMatchFields(), scheduleJobDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改自动任务表异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改自动任务表失败！");
        }
        return rs;
    }


    @Override
    public int delete(ScheduleJobDO scheduleJobDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id=?");
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), scheduleJobDO.getId());
        } catch (SQLException e) {
            logger.error("删除自动任务表异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除自动任务表失败！");
        }
        return rs;
    }


    public ScheduleJobDO get(String id) {
        ScheduleJobDO obj = new ScheduleJobDO();
        obj.setId(id);
        return get(obj);
    }

    @Override
    public ScheduleJobDO get(ScheduleJobDO scheduleJobDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=?");
        ScheduleJobDO rs = new ScheduleJobDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ScheduleJobDO.class, scheduleJobDO.getId());
        } catch (SQLException e) {
            logger.error("查询自动任务表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询自动任务表失败！");
        }
        return rs;
    }


    @Override
    public List<ScheduleJobDO> list(ScheduleJobDO scheduleJobDO) {
        return list(scheduleJobDO, 0, 0);
    }

    @Override
    public List<ScheduleJobDO> list(ScheduleJobDO scheduleJobDO, int start, int limit) {
        logger.debug("scheduleJobDO=" + scheduleJobDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> params = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(scheduleJobDO, params));
        sql.append(" order by upt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<ScheduleJobDO> list;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ScheduleJobDO.class, params);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ScheduleJobDO.class, start, limit, params);
            }
        } catch (SQLException e) {
            logger.error("列表查询自动任务表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询自动任务表失败！" + e.getMessage());
        }
        return list;
    }


    @Override
    public List<ScheduleJobDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(ScheduleJobDO scheduleJobDO) {
        logger.debug("scheduleJobDO=" + scheduleJobDO);
        List<Object> params = Lists.newArrayList();
        String countSql = "select count(1) from (" + "select * from " + TABLE_NAME + getWhereSql(scheduleJobDO, params) + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total;
        try {
            total = session.accountByList(countSql, params);
        } catch (SQLException e) {
            logger.error("总记录数查询自动任务表异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询自动任务表失败！");
        }
        return total;
    }

    public ScheduleJobDO getByBeanName(String beanName) {
        String sql = "select * from " + TABLE_NAME + " where bean_name=?";
        return DBSessionFactory.useN().getObject(sql, ScheduleJobDO.class, new ExceptionHandler() {
            @Override
            public void apply(IDBSession session, Exception e) {
                logger.error("查询自动任务表异常：" + e.getMessage());
                throw new BaseException(SysErr.E_MESSAGE, "查询自动任务表失败！" + e.getMessage());
            }
        }, beanName);
    }

    public List<ScheduleJobDO> queryTasks() {
        String currentDateStr = DateUtil.getDate(DATE_FMT);
        Object[] params = {currentDateStr, currentDateStr};
        String sql = "select * from " + TABLE_NAME + " where EFFT_TIME<=? and INVL_TIME>=? and OPEN_SWITCH_FLG='1'";
        return DBSessionFactory.useN().getObjectList(sql, ScheduleJobDO.class, new ExceptionHandler() {
            @Override
            public void apply(IDBSession session, Exception e) {
                logger.error("列表查询自动任务表异常：" + e.getMessage());
                throw new BaseException(SysErr.E_MESSAGE, "列表查询自动任务表失败！" + e.getMessage());
            }
        }, params);
    }


    /**
     * 根据传入的数据对象，对应属性值不空时拼接查询条件
     */
    private String getWhereSql(ScheduleJobDO scheduleJobDO, List<Object> params) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        if (!DataUtil.isNullStr(scheduleJobDO.getId())) {
            /*添加查询条件：任务id*/
            whereSql.append(" and id=?");
            params.add(scheduleJobDO.getId());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getCronExpr())) {
            /*添加查询条件：cron表达式*/
            whereSql.append(" and cron_expr=?");
            params.add(scheduleJobDO.getCronExpr());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getBeanName())) {
            /*添加查询条件：类名*/
            whereSql.append(" and bean_name like ?");
            params.add("%" + scheduleJobDO.getBeanName().trim() + "%");
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getName())) {
            /*添加查询条件：中文名称*/
            whereSql.append(" or name like ?");
            params.add("%" + scheduleJobDO.getName().trim() + "%");
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getEfftTime())) {
            /*添加查询条件：生效时间*/
            whereSql.append(" and efft_time=?");
            params.add(scheduleJobDO.getEfftTime());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getInvlTime())) {
            /*添加查询条件：失效时间*/
            whereSql.append(" and invl_time=?");
            params.add(scheduleJobDO.getInvlTime());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getOpenSwitchFlg())) {
            /*添加查询条件：任务状态*/
            whereSql.append(" and OPEN_SWITCH_FLG=?");
            params.add(scheduleJobDO.getOpenSwitchFlg());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getPlanExecMeth())) {
            /*添加查询条件：串行/并行*/
            whereSql.append(" and PLAN_EXEC_METH=?");
            params.add(scheduleJobDO.getPlanExecMeth());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getNumKv())) {
            /*添加查询条件：并行数*/
            whereSql.append(" and NUM_KV=?");
            params.add(scheduleJobDO.getNumKv());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getTimeUnitTp())) {
            /*添加查询条件：任务执行时间单位*/
            whereSql.append(" and TIME_UNIT_TP=?");
            params.add(scheduleJobDO.getTimeUnitTp());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getIntvlTime())) {
            /*添加查询条件：时间间隔*/
            whereSql.append(" and intvl_time=?");
            params.add(scheduleJobDO.getIntvlTime());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getStrTime())) {
            /*添加查询条件：开始时间*/
            whereSql.append(" and str_time=?");
            params.add(scheduleJobDO.getStrTime());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getEndTime())) {
            /*添加查询条件：结束时间*/
            whereSql.append(" and end_time=?");
            params.add(scheduleJobDO.getEndTime());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getPlanExecTp())) {
            /*添加查询条件：执行日期选择方式*/
            whereSql.append(" and PLAN_EXEC_TP=?");
            params.add(scheduleJobDO.getPlanExecTp());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getProcDateParaVal())) {
            /*添加查询条件：执行日期*/
            whereSql.append(" and PROC_DATE_PARA_VAL=?");
            params.add(scheduleJobDO.getProcDateParaVal());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getRunSwitchFlg())) {
            /*添加查询条件：说明*/
            whereSql.append(" and RUN_SWITCH_FLG=?");
            params.add(scheduleJobDO.getRunSwitchFlg());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getRmrk())) {
            /*添加查询条件：说明*/
            whereSql.append(" and rmrk=?");
            params.add(scheduleJobDO.getRmrk());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getCrtr())) {
            /*添加查询条件：创建者*/
            whereSql.append(" and crtr=?");
            params.add(scheduleJobDO.getCrtr());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getCrtTime())) {
            /*添加查询条件：创建时间*/
            whereSql.append(" and crt_time=?");
            params.add(scheduleJobDO.getCrtTime());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getUptr())) {
            /*添加查询条件：更新者*/
            whereSql.append(" and uptr=?");
            params.add(scheduleJobDO.getUptr());
        }
        if (!DataUtil.isNullStr(scheduleJobDO.getUptTime())) {
            /*添加查询条件：更新时间*/
            whereSql.append(" and upt_time=?");
            params.add(scheduleJobDO.getUptTime());
        }
        return whereSql.toString();
    }

}
