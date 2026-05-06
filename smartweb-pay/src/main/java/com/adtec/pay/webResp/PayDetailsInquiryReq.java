package com.adtec.pay.webResp;

public class PayDetailsInquiryReq {
    private String busiNo;
    private String busiName;
    private String payNo;
    private String payAcct;
    private String txStat;
    private String autoDeduct;
    private String startTime;
    private String endTime;
    private Long start;
    private Long limit;


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

    public String getTxStat() {
        return txStat;
    }

    public void setTxStat(String txStat) {
        this.txStat = txStat;
    }

    public String getAutoDeduct() {
        return autoDeduct;
    }

    public void setAutoDeduct(String autoDeduct) {
        this.autoDeduct = autoDeduct;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
