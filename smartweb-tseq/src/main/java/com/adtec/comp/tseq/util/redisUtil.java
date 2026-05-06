package com.adtec.comp.tseq.util;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class redisUtil {

		/**
		 * 根据Key获取reids中的数据
		 * @param reqDs
		 * @return
		 */
		public static List<RedisDataDO> getData(String redisKey) {
			List<RedisDataDO> redisDataList = Lists.newArrayList();
			Set<String> keys = keys(redisKey+"*");
			for (String rsKey : keys) {
				System.out.println("key:"+rsKey);
				Map<String, Object> pMap = getMap(rsKey);
				for (Entry<String, Object> entry : pMap.entrySet()) {
					String key = entry.getKey();
					RedisDataDO redisData = new RedisDataDO();
					redisData.setKey(key);
					if (null != pMap.get(key)) {
						if (pMap.get(key).toString().indexOf("{") != -1) {
							String json = JSON.toJSONString(pMap.get(key));
							redisData.setObject(json);
						}else {
							redisData.setObject(pMap.get(key).toString());
						}
					}else {
						redisData.setObject("null");
					}
					
					redisDataList.add(redisData);
				}
			}
			
			return redisDataList;
		}
		
		/**
		 * 根据pattern 获取所有的keys
		 * @param pattern
		 * @return
		 */
		public static TreeSet<String> keys(String pattern) {
			TreeSet<String> keys = new TreeSet<String>();
			JedisCluster cacheJedisCluster=null;
			try {
				cacheJedisCluster = JedisClusterUtils.getJedisCluster();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			Map<String, JedisPool> clusterNodes = cacheJedisCluster.getClusterNodes();
			for (String k : clusterNodes.keySet()) {
				JedisPool jp = clusterNodes.get(k);
				Jedis connection = jp.getResource();
				try {
					keys.addAll(connection.keys(pattern));
				} catch (Exception e) {
					System.out.println("操作失败");
				} finally {
					connection.close();// 用完一定要close这个链接！！！
				}
			}
			return keys;
		}
		
		/**
		 * 根据key获取存储在redis中的自定义Map对象
		 * @param key
		 * @return
		 */
		public static <T> Map<String, T> getMap(String key) {
			if (null == key || (null != key && key.isEmpty())) {
				return null;
			}
			JedisCluster cacheJedisCluster=null;
			try {
				cacheJedisCluster = JedisClusterUtils.getJedisCluster();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			byte[] in = cacheJedisCluster.get(key.getBytes());
			Map<String, T> map = (Map<String, T>)deserialize(in);
			return map;
		}
		
		/**
		 * 对象反序列化
		 * @param in
		 * @return
		 */
	    public static Object deserialize(byte[] in) {    
	        Object rv=null;    
	        ByteArrayInputStream bis = null;    
	        ObjectInputStream is = null;    
	        try {    
	            if(in != null) {    
	                bis=new ByteArrayInputStream(in);    
	                is=new ObjectInputStream(bis);    
	                rv=is.readObject();    
	                is.close();    
	                bis.close();    
	            }    
	        } catch (Exception e) {
	        	System.out.println("反序列化失败:"+e.getMessage());
	            e.printStackTrace();  
	         }finally {    
	             try {  
	                 if(is!=null)is.close();  
	                 if(bis!=null)bis.close();  
	             } catch (Exception e2) {  
	                 e2.printStackTrace();  
	             }  
	         }  
	        return rv;    
	    }
		
}
