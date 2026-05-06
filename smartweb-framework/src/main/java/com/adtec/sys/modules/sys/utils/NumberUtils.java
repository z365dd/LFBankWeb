/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: NumberUtils.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年12月26日 下午2:35:10<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.sys.utils;

import java.text.DecimalFormat;

/**
 * @author pc
 *
 */
public class NumberUtils {

	public final static String[] chineseDigits = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	/**
	 * 去掉前缀0
	 * 
	 * @param number
	 * @return
	 */
	public static String removePrefixZero(String number) {
		String result = number;
		if (result != null) {
			if (result.startsWith("0")) {
				if (result.length() > 1 && result.charAt(1) != '.') {
					result = result.substring(1);
					result = removePrefixZero(result);
				}
			}
		}
		return result;
	}

	/**
	 * 数字金额转换成中文金额
	 * 
	 * @param samount
	 * @return
	 */
	public static String amountToChinese(String samount) {
		if ("".equals(samount))
			return "";
		else {
			Double amount = Double.parseDouble(samount);
			return amountToChinese(amount);
		}
	}

	/**
	 * 把金额转换为汉字表示的数量，小数点后四舍五入保留两位
	 * 
	 * @param amount
	 * @return
	 */
	public static String amountToChinese(double amount) {
		String chineseStr = ""; // 中文字符串
		boolean negative = false; // 正负数标志
		boolean beforeWanIsZero = false; // 标志“千元”级是否为0，默认不为0

		if (amount > 99999999999999.99 || amount < -99999999999999.99)
			throw new IllegalArgumentException("参数值超出允许范围 (-99999999999999.99 ～ 99999999999999.99)！");

		if (amount < 0) {
			negative = true;
			amount = amount * (-1);
		}

		long temp = Math.round(amount * 100);
		int numFen = (int) (temp % 10); // 分
		temp = temp / 10;
		int numJiao = (int) (temp % 10); // 角
		temp = temp / 10;
		// temp 目前是金额的整数部分

		int[] parts = new int[20]; // 其中的元素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分
		int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）
		for (int i = 0;; i++) {
			if (temp == 0)
				break;
			int part = (int) (temp % 10000);
			parts[i] = part;
			numParts++;
			temp = temp / 10000;
		}

		for (int i = 0; i < numParts; i++) {
			// 判断是否第一节数据，是则不用补0
			boolean HeadFlag = true;
			if (i == numParts - 1)
				HeadFlag = false;

			String partChinese = partTranslate(parts[i], HeadFlag);
			if (i % 2 == 0) {
				if ("".equals(partChinese))
					beforeWanIsZero = true;
			}

			if (i != 0) {
				if (i == 2)
					chineseStr = "亿" + chineseStr;
				else {
					if (!"".equals(partChinese))// 先判断“万”是否需要生成
						chineseStr = "万" + chineseStr;
					else if (!beforeWanIsZero && !HeadFlag)// 如果不需要生成“万”再判断是否要加上“零”
					{
						chineseStr = "零" + chineseStr;
					}
				}
			}
			chineseStr = partChinese + chineseStr;
		}

		if ("".equals(chineseStr)) // 整数部分为 0, 则表达为"零元"
			chineseStr = chineseDigits[0];
		else if (negative) // 整数部分不为 0, 并且原金额为负数
			chineseStr = "负" + chineseStr;

		chineseStr = chineseStr + "元";

		if (numFen == 0 && numJiao == 0) {
			chineseStr = chineseStr + "整";
		} else if (numFen == 0) { // 0 分，角数不为 0
			chineseStr = chineseStr + chineseDigits[numJiao] + "角";
		} else { // “分”数不为 0
			if (numJiao == 0)
				chineseStr = chineseStr + "零" + chineseDigits[numFen] + "分";
			else
				chineseStr = chineseStr + chineseDigits[numJiao] + "角" + chineseDigits[numFen] + "分";
		}

		return chineseStr;

	}

	/**
	 * 把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 ""
	 * 
	 * @param amountPart
	 * @return
	 */
	// 由于参数amountPart传入类型是int，使得这个参数会在比如900比如90的时候会丢失前面的0
	private static String partTranslate(int amountPart, boolean flag) {
		if (amountPart < 0 || amountPart > 10000) {
			throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");
		}

		String[] units = new String[] { "", "拾", "佰", "仟" };

		int temp = amountPart;

		String amountStr = new Integer(amountPart).toString();
		int amountStrLength = amountStr.length();
		boolean lastIsZero = true; // 在从低位往高位循环时，记录上一位数字是不是 0
		String chineseStr = "";

		for (int i = 0; i < amountStrLength; i++) {
			if (temp == 0) // 高位已无数据
				break;
			int digit = temp % 10;

			if (digit == 0) { // 取到的数字为 0
				if (!lastIsZero) // 前一个数字不是 0，则在当前汉字串前加“零”字;
				{
					chineseStr = "零" + chineseStr;
				}
				lastIsZero = true;
			} else { // 取到的数字不是 0
				chineseStr = chineseDigits[digit] + units[i] + chineseStr;
				lastIsZero = false;
			}
			temp = temp / 10;
		}

		// 遇到小于1000再补一个0
		if (amountPart < 1000 && amountPart != 0 && flag == true) {
			chineseStr = "零" + chineseStr;
		}
		return chineseStr;
	}

	/**
	 * 转换成只有小数点后2位后缀的字符串
	 * 
	 * @param amountPart
	 * @return
	 */
	public static String numberFormat(Double value) {
		DecimalFormat format1 = new DecimalFormat("0.00");
		return format1.format(value);
	}

	public static String numberFormat(String value) {
		if ("".equals(value))
			return "";
		else
			return numberFormat(Double.valueOf(value));
	}
	
	public static String numberFormat3Bit(String value) {
		if ("".equals(value))
			return "";
		else {
			DecimalFormat format1 = new DecimalFormat("0.000");
			return format1.format(Double.valueOf(value));
		}
	}
	
	public static String numberFormat(Double value, String fm) {
		DecimalFormat format1 = new DecimalFormat(fm);
		return format1.format(value);
	}

	/**
	 * 转换成带金额分隔符的字符串,并限定只有小数点后2位
	 * 
	 * @param amountPart
	 * @return
	 */
	public static String moneyFormat(double amount) {
		DecimalFormat format1 = new DecimalFormat("###,##0.00");
		return format1.format(amount);
	}

}
