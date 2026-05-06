package com.adtec.pay.dto.refund;

import java.util.List;

public class BatRefundReq {

    /**
     * 业务编号
     */
    private String BUSI_NO;
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
     */
    private String VRFY_NO;


    private List<BatRefundReqList> LIST;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
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

    public List<BatRefundReqList> getLIST() {
        return LIST;
    }

    public void setLIST(List<BatRefundReqList> LIST) {
        this.LIST = LIST;
    }
}
