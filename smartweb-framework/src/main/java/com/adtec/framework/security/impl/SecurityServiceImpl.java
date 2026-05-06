package com.adtec.framework.security.impl;  

import javax.crypto.Cipher;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.util.encoders.Hex;

import com.adtec.framework.security.SecurityService;


/**  
 * @类名 SecurityServiceImpl.java  
 * @描述: 
 *     PBEWithMD5AndDES深度加密实现类
 * @版本 v1.0  
 */
public class SecurityServiceImpl implements SecurityService {
	public static final String ALGORITHM_PBE = "PBEWithMD5AndDES";	// 对称加密PBE深度加密算法
	public static final String SALT = "LONG_CAS";	//盐值,必为8个字符
	
	/**
	 * PBEWithMD5AndDES解密
	 */
	public String decrypt(byte[] bytes) throws Exception {
		//"authcode-encrypt"为口令
    	PBEKeySpec keySpec = new PBEKeySpec("authcode-encrypt".toCharArray());
    	SecretKeyFactory keyFac = SecretKeyFactory.getInstance(ALGORITHM_PBE);
    	SecretKey key = keyFac.generateSecret(keySpec);

    	//迭代次数为1000
    	PBEParameterSpec ParamSpec = new PBEParameterSpec(SALT.getBytes(), 1000);
    	//加密算法是"PBEWithMD5AndDES"
    	Cipher cipher = Cipher.getInstance(ALGORITHM_PBE);
    	cipher.init(Cipher.DECRYPT_MODE, key, ParamSpec);
		return new String(cipher.doFinal(Hex.decode(bytes)));
	}

	/**
	 * PBEWithMD5AndDES加密
	 */
	public byte[] encrypt(String str) throws Exception {
		PBEKeySpec keySpec = new PBEKeySpec("authcode-encrypt".toCharArray());
		SecretKeyFactory keyFac = SecretKeyFactory.getInstance(ALGORITHM_PBE);
		SecretKey key = keyFac.generateSecret(keySpec);

		//迭代次数为1000
		PBEParameterSpec ParamSpec = new PBEParameterSpec(SALT.getBytes(), 1000);
		//加密算法是"PBEWithMD5AndDES"
		Cipher cipher = Cipher.getInstance(ALGORITHM_PBE);
		cipher.init(Cipher.ENCRYPT_MODE, key, ParamSpec);
		return Hex.encode(cipher.doFinal(str.getBytes()));
	}

	
}
 