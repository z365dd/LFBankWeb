package com.adtec.pay.dto.offline.detail;

public class SendMsgRes {
    /**
     * 验证码生成标识号
     */
    private String VRFY_NO_CRT_ID;
    /**
     * 报文标识号
     */
    private String MSG_ID;

    public String getVRFY_NO_CRT_ID() {
        return VRFY_NO_CRT_ID;
    }

    public void setVRFY_NO_CRT_ID(String VRFY_NO_CRT_ID) {
        this.VRFY_NO_CRT_ID = VRFY_NO_CRT_ID;
    }

    public String getMSG_ID() {
        return MSG_ID;
    }

    public void setMSG_ID(String MSG_ID) {
        this.MSG_ID = MSG_ID;
    }
}
