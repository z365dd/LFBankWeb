package com.adtec.sys.modules.sys.service;


import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.utils.ObjectTransUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@SuppressWarnings("rawtypes")
@Service("smRedisService")
public class RedisService{
	private final static Log log = LogFactory.getLog(RedisService.class);
	
	private JedisCluster smJedisCluster = (JedisCluster)SpringContextHolder.getBean("smJedisCluster")==null?null:(JedisCluster)SpringContextHolder.getBean("smJedisCluster");
	
	public String getString(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		String value = smJedisCluster.get(key);
		if(log.isInfoEnabled()){
			log.info("getString：[" + key + " , " + value + "]");
		}
		return value;
	}

	public void setString(String key, String value) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if (null == value) {
			log.error("value不能为null");
			return;
		}
		if(log.isInfoEnabled()){
			log.info("setString：[" + key + " , " + value + "]");
		}
		smJedisCluster.set(key, value);
	}

	public void setString(String key, String value, int expireSeconds) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if (null == value) {
			log.error("value不能为null");
			return;
		}
		if(log.isInfoEnabled()){
			log.info("setString：[" + key + " , " + value + " , " + expireSeconds + "]");
		}
		smJedisCluster.setex(key, expireSeconds, value);
	}

	@SuppressWarnings({ "unchecked" })
	public Object getObject(String key, Class objClass) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		if (null == objClass) {
			log.error("返回的数据类型不能空");
			return null;
		}
		String sObj = smJedisCluster.get(key);
		Object value = null;
		if (null != sObj && !sObj.isEmpty()) {
			value = JSON.parseObject(sObj, objClass);
		}
		if(log.isInfoEnabled()){
			log.info("getObject：[" + key + " , " + value + "]");
		}
		return value;
	}

	public Map<String, Object> getObject(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		String sObj = smJedisCluster.get(key);
		Map<String, Object> value = null;
		if (null != sObj && !sObj.isEmpty()) {
			value = JSON.parseObject(sObj, new TypeReference<Map<String, Object>>() {
			});
		}
		if(log.isInfoEnabled()){
			log.info("getObject：[" + key + " , " + value + "]");
		}
		return value;
	}

	public void setObject(String key, Object value) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if (null == value) {
			log.error("value不能为null");
			return;
		}
		String json = JSON.toJSONString(value);
		if(log.isInfoEnabled()){
			log.info("setObject：[" + key + " , " + json + "]");
		}
		smJedisCluster.set(key, json);
	}

	public void setObject(String key, Object value, int expireSeconds) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if (null == value) {
			log.error("value不能为null");
			return;
		}
		String json = JSON.toJSONString(value);
		if(log.isInfoEnabled()){
			log.info("setObject：[" + key + " , " + json + "]");
		}
		smJedisCluster.setex(key, expireSeconds, json);
	}

	public void deleteObject(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if(log.isInfoEnabled()){
			log.info("deleteObject：[" + key + "]");
		}
		smJedisCluster.del(key);
	}

	public TreeSet<String> keys(String pattern) {
		if(log.isInfoEnabled()){
			log.info("Start getting keys ["+pattern+"]...");
		}
		TreeSet<String> keys = new TreeSet<String>();
		Map<String, JedisPool> clusterNodes = smJedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			/*20190319 add by chenyl for 添加对异常redis节点的跳过处理*/
			JedisPool jp = null;
			Jedis connection = null;
			try {
				if(log.isInfoEnabled()){
					log.info("Getting keys from: " + k);
				}
				jp = clusterNodes.get(k);
				connection = jp.getResource();
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				log.error("跳过redis异常节点["+k+"]: " + e);
			} finally {
				if(log.isInfoEnabled()){
					log.info("Connection closed." + k);
				}
				if(null!=connection){
					connection.close();// 用完一定要close这个链接！！！
				}
			}
		}
		if(log.isInfoEnabled()){
			log.info("Keys gotten!");
		}
		return keys;
	}

	public Long incr(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return 0L;
		}
		return smJedisCluster.incr(key);
	}

	public Long decr(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return 0L;
		}
		return smJedisCluster.decr(key);
	}

	public Long llen(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return 0L;
		}
		return smJedisCluster.llen(key);
	}

	public Long lpush(String key, String... value) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return 0L;
		}
		if (null == value || (null != value && value.length <= 0)) {
			log.error("value不能为null");
			return 0L;
		}
		return smJedisCluster.lpush(key, value);
	}

	public Long rpush(String key, String... value) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return 0L;
		}
		if (null == value || (null != value && value.length <= 0)) {
			log.error("value不能为null");
			return 0L;
		}
		return smJedisCluster.rpush(key, value);
	}

	public String lpop(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		if(log.isInfoEnabled()){
			log.info("lpop：[" + key + "]");
		}
		return smJedisCluster.lpop(key);
	}

	public String rpop(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		if(log.isInfoEnabled()){
			log.info("rpop：[" + key + "]");
		}
		return smJedisCluster.rpop(key);
	}

	public List<String> lrange(String key, int start, int end) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		if(log.isInfoEnabled()){
			log.info("lrange：[" + key + " , " + start + " , " + end + "]");
		}
		return smJedisCluster.lrange(key, start, end);
	}

	public String ltrim(String key, int start, int end) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		if(log.isInfoEnabled()){
			log.info("ltrim：[" + key + " , " + start + " , " + end + "]");
		}
		return smJedisCluster.ltrim(key, start, end);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		byte[] in = smJedisCluster.get(key.getBytes());
		List<T> list = (List<T>) ObjectTransUtils.deserialize(in);
		return list;
	}

	public <T> void setList(String key, List<T> list) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if (null == list) {
			log.error("list不能为null");
			return;
		}
		if(log.isInfoEnabled()){
			log.info("setList：[" + key + " , " + list + "]");
		}
		try {
			smJedisCluster.set(key.getBytes(), ObjectTransUtils.serialize(list));
		} catch (Exception e) {
			log.error("Set key error : " + e);
		}
	}

	/**
	 * 获取map
	 * 
	 * @param <T>
	 * @param key
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getMap(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return null;
		}
		byte[] in = smJedisCluster.get(key.getBytes());
		Map<String, T> map = (Map<String, T>) ObjectTransUtils.deserialize(in);
		return map;
	}

	/**
	 * 设置 map
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void setMap(String key, Map<String, T> map) {
		if (null == key || (null != key && key.isEmpty())) {
			log.error("key不能空");
			return;
		}
		if (null == map) {
			log.error("map不能为null");
			return;
		}
		if(log.isInfoEnabled()){
			log.info("setMap：[" + key + " , " + map + "]");
		}
		try {
			smJedisCluster.set(key.getBytes(), ObjectTransUtils.serialize(map));
		} catch (Exception e) {
			log.warn("Set key error : " + e);
		}
	}

}
