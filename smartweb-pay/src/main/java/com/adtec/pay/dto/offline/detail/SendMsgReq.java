package com.adtec.pay.dto.offline.detail;

public class SendMsgReq {
    /**
     * 手机号码
     */
    private String PHONE_NO;
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 模板ID
     */
    private String TMPL_ID;
    /**
     * 短信内容
     */
    private String MSG_CNTT;
    /**
     * 备注
     */
    private String RMRK;
    /**
     * 报文CLOB
     */
    private String MSG_CLOB;

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

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

    public String getTMPL_ID() {
        return TMPL_ID;
    }

    public void setTMPL_ID(String TMPL_ID) {
        this.TMPL_ID = TMPL_ID;
    }

    public String getMSG_CNTT() {
        return MSG_CNTT;
    }

    public void setMSG_CNTT(String MSG_CNTT) {
        this.MSG_CNTT = MSG_CNTT;
    }

    public String getRMRK() {
        return RMRK;
    }

    public void setRMRK(String RMRK) {
        this.RMRK = RMRK;
    }

    public String getMSG_CLOB() {
        return MSG_CLOB;
    }

    public void setMSG_CLOB(String MSG_CLOB) {
        this.MSG_CLOB = MSG_CLOB;
    }
}
