package com.adtec.pay.dto.offline.detail;

import java.util.List;

public class DtlRefundReq {
    /**
     * 平台日期
     */
    private String PLAT_DATE;
    /**
     * 平台流水
     */
    private String PLAT_SEQ;
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 退款金额
     */
    private Double RFND_AMT;
    /**
     * 手机号码
     */
    private String PHONE_NO;
    /**
     * 验证码生成标识号
     */
    private String VRFY_NO_CRT_ID;
    /**
     * 验证号码
     * 短信验证码
     */
    private String VRFY_NO;
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 账户名称
     */
    private String ACCT_NAME;
    /**
     * 报文CLOB
     */
    private String MSG_CLOB;

    /**
     * 退费请求循环列表
     */
    private List<DtlRefundReqList> LIST;

    public String getPLAT_DATE() {
        return PLAT_DATE;
    }

    public void setPLAT_DATE(String PLAT_DATE) {
        this.PLAT_DATE = PLAT_DATE;
    }

    public String getPLAT_SEQ() {
        return PLAT_SEQ;
    }

    public void setPLAT_SEQ(String PLAT_SEQ) {
        this.PLAT_SEQ = PLAT_SEQ;
    }

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(double RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getVRFY_NO_CRT_ID() {
        return VRFY_NO_CRT_ID;
    }

    public void setVRFY_NO_CRT_ID(String VRFY_NO_CRT_ID) {
        this.VRFY_NO_CRT_ID = VRFY_NO_CRT_ID;
    }

    public String getVRFY_NO() {
        return VRFY_NO;
    }

    public void setVRFY_NO(String VRFY_NO) {
        this.VRFY_NO = VRFY_NO;
    }

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getACCT_NAME() {
        return ACCT_NAME;
    }

    public void setACCT_NAME(String ACCT_NAME) {
        this.ACCT_NAME = ACCT_NAME;
    }

    public String getMSG_CLOB() {
        return MSG_CLOB;
    }

    public void setMSG_CLOB(String MSG_CLOB) {
        this.MSG_CLOB = MSG_CLOB;
    }

    public List<DtlRefundReqList> getLIST() {
        return LIST;
    }

    public void setLIST(List<DtlRefundReqList> LIST) {
        this.LIST = LIST;
    }
}
