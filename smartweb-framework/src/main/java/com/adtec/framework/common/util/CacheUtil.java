package com.adtec.framework.common.util;

import java.util.Map;

import com.adtec.sys.modules.sys.service.RedisService;
import com.google.common.collect.Maps;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Cache工具类
 * 
 * @version 2016-3-1
 */
public class CacheUtil {
	
	private static CacheManager cacheManager = ((CacheManager)SpringContextHolder.getBean("cacheManager"));

	public static final String SYS_CACHE = "sysCache";
	
	public static final String HTTP_JSON_CACHE = "httpJson";
	
	public final static String clusterFlag = ParamUtil.getConfig("sm.cluster.flag");
	

	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		if(!"Y".equals(clusterFlag)||"MSAgent".equals(key)){
			Element element = getCache(cacheName).get(key);
			return element==null?null:element.getObjectValue();
		}else{
			RedisService smRedisService = ((RedisService)SpringContextHolder.getBean("smRedisService"));
			Map<String, Object> map = smRedisService.getMap(cacheName);
			return map==null?null:map.get(key);
		}
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Map<String, Object> getCacheMap(String cacheName) {
		RedisService smRedisService = (RedisService)SpringContextHolder.getBean("smRedisService");
		Map<String, Object> map = smRedisService.getMap(cacheName);
		return map;
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		if(!"Y".equals(clusterFlag)||"MSAgent".equals(key)){
			Element element = new Element(key, value);
			getCache(cacheName).put(element);
		}else{
			RedisService smRedisService = ((RedisService)SpringContextHolder.getBean("smRedisService"));
			Map<String, Object> map = smRedisService.getMap(cacheName);
			if(null==map){
				map = Maps.newHashMap();
			}
			map.put(key, value);
			smRedisService.setMap(cacheName, map);;
		}
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		if(!"Y".equals(clusterFlag)||"MSAgent".equals(key)){
			getCache(cacheName).remove(key);
		}else{
			RedisService smRedisService = ((RedisService)SpringContextHolder.getBean("smRedisService"));
			Map<String, Object> map = smRedisService.getMap(cacheName);
			if(null!=map){
				map.remove(key);
				smRedisService.setMap(cacheName, map);
			}
		}
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
