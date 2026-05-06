package com.adtec.pay.dto.recorded;

public class RecSumQryReq {

    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;

    /**
     * 业务编号（多项）
     */
    private String BUSI_NO_LIST;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getSTR_DATE() {
        return STR_DATE;
    }

    public void setSTR_DATE(String STR_DATE) {
        this.STR_DATE = STR_DATE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
    }

    public String getBUSI_NO_LIST() {
        return BUSI_NO_LIST;
    }

    public void setBUSI_NO_LIST(String BUSI_NO_LIST) {
        this.BUSI_NO_LIST = BUSI_NO_LIST;
    }
}
