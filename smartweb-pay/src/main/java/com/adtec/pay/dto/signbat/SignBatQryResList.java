package com.adtec.pay.dto.signbat;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class SignBatQryResList {

    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"协议编号"})
    private String SIGN_PROT_NO;
    /**
     * 缴费单位
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费单位"})
    private String BUSI_NAME;
    /**
     * 渠道号
     */
    @ColumnWidth(15)
    @ExcelProperty({"渠道号"})
    private String CHNL_NO;
    /**
     * 用户号
     */
    @ColumnWidth(15)
    @ExcelProperty({"用户号"})
    private String OTH_CUST_NO;
    /**
     * 用户姓名
     */
    @ColumnWidth(15)
    @ExcelProperty({"用户姓名"})
    private String OTH_CUST_NAME;
    /**
     * 住户地
     */
    @ColumnWidth(15)
    @ExcelProperty({"住户地"})
    private String ADDR;
    /**
     * 签约日期
     */
    @ColumnWidth(15)
    @ExcelProperty({"签约日期"})
    private String SIGN_DATE;
    /**
     * 签约状态  使用SignStatConverter对签约状态进行映射
     * 00-已签约
     * 01-已解约
     */
    @ColumnWidth(15)
    @ExcelProperty(value="签约状态",converter = SignStatConverter.class)
    private String SIGN_STAT;
    /**
     * 解约日期
     */
    @ColumnWidth(15)
    @ExcelProperty({"解约日期"})
    private String CANCL_SIGN_DATE;
    /**
     * 签约机构
     */
    @ColumnWidth(15)
    @ExcelProperty({"签约机构"})
    private String SIGN_BRCH;
    /**
     * 签约用户信息列表
     */
    @ExcelIgnore
    private List<SignBatQryResListList> ACCT_NODE;

    public String getSIGN_PROT_NO() {
        return SIGN_PROT_NO;
    }

    public void setSIGN_PROT_NO(String SIGN_PROT_NO) {
        this.SIGN_PROT_NO = SIGN_PROT_NO;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
    }

    public String getOTH_CUST_NO() {
        return OTH_CUST_NO;
    }

    public void setOTH_CUST_NO(String OTH_CUST_NO) {
        this.OTH_CUST_NO = OTH_CUST_NO;
    }

    public String getOTH_CUST_NAME() {
        return OTH_CUST_NAME;
    }

    public void setOTH_CUST_NAME(String OTH_CUST_NAME) {
        this.OTH_CUST_NAME = OTH_CUST_NAME;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    public String getSIGN_DATE() {
        return SIGN_DATE;
    }

    public void setSIGN_DATE(String SIGN_DATE) {
        this.SIGN_DATE = SIGN_DATE;
    }

    public String getSIGN_STAT() {
        return SIGN_STAT;
    }

    public void setSIGN_STAT(String SIGN_STAT) {
        this.SIGN_STAT = SIGN_STAT;
    }

    public String getCANCL_SIGN_DATE() {
        return CANCL_SIGN_DATE;
    }

    public void setCANCL_SIGN_DATE(String CANCL_SIGN_DATE) {
        this.CANCL_SIGN_DATE = CANCL_SIGN_DATE;
    }

    public List<SignBatQryResListList> getACCT_NODE() {
        return ACCT_NODE;
    }

    public void setACCT_NODE(List<SignBatQryResListList> ACCT_NODE) {
        this.ACCT_NODE = ACCT_NODE;
    }

    public String getSIGN_BRCH() {
        return SIGN_BRCH;
    }

    public void setSIGN_BRCH(String SIGN_BRCH) {
        this.SIGN_BRCH = SIGN_BRCH;
    }
}
