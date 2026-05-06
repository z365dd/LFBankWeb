package com.adtec.pay.dto.proj.rec;

/**
 * 巡检记录查询请求类
 */
public class ProjInspRecReq {
    /**
     * 项目类型
     * 01-代收
     * 02-代付
     * 03-缴费
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
    private String EFFT_FLGL;


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

    public String getEFFT_FLGL() {
        return EFFT_FLGL;
    }

    public void setEFFT_FLGL(String EFFT_FLGL) {
        this.EFFT_FLGL = EFFT_FLGL;
    }
}
