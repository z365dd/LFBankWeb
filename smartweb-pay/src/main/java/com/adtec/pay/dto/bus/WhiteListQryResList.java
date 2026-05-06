package com.adtec.pay.dto.bus;

/**
 * 公交卡白名单查询响应循环列表
 */
public class WhiteListQryResList {
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 账户名称
     */
    private String ACCT_NAME;
    /**
     * 应用标识号
     */
    private String APP_ID;
    /**
     * 证件类型
     */
    private String CERT_TP;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 客户号
     */
    private String CUST_NO;
    /**
     * 状态
     */
    private String STAT;

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getACCT_NAME() {
        return ACCT_NAME;
    }

    public void setACCT_NAME(String ACCT_NAME) {
        this.ACCT_NAME = ACCT_NAME;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getCERT_TP() {
        return CERT_TP;
    }

    public void setCERT_TP(String CERT_TP) {
        this.CERT_TP = CERT_TP;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getCUST_NO() {
        return CUST_NO;
    }

    public void setCUST_NO(String CUST_NO) {
        this.CUST_NO = CUST_NO;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }
}
