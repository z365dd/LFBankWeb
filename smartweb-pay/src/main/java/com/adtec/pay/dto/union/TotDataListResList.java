package com.adtec.pay.dto.union;

public class TotDataListResList {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 成功总金额
     */
    private Double SUCC_TOT_AMT;
    /**
     * 成功总数量
     */
    private Long SUCC_TOT_NUM;
    /**
     * 失败总金额
     */
    private Double FAIL_TOT_AMT;
    /**
     * 失败的总数量
     */
    private Long FAIL_TOT_NUM;


    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(Double TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public Double getSUCC_TOT_AMT() {
        return SUCC_TOT_AMT;
    }

    public void setSUCC_TOT_AMT(Double SUCC_TOT_AMT) {
        this.SUCC_TOT_AMT = SUCC_TOT_AMT;
    }

    public Long getSUCC_TOT_NUM() {
        return SUCC_TOT_NUM;
    }

    public void setSUCC_TOT_NUM(Long SUCC_TOT_NUM) {
        this.SUCC_TOT_NUM = SUCC_TOT_NUM;
    }

    public Double getFAIL_TOT_AMT() {
        return FAIL_TOT_AMT;
    }

    public void setFAIL_TOT_AMT(Double FAIL_TOT_AMT) {
        this.FAIL_TOT_AMT = FAIL_TOT_AMT;
    }

    public Long getFAIL_TOT_NUM() {
        return FAIL_TOT_NUM;
    }

    public void setFAIL_TOT_NUM(Long FAIL_TOT_NUM) {
        this.FAIL_TOT_NUM = FAIL_TOT_NUM;
    }
}
