package com.adtec.sys.modules.sys.utils;

import org.apache.commons.codec.binary.*;

public class Base64Tool {
	
	public static byte[] decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}

	public static byte[] encode(final byte[] bytes) {
		return Base64.encodeBase64(bytes);
	}

	public static byte[] hex2byte(final String str) {
		if (str == null || str.length() <= 0) {
			return null;
		}
		final String digtal = "0123456789ABCDEF";
		final char[] hex2char = str.toCharArray();
		final byte[] buf = new byte[str.length() / 2];
		for (int i = 0; i < buf.length; ++i) {
			int temp = digtal.indexOf(hex2char[2 * i]) * 16;
			temp += digtal.indexOf(hex2char[2 * i + 1]);
			buf[i] = (byte) (temp & 0xFF);
		}
		return buf;
	}
}
