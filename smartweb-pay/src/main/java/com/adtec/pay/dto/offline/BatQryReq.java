package com.adtec.pay.dto.offline;

public class BatQryReq {
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
     * 交易类型
     */
    private String TRAN_TP;
    /**
     * 状态
     * 00-待启用
     * 01-已启用
     * 02-已作废
     * 03-已过期
     * 不送默认查全部
     */
    private String STAT;
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

    public String getTRAN_TP() {
        return TRAN_TP;
    }

    public void setTRAN_TP(String TRAN_TP) {
        this.TRAN_TP = TRAN_TP;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
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
