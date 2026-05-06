package com.adtec.pay.dto.signbat;

public class SignBatQryReqList {
    /**
     * 第三方单位编号
     */
    private String OTH_ENTR_NO;
    /**
     * 第三方客户号
     */
    private String OTH_CUST_NO;
    /**
     * 签约状态
     */
    private String SIGN_STAT;
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 客户号
     */
    private String CUST_NO;
    /**
     * 签约机构
     */
    private String SIGN_BRCH;

    public String getOTH_ENTR_NO() {
        return OTH_ENTR_NO;
    }

    public void setOTH_ENTR_NO(String OTH_ENTR_NO) {
        this.OTH_ENTR_NO = OTH_ENTR_NO;
    }

    public String getOTH_CUST_NO() {
        return OTH_CUST_NO;
    }

    public void setOTH_CUST_NO(String OTH_CUST_NO) {
        this.OTH_CUST_NO = OTH_CUST_NO;
    }

    public String getSIGN_STAT() {
        return SIGN_STAT;
    }

    public void setSIGN_STAT(String SIGN_STAT) {
        this.SIGN_STAT = SIGN_STAT;
    }

    public String getSTR_DATE() {
        return STR_DATE;
    }

    public void setSTR_DATE(String STR_DATE) {
        this.STR_DATE = STR_DATE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
    }

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getCUST_NO() {
        return CUST_NO;
    }

    public void setCUST_NO(String CUST_NO) {
        this.CUST_NO = CUST_NO;
    }

    public String getSIGN_BRCH() {
        return SIGN_BRCH;
    }

    public void setSIGN_BRCH(String SIGN_BRCH) {
        this.SIGN_BRCH = SIGN_BRCH;
    }
}
