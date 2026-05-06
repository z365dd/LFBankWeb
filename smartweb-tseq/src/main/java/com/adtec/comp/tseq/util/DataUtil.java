
package com.adtec.comp.tseq.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class DataUtil {

    /**
     * 字符串含中文截位，每个中文汉字按两个字节计算，其他可见字符按一个字节计算
     * 在遇到截位时候碰到半个汉字的时候，舍弃半个汉字对应的字节防止插入数据库乱码
     * @param str         源字符串
     * @param maxByteLen  截位后的字符串最大长度
     * @return
     */
	public static String subString(String str, final int maxByteLen) {
		int len=maxByteLen;
		if (str == null || maxByteLen <= 0) {
			return "";
		}
		byte[] bStr;
		try {
			bStr = str.getBytes("GBK");  //字节流
			if (maxByteLen >= bStr.length) {
				return str;
			}
			String cStr = new String(bStr, maxByteLen - 1, 2,"GBK");
			if (cStr.length() == 1 && str.contains(cStr)) {
				len--;
			}
			return new String(bStr, 0, len,"GBK");  //重新组字符串
		} catch (UnsupportedEncodingException e) {
			throw new java.lang.RuntimeException("substring fail ");
		}
	}
    /**
	 * 截取指定长度的中文字符
	 * cnSubstr("我是中国人",4,"") 结果为 "我是"
	 * cnSubstr("我是中国人",7,"") 结果为 "我是中","国"字因为占两个字节,所以省略
	 * cnSubstr("我是中国人",7,"...") 结果为 "我是中...",省略"国"字并加上后缀
	 * @param str 字符串
	 * @param length 指定长度,以字节数为单位
	 * @param tail 当字符串长度超过指定长度时加上的后缀
	 * @return
	 */
	public static String cnSubstr(String str, int length, String tail)
	{
		String result = null;
		try {
			int pos = 0;
			int end = 0;
			char[] strChars = str.toCharArray();
			int strLength = strChars.length;
			for (int i = 0; i < strLength; i++, end++) {
				int ascii = strChars[i];
				if (ascii > 255)
					pos += 2;
				else
					pos++;
				if (pos > length)
					break;
			}
			result = (end < strLength) ? (str.substring(0, end) + tail) : (str);
		} catch (Exception e) {
			System.out.println("操作失败");
		}
		return result;
	}
	 /**
	 * 截取指定长度的中文字符,及剩余字符串放入数组0:截取字符串，1剩余字符串
	 * @param str 字符串
	 * @param length 截取长度
	 * @param tail 当字符串长度超过指定长度时加上的后缀
	 * @param newString 截取后余下的字符串
	 */
	public static String[] cnSubstrs(String str, int length, String tail){
		String[] result = new String[2];
		result[0] = cnSubstr(str, length, tail);
		result[1] = str.substring(result[0].length());
		return result;
	}
	/**
	 * IBM  AS400 上使用的EBDCDIC码 转换成 ascii码
	 * @param input
	 * @return ascii码字节数组
	 */
	public static byte[] ebcdic2ascii(byte[] ebcdic){
		 
		try {
			return (new String(ebcdic,"cp935")).getBytes();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	/**
	 * IBM  AS400 上使用的EBDCDIC码 转换成 ascii码
	 * @param input  被格式化成本机字符集后的字符串
	 * @return  ascii码字符串
	 */
	public static String ebcdic2ascii(String ebcdic){
		 
		try {
			return new String(ebcdic.getBytes(),"cp935");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	/**
	 * ascii码转换成IBM  AS400 上使用的EBDCDIC码
	 * @param input
	 * @return
	 */
	public static byte[] ascii2ebcdic(byte[] ascii){
		try {
			return (new String(ascii)).getBytes("cp935");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	/**
	 * ascii码转换成IBM  AS400 上使用的EBDCDIC码
	 * @param ascii  被格式化成本机字符集后的字符串
	 * @return
	 */
	public static String ascii2ebcdic(String ascii){
		try {
			return new String(ascii.getBytes("cp935"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null; 
	}

	/**
	 * 右边去空格函数
	 * 
	 * @param value
	 * @return
	 */
	public static String rTrim(String value) {
		if (value == null || value.equals("")) {
			return value;
		}
		byte[] valueBytes = value.getBytes();
		int len = valueBytes.length;
		for (int i = valueBytes.length - 1; i >= 0 && valueBytes[i] == ' '; i--) {
			// valueBytes[i]='\0';
			len--;
		}
		return new String(valueBytes, 0, len);
	}

	/**
	 * 左边去空格函数
	 * 
	 * @param value
	 * @return
	 */
	public static String lTrim(String value) {
		if (value == null || value.equals("")) {
			return value;
		}
		byte[] valueBytes = value.getBytes();
		int offset = 0;
		for (int i = 0; i < valueBytes.length && valueBytes[i] == ' '; i++) {
			offset++;
		}

		if (offset > 0)
			return new String(valueBytes, offset, valueBytes.length - offset);

		return value;
	}

	/**
	 * 金融数字转换成普通数字
	 * 
	 * @param data
	 *            -要转换的金融数字字符串
	 * @return -返回转换后的正常数字字符串
	 */
	public static String finalToNormal(String data) {
		String newData = data;
		int index = newData.indexOf(',');
		while (index != -1) {
			newData = newData.substring(0, index)
					+ newData.substring(index + 1);
			index = newData.indexOf(',');
		}

		return newData;
	}

	/**
	 * 普通数字型转换成金融记数法。 <br>
	 * 例如 132000123.00 转换后为 132,000,123.00
	 * 
	 * @param data
	 *            - 要转换的数字字符串
	 * @return String - 返回转换后的数字字符串
	 */
	public static String normalToFinal(String data) {

		if (data == null || data.length() == 0)
			return data;
		//20120320 xialiang 若是科学技术法，则转换普通数字
		if(data.toUpperCase().indexOf("E")>0){
			BigDecimal bd = new BigDecimal(data); 
			return normalToFinal(bd.toPlainString());
		}
		int pos = data.lastIndexOf('.');
		int len = 2; // 小数位数
		if (pos != -1) {
			len = data.length() - 1 - pos;
			if(len < 2) {//若小数位小于2位,自动延长至2位 20130802 yangxi
				len = 2;
			}
		}
		try {
			double d = Double.parseDouble(data);
			NumberFormat form = NumberFormat.getInstance();
			String mask = "#,##0";
			if (len > 0) {
				mask = "#,##0.";
				for (int i = 0; i < len; i++) {
					mask = mask + "0";
				}
			}
			((DecimalFormat) form).applyPattern(mask);
			return form.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 普通数字型转换成金融记数法。 <br>
	 * 例如 132000123.00 转换后为 132,000,123.00 <br>
	 * 小数点保留两位
	 * 
	 * @param data
	 *            - 要转换的数字
	 * @return String - 返回转换后的数字字符串
	 */
	public static String normalToFinal(double data) {
		try {
			//20120301 xialiang 该方法为重载函数：格式转换统一调用normalToFinal(String data)
			String dd = String.valueOf(data);
			if(dd.toUpperCase().indexOf("E")>0){
				//若是科学技术法，则先转换普通数字
				BigDecimal bd = new BigDecimal(dd); 
				return normalToFinal(bd.toPlainString());
			}else{
				return normalToFinal(dd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化浮点值为金融字符串型, 指定小数位数长度。（历史遗留原因已经被瞎改了） <br>
	 * 例如 132000123.000000 转换成两位小数点后为 "132,000,123.00"
	 * 
	 * @param data
	 *            - 要转换的浮点数
	 * @param len
	 *            - 保留小数位数
	 * @return String - 返回转换后的数字字符串
	 */
	public static String formatDouble(double data, int len) {
		String ret = null;
		try {
			NumberFormat form = NumberFormat.getInstance();
			// 增加 一个配置参数用于兼容2.0系统
			String mask = "###0";
			if (len > 0) {
				mask+=".";
				for (int i = 0; i < len; i++) {
					mask = mask + "0";
				}
			}
			((DecimalFormat) form).applyPattern(mask);
			ret = form.format(data);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
		}
		return ret;
	}

	/**
	 * 格式化浮点值为字符串型, 默认小数位数长度为二。 <br>
	 * 例如 132000123.000000 转换成后为 "132000123.00"
	 * 
	 * @param data
	 *            - 要转换的浮点数
	 * @return String - 返回转换后的数字字符串
	 */
	public static String formatDouble(double data) {
		String ret = null;
		try {
			NumberFormat form = NumberFormat.getInstance();
			String mask = "###0.00";
			((DecimalFormat) form).applyPattern(mask);
			ret = form.format(data);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
		}
		return ret;
	}

	/**
	 * 格式化科学记数的值为普通数字的字符串型, 默认小数位数长度为二。 <br>
	 * 例如 123.10E3 转换成后为 "132000123.00"
	 * 
	 * @param data
	 *            - 要转换的浮点数
	 * @return String - 返回转换后的数字字符串
	 */
	public static String expToNormal(String data) {
		String ret = null;
		try {
			double d = Double.parseDouble(data);
			NumberFormat form = NumberFormat.getInstance();
			String mask = "###0.00";
			((DecimalFormat) form).applyPattern(mask);
			ret = form.format(d);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	/**
	 * 格式化科学记数的值为普通数字的字符串型,指定小数位数长度。 <br>
	 * 例如 123.10E3 转换成后为 "132000123.00"
	 * 
	 * @param data
	 *            - 要转换的浮点数
	 * @param len
	 *            - 小数位数
	 * @return String - 返回转换后的数字字符串
	 */
	public static String expToNormal(String data, int len) {
		String ret = null;
		try {
			double d = Double.parseDouble(data);
			NumberFormat form = NumberFormat.getInstance();
			String mask = "###0";
			if (len > 0) {
				mask = "###0.";
				for (int i = 0; i < len; i++) {
					mask = mask + "0";
				}
			}
			((DecimalFormat) form).applyPattern(mask);
			ret = form.format(d);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}
	
	/**
	 * 基础数据类型转化  Object转Double
	 * @param str
	 * @return
	 */
	public static double formatDouble(Object obj){
		if(null != obj){
			return Double.parseDouble(obj+"");
		}
		return 0 ;
	}
	
	/**
	 * 基础数据类型转化  Object转Double
	 * @param str
	 * @return
	 */
	public static double formatBigDecimalDouble(Object obj){
		if(null != obj){
			BigDecimal bigDecimal = new BigDecimal(obj+"");
			return bigDecimal.doubleValue();
		}
		return 0 ;
	}
	
	
	/**
	 * 基础数据类型转化  Object转Double
	 * @param str
	 * @return
	 */
	public static double formatDouble(String obj){
		if(null != obj && !"".equals(obj)){
			return Double.parseDouble(obj+"");
		}
		return 0 ;
	}
	/**
	 * 基础数据类型转化  Object转Long
	 * @param str
	 * @return
	 */
	public static Long formatLong(Object obj){
		if(null != obj){
			return Long.parseLong(obj+"");
		}
		return 0L;
	}
	/**
	 * 基础数据类型转化  String转Long
	 * @param str
	 * @return
	 */
	public static Long formatLong(String obj){
		if(null != obj && !"".equals(obj)){
			return Long.parseLong(obj+"");
		}
		return 0L;
	}
	/**
	 * 基础数据类型转化  Object转String
	 * @param str
	 * @return
	 */
	public static String formatString(Object obj){
		if(null != obj){
			return obj+"";
		}
		return "";
	}
	/**
	 * 基础数据类型转化  Object转Float
	 * @param str
	 * @return
	 */
	public static float formatFloat(Object obj){
		if(null != obj){
			return Float.parseFloat(obj+"");
		}
		return 0 ;
	}
	/**
	 * 基础数据类型转化  String转Float
	 * @param str
	 * @return
	 */
	public static float formatFloat(String obj){
		if(null != obj && !"".equals(obj)){
			return Float.parseFloat(obj+"");
		}
		return 0 ;
	}
	/**
	 * 基础数据类型转化  Object转Int
	 * @param str
	 * @return
	 */
	public static int formatInt(Object obj){
		if(null != obj){
			return Integer.parseInt(obj+"");
		}
		return 0 ;
	}
	
	/**
	 * 基础数据类型转化  String转Int
	 * @param str
	 * @return
	 */
	public static int formatInt(String obj){
		if(null != obj && !"".equals(obj)){
			return Integer.parseInt(obj+"");
		}
		return 0 ;
	}


	/************************** 字符串操作 ***********************************/

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return true为空 false 不为空
	 */
	public static boolean isNullStr(String s) {
		if (s == null || s.trim().length() <= 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断字符串数组是否为空
	 * 
	 * @param s
	 *            字符串数组
	 * @return true为空 false 不为空
	 */

	public static boolean isNullStr(String[] s) {
		if ((s == null) || (s.length <= 0))
			return true;
		else
			return false;
	}

	/**
	 * 按长度把字符串前补0
	 * 
	 * @param s
	 *            需要前补0的字符串
	 * @param len
	 *            生成后的字符串长度
	 * @return len长度前补0的字符串
	 */
	public static String fix0BeforeString(String s, int len) {
		if (isNullStr(s))
			s = "";
		for (int i = len - s.length(); i > 0; i--) {
			s = "0" + s;
		}
		return s;
	}
	
	/**
	 * 按长度把字符串前补0
	 * 
	 * @param s
	 *            需要前补0的字符串
	 * @param len
	 *            生成后的字符串长度
	 * @param flag   true如果首字母为负号，负号后面开始补0           
	 * @return len长度前补0的字符串
	 */
	public static String fix0BeforeString(String s, int len,boolean flag) {
		if (isNullStr(s))
			s = "";

		if (flag && (s.substring(0, 1).equals("+") || s.substring(0, 1).equals("-"))) {
			String temp = "";
			temp += s.substring(0, 1);
			s = s.substring(1);
			for (int i = len - 1 - s.length(); i > 0; i--) {
				temp = temp + "0";
			}
			temp += s;
			return temp;

		} else {

			for (int i = len - s.length(); i > 0; i--) {
				s = "0" + s;
			}
			return s;

		}

	}


	/**
	 * 按长度把字符串后补0
	 * 
	 * @param s
	 *            需要后补0的字符串
	 * @param len
	 *            生成后的字符串长度
	 * @return len长度后补0的字符串
	 */
	public static String fix0AfterString(String s, int len) {
		if (isNullStr(s))
			s = "";
		for (int i = len - s.length(); i > 0; i--) {
			s = s + "0";
		}
		return s;
	}

	/**
	 * 返回前补0的固定长度字符串
	 * 
	 * @param num
	 *            long 数字
	 * @param Fix
	 *            int 补0后总共长度
	 * @return String 返回前补0的固定长度字符串
	 */
	public static String fix0BeforeNumber(long num, int Fix) {
	     return fix0BeforeNumber(num,Fix,true);	
	}
	
	
	/**
	 * 返回前补0的固定长度字符串
	 * 
	 * @param num
	 *            long 数字
	 * @param Fix
	 *            int 补0后总共长度
	 * @param flag 如果为true，long为负数。从负号后面开始补0           
	 * @return String 返回前补0的固定长度字符串
	 */
	public static String fix0BeforeNumber(long num, int Fix,boolean flag) {
		String retstr = "" +(flag==true? Math.abs(num):num);
		int size = retstr.length();
        
		if(flag && num<0 ){
			size=size+1;
		}
		for (int i = 0; i < (Fix - size); i++) {
			retstr = "0" + retstr;
		}
		
        if(flag && num<0){
        	retstr="-"+retstr;
        }
		
		return retstr;
	}


	/**
	 * 后补空格
	 * 
	 * @param in
	 *            String 已有字符串
	 * @param len
	 *            int 补空格后长度
	 * @return String 如果原字符串长度>=len，返回原字符串；否则后补空格后的字符串
	 */
	public static String fixSpaceAfterString(String in, int len) {
		String out = "";

		if (in == null) {
			for (int i = 0; i < len; i++) {
				out += " ";
			}
			return out;
		}
		out = in;

		if (in.length() >= len)
			return out;
		else
			for (int i = 0; i < len - in.length(); i++) {
				out += " ";
			}

		return out;
	}

	/**
	 * 后补空格
	 * 
	 * @param in
	 *            String 已有字符串
	 * @param len
	 *            int 补空格后长度
	 * @return String 如果原字符串长度>=len，返回原字符串；否则后补空格后的字符串
	 */
	public static String fixSpaceAfterStringByByte(String in, int len) {
		String out = "";
		if (in == null) {
			for (int i = 0; i < len; i++) {
				out += " ";
			}
			return out;
		}
		out = in;
		if (length(in) >= len)
			return out;
		else
			for (int i = 0; i < len - length(in); i++) {
				out += " ";
			}
		return out;
	}

	/**
	 * 后补空格
	 * 
	 * @param in
	 *            String 已有字符串
	 * @param len
	 *            int 补空格后长度
	 * @return String 如果原字符串长度>=len，返回原字符串；否则后补空格后的字符串
	 */
	public static String fixSpaceBeforeStringByByte(String in, int len) {
		String out = "";
		if (in == null) {
			for (int i = 0; i < len; i++) {
				out += " ";
			}
			return out;
		}
		out = in;
		if (length(in) >= len)
			return out;
		else
			for (int i = 0; i < len - length(in); i++) {
				out = " " + out;
			}
		return out;
	}
	

	/**
	 * 计算字符串长度，按中文编码方式计算(一个中文按两个字节计算)
	 * 
	 * @param src
	 *            需要计算长度的字符串
	 * @return 字符串长度
	 */
	public static int length(String src) {
		int length = -1;
		try {
			length = src.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}

	/**
	 * 文件目录格式转换，格式为： dir/
	 * 
	 * @param dir 文件目录
	 * @return String 转换后值
	 * 
	 */
	public static String getDirFormat(String dir){
		String tmpDir ;
		if(dir == null || File.separator.equals(dir) || "".equals(dir)){
			return "";
		}
		if(dir.startsWith(File.separator)){
			tmpDir = dir.substring(1);
		}else{
			tmpDir = dir;
		}
		if(!tmpDir.endsWith(File.separator)){
			tmpDir = tmpDir + File.separator;
		}
		return tmpDir;
	}

}
