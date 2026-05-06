package com.adtec.pay.dto.bus;

import java.util.List;

/**
 * 公交卡白名单查询响应
 */
public class WhiteListQryRes {
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 记录数
     */
    private int REC_NUM;
    /**
     * 循环列表
     */
    private List<WhiteListQryResList> LIST;

    public Long getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(Long TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public int getREC_NUM() {
        return REC_NUM;
    }

    public void setREC_NUM(int REC_NUM) {
        this.REC_NUM = REC_NUM;
    }

    public List<WhiteListQryResList> getLIST() {
        return LIST;
    }

    public void setLIST(List<WhiteListQryResList> LIST) {
        this.LIST = LIST;
    }
}
