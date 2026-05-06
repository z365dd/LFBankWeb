package com.adtec.pay.entity.union;

public class UnionInst {
    /**
     * 机构号
     */
    private String BRCH_NO;
    /**
     * 机构名称
     */
    private String BRCH_NAME;
    /**
     * 机构类型
     * 1-一级工会
     * 2-市直单位
     */
    private String BRCH_TP;
    /**
     * 上级机构
     */
    private String UP_BRCH;
    /**
     * 机构层级
     * 1- 一级工会
     * 2-市直工会
     */
    private String BRCH_LVL_NO;
    /**
     * 所属区域
     */
    private String ADDR;
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


    public String getBRCH_NO() {
        return BRCH_NO;
    }

    public void setBRCH_NO(String BRCH_NO) {
        this.BRCH_NO = BRCH_NO;
    }

    public String getBRCH_NAME() {
        return BRCH_NAME;
    }

    public void setBRCH_NAME(String BRCH_NAME) {
        this.BRCH_NAME = BRCH_NAME;
    }

    public String getBRCH_TP() {
        return BRCH_TP;
    }

    public void setBRCH_TP(String BRCH_TP) {
        this.BRCH_TP = BRCH_TP;
    }

    public String getUP_BRCH() {
        return UP_BRCH;
    }

    public void setUP_BRCH(String UP_BRCH) {
        this.UP_BRCH = UP_BRCH;
    }

    public String getBRCH_LVL_NO() {
        return BRCH_LVL_NO;
    }

    public void setBRCH_LVL_NO(String BRCH_LVL_NO) {
        this.BRCH_LVL_NO = BRCH_LVL_NO;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
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
