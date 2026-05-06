package com.adtec.pay.dto.offline.detail;

public class DtlRefundReqList {

    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 子序号
     */
    private Long SUB_SER;
    /**
     * 名称
     */
    private String NAME;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 手机号码
     */
    private String PHONE_NO;
    /**
     * 退款金额
     */
    private Double RFND_AMT;

    public String getPROJ_NAME() {
        return PROJ_NAME;
    }

    public void setPROJ_NAME(String PROJ_NAME) {
        this.PROJ_NAME = PROJ_NAME;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
    }

    public Long getSUB_SER() {
        return SUB_SER;
    }

    public void setSUB_SER(Long SUB_SER) {
        this.SUB_SER = SUB_SER;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(Double RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
    }
}
