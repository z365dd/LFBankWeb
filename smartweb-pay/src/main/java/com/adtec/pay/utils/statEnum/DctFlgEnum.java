package com.adtec.pay.utils.statEnum;

/**
 * 优惠标志
 */
public enum DctFlgEnum {
    /**
     * 有优惠
     */
    HAS_DCT("Y", "有优惠"),
    /**
     * 无优惠
     */
    NO_DCT("N", "无优惠");
    private String code;
    private String msg;

    DctFlgEnum(String code, String msg) {
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
        for (DctFlgEnum value : values()) {
            if (value.code.equals(code)) {
                return value.msg;
            }
        }
        return code;
    }
}
