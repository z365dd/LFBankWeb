package com.adtec.pay.dto.bus;

public class BusListQryReq {
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 应用标识号
     */
    private String APP_ID;
    /**
     * 交易类型
     */
    private String TRAN_TP;
    /**
     * 第三方系统
     */
    private String OTH_SYS;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 查询页码
     */
    private int PAG_NUM;
    /**
     * 每页数据条数
     */
    private int REQ_REC_NUM;


    /**
     * 状态
     */
    private String STAT;
    /**
     * 确认状态
     */
    private String CONFM_STAT;


    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getTRAN_TP() {
        return TRAN_TP;
    }

    public void setTRAN_TP(String TRAN_TP) {
        this.TRAN_TP = TRAN_TP;
    }

    public String getOTH_SYS() {
        return OTH_SYS;
    }

    public void setOTH_SYS(String OTH_SYS) {
        this.OTH_SYS = OTH_SYS;
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

    public int getPAG_NUM() {
        return PAG_NUM;
    }

    public void setPAG_NUM(int PAG_NUM) {
        this.PAG_NUM = PAG_NUM;
    }

    public int getREQ_REC_NUM() {
        return REQ_REC_NUM;
    }

    public void setREQ_REC_NUM(int REQ_REC_NUM) {
        this.REQ_REC_NUM = REQ_REC_NUM;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getCONFM_STAT() {
        return CONFM_STAT;
    }

    public void setCONFM_STAT(String CONFM_STAT) {
        this.CONFM_STAT = CONFM_STAT;
    }
}
