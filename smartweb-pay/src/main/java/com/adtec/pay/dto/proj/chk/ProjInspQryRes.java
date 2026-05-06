package com.adtec.pay.dto.proj.chk;

import com.adtec.pay.dto.proj.mng.MngProjQryResList;

import java.util.List;

public class ProjInspQryRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 记录数量
     */
    private Long REC_NUM;

    private List<ProjInspQryResList> LIST;

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

    public List<ProjInspQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<ProjInspQryResList> LIST) {
        this.LIST = LIST;
    }
}
