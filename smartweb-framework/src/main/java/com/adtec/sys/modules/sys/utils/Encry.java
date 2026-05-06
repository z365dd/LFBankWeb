package com.adtec.sys.modules.sys.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encry {
	
	public static final String KEY = "ADTECES";


	public static byte[] encrypt3DES(final byte[] src, final byte[] key) throws Exception {
		byte[] nullIv = new byte[8];
		SecretKey securekey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec(nullIv);
		cipher.init(1, securekey, iv);
		return cipher.doFinal(src);
	}

	public static byte[] decrypt3DES(final byte[] src, final byte[] key) throws Exception {
		byte[] nullIv = new byte[8];
		SecretKey securekey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec(nullIv);
		cipher.init(2, securekey, iv);
		return cipher.doFinal(src);
	}



	public static String encryString(final String value) {
		return encryString("Y", "ADTECES", value);
	}

	public static String decryptString(final String value) {
		return encryString("N", "ADTECES", value);
	}

	private static String encryString(final String type, final String key, String value) {
		if (value == null || value.equals("")) {
			return value;
		}
		try {
			if (type.equals("Y")) {
				byte[] values = value.getBytes();
				int temp = values.length % 8;
				if (temp != 0) {
					final int num = values.length - temp + 8;
					values = Utility.getBytes(value, num);
				}
				byte[] keys = Utility.getBytes(key, 24);
				byte[] newValue = encrypt3DES(values, keys);
				value = new String(Base64Tool.encode(newValue));
			} else {
				byte[] keys2 = Utility.getBytes(key, 24);
				byte[] newValue2 = decrypt3DES(Base64Tool.decode(value.getBytes()), keys2);
				byte b = 0;
				newValue2 = Utility.removeByte(newValue2, b);
				value = new String(newValue2);
			}
		} catch (IOException e) {
			if (type.equals("Y")) {
				System.out.println("加密失败");
			} else {
				System.out.println("解密失败");
			}
		} catch (Exception e2) {
			if (type.equals("Y")) {
				System.out.println("加密失败");
			} else {
				System.out.println("解密失败");
			}
			e2.printStackTrace();
		}
		return value;
	}
}
