package com.adtec.framework.security;  

/**  
 * @类名 SecurityService.java  
 * @描述: 
 *     安全服务接口类
 * @版本 v1.0  
 */
public interface SecurityService {
	
	/**
	 * 加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public byte [] encrypt(String str) throws Exception;
	
	/**
	 * 解密
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public String decrypt(byte [] bytes) throws Exception;
}
 