/**
 * 系统名称: 微服务框架
 * 模块名称: 加密/解密工具类
 * 类  名  称: EntryptUtil.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年1月9日 下午6:16:40<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.para.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author chenyl
 *
 */
public class ParaEntryptUtil {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	private static final String DEFAULT_ENCODING = "UTF-8";
	public static final String AES_ALGORITHM = "AES";
	public static final String AES_KEY = "U3RhcnJpbmcuQWR1YmJveA==";
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = ParaDigests.generateSalt(SALT_SIZE);
		byte[] hashPassword = ParaDigests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return ParaEncodes.encodeHex(salt)+ParaEncodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = ParaEncodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = ParaDigests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(ParaEncodes.encodeHex(salt)+ParaEncodes.encodeHex(hashPassword));
	}

	/**
	 * 使用AES对字符串加密
	 * 
	 * @param str
	 *            utf8编码的字符串
	 * @param key
	 *            密钥（16字节）
	 * @return 加密结果
	 * @throws Exception
	 */
	public static byte[] aesEncrypt(String str, String key) throws Exception {
		if (str == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(DEFAULT_ENCODING), AES_ALGORITHM));
		byte[] bytes = cipher.doFinal(str.getBytes(DEFAULT_ENCODING));
		return bytes;
	}

	/**
	 * 使用AES对数据解密
	 * 
	 * @param bytes
	 *            utf8编码的二进制数据
	 * @param key
	 *            密钥（16字节）
	 * @return 解密结果
	 * @throws Exception
	 */
	public static String aesDecrypt(byte[] bytes, String key) throws Exception {
		if (bytes == null || key == null)
			return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(DEFAULT_ENCODING), AES_ALGORITHM));
		bytes = cipher.doFinal(bytes);
		return new String(bytes, DEFAULT_ENCODING);
	}
	
	/**
	 * 使用AES对字符串加密
	 * 
	 * @param str
	 *            utf8编码的字符串
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String aesEncrypt(String str) {
		String entryptStr = "";
		try {
			if (null != str && !str.isEmpty()) {
				String key = ParaEncodes.decodeBase64String(AES_KEY);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(DEFAULT_ENCODING), AES_ALGORITHM));
				byte[] bytes = cipher.doFinal(str.getBytes(DEFAULT_ENCODING));
				entryptStr = parseByte2HexStr(bytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entryptStr;
	}

	/**
	 * 使用AES对数据解密
	 * 
	 * @param str
	 *            utf8编码的密文字符串
	 * @return 解密结果
	 * @throws Exception
	 */
	public static String aesDecrypt(String str) {
		String entryptStr = "";
		try {
			if (null != str && !str.isEmpty()) {
				byte[] bytes = parseHexStr2Byte(str);
				String key = ParaEncodes.decodeBase64String(AES_KEY);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(DEFAULT_ENCODING), AES_ALGORITHM));
				bytes = cipher.doFinal(bytes);
				entryptStr = new String(bytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entryptStr;
	}
    
	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
