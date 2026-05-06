package com.adtec.pay.dto.offline.detail;

import java.util.List;

public class DtlModifyReq {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 子序号
     */
    private Long SUB_SER;
    /**
     * 操作类型
     * 1-新增
     * 2-修改
     * 3-删除
     */
    private String OPER_TP;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 交易金额
     */
    private String TRAN_AMT;
    /**
     * 操作类型
     * 00-非线下码牌
     * 01-线下码牌（线下扫码）
     * 02-线下现金
     * 04-差错补录
     */
    private String OPER_STAT;
    /**
     * 短备注
     */
    private String SHORT_RMRK;
    /**
     * 中备注
     */
    private String MID_RMRK;
    /**
     * 长备注
     */
    private String LONG_RMRK;

    /**
     * 报文CLOB
     */
    private String MSG_CLOB;
    /**
     * 非联网缴费信息维护——请求列表
     */
    private List<DtlModifyReqList> LIST;

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

    public String getPROJ_NAME() {
        return PROJ_NAME;
    }

    public void setPROJ_NAME(String PROJ_NAME) {
        this.PROJ_NAME = PROJ_NAME;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
    }

    public Long getSUB_SER() {
        return SUB_SER;
    }

    public void setSUB_SER(Long SUB_SER) {
        this.SUB_SER = SUB_SER;
    }

    public String getOPER_TP() {
        return OPER_TP;
    }

    public void setOPER_TP(String OPER_TP) {
        this.OPER_TP = OPER_TP;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getSHORT_RMRK() {
        return SHORT_RMRK;
    }

    public void setSHORT_RMRK(String SHORT_RMRK) {
        this.SHORT_RMRK = SHORT_RMRK;
    }

    public String getMID_RMRK() {
        return MID_RMRK;
    }

    public void setMID_RMRK(String MID_RMRK) {
        this.MID_RMRK = MID_RMRK;
    }

    public String getLONG_RMRK() {
        return LONG_RMRK;
    }

    public void setLONG_RMRK(String LONG_RMRK) {
        this.LONG_RMRK = LONG_RMRK;
    }

    public String getMSG_CLOB() {
        return MSG_CLOB;
    }

    public void setMSG_CLOB(String MSG_CLOB) {
        this.MSG_CLOB = MSG_CLOB;
    }

    public List<DtlModifyReqList> getLIST() {
        return LIST;
    }

    public void setLIST(List<DtlModifyReqList> LIST) {
        this.LIST = LIST;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getTRAN_AMT() {
        return TRAN_AMT;
    }

    public void setTRAN_AMT(String TRAN_AMT) {
        this.TRAN_AMT = TRAN_AMT;
    }

    public String getOPER_STAT() {
        return OPER_STAT;
    }

    public void setOPER_STAT(String OPER_STAT) {
        this.OPER_STAT = OPER_STAT;
    }
}
