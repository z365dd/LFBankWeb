package com.adtec.framework.common.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class UUIDGenerator {
	public UUIDGenerator() {

	}

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	
	
	public static int getIUID(){
		String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Integer i = Integer.valueOf("2014051914");
		return i;
	}
	
	public static String getUID(){
		String s = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		SecureRandom random = new SecureRandom();
		int _s = (int) (random.nextDouble() * 99999);
		s=s+_s;
		return s;
	}
}
