package com.adtec.pay.dto.offline.mission;

public class MissionBatNoticeReq {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 单位咨询电话
     */
    private String PHONE_NO;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
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

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }
}
