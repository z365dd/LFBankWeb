/**
 * 系统名称: 缓存中心
 * 模块名称: Redis集群工厂类
 * 类  名  称: JedisClusterFactory.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年10月24日 上午10:55:19<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.para.common.util;

import com.adtec.para.cfgcenter.entity.ParaCenterData;
import com.adtec.para.cfgcenter.entity.ParaRedisCluster;
import com.adtec.para.cfgcenter.entity.ParaRedisNodeData;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author chenyl
 *
 */
public class ParaJedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean{
	
	private final static Log log = LogFactory.getLog(ParaJedisClusterFactory.class);
  
    private JedisCluster jedisCluster;  
    private Integer timeout;  
    private Integer maxRedirections;  
    private GenericObjectPoolConfig genericObjectPoolConfig;  
    
    @Autowired
    private CuratorFramework configClient;
      
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");  

    @Override
	public JedisCluster getObject() throws Exception {
		// TODO Auto-generated method stub
		return jedisCluster;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class); 
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}
    
	@Override
	public void afterPropertiesSet() throws Exception {
//    	log.info("##------连接缓存中心Redis开始------##");
    	cacheCenterWatcher();
//    	log.info("##------连接缓存中心Redis结束------##");
    }
	
	/**
	 * 开启缓存中心节点监听器
	 */
	private void cacheCenterWatcher() {
		CuratorWatcher callbackWatcher = new CuratorWatcher() {
			@Override
			public void process(WatchedEvent event) throws Exception {
				List<String> list = configClient.getChildren().usingWatcher(this).forPath(ParaZkNode.CACHE_CENTERS);
		    	for (String centerId : list) {
					if (null != configClient.checkExists().forPath(ParaZkNode.CACHE_CENTERS+"/"+centerId)) {
						byte[] b = configClient.getData().forPath(ParaZkNode.CACHE_CENTERS+"/"+centerId);
						String json = new String(b);
						if(null==json || (null!=json && json.isEmpty())){
		            		json = "{}";
		            	}
						ParaCenterData centersData = JSON.parseObject(json, ParaCenterData.class);
						ParaRedisCluster redisClusterData = centersData.getRedisCluster();
						
						Set<HostAndPort> haps = parseHostAndPort(redisClusterData);
						
						String redisPasswd = centersData.getRsPwd();
						if(null!=redisPasswd && !redisPasswd.isEmpty()){
							if(log.isDebugEnabled()){
				            	log.debug("REDISPASSWD="+redisPasswd); 
				            }
							jedisCluster = new JedisCluster(haps, timeout, 0, maxRedirections, redisPasswd, genericObjectPoolConfig);
						}else{
							jedisCluster = new JedisCluster(haps, timeout, maxRedirections, genericObjectPoolConfig);  
						}
					}
				}
			}};
		try {
			if (null == configClient.checkExists().forPath(ParaZkNode.CACHE_CENTERS)) {
				Map<String, Object> paramInfo = Maps.newHashMap();
				paramInfo.put(ParaMapKey.STATUS, "run");
				String data = JSON.toJSONString(paramInfo);
				configClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(ParaZkNode.CACHE_CENTERS, data.getBytes());
			}
			List<String> list = configClient.getChildren().usingWatcher(callbackWatcher).forPath(ParaZkNode.CACHE_CENTERS);
	    	for (String centerId : list) {
				if (null != configClient.checkExists().forPath(ParaZkNode.CACHE_CENTERS+"/"+centerId)) {
					byte[] b = configClient.getData().forPath(ParaZkNode.CACHE_CENTERS+"/"+centerId);
					String json = new String(b);
					if(null==json || (null!=json && json.isEmpty())){
	            		json = "{}";
	            	}
					ParaCenterData centersData = JSON.parseObject(json, ParaCenterData.class);
					ParaRedisCluster redisClusterData = centersData.getRedisCluster();
					
					Set<HostAndPort> haps = parseHostAndPort(redisClusterData);
					
					String redisPasswd = centersData.getRsPwd();
					if(null!=redisPasswd && !redisPasswd.isEmpty()){
						if(log.isDebugEnabled()){
			            	log.debug("REDISPASSWD="+redisPasswd); 
			            }
						jedisCluster = new JedisCluster(haps, timeout, 0, maxRedirections, redisPasswd, genericObjectPoolConfig);
					}else{
						jedisCluster = new JedisCluster(haps, timeout, maxRedirections, genericObjectPoolConfig);  
					}
				}
			}
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("连接缓存中心异常");
		}
	}
	private Set<HostAndPort> parseHostAndPort(ParaRedisCluster redisClusterData) throws Exception {
        try {    
            Set<HostAndPort> haps = new HashSet<HostAndPort>();
            String redisAddress = getRedisAddress(redisClusterData);
            if(null!=redisAddress && !redisAddress.isEmpty()){
            	String[] vals = redisAddress.split(",");
            	for(String val:vals){
            		boolean isIpPort = p.matcher(val).matches();  
            		  
                    if (!isIpPort) {  
                        throw new IllegalArgumentException("ip 或 port 不合法");  
                    }  
                    String[] ipAndPort = val.split(":");  
                    log.debug("ip="+ipAndPort[0]+" , port="+ipAndPort[1]);     
                    HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));  
                    haps.add(hap);
            	}
            }
  
            return haps;  
        } catch (IllegalArgumentException ex) {  
            throw ex;  
        } catch (Exception ex) {  
            throw new Exception("解析 jedis 配置失败", ex);  
        }  
    }  
	
    public String getRedisAddress(ParaRedisCluster redisClusterData) throws Exception{
    	String redisAddress = "";
    	List<ParaRedisNodeData> list = redisClusterData.getServers();
		for (int i=0; i<list.size(); i++) {
			ParaRedisNodeData nodeData = list.get(i);
			redisAddress += nodeData.getIp()+":"+nodeData.getPort();
			for (int j=0;j<nodeData.getSlaveData().size();j++) {
				ParaRedisNodeData slave = nodeData.getSlaveData().get(j);
				redisAddress += ",";
				redisAddress += slave.getIp()+":"+slave.getPort();
				if (i<nodeData.getSlaveData().size()-1){
					redisAddress += ",";
				}
			}
			if (i<list.size()-1) {
				redisAddress += ",";
			}
		}
    	return redisAddress;
    }

	/**
	 * @return the timeout
	 */
	public Integer getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the maxRedirections
	 */
	public Integer getMaxRedirections() {
		return maxRedirections;
	}

	/**
	 * @param maxRedirections the maxRedirections to set
	 */
	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	/**
	 * @return the genericObjectPoolConfig
	 */
	public GenericObjectPoolConfig getGenericObjectPoolConfig() {
		return genericObjectPoolConfig;
	}

	/**
	 * @param genericObjectPoolConfig the genericObjectPoolConfig to set
	 */
	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}
}
