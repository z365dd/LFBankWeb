package com.adtec.comp.fsvr.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RedisClusterOperator {  
	
	private static RedisClusterOperator redisOper = null;
	private static JedisCluster jedisCluster = null;
	
	public synchronized static RedisClusterOperator getInstance(){
		if(redisOper == null){
			redisOper = new RedisClusterOperator();
			init();
		}
		return redisOper;
	}
	
	private static void init(){
		String address = System.getenv("REDISSERVERLIST");
	    Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();  
	    String[] addressMess = address.split(",");
	    for(String ipAport:addressMess){
	        nodes.add(new HostAndPort(ipAport.substring(0, ipAport.indexOf(":")),
	            Integer.parseInt(ipAport.substring(ipAport.indexOf(":")+1))));  
	    }
	    jedisCluster = new JedisCluster(nodes);
	}
	  
    /**  
     *   
     *KEYS pattern  
     *查找所有符合给定模式 pattern 的 key 。  
     *KEYS * 匹配数据库中所有 key 。  
     *KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。  
     *KEYS h*llo 匹配 hllo 和 heeeeello 等。  
     *KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。  
     *特殊符号用 \ 隔开  
     */  
    public TreeSet<String> keys(String pattern) {  
    	
        TreeSet<String> keys = new TreeSet<String>();  
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();  
        for(Iterator<JedisPool> it=clusterNodes.values().iterator();it.hasNext();) {  
            JedisPool pool = it.next();  
            Jedis jedis = pool.getResource();  
            try {  
                keys.addAll(jedis.keys(pattern));  
            } catch (Exception e) {  
                System.out.println("获取keys发生异常！");  
            } finally {  
                jedis.close();  
            }  
        }  
        return keys;  
    } 
    
    public JedisCluster getJedisCluster(){
    	return jedisCluster;
    }
}  
