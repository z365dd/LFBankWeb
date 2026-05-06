package com.adtec.pay.dto.offline.temple;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 商户入账导出excel对应实体类
 */
@Getter
@Setter
@EqualsAndHashCode
public class AccountExcelData {
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"业务编号"})
    private String BUSI_NO;

    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"业务名称"})
    private String BUSI_NAME;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"入账日期"})
    private String CLR_DATE;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费笔数"})
    private Long TOT_NUM;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费金额"})
    private Double TOT_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"退费金额"})
    private Double RFND_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"清算金额"})
    private Double CLR_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"商户补贴"})
    private Double DCT_MERT_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"银行补贴"})
    private Double DCT_BANK_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"手续费"})
    private Double FEE_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"码牌金额"})
    private Double OTH_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"差错补录"})
    private Double MUAL_AMT;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"交易开始日期"})
    private String STR_DATE;
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"交易结束日期"})
    private String END_DATE;

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

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(Double RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
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

    public Double getOTH_AMT() {
        return OTH_AMT;
    }

    public void setOTH_AMT(Double OTH_AMT) {
        this.OTH_AMT = OTH_AMT;
    }

    public Double getMUAL_AMT() {
        return MUAL_AMT;
    }

    public void setMUAL_AMT(Double MUAL_AMT) {
        this.MUAL_AMT = MUAL_AMT;
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
}