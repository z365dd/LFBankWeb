package com.adtec.pay.dto.recorded;

/**
 * 商户入账明细返回列表
 */
public class RecQryResList {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 清算日期
     */
    private String CLR_DATE;
    /**
     * 渠道号
     */
    private String CHNL_NO;
    /**
     * 支付类型
     */
    private String PAY_TP;
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 清算金额
     */
    private Double CLR_AMT;
    /**
     * 商户补贴金额
     */
    private Double DCT_MERT_AMT;
    /**
     * 本行承担金额
     */
    private Double DCT_BANK_AMT;

    /**
     * 手续费金额
     */
    private Double FEE_AMT;
    /**
     * 优惠金额
     */
    private Double DCT_AMT;

    /**
     * 批量金额
     */
    private Double BAT_AMT;
    /**
     * id
     */
    private int id;

    /**
     * pid 入账查询使用 父列表的id
     */
    private int pid;

    public RecQryResList() {
    }

    public RecQryResList(RecQryResList object) {
        BUSI_NO = object.getBUSI_NO();
        BUSI_NAME = object.getBUSI_NAME();
        CHNL_NO = object.getCHNL_NO();
        CLR_DATE = object.getCLR_DATE();
//        PAY_TP = object.getPAY_TP();
//        TOT_NUM = object.getTOT_NUM();
//        TOT_AMT = object.getTOT_AMT();
//        CLR_AMT = object.getCLR_AMT();
//        DCT_MERT_AMT = object.getDCT_MERT_AMT();
//        DCT_BANK_AMT = object.getDCT_BANK_AMT();
//        FEE_AMT = object.getFEE_AMT();

    }

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

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public void setPAY_TP(String PAY_TP) {
        this.PAY_TP = PAY_TP;
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

    public Double getCLR_AMT() {
        return CLR_AMT;
    }

    public void setCLR_AMT(Double CLR_AMT) {
        this.CLR_AMT = CLR_AMT;
    }

    public Double getDCT_MERT_AMT() {
        return DCT_MERT_AMT;
    }

    public void setDCT_MERT_AMT(Double DCT_MERT_AMT) {
        this.DCT_MERT_AMT = DCT_MERT_AMT;
    }

    public Double getDCT_BANK_AMT() {
        return DCT_BANK_AMT;
    }

    public void setDCT_BANK_AMT(Double DCT_BANK_AMT) {
        this.DCT_BANK_AMT = DCT_BANK_AMT;
    }

    public Double getFEE_AMT() {
        return FEE_AMT;
    }

    public void setFEE_AMT(Double FEE_AMT) {
        this.FEE_AMT = FEE_AMT;
    }

    public Double getDCT_AMT() {
        return DCT_AMT;
    }

    public void setDCT_AMT(Double DCT_AMT) {
        this.DCT_AMT = DCT_AMT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Double getBAT_AMT() {
        return BAT_AMT;
    }

    public void setBAT_AMT(Double BAT_AMT) {
        this.BAT_AMT = BAT_AMT;
    }
}
