package com.adtec.pay.dto.error;

public class ErrQryResList {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 清算日期
     */
    private String CLR_DATE;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 名称
     */
    private String NAME;
    /**
     * 金额
     */
    private Double AMT;
    /**
     * 支付类型
     */
    private String PAY_TP;
    /**
     * 差错描述
     * 00-未调账
     * 01-已调账
     * 02-调账失败
     * 03-调账超时 (02、03运营平台上展示为处理异常)
     */
    private String ERR_DESC;
    /**
     * 状态
     */
    private String STAT;

    /**
     * 原平台日期
     */
    private String ORIG_PLAT_DATE;

    /**
     * 原平台流水
     */
    private String ORIG_PLAT_SEQ;

    /**
     * 缴费类型
     */
    private String TRAN_TP;


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

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Double getAMT() {
        return AMT;
    }

    public void setAMT(Double AMT) {
        this.AMT = AMT;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public void setPAY_TP(String PAY_TP) {
        this.PAY_TP = PAY_TP;
    }

    public String getERR_DESC() {
        return ERR_DESC;
    }

    public void setERR_DESC(String ERR_DESC) {
        this.ERR_DESC = ERR_DESC;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getORIG_PLAT_DATE() {
        return ORIG_PLAT_DATE;
    }

    public void setORIG_PLAT_DATE(String ORIG_PLAT_DATE) {
        this.ORIG_PLAT_DATE = ORIG_PLAT_DATE;
    }

    public String getORIG_PLAT_SEQ() {
        return ORIG_PLAT_SEQ;
    }

    public void setORIG_PLAT_SEQ(String ORIG_PLAT_SEQ) {
        this.ORIG_PLAT_SEQ = ORIG_PLAT_SEQ;
    }

    public String getTRAN_TP() {
        return TRAN_TP;
    }

    public void setTRAN_TP(String TRAN_TP) {
        this.TRAN_TP = TRAN_TP;
    }
}
