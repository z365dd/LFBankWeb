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
package com.adtec.comp.tseq.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @author chenyl
 *
 */
public class JedisClusterUtils{
	
	private final static Log log = LogFactory.getLog(JedisClusterUtils.class);
    private static Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");  
    
    
    /**
     * 
     * @return
     * @throws Exception
     */
	public static JedisCluster getJedisCluster() throws Exception {
		String redisAddress="";
		Set<HostAndPort> haps = parseHostAndPort(redisAddress);
		Integer timeout = 10000;
		Integer maxRedirections = 10000;
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		return new JedisCluster(haps, timeout, maxRedirections, genericObjectPoolConfig);  
	}

	private static Set<HostAndPort> parseHostAndPort(String redisAddress) throws Exception {  
        try {    
            Set<HostAndPort> haps = new HashSet<HostAndPort>();
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
}
