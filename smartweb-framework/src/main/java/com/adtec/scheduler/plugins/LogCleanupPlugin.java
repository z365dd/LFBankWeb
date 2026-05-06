package com.adtec.scheduler.plugins;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.handler.ExceptionHandler;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.scheduler.core.annotations.JobDescriptor;
import com.adtec.scheduler.core.handle.IJobHandle;

/**
 * <p>
 * 自动任务日志清理任务
 * 默认每周六凌晨 00:00执行一次
 * 可通过平台管理->调度管理->任务管理 修改调度参数
 * </P>
 *
 * @author lijb
 */
@JobDescriptor(
        name = "自动任务日志清理任务",
        cronExpr = "0 0 0 ? * 7",
        remark = "定时清理自动任务日志表数据，释放硬盘空间"
)
public class LogCleanupPlugin implements IJobHandle {

    private static final String CLEAR_SQL = "TRUNCATE TABLE T_SYS_SCHEDULE_JOB_LOG";

    @Override
    public void doTask() {
        DBSessionFactory.useN().execute(CLEAR_SQL, new ExceptionHandler() {
            @Override
            public void apply(IDBSession session, Exception ex) {
                throw new BaseException(SysErr.E_MESSAGE, "清空自动任务日志表失败：" + ex.getMessage());
            }
        });
    }
}
