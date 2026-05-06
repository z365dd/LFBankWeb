package com.adtec.pay.dto.signbat;

import java.util.List;

public class SignBatQryRes {

    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 签约信息列表
     */
    private List<SignBatQryResList> LIST;

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public List<SignBatQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<SignBatQryResList> LIST) {
        this.LIST = LIST;
    }
}
