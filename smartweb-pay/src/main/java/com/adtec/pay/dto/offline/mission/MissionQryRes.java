package com.adtec.pay.dto.offline.mission;

import java.util.List;

public class MissionQryRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 记录数量
     */
    private Long REC_NUM;

    /**
     * 非联网缴费报表查询响应-循环列表
     */
    private List<MissionQryResList> LIST;

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

    public List<MissionQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<MissionQryResList> LIST) {
        this.LIST = LIST;
    }
}
