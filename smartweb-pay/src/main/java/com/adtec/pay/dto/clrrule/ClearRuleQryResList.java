package com.adtec.pay.dto.clrrule;

public class ClearRuleQryResList {

    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 清算类型 1-汇总清算一笔 2-按渠道分别清算 3-按手续费清算
     */
    private String CLR_TP;
    /**
     * 对账类型 1-行内转账 2-三方转账
     */
    private String CHK_TP;
    /**
     * 通知类型 1-对账完成通知三方 2-对账完成不通知三方
     */
    private String NOTE_TP;
    /**
     * 日切通知类型 2-不通知 1-通知
     */
    private String DAY_NOTE_TP;
    /**
     * 退款类型 1-单位户直接退费 2-内部户退费 3-垫款户退费 4-不允许退费
     */
    private String RFND_TP;
    /**
     * 单位账号
     */
    private String ENTR_ACCT;
    /**
     * 临时账户
     */
    private String TEMP_ACCT;
    /**
     * 临时的账户名称
     */
    private String TEMP_ACCT_NAME;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getCLR_TP() {
        return CLR_TP;
    }

    public void setCLR_TP(String CLR_TP) {
        this.CLR_TP = CLR_TP;
    }

    public String getCHK_TP() {
        return CHK_TP;
    }

    public void setCHK_TP(String CHK_TP) {
        this.CHK_TP = CHK_TP;
    }

    public String getNOTE_TP() {
        return NOTE_TP;
    }

    public void setNOTE_TP(String NOTE_TP) {
        this.NOTE_TP = NOTE_TP;
    }

    public String getRFND_TP() {
        return RFND_TP;
    }

    public void setRFND_TP(String RFND_TP) {
        this.RFND_TP = RFND_TP;
    }

    public String getENTR_ACCT() {
        return ENTR_ACCT;
    }

    public void setENTR_ACCT(String ENTR_ACCT) {
        this.ENTR_ACCT = ENTR_ACCT;
    }

    public String getTEMP_ACCT() {
        return TEMP_ACCT;
    }

    public void setTEMP_ACCT(String TEMP_ACCT) {
        this.TEMP_ACCT = TEMP_ACCT;
    }

    public String getTEMP_ACCT_NAME() {
        return TEMP_ACCT_NAME;
    }

    public void setTEMP_ACCT_NAME(String TEMP_ACCT_NAME) {
        this.TEMP_ACCT_NAME = TEMP_ACCT_NAME;
    }

    public String getDAY_NOTE_TP() {
        return DAY_NOTE_TP;
    }

    public void setDAY_NOTE_TP(String DAY_NOTE_TP) {
        this.DAY_NOTE_TP = DAY_NOTE_TP;
    }
}
