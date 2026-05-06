package com.adtec.pay.dto.check;

public class ChkQryReq {

    /*业务编号*/
    private String BUSI_NO;
    /*对账状态*/
    private String CHK_STAT;
    /*开始日期*/
    private String STR_DATE;
    /*结束日期*/
    private String END_DATE;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getCHK_STAT() {
        return CHK_STAT;
    }

    public void setCHK_STAT(String CHK_STAT) {
        this.CHK_STAT = CHK_STAT;
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
