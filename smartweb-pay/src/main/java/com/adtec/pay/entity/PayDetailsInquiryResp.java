package com.adtec.pay.entity;

public class PayDetailsInquiryResp {
    private String busiNo;
    private String busiName;
    private String payNo;
    private String payAcct;
    private String cstName;
    private String payAmt;
    private String realAmt;
    private String discountAmt;
    private String payType;
    private String txStat;
    private String autoDeduct;

    public String getBusiNo() {
        return busiNo;
    }

    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayAcct() {
        return payAcct;
    }

    public void setPayAcct(String payAcct) {
        this.payAcct = payAcct;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTxStat() {
        return txStat;
    }

    public void setTxStat(String txStat) {
        this.txStat = txStat;
    }

    public String getRealAmt() {
        return realAmt;
    }

    public void setRealAmt(String realAmt) {
        this.realAmt = realAmt;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getAutoDeduct() {
        return autoDeduct;
    }

    public void setAutoDeduct(String autoDeduct) {
        this.autoDeduct = autoDeduct;
    }
}
