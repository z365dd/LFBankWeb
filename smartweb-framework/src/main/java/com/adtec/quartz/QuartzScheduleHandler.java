package com.adtec.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;

public class QuartzScheduleHandler {
	protected final static Logger logger = LoggerFactory.getLogger(QuartzScheduleHandler.class);
	public void execute(){
		String taskRun = ParamUtil.getConfig("ms.task.run");
		if("N".equals(taskRun)||DataUtil.isNullStr(taskRun)){
			logger.info("MS自动任务开关:N");
		}
		if(check(taskRun)){
			try {
				Object scheduleTaskService = SpringContextHolder.getBean("myScheduleTaskService");
				Object[] param = new Object[0];
				ClassUtil.invokeMethodByName(scheduleTaskService, "startTask", param);
			} catch (Exception e) {
				logger.error("反射调用scheduleTaskService服务的startTask方法异常！");
			}
		}
	}
	public static boolean check(String taskRun){
        if( taskRun != null || (null!=taskRun && !taskRun.isEmpty()) ) {
            if (taskRun.toUpperCase().equals("Y")){
            	return true;
            }else{
            	return false;
            }
        }else{
        	return false;
        }
    }
}
