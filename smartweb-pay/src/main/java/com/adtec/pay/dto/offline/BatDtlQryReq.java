package com.adtec.pay.dto.offline;

public class BatDtlQryReq {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 批次号
     */
    private String BAT_NO;
    /**
     * 批次名称
     */
    private String BAT_NAME;
    /**
     * 状态
     */
    private String STAT;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 项目编号
     */
    private String PROJ_NO;
    /**
     * 学号
     */
    private String STU_ID;
    /**
     * 退汇状态
     */
    private String RFND_STAT;
    /**
     * 支付类型
     * 0-现金
     * 1-转账
     * 2-微信
     * 3-支付宝
     * 4-pos
     * 5-本行卡支付（小惠发起）
     */
    private String PAY_TP;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 短备注
     */
    private String SHORT_RMRK;
    /**
     * 中备注
     */
    private String MID_RMRK;
    /**
     * 长备注
     */
    private String LONG_RMRK;
    /**
     * 报文CLOB
     */
    private String MSG_CLOB;

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

    public String getBAT_NO() {
        return BAT_NO;
    }

    public void setBAT_NO(String BAT_NO) {
        this.BAT_NO = BAT_NO;
    }

    public String getBAT_NAME() {
        return BAT_NAME;
    }

    public void setBAT_NAME(String BAT_NAME) {
        this.BAT_NAME = BAT_NAME;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getPROJ_NO() {
        return PROJ_NO;
    }

    public void setPROJ_NO(String PROJ_NO) {
        this.PROJ_NO = PROJ_NO;
    }

    public String getSTU_ID() {
        return STU_ID;
    }

    public void setSTU_ID(String STU_ID) {
        this.STU_ID = STU_ID;
    }

    public String getRFND_STAT() {
        return RFND_STAT;
    }

    public void setRFND_STAT(String RFND_STAT) {
        this.RFND_STAT = RFND_STAT;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public void setPAY_TP(String PAY_TP) {
        this.PAY_TP = PAY_TP;
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

    public String getSHORT_RMRK() {
        return SHORT_RMRK;
    }

    public void setSHORT_RMRK(String SHORT_RMRK) {
        this.SHORT_RMRK = SHORT_RMRK;
    }

    public String getMID_RMRK() {
        return MID_RMRK;
    }

    public void setMID_RMRK(String MID_RMRK) {
        this.MID_RMRK = MID_RMRK;
    }

    public String getLONG_RMRK() {
        return LONG_RMRK;
    }

    public void setLONG_RMRK(String LONG_RMRK) {
        this.LONG_RMRK = LONG_RMRK;
    }

    public String getMSG_CLOB() {
        return MSG_CLOB;
    }

    public void setMSG_CLOB(String MSG_CLOB) {
        this.MSG_CLOB = MSG_CLOB;
    }
}
