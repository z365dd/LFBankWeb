package com.adtec.pay.dto.bus;

/**
 * 公交卡白名单查询请求类
 */
public class WhiteListQryReq {
    /**
     * 客户号
     */
    private String CUST_NO;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 状态
     */
    private String STAT;

    public String getCUST_NO() {
        return CUST_NO;
    }

    public void setCUST_NO(String CUST_NO) {
        this.CUST_NO = CUST_NO;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }
}
