package com.adtec.pay.utils.statEnum;

/**
 * 存放常用修改枚举类
 */
public enum ModifyCode {
    PAY_TIME_ADD("1", "新增缴费时间规则"),
    PAY_TIME_DELETE("2", "删除缴费时间规则"),
    PAY_TIME_MODIFY("3", "修改缴费时间规则");

    private String code;
    private String msg;

    ModifyCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(String code) {
        for (ModifyCode value : ModifyCode.values()) {
            if (value.code.equals(code)) {
                return value.msg + "成功";
            }
        }
        return "无法执行该操作类型:" + code;
    }
}
