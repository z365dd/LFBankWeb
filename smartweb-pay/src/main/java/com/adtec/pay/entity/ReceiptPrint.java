package com.adtec.pay.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 单据打印实体类
 */
@Getter
@Setter
@EqualsAndHashCode
public class ReceiptPrint {

    @ExcelProperty("日期")
    private String CLR_DATE;
    @ExcelProperty("缴费项目")
    private String BUSI_NAME;
    @ExcelProperty("缴费种类")
    private String TYPE;
    @ExcelProperty("支付方式")
    private String PAY_TP;
    @ExcelProperty("交易笔数(笔)")
    private String TRAN_NUM;
    @ExcelProperty("交易金额(元)")
    private String TRAN_AMT;
    @ExcelProperty("实际清算金额(元)")
    private String CLR_AMT;
    @ExcelProperty("备注")
    private String REMARK;

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public void setPAY_TP(String PAY_TP) {
        this.PAY_TP = PAY_TP;
    }

    public String getTRAN_NUM() {
        return TRAN_NUM;
    }

    public void setTRAN_NUM(String TRAN_NUM) {
        this.TRAN_NUM = TRAN_NUM;
    }

    public String getTRAN_AMT() {
        return TRAN_AMT;
    }

    public void setTRAN_AMT(String TRAN_AMT) {
        this.TRAN_AMT = TRAN_AMT;
    }

    public String getCLR_AMT() {
        return CLR_AMT;
    }

    public void setCLR_AMT(String CLR_AMT) {
        this.CLR_AMT = CLR_AMT;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
