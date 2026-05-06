package com.adtec.pay.dto.paytime;

public class PayTimeQryReq {
    /**
     * 商户号
     */
    private String MERT_NO;
    /**
     * 商户名称
     */
    private String MERT_NAME;

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
}
