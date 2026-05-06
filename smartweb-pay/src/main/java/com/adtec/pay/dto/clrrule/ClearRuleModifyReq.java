package com.adtec.pay.dto.clrrule;

public class ClearRuleModifyReq {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;

    /**
     * 操作类型 1-新增 2-删除 3-修改
     */
    private String OPER_TP;
    /**
     * 清算类型
     */
    private String CLR_TP;
    /**
     * 对账类型
     */
    private String CHK_TP;
    /**
     * 退款类型
     */
    private String RFND_TP;
    /**
     * 临时账户
     */
    private String TEMP_ACCT;
    /**
     * 临时的账户名称
     */
    private String TEMP_ACCT_NAME;
    /**
     * 通知类型
     */
    private String NOTE_TP;

    /**
     * 日切通知类型 1-不通知 1-通知
     */
    private String DAY_NOTE_TP;

    /**
     * 规则编号
     */
    private String RULE_NO;

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

    public String getOPER_TP() {
        return OPER_TP;
    }

    public void setOPER_TP(String OPER_TP) {
        this.OPER_TP = OPER_TP;
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

    public String getRFND_TP() {
        return RFND_TP;
    }

    public void setRFND_TP(String RFND_TP) {
        this.RFND_TP = RFND_TP;
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

    public String getNOTE_TP() {
        return NOTE_TP;
    }

    public void setNOTE_TP(String NOTE_TP) {
        this.NOTE_TP = NOTE_TP;
    }

    public String getRULE_NO() {
        return RULE_NO;
    }

    public void setRULE_NO(String RULE_NO) {
        this.RULE_NO = RULE_NO;
    }

    public String getDAY_NOTE_TP() {
        return DAY_NOTE_TP;
    }

    public void setDAY_NOTE_TP(String DAY_NOTE_TP) {
        this.DAY_NOTE_TP = DAY_NOTE_TP;
    }
}
