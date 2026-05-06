package com.adtec.pay.utils;
import java.math.BigDecimal;

public class AmountToChinese {

    // 数字对应的汉字
    private static final String[] CN_NUMERIC = {
            "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"
    };

    // 整数部分的单位
    private static final String[] CN_INTEGER_UNIT = {
            "", "拾", "佰", "仟"
    };

    // 整数部分的大单位
    private static final String[] CN_INTEGER_BIG_UNIT = {
            "", "万", "亿", "万亿"
    };

    // 小数部分的单位
    private static final String[] CN_DECIMAL_UNIT = {
            "角", "分", "厘"
    };

    // 将金额转换为汉字大写
    public static String convertToChinese(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return "零";
        }

        StringBuilder sb = new StringBuilder();
        String amountStr = amount.toString();
        int integerPartLength = amountStr.indexOf(".");
        if(integerPartLength == -1){
            amountStr += ".00";
        }
        integerPartLength = amountStr.indexOf(".");
        String integerPart = amountStr.substring(0, integerPartLength);
        String decimalPart = amountStr.substring(integerPartLength + 1);

        // 处理整数部分
        int integerPartIndex = integerPart.length();
        int unitIndex = 0; // 记录单位的索引位置
        boolean zeroFlag = false; // 是否需要在整数部分加上零
        for (int i = 0; i < integerPart.length(); i++) {
            int digit = integerPart.charAt(i) - '0';
            unitIndex = integerPartIndex - i - 1;

            if (digit == 0) {
                zeroFlag = true;
            } else {
                if (zeroFlag) {
                    sb.append(CN_NUMERIC[0]); // 插入零
                    zeroFlag = false;
                }
                sb.append(CN_NUMERIC[digit]).append(CN_INTEGER_UNIT[unitIndex % 4]);
            }
            if (unitIndex % 4 == 0 && unitIndex > 0) { // 处理万、亿等大单位
                sb.append(CN_INTEGER_BIG_UNIT[unitIndex / 4]);
            }
        }
        if(!(integerPart.length()==1 && integerPart.charAt(integerPart.length()-1) == '0')){
            sb.append("元");
        }
        if(decimalPart.equals("00")){
            sb.append("整");
        }
        // 处理小数部分
        for (int i = 0; i < decimalPart.length(); i++) {
            int digit = decimalPart.charAt(i) - '0';
            if (digit != 0) {
                sb.append(CN_NUMERIC[digit]).append(CN_DECIMAL_UNIT[i]);
            }
        }

        return sb.toString();
    }

}

