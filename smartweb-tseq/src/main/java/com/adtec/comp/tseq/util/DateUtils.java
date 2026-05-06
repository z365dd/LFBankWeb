package com.adtec.comp.tseq.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @version 2016-3-1
 */
public class DateUtils {

	/**************************** 时间日历操作 **************************************/

	/**
	 * 根据输入长度格式化时间
	 * 
	 * @param DateString
	 *            时间字符串
	 * @return 格式化时间串
	 */

	public static String FormatDate(String DateString) {
		String ReturnDate = "";
		if (!DataUtil.isNullStr(DateString)) {
			DateString = DateString.trim();
			int len = DateString.length();
			switch (len) {
			case 4:
				ReturnDate = DateString;
				break;
			case 6:
				ReturnDate = DateString.substring(0, 2) + ":"
						+ DateString.substring(2, 4) + ":"
						+ DateString.substring(4, 6);
				break;
			case 8:
				ReturnDate = DateString.substring(0, 4) + "/"
						+ DateString.substring(4, 6) + "/"
						+ DateString.substring(6, 8);
				break;
			case 14:
				ReturnDate = DateString.substring(0, 4) + "/"
						+ DateString.substring(4, 6) + "/"
						+ DateString.substring(6, 8) + " "
						+ DateString.substring(8, 10) + ":"
						+ DateString.substring(10, 12) + ":"
						+ DateString.substring(12, 14);
				break;
			case 15:
				ReturnDate = DateString.substring(0, 4) + "/"
						+ DateString.substring(4, 6) + "/"
						+ DateString.substring(6, 8) + " "
						+ DateString.substring(9, 11) + ":"
						+ DateString.substring(11, 13) + ":"
						+ DateString.substring(13, 15);
				break;
			default:
				ReturnDate = "日期格式有误";
				break;
			}
		} else {
			ReturnDate = "日期为空!";
		}
		return ReturnDate;
	}

	/**
	 * 返回日历的年字符串
	 * 
	 * @param cal
	 *            日历
	 * @return 日历的年字符串
	 */
	public static String getYear(Calendar cal) {
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	/**
	 * 返回日历的月字符串(两位)
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的月字符串(两位)
	 */
	public static String getMonth(Calendar cal) {
		return DataUtil.fix0BeforeString(String
				.valueOf(cal.get(Calendar.MONTH) + 1), 2);
	}

	/**
	 * 返回日历的日字符串(两位)
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的日字符串(两位)
	 */
	public static String getDay(Calendar cal) {
		return DataUtil.fix0BeforeString(String.valueOf(cal
				.get(Calendar.DAY_OF_MONTH)), 2);
	}

	/**
	 * 返回日历的时字符串(两位)
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的时字符串(两位)
	 */
	public static String getHour(Calendar cal) {
		return DataUtil.fix0BeforeString(String.valueOf(cal
				.get(Calendar.HOUR_OF_DAY)), 2);
	}

	/**
	 * 返回日历的分字符串(两位)
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的分字符串(两位)
	 */
	public static String getMinute(Calendar cal) {
		return DataUtil.fix0BeforeString(String.valueOf(cal
				.get(Calendar.MINUTE)), 2);
	}

	/**
	 * 返回日历的秒字符串(两位)
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的秒字符串(两位)
	 */
	public static String getSecond(Calendar cal) {
		return DataUtil.fix0BeforeString(String.valueOf(cal
				.get(Calendar.SECOND)), 2);
	}

	/**
	 * 返回日历的日期字符串（格式："yyyy-mm-dd"）
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的日期字符串（格式："yyyy-mm-dd"）
	 */
	public static String getDateStr(Calendar cal) {
		return getYear(cal) + "-" + getMonth(cal) + "-" + getDay(cal);
	}

	/**
	 * 返回日历的日期字符串（格式："yyyymmdd"）
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的日期字符串（格式："yyyymmdd"）
	 */
	public static String getNumDateStr(Calendar cal) {
		return getYear(cal) + getMonth(cal) + getDay(cal);
	}

	/**
	 * 返回日历的时间字符串（格式："hh:ss:mm"）
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的时间字符串（格式："hh:ss:mm"）
	 */
	public static String getTimeStr(Calendar cal) {
		return getHour(cal) + ":" + getMinute(cal) + ":" + getSecond(cal);
	}

	/**
	 * 返回日历的时间字符串（格式："hhssmm"）
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的时间字符串（格式："hhssmm"）
	 */
	public static String getNumTimeStr(Calendar cal) {
		return getHour(cal) + getMinute(cal) + getSecond(cal);
	}

	/**
	 * 返回日历的日期时间字符串（格式："yyyy-mm-dd hh:ss:mm"）
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的日期时间字符串（格式："yyyy-mm-dd hh:ss:mm"）
	 */
	public static String getDate(Calendar cal) {
		return getDateStr(cal) + " " + getTimeStr(cal);
	}

	/**
	 * 返回日历的日期时间字符串（格式："yyyymmdd hhssmm"）
	 * 
	 * @param cal
	 *            日历
	 * @return 返回日历的日期时间字符串（格式："yyyymmdd hhssmm"）
	 */
	public static String getNumDate(Calendar cal) {
		return getNumDateStr(cal) + " " + getNumTimeStr(cal);
	}

	/**
	 * 取当前日期时间的字符串，格式为"yyyy-mm-dd hh:ss:mm"
	 * 
	 * @return 返回当前日期时间的字符串，格式为"yyyy-mm-dd hh:ss:mm"
	 */
	public static String getNow() {
		Calendar now = Calendar.getInstance();
		return getDateStr(now) + " " + getTimeStr(now);
	}

	/**
	 * 取当前日期时间的字符串，格式为"yyyymmdd hhssmm"
	 * 
	 * @return 返回当前日期时间的字符串，格式为"yyyymmdd hhssmm"
	 */
	public static String getNumNow() {
		Calendar now = Calendar.getInstance();
		return getNumDateStr(now) + " " + getNumTimeStr(now);
	}

	/**
	 * 取当前日期的字符串，格式为"yyyy-mm-dd"
	 * 
	 * @return 返回当前日期的字符串，格式为"yyyy-mm-dd"
	 */
	public static String getNowDate() {
		Calendar now = Calendar.getInstance();
		return getDateStr(now);
	}

	/**
	 * 取当前日期的字符串，格式为"yyyymmdd"
	 * 
	 * @return 返回当前日期的字符串，格式为"yyyymmdd"
	 */
	public static String getNumNowDate() {
		Calendar now = Calendar.getInstance();
		return getNumDateStr(now);
	}

	/**
	 * 取当前时间的字符串，格式为"hh:ss:mm"
	 * 
	 * @return 返回当前时间的字符串，格式为"hh:ss:mm"
	 */
	public static String getNowTime() {
		Calendar now = Calendar.getInstance();
		return getTimeStr(now);
	}

	/**
	 * 取当前时间的字符串，格式为"hhssmm"
	 * 
	 * @return 返回当前时间的字符串，格式为"hhssmm"
	 */
	public static String getNumNowTime() {
		Calendar now = Calendar.getInstance();
		return getNumTimeStr(now);
	}
	
	/**
	 * 获取当前日期。 <br>
	 * 获取的日期格式为yyyyMMdd
	 * 
	 * @return String - 返回当前日期
	 */
	public static String getDate() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String dateString = dataFormat.format(date);
		return dateString;
	}
	
	/**
	 * 获取当前时间。 <br>
	 * 获取的时间的格式为6位(HHmmss)
	 * @return String - 返回当前时间
	 */
	public static String getTime() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String timeString = dataFormat.format(date);
		return timeString;
	}

}
