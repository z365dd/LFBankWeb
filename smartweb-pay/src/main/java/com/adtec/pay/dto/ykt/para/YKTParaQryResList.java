package com.adtec.pay.dto.ykt.para;

public class YKTParaQryResList {

    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 交易金额
     */
    private Double TRAN_AMT;
    /**
     * 日金额
     */
    private Double DAY_AMT;
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

    public Double getTRAN_AMT() {
        return TRAN_AMT;
    }

    public void setTRAN_AMT(Double TRAN_AMT) {
        this.TRAN_AMT = TRAN_AMT;
    }

    public Double getDAY_AMT() {
        return DAY_AMT;
    }

    public void setDAY_AMT(Double DAY_AMT) {
        this.DAY_AMT = DAY_AMT;
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
