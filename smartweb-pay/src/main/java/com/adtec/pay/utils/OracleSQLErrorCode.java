package com.adtec.pay.utils;

public enum OracleSQLErrorCode {

    ERROR_ORA_00001("ORA-00001", "数据重复"),
    ERROR_ORA_00904("ORA-00904", "存在无效的列或者表"),
    ERROR_ORA_01017("ORA-01017", "登陆数据库被拒"),
    ERROR_ORA_01034("ORA-01034", "Oracle不可用"),
    ERROR_ORA_01722("ORA-01722", "操作数据库，期望值类型错误"),
    ERROR_ORA_01400("ORA-01400", "列操作时，无法将null更新或者插入到表中");

    private String code;
    private String msg;

    OracleSQLErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(String code) {
        for (OracleSQLErrorCode value : OracleSQLErrorCode.values()) {
            if (value.code.equals(code)) {
                return value.msg;
            }
        }
        return "数据库操作失败，错误码：" + code;
    }
}
