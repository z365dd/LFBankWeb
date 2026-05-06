/**
 * 系统名称: SmartWeb平台
 * 模块名称: RSA加解密工具类
 * 类  名  称: RSAUtils.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年12月6日 下午2:38:16<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.common.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

/**
 * @author chenyl
 *
 */
public class RSAUtils {
	private static final String PUB_KEY = "PUB_KEY";
	
	static{
		initKey();
	}
	
	/**
	 * 初始化生成公钥/私钥对
	 * @return
	 */
    private static void initKey() {
        try {
            Provider provider =new org.bouncycastle.jce.provider.BouncyCastleProvider();
            Security.addProvider(provider);
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
            generator.initialize(1024,random);
            /*20211126 mod by chenyl for 保存到缓存中*/
            KeyPair keyPair = generator.generateKeyPair();
            CacheUtil.put(PUB_KEY, keyPair);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 获取Base64编码的公钥
     * @return
     */
    public static String generateBase64PublicKey() {
    	KeyPair keyPair = (KeyPair) CacheUtil.get(PUB_KEY);
        PublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        return new String(Base64.encodeBase64(publicKey.getEncoded()));
    }
    
    /**
     * 使用公钥加密并转出Base64编码
     * @param string	明文
     * @return	加密后的Base64编码字符
     */
    public static String encryptBase64(String string){
    	return new String(Base64.encodeBase64(encrypt(string.getBytes())));    	
    }
    
    /**
     * 使用私钥解密
     * @param byteArray	Base64编码密文字节数组
     * @return	Base64编码明文
     */
    private static byte[] encrypt(byte[] byteArray) {
        try {
            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
            Security.addProvider(provider);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            KeyPair keyPair = (KeyPair) CacheUtil.get(PUB_KEY);
            PublicKey publicKey = keyPair.getPublic();
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 对通过RSA的公钥加密后的Base64编码的密文进行解密
     * @param string	前端送过来的密文
     * @return	明文
     */
    public static String decryptBase64(String string) {
        return new String(decrypt(Base64.decodeBase64(string.getBytes())));
    }
    
    /**
     * 使用私钥解密
     * @param byteArray	Base64编码密文字节数组
     * @return	Base64编码明文
     */
    private static byte[] decrypt(byte[] byteArray) {
        try {
            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
            Security.addProvider(provider);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            KeyPair keyPair = (KeyPair) CacheUtil.get(PUB_KEY);
            PrivateKey privateKey = keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch(Exception e) {
            throw new BaseException(SysErr.E_DEFAULT, "RSA解密异常！");
        }
    }
}
