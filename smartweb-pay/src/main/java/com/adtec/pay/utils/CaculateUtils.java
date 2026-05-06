package com.adtec.pay.utils;

import java.math.BigDecimal;

/**
 * 进行double类型高精度计算
 */
public class CaculateUtils {

    /**
     * 使用BigDecimal保证精度的double相加运算
     *
     * @param v1
     * @param v2
     * @return v1和v2的加和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 使用BigDecimal保证精度的double相加运算
     *
     * @param v1
     * @param v2
     * @return v1和v2的加和
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
