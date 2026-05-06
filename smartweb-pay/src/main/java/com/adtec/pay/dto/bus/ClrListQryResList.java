package com.adtec.pay.dto.bus;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 公交卡清算列表查询 循环列表
 */
@Getter
@Setter
@EqualsAndHashCode
public class ClrListQryResList {
    /**
     * 清算日期
     */
    @ColumnWidth(15)
    @ExcelProperty({"清算日期"})
    private String CLR_DATE;
    /**
     * 清算日期
     */
    @ColumnWidth(15)
    @ExcelProperty({"确认日期"})
    private String CONFM_DATE;
    /**
     * 业务编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"业务编号"})
    private String BUSI_NO;
    /**
     * 业务名称
     */
    @ColumnWidth(15)
    @ExcelProperty({"业务名称"})
    private String BUSI_NAME;
    /**
     * 手续费总金额   激活手续费金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"激活手续费金额"})
    private Double FEE_TOT_AMT;
    /**
     * 手续费总笔数
     */

    @ColumnWidth(20)
    @ExcelProperty({"激活手续费笔数"})
    private Long FEE_TOT_NUM;
    /**
     * 圈存总金额    圈存、充值金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"圈存金额"})
    private Double CRFLD_TOT_AMT;
    /**
     * 圈存总笔数
     */
    @ColumnWidth(15)
    @ExcelProperty({"圈存笔数"})
    private Long CRFLD_TOT_NUM;
    /**
     * 退款总金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"退卡金额"})
    private Double RFND_TOT_AMT;
    /**
     * 退汇总数量
     */
    @ColumnWidth(15)
    @ExcelProperty({"退卡数量"})
    private Long RFND_TOT_NUM;
    /**
     * 轧差金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"轧差金额"})
    private Double NETG_AMT;
    /**
     * 清算金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"清算金额"})
    private Double CLR_AMT;

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
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

    public Double getFEE_TOT_AMT() {
        return FEE_TOT_AMT;
    }

    public void setFEE_TOT_AMT(Double FEE_TOT_AMT) {
        this.FEE_TOT_AMT = FEE_TOT_AMT;
    }

    public Long getFEE_TOT_NUM() {
        return FEE_TOT_NUM;
    }

    public void setFEE_TOT_NUM(Long FEE_TOT_NUM) {
        this.FEE_TOT_NUM = FEE_TOT_NUM;
    }

    public Double getCRFLD_TOT_AMT() {
        return CRFLD_TOT_AMT;
    }

    public void setCRFLD_TOT_AMT(Double CRFLD_TOT_AMT) {
        this.CRFLD_TOT_AMT = CRFLD_TOT_AMT;
    }

    public Long getCRFLD_TOT_NUM() {
        return CRFLD_TOT_NUM;
    }

    public void setCRFLD_TOT_NUM(Long CRFLD_TOT_NUM) {
        this.CRFLD_TOT_NUM = CRFLD_TOT_NUM;
    }

    public Double getRFND_TOT_AMT() {
        return RFND_TOT_AMT;
    }

    public void setRFND_TOT_AMT(Double RFND_TOT_AMT) {
        this.RFND_TOT_AMT = RFND_TOT_AMT;
    }

    public Long getRFND_TOT_NUM() {
        return RFND_TOT_NUM;
    }

    public void setRFND_TOT_NUM(Long RFND_TOT_NUM) {
        this.RFND_TOT_NUM = RFND_TOT_NUM;
    }

    public Double getNETG_AMT() {
        return NETG_AMT;
    }

    public void setNETG_AMT(Double NETG_AMT) {
        this.NETG_AMT = NETG_AMT;
    }

    public Double getCLR_AMT() {
        return CLR_AMT;
    }

    public void setCLR_AMT(Double CLR_AMT) {
        this.CLR_AMT = CLR_AMT;
    }

    public String getCONFM_DATE() {
        return CONFM_DATE;
    }

    public void setCONFM_DATE(String CONFM_DATE) {
        this.CONFM_DATE = CONFM_DATE;
    }
}
