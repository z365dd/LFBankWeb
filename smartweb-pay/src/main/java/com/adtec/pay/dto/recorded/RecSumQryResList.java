package com.adtec.pay.dto.recorded;

public class RecSumQryResList {


    /**
     * 序号
     */
    private Long SER;
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 清算日期-交易日期
     */
    private String CLR_DATE;
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 清算金额
     */
    private Double CLR_AMT;
    /**
     * 商户补贴金额
     */
    private Double DCT_MERT_AMT;
    /**
     * 本行承担金额
     */
    private Double DCT_BANK_AMT;
    /**
     * 手续费金额
     */
    private Double FEE_AMT;
    /**
     * 批量金额
     */
    private Double BAT_AMT;

    /**
     * 入账日期
     */
    private String REQ_DATE;
    /**
     * 收款账号
     */
    private String PAYEE_ACCT;
    /**
     * 码牌金额
     */
    private Double OTH_AMT;
    /**
     * 差错补录
     */
    private Double MUAL_AMT;
    /**
     * 退费金额
     */
    private Double RFND_AMT;
    /**
     * 打印专用  - 支付方式
     */
    private String PAY_TYPE;
    /**
     * 清算状态
     */
    private String STAT;

    public Long getSER() {
        return SER;
    }

    public void setSER(Long SER) {
        this.SER = SER;
    }

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

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(Double TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public Double getCLR_AMT() {
        return CLR_AMT;
    }

    public void setCLR_AMT(Double CLR_AMT) {
        this.CLR_AMT = CLR_AMT;
    }

    public Double getDCT_MERT_AMT() {
        return DCT_MERT_AMT;
    }

    public void setDCT_MERT_AMT(Double DCT_MERT_AMT) {
        this.DCT_MERT_AMT = DCT_MERT_AMT;
    }

    public Double getDCT_BANK_AMT() {
        return DCT_BANK_AMT;
    }

    public void setDCT_BANK_AMT(Double DCT_BANK_AMT) {
        this.DCT_BANK_AMT = DCT_BANK_AMT;
    }

    public Double getFEE_AMT() {
        return FEE_AMT;
    }

    public void setFEE_AMT(Double FEE_AMT) {
        this.FEE_AMT = FEE_AMT;
    }

    public Double getBAT_AMT() {
        return BAT_AMT;
    }

    public void setBAT_AMT(Double BAT_AMT) {
        this.BAT_AMT = BAT_AMT;
    }

    public String getREQ_DATE() {
        return REQ_DATE;
    }

    public void setREQ_DATE(String REQ_DATE) {
        this.REQ_DATE = REQ_DATE;
    }

    public String getPAYEE_ACCT() {
        return PAYEE_ACCT;
    }

    public void setPAYEE_ACCT(String PAYEE_ACCT) {
        this.PAYEE_ACCT = PAYEE_ACCT;
    }

    public String getPAY_TYPE() {
        return PAY_TYPE;
    }

    public void setPAY_TYPE(String PAY_TYPE) {
        this.PAY_TYPE = PAY_TYPE;
    }

    public Double getOTH_AMT() {
        return OTH_AMT;
    }

    public void setOTH_AMT(Double OTH_AMT) {
        this.OTH_AMT = OTH_AMT;
    }

    public Double getMUAL_AMT() {
        return MUAL_AMT;
    }

    public void setMUAL_AMT(Double MUAL_AMT) {
        this.MUAL_AMT = MUAL_AMT;
    }

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(Double RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }
}
