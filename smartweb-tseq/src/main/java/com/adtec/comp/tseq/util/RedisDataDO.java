package com.adtec.comp.tseq.util;

import java.util.Map;

public class RedisDataDO {
	private String paramType;
	private String redisKey;
	private String unixKey;
	private String uniqueKey;
	private Map<String, Object> pMap;
	private String key;
	private Object object;
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
