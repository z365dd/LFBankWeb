package com.adtec.scheduler.core.manager;

import com.adtec.scheduler.core.model.ScheduleJobZkDTO;

import java.util.List;

/**
 * 任务节点管理接口.
 *
 * @author lijunbin
 */
public interface ScheduleJobManager<T> {

    T getClient();

    void setClient(T client);

    /**
     * 返回指定任务是否是执行状态
     *
     * @param beanName 任务唯一标识
     * @return 是否是执行状态
     */
    boolean isRunning(String beanName);

    /**
     * 添加指定任务
     *
     * @param scheduleJobZkDTO 任务详情
     */
    void save(ScheduleJobZkDTO scheduleJobZkDTO);

    /**
     * 更新指定任务
     *
     * @param scheduleJobZkDTO 任务详情
     */
    void update(ScheduleJobZkDTO scheduleJobZkDTO);

    /**
     * 删除指定任务
     *
     * @param scheduleJobZkDTO 任务详情
     */
    void delete(ScheduleJobZkDTO scheduleJobZkDTO);

    /**
     * 返回所有任务
     *
     * @return List<Task>
     */
    List<ScheduleJobZkDTO> queryTasks();

    /**
     * 判断指定任务是否存在
     *
     * @param scheduleJobZkDTO 任务详情
     * @return 是否存在
     */
    boolean isExist(ScheduleJobZkDTO scheduleJobZkDTO);

    /**
     * 返回指定任务详情
     *
     * @param scheduleJobZkDTO 任务数据
     * @return 任务详情
     */
    ScheduleJobZkDTO queryTask(ScheduleJobZkDTO scheduleJobZkDTO);

}
