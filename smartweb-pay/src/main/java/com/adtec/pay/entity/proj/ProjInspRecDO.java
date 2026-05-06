package com.adtec.pay.entity.proj;

/**
 * 项目巡检记录
 */
public class ProjInspRecDO {
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
     * 巡检日期
     */
    private String INSP_DATE;
    /**
     * 巡检人员
     */
    private String INSP_USER_NAME;
    /**
     * 短备注
     */
    private String SHORT_RMRK;
    /**
     * 中备注
     */
    private String MID_RMRK;
    /**
     * 长备注
     */
    private String LONG_RMRK;

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

    public String getINSP_DATE() {
        return INSP_DATE;
    }

    public void setINSP_DATE(String INSP_DATE) {
        this.INSP_DATE = INSP_DATE;
    }

    public String getINSP_USER_NAME() {
        return INSP_USER_NAME;
    }

    public void setINSP_USER_NAME(String INSP_USER_NAME) {
        this.INSP_USER_NAME = INSP_USER_NAME;
    }

    public String getSHORT_RMRK() {
        return SHORT_RMRK;
    }

    public void setSHORT_RMRK(String SHORT_RMRK) {
        this.SHORT_RMRK = SHORT_RMRK;
    }

    public String getMID_RMRK() {
        return MID_RMRK;
    }

    public void setMID_RMRK(String MID_RMRK) {
        this.MID_RMRK = MID_RMRK;
    }

    public String getLONG_RMRK() {
        return LONG_RMRK;
    }

    public void setLONG_RMRK(String LONG_RMRK) {
        this.LONG_RMRK = LONG_RMRK;
    }
}
