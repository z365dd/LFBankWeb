package com.adtec.pay.dto.offline.detail;

public class DtlImportRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 失败数量
     */
    private Long FAIL_NUM;
    /**
     * 导入成功总笔数
     */
    private Long SUCC_TOT_NUM;
    /**
     * 导入成功总金额
     */
    private Double SUCC_TOT_AMT;
    /**
     * 导入失败总笔数
     */
    private Long FAIL_TOT_NUM;
    /**
     * 导入失败总金额
     */
    private Double FAIL_TOT_AMT;
    /**
     * 返回信息
     */
    private String RET_MSG;

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public Long getFAIL_NUM() {
        return FAIL_NUM;
    }

    public void setFAIL_NUM(Long FAIL_NUM) {
        this.FAIL_NUM = FAIL_NUM;
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

    public String getRET_MSG() {
        return RET_MSG;
    }

    public void setRET_MSG(String RET_MSG) {
        this.RET_MSG = RET_MSG;
    }
}
