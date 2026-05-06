package com.adtec.pay.dto.offline.mission;

public class MissionAutoReq {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 项目描述
     */
    private String PROJ_DESC;
    /**
     * 项目类型
     */
    private String PROJ_TP;
    /**
     * 上次账单日期
     */
    private String LAST_TRAN_DATE;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 账单缴费日期-开始日期
     */
    private String STR_DATE;
    /**
     * 账单缴费日期-截止日期
     */
    private String END_DATE;

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

    public String getPROJ_DESC() {
        return PROJ_DESC;
    }

    public void setPROJ_DESC(String PROJ_DESC) {
        this.PROJ_DESC = PROJ_DESC;
    }

    public String getPROJ_TP() {
        return PROJ_TP;
    }

    public void setPROJ_TP(String PROJ_TP) {
        this.PROJ_TP = PROJ_TP;
    }

    public String getLAST_TRAN_DATE() {
        return LAST_TRAN_DATE;
    }

    public void setLAST_TRAN_DATE(String LAST_TRAN_DATE) {
        this.LAST_TRAN_DATE = LAST_TRAN_DATE;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
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
}
