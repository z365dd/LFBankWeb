package com.adtec.pay.dto.paytime;

/**
 * 缴费时间规则维护DTO
 */
public class PayTimeModifyReq {
    /**
     * 商户号
     */
    private String MERT_NO;
    /**
     * 操作类型
     */
    private String OPER_TP;
    /**
     * 规则类型
     */
    private String RULE_TP;
    /**
     * 开始时间
     */
    private String STR_TIME;
    /**
     * 结束时间
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

    public String getOPER_TP() {
        return OPER_TP;
    }

    public void setOPER_TP(String OPER_TP) {
        this.OPER_TP = OPER_TP;
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
