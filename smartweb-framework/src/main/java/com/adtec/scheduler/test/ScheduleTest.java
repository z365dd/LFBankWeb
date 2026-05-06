package com.adtec.scheduler.test;

import com.adtec.scheduler.core.handle.IJobHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijunbin
 */
// @JobDescriptor(name = "自动任务测试类", cronExpr = "0/5 * * * * ?", modlCode = "", modlDesc = "平台管理")
public class ScheduleTest implements IJobHandle {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTest.class);

    @Override
    public void doTask() {
        logger.info("自动任务com.adtec.scheduler.core.handle.IJobHandle.ScheduleTest 执行了.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
