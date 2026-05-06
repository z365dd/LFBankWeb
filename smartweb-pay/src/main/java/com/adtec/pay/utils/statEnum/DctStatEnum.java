package com.adtec.pay.utils.statEnum;

public enum DctStatEnum {
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
    TIMEOUT("03", "超时"),
    /**
     * 已冲正
     */
    REVERSAL("04", "已冲正");

    private String code;
    private String msg;

    DctStatEnum(String code, String msg) {
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
        for (DctStatEnum value : values()) {
            if (value.code.equals(code)) {
                return value.msg;
            }
        }
        return code;
    }
}
