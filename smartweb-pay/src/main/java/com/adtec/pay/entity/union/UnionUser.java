package com.adtec.pay.entity.union;

public class UnionUser {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 名称
     */
    private String NAME;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 手机号码
     */
    private String PHONE_NO;
    /**
     * 状态
     * 1-在职
     * 2-离职
     * 3-退休
     */
    private String STAT;
    /**
     * 可用标志
     * 0-待审批
     * 1-已审批
     */
    private String VALID_FLG;


    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getVALID_FLG() {
        return VALID_FLG;
    }

    public void setVALID_FLG(String VALID_FLG) {
        this.VALID_FLG = VALID_FLG;
    }
}
