package com.adtec.pay.utils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComUtils {

    public final static DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static enum ErrCode {
        /**
         * 有差错
         */
        NO_ERROR("0", "无差错"),
        /**
         * 无差错
         */
        ERROR("1", "有差错");
        private String code;
        private String msg;

        ErrCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public static String getMsg(String code) {
            for (ErrCode value : ErrCode.values()) {
                if (value.code.equals(code)) {
                    return value.msg;
                }
            }
            return String.format("未知标识[%s]", code);
        }
    }

    public static enum ErrStat {
        /**
         * 有差错
         */
        NO_ERROR("0", "未处理"),
        /**
         * 无差错
         */
        ERROR("1", "已处理");
        private String code;
        private String msg;

        ErrStat(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public static String getMsg(String code) {
            for (ErrStat value : values()) {
                if (value.code.equals(code)) {
                    return value.msg;
                }
            }
            return String.format("未知标识[%s]", code);
        }
    }

    public static enum ChkStat {
        /**
         * 待处理
         */
        NO_HANDLE("00", "待处理"),
        /**
         * 正在处理
         */
        HANDING("01", "正在处理"),
        /**
         * 处理成功
         */
        HANDLE_SUCCESS("02", "处理成功"),
        /**
         * 处理失败
         */
        HANDLE_FAILED("03", "处理失败"),
        /**
         * 处理异常
         */
        HANDLE_ERROR("04", "处理异常");
        private String code;
        private String msg;

        ChkStat(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static String getMsg(String code) {
            for (ChkStat value : ChkStat.values()) {
                if (value.getCode().equals(code)) {
                    return value.msg;
                }
            }
            return String.format("未知标识[%s]", code);
        }
    }

    public static enum TranStat {
        /**
         * 成功
         */
        SUCCESS("01", "成功"),
        /**
         * 失败
         */
        FAILED("02", "失败"),
        /**
         * 异常
         */
        ERROR("03", "异常"),
        /**
         * 已冲正
         */
        REVED("04", "已冲正");
        private String code;
        private String msg;

        TranStat(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static String getMsg(String code) {
            for (TranStat value : TranStat.values()) {
                if (value.code.equals(code)) {
                    return value.msg;
                }
            }
            return String.format("未知标识[%s]", code);
        }

        public static List<Map<String, String>> list() {
            List<Map<String, String>> list = new ArrayList<>();
            for (TranStat value : values()) {
                Map<String, String> map = new HashMap<>();
                map.put("code", value.code);
                map.put("msg", value.msg);
                list.add(map);
            }
            return list;
        }
    }

    public static enum PayStat {
        /**
         * 成功
         */
        SUCCESS("0", "现金"),
        /**
         * 失败
         */
        FAILED("1", "转账");
        private String code;
        private String msg;

        PayStat(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static String getMsg(String code) {
            for (PayStat value : PayStat.values()) {
                if (value.code.equals(code)) {
                    return value.msg;
                }
            }
            return String.format("未知标识[%s]", code);
        }

        public static List<Map<String, String>> list() {
            List<Map<String, String>> list = new ArrayList<>();
            for (PayStat value : values()) {
                Map<String, String> map = new HashMap<>();
                map.put("code", value.code);
                map.put("msg", value.msg);
                list.add(map);
            }
            return list;
        }
    }

}
