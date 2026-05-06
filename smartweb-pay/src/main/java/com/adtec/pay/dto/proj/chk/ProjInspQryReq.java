package com.adtec.pay.dto.proj.chk;

public class ProjInspQryReq {
    /**
     * 项目类型
     */
    private String PROJ_TP;
    /**
     * 客户名称
     */
    private String CUST_NAME;
    /**
     * 机构
     */
    private String BRCH;
    /**
     * 巡检状态
     */
    private String INSP_STAT;
    /**
     * 签约状态
     */
    private String SIGN_STAT;
    /**
     * 有效标志
     */
    private String EFFT_FLG;

    public String getPROJ_TP() {
        return PROJ_TP;
    }

    public void setPROJ_TP(String PROJ_TP) {
        this.PROJ_TP = PROJ_TP;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String CUST_NAME) {
        this.CUST_NAME = CUST_NAME;
    }

    public String getBRCH() {
        return BRCH;
    }

    public void setBRCH(String BRCH) {
        this.BRCH = BRCH;
    }

    public String getINSP_STAT() {
        return INSP_STAT;
    }

    public void setINSP_STAT(String INSP_STAT) {
        this.INSP_STAT = INSP_STAT;
    }

    public String getSIGN_STAT() {
        return SIGN_STAT;
    }

    public void setSIGN_STAT(String SIGN_STAT) {
        this.SIGN_STAT = SIGN_STAT;
    }

    public String getEFFT_FLG() {
        return EFFT_FLG;
    }

    public void setEFFT_FLG(String EFFT_FLG) {
        this.EFFT_FLG = EFFT_FLG;
    }


}
