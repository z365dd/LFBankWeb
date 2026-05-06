package com.adtec.pay.dto.offline;

import java.util.List;

public class BatDtlModifyReq {

    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 批次号
     */
    private String BAT_NO;
    /**
     * 批次名称
     */
    private String BAT_NAME;
    /**
     * 子序号
     */
    private Long SUB_SER;
    /**
     * 操作类型
     * 1-新增
     * 2-修改
     * 3-作废
     * 4-删除
     */
    private String OPER_TP;
    /**
     * 缴费号
     */
    private String PAY_NO;
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
     * 批次缴费信息维护请求——循环列表
     */
    private List<BatDtlModifyReqList> LIST;

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

    public String getBAT_NO() {
        return BAT_NO;
    }

    public void setBAT_NO(String BAT_NO) {
        this.BAT_NO = BAT_NO;
    }

    public String getBAT_NAME() {
        return BAT_NAME;
    }

    public void setBAT_NAME(String BAT_NAME) {
        this.BAT_NAME = BAT_NAME;
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

    public List<BatDtlModifyReqList> getLIST() {
        return LIST;
    }

    public void setLIST(List<BatDtlModifyReqList> LIST) {
        this.LIST = LIST;
    }
}
