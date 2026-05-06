package com.adtec.pay.dto.offline.mission;

public class MissionAutoRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(Double TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }
}
