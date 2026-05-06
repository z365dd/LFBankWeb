package com.adtec.sys.modules.sys.utils;

public class Utility {
	
	public static String byte2hex(final byte[] b) {
		final StringBuffer hexBuf = new StringBuffer();
		for (int n = 0; n < b.length; ++n) {
			hexBuf.append(String.format("%02X", b[n] & 0xFF));
		}
		return hexBuf.toString();
	}

	public static byte[] hex2byte(final String str) {
		if (str == null || str.length() <= 0) {
			return null;
		}
		final byte[] buf = new byte[str.length() / 2];
		for (int i = 0; i < buf.length; ++i) {
			final String sub = str.substring(i * 2, (i + 1) * 2);
			buf[i] = Byte.parseByte(sub, 16);
		}
		return buf;
	}

	public static byte[] hex2byte(final byte[] b) {
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		final byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			final String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static byte[] removeByte(final byte[] b, final byte c) {
		int i;
		for (i = b.length; i > 0 && b[i - 1] == c; --i) {}
		final byte[] bytes = new byte[i];
		for (int j = 0; j < i; ++j) {
			bytes[j] = b[j];
		}
		return bytes;
	}

	public static void setByteArray(final byte[] buf, final char c) {
		for (int i = 0; i < buf.length; ++i) {
			buf[i] = (byte) c;
		}
	}

	public static byte[] getBytes(final String str, final int num) {
		final byte[] bytes = new byte[num];
		final byte[] value = str.getBytes();
		for (int i = 0; i < num; ++i) {
			if (i < value.length) {
				bytes[i] = value[i];
			} else {
				bytes[i] = 0;
			}
		}
		return bytes;
	}

	public static byte[] hexString2Bytes(final String src) {
		final int l = src.length() / 2;
		final byte[] ret = new byte[l];
		for (int i = 0; i < l; ++i) {
			ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}
}
