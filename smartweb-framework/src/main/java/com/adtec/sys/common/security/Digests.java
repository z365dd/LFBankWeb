package com.adtec.sys.common.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.Validate;

import com.adtec.sys.common.utils.Encodes;
import com.adtec.sys.common.utils.Exceptions;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 * 
 * 支持AES加密和解密
 * 
 */
public class Digests {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";
	private static final String DEFAULT_ENCODING = "UTF-8";
	public static final String AES_ALGORITHM = "AES";
	public static final String AES_KEY = "U21hcnRXZWJAI0NoZW55bA==";

	private static SecureRandom random = new SecureRandom();

	/**
	 * 对输入字符串进行md5散列.
	 */
	public static byte[] md5(byte[] input) {
		return digest(input, MD5, null, 1);
	}
	public static byte[] md5(byte[] input, int iterations) {
		return digest(input, MD5, null, iterations);
	}
	
	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成随机的Byte[]作为salt.
	 * 
	 * @param numBytes byte数组的大小
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 对文件进行md5散列.
	 */
	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	/**
	 * 对文件进行sha1散列.
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	private static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
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
				String key = Encodes.decodeBase64String(AES_KEY);
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
				String key = Encodes.decodeBase64String(AES_KEY);
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
