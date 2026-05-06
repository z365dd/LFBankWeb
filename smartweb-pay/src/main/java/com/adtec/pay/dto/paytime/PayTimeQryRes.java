package com.adtec.pay.dto.paytime;

import java.util.List;

public class PayTimeQryRes {
    /**
     * 记录条数
     */
    private int REC_NUM;
    /**
     * 商家缴费时间规则列表
     */
    private List<PayTimeQryListRes> LIST;

    public int getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(int REC_NUM) {
        this.REC_NUM = REC_NUM;
    }


    public List<PayTimeQryListRes> getLIST() {
        return LIST;
    }

    public void setLIST(List<PayTimeQryListRes> lIST) {
        LIST = lIST;
    }


}
