package com.adtec.pay.dto.bookList;

import java.util.List;

public class MLppQryBookListRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 优惠金额
     */
    private Double DCT_AMT;
    /**
     * 成功总数量
     */
    private Long SUCC_TOT_NUM;
    /**
     * 成功总金额
     */
    private Double SUCC_TOT_AMT;
    /**
     * 失败的总数量
     */
    private Long FAIL_TOT_NUM;
    /**
     * 失败总金额
     */
    private Double FAIL_TOT_AMT;
    /**
     * 冲正总数量
     */
    private Long REVS_TOT_NUM;
    /**
     * 冲正总金额
     */
    private Double REVS_TOT_AMT;

    /**
     * 待清算笔数
     */
    private Double WAIT_CLR_NUM;

    /**
     * 待清算金额
     */
    private Double WAIT_CLR_AMT;

    /**
     * 优惠列表
     */
    private List<BookList> BOOK_LIST;

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

    public Double getDCT_AMT() {
        return DCT_AMT;
    }

    public void setDCT_AMT(Double DCT_AMT) {
        this.DCT_AMT = DCT_AMT;
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

    public Long getREVS_TOT_NUM() {
        return REVS_TOT_NUM;
    }

    public void setREVS_TOT_NUM(Long REVS_TOT_NUM) {
        this.REVS_TOT_NUM = REVS_TOT_NUM;
    }

    public Double getREVS_TOT_AMT() {
        return REVS_TOT_AMT;
    }

    public void setREVS_TOT_AMT(Double REVS_TOT_AMT) {
        this.REVS_TOT_AMT = REVS_TOT_AMT;
    }

    public List<BookList> getBOOK_LIST() {
        return BOOK_LIST;
    }

    public void setBOOK_LIST(List<BookList> bOOK_LIST) {
        BOOK_LIST = bOOK_LIST;
    }

    public Double getWAIT_CLR_NUM() {
        return WAIT_CLR_NUM;
    }

    public void setWAIT_CLR_NUM(Double wAIT_CLR_NUM) {
        WAIT_CLR_NUM = wAIT_CLR_NUM;
    }

    public Double getWAIT_CLR_AMT() {
        return WAIT_CLR_AMT;
    }

    public void setWAIT_CLR_AMT(Double wAIT_CLR_AMT) {
        WAIT_CLR_AMT = wAIT_CLR_AMT;
    }


}
