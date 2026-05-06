package com.adtec.pay.dto.bookList;

public class MLppQryBookListReq {
    /**
     * 自动标志
     */
    private String AUTO_FLG;
    /**
     * 交易状态
     */
    private String TRAN_STAT;
    /**
     * 打印状态
     */
    private String PRT_STAT;
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 渠道号
     */
    private String CHNL_NO;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 原响应日期
     */
    private String ORIG_REQ_DATE;
    /**
     * 原响应流水
     */
    private String ORIG_REQ_SEQ;
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 机构
     */
    private String BRCH;
    /**
     * 柜员号
     */
    private String TLR_NO;

    /**
     * 退汇状态
     */
    private String RFND_STAT;

    /**
     * 查询类型
     */
    private String QRY_TP;

    /**
     * 缴费类型
     */
    private String TRAN_TP;


    public String getQRY_TP() {
        return QRY_TP;
    }

    public void setQRY_TP(String qRY_TP) {
        QRY_TP = qRY_TP;
    }

    public String getRFND_STAT() {
        return RFND_STAT;
    }

    public void setRFND_STAT(String rFND_STAT) {
        RFND_STAT = rFND_STAT;
    }

    public String getAUTO_FLG() {
        return AUTO_FLG;
    }

    public void setAUTO_FLG(String AUTO_FLG) {
        this.AUTO_FLG = AUTO_FLG;
    }

    public String getTRAN_STAT() {
        return TRAN_STAT;
    }

    public void setTRAN_STAT(String TRAN_STAT) {
        this.TRAN_STAT = TRAN_STAT;
    }

    public String getPRT_STAT() {
        return PRT_STAT;
    }

    public void setPRT_STAT(String PRT_STAT) {
        this.PRT_STAT = PRT_STAT;
    }

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
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

    public String getORIG_REQ_DATE() {
        return ORIG_REQ_DATE;
    }

    public void setORIG_REQ_DATE(String ORIG_REQ_DATE) {
        this.ORIG_REQ_DATE = ORIG_REQ_DATE;
    }

    public String getORIG_REQ_SEQ() {
        return ORIG_REQ_SEQ;
    }

    public void setORIG_REQ_SEQ(String ORIG_REQ_SEQ) {
        this.ORIG_REQ_SEQ = ORIG_REQ_SEQ;
    }

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getBRCH() {
        return BRCH;
    }

    public void setBRCH(String BRCH) {
        this.BRCH = BRCH;
    }

    public String getTLR_NO() {
        return TLR_NO;
    }

    public void setTLR_NO(String TLR_NO) {
        this.TLR_NO = TLR_NO;
    }

    public String getTRAN_TP() {
        return TRAN_TP;
    }

    public void setTRAN_TP(String TRAN_TP) {
        this.TRAN_TP = TRAN_TP;
    }
}
