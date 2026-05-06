package com.adtec.para.common.util;

import com.adtec.cache.cacheagent.util.DataUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;

/**  
 * @类名 ParamUtil.java  
 * @描述: 
 *     配置文件读取工具类
 * @版本 v1.0  
 */
public class ParaParamUtil extends PropertyPlaceholderConfigurer { 	
	private final static Logger logger = LoggerFactory.getLogger(ParaParamUtil.class);
	private static Map<String, Object> ctxCachePropertiesMap = Maps.newHashMap();
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		// TODO Auto-generated method stub
		logger.info("cacheagent propeties init ...");
		super.processProperties(beanFactoryToProcess, props);  
		for (Object key : props.keySet()) { 
			String keyStr = key.toString(); 
			String value = props.getProperty(keyStr); 
			ctxCachePropertiesMap.put(keyStr, value); 
		}
	}
	
	/**
	 * 获取config.properties中配置的属性值
	 * @param name
	 * @return
	 */
	public static Object getContextProperty(String name) { 
        return ctxCachePropertiesMap.get(name); 
    } 
	
	/**
	 * 获取config.properties中配置的属性值
	 * @param name
	 * @return
	 */
	public static String getString(String name) { 
		String value = "";
		if(null!=getContextProperty(name)){
			value = (String)getContextProperty(name);
		}
        return value; 
    } 

	/**
	 * 获取zk探测时间间隔
	 * @return
	 */
	public static Integer getZKTestInterval(){
		return new Integer(getString("zookeeper.test.interval"));
	}

	/**
	 * 获取是否开启缓存中心标记
	 * @return
	 */
	public static String getMSCache() {
		// TODO Auto-generated method stub
		return getString("mscache.run");
	}

	/**
	 * 获取zk的session超时时间
	 * @return
	 */
	public static int getZKSessionTimeout() {
		// TODO Auto-generated method stub
		return new Integer(getString("zookeeper.session.timeout"));
	}
	
	/**
	 * 获取zk的连接超时时间
	 * @return
	 */
	public static int getZkConnectionTimeout(){
		return new Integer(getString("zookeeper.connection.timeout"));
	}
	
	/**
	 * 获取Redis集群创建连接的超时时间
	 * @return
	 */
	public static int getRedisTimeout(){
		return new Integer(getString("redis.timeout"));
	}
	/**
	 * 获取Redis的取值失败，最大尝试次数 
	 * @return
	 */
	public static int getRedisMaxRedirection(){
		return new Integer(getString("redis.maxRedirections"));
	}
	/**
	 * zk节点
	 * @return
	 */
	public static String getZkRoot() {
		if (!DataUtil.isNullStr(getString("zk.root"))) {
			return "/"+getString("zk.root");
		} else {
			return "/Cache";
		}
	}
}
 