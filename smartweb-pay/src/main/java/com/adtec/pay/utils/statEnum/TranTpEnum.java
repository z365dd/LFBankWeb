package com.adtec.pay.utils.statEnum;

/**
 * 交易类型
 */
public enum TranTpEnum {
    /**
     * 欠费缴费
     */
    ARREARS_PAYMENT("01", "欠费缴费");

    private String code;
    private String msg;

    TranTpEnum(String code, String msg) {
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
        for (TranTpEnum value : values()) {
            if (value.code.equals(code)) {
                return value.msg;
            }
        }
        return code;
    }
}
