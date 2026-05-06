package com.adtec.pay.dto.offline.pdf;

/**
 * 表格行对象
 */
public class ReportDataItem {
    private String PROJ_NAME;
    private String OWE_MONTH;
    private String TOT_AMT;
    private String PAY_TIME;
    private String STAT;

    public ReportDataItem(){}

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

    public String getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(String TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public String getPAY_TIME() {
        return PAY_TIME;
    }

    public void setPAY_TIME(String PAY_TIME) {
        this.PAY_TIME = PAY_TIME;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }
}
