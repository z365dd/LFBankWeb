package com.adtec.pay.dto.paytime;

public class PayTimeQryListRes {
    /**
     * 商户号
     */
    private String MERT_NO;
    /**
     * 商户名称
     */
    private String MERT_NAME;
    /**
     * 规则类型
     */
    private String RULE_TP;
    /**
     * 不允许缴费开始日期时间
     */

    private String STR_TIME;
    /**
     * 不允许缴费结束日期时间
     */
    private String END_TIME;
    /**
     * 规则编号
     */
    private String RULE_NO;

    public String getMERT_NO() {
        return MERT_NO;
    }

    public void setMERT_NO(String MERT_NO) {
        this.MERT_NO = MERT_NO;
    }

    public String getMERT_NAME() {
        return MERT_NAME;
    }

    public void setMERT_NAME(String MERT_NAME) {
        this.MERT_NAME = MERT_NAME;
    }

    public String getRULE_TP() {
        return RULE_TP;
    }

    public void setRULE_TP(String RULE_TP) {
        this.RULE_TP = RULE_TP;
    }

    public String getSTR_TIME() {
        return STR_TIME;
    }

    public void setSTR_TIME(String STR_TIME) {
        this.STR_TIME = STR_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getRULE_NO() {
        return RULE_NO;
    }

    public void setRULE_NO(String RULE_NO) {
        this.RULE_NO = RULE_NO;
    }

}
