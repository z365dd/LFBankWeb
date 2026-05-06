package com.adtec.pay.dto.offline.pdf;

import java.util.List;

/**
 * 表格内容
 */
public class ReportData {

    private String BUSI_NAME;
    private String REQ_SEQ;
    private String NAME;
    private String STU_ID;
    private String PHONE_NO;
    private String CREATE_DATE;

    private List<ReportDataItem> SUB_LIST;

    public ReportData(){}

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public String getREQ_SEQ() {
        return REQ_SEQ;
    }

    public void setREQ_SEQ(String REQ_SEQ) {
        this.REQ_SEQ = REQ_SEQ;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSTU_ID() {
        return STU_ID;
    }

    public void setSTU_ID(String STU_ID) {
        this.STU_ID = STU_ID;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public List<ReportDataItem> getSUB_LIST() {
        return SUB_LIST;
    }

    public void setSUB_LIST(List<ReportDataItem> SUB_LIST) {
        this.SUB_LIST = SUB_LIST;
    }
}
