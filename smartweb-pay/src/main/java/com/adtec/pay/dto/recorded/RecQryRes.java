package com.adtec.pay.dto.recorded;

import java.util.List;

/**
 * 商户入账明细表
 */
public class RecQryRes {
    /**
     * 返回入账明细列表记录数
     */
    private Long REC_NUM;
    /**
     * 总笔数
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 机构名称
     */
    private String brchName;
    /**
     * 返回入账明细列表
     */
    private List<RecQryResList> LIST;

    public Long getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(Long REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

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

    public String getBrchName() {
        return brchName;
    }

    public void setBrchName(String brchName) {
        this.brchName = brchName;
    }

    public List<RecQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<RecQryResList> LIST) {
        this.LIST = LIST;
    }
}
