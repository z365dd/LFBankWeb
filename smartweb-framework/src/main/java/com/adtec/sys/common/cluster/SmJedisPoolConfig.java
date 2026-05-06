package com.adtec.sys.common.cluster;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

public class SmJedisPoolConfig extends GenericObjectPoolConfig{
	
	public SmJedisPoolConfig(){
		String flag = ParamUtil.getClusterFlag();
		if(check(flag)){
			try {
				setTestWhileIdle(true);
				setMinEvictableIdleTimeMillis(60000);
				setTimeBetweenEvictionRunsMillis(30000);
				setNumTestsPerEvictionRun(-1);
				int maxTotal = Integer.parseInt(ParamUtil.getRedisMaxTotal());
				int maxIdle = Integer.parseInt(ParamUtil.getRedisMaxIdle());
				int minIdle = Integer.parseInt(ParamUtil.getRedisMinIdle());
				int numTestsPerEvictionRun = Integer.parseInt(ParamUtil.getReidsNumTestsPerEvictionRun());
				long timeBetweenEvictionRunsMillis = Long
						.parseLong(ParamUtil.getRedisTimeBetweenEvictionRunsMillis());
				long minEvictableIdleTimeMillis = Long
						.parseLong(ParamUtil.getRedisMinEvictableIdleTimeMillis());
				long softMinEvictableIdleTimeMillis = Long
						.parseLong(ParamUtil.getRedisSoftMinEvictableIdleTimeMillis());
				long maxWaitMillis = Long.parseLong(ParamUtil.getRedisMaxWaitMillis());
				setMaxTotal(maxTotal);
				setMaxIdle(maxIdle);
				setMinIdle(minIdle);
				setNumTestsPerEvictionRun(numTestsPerEvictionRun);
				setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
				setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
				setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
				setMaxWaitMillis(maxWaitMillis);
				setTestOnBorrow(true);
				setTestWhileIdle(true);
				setBlockWhenExhausted(false);
			} catch (Exception e) {
				throw new BaseException(SysErr.E_MESSAGE, e, "smartweb的reids连接池配置参数异常！");
			}
		}
	}
	public boolean check(String taskRun){
        if( taskRun != null || (null!=taskRun && !taskRun.isEmpty()) ) {
            if (taskRun.toUpperCase().equals("Y")){
            	return true;
            }else{
            	return false;
            }
        }else{
        	return false;
        }
    }
}
