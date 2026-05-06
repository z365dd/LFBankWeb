package com.adtec.pay.dto.offline;

public class BatQryResList {


    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 批次号
     */
    private String BAT_NO;
    /**
     * 批次名称
     */
    private String BAT_NAME;
    /**
     * 创建日期
     */
    private String CRT_DATE;
    /**
     * 交易类型
     */
    private String TRAN_TP;
    /**
     * 状态
     * 00-待启用
     * 01-已启用
     * 02-已作废
     * 03-已过期
     */
    private String STAT;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 文件名称
     */
    private String FILE_NAME;
    /**
     * 总数量
     */
    private Long TOT_NUM;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 已缴费总笔数
     */
    private Long SUCC_TOT_NUM;
    /**
     * 已缴费总金额
     */
    private Double SUCC_TOT_AMT;
    /**
     * 未缴费总笔数
     */
    private Long FAIL_TOT_NUM;
    /**
     * 未缴费总金额
     */
    private Double FAIL_TOT_AMT;
    /**
     * 清算类型
     */
    private String CLR_TP;
    /**
     * 柜员号
     */
    private String TLR_NO;
    /**
     * 机构
     */
    private String BRCH;
    /**
     * 业务种类
     */
    private String BUSI_KD;

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

    public String getBAT_NO() {
        return BAT_NO;
    }

    public void setBAT_NO(String BAT_NO) {
        this.BAT_NO = BAT_NO;
    }

    public String getBAT_NAME() {
        return BAT_NAME;
    }

    public void setBAT_NAME(String BAT_NAME) {
        this.BAT_NAME = BAT_NAME;
    }

    public String getCRT_DATE() {
        return CRT_DATE;
    }

    public void setCRT_DATE(String CRT_DATE) {
        this.CRT_DATE = CRT_DATE;
    }

    public String getTRAN_TP() {
        return TRAN_TP;
    }

    public void setTRAN_TP(String TRAN_TP) {
        this.TRAN_TP = TRAN_TP;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getSTR_DATE() {
        return STR_DATE;
    }

    public void setSTR_DATE(String STR_DATE) {
        this.STR_DATE = STR_DATE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
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

    public String getCLR_TP() {
        return CLR_TP;
    }

    public void setCLR_TP(String CLR_TP) {
        this.CLR_TP = CLR_TP;
    }

    public String getTLR_NO() {
        return TLR_NO;
    }

    public void setTLR_NO(String TLR_NO) {
        this.TLR_NO = TLR_NO;
    }

    public String getBRCH() {
        return BRCH;
    }

    public void setBRCH(String BRCH) {
        this.BRCH = BRCH;
    }

    public String getBUSI_KD() {
        return BUSI_KD;
    }

    public void setBUSI_KD(String BUSI_KD) {
        this.BUSI_KD = BUSI_KD;
    }
}
