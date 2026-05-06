package com.adtec.sys.common.cluster;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {
	private final static Log log = LogFactory.getLog(JedisClusterFactory.class);
	private String addressConfig;

	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;

	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public JedisClusterFactory() {
		String flag = ParamUtil.getClusterFlag();
		if (check(flag)) {
			String redisServerList = ParamUtil.getRedisServerList();
			String timeout = ParamUtil.getRedisTimeOut();
			String maxRedirections = ParamUtil.getRedisMaxRedirections();
			if (DataUtil.isNullStr(redisServerList) || DataUtil.isNullStr(timeout)
					|| DataUtil.isNullStr(maxRedirections)) {
				throw new BaseException(SysErr.E_MESSAGE, "smartweb的redis配置参数缺失！");
			}
			setAddressConfig(redisServerList);
			setTimeout(Integer.parseInt(timeout));
			setMaxRedirections(Integer.parseInt(maxRedirections));
			// SpringContextHolder.getBean(requiredType)
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		Set<HostAndPort> haps = this.parseHostAndPort();

		/* 20181205 add by chenyl for 如果有配置了REDISPASSWD环境变量的，则使用该密码进行登录 */
		String redisPasswd = System.getenv("REDISPASSWD");

		String flag = ParamUtil.getClusterFlag();
		if (check(flag)) {
			if (null != redisPasswd && !redisPasswd.isEmpty()) {
				if (log.isDebugEnabled()) {
					log.debug("REDISPASSWD=" + redisPasswd);
				}
				jedisCluster = new JedisCluster(haps, timeout, 0, maxRedirections, redisPasswd,
						genericObjectPoolConfig);
			} else {
				jedisCluster = new JedisCluster(haps, timeout, maxRedirections, genericObjectPoolConfig);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public JedisCluster getObject() throws Exception {
		// TODO Auto-generated method stub
		return jedisCluster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			if (null != this.addressConfig && !this.addressConfig.isEmpty()) {
				String[] vals = this.addressConfig.split(",");
				for (String val : vals) {
					boolean isIpPort = p.matcher(val).matches();

					if (!isIpPort) {
						throw new IllegalArgumentException("ip 或 port 不合法");
					}
					String[] ipAndPort = val.split(":");
					if (log.isDebugEnabled()) {
						log.debug("ip=" + ipAndPort[0] + " , port=" + ipAndPort[1]);
					}
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

	public void setAddressConfig(String addressConfig) {
		this.addressConfig = addressConfig;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	public boolean check(String taskRun) {
		if (taskRun != null || (null != taskRun && !taskRun.isEmpty())) {
			if (taskRun.toUpperCase().equals("Y")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
