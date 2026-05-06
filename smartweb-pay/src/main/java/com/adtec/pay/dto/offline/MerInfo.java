package com.adtec.pay.dto.offline;

import com.adtec.pay.dto.offline.temple.StatConverter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MerInfo {


    /**
     * 业务编号
      */
    @ColumnWidth(15)
    @ExcelProperty({"业务编号"})
    String BUSI_NO;
    /**
     * 业务名称
      */
    @ColumnWidth(15)
    @ExcelProperty({"业务名称"})
    String BUSI_NAME;
    /**
     * 所属机构号
     */
    @ColumnWidth(15)
    @ExcelProperty({"所属机构号"})
    String BRCH_ID;
    /**
     * 所属机构名称
      */
    @ColumnWidth(15)
    @ExcelProperty({"所属机构名称"})
    String BRCH_NAME;
    /**
     * 清算账户
      */
    @ColumnWidth(15)
    @ExcelProperty({"清算账户"})
    String ENTR_ACCT;
    /**
     * 清算账户名称
      */
    @ColumnWidth(15)
    @ExcelProperty({"清算账户名称"})
    String ENTR_ACCT_NAME;
    /**
     *  清算周期是否为T1|是否支持拆分支付    例如 Y|N   T1清算且不支持拆分支付
      */
    @ColumnWidth(20)
    @ExcelProperty({"是否支持拆分支付"})
    String PAY_INFO;

    @ColumnWidth(15)
    @ExcelProperty({"清算周期"})
    String CLR_TP;

    /**
     * 商户所属地区
      */
    @ColumnWidth(15)
    @ExcelProperty({"所属地区"})
    String ENTR_ADDR;
    /**
     * 商户咨询电话
      */
    @ColumnWidth(15)
    @ExcelProperty({"商户咨询电话"})
    String ENTR_TEL_NO;
    /**
     * 联系人
      */
    @ColumnWidth(15)
    @ExcelProperty({"联系人"})
    String NAME;
    /**
     * 商户状态  Y-已上架 N-已下架
      */
    @ColumnWidth(15)
    @ExcelProperty({"商户状态"})
    String OPEN_STAT;

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

    public String getBRCH_NAME() {
        return BRCH_NAME;
    }

    public void setBRCH_NAME(String BRCH_NAME) {
        this.BRCH_NAME = BRCH_NAME;
    }

    public String getENTR_ACCT() {
        return ENTR_ACCT;
    }

    public void setENTR_ACCT(String ENTR_ACCT) {
        this.ENTR_ACCT = ENTR_ACCT;
    }

    public String getENTR_ACCT_NAME() {
        return ENTR_ACCT_NAME;
    }

    public void setENTR_ACCT_NAME(String ENTR_ACCT_NAME) {
        this.ENTR_ACCT_NAME = ENTR_ACCT_NAME;
    }

    public String getPAY_INFO() {
        return PAY_INFO;
    }

    public void setPAY_INFO(String PAY_INFO) {
        this.PAY_INFO = PAY_INFO;
    }

    public String getENTR_ADDR() {
        return ENTR_ADDR;
    }

    public void setENTR_ADDR(String ENTR_ADDR) {
        this.ENTR_ADDR = ENTR_ADDR;
    }

    public String getENTR_TEL_NO() {
        return ENTR_TEL_NO;
    }

    public void setENTR_TEL_NO(String ENTR_TEL_NO) {
        this.ENTR_TEL_NO = ENTR_TEL_NO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getOPEN_STAT() {
        return OPEN_STAT;
    }

    public void setOPEN_STAT(String OPEN_STAT) {
        this.OPEN_STAT = OPEN_STAT;
    }

    public String getBRCH_ID() {
        return BRCH_ID;
    }

    public void setBRCH_ID(String BRCH_ID) {
        this.BRCH_ID = BRCH_ID;
    }

    public String getCLR_TP() {
        return CLR_TP;
    }

    public void setCLR_TP(String CLR_TP) {
        this.CLR_TP = CLR_TP;
    }
}
