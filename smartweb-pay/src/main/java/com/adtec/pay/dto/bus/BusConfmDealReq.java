package com.adtec.pay.dto.bus;

public class BusConfmDealReq {
    /**
     * 平台日期
     */
    private String PLAT_DATE;
    /**
     * 平台流水
     */
    private String PLAT_SEQ;
    /**
     * 确认状态
     */
    private String CONFM_STAT;

    private String BUSI_NO;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getPLAT_DATE() {
        return PLAT_DATE;
    }

    public void setPLAT_DATE(String PLAT_DATE) {
        this.PLAT_DATE = PLAT_DATE;
    }

    public String getPLAT_SEQ() {
        return PLAT_SEQ;
    }

    public void setPLAT_SEQ(String PLAT_SEQ) {
        this.PLAT_SEQ = PLAT_SEQ;
    }

    public String getCONFM_STAT() {
        return CONFM_STAT;
    }

    public void setCONFM_STAT(String CONFM_STAT) {
        this.CONFM_STAT = CONFM_STAT;
    }
}
