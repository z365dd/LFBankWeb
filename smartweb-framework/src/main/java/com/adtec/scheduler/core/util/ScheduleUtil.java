package com.adtec.scheduler.core.util;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.scheduler.dao.ScheduleJobDao;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.scheduler.service.ScheduleJobService;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 调度处理工具类
 *
 * @author lijunbin
 */
public class ScheduleUtil {

    private static final transient Logger logger = LoggerFactory.getLogger(ScheduleUtil.class);

    public static final transient String SCHEDULE_CACHE = "scheduleCache";

    private static final String SCHEDULE_CACHE_BEAN_ = "beanName_";

    private static final transient String SCHEDULE_CACHE_LIST = "scheduleCacheList";

    public static final transient String SCHEDULE_BEAN_NAME_PREFIX = "";

    private static final ScheduleJobDao SCHEDULE_JOB_DAO = SpringContextHolder.getBean("scheduleJobDao");
    private static final ScheduleJobService SCHEDULE_JOB_SERVICE = SpringContextHolder.getBean("schedulerService");

    private static final String CLUSTER_FLAG = ParamUtil.getConfig("sm.schedule.cluster.flag");

    private static final String BOOT_FLAG = ParamUtil.getConfig("sm.schedule.boot.flag");

    @SuppressWarnings("unchecked")
    public static void put(String beanName, ScheduleJobDO scheduleJobDO) {
        List<ScheduleJobDO> scheduleCacheList;
        Object obj = CacheUtil.get(SCHEDULE_CACHE, SCHEDULE_CACHE_LIST);
        if (obj instanceof List) {
            scheduleCacheList = (List<ScheduleJobDO>) obj;
        } else {
            scheduleCacheList = SCHEDULE_JOB_SERVICE.queryTasks();
        }
        scheduleCacheList.add(scheduleJobDO);
        CacheUtil.put(SCHEDULE_CACHE, SCHEDULE_CACHE_BEAN_ + beanName, scheduleJobDO);
        CacheUtil.put(SCHEDULE_CACHE, SCHEDULE_CACHE_LIST, scheduleCacheList);
    }

    public static void remove(String beanName) {
        CacheUtil.remove(SCHEDULE_CACHE, SCHEDULE_CACHE_BEAN_ + beanName);
        CacheUtil.remove(SCHEDULE_CACHE, SCHEDULE_CACHE_LIST);
    }

    @SuppressWarnings("unchecked")
    public static Optional<List<ScheduleJobDO>> queryTasks() {
        Object obj = CacheUtil.get(SCHEDULE_CACHE, SCHEDULE_CACHE_LIST);
        Optional<List<ScheduleJobDO>> listOptional;
        if (obj instanceof List) {
            listOptional = Optional.of((List<ScheduleJobDO>) obj);
        } else {
            listOptional = Optional.of(SCHEDULE_JOB_SERVICE.queryTasks());
            CacheUtil.put(SCHEDULE_CACHE, SCHEDULE_CACHE_LIST, listOptional.or(new ArrayList<ScheduleJobDO>()));
        }
        return listOptional;
    }

    public static ScheduleJobDO get(String beanName) {
        ScheduleJobDO scheduleJobDO;
        Object obj = CacheUtil.get(SCHEDULE_CACHE, SCHEDULE_CACHE_BEAN_ + beanName);
        if (obj instanceof ScheduleJobDO) {
            scheduleJobDO = (ScheduleJobDO) obj;
        } else {
            scheduleJobDO = SCHEDULE_JOB_DAO.getByBeanName(beanName);
            if (null != scheduleJobDO) {
                CacheUtil.put(SCHEDULE_CACHE, SCHEDULE_CACHE_BEAN_ + beanName, scheduleJobDO);
            }
        }
        return scheduleJobDO;
    }

    public static void insert(ScheduleJobDO scheduleJobDO) {
        SCHEDULE_JOB_SERVICE.insert(scheduleJobDO);
    }

    public static boolean checkFlag(String flag) {
        return "Y".equalsIgnoreCase(flag);
    }

    /**
     * 检查是否集群部署
     *
     * @return true-集群部署
     */
    public static boolean checkClusterFlag() {
        return checkFlag(CLUSTER_FLAG);
    }

    public static boolean checkBootFlag() {
        return checkFlag(BOOT_FLAG);
    }

    /**
     * 动态注册bean
     *
     * @param clazz /
     */
    public static void registerBean(Class<?> clazz) {
        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextHolder.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition(processBeanName(clazz.getSimpleName()), beanDefinitionBuilder.getRawBeanDefinition());
    }

    /**
     * 动态删除bean
     *
     * @param clazz /
     */
    public static void removeBeanDefinition(Class<?> clazz) {
        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextHolder.getApplicationContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        // 删除bean.
        defaultListableBeanFactory.removeBeanDefinition(processBeanName(clazz.getSimpleName()));
    }

    public static String processBeanName(String classSimpleName) {
        return SCHEDULE_BEAN_NAME_PREFIX + classSimpleName.replace(String.valueOf(classSimpleName.charAt(0)), String.valueOf(classSimpleName.charAt(0)).toLowerCase());
    }

    /**
     * 获取本地 host
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取本地ip
     *
     * @return
     */
    public static String getLocalIp() {
        // 本地IP，如果没有配置外网IP则返回它
        String localip = null;
        // 外网IP
        String netip = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
        InetAddress ip;
        // 是否找到外网IP
        boolean finded = false;
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(":")) {
                    // 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(":")) {
                    // 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
