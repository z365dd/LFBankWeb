package com.adtec.pay.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 批量信息数据数据解析使用
 */
@Getter
@Setter
@EqualsAndHashCode
public class BatDtl {

    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 名称
     */
    private String NAME;
    /**
     * 手机号码
     */
    private String PHONE_NO;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 总面积
     */
    private Double TOT_AREA;
    /**
     * 单价
     */
    private Double UNIT_PRICE;
    /**
     * 实际金额
     */
    private Double PRCTL_AMT;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 优惠金额
     */
    private Double DCT_AMT;
    /**
     * 优惠额度
     */
    private Double DCT_QTA;
    /**
     * 滞纳金金额
     */
    private Double LATE_FEE_AMT;
    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 地址
     */
    private String ADDR;
    /**
     * 小区名称
     */
    private String COMMUNITY;
    /**
     * 楼号
     */
    private String BUID_NO;
    /**
     * 单元编号
     */
    private String UNIT_NO;
    /**
     * 房间编号
     */
    private String ROOM_NO;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 学号
     */
    private String STU_ID;
    /**
     * 班级
     */
    private String STU_CLASS;
    /**
     * 专业
     */
    private String MAJOR;
    /**
     * 学院
     */
    private String COLLEGE;
    /**
     * 学校
     */
    private String SCHOOL;


    /**
     * 项目编号
     */
    private String PROJ_NO;
    /**
     * 使用数量
     */
    private Long USE_NUM;
    /**
     * 证件类型
     */
    private String CERT_TP;


    /**
     * 手续费金额
     */
    private Double FEE_AMT;

    /**
     * 退款金额
     */
    private Double RFND_AMT;
    /**
     * 退汇状态
     */
    private String RFND_STAT;
    /**
     * 支付类型
     */
    private String PAY_TP;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 柜员号
     */
    private String TLR_NO;
    /**
     * 机构
     */
    private String BRCH;
    /**
     * 平台日期
     */
    private String PLAT_DATE;
    /**
     * 平台流水
     */
    private String PLAT_SEQ;
    /**
     * 缴费日期
     */
    private String PAY_DATE;
    /**
     * 支付流水
     */
    private String PAY_SEQ;
    /**
     * 核心日期
     */
    private String HOST_DATE;
    /**
     * 核心流水
     */
    private String HOST_SEQ;
    /**
     * 清算日期
     */
    private String CLR_DATE;
    /**
     * 清算标志
     */
    private String CLR_FLG;
    /**
     * 返回代码
     */
    private String RET_CODE;
    /**
     * 返回信息
     */
    private String RET_MSG;

    public String getPAY_NO() {
        return PAY_NO;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public Double getTOT_AREA() {
        return TOT_AREA;
    }

    public Double getUNIT_PRICE() {
        return UNIT_PRICE;
    }

    public Double getPRCTL_AMT() {
        return PRCTL_AMT;
    }

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public Double getDCT_AMT() {
        return DCT_AMT;
    }

    public Double getLATE_FEE_AMT() {
        return LATE_FEE_AMT;
    }

    public String getPROJ_NAME() {
        return PROJ_NAME;
    }

    public String getADDR() {
        return ADDR;
    }

    public String getCOMMUNITY() {
        return COMMUNITY;
    }

    public String getBUID_NO() {
        return BUID_NO;
    }

    public String getUNIT_NO() {
        return UNIT_NO;
    }

    public String getROOM_NO() {
        return ROOM_NO;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public String getPROJ_NO() {
        return PROJ_NO;
    }

    public Long getUSE_NUM() {
        return USE_NUM;
    }

    public String getCERT_TP() {
        return CERT_TP;
    }

    public String getSTU_ID() {
        return STU_ID;
    }

    public String getSTU_CLASS() {
        return STU_CLASS;
    }

    public String getCOLLEGE() {
        return COLLEGE;
    }

    public String getSCHOOL() {
        return SCHOOL;
    }

    public String getMAJOR() {
        return MAJOR;
    }

    public Double getDCT_QTA() {
        return DCT_QTA;
    }

    public Double getFEE_AMT() {
        return FEE_AMT;
    }

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public String getRFND_STAT() {
        return RFND_STAT;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public String getSTR_DATE() {
        return STR_DATE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public String getTLR_NO() {
        return TLR_NO;
    }

    public String getBRCH() {
        return BRCH;
    }

    public String getPLAT_DATE() {
        return PLAT_DATE;
    }

    public String getPLAT_SEQ() {
        return PLAT_SEQ;
    }

    public String getPAY_DATE() {
        return PAY_DATE;
    }

    public String getPAY_SEQ() {
        return PAY_SEQ;
    }

    public String getHOST_DATE() {
        return HOST_DATE;
    }

    public String getHOST_SEQ() {
        return HOST_SEQ;
    }

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public String getCLR_FLG() {
        return CLR_FLG;
    }

    public String getRET_CODE() {
        return RET_CODE;
    }

    public String getRET_MSG() {
        return RET_MSG;
    }
}
