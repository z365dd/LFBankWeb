package com.adtec.pay.dto.offline.proj;


public class ProjQryResList {

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
     * 01-按月收取
     * 02-按年收取
     * 03-一次性收取
     */
    private String PROJ_TP;
    /**
     * 状态
     */
    private String STAT;
    /**
     * 金额
     */
    private Double AMT;
    /**
     * 收费周期
     */
    private String OWE_MONTH;
    /**
     * 创建日期
     */
    private String CRT_DATE;

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

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public Double getAMT() {
        return AMT;
    }

    public void setAMT(Double AMT) {
        this.AMT = AMT;
    }

    public String getCRT_DATE() {
        return CRT_DATE;
    }

    public void setCRT_DATE(String CRT_DATE) {
        this.CRT_DATE = CRT_DATE;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
    }
}
