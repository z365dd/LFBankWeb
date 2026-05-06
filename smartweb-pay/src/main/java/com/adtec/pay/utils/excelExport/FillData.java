package com.adtec.pay.utils.excelExport;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

public class FillData {
    /**
     * 机构名称
     */
    private String instName;
    /**
     * 商户名称
     */
    private String busiName;
    /**
     * 商户编号
     */
    private String busiNo;
    /**
     * 清算账户
     */
    private String clearAcctName;
    /**
     * 清算账号
     */
    private String clearAcctNo;
    /**
     * yyyy-MM-dd 交易日期
     */
    private String transDate;
    /**
     * 交易笔数
     */
    private String totalNum;
    /**
     * 交易金额
     */
    private String totalAmt;
    /**
     * 手续费金额
     */
    private String totalFeeAmt;
    /**
     * 清算日期
     */
    private String clearDate;
    /**
     * 清算笔数
     */
    private String totalClearNum;
    /**
     * 清算金额
     */
    private String totalClearAmt;
    /**
     * 交易流水
     */
    private String serialNo;
    /**
     * 缴费笔数
     */
    private String num;
    /**
     * 缴费金额
     */
    private String amt;
    /**
     * 清算金额
     */
    private String realAmt;
    /**
     * 手续费金额
     */
    private String feeAmt;
    /**
     * 附加信息
     */
    private String extraInfo;

    public FillData() {

    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public String getBusiNo() {
        return busiNo;
    }

    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }

    public String getClearAcctName() {
        return clearAcctName;
    }

    public void setClearAcctName(String clearAcctName) {
        this.clearAcctName = clearAcctName;
    }

    public String getClearAcctNo() {
        return clearAcctNo;
    }

    public void setClearAcctNo(String clearAcctNo) {
        this.clearAcctNo = clearAcctNo;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTotalFeeAmt() {
        return totalFeeAmt;
    }

    public void setTotalFeeAmt(String totalFeeAmt) {
        this.totalFeeAmt = totalFeeAmt;
    }

    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
    }

    public String getTotalClearNum() {
        return totalClearNum;
    }

    public void setTotalClearNum(String totalClearNum) {
        this.totalClearNum = totalClearNum;
    }

    public String getTotalClearAmt() {
        return totalClearAmt;
    }

    public void setTotalClearAmt(String totalClearAmt) {
        this.totalClearAmt = totalClearAmt;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getRealAmt() {
        return realAmt;
    }

    public void setRealAmt(String realAmt) {
        this.realAmt = realAmt;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * 前端数据赋值
     *
     * @param object 前端传过来的需要填充到excel中的数据
     */
    public FillData(JSONObject object) {
        FillData fillData = new FillData();
        this.instName = StringUtils.isBlank(object.getString("instName")) ? "" : object.getString("instName");
        this.busiName = StringUtils.isBlank(object.getString("busiName")) ? "" : object.getString("busiName");
        this.busiNo = StringUtils.isBlank(object.getString("busiNo")) ? "" : object.getString("busiNo");
        this.clearAcctName = StringUtils.isBlank(object.getString("clearAcctName")) ? "" : object.getString("clearAcctName");
        this.clearAcctNo = StringUtils.isBlank(object.getString("clearAcctNo")) ? "" : object.getString("clearAcctNo");
        this.transDate = StringUtils.isBlank(object.getString("transDate")) ? "" : object.getString("transDate");
        this.num = StringUtils.isBlank(object.getString("num")) ? "" : object.getString("num");
        this.totalAmt = StringUtils.isBlank(object.getString("totalAmt")) ? "" : "￥" + object.getString("totalAmt");
        this.totalFeeAmt = StringUtils.isBlank(object.getString("totalFeeAmt")) ? "" : "￥" + object.getString("totalFeeAmt");
        this.clearDate = StringUtils.isBlank(object.getString("clearDate")) ? "" : object.getString("clearDate");
        this.totalClearNum = StringUtils.isBlank(object.getString("totalClearNum")) ? "" : object.getString("totalClearNum");
        this.totalClearAmt = StringUtils.isBlank(object.getString("totalClearAmt")) ? "" : "￥" + object.getString("totalClearAmt");
        this.amt = StringUtils.isBlank(object.getString("amt")) ? "" : "￥" + object.getString("amt");
        this.realAmt = StringUtils.isBlank(object.getString("realAmt")) ? "" : "￥" + object.getString("realAmt");
        this.feeAmt = StringUtils.isBlank(object.getString("feeAmt")) ? "" : "￥" + object.getString("feeAmt");
        this.serialNo = StringUtils.isBlank(object.getString("serialNo")) ? "" : object.getString("serialNo");
        this.extraInfo = StringUtils.isBlank(object.getString("extraInfo")) ? "" : object.getString("extraInfo");
    }


}
