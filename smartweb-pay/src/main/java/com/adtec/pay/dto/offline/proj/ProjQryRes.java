package com.adtec.pay.dto.offline.proj;

import java.util.List;

public class ProjQryRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 记录数量
     */
    private Long REC_NUM;

    /**
     * 非联网缴费收费项目查询-循环列表
     */
    private List<ProjQryResList> LIST;

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

    public List<ProjQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<ProjQryResList> LIST) {
        this.LIST = LIST;
    }
}
