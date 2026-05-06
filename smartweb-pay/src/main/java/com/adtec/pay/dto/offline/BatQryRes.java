package com.adtec.pay.dto.offline;

import java.util.List;

public class BatQryRes {

    /**
     * 总数量
     */
    private Long TOT_NUM;

    /**
     * 记录数量
     */
    private Long REC_NUM;

    /**
     * 非联网缴费批次信息查询相应 - 列表数据
     */
    private List<BatQryResList> LIST;

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

    public List<BatQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<BatQryResList> LIST) {
        this.LIST = LIST;
    }
}
