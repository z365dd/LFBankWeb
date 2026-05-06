package com.adtec.scheduler.config;

import java.util.Collections;
import java.util.List;

import static com.adtec.framework.common.util.ParamUtil.getConfig;

/**
 * 组件配置类
 *
 * @author lijunbin
 */
public class SchedulerProperties {

    private static final String SCHEDULE_BOOT_FLAG = "sm.schedule.boot.flag";
    private static final String ZK_RETRY_INTERVAL = "sm.zookeeper.retry.interval";
    private static final String ZK_RETRY_TIMES = "sm.zookeeper.retry.times";
    private static final String ZK_SESSION_TIMEOUT = "sm.zookeeper.session.timeout";
    private static final String ZK_CONNECT_TIMEOUT = "sm.zookeeper.connection.timeout";
    private static final String SCHEDULE_POOL_SIZE = "sm.schedule.pool.size";
    private static final String SCHEDULE_PROJECT_NODE = "sm.schedule.project.node";
    private static final String SCHEDULE_CLUTER_MODE = "sm.schedule.cluster.mode";

    public static final String ZK_SERVER_LIST = "sm.zookeeper.server.list";

    /**
     * 调度管理器启动标识
     */
    private String scheduleBootFlag;
    /**
     * smartweb集群标识
     */
    private String clusterFlag;
    /**
     * 调度管理集群模式
     */
    private String scheduleClusterMode;
    /**
     * 连接重试时间间隔（毫秒）
     */
    private String zkRetryInterval;
    /**
     * 连接重试次数
     */
    private String zkRetryTimes;
    /**
     * Zookeeper,也可配置和微服务框架的参数
     */
    private String zkServerList;
    /**
     * 会话超时时间（毫秒）
     */
    private String zkSessionTimeout;
    /**
     * 连接超时时间（毫秒）
     */
    private String zkConnectionTimeout;

    /**
     * ip黑名单，配置的ip不执行自动任务
     */
    private List<String> ipBlackList = Collections.emptyList();

    /**
     * spring task scheduler generator size, default: 10
     */
//	@Value("${sm.spring.schedule.pool.size}")
    private String schedulePoolSize;

    /**
     * the interval for refresh & check tasks from zookeeper, default: 30 seconds
     */
    private int refreshTaskInterval = 5;

    /**
     * zk调度节点根路径
     */
    private String scheduleProjectNode;

    public SchedulerProperties() {
        super();
        this.scheduleBootFlag = getConfig(SCHEDULE_BOOT_FLAG);
        this.zkRetryInterval = getConfig(ZK_RETRY_INTERVAL);
        this.zkRetryTimes = getConfig(ZK_RETRY_TIMES);
        this.zkServerList = getConfig(ZK_SERVER_LIST);
        this.zkSessionTimeout = getConfig(ZK_SESSION_TIMEOUT);
        this.zkConnectionTimeout = getConfig(ZK_CONNECT_TIMEOUT);
        this.schedulePoolSize = getConfig(SCHEDULE_POOL_SIZE);
        this.scheduleProjectNode = getConfig(SCHEDULE_PROJECT_NODE);
        this.scheduleClusterMode = getConfig(SCHEDULE_CLUTER_MODE);
    }

    public void updateConfig(String key, String value) {

    }

    public String getScheduleBootFlag() {
        return scheduleBootFlag;
    }

    public void setScheduleBootFlag(String scheduleBootFlag) {
        this.scheduleBootFlag = scheduleBootFlag;
    }

    public String getZkRetryInterval() {
        return zkRetryInterval;
    }

    public void setZkRetryInterval(String zkRetryInterval) {
        this.zkRetryInterval = zkRetryInterval;
    }

    public String getZkRetryTimes() {
        return zkRetryTimes;
    }

    public void setZkRetryTimes(String zkRetryTimes) {
        this.zkRetryTimes = zkRetryTimes;
    }

    public String getZkServerList() {
        return zkServerList;
    }

    public void setZkServerList(String zkServerList) {
        this.zkServerList = zkServerList;
    }

    public String getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(String zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public String getZkConnectionTimeout() {
        return zkConnectionTimeout;
    }

    public void setZkConnectionTimeout(String zkConnectionTimeout) {
        this.zkConnectionTimeout = zkConnectionTimeout;
    }

    public List<String> getIpBlackList() {
        return ipBlackList;
    }

    public void setIpBlackList(List<String> ipBlackList) {
        this.ipBlackList = ipBlackList;
    }

    public String getSchedulePoolSize() {
        return schedulePoolSize;
    }

    public void setSchedulePoolSize(String schedulePoolSize) {
        this.schedulePoolSize = schedulePoolSize;
    }

    public int getRefreshTaskInterval() {
        return refreshTaskInterval;
    }

    public void setRefreshTaskInterval(int refreshTaskInterval) {
        this.refreshTaskInterval = refreshTaskInterval;
    }

    public String getScheduleProjectNode() {
        return scheduleProjectNode;
    }

    public void setScheduleProjectNode(String scheduleProjectNode) {
        this.scheduleProjectNode = scheduleProjectNode;
    }

    public String getScheduleClusterMode() {
        return scheduleClusterMode;
    }

}