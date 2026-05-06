package com.adtec.pay.dto.bookList;

import java.io.Serializable;

public class DctList implements Serializable {
    /**
     * 优惠编号
     */
    private String DCT_ID;
    /**
     * 优惠名称
     */
    private String DCT_NAME;
    /**
     * 优惠类型
     */
    private String DCT_TP;
    /**
     * 优惠子类型
     */
    private String DCT_SUB_TP;
    /**
     * 优惠额度
     */
    private Double DCT_QTA;
    /**
     * 优惠金额
     */
    private Double DCT_AMT;
    /**
     * 本行承担金额
     */
    private Double DCT_BANK_AMT;
    /**
     * 商户补贴金额
     */
    private Double DCT_MERT_AMT;
    /**
     * 手续费优惠方式
     */
    private String DCT_FEE_TP;
    /**
     * 本行占本分比
     */
    private String DCT_BANK_PART;
    /**
     * 商户占半分比
     */
    private String DCT_MERT_PART;
    /**
     * 优惠描述
     */
    private String DCT_DESC;
    /**
     * 机构承担核销账号
     */
    private String DCT_BANK_ACCT;
    /**
     * 银行承担机构号
     */
    private String DCT_BANK_BRCH;
    /**
     * 商户内部账号
     */
    private String DCT_MERT_ACCT;
    /**
     * 银行内部账号名称
     */
    private String DCT_BANK_ACCT_NAME;
    /**
     * 商户开户机构
     */
    private String DCT_MERT_BRCH;
    /**
     * 商户开户机构名称
     */
    private String DCT_MERT_BRCH_NAME;
    /**
     * 分摊方式
     */
    private String DCT_SHARE_TP;


    public String getDCT_ID() {
        return DCT_ID;
    }

    public void setDCT_ID(String DCT_ID) {
        this.DCT_ID = DCT_ID;
    }

    public String getDCT_NAME() {
        return DCT_NAME;
    }

    public void setDCT_NAME(String DCT_NAME) {
        this.DCT_NAME = DCT_NAME;
    }

    public String getDCT_TP() {
        return DCT_TP;
    }

    public void setDCT_TP(String DCT_TP) {
        this.DCT_TP = DCT_TP;
    }

    public String getDCT_SUB_TP() {
        return DCT_SUB_TP;
    }

    public void setDCT_SUB_TP(String DCT_SUB_TP) {
        this.DCT_SUB_TP = DCT_SUB_TP;
    }

    public Double getDCT_QTA() {
        return DCT_QTA;
    }

    public void setDCT_QTA(Double DCT_QTA) {
        this.DCT_QTA = DCT_QTA;
    }

    public Double getDCT_AMT() {
        return DCT_AMT;
    }

    public void setDCT_AMT(Double DCT_AMT) {
        this.DCT_AMT = DCT_AMT;
    }

    public Double getDCT_BANK_AMT() {
        return DCT_BANK_AMT;
    }

    public void setDCT_BANK_AMT(Double DCT_BANK_AMT) {
        this.DCT_BANK_AMT = DCT_BANK_AMT;
    }

    public Double getDCT_MERT_AMT() {
        return DCT_MERT_AMT;
    }

    public void setDCT_MERT_AMT(Double DCT_MERT_AMT) {
        this.DCT_MERT_AMT = DCT_MERT_AMT;
    }

    public String getDCT_FEE_TP() {
        return DCT_FEE_TP;
    }

    public void setDCT_FEE_TP(String DCT_FEE_TP) {
        this.DCT_FEE_TP = DCT_FEE_TP;
    }

    public String getDCT_BANK_PART() {
        return DCT_BANK_PART;
    }

    public void setDCT_BANK_PART(String DCT_BANK_PART) {
        this.DCT_BANK_PART = DCT_BANK_PART;
    }

    public String getDCT_MERT_PART() {
        return DCT_MERT_PART;
    }

    public void setDCT_MERT_PART(String DCT_MERT_PART) {
        this.DCT_MERT_PART = DCT_MERT_PART;
    }

    public String getDCT_DESC() {
        return DCT_DESC;
    }

    public void setDCT_DESC(String DCT_DESC) {
        this.DCT_DESC = DCT_DESC;
    }

    public String getDCT_BANK_ACCT() {
        return DCT_BANK_ACCT;
    }

    public void setDCT_BANK_ACCT(String DCT_BANK_ACCT) {
        this.DCT_BANK_ACCT = DCT_BANK_ACCT;
    }

    public String getDCT_BANK_BRCH() {
        return DCT_BANK_BRCH;
    }

    public void setDCT_BANK_BRCH(String DCT_BANK_BRCH) {
        this.DCT_BANK_BRCH = DCT_BANK_BRCH;
    }

    public String getDCT_MERT_ACCT() {
        return DCT_MERT_ACCT;
    }

    public void setDCT_MERT_ACCT(String DCT_MERT_ACCT) {
        this.DCT_MERT_ACCT = DCT_MERT_ACCT;
    }

    public String getDCT_BANK_ACCT_NAME() {
        return DCT_BANK_ACCT_NAME;
    }

    public void setDCT_BANK_ACCT_NAME(String DCT_BANK_ACCT_NAME) {
        this.DCT_BANK_ACCT_NAME = DCT_BANK_ACCT_NAME;
    }

    public String getDCT_MERT_BRCH() {
        return DCT_MERT_BRCH;
    }

    public void setDCT_MERT_BRCH(String DCT_MERT_BRCH) {
        this.DCT_MERT_BRCH = DCT_MERT_BRCH;
    }

    public String getDCT_MERT_BRCH_NAME() {
        return DCT_MERT_BRCH_NAME;
    }

    public void setDCT_MERT_BRCH_NAME(String DCT_MERT_BRCH_NAME) {
        this.DCT_MERT_BRCH_NAME = DCT_MERT_BRCH_NAME;
    }

    public String getDCT_SHARE_TP() {
        return DCT_SHARE_TP;
    }

    public void setDCT_SHARE_TP(String DCT_SHARE_TP) {
        this.DCT_SHARE_TP = DCT_SHARE_TP;
    }
}
