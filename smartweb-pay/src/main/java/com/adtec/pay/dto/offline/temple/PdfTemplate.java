package com.adtec.pay.dto.offline.temple;

public class PdfTemplate {
    /**
     * 业务编号
     */
    private String BUSI_NO;

    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 业务类型
     */
    private String BUSI_TP;
    /**
     * pdf模板名称
     */
    private String TMPL_ID;
    /**
     * pdf模板描述
     */
    private String TMPL_DESC;
    /**
     * 状态
     */
    private String STAT;
    /**
     * 备注
     */
    private String RMRK;

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

    public String getBUSI_TP() {
        return BUSI_TP;
    }

    public void setBUSI_TP(String BUSI_TP) {
        this.BUSI_TP = BUSI_TP;
    }

    public String getTMPL_ID() {
        return TMPL_ID;
    }

    public void setTMPL_ID(String TMPL_ID) {
        this.TMPL_ID = TMPL_ID;
    }

    public String getTMPL_DESC() {
        return TMPL_DESC;
    }

    public void setTMPL_DESC(String TMPL_DESC) {
        this.TMPL_DESC = TMPL_DESC;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getRMRK() {
        return RMRK;
    }

    public void setRMRK(String RMRK) {
        this.RMRK = RMRK;
    }
}
