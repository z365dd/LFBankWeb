package com.adtec.pay.dto.error;

public class ErrHandleReq {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 清算日期
     */
    private String CLR_DATE;
    /**
     * 原平台日期
     */
    private String ORIG_PLAT_DATE;
    /**
     * 原平台流水
     */
    private String ORIG_PLAT_SEQ;
    /**
     * 处理类型 1-人工已处理 2-冲正 3-补账"
     */
    private String PROC_TP;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public String getORIG_PLAT_DATE() {
        return ORIG_PLAT_DATE;
    }

    public void setORIG_PLAT_DATE(String ORIG_PLAT_DATE) {
        this.ORIG_PLAT_DATE = ORIG_PLAT_DATE;
    }

    public String getORIG_PLAT_SEQ() {
        return ORIG_PLAT_SEQ;
    }

    public void setORIG_PLAT_SEQ(String ORIG_PLAT_SEQ) {
        this.ORIG_PLAT_SEQ = ORIG_PLAT_SEQ;
    }

    public String getPROC_TP() {
        return PROC_TP;
    }

    public void setPROC_TP(String PROC_TP) {
        this.PROC_TP = PROC_TP;
    }
}
