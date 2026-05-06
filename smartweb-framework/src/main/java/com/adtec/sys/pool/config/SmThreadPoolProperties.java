package com.adtec.sys.pool.config;

import static com.adtec.framework.common.util.ParamUtil.getConfig;

import com.adtec.framework.common.util.DataUtil;

public class SmThreadPoolProperties {

    private static final String CORE_POOL_SIZE = "sm.threadPool.corePoolSize";
    private static final String MAXIMUM_POOL_SIZE = "sm.threadPool.maximumPoolSize";
    private static final String KEEP_ALIVE_TIME = "sm.threadPool.keepAliveTime";

    /**
     * 线程池核心池的大小
     */
    private Integer corePoolSize;
    /**
     * 线程池的最大线程数
     */
    private Integer maximumPoolSize;
    /**
     * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
     */
    private Long keepAliveTime;

    public SmThreadPoolProperties() {
        super();
        this.corePoolSize = Integer.parseInt(DataUtil.isNullStr(getConfig(CORE_POOL_SIZE)) ? "20" : getConfig(CORE_POOL_SIZE));
        this.maximumPoolSize = Integer.parseInt(DataUtil.isNullStr(getConfig(MAXIMUM_POOL_SIZE)) ? "50" : getConfig(MAXIMUM_POOL_SIZE));
        this.keepAliveTime = Long.parseLong(DataUtil.isNullStr(getConfig(KEEP_ALIVE_TIME)) ? "0" : getConfig(KEEP_ALIVE_TIME));
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}
