package com.adtec.pay.dto.offline.detail;

import java.util.List;


public class DtlQryRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;

    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 应缴总金额
     */
    private Double TOT_PRCTL_AMT;
    /**
     * 成功总数量
     */
    private Long SUCC_TOT_NUM;
    /**
     * 成功总金额
     */
    private Double SUCC_TOT_AMT;
    /**
     * 成功应缴金额
     */
    private Double SUCC_PRCTL_AMT;
    /**
     * 失败的总数量
     */
    private Long FAIL_TOT_NUM;
    /**
     * 失败总金额
     */
    private Double FAIL_TOT_AMT;
    /**
     * 待缴费应缴金额
     */
    private Double FAIL_PRCTL_AMT;

    /**
     * 记录数量
     */
    private Long REC_NUM;

    /**
     * 非联网缴费收费项目查询-循环列表
     */
    private List<DtlQryResList> LIST;

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

    public Long getSUCC_TOT_NUM() {
        return SUCC_TOT_NUM;
    }

    public void setSUCC_TOT_NUM(Long SUCC_TOT_NUM) {
        this.SUCC_TOT_NUM = SUCC_TOT_NUM;
    }

    public Double getSUCC_TOT_AMT() {
        return SUCC_TOT_AMT;
    }

    public void setSUCC_TOT_AMT(Double SUCC_TOT_AMT) {
        this.SUCC_TOT_AMT = SUCC_TOT_AMT;
    }

    public Long getFAIL_TOT_NUM() {
        return FAIL_TOT_NUM;
    }

    public void setFAIL_TOT_NUM(Long FAIL_TOT_NUM) {
        this.FAIL_TOT_NUM = FAIL_TOT_NUM;
    }

    public Double getFAIL_TOT_AMT() {
        return FAIL_TOT_AMT;
    }

    public void setFAIL_TOT_AMT(Double FAIL_TOT_AMT) {
        this.FAIL_TOT_AMT = FAIL_TOT_AMT;
    }

    public Long getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(Long REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

    public List<DtlQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<DtlQryResList> LIST) {
        this.LIST = LIST;
    }

    public Double getTOT_PRCTL_AMT() {
        return TOT_PRCTL_AMT;
    }

    public void setTOT_PRCTL_AMT(Double TOT_PRCTL_AMT) {
        this.TOT_PRCTL_AMT = TOT_PRCTL_AMT;
    }

    public Double getSUCC_PRCTL_AMT() {
        return SUCC_PRCTL_AMT;
    }

    public void setSUCC_PRCTL_AMT(Double SUCC_PRCTL_AMT) {
        this.SUCC_PRCTL_AMT = SUCC_PRCTL_AMT;
    }

    public Double getFAIL_PRCTL_AMT() {
        return FAIL_PRCTL_AMT;
    }

    public void setFAIL_PRCTL_AMT(Double FAIL_PRCTL_AMT) {
        this.FAIL_PRCTL_AMT = FAIL_PRCTL_AMT;
    }
}
