package com.adtec.pay.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.utils.OracleSQLErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ExecuteTask<T extends SmsSqlTask<R>, R> {

    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(ExecuteTask.class);

    /**
     * @param actionName 操作名称
     * @param task
     * @return R
     */
    public R sqlTaskNeedTransactional(String actionName, T task) {
        return executeTask(actionName, task, true);
    }

    public R sqlTaskNotNeedTransactional(String actionName, T task) {
        return executeTask(actionName, task, false);
    }

    private R executeTask(String actionName, T task, boolean isTransactional) {
        R rs = null;
        //会话连接 操作数据库
        IDBSession session = DBSessionFactory.getSession();
        try {
            if (isTransactional) {
                session.beginTransaction();
            }
            //session执行
            rs = task.task(session);
            if (isTransactional) {
                session.endTransaction();
            }
        } catch (Exception e) {
            logger.error(actionName + "操作失败：" + e.getMessage());
            if (isTransactional) {
                try {
                    session.rollback();
                } catch (SQLException e1) {
                    e = e1;
                }
            }
            if (e instanceof SQLException) {
                SQLException e1 = (SQLException) e;
                String message = e1.getMessage();
                if(message.contains(":")){
                    throw new BaseException(SysErr.E_MESSAGE, OracleSQLErrorCode.getMsg(message.substring(0, message.indexOf(":"))));
                }else{
                    throw new BaseException(SysErr.E_MESSAGE, OracleSQLErrorCode.getMsg(message));
                }

            }
            throw new BaseException(SysErr.E_MESSAGE, actionName + "操作失败！");
        }
        if (rs == null) {
            throw new BaseException(SysErr.E_MESSAGE, actionName + "操作失败！结果为null");
        }
        return rs;
    }

    public void call() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("打印输出此时状态");
            }
        });
    }
}
