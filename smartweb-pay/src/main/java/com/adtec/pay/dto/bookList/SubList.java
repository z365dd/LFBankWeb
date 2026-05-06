package com.adtec.pay.dto.bookList;

import java.io.Serializable;

public class SubList implements Serializable {

    /**
     * 子序号
     */
    private String SUB_SER;

    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 总金额
     */
    private String TOT_AMT;
    /**
     * 班级
     */
    private String STU_CLASS;
    /**
     * 优惠金额
     */
    private String DCT_AMT;

    public String getSUB_SER() {
        return SUB_SER;
    }

    public void setSUB_SER(String SUB_SER) {
        this.SUB_SER = SUB_SER;
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

    public String getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(String TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public String getDCT_AMT() {
        return DCT_AMT;
    }

    public void setDCT_AMT(String DCT_AMT) {
        this.DCT_AMT = DCT_AMT;
    }

    public String getSTU_CLASS() {
        return STU_CLASS;
    }

    public void setSTU_CLASS(String STU_CLASS) {
        this.STU_CLASS = STU_CLASS;
    }
}
