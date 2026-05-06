package com.adtec.pay.dto.recorded;

public class RecQryReq {

    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 序号
     */
    private Long SER;
    /**
     * 清算日期
     */
    private String CLR_DATE;
    /**
     * 渠道号
     */
    private String CHNL_NO;
    /**
     * 0-商户入账明细查询 1-回单打印查询
     */
    private String type;

    /**
     * 开始日期
     */
    private String STR_DATE;

    /**
     * 结束日期
     */
    private String END_DATE;


    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public Long getSER() {
        return SER;
    }

    public void setSER(Long SER) {
        this.SER = SER;
    }

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
