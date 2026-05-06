package com.adtec.pay.dto;

public class OfflineDtlDO {
    /**
     * 业务编号
     */
    private String BUSI_NO;

    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 收费项目名称
     */
    private String PROJ_NAME;
    /**
     * 收费周期
     */
    private String OWE_MONTH;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 平台日期
     */
    private String PLAT_DATE;
    /**
     * 平台流水
     */
    private String PLAT_SEQ;
    /**
     * 退款金额
     */
    private String RFND_AMT;

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

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getPLAT_DATE() {
        return PLAT_DATE;
    }

    public void setPLAT_DATE(String PLAT_DATE) {
        this.PLAT_DATE = PLAT_DATE;
    }

    public String getPLAT_SEQ() {
        return PLAT_SEQ;
    }

    public void setPLAT_SEQ(String PLAT_SEQ) {
        this.PLAT_SEQ = PLAT_SEQ;
    }

    public String getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(String RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
    }
}
