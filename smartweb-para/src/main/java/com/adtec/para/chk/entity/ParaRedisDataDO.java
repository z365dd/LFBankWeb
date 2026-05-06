package com.adtec.para.chk.entity;

import java.util.Map;

public class ParaRedisDataDO {
	private String paramType;
	private String redisKey;
	private String unixKey;
	private String uniqueKey;
	private Map<String, Object> pMap;
	private String key;
	private Object object;
	private String cacheSeq;
	private String redisSeq;
	
	public String getRedisSeq() {
		return redisSeq;
	}
	public void setRedisSeq(String redisSeq) {
		this.redisSeq = redisSeq;
	}
	public String getCacheSeq() {
		return cacheSeq;
	}
	public void setCacheSeq(String cacheSeq) {
		this.cacheSeq = cacheSeq;
	}
	/**
	 * @return the paramType
	 */
	public String getParamType() {
		return paramType;
	}
	/**
	 * @param paramType the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	/**
	 * @return the redisKey
	 */
	public String getRedisKey() {
		return redisKey;
	}
	/**
	 * @param redisKey the redisKey to set
	 */
	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}
	/**
	 * @return the unixKey
	 */
	public String getUnixKey() {
		return unixKey;
	}
	/**
	 * @param unixKey the unixKey to set
	 */
	public void setUnixKey(String unixKey) {
		this.unixKey = unixKey;
	}
	/**
	 * @return the uniqueKey
	 */
	public String getUniqueKey() {
		return uniqueKey;
	}
	/**
	 * @param uniqueKey the uniqueKey to set
	 */
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * @return the pMap
	 */
	public Map<String, Object> getpMap() {
		return pMap;
	}
	/**
	 * @param pMap the pMap to set
	 */
	public void setpMap(Map<String, Object> pMap) {
		this.pMap = pMap;
	}
}
