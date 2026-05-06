package com.adtec.pay.dto.recorded;

import java.util.List;

public class RecSumQryRes {


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
     * 手续费金额
     */
    private Double FEE_AMT;
    /**
     * 本行承担金额
     */
    private Double DCT_BANK_AMT;
    /**
     * 商户补贴金额
     */
    private Double DCT_MERT_AMT;
    /**
     * 清算标志
     */
    private String CLR_FLG;
    /**
     * 记录数量
     */
    private Long REC_NUM;
    /**
     * 批量金额
     */
    private Double BAT_AMT;
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
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;

    private List<RecSumQryResList> LIST;

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

    public Double getFEE_AMT() {
        return FEE_AMT;
    }

    public void setFEE_AMT(Double FEE_AMT) {
        this.FEE_AMT = FEE_AMT;
    }

    public Double getDCT_BANK_AMT() {
        return DCT_BANK_AMT;
    }

    public void setDCT_BANK_AMT(Double DCT_BANK_AMT) {
        this.DCT_BANK_AMT = DCT_BANK_AMT;
    }

    public Double getDCT_MERT_AMT() {
        return DCT_MERT_AMT;
    }

    public void setDCT_MERT_AMT(Double DCT_MERT_AMT) {
        this.DCT_MERT_AMT = DCT_MERT_AMT;
    }

    public Long getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(Long REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

    public List<RecSumQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<RecSumQryResList> LIST) {
        this.LIST = LIST;
    }

    public Double getBAT_AMT() {
        return BAT_AMT;
    }

    public void setBAT_AMT(Double BAT_AMT) {
        this.BAT_AMT = BAT_AMT;
    }

    public String getCLR_FLG() {
        return CLR_FLG;
    }

    public void setCLR_FLG(String CLR_FLG) {
        this.CLR_FLG = CLR_FLG;
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
