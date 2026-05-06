package com.adtec.pay.utils.statEnum;

/**
 * 交易状态
 */
public enum TranStatEnum {
    /**
     * 预计
     */
    EXPECTED("00", "预计"),
    /**
     * 成功
     */
    SUCCESS("01", "成功"),
    /**
     * 失败
     */
    FAILED("02", "失败"),
    /**
     * 超时
     */
    TIMEOUT("03", "异常"),
    /**
     * 已冲正
     */
    REVERSAL("04", "已冲正");

    private String code;
    private String msg;

    TranStatEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsg(String code) {
        for (TranStatEnum value : values()) {
            if (value.code.equals(code)) {
                return value.msg;
            }
        }
        return code;
    }
}
