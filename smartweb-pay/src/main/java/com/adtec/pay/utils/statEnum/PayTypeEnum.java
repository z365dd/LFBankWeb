package com.adtec.pay.utils.statEnum;

public enum PayTypeEnum {


    PayType0("0", "现金"),
    PayType1("1", "转账"),
    PayType2("2", "微信"),
    PayType3("3", "支付宝"),
    PayType4("4", "pos"),
    PayType5("5", "本行卡支付"),
    PayType6("A", "批量扣款");

    private String code;
    private String msg;

    PayTypeEnum(String code, String msg) {
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

    //values()  枚举类的隐式继承方法 将枚举类转化为一个枚举类数组
    public static String getMsg(String code) {
        for (PayTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value.msg;
            }
        }
        return code;
    }
}
