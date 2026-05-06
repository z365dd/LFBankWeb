package com.adtec.pay.dto.proj.mng;

import java.util.List;

public class MngProjQryRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 记录数量
     */
    private Long REC_NUM;

    private List<MngProjQryResList> LIST;

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public Long getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(Long REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

    public List<MngProjQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<MngProjQryResList> LIST) {
        this.LIST = LIST;
    }
}
