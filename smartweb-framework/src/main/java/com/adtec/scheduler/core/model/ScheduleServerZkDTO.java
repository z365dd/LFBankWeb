package com.adtec.scheduler.core.model;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.scheduler.core.util.ScheduleUtil;

import java.sql.Timestamp;
import java.util.UUID;


/**
 * 调度服务器信息定义
 *
 * @author lijunbin
 */
public class ScheduleServerZkDTO {
    /**
     * java中使用双重检查锁定机制,由于Java编译器和JIT的优化的原因系统无法保证我们期望的执行次序。
     * 在java5.0修改了内存模型,使用volatile声明的变量可以强制屏蔽编译器和JIT的优化工作
     */
    private static volatile ScheduleServerZkDTO instance;
    /**
     * 全局唯一编号
     */
    private String uuid;
    /**
     * server标识 用于标记server独立性 避免重复注册
     */
    private String ownSign;
    /**
     * 机器IP地址
     */
    private String ip;
    /**
     * 机器名称
     */
    private String hostName;
    /**
     * 服务开始时间
     */
    private Timestamp registerTime;
    /**
     * 是否注册到server
     */
    private boolean isRegister;

    static {
        instance = createScheduleServer();
    }

    /**
     * 创建分布式任务server对象
     *
     * @return
     */
    public static ScheduleServerZkDTO createScheduleServer() {
        final long currentTime = System.currentTimeMillis();
        final ScheduleServerZkDTO result = new ScheduleServerZkDTO();
        // 已uuid生成server标识
        result.ownSign = UUID.randomUUID().toString().replaceAll("-", "");
        result.ip = ParamUtil.getLocalAddr();
        result.hostName = ScheduleUtil.getLocalHostName();
        result.registerTime = new Timestamp(currentTime);
        result.uuid = result.ip
                + "$"
                + (UUID.randomUUID().toString().replaceAll("-", "")
                .toUpperCase());
        return result;
    }

    /**
     * 单例模式
     */
    private ScheduleServerZkDTO() {
    }

    /**
     * 获取实例(双重检查)
     */
    public static ScheduleServerZkDTO getInstance() {
        return instance;
    }

    public static void setInstance(ScheduleServerZkDTO instance) {
        ScheduleServerZkDTO.instance = instance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwnSign() {
        return ownSign;
    }

    public void setOwnSign(String ownSign) {
        this.ownSign = ownSign;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }
}
