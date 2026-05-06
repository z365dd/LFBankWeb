package com.adtec.sys.common.utils;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.sys.seq.PlatSeq;

public class SeqUtil {
	/**
	 * 获取勾兑流水:yyyyMMdd+8位序号
	 * @return
	 */
	public static String getGDSeq(){
		return DateUtil.getDate()+DataUtil.fix0BeforeString(""+PlatSeq.getPltSeq(), 8);
	}
	
	/**
	 * 获取中间业务云平台流水(22位):999000+yyyyMMdd+8位序号
	 * @return
	 */
	public static String getMBCSeq(){
		return SysUtil.MODL_NO+DateUtil.getDate()+DataUtil.fix0BeforeString(""+PlatSeq.getPltSeq(), 8);
	}
}
