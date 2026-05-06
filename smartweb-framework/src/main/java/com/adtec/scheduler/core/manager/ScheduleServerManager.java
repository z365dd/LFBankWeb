package com.adtec.scheduler.core.manager;

import com.adtec.scheduler.core.model.ScheduleServerZkDTO;

import java.util.List;

/**
 * 服务节点管理接口.
 *
 * @author lijunbin
 */
public interface ScheduleServerManager<T> {

    T getClient();

    void setClient(T client);

    /**
     * 判断指定任务 是否属于指定server节点
     *
     * @param beanName   任务唯一标识
     * @param serverUuid 服务器唯一标识
     * @return 是否属于指定服务器
     */
    boolean isOwner(String beanName, String serverUuid);

    /**
     * 注册服务器
     *
     * @param server 服务器信息
     */
    void registerServer(ScheduleServerZkDTO server);

    /**
     * 判断该服务器是否是分布式调度中心
     *
     * @param serverUuid 服务器唯一标识
     * @param servers    所有服务器
     * @return 指定服务器是否是分布式调度中心
     */
    boolean isMaster(String serverUuid, List<String> servers);

    /**
     * 返回所有服务器名称
     *
     * @return 所有服务器名称
     */
    List<String> getServers();
}
