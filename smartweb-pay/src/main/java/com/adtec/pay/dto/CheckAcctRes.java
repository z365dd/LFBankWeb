package com.adtec.pay.dto;

public class CheckAcctRes {
    //清算账户是否存在  Y-存在  N-不存在
    private String FLG;

    public String getFLG() {
        return FLG;
    }

    public void setFLG(String FLG) {
        this.FLG = FLG;
    }
}
