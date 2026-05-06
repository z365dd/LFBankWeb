package com.adtec.pay.dto.check;

import java.util.List;

public class ChkQryRes {

    /**
     * 记录数量
     */
    private Long REC_NUM;
    /**
     * 对账查询返回列表
     */
    private List<ChkQryResList> LIST;

    public Long getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(Long REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

    public List<ChkQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<ChkQryResList> LIST) {
        this.LIST = LIST;
    }
}
