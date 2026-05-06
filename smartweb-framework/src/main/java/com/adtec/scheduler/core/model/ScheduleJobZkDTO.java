package com.adtec.scheduler.core.model;

import java.io.Serializable;

/**
 * @author lijunbin
 */
public class ScheduleJobZkDTO implements Serializable {

    private String beanName;

    private Integer execTimes;

    private Integer successTimes;

    private Long lastExecTime;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Integer getExecTimes() {
        return execTimes;
    }

    public void setExecTimes(Integer execTimes) {
        this.execTimes = execTimes;
    }

    public Integer getSuccessTimes() {
        return successTimes;
    }

    public void setSuccessTimes(Integer successTimes) {
        this.successTimes = successTimes;
    }

    public Long getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(Long lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    @Override
    public String toString() {
        return "ScheduleJobZkDO{" +
                "beanName='" + beanName + '\'' +
                ", execTimes=" + execTimes +
                ", successTimes=" + successTimes +
                ", lastExecTime=" + lastExecTime +
                '}';
    }
}
