package com.adtec.pay.dto.check;

public class ChkQryResList {


    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 对账日期
     */
    private String CHK_DATE;
    /**
     * 任务编号
     */
    private String TASK_NO;
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 对账状态
     */
    private String CHK_STAT;
    /**
     * 差错数量
     */
    private Long ERR_NUM;

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

    public String getCHK_DATE() {
        return CHK_DATE;
    }

    public void setCHK_DATE(String CHK_DATE) {
        this.CHK_DATE = CHK_DATE;
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

    public String getTASK_NO() {
        return TASK_NO;
    }

    public void setTASK_NO(String TASK_NO) {
        this.TASK_NO = TASK_NO;
    }

    public String getCHK_STAT() {
        return CHK_STAT;
    }

    public void setCHK_STAT(String CHK_STAT) {
        this.CHK_STAT = CHK_STAT;
    }

    public Long getERR_NUM() {
        return ERR_NUM;
    }

    public void setERR_NUM(Long ERR_NUM) {
        this.ERR_NUM = ERR_NUM;
    }
}

