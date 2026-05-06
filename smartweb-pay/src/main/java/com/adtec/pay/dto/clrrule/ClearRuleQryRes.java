package com.adtec.pay.dto.clrrule;

import java.util.List;

public class ClearRuleQryRes {
    /**
     * 记录数量
     */
    private int REC_NUM;

    private List<ClearRuleQryResList> LIST;

    public int getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(int REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

    public List<ClearRuleQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<ClearRuleQryResList> LIST) {
        this.LIST = LIST;
    }
}
